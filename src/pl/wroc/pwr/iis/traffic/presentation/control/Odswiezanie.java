package pl.wroc.pwr.iis.traffic.presentation.control;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

import pl.wroc.pwr.iis.traffic.presentation.ui.Aplikacja;

public final class Odswiezanie {
	public static final void odswiezGlowneOkno() {
		Aplikacja.getInstance().refreshWindow();
	}

	public static final void wysrodkujOkno(Shell shell) {
		Rectangle pDisplayBounds = shell.getDisplay().getBounds();
		int nLeft = (pDisplayBounds.width - shell.getSize().x) / 2;
		int nTop = (pDisplayBounds.height - shell.getSize().y) / 2;
		shell.setBounds(nLeft, nTop, shell.getSize().x, shell.getSize().y);
	}
}
