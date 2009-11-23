package pl.wroc.pwr.iis.traffic.presentation.view.statyczne;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.eclipse.swt.graphics.GC;

import pl.wroc.pwr.iis.traffic.presentation.control.Metody2D;
import pl.wroc.pwr.iis.traffic.presentation.model.PunktMapy;

public final class Paint2D {
    /**
     * Rysuje krzywą łamaną której wierzchołkami są punkty przekazane przez
     * parametr.
     * 
     * @param g
     *            Kontekst graficzny
     * @param listaPunktow
     *            Lista wierzchołków dla krzywej łamanej
     */
    public final static void narysujKrzywa(GC g, ArrayList<PunktMapy> listaPunktow) {
        g.drawPolyline(getPunkty(listaPunktow));
    }
    
    public final static void narysujObszar(GC g, ArrayList<PunktMapy> listaPunktow, boolean tlo) {
    	int[] punkty = getPunktyPolaczone(listaPunktow);
    	if (tlo) g.fillPolygon(punkty);
    	g.drawPolygon(punkty);
    }
    
    public final static void narysujLinie(GC g, PunktMapy p1, PunktMapy p2) {
    	g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
    public final static void narysujOwal(GC g, PunktMapy p1, PunktMapy p2, boolean tlo) {
    	Rectangle rec = Metody2D.getRectangle(p1, p2);
    	if (tlo) {
    		g.fillOval(rec.x, rec.y, rec.width, rec.height);
    	}
    	g.drawOval(rec.x, rec.y, rec.width, rec.height);
    }
    
    public final static void narysujProstokat(GC g, PunktMapy p1, PunktMapy p2, boolean tlo) {
    	Rectangle rec = Metody2D.getRectangle(p1, p2);
    	if (tlo) {
    		g.fillRectangle(rec.x, rec.y, rec.width, rec.height);
    	}
    	g.drawRectangle(rec.x, rec.y, rec.width, rec.height);
    }
    
    
// -----------------------------------------------------------
// 
// -----------------------------------------------------------
    
    private final static int[] getPunkty(ArrayList<PunktMapy> listaPunktow) {
    	int[] punkty = new int[listaPunktow.size() * 2];
        for (int i = 0; i < listaPunktow.size(); i++) {
            punkty[2*i] = listaPunktow.get(i).getX();
            punkty[2*i + 1] = listaPunktow.get(i).getY();
        }
        return punkty;
    }
    
    private final static int[] getPunktyPolaczone(ArrayList<PunktMapy> listaPunktow) {
    	int[] punkty = new int[listaPunktow.size() * 2 + 4];
    	for (int i = 0; i < listaPunktow.size(); i++) {
    		punkty[2*i] = listaPunktow.get(i).getX();
    		punkty[2*i + 1] = listaPunktow.get(i).getY();
    	}
    	
    	punkty[punkty.length - 4] = listaPunktow.get(0).getX();
    	punkty[punkty.length - 3] = listaPunktow.get(0).getY();
    	punkty[punkty.length - 2] = listaPunktow.get(listaPunktow.size() - 1).getX();
    	punkty[punkty.length - 1] = listaPunktow.get(listaPunktow.size() - 1).getY();
    	
    	return punkty;
    }
}
