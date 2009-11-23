/*
 * GeneratorView.java
 * Created on 2005-11-08
 * Copyright 2004-2005 e-informatyka.pl. All rights reserved.
 */
package pl.wroc.pwr.iis.traffic.presentation.view;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.domain.entity.Generator;
import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;
import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;
import pl.wroc.pwr.iis.traffic.presentation.model.WezelView;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.PolaczenieView;

/**
 * @author M.Stanek <mikol@e-informatyka.pl>
 */
public class GeneratorView implements WezelView{
	private static final long serialVersionUID = 4961094809579428374L;

	public final static float R_INNER = 0.65f;
    
    public final static Color INNER_COLOR = ResourceHelper.getColor(0,100,0);
    public final static Color OUTER_COLOR = ResourceHelper.COLOR_BLACK;
    
    /** Określa zrodlowy generator z warstwy danych */
    private Generator generator = new Generator();
    
    
    private ArrayList<PolaczenieView> odjazdy = new ArrayList<PolaczenieView>();
    private ArrayList<PolaczenieView> dojazdy = new ArrayList<PolaczenieView>();
    
    /** Określa położenie widoku danego generatora w obrebie mapy. */
    private PunktMapy srodek;
    private PunktMapy promien;
    
	transient private Ellipse2D kolo;

	public Object clone() throws CloneNotSupportedException {
		GeneratorView result = new GeneratorView(this.srodek);
		result.srodek = (PunktMapy) this.srodek.clone();
		result.promien = (PunktMapy) this.promien.clone();
		result.generator = (Generator) this.generator.clone();
		
		return result;
	}

    
    public GeneratorView(PunktMapy punktMapy) {
    	this.srodek = punktMapy;
    	this.promien = new PunktMapy(srodek.getX()+1, srodek.getY());
    }
    
    public GeneratorView(int x, int y) {
    	this(new PunktMapy(x, y));
    }
    
    /**
     * @param punktMapy The punktMapy to set.
     */
    public void setPunktMapy(PunktMapy punktMapy) {
        this.srodek = punktMapy;
    }

	public void paintComponent(GC g, int skala) {
		rysujOdjazdy(g);
		
		final int odleglosc = Metody2D.getOdleglosc(srodek, promien) * skala;
		final int promien = Math.round(odleglosc * R_INNER * skala);

		g.setLineWidth(0);
		g.setLineStyle(SWT.LINE_SOLID);
        g.setForeground(OUTER_COLOR);
        g.drawOval(
                srodek.getX() * skala - odleglosc,
                srodek.getY() * skala - odleglosc,
                odleglosc * 2, odleglosc * 2
        );
        g.drawOval(
        		srodek.getX() * skala - promien,
        		srodek.getY() * skala - promien ,
        		promien * 2, promien * 2
        );	
        
        g.setForeground(INNER_COLOR);
        g.setBackground(INNER_COLOR);
        
        g.fillOval(
                srodek.getX() * skala - promien+1,
                srodek.getY() * skala - promien+1,
                promien * 2-1, promien * 2-1
        );		
	}
	
	public void paintDynamic(GC g, int skala) {
		// nie rob nic
	}

	private void rysujOdjazdy(GC g) {
    	for (int i = 0; i < this.odjazdy.size(); i++) {
			if (this.odjazdy.get(i) != null) {
				this.odjazdy.get(i).paint(g, this.srodek);
			}
		}
	}
	
	public PunktMapy[] getPunktyEdycji() {
		PunktMapy[] punkty = new PunktMapy[] {srodek, promien};
		return punkty;
	}

	public boolean isZaznaczono(int x, int y) {
		if (kolo == null)
			updateData();
		return kolo.contains(x, y);
	}

	public void setOdjazd(int numerPasa, WezelView wezelDocelowy, int pasDocelowy) {
		setOdjazd(numerPasa, new PolaczenieView(wezelDocelowy, pasDocelowy));
	}
	
	public void setOdjazd(int numerPasa, PolaczenieView odjazd) {
		this.odjazdy.add(odjazd);
		this.generator.addOdjazd(odjazd.wezel.getWezel(), odjazd.pas);
	}
	
	public void setDojazd(int numerPasa, PolaczenieView dojazd) {
		this.dojazdy.add(dojazd);
		this.generator.addDojazd(dojazd.wezel.getWezel(), dojazd.pas);
	}

	public PolaczenieView getOdjazd(int numerPasa) {
		return null;
	}

	public Wezel getWezel() {
		return this.generator;
	}
	
	public Generator getGenerator() {
		return this.generator;
	}

	public PunktMapy getWspolrzedna(int numerPasa, int dlugosc) {
		return this.srodek;
	}

	public PunktMapy getWspolrzednaWjazdu(int numerPasa, int dlugosc) {
		return this.srodek;
	}

	public boolean isPoprawnyNumerPasa(int numerPasa) {
		return true;
	}

	public PunktMapy[] getPunktyWjazdowe() {
		return new PunktMapy[]{this.srodek};
	}

	public PunktMapy[] getPunktyWyjazdowe() {
		return new PunktMapy[]{this.srodek};
	}

	public void przesunCalosc(int deltaX, int deltaY) {
		this.srodek.setX(this.srodek.getX() + deltaX);
		this.srodek.setY(this.srodek.getY() + deltaY);
		
		this.promien.setX(this.promien.getX() + deltaX);
		this.promien.setY(this.promien.getY() + deltaY);
		
		updateData();
	}

	public void updateData() {
		final int promien = Metody2D.getOdleglosc(this.srodek, this.promien) + SASIEDZTWO_BADANIA_PRZECIECIA;
		kolo = new Ellipse2D.Float(this.srodek.getX() - promien, 
								   this.srodek.getY() - promien, 
								   promien * 2, promien * 2);
	}

	public void setOstatniPunkt(int x, int y) {
		this.promien.setXY(x, y);
		updateData();
	}
	
	public void setOstatniPunkt(PunktMapy punkt) {
		if (punkt != null) {
			this.promien = punkt;
		}
	}

	public void addOstatniPunkt(int x, int y) {
		setOstatniPunkt(x, y);
	}
	
	public void wykonajRuch() {
		this.generator.wykonajRuch();
	}

	public void delOstatniPunkt() {
		// pusta
	}
	public void delete() {
		// pusta
	}
	public void delDojazd(int numerDojazdu) {
		// pusta
	}
	public void delOdjazd(int numerOdjazdu) {
		// pusta
	}
	public void dodajPunkt(int x, int y) {
		// pusta
	}
	public void usunPunkt(int x, int y) {
		// pusta
	}
}
