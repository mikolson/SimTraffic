/*
 * Copyright 2005 Michal Stanek. All rights reserved.
 * Project name:    Magisterska
 * File in project: Generator.java
 * Creation date:   2005-12-08
 */
package pl.wroc.pwr.iis.traffic.domain.entity;

import java.util.ArrayList;
import java.util.Collection;

import pl.wroc.pwr.iis.logger.Logger;
import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;

/**
 * Generator nateżenia samochodów. Generator generuje pojazdy z pewnym prawdopodobienstwem
 * dla kazdego cyklu. Jezeli zostanie stwierdzona koniecznosc wygenerowania pojazdu generator
 * w nastepnej kolejnosci losuje typ pojazdu w zaleznosci od ustalonych typow prawdopodopienst pojawienia sie 
 * pojazdow okreslonych typow. 
 * 
 * Dugim zastosowaniem generatora jest analiza samochodow wyjezdzajacych z analizowanego obszaru ruchu. 
 * Generator pelni wowczas role wezla do ktorego trafiaja samochody i na podstawie ich parametrow 
 * dokonywana jest analiza.
 * @author Michał Stanek
 */
public class Generator extends WezelAbstract {
	private static final long serialVersionUID = -5614246474859855247L;

	/** Zmienna określa prawdopodobieństwo wygenerowania dowolnego pojazdu. */
    private int prawdopodobienstwoPojazdu = 40;
    
    /** Pojazdy jakie sa generowane przez generator */
    private ArrayList<Pojazd> pojazdyGenerowane = new ArrayList<Pojazd>();
    
    /** Prawdopodobienstwo wygenerowania konkretnego typu pojazdu */
    private ArrayList<Integer> prawdopodobienstwaPojazdow = new ArrayList<Integer>();
    
    /** Suma prawdopodobienst wygenerowania kazdego pojazdu */
    private float prawdopodobienstwoSumaryczne;
    
    /** Tablica pojazdow jakie zakonczyly swoja podroz na danym generatorze */
    private ArrayList<Pojazd> pojazdyOdebrane = new ArrayList<Pojazd>();
    
    public Object clone() throws CloneNotSupportedException {
    	Generator result = new Generator();
    	
    	result.prawdopodobienstwoPojazdu = this.prawdopodobienstwoPojazdu;
    	result.prawdopodobienstwaPojazdow.addAll((Collection<? extends Integer>) this.prawdopodobienstwaPojazdow.clone());
    	result.pojazdyGenerowane.addAll((Collection<? extends Pojazd>) this.pojazdyGenerowane.clone());
    	result.prawdopodobienstwoSumaryczne = this.prawdopodobienstwoSumaryczne;
    	
    	return result;
    }
    
//  -----------------------------------------------------------------------------
    
    /**
     * Tworzy domyslny generator ktory generuje cztery typu pojazdow z
     * okreslonymi prawdopodobienstwami. Ogolne prawdopodobienstwo wygenerowania
     * pojazdu wynosi:
     */
    public Generator() {
        addPrawdopodobienstwoPojazdu(100, new Pojazd(Pojazd.Typ.OSOBOWY));
        addPrawdopodobienstwoPojazdu(10, new Pojazd(Pojazd.Typ.CIEZAROWY));
        addPrawdopodobienstwoPojazdu(5, new Pojazd(Pojazd.Typ.AUTOBUS));
        addPrawdopodobienstwoPojazdu(1, new Pojazd(Pojazd.Typ.AUTOBUS_PRZEGUBOWY));
    }
    
    /**
     * Tworzy generator ktory domyslnie generuje cztery typy pojazdow. 
     * Ogolne prawdopodobienstwo wygenerowania pojazdu jest podane przez parametr. 
     * Prawdopodobienstwa wygenerowania poszczegolnych pojazdow wynosza odpowiednio:
     * 1.0  dla samochodu osobowego, 
     * 0.1  dla samochodu ciezarowego,
     * 0.05 dla autobusu, 
     * 0.01 dla autobusu przegubowego. 
     * @param prawdopodobienstwo
     */
    public Generator(int prawdopodobienstwo) {
    	this();
        setPrawdopodobienstwoPojazdu(prawdopodobienstwo);
    }

    /**
     * Ustawienie prawdopodobienstwa wygenerowania dowolnego pojazdu przez generator. 
     * @param prawdopodobienstwo
     */
    public void setPrawdopodobienstwoPojazdu(int prawdopodobienstwo) {
        if (prawdopodobienstwo <= 100 && prawdopodobienstwo >= 0) {
            this.prawdopodobienstwoPojazdu = prawdopodobienstwo;
        } else {
            Logger.warn("Blednie podane prawdopodobienstwo wygenerowania pojazdu (dozwolona liczba od 0.0 do 1.0)");
        }
    }
    
    /**
     * @return Zwraca ogolne prawdopodobienstwo wygenerowania przez generator
     *         jakiegos pojazdu ktory posiada na liscie generowanych pojazdow.
     */
    public int getPrawdopodobienstwoPojazdu() {
        return this.prawdopodobienstwoPojazdu;
    }
    
    /**
     * Dodaje pojazd do listy generowanych pojazdow. Dodanie pojazdu oznacza ze
     * generator bedzie bazowal na jego specyfikacji i klonowal w razie potrzeby
     * cechy przekazanego pojazdu. Prawdopodobienstwo oznacza, ze w przypadku
     * gdy generator ma wygenerowac pojazd. Bierze pod uwage sume
     * prawdopodobienstw kazdego pojazdu ze schematu. Losowana jest nastepnie
     * liczba z tego zakresu. Im pojazd ma wieksze prawdopodobienstwo tym jest
     * wieksza szansa, ze to wlasnie on zostanie stworzyny przez generator w
     * cyklu tworzenia pojazdu.
     * 
     * @param prawdopodobienstwo
     *            Prawdopodobienstwo z jakim bedzie tworzony przekazany typ
     *            pojazdu.
     * @param pojazd
     *            Schemat pojazdu jaki bedzie tworzony przez generator.
     */
    public void addPrawdopodobienstwoPojazdu(int prawdopodobienstwo, Pojazd pojazd) {
        if (prawdopodobienstwo >= 0 && pojazd != null) {
            this.prawdopodobienstwaPojazdow.add(prawdopodobienstwo);
            this.pojazdyGenerowane.add(pojazd);
            this.prawdopodobienstwoSumaryczne += prawdopodobienstwo;
        }
    }
    
    /**
     * Zmienia wartosc prawdopodobienstwa dla konkretnego typu pojazdu
     * @param numerPojazdu
     * @param prawdopodobienstwo
     */
    public void setPrawdopodobienstwoPojazdu(int numerPojazdu, int prawdopodobienstwo) {
        if (prawdopodobienstwo >= 0 && prawdopodobienstwo<= 100 && numerPojazdu < prawdopodobienstwaPojazdow.size()) {
            this.prawdopodobienstwaPojazdow.set(numerPojazdu, prawdopodobienstwo);
        }
    }
    
    
    /**
     * Pobranie prawdopodobienstwa konkretnego typu generowanego pojazdu.
     * 
     * @param numer
     *            Numer pojazdu ktorego prawdopodobienstwo chcemy uzyskac.
     * @return Prawdopodobienstwo wygenerowania konretnego pojazdu.
     */
    public int getPrawdopodobienstwoPojazdu(int numer) {
        int result = 0;
        if (numer >= 0 && numer < getIloscGenerowanychPojazdow()) {
            result = this.prawdopodobienstwaPojazdow.get(numer);
        } else {
            Logger.error("Bledny numer pojazdu.", this);
        }
        return result;
    }
    
    /**
     * @return Zwraca aktualna wartosc prawdopodobienstwa sumarycznego.
     */
    protected float getPrawdopodobienstwoSumaryczne() {
        return this.prawdopodobienstwoSumaryczne;
    }
    
    /**
     * @return Zwraca ilosc generowanych przez system typów pojazdów. 
     */
    public int getIloscGenerowanychPojazdow() {
        return this.pojazdyGenerowane.size();
    }
    

    /**
     * @return Returns the pojazdyOdebrane.
     */
    public ArrayList<Pojazd> getPojazdyOdebrane() {
        return this.pojazdyOdebrane;
    }
    
    /**
     * Dodaje nowy pojazd do listy pojazdow odebranych przez generator.
     * 
     * @param pojazd
     *            Odebrany pojazd do dodania do listy.
     */
    public void addPojazdOdebrany(Pojazd pojazd) {
        this.pojazdyOdebrane.add(pojazd);
    }

    /**
     * @param pojazdyOdebrane The pojazdyOdebrane to set.
     */
    public void setPojazdyOdebrane(ArrayList<Pojazd> pojazdyOdebrane) {
        this.pojazdyOdebrane = pojazdyOdebrane;
    }
    
    /**
     * @return Zwraca ilosc pojazdow odebranych przez generator. 
     */
    public int getIloscPojazdowOdebranych() {
        return this.pojazdyOdebrane.size();
    }

    /**
     * TODO comment, test
     * @return
     */
    public Pojazd generujPojazd() {
        Pojazd result = null;
        int losuj = (int) (Math.random() * 100);
        
        if (losuj < getPrawdopodobienstwoPojazdu()) {
            losuj = (int) (Math.random() * getPrawdopodobienstwoSumaryczne());
            
            int suma  = 0;
            for (int i = 0; i < this.pojazdyGenerowane.size(); i++) {
                suma += this.prawdopodobienstwaPojazdow.get(i);
                
                if (suma > losuj) {
                    try {
						result = (Pojazd) this.pojazdyGenerowane.get(i).clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
                    break; 
                }
            }
        }
        
        return result;
    }
    
    /* (non-Javadoc)
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#odlegloscPrzed(int, int)
     */
    public int getOdlegloscPrzed(int indeksPasa, int komorka) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#getMaxPredkoscPasa(int)
     */
    public int getMaxPredkoscPasa(int pas) {
        return Pojazd.MAXYMALNA_PREDKOSC_POJAZDOW;
    }

    /* (non-Javadoc)
     * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#getKierunekPasa(int)
     */
    public Kierunek getKierunekPasa(int numerPasa) {
        return Kierunek.PROSTO;
    }

	public boolean isMozliwaZmianaPasa(int indeksPasa, Kierunek kierunek) {
		return false;
	}

	public int getPredkoscPrzed(int indeksPasa, int komorka, int zakresWidocznosci) {
		return Pojazd.MAXYMALNA_PREDKOSC_POJAZDOW;
	}

	public int getPredkoscZa(int indeksPasa, int komorka, int zakresWidocznosci) {
		return 0;
	}

	public int getOdlegloscZa(int indeksPasa, int komorka) {
		return 0;
	}

	public int getPrzesuniecieWzgledemPasow(int pasBiezacy, int pasDocelowy, int komorka) {
		return 0;
	}

	public int getOdlegloscPrzedNaWjezdzie(int indeksPasa, int komorka, int zakresWidocznosci) {
		return Integer.MAX_VALUE;
	}

	public int getOdlegloscPrzed(int indeksPasa, int komorka, int zakresWidocznosci) {
		return Integer.MAX_VALUE;
	}

	public void setDojazd(int pas, Wezel dojazd, int pasDocelowy) {
		System.out.println("Generator.setDojazd(): NIE UZYWAJ TEJ METODY TYLKO ADD DOJAZD" );
	}

	public void setOdjazd(int pas, Wezel wyjazd, int pasDocelowy) {
		System.out.println("Generator.setDojazd(): NIE UZYWAJ TEJ METODY TYLKO ADD DOJAZD" );
	}

	public Polaczenie getDojazd(int pas) {
		return null;
	}

	public Polaczenie getOdjazd(int pas) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPoprawnyNumerPasa(int numerPasa) {
		// TODO Auto-generated method stub
		return true;
	}

	public void setIloscPasow(int iloscPasow) {
		// TODO Auto-generated method stub
	}

	public int getIloscPasow() {
		return 1;
	}

	public int getPrzesuniecieWzgledemPasow(int pas, Kierunek kierunekDocelowy, int komorka) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean setPojazdAt(int pas, int komorka, Pojazd pojazd) {
		return true;
	}

	public void wykonajRuch() {
		for (int i = 0; i < this.odjazd.length; i++) {
			Pojazd p = generujPojazd();
			
			if (p != null && this.odjazd[i] != null) {
				Wezel w = this.odjazd[i].wezel;
				int pas = this.odjazd[i].pas;
				
				w.setPojazdAt(pas, 0, p);
			}
		}
	}

	public void addDojazd(Wezel dojazd, int pasDocelowy) {
		super.addDojazd();
		this.dojazdy[this.dojazdy.length - 1] = new Polaczenie(dojazd, pasDocelowy);
		setIloscPasow(this.dojazdy.length);
	}

	public void addOdjazd(Wezel odjazd, int pasDocelowy) {
		super.addOdjazd();
		this.odjazd[this.odjazd.length - 1] = new Polaczenie(odjazd, pasDocelowy);
	}
}
