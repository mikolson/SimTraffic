package pl.wroc.pwr.iis.traffic.presentation.view;

import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;
import pl.wroc.pwr.iis.traffic.presentation.model.WezelView;

/**
 * Klasa reprezentuje polaczenie pomiędzy dwoma elementami
 * wizualnymi reprezentujacymi wezly sieci.
 * 
 * @author Michał Stanek
 */
public class PolaczenieView {
	public WezelView wezel;
	public int pas;
	
	public PolaczenieView(WezelView wezel, int pas) {
		this.wezel = wezel;
		this.pas = pas;
	}
	
	public void paint(GC g, PunktMapy start) {
		PunktMapy koniec = this.wezel.getPunktyWjazdowe()[this.pas];
				
		g.setLineWidth(2);
		g.setForeground(ResourceHelper.COLOR_BLUE);
		
		g.drawLine(start.getX(), start.getY(), koniec.getX(), koniec.getY());
		g.drawLine(start.getX()-2, start.getY(), koniec.getX(), koniec.getY());
		g.drawLine(start.getX()+2, start.getY(), koniec.getX(), koniec.getY());
		g.drawLine(start.getX()-4, start.getY(), koniec.getX(), koniec.getY());
		g.drawLine(start.getX()+4, start.getY(), koniec.getX(), koniec.getY());
		g.drawLine(start.getX(), start.getY()+2, koniec.getX(), koniec.getY());
		g.drawLine(start.getX(), start.getY()-2, koniec.getX(), koniec.getY());
		g.drawLine(start.getX(), start.getY()+4, koniec.getX(), koniec.getY());
		g.drawLine(start.getX(), start.getY()-4, koniec.getX(), koniec.getY());
	}
}
