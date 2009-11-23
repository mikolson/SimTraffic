package pl.wroc.pwr.iis.traffic.presentation.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.wroc.pwr.iis.traffic.domain.entity.Pojazd;

public class DrogaPolaczenieJazda {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Canvas canva = null;

    public final int SCALA = 2;
    private DrogaView drogaView = new DrogaView();  //  @jve:decl-index=0:

    boolean loop = true;

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
        
        System.out.println("DrogaJazda.makeMove()");
        drogaView.wykonajRuch();
        if (sShell != null)
		sShell.getDisplay().syncExec(new Runnable () {
		      public void run () {
		         if (!canva.isDisposed()){
		        	long time = System.currentTimeMillis();
		            canva.redraw ();
		         	System.out.println("Czas przerysowania mapy ekranu: " + (System.currentTimeMillis() - time));
		         }
		      }
		   });
    }

    private void createDroga() {
        this.drogaView.setIloscPasow(2);
        this.drogaView.setKierunekPasa(1);
        
        this.drogaView.addOstatniPunkt(20,200);
        this.drogaView.addOstatniPunkt(620,200);

        
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
	}
	
	/**
	 * This method initializes canva	
	 *
	 */
	private void createCanva() {
		canva = new Canvas(sShell, SWT.NONE);
		canva.addPaintListener(new org.eclipse.swt.events.PaintListener() {
			public void paintControl(org.eclipse.swt.events.PaintEvent e) {
                drogaView.paintComponent((e.gc), SCALA);
			}
		});
		canva.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
                if (e.button == SWT.BUTTON1) {
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* Before this is run, be sure to set up the launch configuration (Arguments->VM Arguments)
		 * for the correct SWT library path in order to run with the SWT dlls. 
		 * The dlls are located in the SWT plugin jar.  
		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
		 *       installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		Display display = Display.getDefault();
		DrogaPolaczenieJazda thisClass = new DrogaPolaczenieJazda();

		thisClass.createDroga();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Shell");
		sShell.setLayout(new FillLayout());
		createCanva();
		sShell.setSize(new Point(562, 345));
		sShell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
			public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
				sShell.dispose();
				loop = false;
			}
		});
	}

}
