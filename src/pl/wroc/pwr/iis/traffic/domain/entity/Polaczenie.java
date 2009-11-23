package pl.wroc.pwr.iis.traffic.domain.entity;

import java.io.Serializable;

import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;

public class Polaczenie implements Serializable{
	private static final long serialVersionUID = -5604756784907752216L;
	
	public Wezel wezel;
	public int pas;
	
	public Polaczenie(Wezel wezel, int pas) {
		this.wezel = wezel;
		this.pas = pas;
	}
}
