/*
 * MapaView.java
 * Created on 2005-11-08
 * Copyright 2004-2005 e-informatyka.pl. All rights reserved.
 */
package pl.wroc.pwr.iis.traffic.presentation.view;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.EditModes;
import pl.wroc.pwr.iis.traffic.presentation.model.Grupa;
import pl.wroc.pwr.iis.traffic.presentation.model.Paintable;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;
import pl.wroc.pwr.iis.traffic.presentation.model.WezelView;
import pl.wroc.pwr.iis.traffic.presentation.ui.DrogaWlasciwosci;
import pl.wroc.pwr.iis.traffic.presentation.ui.FiguraWlasciwosci;
import pl.wroc.pwr.iis.traffic.presentation.ui.GeneratorWlasciwosci;
import pl.wroc.pwr.iis.traffic.presentation.ui.ObrazekWlasciwosci;
import pl.wroc.pwr.iis.traffic.presentation.ui.SkrzyzowanieWlasciwosci;
import pl.wroc.pwr.iis.traffic.presentation.view.figury.ElipsaView;
import pl.wroc.pwr.iis.traffic.presentation.view.figury.FiguraView;
import pl.wroc.pwr.iis.traffic.presentation.view.figury.LamanaView;
import pl.wroc.pwr.iis.traffic.presentation.view.figury.LiniaView;
import pl.wroc.pwr.iis.traffic.presentation.view.figury.Obrazek;
import pl.wroc.pwr.iis.traffic.presentation.view.figury.ObszarView;
import pl.wroc.pwr.iis.traffic.presentation.view.figury.ProstokatView;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.PolaczenieView;

/**
 * @author M.Stanek <mikol@e-informatyka.pl>
 */
public class MapaView implements Serializable {
	private static final int DELTA_PRZESUNIECIA = 1;
	private static final long serialVersionUID = -837336414186840416L;

	private int gestoscSiatki = 30;
	private Color kolorSiatki = ResourceHelper.COLOR_BLACK;
    private Color kolorTla = ResourceHelper.COLOR_TLO;
    private Color kolorProwadnic = ResourceHelper.COLOR_GRAY;
    
    private EditModes editMode = EditModes.NORMAL;
    
    /** Jezeli jest ustawione na true to punkty pomiędzy obiektami mogą być dzielone */
    private boolean rysujSiatke = true;
    private boolean wspolnePunkty = true;
    private boolean rysujProwadnice = false;
    private boolean wysokaJakosc = false;
    
    private final static int MOUSE_BUTTON_1 = 1;
//    private final static int MOUSE_BUTTON_2 = 2;
    private final static int MOUSE_BUTTON_3 = 3;

	private final DrogaWlasciwosci 	drogaWlasciwosci = new DrogaWlasciwosci();  
	private final FiguraWlasciwosci figuraWlasciwosci = new FiguraWlasciwosci();  
	private final ObrazekWlasciwosci obrazWlasciwosci = new ObrazekWlasciwosci();  
	private final GeneratorWlasciwosci generatorWlasciwosci = new GeneratorWlasciwosci();  
	private final SkrzyzowanieWlasciwosci skrzyzowanieWlasciwosci = new SkrzyzowanieWlasciwosci();  
	
    /**
     * Aktualne przemieszczenie lewego gornego rogu mapy
     */
    private int szerokosc = 1000, wysokosc = 1000;

    
    private Grupa obiektyMapy = new Grupa();
    
    protected GC dbGC = null;
    protected Image dbImage;
	protected Image dbDynamic;
	protected GC dynGC = null;
    
    public MapaView(int szerokosc, int wysokosc) {
    	this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        
        this.dbImage   = new Image(ResourceHelper.getDisplay(), szerokosc, wysokosc);
        this.dbDynamic = new Image(ResourceHelper.getDisplay(), szerokosc, wysokosc);
        this.dbGC = new GC(this.dbImage);
        this.dynGC = new GC(this.dbDynamic);
//        dynGC.setAntialias(SWT.OFF);
    }
    
    public void disposeResources() {
    	this.dbImage.dispose();
    	this.dbDynamic.dispose();
    	
    	this.dbGC.dispose();
    	
		this.drogaWlasciwosci.dispose();
    }
    
    public void setEditMode(EditModes trybEdycji) {
    	this.zaznaczonaGrupa.clear();
    	this.editMode = trybEdycji;
    }
    
    /**
     * @see pl.wroc.pwr.iis.traffic.presentation.model.Paintable#paintComponent(org.eclipse.swt.graphics.GC, int)
     */
    public void paintComponent(GC g, int skala) {
    	long time = System.currentTimeMillis();
    	ustawJakoscGrafiki();
    	przygotujMalowanie(skala);
        
        // Wykrywanie czy obiekt jest widoczny w danym oknie
        for (Paintable obiekt : this.obiektyMapy.getObiekty()) {
            obiekt.paintComponent(dbGC, skala);
        }
        
        if (this.editMode == EditModes.NORMAL) {
        	rysujPunktyEdycjiZaznaczonego();
        } else if (this.editMode == EditModes.CONNECT_WEZLY) {
        	rysujPunktyPolaczen();
        	
 		   System.out.println("MapaView.paintComponent(): " + zaznaczonyPunkt);
		   if (startPolaczenie != null && zaznaczonyPunkt != null) {
			   PunktMapy start = startPolaczenie.wezel.getPunktyWyjazdowe()[startPolaczenie.pas];
			   dbGC.setForeground(ResourceHelper.COLOR_BLUE);
			   dbGC.drawLine(start.getX(), start.getY(), zaznaczonyPunkt.getX(), zaznaczonyPunkt.getY());
		   }

        	
        } else if (this.editMode == EditModes.POINT_ADD || 
        		   this.editMode == EditModes.POINT_REMOVE) {
		   rysujPunktyEdycjiWszystkich();
        }
        
        if (zaznaczonyPunkt != null) {
        	int promienZaznaczenia = 4;
        	dbGC.setForeground(ResourceHelper.COLOR_RED);
        	dbGC.drawOval(zaznaczonyPunkt.x - promienZaznaczenia, zaznaczonyPunkt.y - promienZaznaczenia, 2 * promienZaznaczenia, 2 * promienZaznaczenia);
        }
        	
        dbGC.setBackground(ResourceHelper.COLOR_MEDIUM_LIGHT_GRAY);
        dbGC.setAntialias(SWT.OFF);
        
        rysujProwadnice(dbGC);
        rysujZaznaczenie(dbGC);
        
        g.drawImage(dbImage, 0, 0);
    	System.out.println("MapaView.paintComponent(): " + (System.currentTimeMillis() - time));
//    	przygotujMalowanie(skala);
    }

	private void ustawJakoscGrafiki() {
		if (!wysokaJakosc) {
    		dbGC.setAntialias(SWT.OFF);
    		dbGC.setInterpolation(SWT.NONE);
    		dbGC.setTextAntialias(SWT.OFF);
    	} else {
    		dbGC.setAntialias(SWT.ON);
    		dbGC.setInterpolation(SWT.HIGH);
    		dbGC.setTextAntialias(SWT.ON);
    	}
	}
    
    public int paintDynamic(GC g, int skala) {
    	long time = System.currentTimeMillis();

    	dynGC.drawImage(dbImage,0 ,0);
    	
//    	 Wykrywanie czy obiekt jest widoczny w danym oknie
        for (Paintable obiekt : this.obiektyMapy.getObiekty()) {
            obiekt.paintDynamic(dynGC, skala);
        }
        
        g.drawImage(dbDynamic, 0, 0);
    	System.out.println("MapaView.paintComponent(): " + (System.currentTimeMillis() - time));
    	return (int) (System.currentTimeMillis() - time);
	}

    private void rysujZaznaczenie(GC g) {
    	if (select) {
    		Rectangle rec = Metody2D.getRectangle(selectStart, selectEnd);
    		
    		g.setLineStyle(SWT.LINE_DOT);
			g.setLineWidth(0);
			g.setForeground(kolorProwadnic);
			
			g.drawRectangle(rec.x, rec.y, rec.width, rec.height);
    	}
	}

	private void rysujProwadnice(GC g) {
		if (isRysujProwadnice()) {
			g.setLineStyle(SWT.LINE_DOT);
			g.setLineWidth(0);
			g.setForeground(kolorProwadnic);
			g.drawLine(lastX, 0, lastX, wysokosc);
			g.drawLine(0, lastY, szerokosc, lastY);
		}
		
	}

	private void rysujPunktyEdycjiZaznaczonego() {
    	for (Paintable obiekt : this.zaznaczonaGrupa.getObiekty()) {
    		if (obiekt != null) {
        		PunktMapy[] p = obiekt.getPunktyEdycji();
        		
        		dbGC.setForeground(ResourceHelper.COLOR_BLUE);
        		dbGC.setBackground(ResourceHelper.COLOR_BLUE);
        		for (PunktMapy punkt : p) {
    				rysujPunktEdycji(dbGC, punkt);
        		}
    		}
		}
    }
    
    
    private void rysujPunktyEdycjiWszystkich() {
		for (Paintable obiekt : this.obiektyMapy.getObiekty()) {
			if (obiekt instanceof WezelView) {
				WezelView wezel = (WezelView) obiekt;

				PunktMapy[] p = wezel.getPunktyEdycji();

				dbGC.setForeground(ResourceHelper.COLOR_BLUE);
				dbGC.setBackground(ResourceHelper.COLOR_BLUE);
				for (PunktMapy punkt : p) {
					rysujPunktEdycji(dbGC, punkt);
				}
			}
		}
	}
    
    private void rysujPunktEdycji(GC g, PunktMapy punkt) {
    	if (punkt != null) {
	    	g.fillOval(punkt.getX() - WezelView.SASIEDZTWO_BADANIA_PRZECIECIA, 
					   punkt.getY() - WezelView.SASIEDZTWO_BADANIA_PRZECIECIA, 
					   WezelView.SASIEDZTWO_BADANIA_PRZECIECIA * 2, 
					   WezelView.SASIEDZTWO_BADANIA_PRZECIECIA * 2);
    	}
    }
 
	private void przygotujMalowanie(int skala) {
		    	dbGC.setForeground(kolorSiatki);
		    	dbGC.setBackground(kolorTla);
		    	dbGC.fillRectangle(0,0, szerokosc, wysokosc);
		
		    	dbGC.setLineWidth(1);
		    	dbGC.setLineStyle(SWT.LINE_SOLID);
		    	
		    	
		        int r = gestoscSiatki * skala;
		        
		        if (rysujSiatke) {
		      	dbGC.setForeground(kolorSiatki);
		        	for (int j = 0; j < this.wysokosc; j += r) {
		        		for (int i = 0; i < this.szerokosc; i+=r){
		        			dbGC.drawPoint(i, j);
		        			
		        		}
		        	}
		        }
	}
    
    
    private void rysujPunktyPolaczen() {
		for (Paintable obiekt : this.obiektyMapy.getObiekty()) {
			if (obiekt instanceof WezelView) {
				WezelView wezel = (WezelView) obiekt;
				PunktMapy[] punkty;
				
				if (startPolaczenie != null) {
					punkty = wezel.getPunktyWjazdowe();
					dbGC.setForeground(ResourceHelper.COLOR_RED);
					dbGC.setBackground(ResourceHelper.COLOR_RED);
					for (PunktMapy punkt : punkty) {
						if (punkt != null) {
							rysujPunktEdycji(dbGC, punkt);
						}
					}
				} else {
					punkty = wezel.getPunktyWyjazdowe();
					
					dbGC.setForeground(ResourceHelper.COLOR_GREEN);
					dbGC.setBackground(ResourceHelper.COLOR_GREEN);
					for (PunktMapy punkt : punkty) {
						if (punkt != null) {
							rysujPunktEdycji(dbGC, punkt);
						}
					}
				}
			}
		}
	}
    
    private PunktMapy pobierzPunkt(int x, int y) {
    	PunktMapy punkt = null;
    	if (this.wspolnePunkty) {
    		punkt = sprawdzZaznaczenieObiektu(wybierzWezel(x, y), x, y);
    	}
    	
    	if (punkt == null) {
    		punkt = new PunktMapy(x, y);
    	}
    	
    	return punkt;
    }
    
//    Paintable w;
    PunktMapy zaznaczonyPunkt;
    Grupa zaznaczonaGrupa = new Grupa();
    
    public void doAction(int x, int y, int button, boolean doubleClick, int stateMask) {
    	PunktMapy aktualnyPunkt = pobierzPunkt(x, y);
    	switch (this.editMode) {
    	case NORMAL:	
    						if (!doubleClick){
    							Paintable obiekt = wybierzWezel(x, y);
    							
    							if (obiekt != null) {
	    							if (stateMask == SWT.SHIFT) {
	    								zaznaczonaGrupa.addSingle(obiekt);
	    							} else if (!zaznaczonaGrupa.contain(obiekt)){
	    								zaznaczonaGrupa.clearAdd(obiekt);
	    							}
    							} else {
    								zaznaczonaGrupa.clear();
    							}
    							
    							if (zaznaczonaGrupa.isNotEmpty()) {
    								drag = true;
    								
    								if (zaznaczonyPunkt == null){
    									zaznaczonyPunkt = sprawdzZaznaczeniePunktu(zaznaczonaGrupa, x, y);
    									if (zaznaczonyPunkt != null) {
    										this.editMode = EditModes.POINT_MOVE;
    									}
    								} else {
//    									grupa.updateObiekty();
    									obiektyMapy.updateData();
    									zaznaczonyPunkt = null;
    								}
    							} else { // Nie zaznaczono zadnego obiektu 
    								// Przejscie w tryb zaznaczenia obszaru
    								this.select = true;
    								this.selectStart = new PunktMapy(x, y);
    							}
    						} else { // podwojne klikniecie na obiekt
    							drag = false;
    							Paintable obiekt = wybierzWezel(x, y);
    							if (obiekt != null) {
    								if (obiekt instanceof DrogaView) {
    									drogaWlasciwosci.open((DrogaView) obiekt, x, y);
    								} else if (obiekt instanceof SkrzyzowanieView) {
    									skrzyzowanieWlasciwosci.open((SkrzyzowanieView) obiekt);
    								} else if (obiekt instanceof GeneratorView) {
    									generatorWlasciwosci.open((GeneratorView) obiekt, x, y);
    								} else if (obiekt instanceof Obrazek) {
    									obrazWlasciwosci.open((Obrazek) obiekt, x, y);
    								} else if (obiekt instanceof FiguraView) {
    									figuraWlasciwosci.open((FiguraView) obiekt, x, y);
    								} else if (obiekt instanceof SkrzyzowanieView) {
    								}    									
    								obiekt.updateData();
    							}
    						}
    						break;
		case ROAD_INSERT: 	
							obiektyMapy.addObiekt(new DrogaView(aktualnyPunkt));
							break;
		case INTERSECTION_INSERT: 	
							obiektyMapy.addObiekt(new SkrzyzowanieView(aktualnyPunkt));
							break;
		case INSERT_GENERATOR :
							obiektyMapy.addObiekt(new GeneratorView(aktualnyPunkt));
							break;
		case RECTANGLE_INSERT:
							obiektyMapy.addObiekt(new ProstokatView(aktualnyPunkt));
							break;
		case ELIPSA_INSERT:
							obiektyMapy.addObiekt(new ElipsaView(aktualnyPunkt));
							break;
		case OBSZAR_INSERT:
							obiektyMapy.addObiekt(new ObszarView(aktualnyPunkt));
							break;
		case LINE_INSERT:
							obiektyMapy.addObiekt(new LiniaView(aktualnyPunkt));
							break;
		case POLYLINE_INSERT:
							obiektyMapy.addObiekt(new LamanaView(aktualnyPunkt));
							break;							
		case OBRAZEK_INSERT:
							obiektyMapy.addObiekt(new Obrazek(aktualnyPunkt));
							break;							
			
		case ROAD_POINTS: 	
		case OBSZAR_POINTS: 	
		case POLYLINE_POINTS:
		case INTERSECTION_POINTS:	
							if (doubleClick) {
								obiektyMapy.getLast().delOstatniPunkt();
								obiektyMapy.getLast().delOstatniPunkt();
								zaznaczonaGrupa.clearAdd(obiektyMapy.getLast());
								this.editMode = EditModes.NORMAL;
							} else {
								if (button == MOUSE_BUTTON_1) {
									obiektyMapy.getLast().setOstatniPunkt(pobierzPunkt(x, y));
									obiektyMapy.getLast().addOstatniPunkt(x + 1, y+1);	
								} else if (button == MOUSE_BUTTON_3) {
									obiektyMapy.getLast().delOstatniPunkt();
								}
							}
							break;
							
		case LINE_POINTS:
		case ELIPSA_POINTS:
		case RECTANGLE_POINTS:
		case GENERATOR_POINTS:
							if (button == MOUSE_BUTTON_1) {
								obiektyMapy.getLast().setOstatniPunkt(pobierzPunkt(x, y));	
								zaznaczonaGrupa.clearAdd(obiektyMapy.getLast());
							}
							break;
							
		case POINT_MOVE: 
							zaznaczonaGrupa.updateData();
							zaznaczonyPunkt = null;
							this.editMode = EditModes.NORMAL;
							break;
					
		case POINT_ADD:	
							if (wybierzWezel(x, y) != null) {
								wybierzWezel(x, y).dodajPunkt(x,y);
							}
							break;
							
		case POINT_REMOVE:	
							if (wybierzWezel(x, y) != null){
								wybierzWezel(x, y).usunPunkt(x, y);
							}
							break;							
							
		case CONNECT_WEZLY:
							if (startPolaczenie == null) {
								startPolaczenie = znajdzPunktPolaczenia(x, y, true);
								if (startPolaczenie != null) {
									zaznaczonyPunkt = new PunktMapy(x, y);
								}
							} else {
								if (znajdzPunktPolaczenia(x,y, false) != null) {
									PolaczenieView endPolaczenie = znajdzPunktPolaczenia(x,y, false);
									startPolaczenie.wezel.setOdjazd(startPolaczenie.pas, endPolaczenie);
									endPolaczenie.wezel.setDojazd(endPolaczenie.pas, startPolaczenie);
									
									// Czyszczenie zmiennych i trybu
									startPolaczenie = null;
									zaznaczonyPunkt = null;
									this.editMode = EditModes.NORMAL;
								}
							}
							break;
		case INTERSECTION_PAS:
							Paintable obiekt = wybierzWezel(x, y);
							if (obiekt instanceof SkrzyzowanieView) {
								SkrzyzowanieView skrzyzowanie = (SkrzyzowanieView) obiekt;
								if (zaznaczonyPunkt == null) {
									zaznaczonyPunkt = aktualnyPunkt;
								} else {
									if (!aktualnyPunkt.equals(zaznaczonyPunkt)) {
										skrzyzowanie.prowadzDroge(zaznaczonyPunkt, aktualnyPunkt);
									} 
									zaznaczonyPunkt = null;
								}
							}
							break;
		case INTERSECTION_TRASA:
							Paintable o = wybierzWezel(x, y);
							if (o instanceof SkrzyzowanieView) {
								SkrzyzowanieView skrzyzowanie = (SkrzyzowanieView) o;
								skrzyzowanie.prowadzTrase(aktualnyPunkt);
							}
							break;							
							
		default: 
			break;
		}
    	
		this.editMode = editMode.getNextMode();
    }

	private PunktMapy sprawdzZaznaczeniePunktu(Grupa grupa, int x, int y) {
		PunktMapy p = null;
		ArrayList<Paintable> obiekty = grupa.getObiekty();
		for (int i = obiekty.size() - 1; i >= 0 ; i--) {
			Paintable obiekt = obiekty.get(i);
			p = sprawdzZaznaczenieObiektu(obiekt, x, y);
			if (p != null) {
				return p;
			}	
		}
		return null;
	}
	
	private PunktMapy sprawdzZaznaczenieObiektu(Paintable obiekt, int x, int y) {
		if (obiekt != null) {
			PunktMapy[] pkt = obiekt.getPunktyEdycji();
			
			for (PunktMapy punkt : pkt) {
				if (punkt != null && Metody2D.sasiedztwoPunktu(punkt, x, y)) {
					return punkt;
				}
			}
		}

		return null;
	}
	
    private Paintable wybierzWezel(int x, int y) {
    	// WYszukanie odpowiedniego wezla
    	ArrayList<Paintable> obiekty = this.obiektyMapy.getObiekty();
		for (int i = obiekty.size() - 1; i >= 0 ; i--) {
			Paintable obiekt = obiekty.get(i);
			if (obiekt.isZaznaczono(x, y)) {
				return obiekt;
			}
		}
		
		return null;
	}
    

    boolean drag = true;
    int lastX, lastY;
    private PolaczenieView startPolaczenie;
	/** Używane jako stan w czasie procesu zaznaczania przeciagnieciem */
	private boolean select;
	private PunktMapy selectStart;
	private PunktMapy selectEnd = new PunktMapy(0,0);
    
    
	public boolean mouseMove(int x, int y, int button) {
    	boolean result = false;
    	switch (this.editMode) {
    	case NORMAL:		
					if (this.zaznaczonaGrupa.isNotEmpty() && this.drag) {
						zaznaczonaGrupa.przesunCalosc(x - lastX, y - lastY);
						result = true;
					}
					break;

    	case POLYLINE_POINTS:
    	case OBSZAR_POINTS:
    	case INTERSECTION_POINTS: 
    	case ROAD_POINTS:
    	case ELIPSA_POINTS:
    	case LINE_POINTS:	
    	case RECTANGLE_POINTS:	
    	case GENERATOR_POINTS:
					obiektyMapy.getLast().setOstatniPunkt(x, y);
					result = true;
					break;
		case POINT_MOVE:		
					if (zaznaczonyPunkt != null) {
						zaznaczonyPunkt.setX(x);
						zaznaczonyPunkt.setY(y);
						result = true;
					}
					break;
		case CONNECT_WEZLY:		
					if (zaznaczonyPunkt != null) {
						zaznaczonyPunkt.setX(x);
						zaznaczonyPunkt.setY(y);
						result = true;
					}
					break;
    	}
    	
    	if (isRysujProwadnice() || this.select) {
    		result = true;
    	}
    	
    	lastX = x;
    	lastY = y;
    	selectEnd.x = x;
    	selectEnd.y = y;
    	return result;
    }
	
	/**
	 * @param x
	 * @param y
	 * @param wjazd True szykanie wsrod wjazdow, False - szukanie wsrod wyjazdow
	 * @return
	 */
	private PolaczenieView znajdzPunktPolaczenia(int x, int y, boolean wyjazd) {
		for (Paintable obiekt : this.obiektyMapy.getObiekty()) {
			if (obiekt instanceof WezelView) {
				WezelView wezel = (WezelView) obiekt;
				PunktMapy[] punkty;
				if (wyjazd) {
					punkty = wezel.getPunktyWyjazdowe();
				} else {
					punkty = wezel.getPunktyWjazdowe();
				}
				
				for (int pas = 0; pas < punkty.length; pas++) {
					if (Metody2D.sasiedztwoPunktu(punkty[pas], x, y)) {
						return new PolaczenieView(wezel, pas);
					}
				}
			}
		}
		
		return null;
	}

	public boolean endAction() {
		boolean przerysowac = false;
		if (this.drag == true) {
			this.drag = false;
			this.obiektyMapy.updateData();
		} else if (this.select == true) {
			this.select = false;
			ArrayList<Paintable> zaznaczone = Metody2D.getZaznaczono(selectStart, new PunktMapy(lastX, lastY), this.obiektyMapy.getObiekty());
			this.zaznaczonaGrupa.clear();
			this.zaznaczonaGrupa.addObiekty(zaznaczone);
			
			przerysowac = true;
		}
		
//		if (this.editMode == EditModes.POINT_MOVE) {
//			this.editMode = EditModes.NORMAL;
//		}
		
		return przerysowac;
	}

	public boolean keyPressed(char character, int keyCode, int stateMask) {
		boolean result = true;
    	switch (this.editMode) {
    	case NORMAL:		
				    		if (keyCode == SWT.DEL) {
								usunObiekty();
				    		} else if (keyCode == SWT.ARROW_LEFT) {
				    			zaznaczonaGrupa.przesunCalosc(-DELTA_PRZESUNIECIA, 0);
				    		} else if (keyCode == SWT.ARROW_RIGHT) {
				    			zaznaczonaGrupa.przesunCalosc(DELTA_PRZESUNIECIA, 0);
				    		} else if (keyCode == SWT.ARROW_UP) {
				    			zaznaczonaGrupa.przesunCalosc(0, -DELTA_PRZESUNIECIA);
				    		} else if (keyCode == SWT.ARROW_DOWN) {
				    			zaznaczonaGrupa.przesunCalosc(0, DELTA_PRZESUNIECIA);
				    		}else if (keyCode == SWT.INSERT){
								this.editMode = EditModes.ROAD_INSERT;
    						}
    						break;
		default:
			result = false;
			break;
    	}
    	
    	return result;
	}

	public void usunObiekty() {
		obiektyMapy.delObiekt(zaznaczonaGrupa.getObiekty());
		zaznaczonaGrupa.clear();
	}
	
	public void setWspolnePunkty(boolean wspolnePunkty) {
		this.wspolnePunkty = wspolnePunkty;
	}
	
	public void moveUp() {
		obiektyMapy.moveUp(zaznaczonaGrupa.getObiekty());
	}
	public void moveDown() {
		obiektyMapy.moveDown(zaznaczonaGrupa.getObiekty());
	}
	public void moveLast() {
		obiektyMapy.moveLast(zaznaczonaGrupa.getObiekty());
	}
	public void moveFirst() {
		obiektyMapy.moveFirst(zaznaczonaGrupa.getObiekty());
	}

	public void setRysujSiatke(boolean rysujSiatke) {
		this.rysujSiatke = rysujSiatke;
	}
	
	public void wykonajRuch() {
		ArrayList<Paintable> obiekty = this.obiektyMapy.getObiekty();
		for (int i = 0; i < obiekty.size(); i++) {
			Paintable obiekt = obiekty.get(i);
			if (obiekt instanceof WezelView) {
				WezelView wezel = (WezelView) obiekt;
				wezel.wykonajRuch();
			}
		}
	}
	
	public Grupa getObiekty() {
		return this.obiektyMapy;
	}
	
	public void setObiekty(Grupa obiekty) {
		this.obiektyMapy = obiekty; 
		this.zaznaczonaGrupa.clear(); // Usuwanie obiektów zaznaczonych na poprzedniej mapie
	}
	
	private Grupa kopiuj;
	public void kopiuj() {
		try {
			kopiuj = (Grupa) zaznaczonaGrupa.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public void wklej() {
		this.obiektyMapy.addObiekty(kopiuj);
		this.zaznaczonaGrupa = kopiuj;
		try {
			this.kopiuj = (Grupa) kopiuj.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public void zaznaczWszystkie() {
		this.zaznaczonaGrupa.clear();
		this.zaznaczonaGrupa.addObiekty(this.obiektyMapy);
	}
	
	public void grupujObiekty() {
		this.obiektyMapy.groupObjects(this.zaznaczonaGrupa);
	}
	
	public void rozgrupujObiekty() {
		this.obiektyMapy.ungroupObjects(this.zaznaczonaGrupa.getObiekty());
	}

	public Color getKolorSiatki() {
		return this.kolorSiatki;
	}

	public void setKolorSiatki(Color kolorSiatki) {
		this.kolorSiatki = kolorSiatki;
	}

	public Color getKolorTla() {
		return this.kolorTla;
	}

	public void setKolorTla(Color kolorTla) {
		this.kolorTla = kolorTla;
	}

	public int getGestoscSiatki() {
		return gestoscSiatki;
	}

	public void setGestoscSiatki(int gestoscSiatki) {
		this.gestoscSiatki = gestoscSiatki;
	}

	public boolean isRysujProwadnice() {
		return rysujProwadnice;
	}

	public void setRysujProwadnice(boolean rysujProwadnice) {
		this.rysujProwadnice = rysujProwadnice;
	}

	public Color getKolorProwadnic() {
		return kolorProwadnic;
	}

	public void setKolorProwadnic(Color kolorProwadnic) {
		this.kolorProwadnic = kolorProwadnic;
	}

	public boolean isWspolnePunkty() {
		return wspolnePunkty;
	}

	public boolean isRysujSiatke() {
		return rysujSiatke;
	}
	
	public void updateData() {
		this.obiektyMapy.updateData();
	}

	public boolean isWysokaJakosc() {
		return wysokaJakosc;
	}

	public void setWysokaJakosc(boolean wysokaJakosc) {
		this.wysokaJakosc = wysokaJakosc;
	}
}