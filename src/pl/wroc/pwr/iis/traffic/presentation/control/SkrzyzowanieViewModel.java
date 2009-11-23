package pl.wroc.pwr.iis.traffic.presentation.control;

import java.util.ArrayList;

import pl.wroc.pwr.iis.traffic.domain.entity.Skrzyzowanie;
import pl.wroc.pwr.iis.traffic.domain.entity.Sygnalizacja;
import pl.wroc.pwr.iis.traffic.presentation.view.SkrzyzowanieView;

public final class SkrzyzowanieViewModel {
	public final static String[] createNazwyTras(SkrzyzowanieView skrzyzowanie) {
		int iPasow = skrzyzowanie.getSkrzyzowanie().getIloscPasow();
		int iTras = skrzyzowanie.getSkrzyzowanie().getIloscTras();
		String[] sTrasy = new String[iTras];
		
		int index = 0;
		for (int i = 0; i < iPasow; i++) {
			ArrayList<Integer> trasy = skrzyzowanie.getSkrzyzowanie().getNumeryTrasDlaPasaRuchu(i);
			
			for (int j = 0; j < trasy.size(); j++) {
				sTrasy[index++] =  index + ": wjazd: " + (i + 1) + " Trasa: " + (j+1);
			}
		}
		return sTrasy;
	}
	
	public final static String[] createNazwyGrupSygnalizacyjnych(SkrzyzowanieView skrzyzowanieView) {
		Skrzyzowanie skrzyzowanie = skrzyzowanieView.getSkrzyzowanie();
		
		ArrayList<Sygnalizacja> grupy = skrzyzowanie.getGrupySygnalizacyjne();
		String[] nazwy = new String[grupy.size()];
		
		for (int i = 0; i < grupy.size(); i++) {
			nazwy[i] = grupy.get(i).getNazwa();
		}
		
		return nazwy;
	}
}
