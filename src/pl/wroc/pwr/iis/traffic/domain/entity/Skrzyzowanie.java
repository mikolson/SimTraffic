package pl.wroc.pwr.iis.traffic.domain.entity;

import java.util.ArrayList;

import pl.wroc.pwr.iis.logger.Logger;
import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;

public final class Skrzyzowanie extends WezelAbstract {
	private static final long serialVersionUID = 2812471723825961307L;

	
	@SuppressWarnings("unchecked") 
	ArrayList<Integer>[] pasyRuchu = new ArrayList[0]; // Zawiera numery tras dla poszczegolnych pasow ruchu (pasow wjazdowych)
	
    ArrayList<Pas> pasy = new ArrayList<Pas>(); // Pasy ruchu wchodzace w sklad tras przejazdu
    ArrayList<Trasa> trasy = new ArrayList<Trasa>(); // Trasy przejazdu pojazdow
    
    // Zmienne dotyczące sterowania sygnalizacją świetlną
    private boolean sygnalizacja = false;
    private ArrayList<Sygnalizacja> grupySygnalizacyjne = new ArrayList<Sygnalizacja>();
	
    private int taktFazy = 0;
    private int aktualnaGrupaSygnalizacyjna = 0;
    
	public Skrzyzowanie() {
		//
    }
    
    public Pas addPas(int dlugosc) {
        Pas result = null;
        if (dlugosc > 0) {
            result = new Pas(dlugosc);
            this.pasy.add(result);
        } else {
            Logger.warn("Skrzyzowanie.addPas(): bledne parametry!", this);
        }
        return result;
    }
    
    /**
     * Zwraca pas wewnatrz skrzyzowania o podanym numerze
     * @param numer 
     * @return
     */
    public Pas getPas(int numer) {
        Pas result = null;
        if (numer >= 0 && numer < this.pasy.size()) {
            result = this.pasy.get(numer);
        } else {
            Logger.warn("Skrzyzowanie.getPas(): bledne parametry!");
        }
        return result;
    }
    
    public void clearPasy() {
        this.pasy.clear();
    }
    
    public void clearAll() {
    	clearPasy();
    	clearTrasy();
    }

	public int getIloscPasow() {
		return this.pasyRuchu.length;
	}
	
	public int getIloscPasowWewnetrznych() {
		return this.pasy.size();
	}
	
	public int getIloscTras() {
		return this.trasy.size();
	}
	
    /**
     * Dodaje trase do listy tras skrzyzowania
     * 
     * @param macPredkosc
     *            Maksymalna predkosc na danej trasie
     * @return Numer trasy w ramach skrzyżowania
     */
    public Trasa addTrasa(int maxPredkosc) {
        Trasa result = new Trasa(maxPredkosc);
        this.trasy.add(result);
        
        Polaczenie[] nowe = new Polaczenie[this.odjazd.length + 1];
        for (int i = 0; i < this.odjazd.length; i++) {
			nowe[i] = this.odjazd[i];
		}
        
        this.odjazd = nowe;
        
        return result;
    }
    /**
     * Dodaje trase do listy tras skrzyzowania
     * 
     * @param trasa
     *            Specyfikacja trasy.
     * @return Numer trasy w ramach skrzyżowania
     */
    public Trasa addTrasa() {
        return addTrasa(Droga.DOMYSLNA_MAX_PREDKOSC);
    }

    
    /**
     * Zwraca liste tras ktore zaczynaja sie od danego pasa ruchu.
     * 
     * @param pas
     *            Pas ruchu jaki ma zaczynac trase.
     * @return Tablica tras rozpoczynajacych sie od wskazanego pasa
     */
    public ArrayList<Trasa> getTrasaDlaPasa(Pas pas) {
        ArrayList<Trasa> result = new ArrayList<Trasa>();
        for (Trasa trasa : this.trasy) {
            if (trasa.getPierwszyOdcinek().equals(pas)) {
                result.add(trasa);
            }
        }
        return result;
    }
    
    public Trasa getTrasa(int numerPasa) {
    	Trasa result = null;
    	if (numerPasa >= 0 && numerPasa < this.trasy.size()){
    		result = this.trasy.get(numerPasa);
    	} else {
    		Logger.warn("Niepoprawny numer trasy (pasa) wewnątrz skrzyżowania.");
    	}
    	return result;
    }
    
    /**
     * Usuwa wszystkie trasy ze skrzyzowania.
     */
    public void clearTrasy() {
        this.trasy.clear();
    }
    
//  -----------------------------------------------------------------------------------------------------
	/**
	 * @param indeksPasa Trasa po jakiej porusza sie pojazd
	 * @param komorka Odleglosc na trasie
	 * @param widocznosc Widocznosc
	 * @return
	 */
	public int getOdlegloscPrzed(int indeksPasa, int komorka, int widocznosc) {
		Trasa trasa = getTrasa(indeksPasa);
        Pas pas = trasa.getOdlegloscPas(komorka); 		// pobranie odpowiedniego pasa wg komorki danej trasy
        int kom = trasa.getOdlegloscIndeks(komorka);	// komorka danego pasa
        int result = 0;

        if (pas != null) {
        	if (kom == 0) {
        		if (isZagrozenia(indeksPasa, trasa.getOdlegloscPas(komorka-1), komorka)) {
        			return 0;
        		}
        	}
        	result = pas.getOdlegloscPrzed(kom, widocznosc);
        	
        	if (result < 0) { //Poszukiwanie mysi byc szersze niz ten konkretny pas
        		int sprawdzono = (pas.getDlugosc() - kom);
        		widocznosc = widocznosc - sprawdzono;
        		
        		// SPrawdzenie czy z innych pasow cos wjedzie na ten pas
        		if (isZagrozenia(indeksPasa, pas, komorka+sprawdzono)) {
        			result = sprawdzono;
        		} else {
        			//Dodanie innego pasa
        			result = getOdlegloscPrzed(indeksPasa, komorka + sprawdzono, widocznosc);
        			result = (result == BRAK_POJAZDOW_W_ZAKRESIE_WIDOCZNOSCI) ? result : sprawdzono + result;
        		}
        	}
        } else {
        	// Sprawdzenie wyjazdu danej trasy poniewaz podana komorka byla juz za dana trasą
        	komorka = komorka - trasa.getDlugosc();
        	if (getOdjazd(indeksPasa) != null) {
        		int pasDocelowy = getOdjazd(indeksPasa).pas;
        		result = getOdjazd(indeksPasa).wezel.getOdlegloscPrzedNaWjezdzie(pasDocelowy, komorka, widocznosc);
        	}
        }
        
		return result;
	}
	
	public int getOdlegloscPrzedNaWjezdzie(int indeksPasa, int komorka, int zakresWidocznosci) {
		int result = 0;
		// Obsluga sygnalizacji swietlnej
        if (isSygnalizacja()) {
        	Sygnalizacja aktualnaSygnalizacja = this.grupySygnalizacyjne.get(this.aktualnaGrupaSygnalizacyjna);
        	int numerTrasy = getPierwszaTrasaPasaWjazdowego(indeksPasa);
        	if (aktualnaSygnalizacja.containsTrasa(indeksPasa)) {
        		result = getOdlegloscPrzed(numerTrasy, komorka, zakresWidocznosci);
        	}
        	
        }
		return result;
	}
	
	/**
	 * Podaje pierwsza trase przejazdu przypisana do danego pasa wjazdowego
	 * 
	 * @param pas
	 *            Numer pasa wjazdowego
	 * @return
	 */
	public int getPierwszaTrasaPasaWjazdowego(int pas) {
		ArrayList<Integer> mozliweTrasy = getNumeryTrasDlaPasaRuchu(pas);
		pas = mozliweTrasy.get(0);
		return pas;
	}

	/**
     * @param pas Aktualny pas 
     * @param odleglosc na jaka odleglosc chemy sie przesunac
     */
	private boolean isZagrozenia(int indeksPasa, Pas pas, int odleglosc) {
    	boolean result = false;

    	Trasa trasa = getTrasa(indeksPasa);
        Pas p = trasa.getOdlegloscPas(odleglosc);
        
        boolean indeksWiekszy = true;
        if (p != null) {
        	ArrayList<Pas> pasy = p.getDojazdy();
        	
        	for (int i = 0; i < pasy.size(); i++) {
        		Pas pp = pasy.get(i);
        		
        		// nie sprawdzaj pasa z ktorego przyjezdza samochod
        		if (!pp.equals(pas)) { 
            		if(indeksWiekszy) {
            			if(isCosWyjedziePozaPas(pp)) {
            				result = true;
            				break;
            			} else {
            				// przeszukuj tylko do aktualnego pasa
    	    				indeksWiekszy = false;
    	    			} 
            		} else {
            			// FIXME TUTAJ TO KONIECZNIE NAPRAWIC
//            			if(isCosStoiMiedzyPasami(pp)) {
//            				result = true;
//            				break;
//            			}
            		}
        		} else {
        			indeksWiekszy =false;
        		}
        	}
        } 
    	// else p == null -> samochod wyjechal juz za skrzyzowanie
        // na pewno nie bedzie kolizji

        
        return result;   
    }
    
	private boolean isCosStoiMiedzyPasami(Pas pas) {
    	boolean result = false;
    	
    	for (int i = pas.getDlugosc()-1; i >= 0; i--) {
			if(pas.getZawartosc(i) != null) {
				Pojazd poj = pas.getZawartosc(i);
				int maxPrzesuniecie = (int) (poj.getDlugosc()); 
				if (maxPrzesuniecie + i>= pas.getDlugosc()) {
					result = true;
				}
				break;
			}
		}
    	
    	return result;
    }
	
    private boolean isCosWyjedziePozaPas(Pas pas) {
    	boolean result = false;
    	
    	for (int i = pas.getDlugosc()-1; i >= 0; i--) {
			if(pas.getZawartosc(i) != null) {
				Pojazd poj = pas.getZawartosc(i);
				int maxPrzesuniecie = (int) (poj.getPredkosc() + poj.getPrzyspieszenie() + poj.getDlugosc()); 
				if (maxPrzesuniecie + i>= pas.getDlugosc()) {
					result = true;
					break;
				}
			}
		}
    	
    	return result;
    }
    
    public int getMaxPredkoscPasa(int pas) {
		return trasy.get(pas).getMaxPredkosc();
	}

	public Kierunek getKierunekPasa(int numerPasa) {
		return Kierunek.PRAWY;
	}

	public boolean isMozliwaZmianaPasa(int indeksPasa, Kierunek kierunek) {
		return false;
	}

	public int getPredkoscPrzed(int indeksPasa, int komorka, int zakresWidocznosci) {
		return 0;
	}

	public int getPredkoscZa(int indeksPasa, int komorka, int zakresWidocznosci) {
		return 0;
	}

	public int getOdlegloscZa(int indeksPasa, int komorka) {
		return 0;
	}

	public int getPrzesuniecieWzgledemPasow(int pas, Kierunek kierunekDocelowy, int komorka) {
		return komorka;
	}


    /* (non-Javadoc)
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Entitable#wykonajRuch()
     */
    public void wykonajRuch() {
    	if (isSygnalizacja()) {
    		this.taktFazy ++;
    		
    		if (taktFazy > this.grupySygnalizacyjne.get(aktualnaGrupaSygnalizacyjna).getCzasFazy()){
    			this.aktualnaGrupaSygnalizacyjna ++;
    			if (aktualnaGrupaSygnalizacyjna >= this.grupySygnalizacyjne.size()) {
    				aktualnaGrupaSygnalizacyjna = 0;
    			}
    		}
    	}
    	
        resetujPasy();
        
        for (Trasa trasa : this.trasy) {
            ArrayList<Pas> odcinki = trasa.getPasy();
            
            for (int i = odcinki.size() - 1; i >= 0; i--) {
                Pas pas = odcinki.get(i);
                if (!pas.isPrzetworzony()) {
                    pas.setPrzetworzony(true);
                    przesunSamochody(pas);
                }
            }
        }
    }
    
    /**
     * Ustawia stan nie przetworzony na wszystkie pasy.
     */
    private void resetujPasy() {
        for (Pas pas : this.pasy) {
            pas.setPrzetworzony(false);
        }
        
    }

    /**
     * Przesuwa wszystkie samochody na danym pasie
     * @param pas
     */
    private void przesunSamochody(Pas pas) {
        int dlugoscPasa = pas.getDlugosc();
        for (int komorka = dlugoscPasa - 1; komorka >= 0; komorka--) {
//            Pojazd pojazd = pas.getZawartosc(i);
            Pojazd pojazd = pas.getZawartoscFast(komorka);
            if (pojazd != null) {
            	pojazd.wykonajRuch();
                int predkosc = (int) pojazd.getPredkosc();

                int komorkaTrasy = pojazd.getKomorka()+predkosc;
                pojazd.setKomorka(komorkaTrasy); // Zmiana informacji o aktualnym polozeniu samochodu
                pas.setZawartosc(null, komorka); // usuniecie pojazdu z akt komorki
                
                int numerTrasy = pojazd.getPas();
                if (komorkaTrasy < getDlugosc(numerTrasy)) {
                	Pas p = getTrasa(numerTrasy).getOdlegloscPas(komorkaTrasy); 		// pobranie odpowiedniego pasa wg komorki danej trasy
                	int kom = getTrasa(numerTrasy).getOdlegloscIndeks(komorkaTrasy);

                	p.setZawartosc(pojazd, kom);
                } else {
                	int pozostalo = komorkaTrasy - getDlugosc(numerTrasy);
                	Polaczenie odjazd = getOdjazd(numerTrasy);
                	odjazd.wezel.setPojazdAt(odjazd.pas, pozostalo, pojazd);
                }
                
                
//                if (komorka + predkosc < dlugoscPasa) { // Wszystko odbywa sie w ramach tego pasa
//                    pas.setZawartosc(pojazd, komorka+predkosc);
//                } else { // Samochod zmieni pasy
//                    int ileJeszcze = predkosc - (dlugoscPasa - komorka);
//                    przesunNaInnyPas(pojazd, pas, ileJeszcze);
//                }
            }
        }
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Pas pas: this.pasy) {
            for (int i = 0; i < pas.getDlugosc(); i++) {
                if (pas.getZawartosc(i) != null) {
                    result.append("X");
                } else {
                    result.append("-");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    /* (non-Javadoc)
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#getOdlegloscPrzed(int, int)
     */
    public int getOdlegloscPrzed(int indeksPasa, int komorka) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    /**
     * Ustawia samochód na danej trasie w danej komorce oraz ustawia w samochodzie 
     * odpowiednia referencje
     * @param p
     * @param numerTrasy
     * @param komorka
     * @return
     */
    public boolean setZawartosc(Pojazd p, int numerTrasy, int komorka) {
    	boolean result = true;
    	Trasa trasa = trasy.get(numerTrasy);
    	
    	// sprawdzenie czy jest wolne miejsce
    	for (int i = 0; i < p.getDlugosc(); i++) {
        	Pas pas = trasa.getOdlegloscPas(komorka + i);
            int kom = trasa.getOdlegloscIndeks(komorka + i);
            
            if (pas.getZawartosc(kom) != null) {
            	result = false;
            	break;
            }
		}
    	
    	if (result) {
    		Pas pas = trasa.getOdlegloscPas(komorka);
    		int kom = trasa.getOdlegloscIndeks(komorka);
    		
    		p.setDroga(this, numerTrasy, komorka);
    		pas.setZawartosc(p, kom);
    	} else {
    		Logger.warn("Brak miejsca na wjazd samochodu");
    	}
        
        return result;
    }
    
    public Pojazd getZawartosc(int numerTrasy, int komorka) {
    	Trasa trasa = trasy.get(numerTrasy);
    	Pas pas = trasa.getOdlegloscPas(komorka);
    	int kom = trasa.getOdlegloscIndeks(komorka);
    	
    	return pas.getZawartosc(kom);
    }

	public boolean isPoprawnyNumerPasa(int numerPasa) {
		if (numerPasa >= 0 && numerPasa < pasyRuchu.length) {
			return true;
		} else {
			Logger.warn("Niepoprawny numer pasa wjazdowego: " + numerPasa);
		}
		return false;
	}
	
	public boolean isPoprawnyNumerTrasy(int numerPasa) {
		if (numerPasa >= 0 && numerPasa < trasy.size()) {
			return true;
		} else {
			Logger.warn("Niepoprawny numer pasa ruchu: " + numerPasa);
		}
		return false;
	}
	
	

	@SuppressWarnings("unchecked")
	public void setIloscPasow(int iloscPasow) {
		if (iloscPasow >= 0) {
//			super.setIloscPasow(iloscPasow);
			this.pasyRuchu = new ArrayList[iloscPasow];
			
			for (int i = 0; i < pasyRuchu.length; i++) {
				pasyRuchu[i] = new ArrayList<Integer>();
			}
		}
	}
	
	/**
	 * Zwieksza ilosc pasow o jeden
	 */
	@SuppressWarnings("unchecked")
	public void addIloscPasow() {
		ArrayList<Integer>[] pRuchu = new ArrayList[pasyRuchu.length + 1];
		
		for (int i = 0; i < pasyRuchu.length; i++) {
			pRuchu[i] = pasyRuchu[i];
		}
		pRuchu[pRuchu.length - 1] = new ArrayList<Integer>();
		
		addDojazd();
//		addOdjazd();
		this.pasyRuchu = pRuchu;
	}
	
	@Override
	public void addOdjazd() {
		super.addOdjazd();
	}
	
	
	/**
	 * Bardzo wazna metoda. Dodaje do kazdego wirtualnego pasa
	 * skrzyżowania, trase przejazdu. 
	 */
	public void addTrasyDlaPasaRuchu(int numerPasa, int trasa) {
		if (isPoprawnyNumerPasa(numerPasa) && trasa >= 0 && trasa < this.trasy.size()) {
			this.pasyRuchu[numerPasa].add(trasa);
		}
	}
	
	public ArrayList<Integer> getNumeryTrasDlaPasaRuchu(int numerPasa) {
		if (numerPasa >= 0 && numerPasa < pasyRuchu.length) {
			return pasyRuchu[numerPasa];
		}
		return null;
	}
	
	/**
	 * Zwraca dlugosc danej trasy. Długosc mierzona jest w ilosci komorek. 
	 * @param numerTrasy
	 * @return
	 */
	public int getDlugosc(int numerTrasy) {
		int result = 0;
		Trasa t = trasy.get(numerTrasy);
		for (Pas pas : t.getPasy()) {
			result += pas.getDlugosc();
		}
		return result;
	}

	/**
	 * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#setPojazdAt(int, int, pl.wroc.pwr.iis.traffic.domain.entity.Pojazd)
	 */
	public boolean setPojazdAt(int pas, int komorka, Pojazd pojazd) {
    	boolean result = false;

    	// Losowanie trasy na jakiej znajdzie sie dany pojazd
    	// TODO zrobienie tego losowania z rozkladem prawdopodobienstwa
    	if (isPoprawnyNumerPasa(pas)) {
    		pas = losujNumerTrasy(pas);
    	
	    	if (pojazd != null && isPoprawnyNumerTrasy(pas) 
	    			&& komorka >=0 && komorka + pojazd.getDlugosc() < getDlugosc(pas)) {
	    		
	    		result = setZawartosc(pojazd, pas, komorka);
	    	}
    	} else {
    		Logger.warn("Niepoprawne parametry dla wstawienia pojazdu na skrzyzowanie");
    	}
    	
    	return result;

	}

	/**
	 * Losuje numer trasy przejazdowej dla pasa wjazdowego
	 * @param pas Numer pasa wjazdowego
	 * @return
	 */
	private int losujNumerTrasy(int pas) {
		ArrayList<Integer> mozliweTrasy = getNumeryTrasDlaPasaRuchu(pas);
		int indeks = (int) (Math.random() * mozliweTrasy.size());
		pas = mozliweTrasy.get(indeks);
		return pas;
	}
	
	public void setOdjazd(int pas, Wezel odjazd, int pasDocelowy) {
		// Tutaj trzeba brac po uwage ze wyjazd jest brany pod uwage uwzgledniajac trasy w ramach
		// skrzyzowania a nie pasy wjazdowe
		if (odjazd != null && isPoprawnyNumerTrasy(pas) && odjazd.isPoprawnyNumerPasa(pasDocelowy)) {
			this.odjazd[pas] = new Polaczenie(odjazd, pasDocelowy);
		} else {
			Logger.warn("");
		}
	}
	
	/**
	 * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#getOdjazd(int)
	 */
	public Polaczenie getOdjazd(int pas) {
	    Polaczenie result = null;
	    if (isPoprawnyNumerTrasy(pas)) {
	        result = this.odjazd[pas];
	    }
	    return result;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Skrzyzowanie();
	}
	
	public void addGrupaSygnalizacyjna() {
		if (this.grupySygnalizacyjne == null) {
			this.grupySygnalizacyjne = new ArrayList<Sygnalizacja>();
		}
		this.grupySygnalizacyjne.add(new Sygnalizacja());
	}
	
	public void removeGrupaSygnalizacyja(int index) {
		this.grupySygnalizacyjne.remove(index);
	}
	
	public boolean grupaSygnalizacyjnaMoveUp(int index) {
		boolean result = false;
		if (index > 0 && index < this.grupySygnalizacyjne.size()) {
			Sygnalizacja tmp = this.grupySygnalizacyjne.get(index - 1);
			Sygnalizacja grupa = this.grupySygnalizacyjne.get(index);
			
			this.grupySygnalizacyjne.set(index - 1, grupa);
			this.grupySygnalizacyjne.set(index, tmp);
			result = true;
		}
		return result;
	}
	
	public boolean grupaSygnalizacyjnaMoveDown(int index) {
		boolean result = false;
		if (index >= 0 && index < this.grupySygnalizacyjne.size() - 1) {
			Sygnalizacja tmp = this.grupySygnalizacyjne.get(index + 1);
			Sygnalizacja grupa = this.grupySygnalizacyjne.get(index);
			
			this.grupySygnalizacyjne.set(index + 1, grupa);
			this.grupySygnalizacyjne.set(index, tmp);
			result = true;
		}
		return result;
	}

	public boolean isSygnalizacja() {
		return sygnalizacja;
	}

	public void setSygnalizacja(boolean sygnalizacja) {
		this.sygnalizacja = sygnalizacja;
	}

	public ArrayList<Sygnalizacja> getGrupySygnalizacyjne() {
		if (this.grupySygnalizacyjne == null) {
			this.grupySygnalizacyjne = new ArrayList<Sygnalizacja>();
		}
		return grupySygnalizacyjne;
	}
	
	public Sygnalizacja getGrupaSygnalizacyjna(int numer) {
		if (numer >= 0 && numer < this.grupySygnalizacyjne.size()) {
			return this.grupySygnalizacyjne.get(numer);
		} else {
			Logger.warn("NIepoprawny numer indeksu");
		}
		return null;
	}

	public Sygnalizacja getAktualnaGrupaSygnalizacyjna() {
		if (this.grupySygnalizacyjne == null) {
			this.grupySygnalizacyjne = new ArrayList<Sygnalizacja>();
		}
		
		Sygnalizacja result = null;
		if (this.grupySygnalizacyjne.size() > 0) {
			result = this.grupySygnalizacyjne.get(aktualnaGrupaSygnalizacyjna);
		}
		
		return result;
	}
}

	
