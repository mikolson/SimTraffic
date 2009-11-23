package pl.wroc.pwr.iis.traffic.presentation.view.figury;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;

import pl.wroc.pwr.iis.traffic.presentation.model.Paintable;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;

public abstract class FiguraView implements Paintable, Serializable {
	
	transient protected Color kolorLini = ResourceHelper.COLOR_BLACK;	// Kolor linii
	protected RGB kolorLiniRGB = kolorLini.getRGB();
	
	protected int szerokoscLini = 0;								// Grubosc linii
	protected int stylLinii = SWT.LINE_SOLID;				// Rodzaj kreski

	protected boolean rysujTlo = true;
	transient protected Color kolorTla = ResourceHelper.COLOR_MEDIUM_DARK_GRAY;	// Kolor linii
	protected RGB kolorTlaRGB = kolorTla.getRGB();
	
	public void paintDynamic(GC g, int skala) {
		// Nie rob nic
	}
	
	protected void setPaintProperties(GC g) {
		if (kolorTla == null) 
			kolorTla = ResourceHelper.getColor(kolorTlaRGB);
		if (kolorLini == null) 
			kolorLini = ResourceHelper.getColor(kolorLiniRGB);
		
		g.setLineStyle(stylLinii);
		g.setLineWidth(szerokoscLini);
		
		g.setForeground(kolorLini);
		g.setBackground(kolorTla);
	}

	public Color getKolorLini() {
		return kolorLini;
	}

	public void setKolorLini(Color kolorLini) {
		if (kolorLini != null) {
			this.kolorLini = kolorLini;
			this.kolorLiniRGB = kolorLini.getRGB();
		}
	}

	public Color getKolorTla() {
		return kolorTla;
	}

	public void setKolorTla(Color kolorTla) {
		if (kolorTla != null) {
			this.kolorTla = kolorTla;
			this.kolorTlaRGB = kolorTla.getRGB();
		}
	}

	public int getStylLinii() {
		return stylLinii;
	}

	public void setStylLinii(int stylLinii) {
		this.stylLinii = stylLinii;
	}

	public int getSzerokoscLini() {
		return szerokoscLini;
	}

	public void setSzerokoscLini(int szerokoscLini) {
		this.szerokoscLini = szerokoscLini;
	}
	
	public void setRysujTlo(boolean rysujTlo) {
		this.rysujTlo = rysujTlo;
	}
	
	/**
	 * @return True jeżeli aktualnie rysowane jest tło figury
	 */
	public boolean isRysujTlo() {
		return this.rysujTlo;
	}
	
	/**
	 * @return True jezeli w figurze rysowane jest jakies tło i ustawianie jego
	 *         ma jakiś sens. False w przeciwnym razie.
	 */
	public boolean isMozliweTlo() {
		return true;
	}
	
	protected void clone(FiguraView figura) {
		figura.kolorLini = this.kolorLini;
		figura.kolorTla = this.kolorTla;
		figura.kolorLiniRGB = this.kolorLiniRGB;
		figura.kolorTlaRGB = this.kolorTlaRGB;
		
		figura.rysujTlo = this.rysujTlo;
		figura.stylLinii = this.stylLinii;
		figura.szerokoscLini = this.szerokoscLini;
	}
	
	public abstract Object clone() throws CloneNotSupportedException;
}
