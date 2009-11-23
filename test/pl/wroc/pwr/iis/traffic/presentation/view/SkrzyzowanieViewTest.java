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
 * @author Administrator
 *
 */
public class SkrzyzowanieViewTest extends JFrame {
    private static final long serialVersionUID = -39325659938166462L;

    public final int SCALA = 2;
    
    private SkrzyzowanieView skrzyzowanieView = new SkrzyzowanieView();
    
    private JPanel jContentPane = null;

    /**
     * This is the default constructor
     */
    public SkrzyzowanieViewTest() {
        super();
        
        skrzyzowanieView.addPunktMapy(10,20);   // 0
        skrzyzowanieView.addPunktMapy(200,20);  // 1
        skrzyzowanieView.addPunktMapy(600,20);  // 2
        skrzyzowanieView.addPunktMapy(200,200); // 3
        skrzyzowanieView.addPunktMapy(400,200); // 4
        skrzyzowanieView.addPunktMapy(10,200); 	// 5
        
        skrzyzowanieView.addPas(0,1); //0
        skrzyzowanieView.addPas(1,2); //1
        skrzyzowanieView.addPas(1,3); //2
        skrzyzowanieView.addPas(1,4); //3
        skrzyzowanieView.addPas(5,1); //4

        skrzyzowanieView.addTrasa(new int[]{0,1}); // 0
        skrzyzowanieView.addTrasa(new int[]{0,2}); // 1
        skrzyzowanieView.addTrasa(new int[]{0,3}); // 2
        skrzyzowanieView.addTrasa(new int[]{4,3}); // 3
        
        Pojazd p1 = new Pojazd(Pojazd.Typ.OSOBOWY);
        Pojazd p2 = new Pojazd(Pojazd.Typ.OSOBOWY);
        Pojazd p3 = new Pojazd(Pojazd.Typ.OSOBOWY);
        Pojazd p4 = new Pojazd(Pojazd.Typ.OSOBOWY);
        p1.setPredkosc(4);
        p2.setPredkosc(2);
        p3.setPredkosc(2);
        p4.setPredkosc(4);
        
        skrzyzowanieView.getWezel().setPojazdAt(0, 20, p1);
        skrzyzowanieView.getWezel().setPojazdAt(1, 0, p2);
        skrzyzowanieView.getWezel().setPojazdAt(2, 40, p3);
        skrzyzowanieView.getWezel().setPojazdAt(1, 10, p4);
        
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
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
//                drogaView.addOstatniPunkt(e.getX(), e.getX());
            	skrzyzowanieView.wykonajRuch();
            	repaint();
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
				private static final long serialVersionUID = 2485539381049928955L;

                protected void paintComponent(Graphics arg0) {
                    super.paintComponent(arg0);
                    ((Graphics2D) arg0).setPaintMode();
                    skrzyzowanieView.paintComponent(((Graphics2D) arg0), SCALA);
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

        SkrzyzowanieViewTest application = new SkrzyzowanieViewTest();
        application.setVisible(true);
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"
