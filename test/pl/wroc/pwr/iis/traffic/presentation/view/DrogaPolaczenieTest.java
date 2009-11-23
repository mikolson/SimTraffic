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
 * @author Michał Stanek
 */
public class DrogaPolaczenieTest extends JFrame {
    private static final long serialVersionUID = -39325659938166462L;

    public final int SCALA = 2;
    
    private DrogaView drogaView1 = new DrogaView();
    private DrogaView drogaView2 = new DrogaView();
//    private Generator g = new Generator(100);
    private JPanel jContentPane = null;

    /**
     * This is the default constructor
     */
    public DrogaPolaczenieTest() {
        super();
        
        this.drogaView1.setIloscPasow(2);
        this.drogaView1.setKierunekPasa(2);
        
        this.drogaView2.setIloscPasow(2);
        this.drogaView2.setKierunekPasa(2);
        
        this.drogaView1.addOstatniPunkt(40,200);
        this.drogaView1.addOstatniPunkt(140,260);
        
        this.drogaView2.addOstatniPunkt(140,260);
        this.drogaView2.addOstatniPunkt(240,300);
        
        this.drogaView1.setOdjazd(0, this.drogaView2, 0);
        this.drogaView1.setOdjazd(1, this.drogaView2, 1);

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
//                    makeMove();
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
                  int m = (int)(Math.random() * 8);
                  if (m > 5) {
                	  drogaView1.addPojazd(0, new Pojazd(Pojazd.Typ.CIEZAROWY));
                  } else {
                	  drogaView1.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
                  }
                }
            }              
        });
    }
    
    boolean loop = false;
    
    private void makeMove() {
//      Long l = System.currentTimeMillis();
        for (int i = 0; i < drogaView1.getIloscPasow(); i++) {
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
        drogaView1.wykonajRuch();
        drogaView2.wykonajRuch();
        
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
                    drogaView1.paintComponent(((Graphics2D) arg0), SCALA);
                    drogaView2.paintComponent(((Graphics2D) arg0), SCALA);
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

        DrogaPolaczenieTest application = new DrogaPolaczenieTest();
        application.setVisible(true);
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"
