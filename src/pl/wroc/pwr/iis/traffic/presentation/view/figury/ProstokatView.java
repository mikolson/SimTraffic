package pl.wroc.pwr.iis.traffic.presentation.view.figury;

import java.awt.Rectangle;

import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.Paint2D;

public class ProstokatView extends FiguraView {
	private static final long serialVersionUID = -7019312506043028463L;
	
	private static final int MINIMALNA_DLUGOSC_BOKU = 10;
	//	int x1, y1, x2, y2;		// Wspolrzedne koncow lini
	protected PunktMapy p1, p2;
	protected transient Rectangle protokat;
	
//	int sx1, sx2, sy1, sy2; // Przeskalowane wspolrzedne
//	int lastSkala= -1;		// Ostatnio uzyta skala
	
	public ProstokatView(PunktMapy punkt) {
		p1 = punkt;
		p2 = new PunktMapy(punkt.x + MINIMALNA_DLUGOSC_BOKU, punkt.y + MINIMALNA_DLUGOSC_BOKU);
		updateData();
	}
	public ProstokatView(int x, int y) {
		p1 = new PunktMapy(x, y);
		p2 = new PunktMapy(x + MINIMALNA_DLUGOSC_BOKU, y + MINIMALNA_DLUGOSC_BOKU);
		updateData();
	}
	
	public void paintComponent(GC g, int skala) {
		setPaintProperties(g);
		Paint2D.narysujProstokat(g, p1, p2, isRysujTlo());
	}

	public PunktMapy[] getPunktyEdycji() {
		return new PunktMapy[]{p1, p2};
	}

	public void setOstatniPunkt(int x, int y) {
//		if (x - p1.getX() >= MINIMALNA_DLUGOSC_BOKU &&
//			y - p1.getY() >= MINIMALNA_DLUGOSC_BOKU) {
			this.p2.setXY(x, y);
			updateData();
//		}
	}
	
	public void setOstatniPunkt(PunktMapy punkt) {
		if (punkt != null){
			this.p2 = punkt;
			updateData();
		}
	}
	
	public void addOstatniPunkt(int x, int y) {
		setOstatniPunkt(x, y);
	}

	public boolean isZaznaczono(int x, int y) {
		if (protokat == null) 
			updateData();
		
		boolean result = this.protokat.contains(x, y);
		
		if (result == false) {
			result = Metody2D.sasiedztwoPunktu(p1, x, y) | Metody2D.sasiedztwoPunktu(p2, x, y);
		}
		return result;
	}

	public void przesunCalosc(int deltaX, int deltaY) {
		p1.move(deltaX, deltaY);
		p2.move(deltaX, deltaY);
		updateData();
	}

	public void dodajPunkt(int x, int y) {
		//
	}
	
	public void usunPunkt(int x, int y) {
		//
	}

	public void updateData() {
		this.protokat = Metody2D.getRectangle(p1, p2); 
	}

	public void delOstatniPunkt() {
		//
	}
	public void delete() {
		// TODO Auto-generated method stub
	}
	
	public Object clone() throws CloneNotSupportedException {
		ProstokatView result = new ProstokatView(this.p1);
		clone(result);
		
		result.p1 = (PunktMapy) this.p1.clone();
		result.p2 = (PunktMapy) this.p2.clone();
		
		return result;
	}
}
