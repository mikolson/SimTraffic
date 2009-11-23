package pl.wroc.pwr.iis.traffic.presentation.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import pl.wroc.pwr.iis.traffic.domain.entity.Pojazd;

public class SiecTest {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Canvas canva = null;

    public final int SCALA = 2;
    private SkrzyzowanieView skrzyzowanieView = new SkrzyzowanieView();
    private DrogaView drogaView1 = new DrogaView();
    private DrogaView drogaView2 = new DrogaView();
    private DrogaView drogaView3 = new DrogaView();

    boolean loop = true;

    private void createDroga() {
        int x = 300;
        int y = 105;
        int d = DrogaView.SZEROKOSC_DROGI;
//        int d = 34;
        
        skrzyzowanieView.addOstatniPunkt(x,y);      // 0
        skrzyzowanieView.addOstatniPunkt(x+d,y+d);  // 1
        skrzyzowanieView.addOstatniPunkt(x+d+d,y+d);  // 2
        skrzyzowanieView.addOstatniPunkt(x+d+d+d,y);  // 3
        skrzyzowanieView.addOstatniPunkt(x+d+d+d,y - d);  // 4
        skrzyzowanieView.addOstatniPunkt(x,y - d);  // 5
        
        skrzyzowanieView.addOstatniPunkt(x+d,y);  // 6
        skrzyzowanieView.addOstatniPunkt(x+d+d,y);  // 7
        skrzyzowanieView.addOstatniPunkt(x+d+d,y-d);  // 8
        skrzyzowanieView.addOstatniPunkt(x+d,y-d);  // 9
        skrzyzowanieView.addOstatniPunkt((int) (x+d+0.5 * d) ,(int) (y-0.5*d));  // 10
        
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[0], skrzyzowanieView.getPunktyEdycji()[6]);
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[6], skrzyzowanieView.getPunktyEdycji()[1]);
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[2], skrzyzowanieView.getPunktyEdycji()[7]);
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[7], skrzyzowanieView.getPunktyEdycji()[3]);
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[4], skrzyzowanieView.getPunktyEdycji()[8]);
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[9], skrzyzowanieView.getPunktyEdycji()[5]);
        
//        skrzyzowanieView.addPas(0,6); //0
//        skrzyzowanieView.addPas(6,1); //1
//        skrzyzowanieView.addPas(2,7); //2
//        skrzyzowanieView.addPas(7,3); //3
//        skrzyzowanieView.addPas(4,8); //4
//        skrzyzowanieView.addPas(9,5); //5

        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[10], skrzyzowanieView.getPunktyEdycji()[6]);
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[7], skrzyzowanieView.getPunktyEdycji()[10]);
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[8], skrzyzowanieView.getPunktyEdycji()[10]);
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[10], skrzyzowanieView.getPunktyEdycji()[9]);

//        skrzyzowanieView.addPas(10,6); //6
//        skrzyzowanieView.addPas(7,10); //7
//        skrzyzowanieView.addPas(8,10); //8
//        skrzyzowanieView.addPas(10,9); //9
       
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[8], skrzyzowanieView.getPunktyEdycji()[9]);
        skrzyzowanieView.prowadzDroge(skrzyzowanieView.getPunktyEdycji()[6], skrzyzowanieView.getPunktyEdycji()[7]);
        
//        skrzyzowanieView.addPas(8,9); //10
//        skrzyzowanieView.addPas(6,7); //11
        
//        	skrzyzowanieView.setIloscPasow(3); // ustawienie ilosc pasow wjazdowych
        
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
	        
//	        skrzyzowanieView.updateData();
        
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

	    	new Thread(){
	            public void run() {
	            	setPriority(Thread.MAX_PRIORITY);
	                while (loop) {
	                	try {
							sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						makeMove();
	                }
	                super.run();
	            }
	        }.start();
         
	}
    
    private void makeMove() {
		skrzyzowanieView.wykonajRuch();
		drogaView1.wykonajRuch();
		drogaView2.wykonajRuch();
		drogaView3.wykonajRuch();

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

	
	/**
	 * This method initializes canva	
	 *
	 */
	private void createCanva() {
		canva = new Canvas(sShell, SWT.NONE);
		canva.addPaintListener(new org.eclipse.swt.events.PaintListener() {
			public void paintControl(org.eclipse.swt.events.PaintEvent e) {
                drogaView1.paintComponent((e.gc), SCALA);
                drogaView2.paintComponent((e.gc), SCALA);
                drogaView3.paintComponent((e.gc), SCALA);
                skrzyzowanieView.paintComponent((e.gc), SCALA);
			}
		});
		canva.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				System.out.println(".mouseDown() : START");
				drogaView3.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
				drogaView1.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
				drogaView2.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
                if (e.button == SWT.BUTTON1) {
                	drogaView1.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
                	System.out.println(".mouseDown() : 1");
                } else if (e.button == SWT.BUTTON2) {
                	drogaView2.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
                	System.out.println(".mouseDown() : 2");
                } else if (e.button == SWT.BUTTON3) {
                	drogaView3.addPojazd(0, new Pojazd(Pojazd.Typ.OSOBOWY));
                	System.out.println(".mouseDown() : 3");
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
		SiecTest thisClass = new SiecTest();

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
		sShell.setSize(new Point(562, 500));
		sShell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
			public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
				sShell.dispose();
				loop = false;
			}
		});
	}

}
