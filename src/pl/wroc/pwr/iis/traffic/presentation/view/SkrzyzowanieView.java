package pl.wroc.pwr.iis.traffic.presentation.view;

import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.logger.Logger;
import pl.wroc.pwr.iis.traffic.domain.entity.Pas;
import pl.wroc.pwr.iis.traffic.domain.entity.Pojazd;
import pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie;
import pl.wroc.pwr.iis.traffic.domain.entity.Sygnalizacja;
import pl.wroc.pwr.iis.traffic.domain.entity.Trasa;
import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;
import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;
import pl.wroc.pwr.iis.traffic.presentation.model.WezelView;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.PojazdView;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.PolaczenieView;

/**
 * @author Michał Stanek
 */
public class SkrzyzowanieView implements WezelView {
	private static final long serialVersionUID = 5981134447337219565L;

	public static final int GESTOSC_SIATKI = 4;
	
	private class Pasy implements Serializable {
		private static final long serialVersionUID = -1103757852317158190L;
		ArrayList<Pas> pasy = new ArrayList<Pas>();
	}
	
	private class PasPolaczenie implements Serializable {
		private static final long serialVersionUID = 5317105115866545728L;
		public int start, koniec;
		
		PasPolaczenie(int start, int koniec) {
			this.start = start;
			this.koniec = koniec;
		}
	}
	
	private class TrasaPolaczenie implements Serializable {
		private static final long serialVersionUID = -5141519293464550279L;
		ArrayList<Integer> elementy = new ArrayList<Integer>(); // Numery pasow trasy

		public int[] toArray() {
			int[] result = new int[elementy.size()];
			for (int i = 0; i < elementy.size(); i++) {
				result[i] = elementy.get(i);
			}
			return result;
		}
	}
	
	/**	
	 * Zmienna zawiera numer trasy która powinna zostać podświetlona. Jezeli numer == -1 to znadna trasa nie zostanie 
	 * podswietlona 
	 */
	private int zaznaczonaTrasa = -1;
		
	/** Polaczenia z innymi wezlami */
	private ArrayList<PolaczenieView> odjazdy = new ArrayList<PolaczenieView>();
	private ArrayList<PolaczenieView> dojazdy = new ArrayList<PolaczenieView>();
	
	/** Definicje */
	private ArrayList<PunktMapy> punktyKolizyjne = new ArrayList<PunktMapy>();
	private PunktMapy[] punktyEdycji;
	
	/** Polaczenia punktow */
	private ArrayList<PasPolaczenie> pasPolaczenia = new ArrayList<PasPolaczenie>();
	/** Polaczenia pasow w trasy */
	private ArrayList<TrasaPolaczenie> trasyPolaczenia = new ArrayList<TrasaPolaczenie>();

	
	/** Tablica list pasow konczacych sie w danym punkcie */
	private ArrayList<Pasy> koniec = new ArrayList<Pasy>();
	/** Tablica list pasow zaczynajacych sie w danym punkcie */
	private ArrayList<Pasy> poczatek = new ArrayList<Pasy>();
	
	/** Zmienna zawiera zestaw punktów ekranu definiujący każdy pas skrzyżowania. */
	private ArrayList<PunktMapy[]> pasyPaint = new ArrayList<PunktMapy[]>();
	private ArrayList<PunktMapy[]> trasyPaint = new ArrayList<PunktMapy[]>();
	
	private Skrzyzowanie skrzyzowanie = new Skrzyzowanie(); // wlasciwe skrzyzowanie 
	
	
	public SkrzyzowanieView() {
		this.skrzyzowanie = new Skrzyzowanie();
	}
	/**
	 * 
	 */
	public SkrzyzowanieView(PunktMapy punkt) {
		this.skrzyzowanie = new Skrzyzowanie();
		this.addOstatniPunkt(punkt);
		this.addOstatniPunkt(punkt.getX(), punkt.getY());
	}
	
	public PunktMapy addPunktMapy(int x, int y){
		PunktMapy result = new PunktMapy(x, y);
		
		punktyKolizyjne.add(result);
		koniec.add(new Pasy());
		poczatek.add(new Pasy());
		
		return result;
	}
	
	public void addPas(int numerPunktu1, int numerPunktu2) {
		// Dodanie pasa do tablicy
		this.pasPolaczenia.add(new PasPolaczenie(numerPunktu1, numerPunktu2));

		PunktMapy p1 = this.punktyKolizyjne.get(numerPunktu1);
		PunktMapy p2 = this.punktyKolizyjne.get(numerPunktu2);

		// Czesc wizualna 
			// Musi zostac utworzone tymczasowo
			ArrayList<PunktMapy> punktyPodzialu = new ArrayList<PunktMapy>();
			punktyPodzialu.add(p1);
			punktyPodzialu.add(p2);
			
			// Dodanie listy punktów dla danego pasa
			PunktMapy[] punkty = Metody2D.podzielKrzywaBezPunktowKrancowych(punktyPodzialu, GESTOSC_SIATKI);
			pasyPaint.add(punkty);
		
		Pas pas = this.skrzyzowanie.addPas(punkty.length);
		
		(poczatek.get(numerPunktu1)).pasy.add(pas); // Dodanie aktualnego pasa do listy pasow
		(koniec.get(numerPunktu2)).pasy.add(pas); // Dodanie aktualnego pasa do listy pasow
		
		polaczPasy(numerPunktu1, numerPunktu2, pas);
	}

	private void polaczPasy(int numerPunktu1, int numerPunktu2, Pas pas) {
		// Polaczenie aktualnego pasa z dojazdami
		ArrayList<Pas> dolaczycDojazdy = this.koniec.get(numerPunktu1).pasy;
		for (Pas dolaczyc : dolaczycDojazdy) {
			dolaczyc.addOdjazd(pas);
			pas.addDojazd(dolaczyc);
			
		}
		
		// Polaczenie starych drog z nowa jako dojazdem
		dolaczycDojazdy = this.poczatek.get(numerPunktu2).pasy;
		for (Pas dolaczyc : dolaczycDojazdy) {
			dolaczyc.addDojazd(pas);
			pas.addOdjazd(dolaczyc);
		}
	}
	
	private int getIloscWjazdow() {
		return getNumeryPunktowWjazdowych().size();
	}
	
	private ArrayList<Integer> getNumeryPunktowWjazdowych() {
		ArrayList<Integer> punkty = new ArrayList<Integer>();
		for (int i = 0; i < this.trasyPolaczenia.size(); i++) {
			int pas = trasyPolaczenia.get(i).elementy.get(0);
			int punkt = this.pasPolaczenia.get(pas).start;
			
			if (!punkty.contains(punkt)) {
				punkty.add(punkt);
			}
		}
		return punkty;

	}
	
	private ArrayList<Integer> getNumeryPunktowWyjazdowych() {
		ArrayList<Integer> punkty = new ArrayList<Integer>();
		for (int i = 0; i < this.trasyPolaczenia.size(); i++) {
			int pas = trasyPolaczenia.get(i).elementy.get(trasyPolaczenia.get(i).elementy.size() - 1);
			int punkt = this.pasPolaczenia.get(pas).koniec;
			
			if (!punkty.contains(punkt)) {
				punkty.add(punkt);
			}
		}
		return punkty;
	}
	
	private int getIloscWyjazdow() {
		return getNumeryPunktowWyjazdowych().size();
	}
	
	public Trasa addTrasa(int[] numeryPasow) {
		ArrayList<Integer> nPasow = new ArrayList<Integer>();
		for (int i : numeryPasow) {
			nPasow.add(i);
		}
		return addTrasa(nPasow);
	}
	
	/**
	 * @param numeryPasow
	 */
	public Trasa addTrasa(ArrayList<Integer> numeryPasow) {
		Trasa trasa = skrzyzowanie.addTrasa();
		int iloscWjazdow = getIloscWjazdow();
		int iloscWyjazdow = getIloscWyjazdow();
		
		TrasaPolaczenie polTrasa = new TrasaPolaczenie();
		polTrasa.elementy = numeryPasow; 
		this.trasyPolaczenia.add(polTrasa);
		
//		this.skrzyzowanie.addIloscPasow(); // Dodaje kolejny pas ruchu wewnątrz skrzyzowania
		
		for (int i : numeryPasow) {
			Pas p = skrzyzowanie.getPas(i);
			trasa.addOdcinek(p);
		}

		//Automatyczne dodanie tras dla pasow ruchu - jeden pas ruchu moze miec wiele 
		// tras ostatecznego przejazdu 
		ArrayList<Integer> wjazdy = getNumeryPunktowWjazdowych();
		int pasRuchu = 0;
		for (int i = 0; i < wjazdy.size(); i++) {
			int punkt = this.pasPolaczenia.get(numeryPasow.get(0)).start;
			if (wjazdy.get(i).equals(punkt)) {
				pasRuchu = i;
				break;
			}
		}
		
		if (getIloscWjazdow() > iloscWjazdow) {
			System.out.println("SkrzyzowanieView.addTrasa(): DODAJE WJAZD");
			addIloscPasow();
		}
		
		if (getIloscWyjazdow() > iloscWyjazdow) {
			System.out.println("SkrzyzowanieView.addTrasa(): DODAJE WYJAZD");
			addOdjazd();
		}

		addTrasyDlaPasaRuchu(pasRuchu, trasyPaint.size());
		
		System.out.println("SkrzyzowanieView.addTrasa() DODAJE TRASE : " + trasyPaint.size() + " do pasa ruchu: " + pasRuchu);
		this.trasyPaint.add(getTrasaPunkty(numeryPasow));

		return trasa;
	}
	
	private void addIloscPasow() {
		dojazdy.add((new PolaczenieView(null, 0)));
		this.skrzyzowanie.addIloscPasow();
	}
	
	private void addOdjazd() {
		odjazdy.add(new PolaczenieView(null, 0));
		this.skrzyzowanie.addOdjazd();
	}

	
	public PunktMapy[] getTrasaPunkty(ArrayList<Integer> numeryPasow) {
		ArrayList<PunktMapy> punktyTrasy = new ArrayList<PunktMapy>();
		
		for (int numerPasa : numeryPasow) {
			PunktMapy[] punkty = pasyPaint.get(numerPasa);
			
			for (int i = 0; i < punkty.length; i++) {
				punktyTrasy.add(punkty[i]);
			}
		}
		
		PunktMapy[] result = new PunktMapy[punktyTrasy.size()]; 
		punktyTrasy.toArray(result);
		
		return result;
	}
	
	/**
	 * @see pl.wroc.pwr.iis.traffic.presentation.model.Paintable#paintComponent(java.awt.Graphics2D, int)
	 */
	public void paintComponent(GC g, int skala) {
		rysujOdjazdy(g);
		
			// rysowanie siatki
			g.setForeground(ResourceHelper.COLOR_DARK_GRAY);
			g.setLineStyle(SWT.LINE_SOLID);
			g.setLineWidth(2);
			for (int i = 0; i < this.pasPolaczenia.size(); i++) {
				PasPolaczenie pas = this.pasPolaczenia.get(i);
				PunktMapy start = this.punktyKolizyjne.get(pas.start);
				PunktMapy koniec = this.punktyKolizyjne.get(pas.koniec);
				
				g.drawLine(start.x,  start.y,   koniec.x, koniec.y);
				g.drawLine(start.x-2,start.y-2, koniec.x, koniec.y);
				g.drawLine(start.x-2,start.y+2, koniec.x, koniec.y);
				g.drawLine(start.x+2,start.y-2, koniec.x, koniec.y);
				g.drawLine(start.x+2,start.y+2, koniec.x, koniec.y);
			}
			
		if (RYSUJ_SIATKE) {
			
			for (int i = 0; i < pasyPaint.size(); i++) {
				PunktMapy[] punkty = pasyPaint.get(i);
				for (PunktMapy punkt : punkty) {
					g.drawPoint(punkt.getX(), punkt.getY());
				}
			}
		}
			
			for (int i = 0; i < pasyPaint.size(); i++) {
				PunktMapy[] punkty = pasyPaint.get(i);
				
				if (this.trasa.elementy.contains(i)) {
					g.setForeground(ResourceHelper.COLOR_GREEN);
				} else if (aktualnieWyswietlanaTrasa != null && aktualnieWyswietlanaTrasa.elementy.contains(i)) {
					g.setForeground(ResourceHelper.COLOR_BLUE);
				} else {
					g.setForeground(ResourceHelper.COLOR_LIGHT_GRAY);
				}
				
				g.setLineStyle(SWT.LINE_SOLID);
				g.setLineWidth(0);
				g.drawLine(punkty[0].x, punkty[0].y, punkty[punkty.length -1].x, punkty[punkty.length -1].y);
				
//				for (PunktMapy punkt : punkty) {
//					g.drawPoint(punkt.getX(), punkt.getY());
//				}
			}

			// Rysowanie punktów kolizyjnych
			g.setForeground(ResourceHelper.COLOR_LIGHT_RED);
			g.setBackground(ResourceHelper.COLOR_LIGHT_RED);
			for (int i = 0; i < this.punktyKolizyjne.size(); i++) {
				PunktMapy punkt = this.punktyKolizyjne.get(i);
				g.fillOval(punkt.getX() - 2, punkt.getY() - 2, 4, 4);
			}
		
		
		// Podswietla aktualnie zaznaczona trase
		podswietlTrase(g);
		
		rysujSwiatla(g);
		
//		rysujPojazdy(g);
	}
	
	
	
	private void rysujSwiatla(GC g) {
		if (getSkrzyzowanie().isSygnalizacja()) {
			g.setBackground(ResourceHelper.COLOR_RED);
			for (int i = 0; i < this.trasyPaint.size(); i++) {
				PunktMapy pierwszyPunkt = this.trasyPaint.get(i)[0];
				g.fillOval(pierwszyPunkt.x - 2, pierwszyPunkt.y - 2, 4, 4);
			}
			
			g.setBackground(ResourceHelper.COLOR_GREEN);
			Sygnalizacja aktualnaSygnalizacja = getSkrzyzowanie().getAktualnaGrupaSygnalizacyjna();
			for (int i = 0; i < aktualnaSygnalizacja.getTrasyCount(); i++) {
				int numerTrasy = aktualnaSygnalizacja.getTrasa(i);
				
				PunktMapy pierwszyPunkt = this.trasyPaint.get(numerTrasy)[0];
				g.fillOval(pierwszyPunkt.x - 2, pierwszyPunkt.y - 2, 4, 4);
			}
		}
	}
	public void paintDynamic(GC g, int skala) {
		rysujPojazdy(g);
	}

	private void rysujPojazdy(GC g) {
		// Rysuje pojazdy znajdujace sie na pasie ruchu
		for (int i = 0; i < skrzyzowanie.getIloscPasowWewnetrznych(); i++) {
			Pas p = skrzyzowanie.getPas(i);
			
			for (int j = 0; j < p.getDlugosc(); j++) {
				Pojazd pojazd = p.getZawartosc(j);
				if (pojazd != null) {
					PojazdView.paint(g, pojazd, this);
				}
			}
		}
	}

	/**
	 * Obrysowuje trase ktora jest aktualnie zaznaczona
	 * @param g
	 */
	private void podswietlTrase(GC g) {
		// Zaznaczenie na czerwono aktualnie wybranej trasy
		if (zaznaczonaTrasa >= 0 && zaznaczonaTrasa < this.trasyPolaczenia.size()) {
			TrasaPolaczenie trasaPolaczenie = this.trasyPolaczenia.get(zaznaczonaTrasa);
			
			if (trasaPolaczenie != null)
			for (int i = 0; i < trasaPolaczenie.elementy.size(); i++) {
				PasPolaczenie pas = this.pasPolaczenia.get(trasaPolaczenie.elementy.get(i));
				PunktMapy start = this.punktyKolizyjne.get(pas.start);
				PunktMapy koniec = this.punktyKolizyjne.get(pas.koniec);
				
				g.setForeground(ResourceHelper.COLOR_RED);
				g.setLineWidth(0);
				g.drawLine(start.x, start.y, koniec.x, koniec.y);
			}
			
		}
	}
	
	private void rysujOdjazdy(GC g) {
    	for (int i = 0; i < this.odjazdy.size(); i++) {
    		PunktMapy[] pWyjazdowe = getPunktyWyjazdowe();
			if (this.odjazdy.get(i) != null && pWyjazdowe.length > i) {
				PunktMapy start = pWyjazdowe[i];
				this.odjazdy.get(i).paint(g, start);
			}	
		}
	}

	public boolean isZaznaczono(int x, int y) {
		for (PunktMapy punkt : this.punktyEdycji) {
			if (Metody2D.sasiedztwoPunktu(punkt, x, y)) {
				return true;
			}
		}
		
		for (PunktMapy[] punkty : pasyPaint) {
			for (PunktMapy punkt : punkty) {
				if (Metody2D.sasiedztwoPunktu(punkt, x, y)) {
					return true;
				}	
			}
		}

		return false;
	}
	
	public int getIloscPasow() {
		return skrzyzowanie.getIloscPasow();
	}
	
	public void setIloscPasow(int iloscPasow) {
		skrzyzowanie.setIloscPasow(iloscPasow);
	}

	public void addTrasyDlaPasaRuchu(int numerPasa, int trasa) {
		skrzyzowanie.addTrasyDlaPasaRuchu(numerPasa, trasa);
	}

	/**
	 * @see pl.wroc.pwr.iis.traffic.presentation.model.WezelView#wykonajRuch()
	 */
	public void wykonajRuch() {
		this.skrzyzowanie.wykonajRuch();
	}

	private ArrayList<Integer> getNumeryTrasKonczacychSieWPunkcie(int koncowyPunkt) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for (int i = 0; i < this.trasyPolaczenia.size(); i++) {
			int pas = trasyPolaczenia.get(i).elementy.get(trasyPolaczenia.get(i).elementy.size() - 1);
			int punkt = this.pasPolaczenia.get(pas).koniec;
			
			if (punkt == koncowyPunkt) {
				result.add(i);
			}
		}
		
		return result;
	}
	
	/**
	 * numerPasa - numer punktu wyjazdowego
	 * @see pl.wroc.pwr.iis.traffic.presentation.model.WezelView#setOdjazd(int, pl.wroc.pwr.iis.traffic.presentation.model.WezelView, int)
	 */
	public void setOdjazd(int numerPasa, WezelView wezelDocelowy, int pasDocelowy) {
		if (isPoprawnyNumerPasa(numerPasa) && wezelDocelowy != null && wezelDocelowy.isPoprawnyNumerPasa(pasDocelowy)) {
			int koncowyPas = this.trasyPolaczenia.get(numerPasa).elementy.get(this.trasyPolaczenia.get(numerPasa).elementy.size() - 1); // pobierz ostatni element trasy
			int koncowyPunkt = this.pasPolaczenia.get(koncowyPas).koniec;
			
			ArrayList<Integer> numeryTras = getNumeryTrasKonczacychSieWPunkcie(koncowyPunkt);

			this.odjazdy.get(numerPasa).pas = pasDocelowy;
			this.odjazdy.get(numerPasa).wezel = wezelDocelowy;				
			
			for (Integer numer : numeryTras) {
				this.skrzyzowanie.setOdjazd(numer, wezelDocelowy.getWezel(), pasDocelowy);
				System.out.println("SkrzyzowanieView.setOdjazd() dla trasy: " + numer);
			}
		} else {
			Logger.warn("Niepoprawne parametry dotyczace pasow : " + isPoprawnyNumerPasa(numerPasa) + " numer pasa : " + numerPasa);
		}
	}
	
	public void setOdjazd(int numerPasa, PolaczenieView odjazd) {
		System.out.println("SkrzyzowanieView.setOdjazd() : " + odjazd.wezel);
		
		setOdjazd(numerPasa, odjazd.wezel, odjazd.pas);
	}
	
	public void setDojazd(int pas, Wezel dojazd, int pasDocelowy) {
		skrzyzowanie.setDojazd(pas, dojazd, pasDocelowy);
	}
	
	public void setDojazd(int numerPasa, PolaczenieView dojazd) {
		if (isPoprawnyNumerPasa(numerPasa) && dojazd != null) {
			this.skrzyzowanie.setDojazd(numerPasa, dojazd.wezel.getWezel(), dojazd.pas);
			this.dojazdy.set(numerPasa, dojazd);
		} else {
			Logger.warn("Niepoprawne parametry dotyczace pasow");
		}
	}

	/**
	 * @see pl.wroc.pwr.iis.traffic.presentation.model.WezelView#getWezel()
	 */
	public final Wezel getWezel() {
		return getSkrzyzowanie();
	}
	
	public final Skrzyzowanie getSkrzyzowanie() {
		return this.skrzyzowanie;
	}

	/**
	 * TODO dokonczyc
	 * @see pl.wroc.pwr.iis.traffic.presentation.model.WezelView#getWspolrzedna(int, int)
	 */
	public PunktMapy getWspolrzedna(int numerTrasy, int dlugosc) {
		PunktMapy result = null;
		PunktMapy[] punkty = trasyPaint.get(numerTrasy);
		if (dlugosc < punkty.length) {
			result = punkty[dlugosc];
		} else {
			PolaczenieView p = getOdjazd(numerWyjazduTrasy(numerTrasy));
			dlugosc -= punkty.length; // obliczenie wspolrzednej na nowym pasie
			result = p.wezel.getWspolrzedna(p.pas, dlugosc);
		}
		return result;
	}
	
	private int numerWyjazduTrasy(int trasa) {
		int ostatniPas = this.trasyPolaczenia.get(trasa).elementy.get(this.trasyPolaczenia.get(trasa).elementy.size() - 1);
		int ostPunkt = this.pasPolaczenia.get(ostatniPas).koniec;
		
		ArrayList<Integer> numery = getNumeryPunktowWyjazdowych();
		
		for (int i = 0; i < numery.size(); i++) {
			if (numery.get(i) == ostPunkt) {
				return i;
			}
		}
		
		return -1;
	}
	
	public PunktMapy getWspolrzednaWjazdu(int numerPasa, int dlugosc) {
		return getWspolrzedna(this.skrzyzowanie.getPierwszaTrasaPasaWjazdowego(numerPasa), dlugosc);
		
	}

	/**
	 * @see pl.wroc.pwr.iis.traffic.presentation.model.WezelView#isPoprawnyNumerPasa(int)
	 */
	public boolean isPoprawnyNumerPasa(int numerPasa) {
//		return (numerPasa < this.odjazdy.size()) && (numerPasa >= 0);
		return this.skrzyzowanie.isPoprawnyNumerTrasy(numerPasa);
	}

	public PolaczenieView getOdjazd(int numerPasa) {
		PolaczenieView result = null;
		if (isPoprawnyNumerPasa(numerPasa)) {
			result = this.odjazdy.get(numerPasa);
		}
		return result;
	}
	
	public PunktMapy[] getPunktyWjazdowe() {
		ArrayList<PunktMapy> punktyWjazdowe = new ArrayList<PunktMapy>();
		
		for (TrasaPolaczenie trasa : this.trasyPolaczenia) {
			int numerPasa = trasa.elementy.get(0);
			int numerPunktu = this.pasPolaczenia.get(numerPasa).start;

			PunktMapy p = this.punktyKolizyjne.get(numerPunktu);
			if (!punktyWjazdowe.contains(p)) {
				punktyWjazdowe.add(p);
			}
		}
		
		PunktMapy[] result = new PunktMapy[punktyWjazdowe.size()];
		punktyWjazdowe.toArray(result);
		return result;
	}

	public PunktMapy[] getPunktyWyjazdowe() {	
		ArrayList<PunktMapy> punktyWyjazdowe = new ArrayList<PunktMapy>();
		
		for (TrasaPolaczenie trasa : this.trasyPolaczenia) {
			int numerPasa = trasa.elementy.get(trasa.elementy.size() - 1);
			int numerPunktu = this.pasPolaczenia.get(numerPasa).koniec;

			PunktMapy p = this.punktyKolizyjne.get(numerPunktu);
			if (!punktyWyjazdowe.contains(p)) {
				punktyWyjazdowe.add(p);
			}
		}
		
		PunktMapy[] result = new PunktMapy[punktyWyjazdowe.size()];
		punktyWyjazdowe.toArray(result);
		return result;
	}

//	public void setOdjazd(int numerPasa, WezelView wezelDocelowy, int pasDocelowy) {
//		if (isPoprawnyNumerPasa(numerPasa) && wezelDocelowy != null && wezelDocelowy.isPoprawnyNumerPasa(pasDocelowy)) {
//			droga.setOdjazd(numerPasa, wezelDocelowy.getWezel(), pasDocelowy);
//			this.odjazdy[numerPasa] = new PolaczenieView(wezelDocelowy, pasDocelowy);
//		}
//	}
//	public void setDojazd(int numerPasa, WezelView wezelZrodlowy, int pasZrodlowy) {
//		if (isPoprawnyNumerPasa(numerPasa) && wezelZrodlowy != null && wezelZrodlowy.isPoprawnyNumerPasa(pasZrodlowy)) {
//			this.droga.setOdjazd(numerPasa, wezelZrodlowy.getWezel(), pasZrodlowy);
//			this.dojazdy[numerPasa] = new PolaczenieView(wezelZrodlowy, pasZrodlowy);
//		}
//	}
//	
//	public PolaczenieView getOdjazd(int numerPasa) {
//		PolaczenieView result = null;
//		
//		if (isPoprawnyNumerPasa(numerPasa)) {
//			result = this.odjazdy[numerPasa];
//		}
//		
//		return result ;
//	}
	


	public void delete() {
		// TODO Auto-generated method stub
	}

	public void delDojazd(int numerPasa) {
		// TODO Auto-generated method stub
		
	}

	public void delOdjazd(int numerPasa) {
		// TODO Auto-generated method stub
	}

	public PunktMapy[] getPunktyEdycji() {
		return this.punktyEdycji;
	}

	public void setOstatniPunkt(int x, int y) {
		this.punktyKolizyjne.get(this.punktyKolizyjne.size() - 1).setXY(x, y);
		this.updateData();
	}

	public void setOstatniPunkt(PunktMapy punkt) {
		delOstatniPunkt();
		addOstatniPunkt(punkt);
		
		this.updateData();
	}

	public void addOstatniPunkt(int x, int y) {
		addOstatniPunkt(new PunktMapy(x, y));
	}
	
	public void addOstatniPunkt(PunktMapy punkt) {
		this.punktyKolizyjne.add(punkt);
		updateData();
	}

	public void delOstatniPunkt() {
		this.punktyKolizyjne.remove(this.punktyKolizyjne.size() - 1);
		updateData();
	}
	
	public void dodajPunkt(int x, int y) {
		addOstatniPunkt(x, y);
	}

	public void usunPunkt(int x, int y) {
		PunktMapy punkt = znajdzPunkt(x, y);
		this.punktyKolizyjne.remove(punkt);
		updateData();
	}
	
	/** Szuka odpowiedniego punktu kolizyjnego o podanych wspolrzednych */
	protected PunktMapy znajdzPunkt(int x, int y) {
		for (PunktMapy punkt : this.punktyKolizyjne) {
			if (Metody2D.sasiedztwoPunktu(punkt, x, y)) {
				return punkt;
			}
		}
		return null;
	}
	/** Szuka numeru odpowiedniego punktu kolizyjnego o podanych wspolrzednych */
	protected Integer znajdzNumerPunktu(int x, int y) {
		for (int i = 0; i < this.punktyKolizyjne.size(); i++) {
			if (Metody2D.sasiedztwoPunktu(this.punktyKolizyjne.get(i), x, y)) {
				return i;
			}
		}
		return null;
	}
	
	/** Szuka numeru odpowiedniego punktu kolizyjnego o podanych wspolrzednych */
	protected Integer znajdzNumerPasa(int x, int y) {
		for (int i = 0; i < this.pasyPaint.size(); i++) {
			for (PunktMapy punkt : this.pasyPaint.get(i)) {
				if (Metody2D.sasiedztwoPunktu(punkt, x, y)) {
					return i;
				}	
			}
		}
		return null;
	}

	public void przesunCalosc(int deltaX, int deltaY) {
		for(PunktMapy punkt : this.punktyKolizyjne) {
			punkt.move(deltaX, deltaY);
		}
		
		updateData();
	}

	public void updateData() {
		this.pasyPaint.clear();
		this.trasyPaint.clear();
		
		for (int i = 0; i < this.punktyKolizyjne.size(); i++) {
			this.koniec.add(new Pasy());
			this.poczatek.add(new Pasy());
		}
		
		this.punktyEdycji = new PunktMapy[this.punktyKolizyjne.size()];
		for (int i = 0; i < this.punktyKolizyjne.size(); i++) {
			this.punktyEdycji[i] = this.punktyKolizyjne.get(i);
		}
		
		// Dodanie pasow przejazdu
		int numerPasa = 0;
		for (PasPolaczenie polaczeniePasa : this.pasPolaczenia) {
			ArrayList<PunktMapy> punktyPodzialu = new ArrayList<PunktMapy>();
			punktyPodzialu.add(this.punktyKolizyjne.get(polaczeniePasa.start));
			punktyPodzialu.add(this.punktyKolizyjne.get(polaczeniePasa.koniec));
			
			PunktMapy[] punkty = Metody2D.podzielKrzywaBezPunktowKrancowych(punktyPodzialu, GESTOSC_SIATKI);
			pasyPaint.add(punkty);
			
			if (this.skrzyzowanie.getPas(numerPasa) != null) {
				this.skrzyzowanie.getPas(numerPasa++).setDlugosc(punkty.length);
			}
		}
		
		// Dodanie wszystkich tras przejazdow
		for (TrasaPolaczenie aTrasa : this.trasyPolaczenia) {
			trasyPaint.add(getTrasaPunkty(aTrasa.elementy));
		}
	}
	
	public void prowadzDroge(PunktMapy start, PunktMapy koniec) {
		Integer s = znajdzNumerPunktu(start.x, start.y);
		Integer k = znajdzNumerPunktu(koniec.x, koniec.y);
		
		if (s != null && k != null && !	s.equals(k)) {
			addPas(s, k);
		}
//		updateData();
	}
	
	TrasaPolaczenie trasa = new TrasaPolaczenie();
	TrasaPolaczenie aktualnieWyswietlanaTrasa = new TrasaPolaczenie();
	public void prowadzTrase(PunktMapy punkt) {
		Integer numer = znajdzNumerPasa(punkt.x, punkt.y);
		if (numer != null) {
			int pStart = this.pasPolaczenia.get(numer).start;
			int pKoniec = this.pasPolaczenia.get(numer).koniec;
			
			if (this.trasa.elementy.size() == 0) {
				if (this.koniec.get(pStart).pasy.size() == 0) { // Trasa musi zaczynac sie od punktu wjazdowego
					trasa.elementy.add(numer);
					aktualnieWyswietlanaTrasa = null;
				}
			} else {
				if (!trasa.elementy.contains(numer)) { // Elementy nie moga sie powtarzac
					trasa.elementy.add(numer);
				} else { // Jezeli ponownie kliknieto na ten sam odcinek
					trasa.elementy.remove(numer);
				}
			}
			
			// Sprawdzenie czy to nie jest juz koniec trasy
			if (this.poczatek.get(pKoniec).pasy.size() == 0) {
				if (!trasyPolaczenia.contains(trasa)) {
					addTrasa(trasa.elementy);
				}
				aktualnieWyswietlanaTrasa = trasa;
				trasa = new TrasaPolaczenie();
			}
		}
	}
	
	/**
	 * Zaznacza wybrana trase wewnątrz skrzyżowania.
	 * @param numerTrasy NUmer trasy do zaznaczenia
	 */
	public void setZaznaczonaTrasa(int numerTrasy) {
		if (numerTrasy >= 0 && numerTrasy < this.trasyPolaczenia.size()) {
			this.zaznaczonaTrasa = numerTrasy; 
		}
	}
	
	/** Anuluje zaznaczenie trasy */
	public void odznaczTrase() {
		this.zaznaczonaTrasa = -1;
	}
	
	public void usunTrase(int numerTrasy) {
		// TODO napisac
		// uwazac zeby usunac trase rowniez z natywnego skrzyzowania
		// usunac tez wjazdy i wyjazdy dla danej trasy
	}
	
	public Object clone() throws CloneNotSupportedException {
		return null;
	}
}
