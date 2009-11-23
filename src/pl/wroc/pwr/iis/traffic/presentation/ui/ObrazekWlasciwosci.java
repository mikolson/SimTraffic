package pl.wroc.pwr.iis.traffic.presentation.ui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.wroc.pwr.iis.traffic.presentation.control.Odswiezanie;
import pl.wroc.pwr.iis.traffic.presentation.view.figury.Obrazek;

public class ObrazekWlasciwosci {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="11,3"
	private Group group = null;
	private Text tSciezka = null;
	private Button bSciezka = null;
	private Composite composite = null;
	private Button bOk = null;
	private Button bAnuluj = null;
	private Button bPrzywrocRozmiar = null;
	private Obrazek obrazek;  //  @jve:decl-index=0:

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		sShell = new Shell();
		sShell.setText("Shell");
		sShell.setLayout(gridLayout);
		createGroup();
		createComposite();
		sShell.setSize(new Point(420, 168));
		
		Odswiezanie.wysrodkujOkno(sShell);

		sShell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
			public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
				sShell.dispose();
			}
		});
	}

	/**
	 * This method initializes group	
	 *
	 */
	private void createGroup() {
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.horizontalSpan = 2;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		group = new Group(sShell, SWT.NONE);
		group.setText("Ścieżka obrazka");
		group.setLayout(gridLayout1);
		group.setLayoutData(gridData1);
		tSciezka = new Text(group, SWT.BORDER);
		tSciezka.setEditable(false);
		tSciezka.setEnabled(true);
		tSciezka.setTextLimit(1);
		tSciezka.setLayoutData(gridData);
		bSciezka = new Button(group, SWT.NONE);
		bSciezka.setText("&Wybierz nowy...");
		bPrzywrocRozmiar = new Button(group, SWT.NONE);
		bPrzywrocRozmiar.setText("&Przywróc orginalny rozmiar obrazka");
		bPrzywrocRozmiar.setLayoutData(gridData11);
		
		bPrzywrocRozmiar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				obrazek.przywrocRozmiar();
			}
		});
		
		bSciezka.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				FileDialog dialog = new FileDialog(sShell, SWT.OPEN);
				dialog.setText("Wybierz plik obrazka");
				dialog.setFilterPath((new File(".")).getAbsolutePath());
				dialog.setFilterExtensions(new String[]{"*.gif; *.jpg; *.bmp","*.*"});
				dialog.setFilterNames(new String[]{"Pliki graficzne (*.gif; *.jpg, *.bmp)", "Wszystkie pliki (*.*)"});
				String path = dialog.open();
				
				if (path != null) {
					tSciezka.setText(path);
				}
			}
		});

	}

	/**
	 * This method initializes composite	
	 *
	 */
	private void createComposite() {
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.CENTER;
		gridData2.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		composite = new Composite(sShell, SWT.NONE);
		composite.setLayout(gridLayout2);
		composite.setLayoutData(gridData2);
		bOk = new Button(composite, SWT.NONE);
		bOk.setText("   &Ok   ");
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
	
	public void wczytajPola() {
		this.tSciezka.setText(this.obrazek.getImagePath());
	}
	
	public void zapiszPola() {
		this.obrazek.setImagePath(this.tSciezka.getText());
	}
	
	public void open(Obrazek obrazek, int x, int y) {
		this.obrazek = obrazek;

		createSShell();
		wczytajPola();
		sShell.open();
	}

}
