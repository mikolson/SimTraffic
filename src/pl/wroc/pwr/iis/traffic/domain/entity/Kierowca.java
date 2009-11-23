/*
 * Copyright 2005 Michal Stanek. All rights reserved.
 * Project name: 	Magisterska
 * File in project: Kierowca.java
 * Creation date: 	2005-12-03
 */
package pl.wroc.pwr.iis.traffic.domain.entity;

import java.io.Serializable;

/**
 * @author Michał Stanek
 */
public class Kierowca implements Serializable {
	private static final long serialVersionUID = -4906650931996398676L;

	/**
	 * Preferowana odleglosc od samochodu jadacego przed kierowca. Domyślna
	 * wartość preferowanej luki wynosi 2 metry, co odpowiada 2 komórką w modelu
	 * komórkowym.
	 */
	private float preferowanyDystans = 2;
	
	private float preferowanyDystansMin = 1;
	private float preferowanyDystansMax = 10;

	/**
	 * W skali od 0(nie wpuszcza) do 1(zawsze wpuszcza). Domyślna wartość tego
	 * parametru przewiduje, że kierowca w 50% przypadków będzie skłonny
	 * stworzyć odpowiednią lukę dla samochodu prubującego wjechac na pasy ruchu
	 * po którym on się aktualnie porusza.
	 */
	private float tendencjaDoWpuszczania = 0.5f;

	/**
	 * Okresla w jakim stopniu dany kierowca zachowuje sie w stosunku do
	 * ograniczen predkosci na drodze. Moze byc ujemna - kierowca woli jechac
	 * wolniej niz przewiduje to ograniczenie (wartość powinna zawierać się
	 * pomiędzy 0, a -1); lub dodatnia - kierowca woli jechac szybciej niz
	 * wynikaloby to z ograniczenia(wartość powinna być wtedy pomiędzy 0 a 1).
	 * Domyślna wartość zakłada, że kierowca stara się jechać z maksymalną
	 * predkością przewidzianą na danym pasie. Parametr ten może również służyć,
	 * do modelowania zachowania kierowcy w czasie trudnych warunków pogodowych,
	 * gdzie ze względu na panujące warunki możemy zdecydować się na zachowanie
	 * większej ostrożności i jazdę z mniejszą predkością.
	 */
	private float tendencjaPredkosci = 0;

	/**
	 * Okresla jak duza musi byc luka czasowa aby kierowca zdecydowal sie na
	 * zrobienie manewru - wyprzedzania, wjechania na skrzyzowanie z ulicy
	 * podporzadkowanej. Dymyslna wartość zakłada, że kierowca potrzebuje
	 * przynajmniej siedmiu bezpiecznych cykli modelu (7 sekund symylacji), aby
	 * zdecydować się na wykonanie manewru.
	 */
	private int akceptowalaLukaPrzyManewrach = 7;

	/**
	 * Modyfikacja domyslnego czasu reakcji dla danego kierowcy - wartosc
	 * domyslna reakcji dla zdarzen przewidywalnych - 0.54 [sek], dla zdarzen
	 * nieprzewidywalnych - 0.73 sek. Ze względu, że model przewiduje
	 * rozdzielczosc symulacji rzędu 1 sek, parametr ten ma małe znaczenie i
	 * jest istotny tylko wówczas, gdy czas reakcji, np. ze względu na aktualne
	 * warunki drogowe jest większy niż jedna sekunda. Warunki, które powodować
	 * mogą wydłużenie czasu reakcji to mgła, śnieżyca, ulewa. Domyślna wartość
	 * zakłada, że czas reakcji kierowcy nie jest wydłużany
	 */
	private int modyfikatorCzasuReakcji = 0;
    
    /**
     * Okresla ilosc metrów na jaka kierowca analizuje swoja aktualna sytuacje.
     * Oczywiscie wartosc ta jest bazowa i tak naprawde powinna byc modyfikowana
     * w miare zwiekszania predkosci.
     */
    private int zakresWidocznosci = 50;
    
    /** Prawdopodobieństwo z jakim kierowca zwolni jadąc po prostej, kiedy to  */
    private int prawdopodobienstwoZwolnienia = 25;

	/**
	 * Konstruktor obiektu kierowcy z domyślnymi wartościami parametrów.
	 */
	public Kierowca() {
        // Wszystkie wartości przypisane sa przy ich deklaracji
	}

	/**
	 * Tworzy kierowcę oraz nadaje wartości jego podstawowych parametrów
	 * osobowych.
	 * 
	 * @param preferowanyDystans
	 *            Ustawia preferowany dystans pomiędzy samochodem jadącym z
	 *            przodu.
	 * @param tendencjadoWpuszczania
	 *            Tendencja do wpuszczania samochodów na pasy ruchu po którym
	 *            porusza sie kierowca.
	 * @param tendencjaPredkosci
	 *            Tendencja do zachowywania ograniczeń predkości dla danego
	 *            pasa.
	 * @param akceptowalnaLukaPrzyManewrach
	 *            Akceptowalna luka czasowa jaką będzie tolerował kierowca w
	 *            czasie wykonywania manewrów.
	 * @param modyfikatorCzasuReakcji
	 *            Modyfikator czasu rekacji kierocy.
	 */
	public Kierowca(float preferowanyDystans, 
					float tendencjadoWpuszczania,
					float tendencjaPredkosci, 
					int akceptowalnaLukaPrzyManewrach,
					int modyfikatorCzasuReakcji) {
		setPreferowanyDystans(preferowanyDystans);
		setModyfikatorCzasuReakcji(modyfikatorCzasuReakcji);
		setAkceptowalaLukaPrzyManewrach(akceptowalnaLukaPrzyManewrach);
		setTendencjaDoWpuszczania(tendencjadoWpuszczania);
		setTendencjaPredkosci(tendencjaPredkosci);
	}

	/**
	 * @return Akceptowalna luka czasowa dla kierowcy. Dokładny opis parametru: @linkplain #akceptowalaLukaPrzyManewrach 
	 */
	public int getAkceptowalaLukaPrzyManewrach() {
		return this.akceptowalaLukaPrzyManewrach;
	}

	/**
	 * Dokładny opis działania parametru opisany jest: <br>
	 * 	@link #akceptowalaLukaPrzyManewrach
	 * 
	 * @param akceptowalaLukaPrzyManewrach
	 *            Wartosc nowej luki czasowej akceptowalej przy manewrach.
	 */
	public void setAkceptowalaLukaPrzyManewrach(int akceptowalaLukaPrzyManewrach) {
		this.akceptowalaLukaPrzyManewrach = akceptowalaLukaPrzyManewrach;
	}

	public int getModyfikatorCzasuReakcji() {
		return this.modyfikatorCzasuReakcji;
	}

	public void setModyfikatorCzasuReakcji(int modyfikatorCzasuReakcji) {
		this.modyfikatorCzasuReakcji = modyfikatorCzasuReakcji;
	}

	public float getPreferowanyDystans() {
		return this.preferowanyDystans;
	}

	/**
	 * Ustawia preferowany dystans jazdy za kolejnym samochodem.
	 * @param preferowanyDystans
	 */
	public void setPreferowanyDystans(float preferowanyDystans) {
		this.preferowanyDystans = preferowanyDystans;
	}

	public float getTendencjaDoWpuszczania() {
		return this.tendencjaDoWpuszczania;
	}

	public void setTendencjaDoWpuszczania(float tendencjaDoWpuszczania) {
		this.tendencjaDoWpuszczania = tendencjaDoWpuszczania;
	}

	public float getTendencjaPredkosci() {
		return this.tendencjaPredkosci;
	}

	/**
	 * Ustawia dendencje predkosci kierowcy
	 * @param tendencjaPredkosci
	 */
	public void setTendencjaPredkosci(float tendencjaPredkosci) {
		this.tendencjaPredkosci = tendencjaPredkosci;
	}

    /**
     * @return Returns the zakresWidocznosci.
     */
    public int getZakresWidocznosci() {
        return this.zakresWidocznosci;
    }

    /**
     * @param zakresWidocznosci The zakresWidocznosci to set.
     */
    public void setZakresWidocznosci(int zakresWidocznosci) {
        this.zakresWidocznosci = zakresWidocznosci;
    }

    /**
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Entitable#wykonajRuch()
     */
    public void wykonajRuch(Pojazd pojazd) {
        zmienPas(pojazd);
//        int odleglosc = (int) Math.max((int) (pojazd.getOdlegloscOdSamochoduPrzed(pojazd.getKierunek(), getWidocznosc(pojazd)))-getPreferowanyDystans(), 0);
        int odleglosc = (int) Math.max((int) (pojazd.getOdlegloscOdSamochoduPrzed(pojazd.getKierunek(), getWidocznosc(pojazd))), 0);
        int maxPredkosc = maxPredkosc(pojazd);
        
        if (odleglosc > maxPredkosc) {
            //WARN to jest wazny element modelu 
             if (czyZwolnic()) {
//            	 zmienPas();
                 pojazd.zwolnij();
             } else {
                 pojazd.przyspiesz();
             } // TODO dorobic zachowanie predkosci
             
        } else {
            pojazd.setPredkosc(odleglosc);
        }
        
        if (pojazd.getPredkosc() == 0) { // Tak zeby stojacy pojazd nie zmieniał pasów
        	pojazd.setKierunek(Kierunek.PROSTO);
        }
        
    }
    
    private boolean zmienPas(Pojazd pojazd) {
    // Sprawdzenie czy nalezy zmienic pasy
    	boolean result = false;
    	pojazd.setKierunek(Kierunek.PROSTO);

    	if (pojazd.isMozliwaZmianaPasa(Kierunek.PROSTO, 0)) { 
    		// duzo obiektow nie pozwala zmienic pasa wiec warto sprawdzic czy wogole dla czegokolwiek mozna zmienic pas 
    		int v = (int) pojazd.getPredkosc();
    		if (pojazd.isMozliwaZmianaPasa(Kierunek.LEWY, getPreferowanyDystans())) {
    			int vPrzed = pojazd.getPredkoscSamochoduPrzed(getWidocznosc(pojazd), Kierunek.PROSTO);
    			int vLewy  = pojazd.getPredkoscSamochoduPrzed(getWidocznosc(pojazd), Kierunek.LEWY);
    			
    			if (vPrzed <= v && vPrzed <= vLewy) {
    				result = true;
    				pojazd.setKierunek(Kierunek.LEWY);
    			}
    		}
    		
    		if (!result && pojazd.isMozliwaZmianaPasa(Kierunek.PRAWY, getPreferowanyDystans())) {
    			int vPrzed = pojazd.getPredkoscSamochoduPrzed(getWidocznosc(pojazd), Kierunek.PROSTO);
    			int vPrawy  = pojazd.getPredkoscSamochoduPrzed(getWidocznosc(pojazd), Kierunek.PRAWY);
    			
    			if (vPrawy > v || vPrawy > vPrzed) {
    				result = true;
    				pojazd.setKierunek(Kierunek.PRAWY);
    			}
    		}
    		
    		// Sprawdzenie czy jest luka bezpieczenstwa
    		// Zmiana pasa na odpowiedni
    	}
    	
    	return result;
	}
    
    private int getWidocznosc(Pojazd pojazd) {
    	return (int) (4 * pojazd.getMaxPredkosc());
    }

	/**
     * TODO tendencja predkosci kierowcy
     * @return
     */
    protected int maxPredkosc(Pojazd pojazd) {
        return Math.min(pojazd.getMaxPredkosc(), pojazd.getMaxPredkoscPasa());
    }
    
    protected boolean czyZwolnic() {
        boolean result = false;
        int losowa = (int) (Math.random() * 100);
        if (losowa <= this.prawdopodobienstwoZwolnienia) {
            result = true;
        }
        return result;
    }
    
    public int losujPreferowanyDystans() {
    	return (int) (preferowanyDystansMin + Math.random() * (preferowanyDystansMax-preferowanyDystansMin));
    }
}
