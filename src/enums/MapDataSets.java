/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: MapDataSets.java
 */
package enums;

/**
 * Enumeration that holds the different data sets.
 */
public enum MapDataSets {

	TINY(5, 3, 6, 1, 1, 1, 2), 
	SMALL(10, 12, 30, 5, 6, 2, 7), 
	MEDIUM(20, 48, 120, 9, 14, 14, 25),
	LARGE(50, 300, 750, 100, 125, 85, 150), 
	HUGE(100, 1200, 2500, 350, 400, 350, 500);

	private int size;
	private int wallMin;
	private int wallMax;
	private int cameraMin;
	private int cameraMax;
	private int pondMin;
	private int pondMax;

	private MapDataSets(int size, int wallMin, int wallMax, int cameraMin, int cameraMax, int pondMin, int pondMax) {
		this.size = size;
		this.wallMin = wallMin;
		this.wallMax = wallMax;
		this.cameraMin = cameraMin;
		this.cameraMax = cameraMax;
		this.pondMin = pondMin;
		this.pondMax = pondMax;
	}

	/**
	 * Returns data set map size
	 * 
	 * @author Robert Johner
	 * @return the integer size value
	 * @see MapDataSets
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns data set walls minimum quantity
	 * 
	 * @author Robert Johner
	 * @return the integer minimum value
	 * @see MapDataSets
	 */
	public int getWallMin() {
		return wallMin;
	}

	/**
	 * Returns data set walls maximum quantity
	 * 
	 * @author Robert Johner
	 * @return the integer maximum value
	 * @see MapDataSets
	 */
	public int getWallMax() {
		return wallMax;
	}

	/**
	 * Returns data set cameras minimum quantity
	 * 
	 * @author Robert Johner
	 * @return the integer minimum value
	 * @see MapDataSets
	 */
	public int getCameraMin() {
		return cameraMin;
	}

	/**
	 * Returns data set cameras maximum quantity
	 * 
	 * @author Robert Johner
	 * @return the integer maximum value
	 * @see MapDataSets
	 */
	public int getCameraMax() {
		return cameraMax;
	}

	/**
	 * Returns data set ponds minimum quantity
	 * 
	 * @author Robert Johner
	 * @return the integer minimum value
	 * @see MapDataSets
	 */
	public int getPondMin() {
		return pondMin;
	}

	/**
	 * Returns data set ponds maximum quantity
	 * 
	 * @author Robert Johner
	 * @return the integer maximum value
	 * @see MapDataSets
	 */
	public int getPondMax() {
		return pondMax;
	}
}