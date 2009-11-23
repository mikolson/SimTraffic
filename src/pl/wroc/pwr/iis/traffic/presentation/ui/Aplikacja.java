/**
 * 
 */
package pl.wroc.pwr.iis.traffic.presentation.ui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import pl.wroc.pwr.iis.traffic.control.Zapis;
import pl.wroc.pwr.iis.traffic.presentation.model.EditModes;
import pl.wroc.pwr.iis.traffic.presentation.model.Grupa;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;
import pl.wroc.pwr.iis.traffic.presentation.view.MapaView;
import sun.security.jca.GetInstance;

/**
 * @author Administrator
 */
public class Aplikacja {
	private final static String KATALOG_ZAPISU = "saved/";  //  @jve:decl-index=0:
	private static final String PLIK_SZYBKIEGO_ZAPISU = KATALOG_ZAPISU + "backUp.sav";  //  @jve:decl-index=0:
	private static final int NORMALNE_TEMPO_SYMULACJI = 60;
	private static final int KROK_ZMIANY = 10;
	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="4,-6"
	
	private static Aplikacja instance = null; 
	
	private ToolBar toolBar = null;
	private Menu menuBar = null;
	private Menu submenuPlik = null;
	private Menu submenuNarzedzia = null;
	private Menu submenuPomoc = null;
	private Canvas canvas = null;
	private ToolBar toolBar1 = null;
	private Menu submenuEdycja = null;

	final MapaView mapaView = new MapaView(1400, 1000);  //  @jve:decl-index=0:
	private Composite composite = null;
	private Button bPlay = null;
	private Button bStop = null;
	private Button bPause = null;
	private Button bIncTempo = null;
	private Button bDecTempo = null;
	private Button button5 = null;
	private Button button51 = null;
	private Button button6 = null;
	private Button button7 = null;
	private Button bMoveLast = null;
	private Button bMoveDown = null;
	private Button bMoveUp = null;
	private Button bMoveFirst = null;
	private Button button52 = null;
	private Button button521 = null;
	private Button button5211 = null;
	private Button bOneStep = null;
	
	boolean symulowac = false;
	int symulacjaTempo = NORMALNE_TEMPO_SYMULACJI;
	
	SelectionAdapter aPrzesunNaWierzch = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.moveFirst();
			przerysujMape();
		}; 
	};
	SelectionAdapter aPrzesunNaSpod = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.moveLast();
			przerysujMape();
		}; 
	};
	SelectionAdapter aPrzesunWyzej = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.moveUp();
			przerysujMape();
		}; 
	};
	SelectionAdapter aPrzesunNizej = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.moveDown();
			przerysujMape();
		}; 
	};
	SelectionAdapter aGrupujObiekty = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.grupujObiekty();  //  @jve:decl-index=0:
			przerysujMape();
		}; 
	};
	SelectionAdapter aRozgrupujObiekty = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.rozgrupujObiekty();
			przerysujMape();
		}; 
	};
	SelectionAdapter aWstawDroge = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.ROAD_INSERT);  //  @jve:decl-index=0:
		}
	}; 
	SelectionAdapter aWstawSkrzyzowanie = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.INTERSECTION_INSERT);
		}
	}; 
	SelectionAdapter aWstawSkrzyzowaniePas = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.INTERSECTION_PAS);
		}
	}; 
	SelectionAdapter aWstawSkrzyzowanieTrasa = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.INTERSECTION_TRASA);
		}
	}; 
	SelectionAdapter aWstawGenerator = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.INSERT_GENERATOR);
		}
	}; 
	SelectionAdapter aWstawLinie = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.LINE_INSERT);
		}
	}; 
	SelectionAdapter aWstawPolilinie = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.POLYLINE_INSERT);
		}
	}; 
	SelectionAdapter aWstawProstokat = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.RECTANGLE_INSERT);
		}
	}; 
	SelectionAdapter aWstawElipsa = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.ELIPSA_INSERT);
		}
	}; 
	SelectionAdapter aWstawObszar = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.OBSZAR_INSERT);
		}
	}; 
	SelectionAdapter aWstawObrazek = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.OBRAZEK_INSERT); 
		}
	}; 
	SelectionAdapter aPolaczWezly = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setEditMode(EditModes.CONNECT_WEZLY);
		}
	}; 
	SelectionAdapter aWlasciwosciKanwy = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			WlasciwosciKanwy wlasciwosci = new WlasciwosciKanwy();
			wlasciwosci.open(mapaView);
		}
	}; 
	SelectionAdapter aRysujProwadnice = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setRysujProwadnice(!mapaView.isRysujProwadnice());  //  @jve:decl-index=0:
			przerysujMape();
		}
	}; 
	SelectionAdapter aWysokaJakosc = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setWysokaJakosc(!mapaView.isWysokaJakosc());  //  @jve:decl-index=0:
			przerysujMape();
		}
	}; 
	SelectionAdapter aRysujSiatke = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setRysujSiatke(!mapaView.isRysujSiatke());  //  @jve:decl-index=0:
			przerysujMape();
		}
	}; 
	SelectionAdapter aWspolnePunkty = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			mapaView.setWspolnePunkty(!mapaView.isWspolnePunkty());  //  @jve:decl-index=0:
		}
	}; 
	
	Thread symulacja = new Thread() {
		public void run() {
			while (!sShell.isDisposed()) {
				if (symulowac) {
		        	long time = System.currentTimeMillis();
					mapaView.wykonajRuch();  //  @jve:decl-index=0:
					System.out.println("Czas symulacji: " + (System.currentTimeMillis() - time));
					refreshWindow();
				}
				
				try {
					sleep(symulacjaTempo);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	};
	
	
	public void refreshWindow() {
		if (!sShell.isDisposed()) {
			sShell.getDisplay().syncExec(new Runnable () {
			      public void run () {
			         if (!canvas.isDisposed()){
			            canvas.redraw ();
			         }
			      }
			   });
			
		}
	}
	
	public static Aplikacja getInstance() {
		return instance;
	}
	
	/**
	 * 
	 */
	public Aplikacja() {
		super();
		instance = this;
	}

	private void startSymulowac() {
		this.symulowac = true;
	}
	
	private void stopSymulowac() {
		this.symulowac = false;
	}
	
	private void incTempo() {
		this.symulacjaTempo += KROK_ZMIANY;
	}
	
	private void decTempo() {
		if (this.symulacjaTempo - KROK_ZMIANY >= 0) {
			this.symulacjaTempo -= KROK_ZMIANY;
		}
	}
	
	
	/**
	 * This method initializes toolBar	
	 *
	 */
	private void createToolBar() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalIndent = 0;
		gridData1.horizontalSpan = 6;
		gridData1.heightHint = -1;
		gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		toolBar = new ToolBar(sShell, SWT.FLAT | SWT.SHADOW_OUT | SWT.RIGHT);
		toolBar.setLayoutData(gridData1);
		
		ToolItem ti0Sep0 = new ToolItem(toolBar, SWT.SEPARATOR);
		ToolItem tiStrzalka = new ToolItem(toolBar, SWT.PUSH);
		tiStrzalka.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/strzalka.png")));
		ToolItem tiDodajPunkt = new ToolItem(toolBar, SWT.PUSH);
		tiDodajPunkt.setText("");
		tiDodajPunkt.setToolTipText("Dodaj punkt");
		tiDodajPunkt.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/dodajPunkt.png")));
		ToolItem tiUsunPunkt = new ToolItem(toolBar, SWT.PUSH);
		tiUsunPunkt.setText("");

		tiUsunPunkt.setToolTipText("Usuń Punkt");
		tiUsunPunkt.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/usunPunkt.png")));
		ToolItem tiSep4 = new ToolItem(toolBar, SWT.SEPARATOR);
		
		toolBar.setSize(200,100);
		ToolItem tiDroga = new ToolItem(toolBar, SWT.PUSH);
		tiDroga.setText("");
		tiDroga.setToolTipText("Wstaw drogę");
		tiDroga.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/droga.png")));
		ToolItem tiSkrzyzowanie = new ToolItem(toolBar, SWT.PUSH);
		tiSkrzyzowanie.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/skrzyzowanie.png")));
		tiSkrzyzowanie.setToolTipText("Wstaw skrzyżowanie");
		tiSkrzyzowanie.setText("");
		ToolItem tiPunktKolizyjny = new ToolItem(toolBar, SWT.PUSH);
		tiPunktKolizyjny.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/punktKolizyjny.png")));
		ToolItem tiSkrzyzowaniePolaczenie = new ToolItem(toolBar, SWT.PUSH);
		tiSkrzyzowaniePolaczenie.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/skrzyzowaniePas.png")));
		tiSkrzyzowaniePolaczenie.setToolTipText("Tworzy pasy wewnątrz skrzyżowania");
		ToolItem tiSkrzyzowanieTrasa = new ToolItem(toolBar, SWT.PUSH);
		tiSkrzyzowanieTrasa.setText("");
		tiSkrzyzowanieTrasa.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/skrzyzowanieTrasa.png")));
		ToolItem tiGenerator = new ToolItem(toolBar, SWT.PUSH);
		tiGenerator.setText("");
		tiGenerator.setToolTipText("Wstaw generator");
		tiGenerator.setSelection(false);
		tiGenerator.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/generator.png")));
		ToolItem tiPolaczenie = new ToolItem(toolBar, SWT.PUSH);
		
		ToolItem ti0Sep1 = new ToolItem(toolBar, SWT.SEPARATOR);
		
		// Elementy dotyczące rysowania obiektów statycznych
		ToolItem tiLinia = new ToolItem(toolBar, SWT.PUSH);
		tiLinia.setText("");
		tiLinia.setToolTipText("Wstaw linię");
		tiLinia.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/linia.png")));
		ToolItem tiLamana = new ToolItem(toolBar, SWT.PUSH);
		tiLamana.setText("");
		tiLamana.setToolTipText("Wstaw łamaną");
		tiLamana.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/lamana.png")));
		ToolItem tiProstokat = new ToolItem(toolBar, SWT.PUSH);
		tiProstokat.setText("");
		tiProstokat.setToolTipText("Wstaw prostokąt");
		tiProstokat.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/prostokat.png")));
		ToolItem tiOwal = new ToolItem(toolBar, SWT.PUSH);
		tiOwal.setText("");
		tiOwal.setToolTipText("Wstaw owal");
		tiOwal.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/owal.png")));
		ToolItem tiObszar = new ToolItem(toolBar, SWT.PUSH);
		tiObszar.setText("");
		tiObszar.setToolTipText("Wstaw obszar");
		tiObszar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/obszar.png")));
		ToolItem tiObrazek = new ToolItem(toolBar, SWT.PUSH);
		tiObrazek.setText("");
		tiObrazek.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/obrazek.png")));
		ToolItem ti0Sep2 = new ToolItem(toolBar, SWT.SEPARATOR);
		tiPolaczenie.setText("Połączenie");
		tiPolaczenie.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/polaczenie2.jpg")));
		ToolItem tiListaDrog = new ToolItem(toolBar, SWT.PUSH);
		tiListaDrog.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/listadrog.png")));
		ToolItem tiListaSkrzyzowan = new ToolItem(toolBar, SWT.PUSH);
		tiListaSkrzyzowan.setHotImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/listaskrzyzowan.png")));
		tiListaSkrzyzowan.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/listaskrzyzowan.png")));
		
		toolBar.setSize(100,22);
		
		
		tiStrzalka.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				mapaView.setEditMode(EditModes.NORMAL);
				przerysujMape();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		
		tiDroga.addSelectionListener(aWstawDroge);
		tiSkrzyzowanie.addSelectionListener(aWstawSkrzyzowanie);
		tiSkrzyzowanieTrasa.addSelectionListener(aWstawSkrzyzowanieTrasa);
		tiSkrzyzowaniePolaczenie.addSelectionListener(aWstawSkrzyzowaniePas);
		tiGenerator.addSelectionListener(aWstawGenerator);
		tiLinia.addSelectionListener(aWstawLinie);
		tiLamana.addSelectionListener(aWstawPolilinie);
		tiProstokat.addSelectionListener(aWstawProstokat);
		tiOwal.addSelectionListener(aWstawElipsa);
		tiObszar.addSelectionListener(aWstawObszar);
		tiPolaczenie.addSelectionListener(aPolaczWezly);
		tiObrazek.addSelectionListener(aWstawObrazek);
		
		tiDodajPunkt.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				mapaView.setEditMode(EditModes.POINT_ADD);
				przerysujMape();				
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		
		tiUsunPunkt.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				mapaView.setEditMode(EditModes.POINT_REMOVE);
				przerysujMape();				
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
	}

	/**
	 * This method initializes canvas	
	 *
	 */
	private void createCanvas() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalSpan = 2;
		gridData.verticalAlignment = GridData.FILL;
		canvas = new Canvas(sShell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.NO_BACKGROUND);
		canvas.setLayoutData(gridData);
		
		canvas.addPaintListener(new org.eclipse.swt.events.PaintListener() {
			public void paintControl(org.eclipse.swt.events.PaintEvent e) {
				if (!symulowac) {
					mapaView.paintComponent(e.gc, 1);
				} else {
					mapaView.paintDynamic(e.gc, 1);
				}
			}
		});
		
		canvas.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {   
			public void mouseUp(org.eclipse.swt.events.MouseEvent e) {    
				if (mapaView.endAction()) {
					przerysujMape();
				}
			}   
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {    
				mapaView.doAction(e.x, e.y, e.button, true, e.stateMask);
				przerysujMape();
			}
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				mapaView.doAction(e.x, e.y, e.button, false, e.stateMask);
				przerysujMape();
			}
		});
		canvas.addMouseMoveListener(new org.eclipse.swt.events.MouseMoveListener() {   
			public void mouseMove(org.eclipse.swt.events.MouseEvent e) {
				if (mapaView.mouseMove(e.x, e.y, e.button)) {
					przerysujMape();
				}
			}
		
		});
		canvas.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
				if (mapaView.keyPressed(e.character, e.keyCode, e.stateMask)) {
					przerysujMape();
				}
			}
		});
	}

	/**
	 * This method initializes toolBar1	
	 *
	 */
	private void createToolBar1() {
		GridData gridData2 = new GridData();
		gridData2.horizontalSpan = 6;
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = org.eclipse.swt.layout.GridData.END;
		gridData2.grabExcessVerticalSpace = false;
		gridData2.grabExcessHorizontalSpace = false;
		toolBar1 = new ToolBar(sShell, SWT.FLAT);
		toolBar1.setEnabled(false);
		toolBar1.setLayoutData(gridData2);
		ToolItem toolItem3 = new ToolItem(toolBar1, SWT.PUSH);
		toolItem3.setText("                             ");
		toolItem3.setWidth(100);
		ToolItem toolItem4 = new ToolItem(toolBar1, SWT.SEPARATOR);
		ToolItem tiLaczPunktyInfo = new ToolItem(toolBar1, SWT.PUSH);
		tiLaczPunktyInfo.setText("Łączenie punktów:");
		ToolItem tiLaczPunkty = new ToolItem(toolBar1, SWT.PUSH);
		tiLaczPunkty.setText("ON");
		ToolItem tiSepDolny1 = new ToolItem(toolBar1, SWT.SEPARATOR);
	}

	/**
	 * This method initializes composite	
	 *
	 */
	private void createComposite() {
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 17;
		composite = new Composite(sShell, SWT.BORDER);
		composite.setLayout(gridLayout1);
		bPlay = new Button(composite, SWT.NONE);
		bPlay.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/play.png")));
		
		bOneStep = new Button(composite, SWT.NONE);
		bOneStep.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/oneStep.png")));
		bPause = new Button(composite, SWT.NONE);
		bPause.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/pause.png")));
		bStop = new Button(composite, SWT.NONE);
		bStop.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/stop.png")));
		button51 = new Button(composite, SWT.PUSH | SWT.FLAT);
		button51.setEnabled(false);
		button51.setVisible(false);
		bIncTempo = new Button(composite, SWT.NONE);
		bIncTempo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/playFaster.png")));
		bDecTempo = new Button(composite, SWT.NONE);
		bDecTempo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/playSlower.png")));
		button5 = new Button(composite, SWT.PUSH | SWT.FLAT);
		button5.setEnabled(false);
		button5.setVisible(false);
		button6 = new Button(composite, SWT.NONE);
		button6.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/zoomIn.png")));
		button7 = new Button(composite, SWT.NONE);
		button7.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/zoomOut.png")));
		button52 = new Button(composite, SWT.PUSH | SWT.FLAT);
		button52.setEnabled(false);
		button52.setVisible(false);
		bMoveLast = new Button(composite, SWT.NONE);
		bMoveLast.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/naSpod.png")));
		bMoveDown = new Button(composite, SWT.NONE);
		bMoveDown.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/naDol.png")));
		bMoveUp = new Button(composite, SWT.NONE);
		bMoveUp.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/doGory.png")));
		bMoveFirst = new Button(composite, SWT.NONE);
		bMoveFirst.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/naWierzch.png")));
		button5211 = new Button(composite, SWT.PUSH | SWT.FLAT);
		button5211.setEnabled(false);
		button5211.setVisible(false);
		button521 = new Button(composite, SWT.PUSH | SWT.FLAT);
		button521.setEnabled(false);
		button521.setVisible(false);
		
		bPlay.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				startSymulowac();
			}
		});
		bPause.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				stopSymulowac();
			}
		});		
		bOneStep.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				mapaView.wykonajRuch();
				przerysujMape();
			}
		});
		bStop.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				stopSymulowac();
			}
		});
		
		
		bIncTempo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				incTempo();
			}
		});
		bDecTempo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				decTempo();
			}
		});
		
		bMoveLast.addSelectionListener(aPrzesunNaSpod);
		bMoveDown.addSelectionListener(aPrzesunNizej);
		bMoveUp.addSelectionListener(aPrzesunWyzej);
		bMoveFirst.addSelectionListener(aPrzesunNaWierzch);
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		
		Aplikacja thisClass = new Aplikacja();
		thisClass.createSShell();
		thisClass.symulacja.start();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		ResourceHelper.disposeResources();
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 8;
		gridLayout.verticalSpacing = 5;
		gridLayout.marginWidth = 1;
		gridLayout.marginHeight = 0;
		gridLayout.makeColumnsEqualWidth = false;
		gridLayout.horizontalSpacing = 0;
		sShell = new Shell(SWT.SHELL_TRIM);
		sShell.setText("SimTraffic");
		sShell.setLayout(gridLayout);
		createToolBar();
		Label filler316 = new Label(sShell, SWT.NONE);
		Label filler6 = new Label(sShell, SWT.NONE);
		Label filler717 = new Label(sShell, SWT.NONE);
		createComposite();
		Label filler718 = new Label(sShell, SWT.NONE);
		Label filler719 = new Label(sShell, SWT.NONE);
		Label filler720 = new Label(sShell, SWT.NONE);
		Label filler721 = new Label(sShell, SWT.NONE);
		Label filler722 = new Label(sShell, SWT.NONE);
		Label filler723 = new Label(sShell, SWT.NONE);
		Label filler1 = new Label(sShell, SWT.NONE);
		createCanvas();
		sShell.setSize(new Point(1217, 612));
		
		// Wysrodkowanie okna na ekranie
		Rectangle pDisplayBounds = sShell.getDisplay().getBounds();
		int nLeft = (pDisplayBounds.width - sShell.getSize().x) / 2;
		int nTop = (pDisplayBounds.height - sShell.getSize().y) / 2;
		sShell.setBounds(nLeft, nTop, sShell.getSize().x, sShell.getSize().y);
		
		
		menuBar = new Menu(sShell, SWT.BAR);
		MenuItem submenuItem = new MenuItem(menuBar, SWT.CASCADE);
		submenuItem.setText("&Plik");
		MenuItem edycjaSubmenu = new MenuItem(menuBar, SWT.CASCADE);
		edycjaSubmenu.setText("&Edycja");
		MenuItem narzedziaSubmenu = new MenuItem(menuBar, SWT.CASCADE);
		narzedziaSubmenu.setText("&Narzędzia");
		MenuItem pomocSubmenu = new MenuItem(menuBar, SWT.CASCADE);
		submenuEdycja = new Menu(edycjaSubmenu);
		MenuItem mKopiuj = new MenuItem(submenuEdycja, SWT.PUSH);
		mKopiuj.setText("Kopiuj\tCTRL + C");
		mKopiuj.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/ico/copy_edit.gif")));
		mKopiuj.setAccelerator(SWT.CTRL + 'C');
		MenuItem mWklej = new MenuItem(submenuEdycja, SWT.PUSH);
		mWklej.setText("Wklej\tCTRL + V ");
		mWklej.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/ico/paste_edit.gif")));
		mWklej.setAccelerator(SWT.CTRL + 'V');
		
		MenuItem sepEdit0 = new MenuItem(submenuEdycja, SWT.SEPARATOR);
		
		MenuItem mZaznaczWszystko = new MenuItem(submenuEdycja, SWT.PUSH);
		MenuItem pOdznaczWszystkie = new MenuItem(submenuEdycja, SWT.PUSH);
		pOdznaczWszystkie.setText("Odznacz wszystko\tCTRL + SHIFT + A");
		pOdznaczWszystkie.setAccelerator(SWT.CTRL + SWT.SHIFT + 'A');
		
		MenuItem mUsun = new MenuItem(submenuEdycja, SWT.PUSH);
		mUsun.setText("Usuń zaznaczone\tDelete");
		mUsun.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/ico/delete.gif")));
		mZaznaczWszystko.setText("Zaznacz wszystkie\tCTRL + A");
		mZaznaczWszystko.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/ico/selectAll.gif")));
		mZaznaczWszystko.setAccelerator(SWT.CTRL + 'A');

		MenuItem sepEdycja = new MenuItem(submenuEdycja, SWT.SEPARATOR);
		
		/// Przesuwanie obiektów
		MenuItem pPrzesunNaWierzch = new MenuItem(submenuEdycja, SWT.PUSH);
		MenuItem pPrzesunNad = new MenuItem(submenuEdycja, SWT.PUSH);
		MenuItem pPrzesunPod = new MenuItem(submenuEdycja, SWT.PUSH);
		MenuItem pPrzesunNaSpod = new MenuItem(submenuEdycja, SWT.PUSH);

		pPrzesunNaWierzch.setText("Przesuń na wierzch\tHOME");
		pPrzesunNaWierzch.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/naWierzch.png")));
		pPrzesunNad.setText("Przesun wyrzej\tPAGE UP");
		pPrzesunNad.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/doGory.png")));
		pPrzesunPod.setText("Przesun niżej\tPAGE DOWN");
		pPrzesunPod.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/naDol.png")));
		pPrzesunNaSpod.setText("Przesuń na spod\tEND");
		pPrzesunNaSpod.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/naSpod.png")));
		
		pPrzesunNaWierzch.setAccelerator(SWT.HOME);
		pPrzesunNad.setAccelerator(SWT.PAGE_UP);
		pPrzesunPod.setAccelerator(SWT.PAGE_DOWN);
		pPrzesunNaSpod.setAccelerator(SWT.END);
		
		pPrzesunNad.addSelectionListener(aPrzesunNaWierzch);
		pPrzesunNaSpod.addSelectionListener(aPrzesunNaSpod);
		pPrzesunNad.addSelectionListener(aPrzesunWyzej);
		pPrzesunPod.addSelectionListener(aPrzesunNizej);
		
		// Koniec przesuwania obiektów

		
		MenuItem sepEdycja1 = new MenuItem(submenuEdycja, SWT.SEPARATOR);
		
		MenuItem pGrupuj = new MenuItem(submenuEdycja, SWT.PUSH);
		pGrupuj.setText("Grupuj\tCTRL + G");
		pGrupuj.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/ico/group.gif")));
		pGrupuj.setAccelerator(SWT.CTRL + 'G');
		pGrupuj.addSelectionListener(aGrupujObiekty);
		
		MenuItem pRozgrupuj = new MenuItem(submenuEdycja, SWT.PUSH);
		pRozgrupuj.setText("Rozgrupuj\tCTRL + SHIFT + G");
		pRozgrupuj.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/ico/ungroup.gif")));
		pRozgrupuj.setAccelerator(SWT.CTRL + SWT.SHIFT + 'G');
		pRozgrupuj.addSelectionListener(aRozgrupujObiekty);
		
		MenuItem sepEdycja2 = new MenuItem(submenuEdycja, SWT.SEPARATOR);
		
		MenuItem pWlasciwosciKanwy = new MenuItem(submenuEdycja, SWT.PUSH);
		pWlasciwosciKanwy.setText("&Właściwości Kanwy\tCTRL + P");
		pWlasciwosciKanwy.setAccelerator(SWT.CTRL + 'P');
		pWlasciwosciKanwy.addSelectionListener(aWlasciwosciKanwy);
		
		edycjaSubmenu.setMenu(submenuEdycja);
		submenuPomoc = new Menu(pomocSubmenu);
		MenuItem pOProgramie = new MenuItem(submenuPomoc, SWT.PUSH);
		pOProgramie.setText("O programie\tCTRL + F1");
		pomocSubmenu.setText("Pomo&c");
		pomocSubmenu.setMenu(submenuPomoc);
		submenuNarzedzia = new Menu(narzedziaSubmenu);
		
		MenuItem pWstawDroge = new MenuItem(submenuNarzedzia, SWT.PUSH);
		
		pWstawDroge.setText("Wstaw drogę\tD");
		pWstawDroge.setAccelerator('D');
		pWstawDroge.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/droga.png")));
		pWstawDroge.addSelectionListener(aWstawDroge);
		
		MenuItem pWstawSkrzyżowanie = new MenuItem(submenuNarzedzia, SWT.PUSH);
		pWstawSkrzyżowanie.setText("Wstaw skrzyżowanie\tS");
		pWstawSkrzyżowanie.setAccelerator('S');
		pWstawSkrzyżowanie.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/skrzyzowanie.png")));
		pWstawSkrzyżowanie.addSelectionListener(aWstawSkrzyzowanie);
		
		MenuItem pWstawGenerator = new MenuItem(submenuNarzedzia, SWT.PUSH);
		pWstawGenerator.setText("Wstaw generator\tG");
		pWstawGenerator.setAccelerator('G');
		pWstawGenerator.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/generator.png")));
		pWstawGenerator.addSelectionListener(aWstawGenerator);
		
		MenuItem pUtworzPolączenie = new MenuItem(submenuNarzedzia, SWT.PUSH);
		pUtworzPolączenie.setText("Utwórz połączenie\tF");
		pUtworzPolączenie.setAccelerator('F');
		pUtworzPolączenie.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/polaczenie2.png")));
		pUtworzPolączenie.addSelectionListener(aPolaczWezly);
		
		MenuItem sepNarzedzia0 = new MenuItem(submenuNarzedzia, SWT.SEPARATOR);
		MenuItem pWstawLinie = new MenuItem(submenuNarzedzia, SWT.PUSH);
		pWstawLinie.setText("Wstaw prostą\tALT + Q");
		pWstawLinie.setAccelerator(SWT.ALT + 'Q');
		pWstawLinie.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/linia.png")));
		MenuItem pWstawŁamaną = new MenuItem(submenuNarzedzia, SWT.PUSH);
		pWstawŁamaną.setText("Wstaw łamaną\tALT + W");
		pWstawŁamaną.setAccelerator(SWT.ALT + 'W');
		
		pWstawŁamaną.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/lamana.png")));
		MenuItem pWstawProstokąt = new MenuItem(submenuNarzedzia, SWT.PUSH);
		pWstawProstokąt.setText("Wstaw prostokąt\tALT + E");
		pWstawProstokąt.setAccelerator(SWT.ALT + 'E');
		pWstawProstokąt.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/prostokat.png")));
		
		MenuItem pWstawElipsę = new MenuItem(submenuNarzedzia, SWT.PUSH);
		pWstawElipsę.setText("Wstaw elipsę\tALT + R");
		pWstawElipsę.setAccelerator(SWT.ALT + 'R');
		pWstawElipsę.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/owal.png")));
		
		MenuItem pWstawObszar = new MenuItem(submenuNarzedzia, SWT.PUSH);
		pWstawObszar.setText("Wstaw obszar\tALT + T");
		pWstawObszar.setAccelerator(SWT.ALT + 'T');
		pWstawObszar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/obszar.png")));
		
		MenuItem pWstawObraz = new MenuItem(submenuNarzedzia, SWT.PUSH);
		pWstawObraz.setText("Wstaw obrazek\tALT + Y");
		pWstawObraz.setAccelerator(SWT.ALT + 'Y');
		pWstawObraz.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/obrazek.png")));
		MenuItem sepNarzędzia1 = new MenuItem(submenuNarzedzia, SWT.SEPARATOR);

		MenuItem cWysokaJakosc = new MenuItem(submenuNarzedzia, SWT.CHECK);
		cWysokaJakosc.setText("Wysoka jakość grafiki\tF10");
		cWysokaJakosc.setAccelerator(SWT.F10);
		cWysokaJakosc.setSelection(mapaView.isWysokaJakosc());
		cWysokaJakosc.addSelectionListener(aWysokaJakosc);

		
		MenuItem cRysujSiatke = new MenuItem(submenuNarzedzia, SWT.CHECK);
		cRysujSiatke.setText("Rysuj siatkę\tF11");
		cRysujSiatke.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/siatka.png")));
		cRysujSiatke.setAccelerator(SWT.F11);
		cRysujSiatke.setSelection(mapaView.isRysujSiatke());
		cRysujSiatke.addSelectionListener(aRysujSiatke);
		
		MenuItem cRysujProwadnice = new MenuItem(submenuNarzedzia, SWT.CHECK);
		cRysujProwadnice.setText("Rysuj prowadnice\tF12");
		cRysujProwadnice.setAccelerator(SWT.F12);
		cRysujProwadnice.setSelection(mapaView.isRysujProwadnice());
		cRysujProwadnice.addSelectionListener(aRysujProwadnice);
		
		MenuItem cLaczPunkty = new MenuItem(submenuNarzedzia, SWT.CHECK);
		cLaczPunkty.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/wspolneWierzcholki.png")));
		cLaczPunkty.setText("Łącz punkty\tInsert");
		cLaczPunkty.setAccelerator(SWT.INSERT);
		cLaczPunkty.setSelection(mapaView.isWspolnePunkty());
		cLaczPunkty.addSelectionListener(aWspolnePunkty);

		narzedziaSubmenu.setMenu(submenuNarzedzia);
		submenuPlik = new Menu(submenuItem);
		submenuItem.setMenu(submenuPlik);
		MenuItem pNowy = new MenuItem(submenuPlik, SWT.PUSH);
		pNowy.setText("&Nowy\tCTRL + N");
		pNowy.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/icons/file_obj.gif")));
		pNowy.setAccelerator(SWT.CTRL + 'N');
		MenuItem sepPlik0 = new MenuItem(submenuPlik, SWT.SEPARATOR);
		MenuItem pOtwórz = new MenuItem(submenuPlik, SWT.PUSH);
		pOtwórz.setText("&Otwórz szybko\tCTRL + O");
		pOtwórz.setAccelerator(SWT.CTRL + 'O');
		MenuItem pZapisz = new MenuItem(submenuPlik, SWT.PUSH);
		MenuItem sepPlik1 = new MenuItem(submenuPlik, SWT.SEPARATOR);
		
		MenuItem pOtwórzZ = new MenuItem(submenuPlik, SWT.PUSH);
		pOtwórzZ.setText("Otwórz z ...\tCTRL + SHIFT + O");
		pOtwórzZ.setAccelerator(SWT.CTRL + SWT.SHIFT + 'O');
		
		pZapisz.setText("Zapisz szybko\tCTRL + S");
		pZapisz.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/icons/etool16/save_edit.gif")));
		pZapisz.setAccelerator(SWT.CTRL + 'S');
		MenuItem pZapiszJako = new MenuItem(submenuPlik, SWT.PUSH);
		pZapiszJako.setText("Zapisz jako...\tCTRL + SHIFT + S");
		pZapiszJako.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/icons/etool16/saveas_edit.gif")));
		pZapiszJako.setAccelerator(SWT.CTRL + SWT.SHIFT + 'S');

		MenuItem sep0 = new MenuItem(submenuPlik, SWT.SEPARATOR);
		MenuItem pZakończ = new MenuItem(submenuPlik, SWT.PUSH);
		pZakończ.setText("&Zakończ\tCTRL+Q");
		pZakończ.setAccelerator(SWT.CTRL + 'Q');
		sShell.setMenuBar(menuBar);
		
		Label filler8 = new Label(sShell, SWT.NONE);
		Label filler5 = new Label(sShell, SWT.NONE);
		Label filler2 = new Label(sShell, SWT.NONE);
		Label filler9 = new Label(sShell, SWT.NONE);
		Label filler315 = new Label(sShell, SWT.NONE);
		Label filler4 = new Label(sShell, SWT.NONE);
		Label filler = new Label(sShell, SWT.NONE);
		Label filler7 = new Label(sShell, SWT.NONE);
		Label filler311 = new Label(sShell, SWT.NONE);
		Label filler312 = new Label(sShell, SWT.NONE);
		Label filler313 = new Label(sShell, SWT.NONE);
		Label filler314 = new Label(sShell, SWT.NONE);
		Label filler3 = new Label(sShell, SWT.NONE);
		createToolBar1();
		
		sShell.addDisposeListener(new org.eclipse.swt.events.DisposeListener() {
			public void widgetDisposed(org.eclipse.swt.events.DisposeEvent e) {
				System.out.println("Aplikacja.widgetDisposed()");
				ResourceHelper.disposeResources();
				mapaView.disposeResources();
			}
		});
		sShell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
			public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
				sShell.dispose();
				System.out.println(".shellClosed(): Dispose Shell" );
			}
		});
		
		pNowy.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				MessageBox messageBox = new MessageBox(sShell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
				messageBox.setMessage("Czy na pewno usunąć wszystkie obiekty z mapy?");
				messageBox.setText("Nowa mapa");
				if (messageBox.open() == SWT.YES) {
					mapaView.setObiekty(new Grupa());
					przerysujMape();
				}
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		
		pOtwórzZ.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				FileDialog dialog = new FileDialog(sShell);
				dialog.setFilterPath((new File("./" + KATALOG_ZAPISU)).getAbsolutePath());
				dialog.setFilterExtensions(new String[]{"*.sav","*.*"});
				dialog.setFilterNames(new String[]{"Plansze symulacji (*.sav)", "Wszystkie pliki (*.*)"});
				dialog.setText("Otwórz mapę dróg");
				String path = dialog.open();
				
				if (path != null) {
					wczytajObiekty(path);
				}
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		pOtwórz.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				MessageBox messageBox = new MessageBox(sShell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
				messageBox.setMessage("Czy na pewno wczytać obiekty i skasować obecną zawartość mapy?");
				messageBox.setText("Wczytanie obiektów");
				if (messageBox.open() == SWT.YES) {
					wczytajObiekty(PLIK_SZYBKIEGO_ZAPISU);
				}
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		pZapisz.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				MessageBox messageBox = new MessageBox(sShell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
				messageBox.setMessage("Czy na pewno zapisać bieżącą mapę?");
				messageBox.setText("Zapis obiektów");
				if (messageBox.open() == SWT.YES) {
					Zapis.zapiszObiekty(PLIK_SZYBKIEGO_ZAPISU, mapaView.getObiekty());
				}
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		pZapiszJako.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				FileDialog dialog = new FileDialog(sShell, SWT.SAVE);
				dialog.setFilterPath((new File("./" + KATALOG_ZAPISU)).getAbsolutePath());
				dialog.setFilterExtensions(new String[]{"*.sav","*.*"});
				dialog.setFilterNames(new String[]{"Plansze symulacji (*.sav)", "Wszystkie pliki (*.*)"});
				dialog.setText("Zapisz mapę dróg");
				String path = dialog.open();
				
				if (path != null) {
					boolean zapisac = true;
					if ((new File(path)).exists()) {
						//SWT.ABORT, SWT.OK, SWT.CANCEL, SWT.RETRY, SWT.IGNORE, SWT.YES
						MessageBox messageBox = new MessageBox(sShell, SWT.YES | SWT.NO | SWT.ICON_WARNING);
						messageBox.setMessage("Zastąpić bieżący plik danych?");
						messageBox.setText("Zapis pliku");
						if (messageBox.open() != SWT.YES) {
							zapisac = false;
						}
					}
					
					if (zapisac) {
						Zapis.zapiszObiekty(path, mapaView.getObiekty());
					}
				}
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		
		mKopiuj.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				mapaView.kopiuj();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		
		mWklej.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				mapaView.wklej();
				przerysujMape();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		
		mZaznaczWszystko.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				mapaView.zaznaczWszystkie();
				przerysujMape();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});

		mUsun.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				mapaView.usunObiekty();
				przerysujMape();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
	}

	private void wczytajObiekty(String plik) {
		Grupa obiekty = Zapis.odczytajObiekty(plik);
		if (obiekty != null) {
			mapaView.setObiekty(obiekty);
			mapaView.updateData();
			przerysujMape();
		}
	}
	
	protected void przerysujMape() {
		canvas.redraw();
		canvas.update();
	}

}
