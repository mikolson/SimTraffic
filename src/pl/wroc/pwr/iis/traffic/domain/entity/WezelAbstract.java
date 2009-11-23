package pl.wroc.pwr.iis.traffic.domain.entity;

import java.io.Serializable;

import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Entitable;
import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;

/**
 * Zapewnia dodawanie oraz usuwanie polączeń danego węzła z innymi węzłami sieci
 * drogowej.
 * 
 * @author Michał Stanek
 */
public abstract class WezelAbstract implements Wezel, Entitable, Serializable {

	protected Polaczenie[] dojazdy = new Polaczenie[0];
	protected Polaczenie[] odjazd = new Polaczenie[0];

	/**
	 * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#getDojazd(int)
	 */
	public Polaczenie getDojazd(int pas) {
	    Polaczenie result = null;
	    if (isPoprawnyNumerPasa(pas)) {
	        result = this.dojazdy[pas];
	    }
	    return result;
	}

	/**
	 * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#getOdjazd(int)
	 */
	public Polaczenie getOdjazd(int pas) {
	    Polaczenie result = null;
	    if (isPoprawnyNumerPasa(pas)) {
	        result = this.odjazd[pas];
	    }
	    return result;
	}

	/**
	 * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#setDojazd(int, pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel, int)
	 */
	public void setDojazd(int pas, Wezel dojazd, int pasDocelowy) {
		if (dojazd != null ) {
//			if (dojazd != null && isPoprawnyNumerPasa(pas) && dojazd.isPoprawnyNumerPasa(pasDocelowy)) {
	        this.dojazdy[pas] = new Polaczenie(dojazd, pasDocelowy);
	    }
	}

	/**
	 * @see pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel#setOdjazd(int, pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel, int)
	 */
	public void setOdjazd(int pas, Wezel odjazd, int pasDocelowy) {
		if (odjazd != null && isPoprawnyNumerPasa(pas) && odjazd.isPoprawnyNumerPasa(pasDocelowy)) {
	        this.odjazd[pas] = new Polaczenie(odjazd, pasDocelowy);
	    }
	}

	public void setIloscPasow(int iloscPasow) {
        this.dojazdy = new Polaczenie[iloscPasow];
        this.odjazd = new Polaczenie[iloscPasow];
	}
	
	protected void addDojazd() {
		Polaczenie[] doj = new Polaczenie[this.dojazdy.length + 1];
		
		for (int i = 0; i < this.dojazdy.length; i++) {
			doj[i] = this.dojazdy[i];
		}
		
		this.dojazdy = doj;
	}
	
	protected void addOdjazd() {
		Polaczenie[] odj = new Polaczenie[this.odjazd.length + 1];
		
		for (int i = 0; i < this.odjazd.length; i++) {
			odj[i] = this.odjazd[i];
		}
		
		this.odjazd = odj;
	}
	
	public abstract Object clone() throws CloneNotSupportedException;
}
