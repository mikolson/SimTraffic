/*
 * PunktMapy.java
 * Created on 2005-11-08
 * Copyright 2004-2005 e-informatyka.pl. All rights reserved.
 */
package pl.wroc.pwr.iis.traffic.presentation.model;

import java.io.Serializable;

/**
 * @author M.Stanek <mikol@e-informatyka.pl>
 */
public class PunktMapy implements Serializable, Cloneable {
	private static final int PRZESUNIECIE_PRZY_KOPIOWANIU = 10;

	private static final long serialVersionUID = 7657254009568237266L;
	
	public int x;
    public int y;
    
    public PunktMapy(PunktMapy punkt) {
        if (punkt != null) {
        	this.x = punkt.x;
        	this.y = punkt.y;
        }
    }
    
    public PunktMapy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setXY(int x, int y){
    	this.x = x;
    	this.y = y;
    }
    
    public void move(int deltaX, int deltaY) {
    	this.x += deltaX;
    	this.y += deltaY;
    }
    
    @Override
    public String toString() {
    	return "("+ x + ";" + y +")";
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(this == obj) return true;
    	if (this.getClass().equals(obj.getClass())) {
    		PunktMapy p = (PunktMapy) obj;
    		
    		if (p.x == this.x && p.y == this.y) return true;
    	}
    	return false;
    }
    
    public Object clone() throws CloneNotSupportedException {
    	return new PunktMapy(this.x + PRZESUNIECIE_PRZY_KOPIOWANIU, this.y + PRZESUNIECIE_PRZY_KOPIOWANIU);
    }
}
