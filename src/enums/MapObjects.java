/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: MapObjects.java
 */
package enums;

/**
 * Enumeration that holds all the different map objects which can encounter in
 * the generated 2d map with their corresponding color.
 */
public enum MapObjects {

	CAMERA(MapObjectsColors.RED),
	CAMERARADIUS(MapObjectsColors.LIGHTRED),
	ENDCELL(MapObjectsColors.GRAY), 
	STARTCELL(MapObjectsColors.YELLOW),
	NEXTCELL(MapObjectsColors.LIGHTGREEN),
	EMPTY(MapObjectsColors.WHITE), 
	PATH(MapObjectsColors.GREEN), 
	WALL(MapObjectsColors.BLACK),
	POND(MapObjectsColors.BLUE);

	private MapObjectsColors color;

	private MapObjects(MapObjectsColors color) {
		this.color = color;
	}

	/**
	 * Returns color of map object
	 * 
	 * @author Robert Johner
	 * @return the color of the map object
	 * @see MapObjectsColors
	 */
	public MapObjectsColors getColor() {
		return color;
	}
}