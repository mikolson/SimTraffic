package pl.wroc.pwr.iis.traffic.presentation.view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.logger.Logger;
import pl.wroc.pwr.iis.traffic.domain.entity.Pojazd;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;
import pl.wroc.pwr.iis.traffic.presentation.model.WezelView;

/**
 * @author Administrator
 *
 */
public class PojazdView {
    private static final Color COLOR_INNE = ResourceHelper.getColor(100,200,100);
	private static final Color COLOR_AUTOBUS = ResourceHelper.getColor(100,100,200);
	private static final Color COLOR_CIEZAROWY = ResourceHelper.getColor(200,100,200);
	private static final Color COLOR_OSOBOWY = ResourceHelper.getColor(200,200,200);
	
	public final static int DLUGOSC_SAMOCHODU = 10;
    public final static int SZEROKOSC_SAMOCHODU = DrogaView.SZEROKOSC_DROGI / 3;
    
    public static void paint(GC g, Pojazd pojazd, WezelView wezel) {
//        if (pojazd != null && punkty != null && pojazd.getKomorka() < punkty.length) {
    	if (pojazd != null && wezel != null) {
    		int p = pojazd.getPas();
    		int k1 = pojazd.getKomorka();
    		int k2 = pojazd.getKomorka() + pojazd.getDlugosc() - 1;
        	PunktMapy p1 = wezel.getWspolrzedna(p, k1); 
        	PunktMapy p2 = wezel.getWspolrzedna(p, k2); 
            
            if (pojazd.getTypPojazdu().equals(Pojazd.Typ.OSOBOWY)) {
                g.setForeground(COLOR_OSOBOWY);
            } else if (pojazd.getTypPojazdu().equals(Pojazd.Typ.CIEZAROWY)){
                g.setForeground(COLOR_CIEZAROWY);
            } else if (pojazd.getTypPojazdu().equals(Pojazd.Typ.AUTOBUS)){
                g.setForeground(COLOR_AUTOBUS);
            } else {
                g.setForeground(COLOR_INNE);
            }
            
            g.setLineStyle(SWT.LINE_SOLID);
            g.setLineWidth(SZEROKOSC_SAMOCHODU);
            g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            g.setForeground(ResourceHelper.COLOR_BLACK);
            g.drawPoint(p1.getX(), p1.getY());
            g.drawPoint(p2.getX(), p2.getY());
        } else {
        	Logger.warn("Niepoprawne parametry wywolania metody paint");
        }
    }
}
