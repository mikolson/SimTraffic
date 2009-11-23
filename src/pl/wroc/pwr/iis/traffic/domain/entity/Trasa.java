/**
 * 
 */
package pl.wroc.pwr.iis.traffic.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;

import pl.wroc.pwr.iis.logger.Logger;

/**
 * @author michal
 *
 */
/**
 * Okresla trase jaka mozna przejechac dane skrzyzowanie.
 * @author michal
 */
public class Trasa implements Serializable {
	private static final long serialVersionUID = -6231922967129263749L;

	ArrayList<Pas> odcinki = new ArrayList<Pas>();
    
    protected int maxPredkosc;
    
    public Trasa(int maxPredkosc) {
        this.maxPredkosc = maxPredkosc;
    }
    
    /** TODO: zrobic natezenie w ramach trasy */
    float naterzenieRuchuTrasy;
    
    public int addOdcinek(Pas odcinek) {
        int result = 0;
        if (odcinek != null) {
            result = this.odcinki.size();
            this.odcinki.add(odcinek);
        } else {
            Logger.warn("Trasa.addOdcinek/Pas: bledne parametry!");
        }
        return result;
    }
    
    /**
     * Pobiera pierwszy odcinek trasy
     */
    public Pas getPierwszyOdcinek() {
        return this.odcinki.get(0);
    }
    
    /**
     * Pobiera pas trasy o wskazanym numerze
     * 
     * @param numerOdcinka
     *            Numer pasa w ramach trasy
     * @return Referencja na obiekt pasa
     */
    public Pas getOdcinek(int numerOdcinka) {
        Pas result = null;
        if (numerOdcinka >=0 && numerOdcinka < this.odcinki.size()) {
            result = this.odcinki.get(numerOdcinka);
        } 
        return result;
    }
    
    /**
     * @return Lista wszystkich pasow
     */
    public ArrayList<Pas> getPasy() {
        return this.odcinki;
    }
    
    public int getIloscOdcinkow() {
        return this.odcinki.size();
    }
    
    /**
     * Pobiera numer danego pasa w danej trasie. 
     * @param pas Referencja na pas trasy
     * @return numer jego w trasie
     */
    public int getNumerPasaWtrasie(Pas pas) {
        int result = 0; 
        for (int i = 0; i < this.odcinki.size(); i++) {
            if (pas.equals(this.odcinki.get(i))) {
                result = i;
                break;
            }
        }
        return result;
    }
   
    /**
     * @return Zwraca pas ktory odpowiada podanej odleglosci na trasie
     */
    public Pas getOdlegloscPas(int odleglosc) {
        Pas result = null;
        for (int i = 0; i < odcinki.size(); i++) {
            int dl = odcinki.get(i).getDlugosc();

            if (odleglosc < dl) {
            	result = odcinki.get(i);
                break;
            }
            odleglosc -= dl;
        }
        return result;
    }
    
    public int getDlugosc() {
    	int result = 0;
    	
    	for (int i = 0; i < this.odcinki.size(); i++) {
            result += this.odcinki.get(i).getDlugosc();
        }
    	
    	return result;
    }
    
    /**
     * @return Dla podanej odleglosci na trasie zwraca indeks komorki na konkretnym pasie
     */
    public int getOdlegloscIndeks(int odleglosc) {
        int result = 0;
        
        for (int i = 0; i < odcinki.size(); i++) {
            int dl = odcinki.get(i).getDlugosc();
            if (odleglosc < dl) {
                result = odleglosc;
                break;
            }
            odleglosc -= dl;
        }
        return result;
    }

	public int getMaxPredkosc() {
		return maxPredkosc;
	}

	public void setMaxPredkosc(int maxPredkosc) {
		this.maxPredkosc = maxPredkosc;
	}
}