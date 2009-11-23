/**
 * 
 */
package pl.wroc.pwr.iis.traffic.presentation.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pl.wroc.pwr.iis.traffic.domain.entity.Pojazd;

/**
 * @author Administrator
 *
 */
public class SiecViewTest extends JFrame {
    private static final long serialVersionUID = -39325659938166462L;

    public final int SCALA = 2;
    
    private SkrzyzowanieView skrzyzowanieView = new SkrzyzowanieView();
    private DrogaView drogaView1 = new DrogaView();
    private DrogaView drogaView2 = new DrogaView();
    private DrogaView drogaView3 = new DrogaView();

    private JPanel jContentPane = null;

    /**
     * This is the default constructor
     */
    public SiecViewTest() {
        super();
        
        int x = 300;
        int y = 105;
        int d = DrogaView.SZEROKOSC_DROGI;
//        int d = 34;
        
        skrzyzowanieView.addPunktMapy(x,y);      // 0
        skrzyzowanieView.addPunktMapy(x+d,y+d);  // 1
        skrzyzowanieView.addPunktMapy(x+d+d,y+d);  // 2
        skrzyzowanieView.addPunktMapy(x+d+d+d,y);  // 3
        skrzyzowanieView.addPunktMapy(x+d+d+d,y - d);  // 4
        skrzyzowanieView.addPunktMapy(x,y - d);  // 5
        
        skrzyzowanieView.addPunktMapy(x+d,y);  // 6
        skrzyzowanieView.addPunktMapy(x+d+d,y);  // 7
        skrzyzowanieView.addPunktMapy(x+d+d,y-d);  // 8
        skrzyzowanieView.addPunktMapy(x+d,y-d);  // 9
        skrzyzowanieView.addPunktMapy((int) (x+d+0.5 * d) ,(int) (y-0.5*d));  // 10
        
        skrzyzowanieView.addPas(0,6); //0
        skrzyzowanieView.addPas(6,1); //1
        skrzyzowanieView.addPas(2,7); //2
        skrzyzowanieView.addPas(7,3); //3
        skrzyzowanieView.addPas(4,8); //4
        skrzyzowanieView.addPas(9,5); //5
        
        skrzyzowanieView.addPas(10,6); //6
        skrzyzowanieView.addPas(7,10); //7
        skrzyzowanieView.addPas(8,10); //8
        skrzyzowanieView.addPas(10,9); //9
       
        skrzyzowanieView.addPas(8,9); //10
        skrzyzowanieView.addPas(6,7); //11
        
        	skrzyzowanieView.setIloscPasow(3); // ustawienie ilosc pasow wjazdowych
        
        skrzyzowanieView.addTrasa(new int[]{0,11,3}); // 0
        skrzyzowanieView.addTrasa(new int[]{0,1}); // 1
        
	        skrzyzowanieView.addTrasyDlaPasaRuchu(0, 0);
	        skrzyzowanieView.addTrasyDlaPasaRuchu(0, 1);
        
        skrzyzowanieView.addTrasa(new int[]{2,3}); // 2
        skrzyzowanieView.addTrasa(new int[]{2,7,9,5}); // 3
        
	        skrzyzowanieView.addTrasyDlaPasaRuchu(1, 2);
	        skrzyzowanieView.addTrasyDlaPasaRuchu(1, 3);
	        
        skrzyzowanieView.addTrasa(new int[]{4,10,5}); // 4
        skrzyzowanieView.addTrasa(new int[]{4,8,6,1}); // 5
        
	        skrzyzowanieView.addTrasyDlaPasaRuchu(2, 4);
	        skrzyzowanieView.addTrasyDlaPasaRuchu(2, 5);
	     
	        skrzyzowanieView.updateData();
	        
        
        Pojazd p1 = new Pojazd(Pojazd.Typ.OSOBOWY);
        Pojazd p2 = new Pojazd(Pojazd.Typ.OSOBOWY);
        Pojazd p3 = new Pojazd(Pojazd.Typ.OSOBOWY);
        Pojazd p4 = new Pojazd(Pojazd.Typ.OSOBOWY);
        p1.setPredkosc(4);
        p2.setPredkosc(2);
        p3.setPredkosc(2);
        p4.setPredkosc(4);
//        
//        skrzyzowanieView.getSkrzyzowanie().setZawartosc(p1, 1, 0);
//        skrzyzowanieView.getSkrzyzowanie().setZawartosc(p2, 2, 5);
//        skrzyzowanieView.getSkrzyzowanie().setZawartosc(p3, 3, 0);
//        skrzyzowanieView.getSkrzyzowanie().setZawartosc(p4, 4, 0);
        
// -------------------------------------------
        int del = -10;//-18;//-6;
        this.drogaView1.setIloscPasow(2);
        this.drogaView1.setKierunekPasa(1);
//        this.drogaView1.addOstatniPunkt(x-80,y);
        this.drogaView1.addOstatniPunkt(x-100,y+del);
        this.drogaView1.addOstatniPunkt(x,y+del);
        
        this.drogaView2.setIloscPasow(2);
        this.drogaView2.setKierunekPasa(1);
        this.drogaView2.addOstatniPunkt(x+del+d+d,y+100);
        this.drogaView2.addOstatniPunkt(x+del+d+d,y+d);
        
        this.drogaView3.setIloscPasow(2);
        this.drogaView3.setKierunekPasa(1);
        this.drogaView3.addOstatniPunkt(x+100+d+d,y+del);
        this.drogaView3.addOstatniPunkt(x+d+d+d,y+del);

	    	this.drogaView1.setOdjazd(0, skrzyzowanieView, 0);
	    	this.drogaView2.setOdjazd(0, skrzyzowanieView, 1);
	    	this.drogaView3.setOdjazd(0, skrzyzowanieView, 2);
    	
	    	this.skrzyzowanieView.setOdjazd(0, drogaView3, 1);
	    	this.skrzyzowanieView.setOdjazd(1, drogaView2, 1);
	    	
	    	this.skrzyzowanieView.setOdjazd(2, drogaView3, 1);
	    	this.skrzyzowanieView.setOdjazd(3, drogaView1, 1);
	    	
	    	this.skrzyzowanieView.setOdjazd(4, drogaView1, 1);
	    	this.skrzyzowanieView.setOdjazd(5, drogaView2, 1);

        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(677, 543);
        this.setContentPane(getJContentPane());
        this.setTitle("Application");
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent e) {
//                drogaView.setOstatniPunkt(e.getX(), e.getY());
                repaint();
            }
        });
        
        (new Thread() {
        	public void run() {
				while (true) {
					
					try {
						sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					skrzyzowanieView.wykonajRuch();
					drogaView1.wykonajRuch();
					drogaView2.wykonajRuch();
					drogaView3.wykonajRuch();
					repaint();
				}
        	};
        }).start();
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
//                drogaView.addOstatniPunkt(e.getX(), e.getX());
            	if (e.getButton() == MouseEvent.BUTTON1) {
            		drogaView1.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
            	} else if (e.getButton() == MouseEvent.BUTTON3) {
            		drogaView2.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
            	} else if (e.getButton() == MouseEvent.BUTTON2) {
            		drogaView3.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
            	}
            }
        });
        this.addKeyListener(new java.awt.event.KeyAdapter() {
        	public void keyPressed(java.awt.event.KeyEvent e) {
        		System.out.println("keyPressed() " + e.getKeyChar()); // TODO Auto-generated Event stub keyPressed()
        	}
        });
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel() {
                /* (non-Javadoc)
                 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
                 */
                protected void paintComponent(Graphics arg0) {
                    super.paintComponent(arg0);
                    ((Graphics2D) arg0).setPaintMode();
                    skrzyzowanieView.paintComponent(((Graphics2D) arg0), SCALA);
                    drogaView1.paintComponent(((Graphics2D) arg0), SCALA);
                    drogaView2.paintComponent(((Graphics2D) arg0), SCALA);
                    drogaView3.paintComponent(((Graphics2D) arg0), SCALA);
                }
            };
            jContentPane.setLayout(new BorderLayout());
        }
        return jContentPane;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        SiecViewTest application = new SiecViewTest();
        application.show();
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"
