/*
 * DrogaView.java
 * Created on 2005-11-08
 * Copyright 2004-2005 e-informatyka.pl. All rights reserved.
 */
package pl.wroc.pwr.iis.traffic.presentation.view;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.logger.Logger;
import pl.wroc.pwr.iis.traffic.domain.entity.Droga;
import pl.wroc.pwr.iis.traffic.domain.entity.Pojazd;
import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;
import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;
import pl.wroc.pwr.iis.traffic.presentation.model.WezelView;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.Paint2D;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.PojazdView;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.PolaczenieView;

/**
 * @author M.Stanek <mikol@e-informatyka.pl>
 */
public class DrogaView implements WezelView {
	private static final long serialVersionUID = -4111100556165102381L;
	
	public static final boolean RYSUJ_TLO = true;
	public static final boolean RYSUJ_SIATKE = false;
	
    public static final int GESTOSC_SIATKI = 5;
    public static final int SZEROKOSC_DROGI = 30; //13
    
    private enum Kierunek { LEWO , PRAWO }
    
    /** Referencja na droge w grafie polonczen sieci drog. */
    private Droga droga = new Droga();
    
    /** 
     * Tablica zawiera wspolczynniki kierunkowe kazdego segmentu drogi.
     * Aktualizowane sa one podczas wywolania procedury prowadzDroge
     */
    Float[] wspolczynniki = new Float[0];
    
    /** Kolejne wspolrzedne zalaman drogi. Sa to wspolrzedne linii srodkowej */
    ArrayList<PunktMapy> punktyPosrednie = new ArrayList<PunktMapy>();
    
    PolaczenieView[] odjazdy = new PolaczenieView[Droga.DOMYSLNA_ILOSC_PASOW];
    PolaczenieView[] dojazdy = new PolaczenieView[Droga.DOMYSLNA_ILOSC_PASOW];

    ArrayList punktyLewe  = new ArrayList();
    ArrayList punktyPrawe = new ArrayList();
    ArrayList pas     = new ArrayList();
	private Polygon obrys;
	
    
    public Object clone() throws CloneNotSupportedException {
    	return null;
    }
    
    /**
     * 
     */
    public DrogaView() {
    }
    
    public DrogaView(int x, int y) {
    	setIloscPasow(2);
    	addOstatniPunkt(x, y);
    	addOstatniPunkt(x, y);
    }
    
    public DrogaView(PunktMapy punkt) {
    	setIloscPasow(2);
    	addOstatniPunkt(punkt);
    	addOstatniPunkt(punkt.getX(), punkt.getY());
    }
    
    protected ArrayList<PunktMapy> getPunktyPosrednie() {
        return this.punktyPosrednie;
    }
    
    public PunktMapy[] getPunktyEdycji() {
    	PunktMapy[] punktyPolaczen = new PunktMapy[this.punktyPosrednie.size()];
    	this.punktyPosrednie.toArray(punktyPolaczen);
    	return punktyPolaczen;
    }
    
    public PunktMapy[] getPunktyWjazdowe() {
    	PunktMapy[] punktyPolaczen = new PunktMapy[this.pas.size()];
    	
    	for (int numerPasa = 0; numerPasa < this.pas.size(); numerPasa++) {
        	PunktMapy[] p = (PunktMapy[]) this.pas.get(numerPasa);
        	if (p.length > 0) {
    			punktyPolaczen[numerPasa] = p[0];
        	}
        }
    	
    	return punktyPolaczen;
    }
    
    public boolean isZaznaczono(int x, int y) {
    	if (obrys != null) {
    		int s2 = SASIEDZTWO_BADANIA_PRZECIECIA *2;
    		return obrys.intersects(x -SASIEDZTWO_BADANIA_PRZECIECIA, y - SASIEDZTWO_BADANIA_PRZECIECIA, s2, s2);
    	}
    	return false;
    }
    
    public PunktMapy[] getPunktyWyjazdowe() {
    	PunktMapy[] punktyPolaczen = new PunktMapy[this.pas.size()];
    	
    	for (int numerPasa = 0; numerPasa < this.pas.size(); numerPasa++) {
    		PunktMapy[] p = (PunktMapy[]) this.pas.get(numerPasa);
    		if (p.length > 0) {
    			punktyPolaczen[numerPasa] = p[p.length - 1];
    		}
    	}
    	
    	return punktyPolaczen;
    }
    
    public void setOstatniPunkt(int x, int y) {
        ArrayList<PunktMapy> p = getPunktyPosrednie();
        p.set(p.size()-1, new PunktMapy(x, y));
        
        updateData();
    }
    
	public void setOstatniPunkt(PunktMapy punkt) {
		if (punkt != null) {
			ArrayList<PunktMapy> p = getPunktyPosrednie();
			p.set(p.size()-1, punkt);
			
			updateData();
		}
	}

    public void addOstatniPunkt(int x, int y) {
        addOstatniPunkt(new PunktMapy(x, y));
    }
    
    public void addOstatniPunkt(PunktMapy punkt) {
    	ArrayList<PunktMapy> p = getPunktyPosrednie();
    	p.add(punkt);
    	updateData();
    }
    
    public void removeOstatniPunkt() {
    	this.punktyPosrednie.remove(this.punktyPosrednie.size()-1);
    	updateData();
    }
    
    protected ArrayList<PunktMapy> prowadzDroge(ArrayList<PunktMapy> sciezka, int odleglosc, Kierunek kierunek ) {
        ArrayList<PunktMapy> result = new ArrayList<PunktMapy>();
        this.wspolczynniki = new Float[sciezka.size() - 1];
        
        if (sciezka.size() > 1) {
            PunktMapy p1 = sciezka.get(0);
            PunktMapy p2 = sciezka.get(1);
            
            float m1 = Metody2D.wspKiernkowy(p1, p2);
            this.wspolczynniki[0] = m1;
            
            PunktMapy wynik;
            
            if (p2.getX() < p1.getX()) {
                if (kierunek == Kierunek.PRAWO ) {
                    wynik = Metody2D.punktLewo(p1, odleglosc, m1);
                } else {
                    wynik = Metody2D.punktPrawo(p1, odleglosc, m1);
                }
            } else {
                if (kierunek == Kierunek.PRAWO ) {
                    wynik = Metody2D.punktPrawo(p1, odleglosc, m1);
                } else {
                    wynik = Metody2D.punktLewo(p1, odleglosc, m1);
                }
            }
            
            result.add(wynik);
        }
        
        for (int i = 1; i+1 < sciezka.size(); i++) {
            PunktMapy pR0 = result.get(result.size()-1);
            
            PunktMapy p1 = sciezka.get(i);
            PunktMapy p2 = sciezka.get(i+1);
            
            float m0 = this.wspolczynniki[i-1];
            float m1 = Metody2D.wspKiernkowy(p1, p2);    
            
            this.wspolczynniki[i] = m1;
            
            PunktMapy pLS = Metody2D.punktLewo(p1, odleglosc, m1) ;   
            PunktMapy pLR = Metody2D.punktPrawo(p1, odleglosc, m1) ;
            
            PunktMapy pPR;
            
            if (p2.getX() < p1.getX()) {
                if (kierunek == Kierunek.PRAWO ) {
                    pPR = Metody2D.punktPrzeciecia(m0, pR0, m1, pLS);
                } else {
                    pPR = Metody2D.punktPrzeciecia(m0, pR0, m1, pLR);
                }
            } else {
                if (kierunek == Kierunek.PRAWO ) {
                    pPR = Metody2D.punktPrzeciecia(m0, pR0, m1, pLR);
                } else {
                    pPR = Metody2D.punktPrzeciecia(m0, pR0, m1, pLS);
                }
            }

            result.add(pPR);                
        }

        if (sciezka.size() > 1) {
            PunktMapy p1 = sciezka.get(sciezka.size()-2);
            PunktMapy p2 = sciezka.get(sciezka.size()-1);
            
            float m1 = Metody2D.wspKiernkowy(p1, p2);   
            
            PunktMapy pL = Metody2D.punktLewo(p2, odleglosc, m1);
            PunktMapy pR = Metody2D.punktPrawo(p2, odleglosc, m1);
            
            if (p2.getX() < p1.getX()) {
                if (kierunek == Kierunek.PRAWO ) {
                    result.add(pL);
                } else {
                    result.add(pR);
                }
            } else {
                if (kierunek == Kierunek.PRAWO ) {
                    result.add(pR);
                } else {
                    result.add(pL);
                }
            }
            
            this.wspolczynniki[this.wspolczynniki.length - 1] = m1;
        }
        
        return result;
    }
    
    /**
     * Po kazdej zmianie w punktach srodkowych drogi nalezy wywolac ta metode. Przeliczy ona
     * wszystkie punkty posrednie na danej drodze.
     */
    public void updateData() {
        ArrayList<PunktMapy> punkty_srodkowe = getPunktyPosrednie();
        
        this.punktyLewe.clear();
        this.punktyPrawe.clear();
        this.pas.clear();

        int prawych = this.droga.getIloscPasowPrawych();
        
        for (int i = 0; i < prawych; i++) {
            int odleglosc = SZEROKOSC_DROGI*(prawych - i);
            this.punktyPrawe.add(prowadzDroge(punkty_srodkowe, odleglosc, Kierunek.PRAWO));
            PunktMapy[] punkty = Metody2D.podzielKrzywa(prowadzDroge(punkty_srodkowe, odleglosc - SZEROKOSC_DROGI / 2, Kierunek.PRAWO), GESTOSC_SIATKI);
            this.pas.add(punkty);
            this.droga.setDlugoscDrogi(i, punkty.length);
        }
        
        int j = 0;
        for (int i = prawych; i < this.droga.getIloscPasow(); i++) {
            System.out.println("DrogaView.updateData()");
            int odleglosc = SZEROKOSC_DROGI*(++j);
            this.punktyLewe.add(prowadzDroge(punkty_srodkowe, odleglosc, Kierunek.LEWO));
            PunktMapy[] punkty = Metody2D.podzielKrzywa(prowadzDroge(punkty_srodkowe, odleglosc - SZEROKOSC_DROGI / 2, Kierunek.LEWO), GESTOSC_SIATKI);
            this.pas.add(reverse(punkty));
            this.droga.setDlugoscDrogi(i, punkty.length);
        }
        
        stworzObrys();
    }
    
    /**
     * Tworzy polilinie ktora obryswouje caly obszar drogi 
     */
    private void stworzObrys() {
    	ArrayList<PunktMapy> punktyLewe = (ArrayList<PunktMapy>) this.punktyLewe.get(this.punktyLewe.size()-1); 
//    	ArrayList<PunktMapy> punktyPrawe = (ArrayList<PunktMapy>) this.punktyPrawe.get(this.punktyPrawe.size()-1);
    	ArrayList<PunktMapy> punktyPrawe = (ArrayList<PunktMapy>) this.punktyPrawe.get(0);
		
    	int[] punktyX = new int[(punktyLewe.size() + punktyPrawe.size())];
    	int[] punktyY = new int[(punktyLewe.size() + punktyPrawe.size())];
	    	
	    	int j = 0;
	    	for (int i = 0; i < punktyLewe.size(); i++) {
				punktyX[j] = punktyLewe.get(i).getX(); 
				punktyY[j++] = punktyLewe.get(i).getY(); 
			}
	    	
	    	for (int i = punktyPrawe.size()-1; i >= 0 ; i--) {
	    		punktyX[j] = punktyPrawe.get(i).getX(); 
	    		punktyY[j++] = punktyPrawe.get(i).getY(); 
	    	}
	    	
	    obrys = new Polygon(punktyX, punktyY, punktyX.length);
	}

	protected PunktMapy[] reverse(PunktMapy[] punkty) {
    	PunktMapy[] result = new PunktMapy[punkty.length];
    	
    	int j= 0;
    	for (int i = punkty.length - 1; i >= 0; i--) {
			result[j++] = punkty[i];
		}
    	
    	return result;
    }
    
    /**
     * @see pl.wroc.pwr.iis.traffic.presentation.model.Paintable#paintComponent(Graphics2D, int)
     */
	public void paintComponent(GC g, int skala) {
        // Przeliczenie punktow srodkowych wg. aktualnej skali uzytej w wyswietleniu
        ArrayList<PunktMapy> punkty_srodkowe = getPunktyPosrednie();

        rysujOdjazdy(g);
        // Rysuje czarne tło
        if (RYSUJ_TLO) {
        	obrysujDroge(g); 
        }
        
        g.setLineStyle(SWT.LINE_DOT);
        g.setLineWidth(0);
        g.setForeground(ResourceHelper.COLOR_WHITE);
        Paint2D.narysujKrzywa(g, punkty_srodkowe);

        g.setForeground(ResourceHelper.COLOR_RED);
        g.setLineStyle(SWT.LINE_SOLID);
        
        // Rysowanie obrysow pasow lewych
        for (int i = 0; i < this.punktyLewe.size(); i++) {
            ArrayList<PunktMapy> punkty = (ArrayList<PunktMapy>) this.punktyLewe.get(i);
            Paint2D.narysujKrzywa(g, punkty);
        }

        // Rysowanie obrysow pasow prawych
        g.setForeground(ResourceHelper.COLOR_GREEN);
        for (int i = 0; i < this.punktyPrawe.size(); i++) {
            ArrayList<PunktMapy> punkty = (ArrayList<PunktMapy>) this.punktyPrawe.get(i);
            Paint2D.narysujKrzywa(g, punkty);
        }

        // Rysowanie siatki pasa
        if (RYSUJ_SIATKE) {
        	g.setForeground(ResourceHelper.COLOR_BLACK);
        	for (int numerPasa = 0; numerPasa < this.pas.size(); numerPasa++) {
        		PunktMapy[] p = (PunktMapy[]) this.pas.get(numerPasa);
        		for (PunktMapy mapy : p) {
        			g.drawPoint(mapy.getX(), mapy.getY());
        		}
        	}
        }
        // Rysowanie pojazdow na 
        for (int numerPasa = 0; numerPasa < this.pas.size(); numerPasa++) {
            narysujPojazdy(g, numerPasa);
        }

//        g.fillRectangle(0,0,100,100);
    }
	
	public void paintDynamic(GC g, int skala) {
        // Rysowanie pojazdow na 
        for (int numerPasa = 0; numerPasa < this.pas.size(); numerPasa++) {
            narysujPojazdy(g, numerPasa);
        }
	}
    
    private void rysujOdjazdy(GC g) {
    	for (int i = 0; i < this.odjazdy.length; i++) {
			if (this.odjazdy[i] != null) {
				PunktMapy start = getPunktyWyjazdowe()[i];
				this.odjazdy[i].paint(g, start);
			}
		}
	}

	protected void obrysujDroge(GC gc) {
    	gc.setForeground(ResourceHelper.COLOR_DARK_GRAY);
    	gc.setBackground(ResourceHelper.COLOR_DARK_GRAY);
    	
    	int[] punkty = new int[obrys.npoints * 2];
    	int j = 0;
    	for (int i = 0; i < obrys.npoints; i++) {
    		punkty[j++] = obrys.xpoints[i];
    		punkty[j++] = obrys.ypoints[i];
		}
    	gc.fillPolygon(punkty);
    }
    
    protected void narysujPojazdy(GC g, int numerPasa) {
        for (int i = 0; i < this.droga.getDlugosc(numerPasa); i++) {
             Pojazd pojazd = this.droga.getZawartoscKomorki(numerPasa, i);
             if ( pojazd != null) {
                 PojazdView.paint(g, pojazd, this);
             }
        }
    }
	public void setIloscPasow(int iloscPasow) {
		if (iloscPasow >= 0) {
			this.odjazdy = new PolaczenieView[iloscPasow];
			this.dojazdy = new PolaczenieView[iloscPasow];
			droga.setIloscPasow(iloscPasow);
		} else {
			Logger.warn("Niepoprawne parametry podczas ustawiania ilosci pasow");
		}
	}
	
	public int getIloscPasow() {
		return this.droga.getIloscPasow();
	}

	public int getIloscPasowPrawych() {
		return this.droga.getIloscPasowPrawych();
	}
	
	public void setIloscPasowPrawych(int iloscPasowPrawych) {
		if (iloscPasowPrawych >= 0 && iloscPasowPrawych < getIloscPasow()) {
			this.droga.setKierunekPasa(iloscPasowPrawych);
		} else {
			Logger.warn("Niepoprawny parametr ilości pasow prawych");
		}
	}
	
	public int getMaxPredkosc() {
		return this.droga.getMaxPredkoscPasa(0);
	}
	public void setMaxPredkosc(int maxPredkosc) {
		this.droga.setMaxPredkoscWszystkichPasow(maxPredkosc);
	}
	
	public void setKierunekPasa(int numerPierwszegoPasaLewego) {
		this.droga.setKierunekPasa(numerPierwszegoPasaLewego);
	}

	public void wykonajRuch() {
		this.droga.wykonajRuch();
	}

	public boolean addPojazd(int numerPasa, Pojazd pojazd) {
		return this.droga.addPojazd(numerPasa, pojazd);
	}

	public void setOdjazd(int numerPasa, WezelView wezelDocelowy, int pasDocelowy) {
		if (isPoprawnyNumerPasa(numerPasa) && wezelDocelowy != null && wezelDocelowy.isPoprawnyNumerPasa(pasDocelowy)) {
			droga.setOdjazd(numerPasa, wezelDocelowy.getWezel(), pasDocelowy);
			this.odjazdy[numerPasa] = new PolaczenieView(wezelDocelowy, pasDocelowy);
			System.out.println("DrogaView.setOdjazd(): " + this.odjazdy[numerPasa]);
		}
	}
	
	public void setOdjazd(int numerPasa, PolaczenieView odjazd) {
		setOdjazd(numerPasa, odjazd.wezel, odjazd.pas);
	}
	
	public void setDojazd(int numerPasa, WezelView wezelZrodlowy, int pasZrodlowy) {
		if (wezelZrodlowy != null ) {
//			if (isPoprawnyNumerPasa(numerPasa) && wezelZrodlowy != null && wezelZrodlowy.isPoprawnyNumerPasa(pasZrodlowy)) {
			this.droga.setDojazd(numerPasa, wezelZrodlowy.getWezel(), pasZrodlowy);
			this.dojazdy[numerPasa] = new PolaczenieView(wezelZrodlowy, pasZrodlowy);
		}
	}
	
	/**
	 * Laczy dwie drogi. W bierzacej ustawia dojazd a w wezle przekazanym 
	 * przez dojazd.wezel ustawia odpowiedni odjazd z danego pasa.
	 * @param numerPasa
	 * @param dojazd
	 */
	public void setDojazd(int numerPasa, PolaczenieView dojazd) {
		setDojazd(numerPasa, dojazd.wezel, dojazd.pas);
	}
	
	public PolaczenieView getOdjazd(int numerPasa) {
		PolaczenieView result = null;
		
		if (isPoprawnyNumerPasa(numerPasa)) {
			result = this.odjazdy[numerPasa];
		}
		
		return result ;
	}
	

	public Wezel getWezel() {
		return this.droga;
	}
	
	public Droga getDroga() {
		return this.droga;
	}

	public PunktMapy getWspolrzedna(int numerPasa, int dlugosc) {
		PunktMapy result = null;
		
		if (isPoprawnyNumerPasa(numerPasa)) {
			PunktMapy[] punkty = (PunktMapy[]) this.pas.get(numerPasa);
			
			if (dlugosc < punkty.length) {
				result = punkty[dlugosc]; 
			} else {
				dlugosc -= punkty.length;
				PolaczenieView polaczenie = getOdjazd(numerPasa);
				result = polaczenie.wezel.getWspolrzednaWjazdu(polaczenie.pas, dlugosc);
			}
		}
		
		return result;
	}
	
	public PunktMapy getWspolrzednaWjazdu(int numerPasa, int dlugosc) {
		return getWspolrzedna(numerPasa, dlugosc);
	}

	public boolean isPoprawnyNumerPasa(int numerPasa) {
		return this.droga.isPoprawnyNumerPasa(numerPasa);
	}
	
	
	public void dodajPunkt(int x, int y) {
		Line2D l = new Line2D.Float();
		final int sasiedztwo2 = SASIEDZTWO_BADANIA_PRZECIECIA * 2; 
		
		for (int i = 1; i < this.punktyPosrednie.size(); i++) {
			PunktMapy start = this.punktyPosrednie.get(i-1);
			PunktMapy koniec = this.punktyPosrednie.get(i);
			
			l.setLine(start.getX(), start.getY(), koniec.getX(), koniec.getY());
			if (l.intersects(x-SASIEDZTWO_BADANIA_PRZECIECIA, y - SASIEDZTWO_BADANIA_PRZECIECIA, sasiedztwo2, sasiedztwo2)) {
				this.punktyPosrednie.add(i,new PunktMapy(x, y));
				updateData();
				return;
			}
		}
	}
	
	public void usunPunkt(int x, int y) {
		if (this.punktyPosrednie.size() > 1) {
			for (int i = 0; i < this.punktyPosrednie.size(); i++) {
				PunktMapy punkt = this.punktyPosrednie.get(i);
				
				if (Metody2D.sasiedztwoPunktu(punkt, x, y)) {
					this.punktyPosrednie.remove(i);
					updateData();
					return;
				}
			}
		}
	}
	
	public void przesunCalosc(int x, int y) {
		for (int i = 0; i < this.punktyPosrednie.size(); i++) {
			PunktMapy punkt = this.punktyPosrednie.get(i);
			punkt.setX(punkt.getX() + x);
			punkt.setY(punkt.getY() + y);
		}
		
		updateData();
	}
	
	
	public void delete() {
		for (int i = 0; i < this.odjazdy.length; i++) {
			this.odjazdy[i].wezel.delDojazd(this.odjazdy[i].pas);
		} 
	}

	public void delDojazd(int numerPasa) {
		if (isPoprawnyNumerPasa(numerPasa)) {
			this.dojazdy[numerPasa] = null;
		}
	}

	public void delOdjazd(int numerPasa) {
		if (isPoprawnyNumerPasa(numerPasa)) {
			this.odjazdy[numerPasa] = null;
		}
	}

	public void delOstatniPunkt() {
		removeOstatniPunkt();
	}
}
