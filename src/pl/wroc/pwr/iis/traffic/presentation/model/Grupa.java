package pl.wroc.pwr.iis.traffic.presentation.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.swt.graphics.GC;

/**
 * @author Michał Stanek
 */
public class Grupa implements Serializable, Cloneable, Paintable {
	private static final long serialVersionUID = -5555683087468989802L;
	
	private ArrayList<Paintable> obiekty = new ArrayList<Paintable>();;
    
    public Grupa() {
    }

    
    public ArrayList<Paintable> getObiekty() {
        return this.obiekty;
    }

    public void setObiekty(ArrayList<Paintable> obiekty) {
        this.obiekty = obiekty;
    }
    
    public boolean addObiekt(Paintable obiekt) {
    	if (obiekt != null) {
    		return this.obiekty.add(obiekt);
    	}
        return false; 
    }
    
    public void addObiekty(Grupa obiekty) {
    	for (Paintable obiekt : obiekty.obiekty) {
			addObiekt(obiekt);
		}
    }
    
    public void addObiekty(ArrayList<Paintable> obiekty) {
    	for (Paintable obiekt : obiekty) {
			addObiekt(obiekt);
		}
    }
    
    public boolean delObiekt(Paintable obiekt) {
        return this.obiekty.remove(obiekt);
    }
    
    public void delObiekt(ArrayList<Paintable> obiekty){
    	for (Paintable obiekt : obiekty) {
			delObiekt(obiekt);
		}
    }
    
    public void clear() {
    	this.obiekty.clear();
    }
    
    public Paintable getLast() {
    	return this.obiekty.get(this.obiekty.size() - 1);
    }
    
    /**
     * Dodaje obiekt jezeli nie ma go w kolekcji a usuwa jezeli juz jest
     */
    public void insObiekt(Paintable obiekt) {
    	if (this.obiekty.contains(obiekt)) {
    		this.obiekty.remove(obiekt);
    	} else {
    		this.obiekty.add(obiekt);
    	}
    }
    
    
    public void addSingle(Paintable obiekt) {
    	if (!this.obiekty.contains(obiekt)) {
    		addObiekt(obiekt);
    	}
    }
    
    public boolean contain(Paintable obiekt) {
    	return this.obiekty.contains(obiekt);
    }
    
    /**
     * Usuwa wszystkie obiekty w grupie i dodaje jeden obiekt do grupy.
     * @param obiekt Obiekt jaki bedzie w grupie
     */
    public void clearAdd(Paintable obiekt) {
    	this.obiekty.clear();
    	addObiekt(obiekt);
    }
    
    public boolean isNotEmpty() {
    	return (this.obiekty.size() > 0);
    }
    
//	-----------------------------------------------------------
//	Metody zarządzające kolejnościa wyświetlania obiektów 
//  -----------------------------------------------------------
    
    public void moveDown(ArrayList<Paintable> obiekty){
    	for (Paintable obiekt : obiekty) {
			moveDown(obiekt);
		}
    }
    public void moveDown(Paintable obiekt) {
    	for (int i = 1; i < this.obiekty.size(); i++) {
    		if (this.obiekty.get(i).equals(obiekt)) {
    			this.obiekty.set(i, this.obiekty.get(i-1));
    			this.obiekty.set(i-1, obiekt);
    			break;
    		}
		}
    }
    
    public void moveUp(ArrayList<Paintable> obiekty){
    	for (Paintable obiekt : obiekty) {
			moveUp(obiekt);
		}
    }
    
    public void moveUp(Paintable obiekt) {
    	for (int i = 0; i < this.obiekty.size() - 1; i++) {
    		if (this.obiekty.get(i).equals(obiekt)) {
    			this.obiekty.set(i, this.obiekty.get(i+1));
    			this.obiekty.set(i+1, obiekt);
    			break;
    		}
		}
    }
    
    public void moveLast(ArrayList<Paintable> obiekty){
    	for (Paintable obiekt : obiekty) {
			moveLast(obiekt);
		}
    }
    public void moveLast(Paintable obiekt) {
    	for (int i = 1; i < this.obiekty.size(); i++) {
    		if (this.obiekty.get(i).equals(obiekt)) {
    			this.obiekty.set(i, this.obiekty.get(0));
    			this.obiekty.set(0, obiekt);
    		}
    	}
    }
    
    public void moveFirst(ArrayList<Paintable> obiekty){
    	for (Paintable obiekt : obiekty) {
			moveFirst(obiekt);
		}
    }
    public void moveFirst(Paintable obiekt) {
    	int ostatniElement = this.obiekty.size() - 1;
		for (int i = 0; i < ostatniElement; i++) {
    		if (this.obiekty.get(i).equals(obiekt)) {
    			this.obiekty.set(i, this.obiekty.get(ostatniElement));
    			this.obiekty.set(ostatniElement, obiekt);
    		}
    	}
    }
    
//	-----------------------------------------------------------
//	Metody potrzebna do kopiowania i wklejania obiektów 
//  -----------------------------------------------------------
    
    public Object clone() throws CloneNotSupportedException {
    	Grupa result = new Grupa();
    	
    	for (int i = 0; i < this.obiekty.size(); i++) {
			result.addObiekt((Paintable) this.obiekty.get(i).clone());
		}
    	
    	return result;
    }

//	-----------------------------------------------------------
//	Metody potrzebne aby można było wykorzystać grupowanie 
//  -----------------------------------------------------------
    
	public void paintComponent(GC g, int skala) {
		for (int i = 0; i < this.obiekty.size(); i++) {
			this.obiekty.get(i).paintComponent(g, skala);
		}
	}
	
	public void paintDynamic(GC g, int skala) {
		for (int i = 0; i < this.obiekty.size(); i++) {
			this.obiekty.get(i).paintDynamic(g, skala);
		}
	}

	public PunktMapy[] getPunktyEdycji() {
		ArrayList<PunktMapy> punktyEdycji = new ArrayList<PunktMapy>();
		
		for (int i = 0; i < this.obiekty.size(); i++) {
			PunktMapy[] punkty = this.obiekty.get(i).getPunktyEdycji();
			
			for (PunktMapy punkt : punkty) {
				punktyEdycji.add(punkt);
			}
		}
		
		PunktMapy[] result = new PunktMapy[punktyEdycji.size()];
		punktyEdycji.toArray(result);
		
		return result;
	}

	public boolean isZaznaczono(int x, int y) {
		for (Paintable obiekt : this.obiekty) {
			if (obiekt.isZaznaczono(x, y)) {
				return true;
			}
		}
		return false;
	}

	public void przesunCalosc(int deltaX, int deltaY) {
		for (Paintable obiekt : this.obiekty) {
			obiekt.przesunCalosc(deltaX, deltaY);
		}
	}

	public void updateData() {
		for (Paintable obiekt : this.obiekty) {
			obiekt.updateData();
		}
	}

	public void delete() {
		for (Paintable obiekt : this.obiekty) {
			obiekt.delete();
		}
	}
	
	public void setOstatniPunkt(int x, int y) { /* nic */ }
	public void setOstatniPunkt(PunktMapy punkt) { /* nic */ }
	public void addOstatniPunkt(int x, int y) { /* nic */ }
	public void delOstatniPunkt() { /* nic */ }
	public void dodajPunkt(int x, int y) { /* nic */ }
	public void usunPunkt(int x, int y) { /* nic */ }
	
	public void groupObjects(Grupa obiektyGrupy) {
		Grupa grupa = new Grupa();
		
		// Dodanie obiektów w takiej kolejności w jakiej 
		// wystepowaly one wczesniej na planszy
		for (int i = 0; i < this.obiekty.size(); i++) {
			for (int j = 0; j < obiektyGrupy.obiekty.size(); j++) {
				if (this.obiekty.get(i) == obiektyGrupy.obiekty.get(j) ) {
					grupa.addObiekt(obiektyGrupy.obiekty.get(j));
				}
			}
		}
		delObiekt(obiektyGrupy.obiekty);
		
		addObiekt(grupa);
	}
	
	public void ungroupObjects(Paintable grupaObiektow) {
		if (grupaObiektow instanceof Grupa) {
			delObiekt((Grupa) grupaObiektow);
			addObiekty((Grupa) grupaObiektow);
		}
	}
	
	public void ungroupObjects(ArrayList<Paintable> grupaObiektow) {
		for (Paintable obiekt : grupaObiektow) {
			ungroupObjects(obiekt);
		}
	}
}
