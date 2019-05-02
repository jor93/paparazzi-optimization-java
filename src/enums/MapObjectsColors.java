/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: MapObjectsColors.java
 */
package enums;

import java.awt.Color;

/**
 * Enumeration that holds all the possible colors in the in the generated 2d
 * map.
 */
public enum MapObjectsColors {
	RED(new Color(229, 57, 53)),
	LIGHTRED(new Color(239,154,154)),
	BLUE(new Color(30, 136, 229)), 
	GREEN(new Color(67, 160, 71)),
	LIGHTGREEN(new Color(200,230,201)),
	YELLOW(new Color(253, 216, 53)), 
	BLACK(new Color(0, 0, 0)),
	GRAY(new Color(117, 117, 117)),
	WHITE(new Color(255,255,255));
	
	private Color color;

	private MapObjectsColors(Color color) {
		this.color = color;
	}

	/**
	 * Returns color object
	 * 
	 * @author Robert Johner
	 * @return the color object
	 * @see Color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns color RGB value
	 * 
	 * @author Robert Johner
	 * @return the RGB color value
	 */
	public String toRGB() {
		return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ");";
	}
	
}
