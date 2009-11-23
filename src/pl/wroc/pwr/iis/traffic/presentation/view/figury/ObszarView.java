package pl.wroc.pwr.iis.traffic.presentation.view.figury;

import java.awt.Polygon;

import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.Paint2D;

public class ObszarView extends LamanaView {
	private static final long serialVersionUID = 3219259704893156219L;
	
	//	int x1, y1, x2, y2;		// Wspolrzedne koncow lini
	transient protected Polygon obszar = new Polygon();
//	int sx1, sx2, sy1, sy2; // Przeskalowane wspolrzedne
//	int lastSkala= -1;		// Ostatnio uzyta skala
	
	public ObszarView(PunktMapy punkt) {
		super(punkt);
//		delOstatniPunkt();
	}
	
	public ObszarView(int x, int y) {
		super(x, y);
//		delOstatniPunkt();
	}
	
	public void paintComponent(GC g, int skala) {
		setPaintProperties(g);
		Paint2D.narysujObszar(g, punkty, isRysujTlo());
	}

	public boolean isZaznaczono(int x, int y) {
		if (this.obszar == null)
			updateData();
		boolean result = this.obszar.contains(x, y);
		
		for (PunktMapy punkt : this.punkty) {
			result |= Metody2D.sasiedztwoPunktu(punkt, x, y);
		}
		
		return result;
	}

	public void updateData() {
		int[] pX = new int[this.punkty.size()];
		int[] pY = new int[this.punkty.size()];
		
		for (int i = 0; i < this.punkty.size(); i++) {
			pX[i] = this.punkty.get(i).getX();
			pY[i] = this.punkty.get(i).getY();
		}
		
		obszar = new Polygon(pX, pY, pX.length);
	}
	
	public boolean isRysujTlo() {
		return this.rysujTlo;
	}
	
	public Object clone() throws CloneNotSupportedException {
		ObszarView result = new ObszarView(punkty.get(0));
		clone(result); // przepisanie wlasciwosci tla i linii
		
		result.punkty.clear();
		for (int i = 0; i < this.punkty.size(); i++) {
			result.addOstatniPunkt((PunktMapy) punkty.get(i).clone());
		}
		
		return result;
	}
	
	@Override
	public boolean isMozliweTlo() {
		return true;
	}
}
