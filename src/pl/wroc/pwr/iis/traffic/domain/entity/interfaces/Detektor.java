/*
 * Copyright 2005 Michal Stanek. All rights reserved.
 * Project name: 	Magisterska
 * File in project: Detektor.java
 * Creation date: 	2005-12-03
 */
package pl.wroc.pwr.iis.traffic.domain.entity.interfaces;

import pl.wroc.pwr.iis.traffic.domain.entity.Pojazd;

/**
 * Interfejs <code>Detector</code> określa jakie metody musi dostarczać każdy
 * typ detektora, który będziemy chcieli umieścić na drodze.
 * 
 * @author Michał Stanek
 */
public interface Detektor {

	/**
	 * Metoda wywoływana jest kiedy na drodze, w miejscu w którym umieszczony
	 * jest detektor pojawia się pojazd. Możliwe jest, że w ciągu jednego cyklu
	 * automatu metoda detektora zostanie wywołana wielok
	 * 
	 * @param samochod
	 *            Referencja na pojazd który pojawił się w obszarze działania
	 *            detektora.
	 */
	void monitoruj(Pojazd samochod);

	/**
	 * Metoda służy do pobrania długości detektora. Długość ta podawana jest w
	 * metrach i określa na jakim długim obszarze od punktu startu detektor
	 * zbiera jeszcze informacje.
	 * 
	 * @return Długość detektora w metrach (jednostkach automatu komórkowego)
	 */
	int getDlugosc();
}
