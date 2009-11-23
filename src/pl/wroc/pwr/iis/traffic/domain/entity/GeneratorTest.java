/**
 * 
 */
package pl.wroc.pwr.iis.traffic.domain.entity;

import junit.framework.TestCase;

/**
 * @author Michal Stanek
 */
public class GeneratorTest extends TestCase {
    Generator g;
    
    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        this.g = new Generator();
    }
    
    public void testSetPrawdopodobienstwoPojazdu() throws Exception {
        assertFalse(this.g.getPrawdopodobienstwoPojazdu() == 0);
        
        this.g.setPrawdopodobienstwoPojazdu(50);
        assertEquals(this.g.getPrawdopodobienstwoPojazdu(), 50);

        this.g.setPrawdopodobienstwoPojazdu(0);
        assertEquals(this.g.getPrawdopodobienstwoPojazdu(), 0);
        
        this.g.setPrawdopodobienstwoPojazdu(1);
        assertEquals(this.g.getPrawdopodobienstwoPojazdu(), 1);
        
        
        this.g.setPrawdopodobienstwoPojazdu(120);
        assertEquals(this.g.getPrawdopodobienstwoPojazdu(), 1);
    }
    
    
    public void testAddPrawdopodobienstwoPojazdu() throws Exception {
        assertEquals(0, this.g.getIloscGenerowanychPojazdow());
        
        this.g.addPrawdopodobienstwoPojazdu(0, new Pojazd(Pojazd.Typ.OSOBOWY, new Kierowca()));
        assertEquals(0, this.g.getIloscGenerowanychPojazdow());
        
        this.g.addPrawdopodobienstwoPojazdu(50, null);
        assertEquals(0, this.g.getIloscGenerowanychPojazdow());

        this.g.addPrawdopodobienstwoPojazdu(1, new Pojazd(Pojazd.Typ.OSOBOWY, new Kierowca()));
        assertEquals(1, this.g.getIloscGenerowanychPojazdow());
    }
    
    public void testKonstruktorZParametrami() throws Exception {
        this.g = new Generator(50);
        assertEquals(this.g.getPrawdopodobienstwoPojazdu(), 50);
        assertEquals(this.g.getIloscGenerowanychPojazdow(), 4);
    }
    
    public void testGetPrawdopodobienstwoSumaryczne() throws Exception {
        
    }
    
    public void testGenerujPojazd() throws Exception {
        this.g = new Generator(100);
        int w = 0;
        for (int i = 0; i < 10000; i++) {
            Pojazd p = this.g.generujPojazd();
            if (p != null) {
                w++;
            }
        }
        assertEquals(w, 10000);
        
        this.g = new Generator(0);
        w = 0;
        for (int i = 0; i < 10000; i++) {
            Pojazd p = this.g.generujPojazd();
            if (p != null) {
                w++;
            }
        }
        assertEquals(w, 0);
        
        
        this.g = new Generator(50);
        w = 0;
        for (int i = 0; i < 10000; i++) {
            Pojazd p = this.g.generujPojazd();
            if (p != null) {
                w++;
            }
        }
        assertTrue(w < 5200);
        assertTrue(w > 4800);
        
        System.out.println("Dla 10000 razy przy prawd. 50% wygenerowano: " + w + " samochodów.");
    }
}
