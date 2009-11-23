/**
 * 
 */
package pl.wroc.pwr.iis.traffic.presentation.view.figury;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;

/**
 * @author Michał Stanek 
 *
 */
public class Obrazek extends ProstokatView {
	private static final long serialVersionUID = -2441968404863250911L;
	private static final int MINIMALNA_DLUGOSC_BOKU = 150;
	protected String imagePath =  "d:/test.jpg"; //null;//
	protected transient Image image = null;
	
	public Obrazek(PunktMapy punkt) {
		super(punkt);
		p2 = new PunktMapy(punkt.x + MINIMALNA_DLUGOSC_BOKU, punkt.y + MINIMALNA_DLUGOSC_BOKU);
		updateData();
		przywrocRozmiar();
	}
	
	public Obrazek(int x, int y) {
		super(x, y);
		p2 = new PunktMapy(x + MINIMALNA_DLUGOSC_BOKU, y + MINIMALNA_DLUGOSC_BOKU);
		updateData();
		przywrocRozmiar();
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
		image = ResourceHelper.getImage(imagePath);
		przywrocRozmiar();
	}

	public void przywrocRozmiar() {
		p2.x = p1.x + image.getBounds().width;
		p2.y = p1.y + image.getBounds().height;
		
		updateData();
	}
	
	public void naprawWspolrzedne() {
		if (p2.x <= p1.x) {
			p2.x = p1.x + 1;
		}
		if (p2.y <= p1.y) {
			p2.y = p1.y + 1;
		}
	}

	public void paintComponent(GC g, int skala) {
		naprawWspolrzedne();
		super.paintComponent(g, skala);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		if (image != null) 
			g.drawImage(image, 
				0, 0, image.getBounds().width, image.getBounds().height, 
				p1.x, p1.y, Metody2D.getDlugoscX(p1, p2), Metody2D.getDlugoscY(p1, p2)
			);
	}

	public boolean isRysujTlo() {
		return false;
	}
	
	public boolean isMozliweTlo() {
		return false;
	}
	
	public void updateData() {
		naprawWspolrzedne();
		
		if (imagePath != null && image == null) {
			image = ResourceHelper.getImage(imagePath);
		}
		super.updateData();
	}
}
