/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: Cell.java
 */
package model;

import enums.MapObjects;
import javafx.scene.control.Label;

/**
 * Class that represents one cell on the paparazzi map.
 */
public class Cell extends Label {

	// x coordinate
	private Integer x;
	// x coordinate
	private Integer y;

	private boolean isStartCell = false;
	private boolean isEndCell = false;
	private boolean isWall = false;
	private boolean isCamera = false;
	private boolean isCameraRadius = false;
	private boolean isPond = false;

	/**
	 * Creates a new Cell
	 * 
	 * @author Robert Johner
	 * @param y the y coordinate in the map array
	 * @param x the x coordinate in the map array
	 * @see Cell
	 */
	public Cell(int y, int x) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns costs of cell
	 * 
	 * @author Robert Johner
	 * @return the costs of the cell
	 * @see Cell
	 */
	public int getCost() {
		if (isCamera)
			return 30;
		if (isCameraRadius && isPond)
			return 55;
		if (isCameraRadius && !isEndCell)
			return 15;
		if (isPond)
			return 40;
		if (isWall)
			return 0;
		if (isStartCell)
			return 0;
		return 1;
	}

	/**
	 * Returns cell type
	 * 
	 * @author Robert Johner
	 * @return the cell type
	 * @see MapObjects
	 */
	public MapObjects getCellType() {
		if (isEndCell)
			return MapObjects.ENDCELL;
		if (isCamera)
			return MapObjects.CAMERA;
		if (isPond)
			return MapObjects.POND;
		if (isWall)
			return MapObjects.WALL;
		if (isStartCell)
			return MapObjects.STARTCELL;
		if(isCameraRadius)
			return MapObjects.CAMERARADIUS;
		return MapObjects.EMPTY;
	}

	/**
	 * Returns x coordinate
	 * 
	 * @author Robert Johner
	 * @return the x coordinate value
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * Sets x coordinate
	 * 
	 * @author Robert Johner
	 * @param x  the x coordinate value
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * Returns y coordinate
	 * 
	 * @author Robert Johner
	 * @return the y coordinate value
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * Sets y coordinate
	 * 
	 * @author Robert Johner
	 * @param y  the y coordinate value
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * Returns true if cell is start cell
	 * 
	 * @author Robert Johner
	 * @return the start cell value
	 */
	public boolean isStartCell() {
		return isStartCell;
	}

	/**
	 * Sets start cell
	 * 
	 * @author Robert Johner
	 * @param isStartCell  the start cell value
	 */
	public void setStartCell(boolean isStartCell) {
		this.isStartCell = isStartCell;
	}

	/**
	 * Returns true if cell is end cell
	 * 
	 * @author Robert Johner
	 * @return the end cell value
	 */
	public boolean isEndCell() {
		return isEndCell;
	}

	/**
	 * Sets end cell
	 * 
	 * @author Robert Johner
	 * @param isEndCell  the end cell value
	 */
	public void setEndCell(boolean isEndCell) {
		this.isEndCell = isEndCell;
	}

	/**
	 * Returns true if cell is a wall
	 * 
	 * @author Robert Johner
	 * @return the wall cell value
	 */
	public boolean isWall() {
		return isWall;
	}

	/**
	 * Sets wall cell
	 * 
	 * @author Robert Johner
	 * @param isWall  the start cell value
	 */
	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

	/**
	 * Returns true if cell is a camera
	 * 
	 * @author Robert Johner
	 * @return the camera cell value
	 */
	public boolean isCamera() {
		return isCamera;
	}

	/**
	 * Sets camera cell
	 * 
	 * @author Robert Johner
	 * @param isCamera  the camera cell value
	 */
	public void setCamera(boolean isCamera) {
		this.isCamera = isCamera;
	}

	/**
	 * Returns true if cell is a camera radius
	 * 
	 * @author Robert Johner
	 * @return the camera radius cell value
	 */
	public boolean isCameraRadius() {
		return isCameraRadius;
	}
	
	/**
	 * Sets camera radius cell
	 * 
	 * @author Robert Johner
	 * @param isCameraRadius  the camera radius cell value
	 */	
	public void setCameraRadius(boolean isCameraRadius) {
		this.isCameraRadius = isCameraRadius;
	}

	/**
	 * Returns true if cell is a pond
	 * 
	 * @author Robert Johner
	 * @return the pond cell value
	 */
	public boolean isPond() {
		return isPond;
	}

	/**
	 * Sets pond  cell
	 * 
	 * @author Robert Johner
	 * @param isPond  the pond cell value
	 */	
	public void setPond(boolean isPond) {
		this.isPond = isPond;
	}

	@Override
	public int hashCode() {
		return x.hashCode() + y.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cell) {
			Cell c = (Cell) obj;
			return c.x.equals(x) && c.y.equals(y);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + ", costs=" + getCost() + ", type=" + getCellType() + "]";
	}
}