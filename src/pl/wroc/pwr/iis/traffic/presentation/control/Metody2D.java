/*
 * StructureEditC.java
 * Created on 2005-11-08
 * Copyright 2004-2005 e-informatyka.pl. All rights reserved.
 */
package pl.wroc.pwr.iis.traffic.presentation.control;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import pl.wroc.pwr.iis.traffic.presentation.model.Paintable;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.model.WezelView;

/**
 * @author M.Stanek <mikol@e-informatyka.pl>
 */
public final class Metody2D {
    
    public final static float wspKiernkowy(PunktMapy p1, PunktMapy p2) {
        return wspKiernkowy(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
    /**
     * Oblicza wspolczynnik kierunkowy prostej przechodzacej przez punkty (x1, y1) oraz (x2,y2)
     * @return m dla danej prostej
     */
    public final static float wspKiernkowy(float x1, float y1, float x2, float y2) {
        return (y2 - y1) / (x2 - x1);
    }
    
    public final static float wspProstopadly(PunktMapy p1, PunktMapy p2) {
        return wspProstopadly(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
    /**
     * Zwraca wartosc wspolczynnika kierunkowego prostej prostopadlej do prostej przechodzacej 
     * przez punkty (x1, y1) oraz (x2, y2)
     * @return Wspolczynnik kierunkowy prostej prostopadlej
     */
    public final static float wspProstopadly(float x1, float y1, float x2, float y2) {
        return -(x2 - x1) / (y2 - y1);
    }
    
    public final static PunktMapy punktLewo(PunktMapy p, int d, float m1) {
        return punktLewo(p.getX(), p.getY(), d, m1);
    }
    
    public final static PunktMapy punktLewo(int x, int y, int d, float m1) {
        float x_s;
        float y_s; 
        float m2 = -1 / m1;
        
        if (m1 == Float.NEGATIVE_INFINITY) {
            x_s = x-d;
            y_s = y; 
        }else if (m1 == Float.POSITIVE_INFINITY) {
            x_s = x+d;
            y_s = y; 
        } else if (m2 == Float.NEGATIVE_INFINITY) {
            x_s = x;
            y_s = y-d;
        } else if (m2 == Float.POSITIVE_INFINITY){
            x_s = x;
            y_s = y-d;
        } else {
            x_s  = (float)(d*Math.sqrt(1+m1*m1)-x*(m2-m1))/(m1-m2);
            y_s  =  m2*(x_s-x)+y;
        } 

        return new PunktMapy((int)x_s, (int)y_s); 
    }
    
    public final static PunktMapy punktPrawo(PunktMapy p, int d, float m1) {
        return punktPrawo(p.getX(), p.getY(), d, m1);
    }
    
    public final static PunktMapy punktPrawo(int x, int y, int d, float m1) {
        float x_s = x-d;
        float y_s = y; 

        float m2 = -1 / m1;
        
        if (m1 == Float.NEGATIVE_INFINITY) {
            x_s = x+d;
            y_s = y; 
        }else if (m1 == Float.POSITIVE_INFINITY) {
            x_s = x-d;
            y_s = y; 
        } else if (m2 == Float.NEGATIVE_INFINITY) {
            x_s = x;
            y_s = y+d;
        } else if (m2 == Float.POSITIVE_INFINITY){
            x_s = x;
            y_s = y+d;
        } else {
            x_s  = (float)(d*Math.sqrt(1+m1*m1)+x*(m2-m1))/(m2-m1);
            y_s  =  m2*(x_s-x)+y;
        } 
        
        return new PunktMapy((int)x_s, (int)y_s); 
    }
    
    public final static PunktMapy punktPrzeciecia(float m1, PunktMapy p1, float m2, PunktMapy p2) {
        int x1 = p1.getX(); int y1 = p1.getY();
        int x2 = p2.getX(); int y2 = p2.getY();

        float x,y;
        
        if (m2 == Float.POSITIVE_INFINITY) { // Dla wartosci pionowych
            x = x2;
            y = m1 * x + y1 - m1 * x1;
        } else if (m2 == Float.NEGATIVE_INFINITY) {
            x = x2;
            y = m1 * x + y1 - m1 * x1;
        } else if (m1 == Float.POSITIVE_INFINITY) { // Dla wartosci pionowych
            x = x1;
            y = m2 * x + y2 - m2 * x2;
        } else if (m1 == Float.NEGATIVE_INFINITY) { // Dla wartosci pionowych
            x = x1;
            y = m2 * x + y2 - m2 * x2;
        }else {
            x = (y2 - m2 * x2 - y1 + m1 * x1) / (m1 - m2);
            y = (m2 * x + y2 - m2 * x2);
        }
        
        if (x == Float.NaN) {
            x = x1;
//            System.out.println("DrogaView.punktPrzeciecia()");
        }
        
        
        if (y == Float.NaN) {
            y = y1;
        }
//        System.out.println("DrogaView.punktPrzeciecia() x: " + x);
//        System.out.println("DrogaView.punktPrzeciecia() y: " + y);
//        
        return new PunktMapy((int)x, (int)y);
    }
    
    public static PunktMapy[] podzielKrzywaBezPunktowKrancowych(ArrayList<PunktMapy> listaPunktow, int gestosc) {
    	ArrayList<PunktMapy> tmp = obliczPunktyKrzywej(listaPunktow, gestosc);
        
        PunktMapy[] result = new PunktMapy[tmp.size()-1];
        for (int i = 0; i < result.length; i++) {
            result[i] = tmp.get(i);
        }
        
        return result;
    }
    public static PunktMapy[] podzielKrzywa(ArrayList<PunktMapy> listaPunktow, int gestosc) {
        ArrayList<PunktMapy> tmp = obliczPunktyKrzywej(listaPunktow, gestosc);
        
        PunktMapy[] result = new PunktMapy[tmp.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = tmp.get(i);
        }
        
//        System.out.println("DrogaView.podzielKrzywa() dlugosc: " + result[0].length);
        return result;
    }

	private static ArrayList<PunktMapy> obliczPunktyKrzywej(ArrayList<PunktMapy> listaPunktow, int gestosc) {
		ArrayList<PunktMapy> tmp = new ArrayList<PunktMapy>();
        
        for (int i = 1; i < listaPunktow.size(); i++) {
            
            PunktMapy p1 = listaPunktow.get(i-1);
            PunktMapy p2 = listaPunktow.get(i);

            int dlugosc = (int) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
            
            int podzial = dlugosc / gestosc;
            

            for (int m = podzial; m >0; m--) {
                float n = m - 1;
                float a = n / (podzial - n);

                int x = (int) Math.round((p2.getX() + a * p1.getX()) / (1 + a)); 
                int y = (int) Math.round((p2.getY() + a * p1.getY()) / (1 + a));
                
                tmp.add(new PunktMapy(x,y));
            }
        }
		return tmp;
	}
	
	/**
	 * Metoda moze sluzyc do sprawdzenia czy dany punkt nie zostal zaznaczony. 
	 */
	public final static boolean sasiedztwoPunktu (PunktMapy punkt, int x, int y) {
		if (punkt.getX() - WezelView.SASIEDZTWO_BADANIA_PRZECIECIA < x && 
			punkt.getX() + WezelView.SASIEDZTWO_BADANIA_PRZECIECIA > x &&
			punkt.getY() - WezelView.SASIEDZTWO_BADANIA_PRZECIECIA < y && 
			punkt.getY() + WezelView.SASIEDZTWO_BADANIA_PRZECIECIA > y) {
			return true;
		}
		return false;
	}
	
	/**
	 * Oblicza odleglosc dwoch punktow od siebie
	 */
	public static final int getOdleglosc(int x1, int y1, int x2, int y2) {
		return (int) Math.round(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
	}
	
	public static final int getOdleglosc(PunktMapy p1, PunktMapy p2) {
		return getOdleglosc(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	/**
	 * Metoda oblicza odleglosc pozioma pomiędzy dwoma punktami
	 */
	public final static int getDlugoscX(PunktMapy p1, PunktMapy p2) {
		return p2.getX() - p1.getX(); 
	}
	
	/**
	 * Metoda oblicza odleglosc pionowa pomiedzy dwoma punktami
	 */
	public final static int getDlugoscY(PunktMapy p1, PunktMapy p2) {
		return p2.getY() - p1.getY(); 
	}
	
	public final static ArrayList<Paintable> getZaznaczono(PunktMapy p1, PunktMapy p2, ArrayList<Paintable> obiekty) {
		ArrayList<Paintable> result = new ArrayList<Paintable>();
		Rectangle prostokat = getRectangle(p1, p2);
		boolean zawiera; 
		
		for (Paintable obiekt : obiekty) {
			PunktMapy[] punkty = obiekt.getPunktyEdycji();
			zawiera = true;
			for (PunktMapy punkt : punkty) {
				if (!prostokat.contains(punkt.x, punkt.y)) {
					zawiera = false;
					break;
				}
			}
			if (zawiera) {
				result.add(obiekt);
			}
		}
		
		return result;
	}

	public final static Rectangle getRectangle(PunktMapy p1, PunktMapy p2) {
		Rectangle result = null;
		if (p1 != null && p2 != null) {
			int startX, startY, szerokosc, wysokosc;
			
			if(p1.x < p2.x) {
				startX = p1.x;
				szerokosc = p2.x - p1.x;
			} else {
				startX = p2.x;
				szerokosc = p1.x - p2.x;
			}
			
			if(p1.y < p2.y) {
				startY = p1.y;
				wysokosc = p2.y - p1.y;
			} else {
				startY = p2.y;
				wysokosc = p1.y - p2.y;
			}
			
			result = new Rectangle(startX, startY, szerokosc, wysokosc);
		}
		return result;
	}
}
