/**
 * 
 */
package pl.wroc.pwr.iis.traffic.domain.entity;

import junit.framework.TestCase;

/**
 * @author michal
 *
 */
public class PasTest extends TestCase {

    Pas pas;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        this.pas = new Pas(20);
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetOdlegloscPrzed() throws Exception {
        Pas pas2 = new Pas(20);
        Pas pas3 = new Pas(40);
        this.pas.addDojazd(pas2);
        this.pas.addOdjazd(pas3);
        
        Pojazd p = new Pojazd(Pojazd.Typ.OSOBOWY);
        p.setPredkosc(1);
        p.setPas(0);

        this.pas.setZawartosc(p,10);
        pas2.setZawartosc(p,10);
        pas3.setZawartosc(p,10);
        
        assertEquals(this.pas.getOdlegloscPrzed(0,20), 9);
        assertEquals(this.pas.getOdlegloscPrzed(9,20), 0);
        assertEquals(this.pas.getOdlegloscPrzed(0,5), Integer.MAX_VALUE);
        assertEquals(this.pas.getOdlegloscPrzed(5,5), 4);

    }
    
}
