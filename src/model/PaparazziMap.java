/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 13 Apr 2019
 * File: PaparazziMap.java
 */
package model;

import java.util.Arrays;

import enums.MapDataSets;

/**
 * Class that represents a paparazzi map.
 */
public class PaparazziMap {

	// map
	private Cell[][] map;
	// start cell
	private Cell startCell;
	// end cell
	private Cell endCell;
	// data set
	private MapDataSets dataSet;
	
	/**
	 * Creates a new empty paparazzi map
	 * 
	 * @author Robert Johner
	 * @see PaparazziMap
	 */
	public PaparazziMap() {
		super();
	}
	
	/**
	 * Returns map
	 * 
	 * @author Robert Johner
	 * @return the map
	 */
	public Cell[][] getMap() {
		return map;
	}
	
	/**
	 * Sets map
	 * 
	 * @author Robert Johner
	 * @param map  the map
	 */
	public void setMap(Cell[][] map) {
		this.map = map;
	}

	/**
	 * Returns start cell
	 * 
	 * @author Robert Johner
	 * @return the start cell
	 */
	public Cell getStartCell() {
		return startCell;
	}
	
	/**
	 * Sets start cell
	 * 
	 * @author Robert Johner
	 * @param startCell  the start cell
	 */
	public void setStartCell(Cell startCell) {
		this.startCell = startCell;
	}
	
	/**
	 * Returns end cell
	 * 
	 * @author Robert Johner
	 * @return the end cell
	 */
	public Cell getEndCell() {
		return endCell;
	}
	
	/**
	 * Sets end cell
	 * 
	 * @author Robert Johner
	 * @param endCell  the end cell
	 */
	public void setEndCell(Cell endCell) {
		this.endCell = endCell;
	}
		
	/**
	 * Returns data set
	 * 
	 * @author Robert Johner
	 * @return the start cell 
	 * @see MapDataSets
	 */
	public MapDataSets getDataSet() {
		return dataSet;
	}

	/**
	 * Sets data set
	 * 
	 * @author Robert Johner
	 * @param dataSet  the data set
	 */
	public void setDataSet(MapDataSets dataSet) {
		this.dataSet = dataSet;
	}

	@Override
	public String toString() {
		return "PaparazziMap [map=" + Arrays.toString(map) + ", startCell=" + startCell + ", endCell=" + endCell
				+ ", dataSet=" + dataSet + "]";
	}
}
