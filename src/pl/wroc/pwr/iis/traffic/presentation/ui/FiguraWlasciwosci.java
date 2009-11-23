package pl.wroc.pwr.iis.traffic.presentation.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import pl.wroc.pwr.iis.traffic.presentation.control.Odswiezanie;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;
import pl.wroc.pwr.iis.traffic.presentation.view.figury.FiguraView;

public class FiguraWlasciwosci {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="6,8"
	private Group gWlasciwosciLinii = null;
	private Group gWlasciwosciTla = null;
	private Composite cPrzyciskiSterujace = null;
	private CLabel cLabel = null;
	private CLabel cLabel1 = null;
	private CLabel lKolorLinii = null;
	private Combo cStylLinii = null;
	private Spinner sSzerokoscLinii = null;
	private Button bKolorLinii = null;
	private CLabel lKolorTla = null;
	private Button bOk = null;
	private Button bAnuluj = null;
	private Canvas cKolorTla = null;
	private Button bKolorTla = null;
	private Canvas cKolorLinii = null;
	
	private ColorDialog dKolor;
	private FiguraView figura;  //  @jve:decl-index=0:
	
	private Color tlo, linia;
	private int[] styleLinii = new int[] {SWT.LINE_SOLID, SWT.LINE_DASH, SWT.LINE_DOT, SWT.LINE_DASHDOT, SWT.LINE_DASHDOTDOT};

	KeyListener keyListner = new org.eclipse.swt.events.KeyAdapter() {
		public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
			if (e.character == SWT.CR) {
				setAndClose();
			} else if (e.character == SWT.ESC) {
				sShell.close();
			}
		}
	};
	private Button bRysujTlo = null;
	
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		sShell = new Shell(SWT.TITLE | SWT.CLOSE | SWT.APPLICATION_MODAL);
		sShell.setText("Właściwości figury");
		sShell.setSize(new Point(210, 289));
		Odswiezanie.wysrodkujOkno(sShell);
		
		sShell.setLayout(gridLayout);
		createGWlasciwosciLinii();
		createGWlasciwosciTla();
		createCPrzyciskiSterujace();
		sShell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
			public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
				sShell.dispose();
			}
		});
	}
	/**
	 * This method initializes gWlasciwosciLinii	
	 *
	 */
	private void createGWlasciwosciLinii() {
		GridData gridData21 = new GridData();
		gridData21.grabExcessHorizontalSpace = true;
		gridData21.verticalAlignment = GridData.CENTER;
		gridData21.horizontalSpan = 2;
		gridData21.horizontalAlignment = GridData.FILL;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.grabExcessHorizontalSpace = true;
		gridData11.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 3;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		gWlasciwosciLinii = new Group(sShell, SWT.NONE);
		gWlasciwosciLinii.setText("Właściwości linii");
		gWlasciwosciLinii.setLayoutData(gridData);
		gWlasciwosciLinii.setLayout(gridLayout1);
		cLabel = new CLabel(gWlasciwosciLinii, SWT.NONE);
		cLabel.setText("Szerokość:");
		sSzerokoscLinii = new Spinner(gWlasciwosciLinii, SWT.BORDER);
		sSzerokoscLinii.setLayoutData(gridData21);
		
		sSzerokoscLinii.addKeyListener(keyListner);
		
		cLabel1 = new CLabel(gWlasciwosciLinii, SWT.NONE);
		cLabel1.setText("Styl:");
		createCStylLinii();
		lKolorLinii = new CLabel(gWlasciwosciLinii, SWT.NONE);
		lKolorLinii.setText("Kolor:");
		createCKolorLinii();
		bKolorLinii = new Button(gWlasciwosciLinii, SWT.PUSH);
		bKolorLinii.setText("&Wybierz ...");
		bKolorLinii.setForeground(new Color(Display.getCurrent(), 0, 123, 0));
		bKolorLinii.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
		bKolorLinii.setImage(null);
		bKolorLinii.setLayoutData(gridData11);
		bKolorLinii.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				dKolor = new ColorDialog(sShell);
				RGB kolor = dKolor.open();
				if (kolor != null) {
					linia = ResourceHelper.getColor(kolor.red, kolor.green, kolor.blue);
					cKolorLinii.setBackground(linia);
				}
			}
		});
	}
	/**
	 * This method initializes gWlasciwosciTla	
	 *
	 */
	private void createGWlasciwosciTla() {
		GridData gridData9 = new GridData();
		gridData9.horizontalSpan = 3;
		gridData9.horizontalAlignment = GridData.FILL;
		gridData9.verticalAlignment = GridData.CENTER;
		gridData9.grabExcessHorizontalSpace = true;
		GridData gridData7 = new GridData();
		gridData7.grabExcessHorizontalSpace = true;
		gridData7.verticalAlignment = GridData.CENTER;
		gridData7.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 3;
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		gWlasciwosciTla = new Group(sShell, SWT.NONE);
		gWlasciwosciTla.setText("Właściwości tła");
		gWlasciwosciTla.setEnabled(true);
		gWlasciwosciTla.setLayout(gridLayout2);
		gWlasciwosciTla.setLayoutData(gridData1);
		bRysujTlo = new Button(gWlasciwosciTla, SWT.TOGGLE);
		bRysujTlo.setText("Rysuj tło");
		bRysujTlo.setSelection(true);
		bRysujTlo.setLayoutData(gridData9);
		lKolorTla = new CLabel(gWlasciwosciTla, SWT.NONE);
		lKolorTla.setText("Kolor:        ");
		createCanvas();
		bKolorTla = new Button(gWlasciwosciTla, SWT.NONE);
		bKolorTla.setText("Wybi&erz ...");
		bKolorTla.setLayoutData(gridData7);
		bKolorTla.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				dKolor = new ColorDialog(sShell);
				RGB kolor = dKolor.open();
				if (kolor != null) {
					tlo = ResourceHelper.getColor(kolor.red, kolor.green, kolor.blue);
					cKolorTla.setBackground(tlo);
				}
			}
		});
		
		bRysujTlo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				wlaczWylaczWybieranieKoloruTla();
			}
		});
	}
	
	private void wlaczWylaczWybieranieKoloruTla() {
		if (!figura.isMozliweTlo()) {
			gWlasciwosciTla.setEnabled(false);
			bRysujTlo.setEnabled(false);
			lKolorTla.setEnabled(false);
			bKolorTla.setEnabled(false);
		} else {
			if (bRysujTlo.getSelection()) {
				bKolorTla.setEnabled(true);
			} else {
				bKolorTla.setEnabled(false);
			}
		}
	}
	
	/**
	 * This method initializes cPrzyciskiSterujace	
	 *
	 */
	private void createCPrzyciskiSterujace() {
		GridData gridData6 = new GridData();
		gridData6.widthHint = 80;
		GridData gridData5 = new GridData();
		gridData5.widthHint = 80;
		GridLayout gridLayout3 = new GridLayout();
		gridLayout3.numColumns = 2;
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		gridData2.grabExcessVerticalSpace = true;
		gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		cPrzyciskiSterujace = new Composite(sShell, SWT.NONE);
		cPrzyciskiSterujace.setLayoutData(gridData2);
		cPrzyciskiSterujace.setLayout(gridLayout3);
		bOk = new Button(cPrzyciskiSterujace, SWT.NONE);
		bOk.setText("&Ok");
		bOk.setLayoutData(gridData5);
		bOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				setAndClose();
			}
		});
		bOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
			}
		});
		bAnuluj = new Button(cPrzyciskiSterujace, SWT.NONE);
		bAnuluj.setText("&Anuluj");
		bAnuluj.setLayoutData(gridData6);
		bAnuluj.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				sShell.close();
			}
		});
	}
	
	protected void setAndClose() {
		ustawPola();
		sShell.close();
	}
	/**
	 * This method initializes cStylLinii	
	 *
	 */
	private void createCStylLinii() {
		GridData gridData3 = new GridData();
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = GridData.CENTER;
		gridData3.horizontalSpan = 2;
		gridData3.horizontalAlignment = GridData.FILL;
		cStylLinii = new Combo(gWlasciwosciLinii, SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		cStylLinii.setItems(new String[]{"___________________" , "___  ___  ___  ___", "_ _ _ _ _ _ _ _ _ _ _ _", "___ _ ___ _ ___ _ ___ _", "___ _ _ ___ _ _ ___ _ _ ___"});
		cStylLinii.addKeyListener(keyListner);
		cStylLinii.setLayoutData(gridData3);
	}
	public void open(FiguraView figura, int x, int y) {
		this.figura = figura;
		createSShell();
//		sShell.setBounds(x, y, SZEROKOSC, WYSOKOSC);
		
		wczytajPola();
		sShell.open();
	}
	
	private void wczytajPola() {
		sSzerokoscLinii.setSelection(this.figura.getSzerokoscLini());
		
		linia = new Color(sShell.getDisplay(), this.figura.getKolorLini().getRGB());
		tlo = new Color(sShell.getDisplay(), this.figura.getKolorTla().getRGB());

		cKolorLinii.setBackground(this.figura.getKolorLini());
		cKolorTla.setBackground(this.figura.getKolorTla());
		
		tlo = this.figura.getKolorTla();
		linia = this.figura.getKolorLini();
		
		cStylLinii.select(0);
		for (int i = 0; i < styleLinii.length; i++) {
			if (styleLinii[i] == this.figura.getStylLinii()) {
				cStylLinii.select(i);
				break;
			}
		}
		
		bRysujTlo.setSelection(this.figura.isRysujTlo());
		wlaczWylaczWybieranieKoloruTla();
	}
	
	private void ustawPola() {
		figura.setSzerokoscLini(sSzerokoscLinii.getSelection());
		figura.setKolorLini(linia);
		figura.setKolorTla(tlo);
		figura.setStylLinii(styleLinii[cStylLinii.getSelectionIndex()]);
		figura.setRysujTlo(bRysujTlo.getSelection());
	}
	/**
	 * This method initializes canvas	
	 *
	 */
	private void createCanvas() {
		GridData gridData4 = new GridData();
		gridData4.widthHint = 21;
		gridData4.horizontalAlignment = GridData.CENTER;
		gridData4.verticalAlignment = GridData.CENTER;
		gridData4.heightHint = 21;
		cKolorTla = new Canvas(gWlasciwosciTla, SWT.BORDER);
		cKolorTla.setLayoutData(gridData4);
	}
	/**
	 * This method initializes cKolorLinii	
	 *
	 */
	private void createCKolorLinii() {
		GridData gridData8 = new GridData();
		gridData8.heightHint = 21;
		gridData8.widthHint = 21;
		cKolorLinii = new Canvas(gWlasciwosciLinii, SWT.BORDER);
		cKolorLinii.setLayoutData(gridData8);
	}

}
