package pl.wroc.pwr.iis.traffic.presentation.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.sun.org.apache.bcel.internal.generic.LNEG;

import pl.wroc.pwr.iis.traffic.domain.entity.Sygnalizacja;
import pl.wroc.pwr.iis.traffic.presentation.control.Odswiezanie;
import pl.wroc.pwr.iis.traffic.presentation.control.SkrzyzowanieViewModel;
import pl.wroc.pwr.iis.traffic.presentation.view.SkrzyzowanieView;

public class SkrzyzowanieWlasciwosci {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,2"
	private Group group = null;
	private Composite composite = null;
	private Button bOk = null;
	private Button bAnuluj = null;
	private Label lTrasy = null;
	private Combo cTrasy = null;
	private Label lDlugosc = null;
	private Label lDlugoscTrasy = null;
	private Label lMaxPredkosc = null;
	private Shell sGrupySyngaizacyjne = null;  //  @jve:decl-index=0:visual-constraint="403,3"
	private Composite composite6 = null;
	private Button bOkWlasciwosci = null;
	private Button bAnulujWlasciwosci = null;
	private Group composite3 = null;
	private List lGrupySygnalizacyjne = null;
	private Composite composite4 = null;
	private Button bMoveUp = null;
	private Button bMoveDown = null;
	private Composite composite5 = null;
	private Label label4 = null;
	private Text tNazwaGrupy = null;
	private Group group1 = null;
	private Label Minimum = null;
	private Label label3 = null;
	private Spinner sMinimalnaDlugoscFazy = null;
	private Spinner sMaxDlugoscFazy = null;
	private Button bDodajGrupe = null;
	private Button bUsunGrupe = null;
	private Group group2 = null;
	private Composite composite7 = null;
	private Label Trasa = null;
	private List lTrasyDoWyboru = null;
	private Composite composite8 = null;
	private Button button = null;
	private Button button7 = null;
	private Composite composite9 = null;
	private Label label1 = null;
	private Combo cNazwyGrup = null;
	private List lSkladGrupy = null;
	private Composite composite1 = null;
	private Button cWlaczSygnalizacje = null;
	private Label label = null;
	private Spinner sDlugoscFazy = null;
	private Button button2 = null;
	private Composite composite2 = null;
	private Spinner sMaxPredkosc = null;
	private Button button1 = null;
	private Button bUsun = null;
	private SkrzyzowanieView skrzyzowanie;  //  @jve:decl-index=0:
	private String[] nazwyTras;
	
	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		sShell = new Shell(SWT.DIALOG_TRIM);
		sShell.setText("Właściwości skrzyżowania");
		sShell.setLayout(gridLayout);
		createGroup();
		createGroup2();
		createComposite();
		sShell.setSize(new Point(392, 414));
		
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
		GridData gridData71 = new GridData();
		gridData71.horizontalAlignment = GridData.FILL;
		gridData71.verticalAlignment = GridData.CENTER;
		GridData gridData61 = new GridData();
		gridData61.grabExcessHorizontalSpace = false;
		gridData61.verticalAlignment = GridData.CENTER;
		gridData61.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		group = new Group(sShell, SWT.NONE);
		group.setLayoutData(gridData);
		group.setLayout(gridLayout2);
		lTrasy = new Label(group, SWT.NONE);
		lTrasy.setText("Trasa przejazdu: ");
		createCTrasy();
		lMaxPredkosc = new Label(group, SWT.NONE);
		lMaxPredkosc.setText("Maksymalna prędkość (m/s): ");
		sMaxPredkosc = new Spinner(group, SWT.BORDER);
		sMaxPredkosc.setLayoutData(gridData61);
		sMaxPredkosc
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						zapiszMaxPredkoscTrasy();
					}
				});
		lDlugosc = new Label(group, SWT.NONE);
		lDlugosc.setText("Długość trasy: ");
		lDlugoscTrasy = new Label(group, SWT.NONE);
		lDlugoscTrasy.setText("100 m");
		lDlugoscTrasy.setLayoutData(gridData71);
	}

	/**
	 * This method initializes composite	
	 *
	 */
	private void createComposite() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.CENTER;
		gridData1.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		gridLayout1.horizontalSpacing = 5;
		gridLayout1.verticalSpacing = 10;
		composite = new Composite(sShell, SWT.NONE);
		composite.setLayout(gridLayout1);
		composite.setLayoutData(gridData1);
		bOk = new Button(composite, SWT.NONE);
		bOk.setText("   &Ok   ");
		bAnuluj = new Button(composite, SWT.NONE);
		bAnuluj.setText("&Anuluj");
	}

	/**
	 * This method initializes cTrasy	
	 */
	private void createCTrasy() {
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		cTrasy = new Combo(group, SWT.DROP_DOWN | SWT.EMBEDDED | SWT.READ_ONLY);
		cTrasy.setLayoutData(gridData2);
		
		// ----------------------------------------------------
		cTrasy.addSelectionListener(new SelectionAdapter (){
			public void widgetSelected(SelectionEvent e) {
				skrzyzowanie.setZaznaczonaTrasa(cTrasy.getSelectionIndex());
				Odswiezanie.odswiezGlowneOkno();
				wczytajDlugoscTrasy();
				wczytajMaxPredkoscTrasy();
			}	
		});
	}

	private void wczytajDlugoscTrasy() {
		lDlugoscTrasy.setText(skrzyzowanie.getSkrzyzowanie().getTrasa(cTrasy.getSelectionIndex()).getDlugosc()+" m");
	}
	private void wczytajMaxPredkoscTrasy() {
		sMaxPredkosc.setSelection(skrzyzowanie.getSkrzyzowanie().getTrasa(cTrasy.getSelectionIndex()).getMaxPredkosc());
	}
	private void zapiszMaxPredkoscTrasy() {
		if (cTrasy.getSelectionIndex() >= 0) {
			skrzyzowanie.getSkrzyzowanie().getTrasa(cTrasy.getSelectionIndex()).setMaxPredkosc(sMaxPredkosc.getSelection());
		}
	}
	/**
	 * This method initializes sGrupySyngaizacyjne	
	 *
	 */
	private void createSGrupySyngaizacyjne() {
		GridLayout gridLayout5 = new GridLayout();
		gridLayout5.numColumns = 1;
		sGrupySyngaizacyjne = new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		sGrupySyngaizacyjne.setText("Właściwości");
		sGrupySyngaizacyjne.setLayout(gridLayout5);
		sGrupySyngaizacyjne.setSize(new Point(257, 328));
		Odswiezanie.wysrodkujOkno(sGrupySyngaizacyjne);
		
		createComposite3();
		createGroup1();
		createComposite6();
		sGrupySyngaizacyjne.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
			public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
				sGrupySyngaizacyjne.dispose();
			}
		});
	}

	/**
	 * This method initializes composite6	
	 *
	 */
	private void createComposite6() {
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.CENTER;
		gridData5.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout6 = new GridLayout();
		gridLayout6.numColumns = 4;
		gridLayout6.marginWidth = 15;
		composite6 = new Composite(sGrupySyngaizacyjne, SWT.NONE);
		composite6.setLayout(gridLayout6);
		composite6.setLayoutData(gridData5);
		button2 = new Button(composite6, SWT.NONE);
		button2.setText("&Zastosuj");
		button2.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				zapiszWlasciwosciGrupyOswietlenia();
			}
		});
		createComposite2();
		bOkWlasciwosci = new Button(composite6, SWT.NONE);
		bOkWlasciwosci.setText("   &OK   ");
		bOkWlasciwosci.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						zapiszWlasciwosciGrupyOswietlenia();
						sGrupySyngaizacyjne.close();
					}
				});
		bAnulujWlasciwosci = new Button(composite6, SWT.NONE);
		bAnulujWlasciwosci.setText("&Anuluj");
		bAnulujWlasciwosci.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sGrupySyngaizacyjne.close();
					}
				});
	}

	/**
	 * This method initializes composite3	
	 *
	 */
	private void createComposite3() {
		GridData gridData8 = new GridData();
		gridData8.horizontalAlignment = GridData.FILL;
		gridData8.grabExcessHorizontalSpace = true;
		gridData8.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.verticalAlignment = GridData.FILL;
		GridLayout gridLayout61 = new GridLayout();
		gridLayout61.numColumns = 2;
		composite3 = new Group(sGrupySyngaizacyjne, SWT.BOTTOM | SWT.UP | SWT.FLAT);
		composite3.setLayout(gridLayout61);
		composite3.setLayoutData(gridData8);
		composite3.setText("Grupy sygnalizacyjne");
		lGrupySygnalizacyjne = new List(composite3, SWT.BORDER);
		lGrupySygnalizacyjne.setLayoutData(gridData4);
		lGrupySygnalizacyjne.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						wczytajWlasciwosciGrupyOswietlenia();
					}
				});
		createComposite4();
		createComposite5();
	}
	
	private void wczytajWlasciwosciGrupyOswietlenia() {
		if (lGrupySygnalizacyjne.getItemCount() > 0 && lGrupySygnalizacyjne.getSelectionIndex() >= 0) {
			Sygnalizacja sygnalizacja = skrzyzowanie.getSkrzyzowanie().getGrupaSygnalizacyjna(lGrupySygnalizacyjne.getSelectionIndex());
			if (sygnalizacja != null) {
				tNazwaGrupy.setText(sygnalizacja.getNazwa());
				sDlugoscFazy.setSelection(sygnalizacja.getCzasFazy());
				sMinimalnaDlugoscFazy.setSelection(sygnalizacja.getMinCzasFazy());
				sMaxDlugoscFazy.setSelection(sygnalizacja.getMaxCzasFazy());
				sDlugoscFazy.setMaximum(sMaxDlugoscFazy.getSelection());
			}
		}
	}
	
	private void zapiszWlasciwosciGrupyOswietlenia() {
		if (lGrupySygnalizacyjne.getItemCount() > 0 && lGrupySygnalizacyjne.getSelectionIndex() >= 0) {
			Sygnalizacja sygnalizacja = skrzyzowanie.getSkrzyzowanie().getGrupaSygnalizacyjna(lGrupySygnalizacyjne.getSelectionIndex());
			if (sygnalizacja != null) {
				sygnalizacja.setNazwa(tNazwaGrupy.getText());
				sygnalizacja.setCzasFazy(sDlugoscFazy.getSelection());
				sygnalizacja.setMinCzasFazy(sMinimalnaDlugoscFazy.getSelection());
			}
			odswiezWlasciwosci();
		}
	}

	/**
	 * This method initializes composite4	
	 *
	 */
	private void createComposite4() {
		GridData gridData22 = new GridData();
		gridData22.widthHint = 16;
		gridData22.heightHint = 16;
		GridData gridData21 = new GridData();
		gridData21.widthHint = 16;
		gridData21.heightHint = 16;
		GridData gridData20 = new GridData();
		gridData20.widthHint = 16;
		gridData20.heightHint = 16;
		GridData gridData12 = new GridData();
		gridData12.widthHint = 16;
		gridData12.heightHint = 16;
		GridLayout gridLayout7 = new GridLayout();
		gridLayout7.numColumns = 1;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.BEGINNING;
		gridData3.grabExcessVerticalSpace = true;
		gridData3.verticalAlignment = GridData.FILL;
		composite4 = new Composite(composite3, SWT.NONE);
		composite4.setLayoutData(gridData3);
		composite4.setLayout(gridLayout7);
		bMoveUp = new Button(composite4, SWT.ARROW_UP | SWT.ARROW);
		bMoveUp.setText("G");
		bMoveUp.setLayoutData(gridData12);
		bMoveUp.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (lGrupySygnalizacyjne.getSelectionIndex() >= 0) {
					int index = lGrupySygnalizacyjne.getSelectionIndex();
					if (index >= 0) {
						if (skrzyzowanie.getSkrzyzowanie().grupaSygnalizacyjnaMoveUp(index)){
							odswiezWlasciwosci();
							lGrupySygnalizacyjne.setSelection(index - 1);
						}
						
					}
				}
			}
		});
		bDodajGrupe = new Button(composite4, SWT.ARROW_UP);
		bDodajGrupe.setText("+");
		bDodajGrupe.setLayoutData(gridData20);
		bDodajGrupe.setFont(new Font(Display.getDefault(), "Courier New", 8, SWT.NORMAL));
		bDodajGrupe.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				skrzyzowanie.getSkrzyzowanie().addGrupaSygnalizacyjna();
				odswiezWlasciwosci();
			}
		});
		bUsunGrupe = new Button(composite4, SWT.ARROW_UP);
		bUsunGrupe.setText("-");
		bUsunGrupe.setLayoutData(gridData21);
		bUsunGrupe.setFont(new Font(Display.getDefault(), "Courier New", 8, SWT.NORMAL));
		bUsunGrupe.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (lGrupySygnalizacyjne.getSelectionIndex() >= 0) {
					skrzyzowanie.getSkrzyzowanie().removeGrupaSygnalizacyja(lGrupySygnalizacyjne.getSelectionIndex());
					odswiezWlasciwosci();
				}
			}
		});
		bMoveDown = new Button(composite4, SWT.DOWN | SWT.ARROW);
		bMoveDown.setText("D");
		bMoveDown.setLayoutData(gridData22);
		bMoveDown.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int index = lGrupySygnalizacyjne.getSelectionIndex();
				if (index >= 0) {
					if (skrzyzowanie.getSkrzyzowanie().grupaSygnalizacyjnaMoveDown(index)){
						odswiezWlasciwosci();
						lGrupySygnalizacyjne.setSelection(index + 1);
					}
					
				}
			}
		});
	}

	private void odswiezWlasciwosci() {
		wczytajNazwyGrupSygnalizacyjnych();
		wczytajPolaGrupSygnalizacyjnych();
		wczytajWlasciwosciGrupyOswietlenia();
	}
	
	/**
	 * This method initializes composite5	
	 *
	 */
	private void createComposite5() {
		GridData gridData16 = new GridData();
		gridData16.grabExcessHorizontalSpace = false;
		gridData16.verticalAlignment = GridData.CENTER;
		gridData16.horizontalAlignment = GridData.FILL;
		GridData gridData7 = new GridData();
		gridData7.horizontalAlignment = GridData.FILL;
		gridData7.grabExcessHorizontalSpace = true;
		gridData7.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout8 = new GridLayout();
		gridLayout8.numColumns = 2;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.grabExcessHorizontalSpace = false;
		gridData6.horizontalSpan = 2;
		gridData6.verticalAlignment = GridData.CENTER;
		composite5 = new Composite(composite3, SWT.NONE);
		composite5.setLayoutData(gridData6);
		composite5.setLayout(gridLayout8);
		label4 = new Label(composite5, SWT.NONE);
		label4.setText("Nazwa:");
		tNazwaGrupy = new Text(composite5, SWT.BORDER);
		tNazwaGrupy.setLayoutData(gridData7);
		tNazwaGrupy.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
			}
		});
		label = new Label(composite5, SWT.NONE);
		label.setText("Długość fazy:");
		sDlugoscFazy = new Spinner(composite5, SWT.BORDER);
		sDlugoscFazy.setLayoutData(gridData16);
	}

	/**
	 * This method initializes group1	
	 *
	 */
	private void createGroup1() {
		GridData gridData24 = new GridData();
		gridData24.horizontalAlignment = GridData.FILL;
		gridData24.verticalAlignment = GridData.CENTER;
		GridData gridData23 = new GridData();
		gridData23.grabExcessHorizontalSpace = true;
		gridData23.verticalAlignment = GridData.CENTER;
		gridData23.horizontalAlignment = GridData.FILL;
		GridData gridData11 = new GridData();
		gridData11.widthHint = 100;
		GridData gridData10 = new GridData();
		gridData10.grabExcessHorizontalSpace = false;
		gridData10.verticalAlignment = GridData.CENTER;
		gridData10.widthHint = 100;
		gridData10.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout9 = new GridLayout();
		gridLayout9.numColumns = 2;
		GridData gridData9 = new GridData();
		gridData9.horizontalAlignment = GridData.FILL;
		gridData9.verticalAlignment = GridData.CENTER;
		group1 = new Group(sGrupySyngaizacyjne, SWT.NONE);
		group1.setLayoutData(gridData9);
		group1.setLayout(gridLayout9);
		group1.setText("Ograniczenia długości światła zielonego");
		Minimum = new Label(group1, SWT.NONE);
		Minimum.setText("Minimalne:");
		Minimum.setLayoutData(gridData23);
		sMinimalnaDlugoscFazy = new Spinner(group1, SWT.BORDER);
		sMinimalnaDlugoscFazy.setLayoutData(gridData10);
		label3 = new Label(group1, SWT.NONE);
		label3.setText("Maksymalne");
		label3.setLayoutData(gridData24);
		sMaxDlugoscFazy = new Spinner(group1, SWT.BORDER | SWT.FLAT);
		sMaxDlugoscFazy.setMaximum(500);
		sMaxDlugoscFazy.setSelection(200);
		sMaxDlugoscFazy.setLayoutData(gridData11);
		sMaxDlugoscFazy.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sDlugoscFazy.setMaximum(sMaxDlugoscFazy.getSelection());
					}
				});
	}

	/**
	 * This method initializes group2	
	 *
	 */
	private void createGroup2() {
		GridData gridData14 = new GridData();
		gridData14.horizontalAlignment = GridData.FILL;
		gridData14.grabExcessHorizontalSpace = true;
		gridData14.grabExcessVerticalSpace = true;
		gridData14.verticalAlignment = GridData.FILL;
		GridLayout gridLayout10 = new GridLayout();
		gridLayout10.numColumns = 1;
		group2 = new Group(sShell, SWT.NONE);
		group2.setText("Sygnalizacja świetlna");
		createComposite1();
		createComposite7();
		group2.setLayoutData(gridData14);
		group2.setLayout(gridLayout10);
	}

	/**
	 * This method initializes composite7	
	 *
	 */
	private void createComposite7() {
		GridData gridData19 = new GridData();
		gridData19.grabExcessHorizontalSpace = false;
		gridData19.verticalAlignment = GridData.CENTER;
		gridData19.horizontalAlignment = GridData.FILL;
		GridData gridData18 = new GridData();
		gridData18.horizontalAlignment = GridData.FILL;
		gridData18.grabExcessHorizontalSpace = true;
		gridData18.grabExcessVerticalSpace = true;
		gridData18.verticalAlignment = GridData.FILL;
		GridData gridData15 = new GridData();
		gridData15.horizontalAlignment = GridData.FILL;
		gridData15.grabExcessHorizontalSpace = true;
		gridData15.grabExcessVerticalSpace = true;
		gridData15.verticalAlignment = GridData.FILL;
		GridData gridData13 = new GridData();
		gridData13.horizontalAlignment = GridData.FILL;
		gridData13.grabExcessVerticalSpace = true;
		gridData13.verticalAlignment = GridData.FILL;
		GridLayout gridLayout11 = new GridLayout();
		gridLayout11.numColumns = 3;
		composite7 = new Composite(group2, SWT.BORDER);
		composite7.setLayout(gridLayout11);
		composite7.setLayoutData(gridData15);
		Trasa = new Label(composite7, SWT.NONE);
		Trasa.setText("Trasa:");
		Label filler10 = new Label(composite7, SWT.NONE);
		createComposite9();
		lTrasyDoWyboru = new List(composite7, SWT.BORDER);
		lTrasyDoWyboru.setLayoutData(gridData18);
		
		lTrasyDoWyboru.addSelectionListener(new SelectionAdapter (){
			public void widgetSelected(SelectionEvent e) {
				skrzyzowanie.setZaznaczonaTrasa(lTrasyDoWyboru.getSelectionIndex());
				Odswiezanie.odswiezGlowneOkno();
			}	
		});
		
		createComposite8();
		lSkladGrupy = new List(composite7, SWT.BORDER);
		lSkladGrupy.setLayoutData(gridData13);
		lSkladGrupy.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int index = cNazwyGrup.getSelectionIndex();
				int indexTrasy = lSkladGrupy.getSelectionIndex();
				if (index >= 0) {
					Sygnalizacja sygnalizacja = skrzyzowanie.getSkrzyzowanie().getGrupaSygnalizacyjna(index);
					int trasa = sygnalizacja.getTrasa(indexTrasy);
					skrzyzowanie.setZaznaczonaTrasa(trasa);
					Odswiezanie.odswiezGlowneOkno();
				}
			}
		});
		bUsun = new Button(composite7, SWT.NONE);
		bUsun.setText("Usuń trasę");
		Label filler3 = new Label(composite7, SWT.NONE);
		button1 = new Button(composite7, SWT.NONE);
		button1.setText("Zarządzaj grupami ...");
		button1.setLayoutData(gridData19);
		
		button1.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				createSGrupySyngaizacyjne();
				Odswiezanie.wysrodkujOkno(sGrupySyngaizacyjne);
				wczytajNazwyGrupSygnalizacyjnych();
				wczytajWlasciwosciGrupyOswietlenia();
				sGrupySyngaizacyjne.open();
			}
		});
	}

	/**
	 * This method initializes composite8	
	 *
	 */
	private void createComposite8() {
		GridLayout gridLayout12 = new GridLayout();
		gridLayout12.numColumns = 1;
		composite8 = new Composite(composite7, SWT.NONE);
		composite8.setLayout(gridLayout12);
		button = new Button(composite8, SWT.NONE);
		button.setText(">>");
		button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				int index = cNazwyGrup.getSelectionIndex();
				int indexTrasy = lTrasyDoWyboru.getSelectionIndex();
				if (index >= 0 && indexTrasy >=0) {
					Sygnalizacja sygnalizacja = skrzyzowanie.getSkrzyzowanie().getGrupaSygnalizacyjna(index);
					sygnalizacja.addTrasa(indexTrasy);
				}
				
				wczytajTrasyGrupSygnalizacyjnych();
			}
		});
		button7 = new Button(composite8, SWT.NONE);
		button7.setText("<<");
	}

	/**
	 * This method initializes composite9	
	 *
	 */
	private void createComposite9() {
		GridLayout gridLayout13 = new GridLayout();
		gridLayout13.numColumns = 2;
		gridLayout13.verticalSpacing = 0;
		gridLayout13.marginWidth = 0;
		gridLayout13.marginHeight = 0;
		gridLayout13.horizontalSpacing = 0;
		composite9 = new Composite(composite7, SWT.NONE);
		composite9.setLayout(gridLayout13);
		label1 = new Label(composite9, SWT.NONE);
		label1.setText("Grupa: ");
		createCNazwyGrup();
	}

	/**
	 * This method initializes cNazwyGrup	
	 *
	 */
	private void createCNazwyGrup() {
		cNazwyGrup = new Combo(composite9, SWT.NONE);
		cNazwyGrup.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				wczytajTrasyGrupSygnalizacyjnych();
			}
		});
	}
	
	private void wczytajTrasyGrupSygnalizacyjnych() {
		int index = cNazwyGrup.getSelectionIndex();
		if (index >= 0) {
			Sygnalizacja sygnalizacja = skrzyzowanie.getSkrzyzowanie().getGrupaSygnalizacyjna(index);
			String[] sklad = new String[sygnalizacja.getTrasyCount()];
			
			for (int i = 0; i < sklad.length; i++) {
				sklad[i] = nazwyTras[sygnalizacja.getTrasa(i)];
			}
			lSkladGrupy.setItems(sklad);
		}
	}

	/**
	 * This method initializes composite1	
	 *
	 */
	private void createComposite1() {
		GridLayout gridLayout3 = new GridLayout();
		gridLayout3.numColumns = 1;
		composite1 = new Composite(group2, SWT.NONE);
		composite1.setLayout(gridLayout3);
		cWlaczSygnalizacje = new Button(composite1, SWT.CHECK);
		cWlaczSygnalizacje.setText("Włącz sygnalizację świetlną");
		cWlaczSygnalizacje.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//				cWlaczSygnalizacje.setSelection(!cWlaczSygnalizacje.getSelection());
				skrzyzowanie.getSkrzyzowanie().setSygnalizacja(cWlaczSygnalizacje.getSelection());
			}
		});
	}

	/**
	 * This method initializes composite2	
	 *
	 */
	private void createComposite2() {
		GridData gridData17 = new GridData();
		gridData17.widthHint = 20;
		composite2 = new Composite(composite6, SWT.NONE);
		composite2.setLayout(new GridLayout());
		composite2.setLayoutData(gridData17);
	}
	
	public void open(SkrzyzowanieView skrzyzowanie) {
		this.skrzyzowanie = skrzyzowanie;
		createSShell();
		
		wczytajPola();
		sShell.open();
	}
	
	private void wczytajPola() {
		nazwyTras = SkrzyzowanieViewModel.createNazwyTras(this.skrzyzowanie);
		
		cTrasy.setItems(nazwyTras);
		cTrasy.select(0);
		
		lTrasyDoWyboru.setItems(nazwyTras);
		
		wczytajDlugoscTrasy();
		wczytajMaxPredkoscTrasy();
	}
	
	private void zapiszPola() {
		cTrasy.setItems(nazwyTras);
		cTrasy.select(0);
		
		lTrasyDoWyboru.setItems(nazwyTras);
		
		wczytajDlugoscTrasy();
		wczytajMaxPredkoscTrasy();
	}


	private void wczytajPolaGrupSygnalizacyjnych() {
		wczytajNazwyGrupSygnalizacyjnych();
	}
	
	private void wczytajNazwyGrupSygnalizacyjnych() {
		String[] nazwy = SkrzyzowanieViewModel.createNazwyGrupSygnalizacyjnych(skrzyzowanie);
		lGrupySygnalizacyjne.setItems(nazwy);
		cNazwyGrup.setItems(nazwy);
	} 
}
