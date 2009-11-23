package pl.wroc.pwr.iis.traffic.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;

public final class Sygnalizacja implements Serializable {
	private static final long serialVersionUID = 612535131314801689L;
	
	private ArrayList<Integer> trasy = new ArrayList<Integer>();
	private int czasFazy = 50;
	
	private int minCzasFazy = 8;
	private int maxCzasFazy = 500;

	private String nazwa = "Grupa ";
	
// ------------------------------------------

	public void addTrasa(Integer numerTrasy) {
		if (!this.trasy.contains(numerTrasy)) {
			this.trasy.add(numerTrasy);
		}
	}
	
	public Integer getTrasa(int numerTrasy) {
		return this.trasy.get(numerTrasy);
	}
	
	public void removeTrasa(Integer numerTrasy) {
		this.trasy.remove(numerTrasy);
	}
	
	public boolean containsTrasa(Integer numerTrasy) {
		return this.trasy.contains(numerTrasy);
	}
	
	public int getTrasyCount() {
		return this.trasy.size();
	}

	public int getCzasFazy() {
		return czasFazy;
	}

	public void setCzasFazy(int czasFazy) {
		this.czasFazy = czasFazy;
	}

	public int getMinCzasFazy() {
		return minCzasFazy;
	}

	public void setMinCzasFazy(int minCzasFazy) {
		this.minCzasFazy = minCzasFazy;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public int getMaxCzasFazy() {
		return maxCzasFazy;
	}

	public void setMaxCzasFazy(int maxCzasFazy) {
		this.maxCzasFazy = maxCzasFazy;
	}
}
