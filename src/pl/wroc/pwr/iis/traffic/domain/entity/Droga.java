/*
 * Copyright 2005 Michal Stanek. All rights reserved.
 * Project name: 	Magisterska
 * File in project: Droga.java
 * Creation date: 	2005-12-03
 */
package pl.wroc.pwr.iis.traffic.domain.entity;

import pl.wroc.pwr.iis.logger.Logger;

/**
 * TODO
 *  1. Uwzglednic modelowanie linii ciaglej w systemie.
 *  2. Uwzględnić modelowanie różnych zakończeń oraz początków dróg.
 *  
 * @author M.Stanek <mikol@e-informatyka.pl>
 */
public class Droga extends WezelAbstract {
	private static final long serialVersionUID = -189683038646511659L;

	public final static int ODLEGLOSC_W_METRACH_MIEDZY_PASAMI  = 3;
	
    public final static int DOMYSLNA_ILOSC_PASOW  = 2;
    public final static int DOMYSLNA_MAX_PREDKOSC = 50;
    
    /** Samochody znajdujace sie na kazdym pasie. */
    private Pojazd[][] pojazdy = new Pojazd[0][0];
    private Pojazd[][] pojazdyTmp = new Pojazd[0][0];
    /** Określa po którym pasie następuje zmiana kierunku ruchu. */
    private int 		 numerPierwszegoPasaLewego = 1;
    
    /** Wartosci maksymalnej predkosci dla kazdego pasa. */
    private int[]		 maxPredkosc = new int[0];
    
    /**
     * Stworzenie obiektu drogi
     */
    public Droga() {
        super();
    
        setIloscPasow(DOMYSLNA_ILOSC_PASOW);
        setKierunekPasa(1);
    }
    
    /**
     * @return Zwraca dlugosc drogi wg dlugosci pasa o indeksie 0
     */
    public int getDlugosc() {
        int result = 0;
        
        if (this.pojazdy.length > 0) {
            result = this.pojazdy[0].length;
        }
        
        return result;
    }
    
    /**
     * @param numerPasa Numer pasa ktorego dlugosc chcemy uzyskac
     * @return Zwraca dlugosc konkretnego pasa jezdni
     */
    public int getDlugosc(int numerPasa) {
        int result = 0;
        
        if (this.pojazdy.length > 0 && numerPasa < this.pojazdy.length) {
            result = this.pojazdy[numerPasa].length;
        }
        
        return result;
    }


    /**
	 * Metoda ustawia nowa długość drogi. Jednoszesnie wszystkie obiekty jakie
	 * znajduja sie na drodze zostaja z niej usuniete. Dlugosc drogi podawana
	 * jest w metrach. Przy czym wielkosc komorki sieci odpowiada dokladnie
	 * jednemu metrowi. Wszystkie pasy drogi beda posiadac ta sama dlugosc. 
	 * 
	 * @param dlugoscDrogi
	 *            Nowa dlugosc drogi. Dlugosc podana w metrach.
	 */
    public void setDlugoscDrogi(int dlugoscDrogi) {
        this.pojazdy = new Pojazd[getIloscPasow()][dlugoscDrogi];
        this.pojazdyTmp = new Pojazd[getIloscPasow()][dlugoscDrogi];
    }
    
    /**
     * Ustawia nowa dlugosc dla konkretnego pasa drogi. Wszystkie obiekty
     * znajdujące sie na tym pasie zostaną usunięte. Metoda pozwala ustalić
     * niezależna długość dla każdego pasa z osobna 
     * 
     * TODO TEST
     * 
     * @param pasy
     *            Indeks pasa, którego długość chcemy ustawić
     * @param dlugoscDrogi
     *            Nowa długość pasa.
     */
    public void setDlugoscDrogi(int pas, int dlugoscDrogi) {
        if (isPoprawnyNumerPasa(pas) && dlugoscDrogi >= 0) {
            this.pojazdy[pas] = new Pojazd[dlugoscDrogi];
            this.pojazdyTmp[pas] = new Pojazd[dlugoscDrogi];
        } else {
            Logger.warn("Niepoprawne dane przy probie ustawienia pasa: " + pas +" : " + dlugoscDrogi,this);
        }
    }

    /**
	 * Zwraca maksymalna predkosc doswolona dla danego pasa ruchu.
	 * 
	 * @param pasy
	 *            O ktory pasy ruchu nam chodzi.
	 * @return Maksymalna predkosc na pasie.
	 */
    public int getMaxPredkoscPasa(int pas) {
        int result = -1;
        if (isPoprawnyNumerPasa(pas)) {
            result = this.maxPredkosc[pas];
        }
        return result;
    }
    
    /**
	 * Ustawienie maksymalnej predkosci dla wszystkich pasow jednoczesnie.
	 * 
	 * @param maxPredkosc
	 *            Nowa maksymalna predkosc
	 */
    public void setMaxPredkoscWszystkichPasow(int maxPredkosc) {
        for (int i = 0; i < this.maxPredkosc.length; i++) {
            this.maxPredkosc[i] = maxPredkosc;
        }
    }
    
    /**
     * @return Zwraca ilość pasów na drodze. 
     */
    public int getIloscPasow() {
        return this.pojazdy.length;
    }
    
    public int getIloscPasowPrawych() {
        return this.numerPierwszegoPasaLewego;
    }
    
    public int getIloscPasowLewych() {
        return getIloscPasow() - getIloscPasowPrawych();
    }

    /**
	 * Zwraca kierunek wybranego pasa.
	 * 
	 * @param numerPasa
	 *            Indeks pasa którego kierunek chcemt sprawdzić
	 * @return Kierunek wskazanego pasa ruchu
	 */
    public Kierunek getKierunekPasa(int numerPasa) {
        Kierunek result = null;
        if (isPoprawnyNumerPasa(numerPasa)) {
            if (numerPasa >= this.numerPierwszegoPasaLewego) {
                result = Kierunek.LEWY;
            } else {
                result = Kierunek.PRAWY;
            }
        }
            
        return result;
    }
    
    /**
     * Ustawia linie podziału pomiędzy kierunkami pasów. Linia podziału mówi, że
     * wszystkie pasy o numerze mniejszym od niej mają kierunek prawy a
     * wszystkie pasy o numerze wyższym mają kierunek lewy.
     * 
     * @param numerPierwszegoPasaLewego
     *            Nowa linia podziału kierunków. Wartość parametru powinna
     *            określać numer pierwszego pasa drogi który jest pasem lewym.
     */
    public void setKierunekPasa(int numerPierwszegoPasaLewego) {
    	if (numerPierwszegoPasaLewego >= 0 && numerPierwszegoPasaLewego <= this.pojazdy.length) {
    		this.numerPierwszegoPasaLewego = numerPierwszegoPasaLewego;
    	}
    }
    
    /**
	 * TODO : zastanowić się czy uwzględniać tutaj preferowany dystans między
	 * obiektami.
	 * 
	 * Sprawdza czy dany samochód może wjechac na pasy ruchu.
	 * 
	 * @param numerPasa
	 *            Numer pasa ruchu na jaki chcemy wjechac
	 * @param samochod
	 *            Samochód jaki chce wjechac na pasy ruchu.
	 * @return True jezeli udało umieścić sie samochód na danym pasie.
	 */
//    protected boolean czyMoznaWjechacNaPas(int numerPasa, Pojazd samochod) {
//        int dlugoscSamochodu = samochod.getDlugosc();
//        boolean result = true;
//        
//        for (int i = 0; i <= dlugoscSamochodu; i++) {
//           if (getZawartosc(numerPasa, i) != null) {
//               result = false;
//               break;
//           }
//        }
//        
//        return result;
//    }
    
    /**
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#isPoprawnyNumerPasa(int)
     */
    public boolean isPoprawnyNumerPasa(int numerPasa) {
        boolean result = false; 
        if (numerPasa >= 0 && numerPasa < getIloscPasow()) {
            result = true;
        } else {
            Logger.warn("Bledny numer pasa: " + numerPasa,this);
        }
        return result;
    }
    
    /**
     * Pobiera indeks w tablicy drogi dla konkretnej odleglosci. Metoda
     * ta jest potrzebna w momencie kiedy operujemy na pasach drogi prowadzacych w roznych
     * kierunkach. Poniewaz wewnetrzna reprezentacja samochodow na drodze 
     * jest w postaci tablicy. Samochody na tych samych odleglosciach ale na 
     * pasach w roznych kierunkach maja inne indeksy. 
     * @param numerPasa 
     * @param odleglosc
     * @return
     */
    public int getIndeks(int numerPasa, int odleglosc) {
        if (odleglosc > getDlugosc(numerPasa)) {
            Logger.error("Droga : odleglosc nie moze byc wieksza niz dlugosc drogi");
        }
        
        int result = odleglosc;
        if (numerPasa >= 0 && numerPasa < getIloscPasow()) {
            if (getKierunekPasa(numerPasa).equals(Kierunek.LEWY) ) {
                result = getDlugosc(numerPasa) - 1 - odleglosc;
            }
        } else {
            Logger.warn("Blednie podany pasy ruchu");
        }
        
        return result;
    }
    
    /**
     * Zwraca zawartosc pasa ruchu na podanej dlugosci
     * @param numerPasa
     * @param odleglosc
     * @return
     */
    protected Pojazd getZawartosc(int numerPasa, int odleglosc) {
        Pojazd result = null;
        
//        if (poprawnoscPasa(numerPasa)) {
            int indeks = getIndeks(numerPasa, odleglosc);
            result = this.pojazdy[numerPasa][indeks];
//        }
        
        return result;
    }

    /**
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#setIloscPasow(int)
     */
    public void setIloscPasow(int iloscPasow) {
        if (iloscPasow >=0) {
        	super.setIloscPasow(iloscPasow);
        	int dlugosc = getDlugosc();
        	
        	this.pojazdy = new Pojazd[iloscPasow][dlugosc];
        	this.pojazdyTmp = new Pojazd[iloscPasow][dlugosc];
        	this.numerPierwszegoPasaLewego = iloscPasow / 2;
        	this.maxPredkosc = new int[iloscPasow];
        	setMaxPredkoscWszystkichPasow(Pojazd.MAXYMALNA_PREDKOSC_POJAZDOW);
        }
    }

    /**
	 * Zwraca liczbe samochodow znajdujacych sie na danym pasie.
	 * 
	 * @param numerPasa
	 *            Numer pasa ruchu na jakim chcemy zbadać liczbę samochodów. 
	 * @return Ilość samochodów znajdujących się na podanym przez parametr pasie
	 *         ruchu.
	 */
    public int getIloscSamochodowNaPasie(int numerPasa) {
        int result = 0;
        if (isPoprawnyNumerPasa(numerPasa)) {
            for (int i = 0; i < this.pojazdy[numerPasa].length; i++) {
                if (this.pojazdy[numerPasa][i] != null) {
                    result++;
                }
            }
        }
        return result;
    }
    
    /**
     * TODO comment test
     * @param numerPasa
     * @param numerKomorki
     * @return
     */
    public Pojazd getZawartoscKomorki(int numerPasa, int numerKomorki) {
        Pojazd result = null;
        if (isPoprawnyNumerPasa(numerPasa) && numerKomorki >= 0 && numerKomorki < getDlugosc(numerPasa)) {
            result = this.pojazdy[numerPasa][numerKomorki];
        }
        return result;
    }
    

    protected int getPrzesuniecieWzgledemPasow(int pasBiezacy, int pasDocelowy, int komorka) {
    	if (pasBiezacy == pasDocelowy){ // zeby przyspieszyc kod 
    		return komorka;
    	}
    	
		int result = 0;
    	if (isPoprawnyNumerPasa(pasBiezacy) && isPoprawnyNumerPasa(pasDocelowy) && komorka >=0 && komorka <= getDlugosc(pasBiezacy)) {
    		result = Math.round((((float)komorka / (float)getDlugosc(pasBiezacy)) * (float)getDlugosc(pasDocelowy)));
    	} else {
    		Logger.warn("getPrzesuniecieWzgledemPasow: Bledne parametry!");
    	}
    	
    	return result;
    }
    

	/**
	 * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#getPrzesuniecieWzgledemPasow(int, pl.wroc.pwr.iis.traffic.domain.entity.Kierunek, int)
	 */
	public int getPrzesuniecieWzgledemPasow(int pas, Kierunek kierunekDocelowy, int komorka) {
		return getPrzesuniecieWzgledemPasow(pas, kierunekDocelowy.zmienPas(pas), komorka);
	}

    
    /**
     * @param pasy
     * @param komorka
     */
    protected void przesunSamochod(int pas, int komorka, Pojazd[][] wynik) {
        Pojazd p = this.pojazdy[pas][komorka];
        
        if (p != null) {
            int przesuniecie = (int) p.getPredkosc();

            int pasDocelowy = p.getKierunek().zmienPas(pas);
            
            int komorkaDocelowa = getPrzesuniecieWzgledemPasow(pas, pasDocelowy, komorka) + przesuniecie; // Numer komorki docelowej;            
            if (pasDocelowy != pas) {
            	komorkaDocelowa =  (int) Math.round(Math.sqrt(komorkaDocelowa * komorkaDocelowa + ODLEGLOSC_W_METRACH_MIEDZY_PASAMI * ODLEGLOSC_W_METRACH_MIEDZY_PASAMI));
            } 
            
            if (komorkaDocelowa < getDlugosc(pasDocelowy) && komorkaDocelowa >= 0) {  
            	// Poruszanie się w obrębie danej drogi
            	wynik[pas][komorka] = null;
                wynik[pasDocelowy][komorkaDocelowa] = p;
                p.przesun(pasDocelowy, komorkaDocelowa, przesuniecie); // Tutaj przesuwanie jest uzależnione od tego co sie dzieje w tej metodzie
            } else {
            	// Wjezdzanie na inny pas ruchu
            	Polaczenie cel = getOdjazd(pasDocelowy);						
            	komorkaDocelowa = komorkaDocelowa - getDlugosc(pasDocelowy) - 1; // Na jaka komorke nalezy wstawic dany pojazd -1bo indesy zaczynaja sie od 0
            	if (cel != null) {
            		wynik[pas][komorka] = null;
	          		if (!cel.wezel.setPojazdAt(cel.pas, 0, p)) {
//          			if (!cel.wezel.setPojazdAt(cel.pas, komorkaDocelowa, p)) {
	          			Logger.warn("Nie udało się umieścić pojazdu na nowej drodze!", this);
	          		}
	          		p.przesun(przesuniecie);
            	} else {
            		Logger.warn("Pas nie ma zdefiniowanego wyjazdu dla pasa drogi o numerze: " + pas, this);
            	}
            }
        }
    }
    
    /**
	 * Dodaje samochod na wyznaczny pasy drogi. Jezeli samochodu nie mozna bylo
	 * dodac (nie bylo wystarczajacej ilosci wolnego miejsca zeby wjechal),
	 * metoda zwraca false. Jezeli samochod wjechal prawidlowo na droge
	 * 
	 * @param numerPasa
	 * @param pojazd
	 * @return
	 */
    public boolean addPojazd(int numerPasa, Pojazd pojazd) {	
    	return setPojazdAt(numerPasa, 0, pojazd);
    }
    
    /**
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#setPojazdAt(int, int, pl.wroc.pwr.iis.traffic.domain.entity.Pojazd)
     */
    public boolean setPojazdAt(int pas, int komorka, Pojazd pojazd) {
    	boolean result = false;
    	
    	if (pojazd != null && isPoprawnyNumerPasa(pas) 
    			&& komorka >=0 && komorka + pojazd.getDlugosc() < getDlugosc(pas)) {
    		
    		result = true;
    		
    		for (int i = komorka; i < komorka + pojazd.getDlugosc(); i++) {
				if (this.pojazdy[pas][i] != null) {
					result = false;
//					Logger.warn("Proba wstawienia samochodu na zajete pole");
				}
			}
    		
    		if (result) {
    			this.pojazdy[pas][komorka] = pojazd;
    			pojazd.setDroga(this, pas, komorka);
    		}
    	} 
    	
    	return result;
    }

    /**
     * @return Zwraca dlugosc najdluzszego pasa na drodze
     */
    private int getMaxDlugosc() {
    	int max = getDlugosc(0);
    	for (int i = 1; i < getIloscPasow(); i++) {
			if (getDlugosc(i) > max) {
				max = getDlugosc(i);
			}
		}
    	return max;
    }
    
    /**
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Entitable#wykonajRuch()
     */
    public void wykonajRuch() {
//        this.pojazdyTmp = new Pojazd[getIloscPasow()][];
//        for (int i = 0; i < pojazdyTmp.length; i++) {
//            this.pojazdyTmp[i] = new Pojazd[this.pojazdy[i].length];
//        }
        
        int dlugosc = getMaxDlugosc();
        for (int j = dlugosc - 1; j >= 0; j--) { // po komorkach pasow od konca
        	for (int i = 0; i < getIloscPasow(); i++) { // po wszystkich pasach
                    if (j < this.pojazdy[i].length && this.pojazdy[i][j] != null) {
                        this.pojazdy[i][j].wykonajRuch();
                        przesunSamochod(i, j, this.pojazdy);
                    }
                }
        }
        
    }
    
    /**
     * Zwraca jaka jest odległość do najbliższego samochodu począwszy od danej
     * komórki na pasie.
     * 
     * @param indeksPasa
     *            Numer pasa na jakim chcemy badać odległość do najbliższego
     *            pojazdu.
     * @param komorka
     *            Komórka pasa od której interesuje nas liczenie odległości.
     */
    public int getOdlegloscPrzed(int indeksPasa, int komorka, int zakresWidocznosci) {
    	int result = 0;
        if (isPoprawnyNumerPasa(indeksPasa) && komorka >= 0 && komorka < getDlugosc(indeksPasa)) {
			for (int i = komorka; i < getDlugosc(indeksPasa); i++) {
				if (this.pojazdy[indeksPasa][i] != null) {
					return result;
				}
				result++;
				
				if (zakresWidocznosci-- == 0) {
					break; // nie ma sensu dalej badac
				}
			}
			
			// Wyjechano za dany pas
				int dodac = getOdlegloscPolaczenia(indeksPasa, 0, zakresWidocznosci);
				result = (dodac == BRAK_POJAZDOW_W_ZAKRESIE_WIDOCZNOSCI) ? BRAK_POJAZDOW_W_ZAKRESIE_WIDOCZNOSCI : result + dodac;
		} else {
			result = getOdlegloscPolaczenia(indeksPasa, komorka - getDlugosc(indeksPasa), zakresWidocznosci);
		}
		return result;
    }
    
    private int getOdlegloscPolaczenia(int indeksPasa, int komorka, int zakresWidocznosci) {
    	int result = 0;
    	if (zakresWidocznosci > 0) {
    		Polaczenie p = getOdjazd(indeksPasa);
    		if (p != null) {
    			result = p.wezel.getOdlegloscPrzedNaWjezdzie(p.pas, komorka, zakresWidocznosci);
    		}
    	} else {
    		result = BRAK_POJAZDOW_W_ZAKRESIE_WIDOCZNOSCI;
    	}
		return result;
    }
    
    /**
	 * Zwraca jaka jest odległość do najbliższego samochodu znajdującego się z
	 * tyłu począwszy od danej komórki na pasie.
	 * 
	 * @param indeksPasa
	 *            Numer pasa na jakim chcemy badać odległość do najbliższego
	 *            pojazdu.
	 * @param komorka
	 *            Komórka pasa od której interesuje nas liczenie odległości.
	 */
    public int getOdlegloscZa(int indeksPasa, int komorka) {
    	int result = 0;
        if (isPoprawnyNumerPasa(indeksPasa) && komorka >= 0 && komorka < getDlugosc(indeksPasa)) {
			for (int i = komorka - 1; i >= 0; i--) {
				if (this.pojazdy[indeksPasa][i] != null) {
					return result - this.pojazdy[indeksPasa][i].getDlugosc();
				}
				result++;
			}
		}
		return result;
    }
    
    /**
     * COMMENT ME
     * @param indeksPasa
     * @param komorka
     * @param zakresWidocznosci
     * @return
     */
    public int getPredkoscPrzed(int indeksPasa, int komorka, int zakresWidocznosci) {
        if (isPoprawnyNumerPasa(indeksPasa) && komorka >= 0 && komorka < getDlugosc(indeksPasa)) {
			for (int i = 1; i + komorka < getDlugosc(indeksPasa) && i  <= zakresWidocznosci; i++) {
				if (this.pojazdy[indeksPasa][komorka + i] != null) {
					return (int) this.pojazdy[indeksPasa][komorka + i].getPredkosc();
				}
			}
		}

		return Integer.MAX_VALUE;
    }
    
    /**
     * TODO COMMENT ME
     * @param indeksPasa
     * @param komorka
     * @param zakresWidocznosci
     * @return
     */
    public int getPredkoscZa(int indeksPasa, int komorka, int zakresWidocznosci) {
        if (isPoprawnyNumerPasa(indeksPasa) && komorka >= 0 && komorka < getDlugosc(indeksPasa)) {
			for (int i = 1; komorka - i >=  0  && i  <= zakresWidocznosci; i++) {
				if (this.pojazdy[indeksPasa][komorka - i] != null) {
					return (int) this.pojazdy[indeksPasa][komorka - i].getPredkosc();
				}
			}
		}

		return 0;
    }
    
    /**
     * Sprawdza czy mozna zmienic pasy na inny z krokiem jeden w podanym kierunku
     * @param indeksPasa
     * @return
     */
    public boolean isMozliwaZmianaPasa(int indeksPasa, Kierunek kierunek) {
    	boolean result = false;
    	if (isPoprawnyNumerPasa(indeksPasa) && kierunek != null) {
    		if (kierunek.equals(Kierunek.PROSTO)) {
    			result = true;
    		} else if (kierunek.equals(Kierunek.PRAWY)) {
    			if (indeksPasa < getIloscPasowPrawych() && indeksPasa > 0) {
    				result = true;
    			} else if (indeksPasa >= getIloscPasowPrawych() + 1) {
    				result = true;
    			}
    		} else if (kierunek.equals(Kierunek.LEWY)) {
    			if (indeksPasa < getIloscPasowPrawych() - 1) {
    				result = true;
    			} else if (indeksPasa >= getIloscPasowPrawych() && indeksPasa < getIloscPasow()-1) {
    				result = true;
    			}
    		}
    	} else {
    		Logger.warn("isZmianaPasa: bledne parametry");
    	}
    	return result;
    }
    
    /**
	 * @see java.lang.Object#toString()
	 */
    public String toString() {
        StringBuffer wynik = new StringBuffer();
        for (int i = 0; i < getIloscPasow(); i++) {
            
            if (getKierunekPasa(i).equals(Kierunek.LEWY)) {
                wynik.append("<- ");
            } else {
                wynik.append("-> ");
            }
            
            for (int j = 0; j < getDlugosc(i); j++) {
                if (this.pojazdy[i][j] != null) {
                    wynik.append("X");
                } else {
                    wynik.append("-");
                }
            }
            wynik.append("\n");
        }
        return wynik.toString();
    }

	public int getOdlegloscPrzedNaWjezdzie(int indeksPasa, int komorka, int zakresWidocznosci) {
		return getOdlegloscPrzed(indeksPasa, komorka, zakresWidocznosci);
	}
	
	/**
     * Pomija dojazdy i odjzazdy w czasie kopiowania
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException {
    	Droga result = new Droga();
    	result.pojazdy = new Pojazd[this.pojazdy.length][];
    	result.pojazdyTmp = new Pojazd[this.pojazdyTmp.length][];
    	result.maxPredkosc = new int[this.maxPredkosc.length];
    	
    	for (int i = 0; i < result.pojazdy.length; i++) {
			result.pojazdy[i] = new Pojazd[getDlugosc(i)];
			result.pojazdyTmp[i] = new Pojazd[getDlugosc(i)];
			result.maxPredkosc[i] = this.maxPredkosc[i];
		}
    	
    	return result;
    }
}
