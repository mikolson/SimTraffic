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
public class SkrzyzowanieView2Test extends JFrame {
    private static final long serialVersionUID = -39325659938166462L;

    public final int SCALA = 2;
    
    private SkrzyzowanieView skrzyzowanieView = new SkrzyzowanieView();
    
    private JPanel jContentPane = null;

    /**
     * This is the default constructor
     */
    public SkrzyzowanieView2Test() {
        super();
        
        int x = 200;
        int y = 105;
        int d = 60;
        
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
        skrzyzowanieView.addPunktMapy((int) (x+d+0.5 * d) ,(int) (y-0.5*d));  // 9
        
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
        
        	skrzyzowanieView.setIloscPasow(3);
        
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
	        
	        
        
        Pojazd p1 = new Pojazd(Pojazd.Typ.OSOBOWY);
        Pojazd p2 = new Pojazd(Pojazd.Typ.OSOBOWY);
        Pojazd p3 = new Pojazd(Pojazd.Typ.OSOBOWY);
        Pojazd p4 = new Pojazd(Pojazd.Typ.OSOBOWY);
        p1.setPredkosc(4);
        p2.setPredkosc(2);
        p3.setPredkosc(2);
        p4.setPredkosc(4);

        skrzyzowanieView.getWezel().setPojazdAt(0, 5, p1);
        skrzyzowanieView.getWezel().setPojazdAt(0, 0, p2);
        skrzyzowanieView.getWezel().setPojazdAt(1, 0, p3);
        skrzyzowanieView.getWezel().setPojazdAt(2, 0, p4);
        
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
                /* (non-Javadoc)
                 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
                 */
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

        SkrzyzowanieView2Test application = new SkrzyzowanieView2Test();
        application.show();
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"
