package pl.wroc.pwr.iis.traffic.domain.entity;

/**
 * @author Michał Stanek
 */
public enum Kierunek {
    LEWY {
    	public int zmienPas(int pas){
    		return pas + 1;
    	}
    },
    PRAWY {
    	public int zmienPas(int pas){
    		return pas - 1;
    	}
    },
    PROSTO {
    	public int zmienPas(int pas){
    		return pas;
    	}
    };
    
    /**
	 * Oblicza numer pasa przy zmianie w wyznaczonym kierunku
	 * 
	 * @param pas
	 *            Aktualny numer pasa
	 * @return Numer pasa jezeli chcemy go zmienic w wybranym kierunku
	 */
    abstract int zmienPas(int pas);
}
