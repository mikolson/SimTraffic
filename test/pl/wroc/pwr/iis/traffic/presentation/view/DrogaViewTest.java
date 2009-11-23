/**
 * 
 */
package pl.wroc.pwr.iis.traffic.presentation.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Administrator
 *
 */
public class DrogaViewTest extends JFrame {
    private static final long serialVersionUID = -39325659938166462L;

    public final int SCALA = 2;
    
    private DrogaView drogaView = new DrogaView();
    
    private JPanel jContentPane = null;

    /**
     * This is the default constructor
     */
    public DrogaViewTest() {
        super();
        
        this.drogaView.setIloscPasow(4);
        this.drogaView.addOstatniPunkt(10,10);
        this.drogaView.addOstatniPunkt(20,40);
        this.drogaView.addOstatniPunkt(50,100);
        
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
                drogaView.setOstatniPunkt(e.getX(), e.getY());
                repaint();
            }
        });
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                drogaView.addOstatniPunkt(e.getX(), e.getX());
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

        DrogaViewTest application = new DrogaViewTest();
        application.show();
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"
