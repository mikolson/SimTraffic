package pl.wroc.pwr.iis.traffic.presentation.view.figury;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.Paint2D;

public class ElipsaView extends ProstokatView {
	private static final long serialVersionUID = -213817181497579193L;
	
	transient protected Ellipse2D obszar;
	
	public ElipsaView(PunktMapy punkt) {
		super(punkt);
	}
	
	public ElipsaView(int x, int y) {
		super(x,y);
	}
	public void paintComponent(GC g, int skala) {
		setPaintProperties(g);
		
		Paint2D.narysujOwal(g, p1, p2, isRysujTlo());
	}
	
	public boolean isZaznaczono(int x, int y) {
		if (obszar == null)
			updateData();
		
		boolean result = obszar.contains(x, y);
		
		if (result == false) {
			result = Metody2D.sasiedztwoPunktu(p1, x, y) | Metody2D.sasiedztwoPunktu(p2, x, y);
		}
		return result;
	}
	public void updateData() {
		Rectangle rec = Metody2D.getRectangle(p1,p2);
		obszar = new Ellipse2D.Float(rec.x, rec.y, rec.width, rec.height);
	}
	
	public Object clone() throws CloneNotSupportedException {
		ElipsaView result = new ElipsaView(this.p1);
		clone(result);
		
		result.p1 = (PunktMapy) this.p1.clone();
		result.p2 = (PunktMapy) this.p2.clone();
		
		return result;
	}
}
