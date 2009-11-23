package pl.wroc.pwr.iis.traffic.presentation.model;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * @author Michał Stanek
 */
public class ResourceHelper {
	private static final Display display = Display.getDefault();

	private static ArrayList<Font> fonts = new ArrayList<Font>();
	private static ArrayList<Color> colors = new ArrayList<Color>();
	private static ArrayList<Image> images = new ArrayList<Image>();
	
	public final static Color COLOR_BLACK = getColor(0,0,0);
	public final static Color COLOR_WHITE = getColor(255,255,255);
	public final static Color COLOR_RED = getColor(255,0,0);
	public final static Color COLOR_LIGHT_RED = getColor(200,120,120);
	public final static Color COLOR_GREEN = getColor(0,255,0);
	public final static Color COLOR_BLUE = getColor(0,0,255);
	public final static Color COLOR_LIGHT_BLUE = getColor(150,150,255);
	public final static Color COLOR_DARK_BLUE = getColor(0,0,150);
	public final static Color COLOR_LIGHT_GRAY = getColor(192,192,192);
	public final static Color COLOR_MEDIUM_LIGHT_GRAY = getColor(160,160,160);
	public final static Color COLOR_GRAY = getColor(120,120,120);
	public final static Color COLOR_DARK_GRAY = getColor(80,80,80);
	public final static Color COLOR_MEDIUM_DARK_GRAY = getColor(110,110,110);

	public final static Color COLOR_TLO = getColor(200,200,200);
	
	public static void disposeResources() {
		for (Font font : fonts) {
			font.dispose();
		}

		for (Color color : colors) {
			color.dispose();
		}
		
		for (Image image : images) {
			image.dispose();
		}
	}

	public static Color getColor(int red, int green, int blue) {
		Color result = new Color(display, red, green, blue);
		colors.add(result);
		return result;
	}
	
	public static Color getColor(RGB rgb) {
		return getColor(rgb.red, rgb.green, rgb.blue);
	}
	
	public static Image getImage(String path) {
		Image result = new Image(display, path);
		images.add(result);
		return result;
	}
	
	public static Display getDisplay() {
		return display;
	}
}
