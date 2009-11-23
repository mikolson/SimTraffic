package pl.wroc.pwr.iis.traffic.domain.entity;

import junit.framework.TestCase;

public class SkrzyzowanieTest extends TestCase {
	
	Skrzyzowanie s;
	
	protected void setUp() throws Exception {
		super.setUp();
		this.s = new Skrzyzowanie();
    }
    
    public void testAddPas() throws Exception {
        this.s.addPas(10);
        this.s.addPas(10);
        this.s.addPas(20);
        
        System.out.println("SkrzyzowanieTest.testAddPas(): \n" + s.toString());
    }
    
    public void testWykonajRuch() throws Exception {
        
    }
    
	/*
	 * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie.getOdlegloscPrzed(int, int)'
	 */
	public void testGetOdlegloscPrzed() {
        this.s.addPas(10);
        this.s.addPas(10);
        this.s.addPas(10);
        
        this.s.getPas(2).addDojazd(this.s.getPas(0));
        this.s.getPas(2).addDojazd(this.s.getPas(1));
        
        Trasa trasa = this.s.addTrasa(4);
        trasa.addOdcinek(this.s.getPas(0));
        trasa.addOdcinek(this.s.getPas(2));
        
        Pojazd p = new Pojazd(Pojazd.Typ.OSOBOWY);
        p.setPredkosc(1);
        p.setPas(0);
        
        this.s.getPas(0).setZawartosc(p,5);
        this.s.getPas(2).setZawartosc(p,5);
        
        
        assertEquals(4, this.s.getOdlegloscPrzed(0, 0, 4));
        assertEquals(4, this.s.getOdlegloscPrzed(0, 0, 10));
        assertEquals(Integer.MAX_VALUE, this.s.getOdlegloscPrzed(0, 0, 1));
        assertEquals(Integer.MAX_VALUE, this.s.getOdlegloscPrzed(0, 0, 3));
        
        assertEquals(9, this.s.getOdlegloscPrzed(0, 5, 10));
        
	}

	/*
	 * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie.getMaxPredkoscPasa(int)'
	 */
	public void testGetMaxPredkoscPasa() {
        this.s.addPas(10);
        this.s.addPas(10);
        
        this.s.getPas(0).addOdjazd(this.s.getPas(1));
        
        Trasa trasa = this.s.addTrasa(4);
        trasa.addOdcinek(this.s.getPas(0));
        trasa.addOdcinek(this.s.getPas(1));
        
        Pojazd p = new Pojazd(Pojazd.Typ.OSOBOWY);
        p.setPredkosc(1);
        p.setPas(0);
        
        this.s.getPas(0).setZawartosc(p,0);

        for (int i = 0; i < 20; i++) {
            System.out.println(this.s.toString());
            this.s.wykonajRuch();
        }
        System.out.println(this.s.toString());
	}
    
    public void testGetOdlegloscPas() throws Exception {
        this.s.addPas(10);
        this.s.addPas(10);
        
        this.s.getPas(0).addOdjazd(this.s.getPas(1));
        

        
        Trasa trasa = this.s.addTrasa(4);
        trasa.addOdcinek(this.s.getPas(0));
        trasa.addOdcinek(this.s.getPas(1));
        
        assertEquals(trasa.getOdlegloscPas(0),trasa.getOdcinek(0));
        assertEquals(trasa.getOdlegloscPas(1),trasa.getOdcinek(0));
        assertEquals(trasa.getOdlegloscPas(9),trasa.getOdcinek(0));
        assertEquals(trasa.getOdlegloscPas(10),trasa.getOdcinek(1));
        assertEquals(trasa.getOdlegloscPas(15),trasa.getOdcinek(1));
        assertEquals(trasa.getOdlegloscPas(20),null);
        
        assertEquals(trasa.getOdlegloscIndeks(0), 0);
        assertEquals(trasa.getOdlegloscIndeks(1), 1);
        assertEquals(trasa.getOdlegloscIndeks(9), 9);
        assertEquals(trasa.getOdlegloscIndeks(10), 0);
        assertEquals(trasa.getOdlegloscIndeks(15), 5);
        assertEquals(trasa.getOdlegloscIndeks(20), 0);
    }
    

	/*
	 * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie.getKierunekPasa(int)'
	 */
	public void testGetKierunekPasa() {

	}

	/*
	 * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie.isMozliwaZmianaPasa(int, Kierunek)'
	 */
	public void testIsMozliwaZmianaPasa() {

	}

	/*
	 * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie.getPredkoscPrzed(int, int, int)'
	 */
	public void testGetPredkoscPrzed() {

	}

	/*
	 * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie.getPredkoscZa(int, int, int)'
	 */
	public void testGetPredkoscZa() {

	}

	/*
	 * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie.getOdlegloscZa(int, int)'
	 */
	public void testGetOdlegloscZa() {

	}

	/*
	 * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie.getPrzesuniecieWzgledemPasow(int, int, int)'
	 */
	public void testGetPrzesuniecieWzgledemPasow() {

	}

}
