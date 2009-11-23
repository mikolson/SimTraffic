package pl.wroc.pwr.iis.traffic.presentation.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import pl.wroc.pwr.iis.traffic.domain.entity.Generator;
import pl.wroc.pwr.iis.traffic.presentation.control.Odswiezanie;
import pl.wroc.pwr.iis.traffic.presentation.view.GeneratorView;

public class GeneratorWlasciwosci {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="136,-24"
	private Group group = null;
	private Group group1 = null;
	private Composite composite = null;
	private Button bOk = null;
	private Button bAnuluj = null;
	private Scale sPojazdy = null;
	private Scale sOsobowy = null;
	private Label lSamochodOsobowy = null;
	private Label lSamochodCiezarowy = null;
	private Scale sCiezarowy = null;
	private Label Autobus = null;
	private Scale sAutobus = null;
	private Scale sAutobusPrzegubowy = null;
	private Label lAutobusPrzegubowy = null;
	private Label lZeroProcent = null;
	private Label lStoProcent = null;
	private Button bStatystyki = null;
	private GeneratorView generator;  //  @jve:decl-index=0:
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridData gridData7 = new GridData();
		gridData7.grabExcessHorizontalSpace = true;
		gridData7.verticalAlignment = GridData.CENTER;
		gridData7.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		sShell = new Shell();
		sShell.setText("Generator");
		sShell.setLayout(gridLayout);
		createGroup();
		createGroup1();
		bStatystyki = new Button(sShell, SWT.NONE);
		bStatystyki.setText("&Pokaż statystyki pojazdów ...");
		bStatystyki.setEnabled(false);
		bStatystyki.setLayoutData(gridData7);
		createComposite();
		sShell.setSize(new Point(278, 438));
		
		Odswiezanie.wysrodkujOkno(sShell);
	}
	/**
	 * This method initializes group	
	 *
	 */
	private void createGroup() {
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.grabExcessHorizontalSpace = true;
		gridData11.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 3;
		gridLayout2.verticalSpacing = 13;
		gridLayout2.horizontalSpacing = 5;
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = GridData.FILL;
		group = new Group(sShell, SWT.NONE);
		group.setText("Prawdopodobieństwo pojazdu");
		group.setLayoutData(gridData);
		group.setLayout(gridLayout2);
		lZeroProcent = new Label(group, SWT.NONE);
		lZeroProcent.setText("0%");
		sPojazdy = new Scale(group, SWT.NONE);
		sPojazdy.setSelection(30);
		sPojazdy.setLayoutData(gridData11);
		lStoProcent = new Label(group, SWT.NONE);
		lStoProcent.setText("100%");
	}
	/**
	 * This method initializes group1	
	 *
	 */
	private void createGroup1() {
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.grabExcessHorizontalSpace = true;
		gridData6.heightHint = -1;
		gridData6.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout3 = new GridLayout();
		gridLayout3.numColumns = 2;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.verticalAlignment = GridData.FILL;
		group1 = new Group(sShell, SWT.NONE);
		group1.setText("Generowane typy pojazdów");
		group1.setLayoutData(gridData1);
		group1.setLayout(gridLayout3);
		lSamochodOsobowy = new Label(group1, SWT.NONE);
		lSamochodOsobowy.setText("Samochód osobowy: ");
		sOsobowy = new Scale(group1, SWT.NONE);
		sOsobowy.setSelection(100);
		sOsobowy.setLayoutData(gridData3);
		lSamochodCiezarowy = new Label(group1, SWT.NONE);
		lSamochodCiezarowy.setText("Samochód ciężarowy: ");
		sCiezarowy = new Scale(group1, SWT.NONE);
		sCiezarowy.setSelection(50);
		sCiezarowy.setPageIncrement(10);
		sCiezarowy.setLayoutData(gridData4);
		Autobus = new Label(group1, SWT.NONE);
		Autobus.setText("Autobus: ");
		sAutobus = new Scale(group1, SWT.NONE);
		sAutobus.setSelection(10);
		sAutobus.setLayoutData(gridData5);
		lAutobusPrzegubowy = new Label(group1, SWT.NONE);
		lAutobusPrzegubowy.setText("Autobus przegubowy: ");
		sAutobusPrzegubowy = new Scale(group1, SWT.NONE);
		sAutobusPrzegubowy.setSelection(2);
		sAutobusPrzegubowy.setLayoutData(gridData6);
	}
	/**
	 * This method initializes composite	
	 *
	 */
	private void createComposite() {
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		gridLayout1.marginHeight = 10;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.CENTER;
		gridData2.verticalAlignment = GridData.CENTER;
		composite = new Composite(sShell, SWT.NONE);
		composite.setLayoutData(gridData2);
		composite.setLayout(gridLayout1);
		bOk = new Button(composite, SWT.NONE);
		bOk.setText("   &OK   ");
		bAnuluj = new Button(composite, SWT.NONE);
		bAnuluj.setText("&Anuluj");
		
		bAnuluj.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				sShell.close();
			}
		});
		
		bOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				zapiszPola();
				sShell.close();
			}
		});
	}

	public void open(GeneratorView generator, int x, int y) {
		this.generator = generator;
		createSShell();
		
		wczytajPola();
		sShell.open();
	}
	
	private void wczytajPola() {
		this.sPojazdy.setSelection(this.generator.getGenerator().getPrawdopodobienstwoPojazdu());
		
		Scale[] wskazniki = new Scale[] {sOsobowy, sCiezarowy, sAutobus, sAutobusPrzegubowy};
		
		Generator g = this.generator.getGenerator();
		for (int i = 0; i < g.getIloscGenerowanychPojazdow() && i < wskazniki.length; i++) {
			wskazniki[i].setSelection(g.getPrawdopodobienstwoPojazdu(i));
		}
		
		this.sPojazdy.setSelection(this.generator.getGenerator().getPrawdopodobienstwoPojazdu(0));
	}
	
	private void zapiszPola() {
		this.generator.getGenerator().setPrawdopodobienstwoPojazdu(this.sPojazdy.getSelection());
		
		Scale[] wskazniki = new Scale[] {sOsobowy, sCiezarowy, sAutobus, sAutobusPrzegubowy};
		
		Generator g = this.generator.getGenerator();
		for (int i = 0; i < g.getIloscGenerowanychPojazdow() && i < wskazniki.length; i++) {
			g.setPrawdopodobienstwoPojazdu(i, wskazniki[i].getSelection());
			
		}
		
		this.sPojazdy.setSelection(this.generator.getGenerator().getPrawdopodobienstwoPojazdu(0));
	}
}
