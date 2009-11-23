package pl.wroc.pwr.iis.traffic.presentation.view.statyczne;

import java.awt.geom.Ellipse2D;

import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.view.Paint2D;

public class ElipsaView extends ProstokatView {
	protected Ellipse2D obszar;
	
	public ElipsaView(PunktMapy punkt) {
		super(punkt);
	}
	
	public ElipsaView(int x, int y) {
		super(x,y);
	}
	public void paintComponent(GC g, int skala) {
		setPaintProperties(g);
		
		Paint2D.narysujOwal(g, p1, p2);
	}
	
	public boolean isZaznaczono(int x, int y) {
		boolean result = obszar.contains(x, y);
		
		if (result == false) {
			result = Metody2D.sasiedztwoPunktu(p1, x, y) | Metody2D.sasiedztwoPunktu(p2, x, y);
		}
		return result;
	}
	public void updateData() {
		obszar = new Ellipse2D.Float(p1.getX(), p1.getY(), Metody2D.getDlugoscX(p1, p2), Metody2D.getDlugoscY(p1, p2));
	}
}
