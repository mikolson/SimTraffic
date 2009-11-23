/*
 * Copyright 2005 Michal Stanek. All rights reserved.
 * Project name: 	Magisterska
 * File in project: Pojazd.java
 * Creation date: 	2005-12-03
 */
package pl.wroc.pwr.iis.traffic.domain.entity;

import java.io.Serializable;

import pl.wroc.pwr.iis.logger.Logger;
import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Entitable;
import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;

/**
 * TODO: <br> 
 *  1.parametr okreslajacy w jaki sposob dany pojazd wplywa na zmiane widocznosci 
 *    pojazdu znajdujacego sie bezposrednio za nim.
 *     
 * @author Administrator
 */
public class Pojazd implements Cloneable, Entitable, Serializable {

	private static final long serialVersionUID = 195210492906073152L;

	/** FIXME poprawic na prawdziwa wartosc */
    /**
	 * Okresla z jaka maksymalna predkoscia moze poruszac sie najszybszy pojazd
	 * w czasie symulacji. Wartosc ta jest wykorzystywana w trakcie ustalania
	 * dopuszczalnej bezpiecznej luki przy jakiej mozliwa jest zmiana pasa.
	 */
    
    private static final float MNOZNIK_DLUGOSCI = 1.5f;
	private static final float MNOZNIK_PREDKOSCI = 0.15f;
	private static final int PREDKOSC_SAM_OSOBOWEGO = 36;//(int) (36 * MNOZNIK_PREDKOSCI);
	public static final int MAXYMALNA_PREDKOSC_POJAZDOW = (int) (PREDKOSC_SAM_OSOBOWEGO);
    
	/**
     * Dostepne typy pojazdów. 
     */
    public enum Typ {
        /** Samochód osobowy:    (4m dlugosci, 36m/s (130km/h) max predkosc, przyspieszenie 1.5 m/s^2) */
        OSOBOWY, 
        /** Samochód ciezarowy:  (6m dlugosci, 27m/s (100km/h) max predkosc, przyspieszenie 1.0 m/s^2) */
        CIEZAROWY, 
        /** Autobus:             (11m dlugosci, 22m/s (80km/h)  max predkosc, przyspieszenie 0.5 m/s^2) */
        AUTOBUS,
        /** Autobus:             (17m dlugosci, 22m/s (80km/h)  max predkosc, przyspieszenie 0.5 m/s^2) */
        AUTOBUS_PRZEGUBOWY       
    }
    
    /** Okresla jakiego typu jest dany samochod. */
    private final Typ typPojazdu;
    
    /**  
     * Srednie dlugosci pojazdow:
     *  samochod osobowy - 4,7 metra.
     *  samochod ciezarowy - 6.0 metra
     *  autobus - 11 metrów
     *  autobus przegubowy - 17 metrów.
     * */
    private final int dlugosc;
    private final int dlugoscWizualna;
    
    /**
     * Determinuje o jaka wartosc mozliwa będzie zmiana predkosci w kazdej
     * chwili czasu. Domyslnie jest tu podana wartosc przyspieszenia w sekundach
     * do 100 km/h.
     */
    private final float przyspieszenie;
    
    /** Maksymalna mozliwa do osiagniecia przez dany pojazd predkosc na drodze. */
    private final int maxPredkosc;

    /**
     * Kierowca kierujacy pojazdem. Kazdy kierowca pomze posiadac pewna odmienna
     * charakterystyke.
     */
    private final Kierowca kierowca;
    
// -------------------------------------------------------------------------------    
    
    /** Droga na jakiej aktualnie znajduje sie pojazd. */
    private Wezel wezel;
    /** Indeks pasa w ramach wezla sieci. */
    private int pas;
    /** Indeks komorki w ramach pasa wezla sieci. */
    private int komorka;
    
    /** Aktualna wartosc predkosci */
    private float predkosc = 0;
    
    /** Czas jaki dany pojazd przebywal w sieci. */
    private int czas = 0;
    
    /** Ilość komórek sieci jakie przejechal dany pojazd */
    private long przejechaneJednostki;
    
    private Kierunek kierunek = Kierunek.PROSTO;

    public Object clone() throws CloneNotSupportedException {
        Pojazd result = new Pojazd(this.typPojazdu);
        result.predkosc = predkosc;
        result.czas = czas;
        result.przejechaneJednostki = przejechaneJednostki;
        
        return result;
    }
    
    /**
     * Stworzenie nowego pojazdu zawsze musi odbywac sie przez podanie typu
     * pojazdu. Konstruktor utworzy i przypisze kierowce domyslnego (stworzonego
     * przez kontruktor domyslny). Nie podanie typu pojazdu jaki ma zostac
     * stworzony wygeneruje blad.
     * 
     * @param typSamochodu
     *            Typ pojazdu jaki ma byc stworzony.
     */
    public Pojazd(Typ typSamochodu) {
        this(typSamochodu, null);
    }
    
    /**
     * Stworzenie nowego pojazdu zawsze musi odbywac sie przez podanie typu
     * pojazdu oraz rodzaju kierowcy. odanie jako typ kierowcy null spowoduje
     * przypisanie kierowcy domyslnego (stworzonego przez kontruktor domyslny).
     * Nie podanie typu pojazdu jaki ma zostac stworzony wygeneruje blad.
     * 
     * @param typSamochodu
     *            Typ pojazdu jaki ma byc stworzony.
     * @param kierowca
     *            Kierowca jaki ma zostac przypisany do pojazdu.
     */
    public Pojazd(Typ typSamochodu, Kierowca kierowca) {
        if (typSamochodu.equals(Typ.OSOBOWY)) {
            this.dlugoscWizualna = (int) (4 * MNOZNIK_DLUGOSCI);
            this.przyspieszenie = 1.5f;
//            this.przyspieszenie = 1f;
            this.maxPredkosc = (int) (PREDKOSC_SAM_OSOBOWEGO * MNOZNIK_PREDKOSCI);
//            this.maxPredkosc = 4;
            this.typPojazdu = Typ.OSOBOWY;
        } else if (typSamochodu.equals(Typ.CIEZAROWY)) {
            this.dlugoscWizualna = (int) (6 * MNOZNIK_DLUGOSCI);
            this.przyspieszenie = 1;
            this.maxPredkosc = (int) (27 * MNOZNIK_PREDKOSCI);
//            this.maxPredkosc = 3;
            this.typPojazdu = Typ.CIEZAROWY;
        } else if (typSamochodu.equals(Typ.AUTOBUS)) {
            this.dlugoscWizualna = (int) (11 * MNOZNIK_DLUGOSCI);
            this.przyspieszenie = 0.5f;
            this.maxPredkosc = (int) (22 * MNOZNIK_PREDKOSCI);
//            this.maxPredkosc = 1;
            this.typPojazdu = Typ.AUTOBUS;
        } else if (typSamochodu.equals(Typ.AUTOBUS_PRZEGUBOWY)){
            this.dlugoscWizualna = (int) (17 * MNOZNIK_DLUGOSCI);
            this.przyspieszenie = 0.5f;
            this.maxPredkosc = (int) (22 * MNOZNIK_PREDKOSCI);
            this.typPojazdu = Typ.AUTOBUS_PRZEGUBOWY;
        } else {
            // po to aby nie bylo bledu kompilacji
            this.dlugoscWizualna = (int) (4 * MNOZNIK_DLUGOSCI);
            this.przyspieszenie = 1.5f;
            this.maxPredkosc = 36;
            this.typPojazdu = Typ.OSOBOWY;
            // --- Wyslanie komunikatu o bledzie
            Logger.error("Nierozpoznany typ pojazdu");
        }
        
        
        if (kierowca == null) {
            this.kierowca = new Kierowca();
        } else {
            this.kierowca = kierowca;
        }
        
        this.dlugosc = dlugoscWizualna; //+ this.kierowca.losujPreferowanyDystans();
    }

    /**
     * @return Zwraca dlugosc pojazdu. 
     */
    public int getDlugosc() {
        return this.dlugosc;
    }
    
    /**
     * @return Dlugosc jaka bedzie wyswietlana
     */
    public int getDlugoscWizualna() {
        return this.dlugosc;
    }

	/**
	 * @return Zwraca aktualna predkosc pojazdu na drodze.
	 */
	public int getPredkosc() {
		return Math.round(this.predkosc);
	}

	/**
	 * Ustawia aktualna predkosc pojazdu.
	 * @param predkosc
	 */
	public void setPredkosc(float predkosc) {
        if (predkosc >= 0 ) {
            this.predkosc = predkosc;
        } else {
            Logger.warn("Proba ustawienia predkości mniejszej niż 0");
        }
	}

	/**
	 * @return Zwraca maksymalna możliwą predkość pojazdu.
	 */
	public int getMaxPredkosc() {
		return this.maxPredkosc;
	}

	/**
	 * @return Zwraca przyspieszenie pojazdu. Przyspieszenie pojazdu jest stałe i nie moze ulec zmianie.
	 */
	public float getPrzyspieszenie() {
		return this.przyspieszenie;
	}

	/**
	 * @return Zwraca kierowce jaki steruje danym pojazdem.
	 */
	public Kierowca getKierowca() {
		return this.kierowca;
	}

	/**
     * @return Zwraca obiekt drogi na jakim aktualnie znajduje sie pojazd.
     */
    public Wezel getWezel() {
        return this.wezel;
    }

	/**
     * Ustawia w samochodzie referencje na obiekt drogi na jakim aktualnie
     * znajduje sie samochod.
     * 
     * @param droga
     *            Obiekt drogi na jakim aktualnie znajduje sie pojazd
     */
    public void setWezel(Wezel droga) {
        this.wezel = droga;
    }

    /**
     * @return Zwraca komorke w jakiej znajduje sie samochod w ramach pasa
     *         konkretnej drogi.
     */
    public int getKomorka() {
        return this.komorka;
    }

    /**
     * @param komorka
     *            Ustawia komorke na drodze w ramach danego pasa po ktorej
     *            porusza sie samochod.
     */
    public void setKomorka(int komorka) {
        this.komorka = komorka;
    }

    /**
     * @return Zwraca indeks pasa ruchu w ramach drogi na jakim porusza sie dany
     *         samochod.
     */
    public int getPas() {
        return this.pas;
    }

    /**
     * @param pasy
     *            Ustawia na jakim pasie ruchu porusza sie dany samochód.
     */
    public void setPas(int pas) {
        this.pas = pas;
    }
    
    /**
     * Ustawia wszystkie parametry drogi na jakiej znajduje sie pojazd.
     * 
     * @param wezel
     *            Wezel sieci na jakim aktualnie znajduje sie samochod
     * @param pasy
     *            Pas ruchu na jakim znajduje sie pojazd.
     * @param komorka
     *            Komorka na jakiej w ramach pasu pojazd aktualnie stoi
     */
    public void setDroga(Wezel wezel, int pas, int komorka) {
        this.wezel = wezel;
        setDroga(pas, komorka);
    }
    
    /**
	 * Przesuwa w obrebie danej tylko drogi.
	 * 
	 * @param pas
	 *            Pas na którym jest pojazd
	 * @param komorka
	 *            Komórka w ramach danego pasa
	 */
    public void setDroga(int pas, int komorka) {
    	this.pas = pas;
        this.komorka = komorka;
    }

    /**
     * @return Zwraca typ pojazdu.
     */
    public Typ getTypPojazdu() {
        return this.typPojazdu;
    }
    
    /**
     * @return Returns the czas.
     */
    public int getCzas() {
        return this.czas;
    }

    /**
     * @param czas The czas to set.
     */
    public void setCzas(int czas) {
        this.czas = czas;
    }
    
    /**
     * Dodaje no ilości czasu jaki pojazd przebywa w sieci wartosci 1.  
     */
    public void addCzas() {
        this.czas++;
    }

    /**
     * @return Zwraca ilość przejechanych jednostek wezłów sieci połączeń.  
     */
    public long getPrzejechaneJednostki() {
        return this.przejechaneJednostki;
    }

    /**
     * @param przejechaneJednostki
     *            Ustawia nowa wartosc przejechanych jednostek przez dany
     *            pojazd.
     */
    protected void setPrzejechaneJednostki(long przejechaneJednostki) {
        this.przejechaneJednostki = przejechaneJednostki;
    }

    /**
     * Dodaje do aktualnie przejechanych jednostek pojazdu wartość przyrostu.
     * 
     * @param przyrost
     *            Określa o ile zwiekszyć ilość przejechanych jednostek.
     */
    protected void addPrzejechaneJednostki(int przyrost) {
        this.przejechaneJednostki += przyrost;
    }
    
    /**
     * Zwieksza predkosc samochodu, zgodnie z dozwolonym przyspieszeniem. 
     */
    public void przyspiesz() {
        if (this.predkosc + this.przyspieszenie <= this.maxPredkosc) {
            this.predkosc += this.przyspieszenie;
        } else {
            this.predkosc = this.maxPredkosc;
        }
    }
    
    public void zwolnij() {
        if (this.predkosc >= this.przyspieszenie) {
            this.predkosc -= this.przyspieszenie;
        } else {
        	this.predkosc = 0;
        }
    }
    
    /**
     * Metoda powinna byc wywolywana przez system po wczesniejszym przesunieciu
     * samochodu w ramach drogi. Metoda ta zwieksza wartosci zmiennych
     * kontrolnych sumaryczne przesuniecie oraz czas przebywania w sieci.
     */
    public void przesun(int przesuniecie) {
        this.przejechaneJednostki += przesuniecie;
        this.czas++;
    }
    
    /**
     * Przesuwa dany samochod w ramach drogi oraz ustawia aktualne wspolrzedne jego pozycji. 
     * @param pas Pas w ramach drogi
     * @param komorka Komórka pasa
     * @param przesuniecie O ile jednostek przesunał się samochód
     */
    public void przesun(int pas, int komorka, int przesuniecie){
    	przesun(przesuniecie);
    	setDroga(pas, komorka);
    }
    
    /**
     * @param kierunek Okresla kierunek w jakim bedziemy poszukiwac odleg
     * @return Zwraca odleglość jaka jest do samochodu jadacego przed danym
     *         pojazdem.
     */
    public int getOdlegloscOdSamochoduPrzed(Kierunek kierunek, int widocznosc) {
        int deltaKomorka = getWezel().getPrzesuniecieWzgledemPasow(this.pas, kierunek, this.komorka);
//        System.out.println("------------------------------");
//        System.out.println("pozycja: " + getPas() + " : " +getKomorka());
//        System.out.println("badanie: " + kierunek.zmienPas(this.pas) + " : " +(deltaKomorka + getDlugosc()));
//        System.out.println("getOdlegloscOdSamochoduPrzed(): " + this.wezel.getOdlegloscPrzed(kierunek.zmienPas(pas), deltaKomorka + getDlugosc(), widocznosc));
        return this.wezel.getOdlegloscPrzed(kierunek.zmienPas(pas), deltaKomorka + getDlugosc(), widocznosc);
    }
    
    /**
     * @param kierunek Kierunek w jakim
     * @return Zwraca predkosc samochodu jadacego przed danym pojazdem.
     */
    public int getPredkoscSamochoduPrzed(int widocznosc, Kierunek kierunek) {
        int deltaKomorka = getWezel().getPrzesuniecieWzgledemPasow(this.pas, kierunek, this.komorka);
        return this.wezel.getPredkoscPrzed(kierunek.zmienPas(this.pas), deltaKomorka + getDlugosc(), widocznosc);
    }

    /**
     * @returnZwraca predkosc samochodu jadacego za danym pojazdem
     */
    public int getPredkoscSamochoduZa(int widocznosc, Kierunek kierunek) {        
        int deltaKomorka = getWezel().getPrzesuniecieWzgledemPasow(this.pas, kierunek, this.komorka);
		return this.wezel.getPredkoscZa(kierunek.zmienPas(this.pas), deltaKomorka, widocznosc);
    }
    
    /**
     * Metoda sprawdza czy jest mozliwa zmiana pasa w podanym przez parametr kierunku. 
     * Sprawdzane jest czy fizycznie istnieje pasy na ktory jest mozliwosc wjechania, a 
     * nastepnie badane jest czy na pasie tym znajduje sie wystarczajaca luka aby tam 
     * wjechac. Luka ta musi wynisic [vMax, v]; 
     * @param kierunek Kierunek w ktorym mamy 
     * @return
     */
    public boolean isMozliwaZmianaPasa(Kierunek kierunek, float preferowanyDystans) {
    	boolean result = false;
    	if (getWezel().isMozliwaZmianaPasa(getPas(), kierunek)) {
        	int delta_komorka = getWezel().getPrzesuniecieWzgledemPasow(this.pas, kierunek, this.komorka);
        	
    		int xPrzed = getWezel().getOdlegloscPrzed(kierunek.zmienPas(this.pas), delta_komorka + getDlugosc(), Math.round(getPredkosc()+getPrzyspieszenie() + preferowanyDystans));
    		int xZa = getWezel().getOdlegloscZa(kierunek.zmienPas(this.pas), delta_komorka);
    		
    		if (xPrzed == Integer.MAX_VALUE && xZa > Pojazd.MAXYMALNA_PREDKOSC_POJAZDOW) {
    			result = true;
    		}
    	}
    	return result;
    }
    
    
    public int getOdlegloscOdSamochoduPrzedLewo() {
        return 0;
    }
    
    public int getOdlegloscOdSamochoduLewo() {
    	return 0;
    }
    
    public int getOdlegloscOdSamochoduPrzedPrawo() {
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Entitable#wykonajRuch()
     */
    public void wykonajRuch() {
        this.kierowca.wykonajRuch(this);
    }
    
    public int getMaxPredkoscPasa() {
        return getWezel().getMaxPredkoscPasa(getPas());
    }
    
    public Kierunek getKierunekRuchu() {
        return getWezel().getKierunekPasa(getPas());
    }

	public Kierunek getKierunek() {
		return kierunek;
	}

	public void setKierunek(Kierunek kierunek) {
		this.kierunek = kierunek;
	}
}

