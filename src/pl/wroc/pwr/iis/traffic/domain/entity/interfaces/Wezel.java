/**
 * 
 */
package pl.wroc.pwr.iis.traffic.domain.entity.interfaces;

import pl.wroc.pwr.iis.traffic.domain.entity.Kierunek;
import pl.wroc.pwr.iis.traffic.domain.entity.Pojazd;
import pl.wroc.pwr.iis.traffic.domain.entity.Polaczenie;

/**
 * @author Administrator
 * 
 */
public interface Wezel extends Cloneable {
	int BRAK_POJAZDOW_W_ZAKRESIE_WIDOCZNOSCI = Integer.MAX_VALUE;

	/**
	 * Sluzy do badania odleglosci przed nastepnym pojazdem przy wjezdzie na nastepny 
	 * pas. Jest to po to ze niektore elementy np skrzyzowania 
	 * 
	 * @param indeksPasa
	 * @param komorka
	 * @param zakresWidocznosci
	 * @return
	 */
	int getOdlegloscPrzedNaWjezdzie(int indeksPasa, int komorka, int zakresWidocznosci);

	int getOdlegloscPrzed(int indeksPasa, int komorka, int zakresWidocznosci);

	int getMaxPredkoscPasa(int pas);

	/**
	 * @param pas
	 *            Pas dla którego ustawiamy połączenie dojazdowe
	 * @param dojazd
	 *            Wezel dojazdowy,
	 * @param pasDocelowy
	 *            numer pasa z ktorego dojezdzaja samochody
	 */
	void setDojazd(int pas, Wezel dojazd, int pasDocelowy);

	/**
	 * Sprawdza czy numer pasa dojazdowego i odjazdowego jest prawidlowy
	 * 
	 * @param pas
	 *            Pas dla którego ustawiamy połączenie dojazdowe
	 * @param dojazd
	 *            Wezel odjazdowy
	 * @param pasDocelowy
	 *            numer pasa z ktorego dojezdzaja samochody
	 */
	void setOdjazd(int pas, Wezel wyjazd, int pasDocelowy);

	Polaczenie getDojazd(int pas);
	Polaczenie getOdjazd(int pas);

	/**
	 * Sprawdza czy podany numer pasa jest dla danego wezla poprawny. To znaczy
	 * czy przekazany indeks nie jest za maly albo za duzy.
	 * 
	 * @param numerPasa
	 *            Indeks pasa ruchu.
	 * @return True jeżeli przekazany przez parametr indeks pasa jest
	 *         prawidłowy.
	 */
	boolean isPoprawnyNumerPasa(int numerPasa);

	/**
	 * Ustawia nowa ilosc pasow
	 * 
	 * @param iloscPasow
	 */
	void setIloscPasow(int iloscPasow);

	int getIloscPasow();

	/**
	 * @param numerPasa
	 * @return
	 */
	Kierunek getKierunekPasa(int numerPasa);

	boolean isMozliwaZmianaPasa(int indeksPasa, Kierunek kierunek);

	int getPredkoscPrzed(int indeksPasa, int komorka, int zakresWidocznosci);

	int getPredkoscZa(int indeksPasa, int komorka, int zakresWidocznosci);

	int getOdlegloscZa(int indeksPasa, int komorka);

	/**
	 * Zwraca numer komorki jaka odpowiada komorce z jednego pasa, na innym
	 * pasie. Jest to pomocne przy zmianie pasow, kiedy pojazd musi wykonac ten
	 * manewr rownolegle.
	 * 
	 * @param pas
	 *            Pas aktualny
	 * @param kierunekDocelowy
	 *            W jakim kierunku chcemy zmienic pas
	 * @param komorka
	 *            Komorka na bierzacym pasie
	 * @return Odpowiadajaca komorka na pasie docelowym
	 */
	int getPrzesuniecieWzgledemPasow(int pas, Kierunek kierunekDocelowy,
			int komorka);

	// int getPrzesuniecieWzgledemPasow(int pasBiezacy, int pasDocelowy, int
	// komorka);

	/**
	 * Ustawia dany pojazd na konkretnym pasie i komorce danego wezla. Metoda
	 * sprawdza czy komorka ta jest pusta i czy wstawienie takie jest wogole
	 * mozliwe. Jezeli warunki sa spełnione pojazd jest wstawiany oraz w
	 * pojezdzie ustawiane sa informacje na temat jego aktualnego polozenia.
	 * 
	 * @param pas
	 *            Pas na jakim ma sie znalezc pojazd
	 * @param komorka
	 *            Komorka na jakiej ma sie znalezc pojazd
	 * @param pojazd
	 *            Jaki pojazd umiescic w miejscu docelowym
	 * @return True jezeli wstawienie pojazdu jest mozliwe
	 */
	public boolean setPojazdAt(int pas, int komorka, Pojazd pojazd);
	
	public Object clone() throws CloneNotSupportedException;
}
