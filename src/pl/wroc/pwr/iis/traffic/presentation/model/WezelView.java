package pl.wroc.pwr.iis.traffic.presentation.model;

import pl.wroc.pwr.iis.traffic.domain.entity.interfaces.Wezel;
import pl.wroc.pwr.iis.traffic.presentation.view.statyczne.PolaczenieView;


public interface WezelView extends Paintable {
	boolean RYSUJ_SIATKE = false;
	
	/**
	 * @param numerPasa
	 *            Numer pasa ktorego odjazd chcemy pobrac
	 * @return Dane na temat odjazdu z danego pasa.
	 */
	PolaczenieView getOdjazd(int numerPasa);
	
	/**
	 * @return Zwraca wezel z poziomu Domain, ktorego dany element jest
	 *         wizualizacją
	 */
	Wezel getWezel();

	/**
	 * Pobiera wspolrzędna konkretnej komorki na danym pasie.
	 * 
	 * @param pas
	 *            Numer pasa
	 * @param dlugosc
	 *            Numer komorki pasa zaczynajac od jego poczatku
	 * @return Wspolrzedna na ekranie
	 */
	PunktMapy getWspolrzedna(int numerPasa, int dlugosc);

	PunktMapy getWspolrzednaWjazdu(int numerPasa, int dlugosc);
	
	/**
	 * Wykonuje ruch na danej drodze.
	 */
	void wykonajRuch();
	
	/**
	 * @param numerPasa
	 *            numer pasa
	 * @return True jezeli podany numer pasa jest prawidlowy
	 */
	boolean isPoprawnyNumerPasa(int numerPasa);
	
	/**
	 * @return Ma zwracac wszystkie punkty jakie dla danego wezla mogą być
	 *         punktami wjazdowymi.
	 */
	PunktMapy[] getPunktyWjazdowe();

	/**
	 * @return Ma zwracac wszystkie punkty jakie dla danego wezla mogą być
	 *         punktami wyjazdowymi
	 */
	PunktMapy[] getPunktyWyjazdowe();

	/**
	 * Ustawia odjazd dla danego elementu.
	 * 
	 * @param numerPasa
	 *            numer pasa dla którego chcemy uzyskac odjazd
	 * @param wezelDocelowy
	 *            Obiekt wizualny wezla docelowego
	 * @param pasDocelowy
	 *            pas na ktory nalezy wjechac w obiektcie docelowym
	 */
	void setOdjazd(int numerPasa, WezelView wezelDocelowy, int pasDocelowy);
	
	void setOdjazd(int numerPasa, PolaczenieView odjazd);
	void setDojazd(int numerPasa, PolaczenieView dojazd);
	
	/**
	 * Zadaniem metody jest usuniecie danych o dojazdach danego pasa ruchu w
	 * ramach wezla sieci dróg
	 * 
	 * @param numerPasa
	 *            Numer pasa z ktorego informacje chcemy usunać
	 */
	void delDojazd(int numerPasa);
	
	/**
	 * Zadaniem metody jest usuniecie danych o dojazdach danego pasa ruchu w
	 * ramach wezla sieci dróg
	 * 
	 * @param numerPasa
	 *            Numer pasa z ktorego informacje chcemy usunać
	 */
	void delOdjazd(int numerPasa);
}
