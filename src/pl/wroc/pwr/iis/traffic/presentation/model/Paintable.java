/*
 * Paintable.java
 * Created on 2005-11-08
 * Copyright 2004-2005 e-informatyka.pl. All rights reserved.
 */
package pl.wroc.pwr.iis.traffic.presentation.model;

import java.io.Serializable;

import org.eclipse.swt.graphics.GC;

/**
 * @author M.Stanek <mikol@e-informatyka.pl>
 */
public interface Paintable extends Serializable, Cloneable {
	int SASIEDZTWO_BADANIA_PRZECIECIA = 3;
	
    void paintComponent(GC g, int skala);
    void paintDynamic(GC g, int skala);

    PunktMapy[] getPunktyEdycji();
    
    void setOstatniPunkt(int x, int y);
    void setOstatniPunkt(PunktMapy punkt);
    
    /**
     * Dodaje do obiektu nowy punkt 
     * @param x
     * @param y
     */
    void addOstatniPunkt(int x, int y);
    
    /**
	 * Motoda wywolywana kiedy nastąpiło usunięcie ostatniego punktu obiektu.
	 * Np. w trakcie edycji można zrezygnować z wybranego ostatnio punktu.
	 */
    void delOstatniPunkt();

	boolean isZaznaczono(int x, int y);

	/**
	 * Dodaje nowy punkt 
	 * @param x
	 * @param y
	 */
	void dodajPunkt(int x, int y);

	/**
	 * Usuwa punkt 
	 * @param x
	 * @param y
	 */
	void usunPunkt(int x, int y);
	
	/**
	 * Przesuwa caly obiekt
	 */
	void przesunCalosc(int deltaX, int deltaY);
	
	void updateData();
	
	/**
	 * Metoda wywolywana w momencie kiedy obiekt jest kasowany
	 */
	void delete();
	
	public Object clone() throws CloneNotSupportedException;
}
