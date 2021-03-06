package pl.wroc.pwr.iis.traffic.presentation.view.figury;

import java.awt.geom.Line2D;

import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;

public class LiniaView extends FiguraView {
	private static final long serialVersionUID = 1229521713152376124L;
	
	//	int x1, y1, x2, y2;		// Wspolrzedne koncow lini
	protected PunktMapy p1, p2;
	transient protected Line2D linia;
//	int sx1, sx2, sy1, sy2; // Przeskalowane wspolrzedne
//	int lastSkala= -1;		// Ostatnio uzyta skala
	
	boolean first = true; // True jezeli bedzie dodawany pierwszy punkt				
	
	public LiniaView(PunktMapy punkt) {
		p1 = punkt;
		p2 = new PunktMapy(punkt);
		
		updateData();
	}
	public LiniaView(int x, int y) {
		p1 = new PunktMapy(x, y);
		p2 = new PunktMapy(x, y);
		updateData();
	}
	
	public void paintComponent(GC g, int skala) {
		setPaintProperties(g);
		g.drawLine(
				p1.getX() * skala,
				p1.getY() * skala,
				p2.getX() * skala,
				p2.getY() * skala);
	}

	public PunktMapy[] getPunktyEdycji() {
		return new PunktMapy[]{p1, p2};
	}

	public void setOstatniPunkt(int x, int y) {
		 this.p2.setXY(x, y);
		 updateData();
	}
	
	public void setOstatniPunkt(PunktMapy punkt) {
		if (punkt != null) {
			this.p2 = punkt;
			updateData();
		}
	}

	
	public void addOstatniPunkt(int x, int y) {
		setOstatniPunkt(x, y);
	}

	public boolean isZaznaczono(int x, int y) {
		if (this.linia == null) 
			updateData();
		return linia.intersects(x-SASIEDZTWO_BADANIA_PRZECIECIA, 
						 y-SASIEDZTWO_BADANIA_PRZECIECIA,
						 SASIEDZTWO_BADANIA_PRZECIECIA *2,
						 SASIEDZTWO_BADANIA_PRZECIECIA *2);
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
		this.linia = new Line2D.Float(p1.getX(), p1.getY(), p2.getX(), p2.getY()); 
	}

	public void delOstatniPunkt() {
		//
	}
	public void delete() {
		// TODO Auto-generated method stub
	}
	
	public boolean isRysujTlo() {
		return false;
	}
	
	public boolean isMozliweTlo() {
		return false;
	}
	
	public Object clone() throws CloneNotSupportedException {
		LiniaView result = new LiniaView(this.p1);
		
		clone(result);
		result.p1 = (PunktMapy) this.p1.clone();
		result.p2 = (PunktMapy) this.p2.clone();
		
		return result;
	}

}
