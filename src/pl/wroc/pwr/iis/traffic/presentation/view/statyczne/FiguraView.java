package pl.wroc.pwr.iis.traffic.presentation.view.statyczne;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.presentation.model.Paintable;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;

public abstract class FiguraView implements Paintable {
	
	Color kolorLini = ResourceHelper.COLOR_BLACK;	// Kolor linii
	int szerokoscLini = 0;								// Grubosc linii
	int stylLinii = SWT.LINE_SOLID;				// Rodzaj kreski

	Color kolorTla = ResourceHelper.COLOR_LIGHT_GRAY;	// Kolor linii
	
	protected void setPaintProperties(GC g) {
		g.setLineStyle(stylLinii);
		g.setLineWidth(szerokoscLini);
		
		g.setForeground(kolorLini);
		g.setBackground(kolorTla);
	}

	public Color getKolorLini() {
		return kolorLini;
	}

	public void setKolorLini(Color kolorLini) {
		this.kolorLini = kolorLini;
	}

	public Color getKolorTla() {
		return kolorTla;
	}

	public void setKolorTla(Color kolorTla) {
		this.kolorTla = kolorTla;
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
}
