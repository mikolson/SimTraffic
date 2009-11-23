package pl.wroc.pwr.iis.traffic.domain.entity;

import junit.framework.TestCase;

/**
 * @author Michał Stanek
 *
 */
public class PojazdTest extends TestCase {
    Pojazd p;
    
    protected void setUp() throws Exception {
        this.p = new Pojazd(Pojazd.Typ.OSOBOWY, new Kierowca());
    }
    
    public void testClone() throws Exception {
        Pojazd p2 = (Pojazd) this.p.clone();
        
        assertEquals(p2.getTypPojazdu(), this.p.getTypPojazdu());
        
        assertEquals(p2.getDlugosc(), this.p.getDlugosc());
        assertEquals(p2.getPrzyspieszenie(), this.p.getPrzyspieszenie());
        assertEquals(p2.getKierowca(), this.p.getKierowca());
        assertEquals(p2.getMaxPredkosc(), this.p.getMaxPredkosc());
        
        assertEquals(p2.getCzas(), 0);
        assertEquals(p2.getPrzejechaneJednostki(), 0);
        assertEquals(p2.getWezel(), null);
    }
    
    public void testAddCzas() throws Exception {
        assertEquals(0, this.p.getCzas());
        this.p.addCzas();
        assertEquals(1, this.p.getCzas());
    }
    
    public void testAddPrzejechaneJednostki() throws Exception {
        assertEquals(this.p.getPrzejechaneJednostki(), 0);
        this.p.addPrzejechaneJednostki(0);
        assertEquals(this.p.getPrzejechaneJednostki(), 0);
        this.p.addPrzejechaneJednostki(1);
        assertEquals(this.p.getPrzejechaneJednostki(), 1);
        this.p.addPrzejechaneJednostki(2);
        assertEquals(this.p.getPrzejechaneJednostki(), 3);
    }
    
    public void testPrzyspiesz() throws Exception {
        assertEquals(this.p.getPredkosc(), 0);
        this.p.przyspiesz();
        
        assertEquals(this.p.getPredkosc(), Math.round(this.p.getPrzyspieszenie()));
        
        this.p.przyspiesz();
        assertEquals(this.p.getPredkosc(), Math.round(2 * this.p.getPrzyspieszenie()));
    }
    
    public void testPrzesun() throws Exception {
        assertEquals(this.p.getCzas(), 0);
        assertEquals(this.p.getPrzejechaneJednostki(), 0);
        
        this.p.przesun(0);
        assertEquals(this.p.getCzas(), 1);
        
        this.p.przesun(0);
        
        assertEquals(this.p.getCzas(), 2);
        assertEquals(this.p.getPrzejechaneJednostki(), 0);
        
        
        this.p.setPredkosc(10);
        this.p.przesun(10);
        
        assertEquals(this.p.getCzas(), 3);
        assertEquals(this.p.getPrzejechaneJednostki(), 10);

        this.p.przesun(10);
        assertEquals(this.p.getCzas(), 4);
        assertEquals(this.p.getPrzejechaneJednostki(), 20);
        
        this.p.setPredkosc(20);
        this.p.przesun(20);
        
        assertEquals(this.p.getCzas(), 5);
        assertEquals(this.p.getPrzejechaneJednostki(), 40);
    }
    
    public void testGetOdlegloscPrzed() throws Exception {
		Droga d = new Droga();
		Pojazd p1 = new Pojazd(Pojazd.Typ.OSOBOWY, new Kierowca());
		Pojazd p2 = new Pojazd(Pojazd.Typ.OSOBOWY, new Kierowca());
		p1.setPredkosc(1);

		d.setIloscPasow(1);
		d.setDlugoscDrogi(100);

		d.setPojazdAt(0, 10, p1);
		d.setPojazdAt(0, 14, p2);
		assertEquals(0, p1.getOdlegloscOdSamochoduPrzed(Kierunek.PROSTO,100));
		
		d = new Droga();
		d.setIloscPasow(1);
		d.setDlugoscDrogi(100);
		
		d.setPojazdAt(0, 10, p1);
		d.setPojazdAt(0, 20, p2);
		assertEquals(6, p1.getOdlegloscOdSamochoduPrzed(Kierunek.PROSTO,100));
		
		System.out.println("---Wykonaj ruch---");
		d.wykonajRuch();
		System.out.println("" + d);
		assertEquals(5, p1.getOdlegloscOdSamochoduPrzed(Kierunek.PROSTO,100));
		assertEquals(75, p2.getOdlegloscOdSamochoduPrzed(Kierunek.PROSTO,100));
//		p2.setPredkosc(2);
		System.out.println("---Wykonaj ruch---");
		d.wykonajRuch();
		System.out.println("" + d);
		assertEquals(4, p1.getOdlegloscOdSamochoduPrzed(Kierunek.PROSTO,100));
	}
}
