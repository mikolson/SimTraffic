package pl.wroc.pwr.iis.traffic.presentation.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import pl.wroc.pwr.iis.traffic.presentation.control.Odswiezanie;
import pl.wroc.pwr.iis.traffic.presentation.model.ResourceHelper;
import pl.wroc.pwr.iis.traffic.presentation.view.MapaView;

public class WlasciwosciKanwy {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="174,85"
	private CLabel lKolorTla = null;
	private Canvas cKolorTla = null;
	private Button bKolorTla = null;
	private Label lOdlegloscPunktów = null;
	private Spinner sOdleglosc = null;
	private Composite cPrzyciskiAkceptacji = null;
	private Button bOk = null;
	private Button bAnuluj = null;
	private CLabel lKolorSiatki = null;
	private Canvas cKolorSiatki = null;
	private Button bKolorSiatki = null;
	private MapaView mapaObiektow;  //  @jve:decl-index=0:
	private CLabel lKolorProwadnic = null;
	private Canvas cKolorProwdnic = null;
	private Button bKolorProwadnic = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 2;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.grabExcessHorizontalSpace = true;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		sShell = new Shell(SWT.TITLE | SWT.CLOSE | SWT.APPLICATION_MODAL | SWT.BORDER);
		sShell.setText("Właściwości kanwy");
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(246, 204));
//		sShell.pack();
//		 Wysrodkowanie okna na ekranie
		Odswiezanie.wysrodkujOkno(sShell);		
		
		lOdlegloscPunktów = new Label(sShell, SWT.NONE);
		lOdlegloscPunktów.setText("Odległość punktów: ");
		sOdleglosc = new Spinner(sShell, SWT.BORDER);
		sOdleglosc.setLayoutData(gridData1);
		lKolorTla = new CLabel(sShell, SWT.NONE);
		lKolorTla.setText("Kolor tła:");
		createCKolorTla();
		bKolorTla = new Button(sShell, SWT.NONE);
		bKolorTla.setText("Wybi&erz ...");
		bKolorTla.setLayoutData(gridData2);
		bKolorTla.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				ColorDialog dKolor = new ColorDialog(sShell);
				RGB kolor = dKolor.open();
				if (kolor != null) {
					Color tlo = ResourceHelper.getColor(kolor.red, kolor.green, kolor.blue);
					cKolorTla.setBackground(tlo);
				}
			}
		});
		lKolorSiatki = new CLabel(sShell, SWT.NONE);
		lKolorSiatki.setText("Kolor siatki:");
		createCKolorSiatki();
		bKolorSiatki = new Button(sShell, SWT.NONE);
		bKolorSiatki.setText("Wybi&erz ...");
		bKolorSiatki.setLayoutData(gridData5);
		lKolorProwadnic = new CLabel(sShell, SWT.NONE);
		lKolorProwadnic.setText("Kolor prowadnic:");
		createCKolorProwdnic();
		bKolorProwadnic = new Button(sShell, SWT.NONE);
		bKolorProwadnic.setText("Wybi&erz ...");
		bKolorProwadnic.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						ColorDialog dKolor = new ColorDialog(sShell);
						RGB kolor = dKolor.open();
						if (kolor != null) {
							Color tlo = ResourceHelper.getColor(kolor.red, kolor.green, kolor.blue);
							cKolorProwdnic.setBackground(tlo);
						}
					}
				});
		bKolorSiatki.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						ColorDialog dKolor = new ColorDialog(sShell);
						RGB kolor = dKolor.open();
						if (kolor != null) {
							Color tlo = ResourceHelper.getColor(kolor.red, kolor.green, kolor.blue);
							cKolorSiatki.setBackground(tlo);
						}
					}
				});
		createCPrzyciskiAkceptacji();
		sShell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
			public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
				sShell.dispose();
			}
		});
	}

	/**
	 * This method initializes cKolorTla	
	 *
	 */
	private void createCKolorTla() {
		GridData gridData = new GridData();
		gridData.heightHint = 25;
		gridData.widthHint = 25;
		cKolorTla = new Canvas(sShell, SWT.BORDER);
		cKolorTla.setLayoutData(gridData);
	}

	/**
	 * This method initializes cPrzyciskiAkceptacji	
	 *
	 */
	private void createCPrzyciskiAkceptacji() {
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.CENTER;
		gridData3.horizontalSpan = 3;
		gridData3.grabExcessHorizontalSpace = false;
		gridData3.grabExcessVerticalSpace = true;
		gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		cPrzyciskiAkceptacji = new Composite(sShell, SWT.NONE);
		cPrzyciskiAkceptacji.setLayoutData(gridData3);
		cPrzyciskiAkceptacji.setLayout(gridLayout1);
		bOk = new Button(cPrzyciskiAkceptacji, SWT.NONE);
		bOk.setText("   &Ok   ");
		bOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				zapiszWlasciwosci();
				sShell.close();
			}
		});
		bAnuluj = new Button(cPrzyciskiAkceptacji, SWT.NONE);
		bAnuluj.setText("&Anuluj");
		bAnuluj.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				sShell.close();
			}
		});
	}

	/**
	 * This method initializes cKolorSiatki	
	 *
	 */
	private void createCKolorSiatki() {
		GridData gridData4 = new GridData();
		gridData4.widthHint = 25;
		gridData4.heightHint = 25;
		cKolorSiatki = new Canvas(sShell, SWT.BORDER);
		cKolorSiatki.setLayoutData(gridData4);
	}

	private void wczytajWlasciwosci() {
		cKolorTla.setBackground(mapaObiektow.getKolorTla());
		cKolorSiatki.setBackground(mapaObiektow.getKolorSiatki());
		cKolorProwdnic.setBackground(mapaObiektow.getKolorProwadnic());
		sOdleglosc.setSelection(mapaObiektow.getGestoscSiatki());
	}
	
	private void zapiszWlasciwosci() {
		mapaObiektow.setKolorTla(cKolorTla.getBackground());
		mapaObiektow.setKolorSiatki(cKolorSiatki.getBackground());
		mapaObiektow.setKolorProwadnic(cKolorProwdnic.getBackground());
		mapaObiektow.setGestoscSiatki(sOdleglosc.getSelection());
	}
	
	public void open(MapaView mapaObiektow) {
		this.mapaObiektow = mapaObiektow;
		
		createSShell();
		wczytajWlasciwosci();
		sShell.open();
	}

	/**
	 * This method initializes cKolorProwdnic	
	 *
	 */
	private void createCKolorProwdnic() {
		GridData gridData6 = new GridData();
		gridData6.heightHint = 25;
		gridData6.widthHint = 25;
		cKolorProwdnic = new Canvas(sShell, SWT.BORDER);
		cKolorProwdnic.setLayoutData(gridData6);
	}
}
