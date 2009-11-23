/**
 * 
 */
package pl.wroc.pwr.iis.traffic.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;

import pl.wroc.pwr.iis.logger.Logger;
import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;

/**
 * @author Michal Stanek
 */
public class Pas implements Serializable{
	private static final long serialVersionUID = -1500726386979000940L;
	
	ArrayList<Pas> dojazdy = new ArrayList<Pas>();
    ArrayList<Pas> odjazdy = new ArrayList<Pas>();
    
    private Pojazd[] pojazdy = new Pojazd[0];
    private boolean przetworzony = false;
    
    public Pas(int dlugosc) {
        setDlugosc(dlugosc);
    }
    
    /**
     * @param odleglosc
     *            Podana odleglosc
     * @return Zwraca pojazd znajdujacy sie w danej odleglosci na pasie
     */
    public Pojazd getZawartosc(int odleglosc) {
        Pojazd result = null;
        if (odleglosc >= 0 && odleglosc < this.pojazdy.length) {
            result = this.pojazdy[odleglosc];
        } else {
            Logger.warn("Pas.getZawartosc: niepoprawny parametr" + odleglosc + " / " + getDlugosc() + "!");
        }
        return result;
    }
    
    public Pojazd getZawartoscFast(int odleglosc) {
    	return this.pojazdy[odleglosc];
    }
    
    public void setZawartosc(Pojazd pojazd, int odleglosc) {
        this.pojazdy[odleglosc] = pojazd;
    }
    
    public int getDlugosc() {
        return this.pojazdy.length;
    }
    
    public void setDlugosc(int dlugosc) {
        this.pojazdy = new Pojazd[dlugosc];
    }
    

    public void addDojazd(Pas pas) {
        if (pas != null) {
            this.dojazdy.add(pas);
        } else {
            Logger.warn("Pas.addDojazd: bledne parametry!!");
        }
    } 
    
    public void addOdjazd(Pas pas) {
        if (pas != null) {
            this.odjazdy.add(pas);
        } else {
            Logger.warn("Pas.addOdjazd: bledne parametry!!");
        }
    } 
    
    public Pas getOdjazd(int numer) {
        Pas result = null;
        if (numer >= 0 && numer < this.odjazdy.size()) {
            result = this.odjazdy.get(numer);
        } else {
            Logger.warn("Pas.getOdjazd: bledne parametry!!");
        }
        return result;
    }
    
    public Pas getDojazd(int numer) {
        Pas result = null;
        if (numer >= 0 && numer < this.odjazdy.size()) {
            result = this.odjazdy.get(numer);
        } else {
            Logger.warn("Pas.getOdjazd: bledne parametry!!");
        }
        return result;
    }
    
    public ArrayList<Pas> getDojazdy() {
    	return this.dojazdy;
    }
    
    public void clearOdjazdy() {
        this.odjazdy.clear();
    }
    
    public void clearDojazdy() {
        this.odjazdy.clear();
    }

    /**
     * @return Returns the przetworzony.
     */
    public boolean isPrzetworzony() {
        return this.przetworzony;
    }

    /**
     * @param przetworzony The przetworzony to set.
     */
    public void setPrzetworzony(boolean przetworzony) {
        this.przetworzony = przetworzony;
    }

    /**
     * Pobiera dystans do pierwszego samochodu poczynajac od komorki o podanym
     * indeksie.
     * 
     * @param komorka
     * @param widocznosc
     * @return Odleglosc do pojazdu znajdujacego sie na pasie. Integer.MaxValue
     *         jezeli w podanym przedziale widocznosci nie bylo pojazdu, -1
     *         jezeli nalezy szukac na kolejnych pasach.
     */
    public int getOdlegloscPrzed(int komorka, int widocznosc) {
        int result = 0;
        boolean znaleziono = false;
        if (komorka >= 0 && komorka < getDlugosc()) {
            for (int i = komorka; i < getDlugosc(); i++) {
                if (this.pojazdy[i] != null) {
                    znaleziono = true;
                    break; 
                }
                result++;
                
                if (--widocznosc < 0) {
                    return Wezel.BRAK_POJAZDOW_W_ZAKRESIE_WIDOCZNOSCI;
                }
            }
            

            if (widocznosc > 0 && !znaleziono) {
                result = -1;
            }
        } else {
            Logger.error("Pas.getOdlegloscPrzed() : bledny numer komorki");
        }
        return result;
    }
}