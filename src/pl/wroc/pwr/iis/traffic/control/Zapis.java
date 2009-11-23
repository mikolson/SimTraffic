package pl.wroc.pwr.iis.traffic.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.wroc.pwr.iis.logger.Logger;
import pl.wroc.pwr.iis.traffic.presentation.model.Grupa;

public class Zapis {
	public static void zapiszObiekty(String nazwaPliku, Grupa grupaObiektow) {
		FileOutputStream f_out;
		try {
			f_out = new FileOutputStream(nazwaPliku);
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
			obj_out.writeObject(grupaObiektow);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Logger.warn("Nie znaleziono pliku");
		} catch (IOException e) {
			e.printStackTrace();
			Logger.warn("Błąd I/O");
		}
	}
	
	public static Grupa odczytajObiekty(String nazwaPliku) {
		Grupa result = null;
		try {
			FileInputStream f_in = new FileInputStream(nazwaPliku);
			ObjectInputStream obj_in = new ObjectInputStream (f_in);
			
			Object obj = obj_in.readObject();

			if (obj instanceof Grupa)
			{
				 result = (Grupa) obj;
			} 
		} catch (FileNotFoundException e) {
			Logger.warn("Nie znaleziono pliku");
		} catch (IOException e) {
			Logger.warn("Błąd I/O");
		} catch (ClassNotFoundException e) {
			Logger.warn("Niepoprawny obiekt zapisany w pliku, lub plik zapisany w starszej wersji programu");
		}
		
		return result;
	}
}
