/**
 * 
 */
package pl.wroc.pwr.iis.traffic.presentation.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * @author Administrator
 *
 */
public class MapaViewTest extends JFrame {
    private static final long serialVersionUID = -39325659938166462L;

    public final int SCALA = 2;
    
    private MapaView mapaView;
    
    private JPanel jContentPane = null;
    private JMenuBar jJMenuBar = null;
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        MapaViewTest application = new MapaViewTest();
        application.show();
    }

    /**
     * This is the default constructor
     */
    public MapaViewTest() {
        super();
        
        this.mapaView = new MapaView(541,501);
        
        initialize();
    }

    /**
     * This method initializes this
     * 1234
     * 
     * @return void
     */
    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(getJJMenuBar());
        this.setSize(642, 306);
        this.setContentPane(getJContentPane());
        this.setTitle("Application");
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
                    mapaView.paintComponent(((Graphics2D) arg0), SCALA);
                }
            };
            jContentPane.setLayout(new BorderLayout());
        }
        return jContentPane;
    }

    /**
     * This method initializes jJMenuBar	
     * 	
     * @return javax.swing.JMenuBar	
     */
    private JMenuBar getJJMenuBar() {
        if (jJMenuBar == null) {
            jJMenuBar = new JMenuBar();
        }
        return jJMenuBar;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
