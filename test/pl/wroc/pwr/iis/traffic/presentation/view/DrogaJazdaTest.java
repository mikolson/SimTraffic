/**
 * 
 */
package pl.wroc.pwr.iis.traffic.presentation.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pl.wroc.pwr.iis.traffic.domain.entity.Pojazd;

/**
 * 
 * @author Administrator
 */
public class DrogaJazdaTest extends JFrame {
    private static final long serialVersionUID = -39325659938166462L;

    public final int SCALA = 2;
    
    private DrogaView drogaView = new DrogaView();
//    private Generator g = new Generator(100);
    private JPanel jContentPane = null;

    /**
     * This is the default constructor
     */
    public DrogaJazdaTest() {
        super();
        
        this.drogaView.setIloscPasow(4);
        this.drogaView.setKierunekPasa(4);
        
        this.drogaView.addOstatniPunkt(20,400);
        this.drogaView.addOstatniPunkt(620,400);
        
//        this.drogaView.addOstatniPunkt(200,400);
//        this.drogaView.addOstatniPunkt(100,130);
//        this.drogaView.addOstatniPunkt(140,50);
//        this.drogaView.addOstatniPunkt(320,40);
//        this.drogaView.addOstatniPunkt(450,40);
//        this.drogaView.addOstatniPunkt(540,100);
//        this.drogaView.addOstatniPunkt(620, 360);
//        this.drogaView.addOstatniPunkt(520, 500);

//        for (int i = 0; i < droga.getIloscPasow(); i++) {
//            this.droga.addPojazd(i, new Pojazd(Pojazd.Typ.OSOBOWY));
//        }

// for (int i = 0; i < droga.getIloscPasow()-1; i++) {
//            droga.addSamochod(i, g.generujPojazd());
//        }

//        this.droga.addSamochod(3, new Pojazd(Pojazd.Typ.CIEZAROWY));
//        this.droga.addSamochod(5, new Pojazd(Pojazd.Typ.AUTOBUS));
//        this.droga.addSamochod(3, new Pojazd(Pojazd.Typ.AUTOBUS_PRZEGUBOWY));
        
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
        
//        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseMoved(java.awt.event.MouseEvent e) {
//                drogaView.setOstatniPunkt(e.getX(), e.getY());
//                repaint();
//            }
//        });
        
        loop = true;
        new Thread(){
            public void run() {
            	setPriority(Thread.MAX_PRIORITY);
                while (loop) {
                    makeMove();
                    try {
						sleep(20);
						sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                super.run();
            }
        }.start(); 
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getButton() == e.BUTTON1) {
                    makeMove();
                } else {
                  int m = (int)(Math.random() * 10);
                  if (m >8 ) {
                	  drogaView.addPojazd(0, new Pojazd(Pojazd.Typ.AUTOBUS));
                  } else if (m > 5) {
                	  drogaView.addPojazd(0, new Pojazd(Pojazd.Typ.CIEZAROWY));
                  } else {
                	  drogaView.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
                  }
                }
            }              
        });
    }
    
    boolean loop = false;
    
    private void makeMove() {
//      Long l = System.currentTimeMillis();
        for (int i = 0; i < drogaView.getIloscPasow(); i++) {
//          int m = (int)(Math.random() * 10);
//          System.out.println("DrogaJazdaTest.initialize():" + m);
//          if (m >8 ) {
//          droga.addSamochod(i, new Pojazd(Pojazd.Typ.AUTOBUS));
//          } else if (m > 5) {
//          droga.addSamochod(i, new Pojazd(Pojazd.Typ.CIEZAROWY));
//          } else {
//            droga.addPojazd(i, new Pojazd(Pojazd.Typ.OSOBOWY));
//          }
        }
        drogaView.wykonajRuch();
        
//        System.out.println("DrogaJazdaTest.makeMove(): " + (System.currentTimeMillis() - l));
        getJContentPane().repaint();
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
                    drogaView.paintComponent(((Graphics2D) arg0), SCALA);
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

        DrogaJazdaTest application = new DrogaJazdaTest();
        application.show();
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"
