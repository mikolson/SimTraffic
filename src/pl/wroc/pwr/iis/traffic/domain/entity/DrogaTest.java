/*
 * Copyright 2005 Michal Stanek. All rights reserved.
 * Project name: 	Magisterska
 * File in project: DrogaTest.java
 * Creation date: 	2005-12-03
 */
package pl.wroc.pwr.iis.traffic.domain.entity;

import junit.framework.TestCase;

/**
 * @author Administrator
 */
public class DrogaTest extends TestCase {

    Droga d;
    
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DrogaTest.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        this.d = new Droga();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetDlugoscDrogi() {
        int dl = 10;
        this.d.setDlugoscDrogi(10);
        
        assertEquals(this.d.getDlugosc(), dl);
    }
    
    public void testSetIloscPasow() {
        int dl = 10;
        int ip = 2;
        this.d.setDlugoscDrogi(dl);
        this.d.setIloscPasow(ip);
        assertEquals(this.d.getDlugosc(), dl);
        assertEquals(this.d.getIloscPasow(), ip);
        
        this.d.setIloscPasow(ip = 5);
        assertEquals(this.d.getDlugosc(), dl);
        assertEquals(this.d.getIloscPasow(), ip);
        
        this.d.setIloscPasow(ip = 0);
        assertEquals(this.d.getDlugosc(), 0);
        assertEquals(this.d.getIloscPasow(), ip);
    }
    
    public void testIloscPasow() throws Exception {
        this.d.setDlugoscDrogi(10);
        this.d.setIloscPasow(2);
        
        this.d.setKierunekPasa(0);
        assertEquals(0, this.d.getIloscPasowPrawych());
        assertEquals(2, this.d.getIloscPasowLewych());
        
        this.d.setKierunekPasa(1);
        assertEquals(1, this.d.getIloscPasowPrawych());
        assertEquals(1, this.d.getIloscPasowLewych());
        
        this.d.setKierunekPasa(2);
        assertEquals(2, this.d.getIloscPasowPrawych());
        assertEquals(0, this.d.getIloscPasowLewych());
    }

    /*
     * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Droga.setDlugoscDrogi(int)'
     */
    public void testSetDlugoscDrogi() {
        assertEquals(this.d.getDlugosc(), 0);
        this.d.setDlugoscDrogi(10);
        assertEquals(this.d.getDlugosc(), 10);
        this.d.setIloscPasow(4);
        assertEquals(this.d.getDlugosc(), 10);
        this.d.setIloscPasow(1);
        assertEquals(this.d.getDlugosc(), 10);
        this.d.setIloscPasow(0);
        assertEquals(this.d.getDlugosc(), 0);
        this.d.setIloscPasow(1);
        this.d.setDlugoscDrogi(20);
        assertEquals(this.d.getDlugosc(), 20);
    }

    /*
     * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Droga.wykonajRuch()'
     */
    public void testWykonajRuch() {

    }

    /*
     * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Droga.setMaxPredkoscWszystkichPasow(int)'
     */
    public void testSetMaxPredkoscWszystkichPasow() {
        this.d.setIloscPasow(3);
        this.d.setMaxPredkoscWszystkichPasow(60);
        
        for (int i = 0; i < 3; i++) {
            assertEquals(this.d.getMaxPredkoscPasa(i),60);
        }
    }

    /*
     * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Droga.getKierunekPasa(int)'
     */
    public void testGetKierunekPasa() {
        this.d.setIloscPasow(2);
        assertEquals(Kierunek.PRAWY, this.d.getKierunekPasa(0));
        assertEquals(Kierunek.LEWY, this.d.getKierunekPasa(1));
        
    }

    public void testGetIloscSamochodowNaPasie() throws Exception {
        this.d.setDlugoscDrogi(10);
        this.d.setIloscPasow(10);
        
        assertEquals(this.d.getDlugosc(), 10);
        assertEquals(this.d.getIloscSamochodowNaPasie(0), 0);
    }

    /*
     * Test method for 'pl.wroc.pwr.iis.traffic.domain.entity.Droga.getIndeks(int, int)'
     */
    public void testGetIndeks() {
        this.d.setIloscPasow(1);
        this.d.setDlugoscDrogi(10);
        this.d.setKierunekPasa(1);
        
        assertEquals(this.d.getDlugosc(), 10);
        assertEquals(this.d.getKierunekPasa(0), Kierunek.PRAWY);
        
        int i = this.d.getIndeks(0, 1);
        assertEquals(i, 1);

        // Bledne podanie indeksu na pasie - powinien wyswietlic sie blad
        i = this.d.getIndeks(0, 11);
        
        this.d.setKierunekPasa(0);
        assertEquals(this.d.getKierunekPasa(0), Kierunek.LEWY);
        
        i = this.d.getIndeks(0, 1);
        assertEquals(8, i);
    }

    public void testPobierzZawartosc() throws Exception {
        this.d.setIloscPasow(5);
        this.d.setDlugoscDrogi(10);
        
        for (int i = 0; i < this.d.getDlugosc(); i++) {
            for (int j = 0; j < this.d.getIloscPasow(); j++) {
                assertEquals(null, this.d.getZawartosc(j, i));
            }
        }
    }
    
//    public void testCzyMoznaWjechacNaPas() throws Exception {
//        this.d.setDlugoscDrogi(10);
//        this.d.setIloscPasow(1);
//        
//        Pojazd samochod = new Pojazd(Pojazd.Typ.OSOBOWY, null);
//        
//        assertTrue(this.d.czyMoznaWjechacNaPas(0, samochod));
//        this.d.addPojazd(0, samochod);
//        assertFalse(this.d.czyMoznaWjechacNaPas(0, samochod));
//    }

    public void testAddSamochod() {
        this.d.setDlugoscDrogi(100);
        this.d.setIloscPasow(1);
        
        Pojazd samochod = new Pojazd(Pojazd.Typ.OSOBOWY, null);
        
        assertEquals(this.d.getIloscSamochodowNaPasie(0), 0);
        assertTrue(this.d.addPojazd(0, samochod));
        assertEquals(this.d, samochod.getWezel());
        assertEquals(0, samochod.getPas());
        
        assertEquals(this.d.getIloscSamochodowNaPasie(0), 1);
        
        assertFalse(this.d.addPojazd(0, samochod));
        assertEquals(this.d.getIloscSamochodowNaPasie(0), 1);
    }
    
    public void testGetOdlegloscPrzed() throws Exception {
        this.d.setDlugoscDrogi(100);
        this.d.setIloscPasow(10);
        this.d.setKierunekPasa(5);
        
        assertEquals(this.d.getOdlegloscPrzed(0,0, 100), 99);
        assertEquals(this.d.getOdlegloscPrzed(0,5, 100), 94);
        assertEquals(this.d.getOdlegloscPrzed(0,90, 100), 9);
        
        assertEquals(this.d.getOdlegloscPrzed(6, 0, 100), this.d.getDlugosc() - 1);
        assertEquals(this.d.getOdlegloscPrzed(6, 5, 100), 94);
        assertEquals(this.d.getOdlegloscPrzed(6, 90, 100), 9);
        
        Pojazd samochod = new Pojazd(Pojazd.Typ.OSOBOWY, null);
        this.d.setPojazdAt(0, 10, samochod);
        
        assertEquals(9, this.d.getOdlegloscPrzed(0, 0, 100));
        assertEquals(4,  this.d.getOdlegloscPrzed(0, 5, 100));
        
        this.d.setPojazdAt(0, 20, samochod);
        assertEquals(9, this.d.getOdlegloscPrzed(0, 10, 100));
        assertEquals(0,  this.d.getOdlegloscPrzed(0, 19, 100));
        assertEquals(0,  this.d.getOdlegloscPrzed(0, 9, 100));
        assertEquals(79,  this.d.getOdlegloscPrzed(0, 20, 100));
    }
    
    public void testGetOdlegloscZa() throws Exception {
        this.d.setDlugoscDrogi(100);
        this.d.setIloscPasow(10);
        this.d.setKierunekPasa(5);
        
        assertEquals(this.d.getOdlegloscZa(0,0), 0);
        assertEquals(this.d.getOdlegloscZa(0,5), 5);
        assertEquals(this.d.getOdlegloscZa(0,90), 90);
        
        assertEquals(this.d.getOdlegloscZa(6,0), 0);
        assertEquals(this.d.getOdlegloscZa(6,5), 5);
        assertEquals(this.d.getOdlegloscZa(6,90), 90);
        
        Pojazd samochod = new Pojazd(Pojazd.Typ.OSOBOWY, null);
        this.d.setPojazdAt(0, 10, samochod);
        
        int dlugosc = samochod.getDlugosc();
        
        assertEquals( 4, this.d.getOdlegloscZa(0, 10+dlugosc+5)); //  10 xxxxx----X
        assertEquals(0,  this.d.getOdlegloscZa(0, 10+dlugosc+1));
    }
    
    public void testGetPredkoscPrzed() throws Exception {
    	 this.d.setDlugoscDrogi(100);
         this.d.setIloscPasow(10);
         this.d.setKierunekPasa(5);
         
         assertEquals(this.d.getPredkoscPrzed(0,0, 10), Integer.MAX_VALUE);
         assertEquals(this.d.getPredkoscPrzed(0,20, 20), Integer.MAX_VALUE);
         
         Pojazd samochod = new Pojazd(Pojazd.Typ.OSOBOWY, null);
         this.d.setPojazdAt(0, 11, samochod);
         
         assertEquals(this.d.getPredkoscPrzed(0,0, 20), (int)samochod.getPredkosc());
         samochod.setPredkosc(10);
         assertEquals(this.d.getPredkoscPrzed(0,0, 20), (int)samochod.getPredkosc());
         assertEquals(this.d.getPredkoscPrzed(0,0, 11), (int)samochod.getPredkosc());
         assertEquals(this.d.getPredkoscPrzed(0,0, 10), Integer.MAX_VALUE);
	}
    
    public void testGetPredkoscZa() throws Exception {
    	this.d.setDlugoscDrogi(100);
        this.d.setIloscPasow(10);
        this.d.setKierunekPasa(5);
        
        assertEquals(this.d.getPredkoscZa(0,0, 10), 0);
        assertEquals(this.d.getPredkoscZa(0,20, 20), 0);
        
        Pojazd samochod = new Pojazd(Pojazd.Typ.OSOBOWY, null);
        this.d.setPojazdAt(0, 9, samochod);
        
        assertEquals(this.d.getPredkoscZa(0,20, 20), (int)samochod.getPredkosc());
        samochod.setPredkosc(10);
        assertEquals(this.d.getPredkoscZa(0,20, 20), (int)samochod.getPredkosc());
        assertEquals(this.d.getPredkoscZa(0,20, 11), (int)samochod.getPredkosc());
        assertEquals(this.d.getPredkoscZa(0,20, 10), 0);
	}
    
    public void testIsZmianaPasa() throws Exception {
    	this.d.setDlugoscDrogi(100);
        this.d.setIloscPasow(6);
        this.d.setKierunekPasa(4);

        for (int i = 0; i < d.getIloscPasow(); i++) {
        	assertTrue(this.d.isMozliwaZmianaPasa(i,Kierunek.PROSTO));
		}
        
        assertFalse(this.d.isMozliwaZmianaPasa(this.d.getIloscPasow(), Kierunek.PROSTO));
        
        // Warunki dla pasow prawych
        assertFalse(this.d.isMozliwaZmianaPasa(0,Kierunek.PRAWY));
        assertTrue(this.d.isMozliwaZmianaPasa(0,Kierunek.LEWY));
        
        assertTrue(this.d.isMozliwaZmianaPasa(1,Kierunek.PRAWY));
        assertTrue(this.d.isMozliwaZmianaPasa(1,Kierunek.LEWY));
        
        assertTrue(this.d.isMozliwaZmianaPasa(2,Kierunek.PRAWY));
        assertTrue(this.d.isMozliwaZmianaPasa(2,Kierunek.LEWY));
        
        assertTrue(this.d.isMozliwaZmianaPasa(3,Kierunek.PRAWY));
        assertFalse(this.d.isMozliwaZmianaPasa(3,Kierunek.LEWY));
        
        // Warunki dla pasow lewych
        assertFalse(this.d.isMozliwaZmianaPasa(4,Kierunek.PRAWY));
        assertTrue(this.d.isMozliwaZmianaPasa(4,Kierunek.LEWY));

        assertTrue(this.d.isMozliwaZmianaPasa(5,Kierunek.PRAWY));
        assertFalse(this.d.isMozliwaZmianaPasa(5,Kierunek.LEWY));
	}
    
    public void testToString() throws Exception {
        this.d.setDlugoscDrogi(100);
        this.d.setIloscPasow(4);
        this.d.setKierunekPasa(5);
        
        this.d.toString();
        
        System.out.println(this.d.toString());
        
        Pojazd samochod = new Pojazd(Pojazd.Typ.OSOBOWY, null);
        Pojazd samochod2 = new Pojazd(Pojazd.Typ.OSOBOWY, null);
        
        this.d.addPojazd(0, samochod);
        this.d.addPojazd(3, samochod2);
        
        System.out.println(this.d.toString());
        
        this.d.wykonajRuch();
        System.out.println(this.d.toString());
        for (int i = 0; i < 10; i++) {
            Pojazd samochod3 = new Pojazd(Pojazd.Typ.OSOBOWY, null);
            this.d.addPojazd(1, samochod3);
            this.d.wykonajRuch();
        }
        System.out.println(this.d.toString());
    }
}
