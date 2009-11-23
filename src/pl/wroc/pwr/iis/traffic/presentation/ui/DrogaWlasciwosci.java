package pl.wroc.pwr.iis.traffic.presentation.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import pl.wroc.pwr.iis.traffic.presentation.control.Odswiezanie;
import pl.wroc.pwr.iis.traffic.presentation.view.DrogaView;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DrogaWlasciwosci {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="9,7"
	private CLabel lIloscPasow = null;
	private CLabel lIloscPasowLewych = null;
	private CLabel cLabel = null;
	private Spinner sIloscPasow = null;
	private Spinner sPasowLewych = null;
	private Spinner sMaxPredkosc = null;
	private Composite cPrzyciskiDolne = null;
	private Button bOk = null;
	private Button bAnuluj = null;
	private DrogaView drogaView;
	
	
	private KeyListener listner = new org.eclipse.swt.events.KeyAdapter() {
		public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
			if (e.keyCode == SWT.ESC) {
				sShell.close();  //  @jve:decl-index=0:
			} else if (e.keyCode == SWT.LF) {
				System.out.println("OK");
				ustawDane();
				sShell.close();
			}
			
			System.out.println("DrogaWlasciwosci.createSShell(): " + e);
		}
	};
	private Label lDlugosc = null;
	private Text tDlugosc = null;
	
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridData gridData8 = new GridData();
		gridData8.grabExcessHorizontalSpace = false;
		gridData8.verticalAlignment = GridData.CENTER;
		gridData8.horizontalAlignment = GridData.FILL;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.horizontalAlignment = GridData.FILL;
		GridData gridData1 = new GridData();
		gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		GridData gridData = new GridData();
		gridData.heightHint = 26;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		sShell = new Shell(SWT.TITLE | SWT.CLOSE | SWT.APPLICATION_MODAL);
		sShell.setText("Właściwości drogi");
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(276, 180));
		
		Odswiezanie.wysrodkujOkno(sShell);
		
		lIloscPasow = new CLabel(sShell, SWT.NONE);
		lIloscPasow.setText("Ilość pasów:  ");
		lIloscPasow.setLayoutData(gridData1);
		
		sIloscPasow = new Spinner(sShell, SWT.BORDER);
		sIloscPasow.setSelection(this.drogaView.getIloscPasow());
		sIloscPasow.setLayoutData(gridData2);
		sIloscPasow.addKeyListener(listner);
		
		lIloscPasowLewych = new CLabel(sShell, SWT.NONE);
		lIloscPasowLewych.setText("Pas środkowy:  ");
		lIloscPasowLewych.setLayoutData(gridData);
		
		sPasowLewych = new Spinner(sShell, SWT.BORDER);
		sPasowLewych.setLayoutData(gridData3);
		sPasowLewych.setSelection(this.drogaView.getIloscPasowPrawych());
		sPasowLewych.addKeyListener(listner);
		
		cLabel = new CLabel(sShell, SWT.NONE);
		cLabel.setText("Maksymalna prędkosć (m/s):  ");
		
		sMaxPredkosc = new Spinner(sShell, SWT.BORDER);
		sMaxPredkosc.setMaximum(50);
		sMaxPredkosc.setLayoutData(gridData4);
		sMaxPredkosc.setSelection(this.drogaView.getMaxPredkosc());
		lDlugosc = new Label(sShell, SWT.NONE);
		lDlugosc.setText("Długość drogi:");
		tDlugosc = new Text(sShell, SWT.BORDER);
		tDlugosc.setEditable(false);
		tDlugosc.setLayoutData(gridData8);
		tDlugosc.setText(this.drogaView.getDroga().getDlugosc(0)+" m");
		sMaxPredkosc.addKeyListener(listner);
		
		createCPrzyciskiDolne();
		
		sShell.addKeyListener(listner);
		sShell.addTraverseListener(new org.eclipse.swt.events.TraverseListener() {
			public void keyTraversed(org.eclipse.swt.events.TraverseEvent e) {
				System.out.println("keyTraversed()"); // TODO Auto-generated Event stub keyTraversed()
			}
		});
	}
	/**
	 * This method initializes cPrzyciskiDolne	
	 *
	 */
	private void createCPrzyciskiDolne() {
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = org.eclipse.swt.SWT.HORIZONTAL;
		rowLayout.marginTop = 10;
		RowData rowData1 = new org.eclipse.swt.layout.RowData();
		rowData1.width = 100;
		RowData rowData = new org.eclipse.swt.layout.RowData();
		rowData.width = 100;
		GridData gridData5 = new GridData();
		gridData5.horizontalSpan = 2;
		gridData5.grabExcessVerticalSpace = true;
		gridData5.horizontalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		gridData5.verticalAlignment = GridData.FILL;
		gridData5.grabExcessHorizontalSpace = true;
		cPrzyciskiDolne = new Composite(sShell, SWT.NONE);
		cPrzyciskiDolne.setLayoutData(gridData5);
		cPrzyciskiDolne.setLayout(rowLayout);
		bOk = new Button(cPrzyciskiDolne, SWT.NONE);
		bOk.setText("&Ok");
		bOk.setLayoutData(rowData);
		bOk.addKeyListener(listner);
		
		bAnuluj = new Button(cPrzyciskiDolne, SWT.NONE);
		bAnuluj.setText("&Anuluj");
		bAnuluj.setLayoutData(rowData1);
		bAnuluj.addKeyListener(listner);
		
		// Anuluj
		bAnuluj.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				sShell.close();
			}
		});
		
		// OK
		bOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				ustawDane();
				sShell.close();
			}
		});
	}

	public DrogaWlasciwosci() {
	}
	
	public void open(DrogaView drogaView, int x, int y) {
		this.drogaView = drogaView;
		
		createSShell();
//		sShell.setBounds(x, y, SZEROKOSC, WYSOKOSC);
		sShell.open();
	}
	
	public void dispose() {
//		sShell.dispose();
	}
	private void ustawDane() {
		drogaView.setIloscPasow(sIloscPasow.getSelection());
		drogaView.setIloscPasowPrawych(sPasowLewych.getSelection());
		drogaView.setMaxPredkosc(sMaxPredkosc.getSelection());
		drogaView.updateData();
	}
	
}
