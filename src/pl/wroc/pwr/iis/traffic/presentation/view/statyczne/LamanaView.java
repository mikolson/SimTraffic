package pl.wroc.pwr.iis.traffic.presentation.view.statyczne;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.view.Paint2D;

public class LamanaView extends FiguraView {
//	int x1, y1, x2, y2;		// Wspolrzedne koncow lini
	protected ArrayList<PunktMapy> punkty = new ArrayList<PunktMapy>();
	protected Line2D[] linia = new Line2D[0];
//	int sx1, sx2, sy1, sy2; // Przeskalowane wspolrzedne
//	int lastSkala= -1;		// Ostatnio uzyta skala
	
	boolean first = true; // True jezeli bedzie dodawany pierwszy punkt				
	
	public LamanaView(PunktMapy punkt) {
		addOstatniPunkt(punkt);
		addOstatniPunkt( new PunktMapy(punkt) );
		updateData();
	}
	
	public LamanaView(int x, int y) {
		addOstatniPunkt(x,y);
		addOstatniPunkt(x,y);
		updateData();
	}
	
	public void paintComponent(GC g, int skala) {
		setPaintProperties(g);
		Paint2D.narysujKrzywa(g, punkty);
	}

	public PunktMapy[] getPunktyEdycji() {
		PunktMapy[] punkty = new PunktMapy[this.punkty.size()];
		this.punkty.toArray(punkty);
		return punkty;
	}

	public void setOstatniPunkt(int x, int y) {
		 this.punkty.get(this.punkty.size() - 1).setXY(x, y);
		 updateData();
	}

	public void setOstatniPunkt(PunktMapy punkt) {
		if (punkt != null) {
			this.punkty.set(this.punkty.size() - 1, punkt);
		}
	}
	
	public void addOstatniPunkt(int x, int y) {
		addOstatniPunkt(new PunktMapy(x, y));
	}
	
	public void addOstatniPunkt(PunktMapy punkt) {
		if (punkt != null) {
			this.punkty.add(punkt);
		}
	}

	public boolean isZaznaczono(int x, int y) {
		for (Line2D l : this.linia) {
			if (l.intersects(x-SASIEDZTWO_BADANIA_PRZECIECIA, 
					 y-SASIEDZTWO_BADANIA_PRZECIECIA,
					 SASIEDZTWO_BADANIA_PRZECIECIA *2,
					 SASIEDZTWO_BADANIA_PRZECIECIA *2)) {
				return true;
			}
		}
		return false;
	}

	public void przesynCalosc(int deltaX, int deltaY) {
		for (PunktMapy punkt : this.punkty) {
			punkt.move(deltaX, deltaY);
		}
		updateData();
	}

	public void dodajPunkt(int x, int y) {
		//
	}

	public void usunPunkt(int x, int y) {
		//
	}

	public void updateData() {
		if (this.punkty.size() >= 2) {
			this.linia = new Line2D[this.punkty.size() - 1];
			
			PunktMapy p1, p2;
			int j = 0;
			for (int i = 1; i < this.punkty.size(); i++) {
				p1 = this.punkty.get(i - 1);
				p2 = this.punkty.get(i);
				
				this.linia[j++] = new Line2D.Float(p1.getX(), p1.getY(), p2.getX(), p2.getY());
			}
		}
	}


	public void delOstatniPunkt() {
		if (this.punkty.size() > 2) {
			this.punkty.remove(this.punkty.size() - 1);
			updateData();
		}
	}
}
