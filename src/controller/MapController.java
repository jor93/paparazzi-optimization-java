/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: MapController.java
 */
package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import algorithms.Dijkstra;
import enums.MapDataSets;
import model.Cell;
import model.PaparazziMap;

/**
 * Map Controller to generate a random paparazzi maps for the defined data set.
 * Controller is implemented in a Singleton pattern.
 */
public class MapController {

	// singleton instance
	private static MapController instance = null;
	// random generatator
	private Random randomGenerator;

	private MapController() {
		this.randomGenerator = new Random();
	}

	/**
	 * Initialize and returns FileController instance.
	 * 
	 * @author Robert Johner
	 * @return the map controller singleton instance
	 * @see MapController
	 */
	public static MapController getInstance() {
		if (instance == null)
			instance = new MapController();

		return instance;
	}

	/**
	 * Generates new random paparazzi map and sets map cells. After random creation,
	 * the map is checked with Dijkstra if its possible to solve. are: Normal Cell,
	 * Start Cell, End Cell, Wall Cell, Camera Cell, Camera Radius Cell, Pond Cell.
	 *
	 * @author Robert Johner
	 * @param dataSet the Map Data Set that should be created
	 * @return the generated paparazzi map to solve
	 * @see PaparazziMap
	 */
	public PaparazziMap generateMap(MapDataSets dataSet) {
		// create empty paparazzi map
		PaparazziMap paparazziMap = new PaparazziMap();
		// set data set
		paparazziMap.setDataSet(dataSet);
		do {
			// create blocked cell set
			Set<Cell> blockedCell = new HashSet<>();
			// get map size from data set
			int size = dataSet.getSize();
			// create map
			Cell[][] map = new Cell[size][size];
			// fill map with empty cells
			map = fillMap(map, dataSet);
			// create random start cell on outer x and y
			Cell startCell;
			do {
				startCell = new Cell(randomGenerator.nextInt(size), randomGenerator.nextInt(size));
			} while (!isOuterCell(startCell, size));
			startCell.setStartCell(true);
			// set start cell in map
			map[startCell.getX()][startCell.getY()] = startCell;
			// set start cell of problem
			paparazziMap.setStartCell(startCell);
			// add to blocked cells
			blockedCell.add(startCell);
			// create wrapper
			PaparazziProblemWrapper wrapper = new PaparazziProblemWrapper(map);
			// add near fields to blocked
			blockedCell.addAll(wrapper.blockStartNeighbors(startCell));
			Cell endCell;
			boolean isSet = false;
			// ensure that end cell is not start cell and not on outer cell
			do {
				// create random end cell
				endCell = new Cell(randomGenerator.nextInt(size), randomGenerator.nextInt(size));
				// check if end cell is start cell
				if (!endCell.equals(startCell) && !isOuterCell(endCell, size) && !blockedCell.contains(endCell)) {
					isSet = true;
				}
			} while (!isSet);
			// set end cell of problem
			endCell.setEndCell(true);
			// set start cell in map
			map[endCell.getX()][endCell.getY()] = endCell;
			// set end cell of problem
			paparazziMap.setEndCell(endCell);
			// add to blocked cells
			blockedCell.add(endCell);

			// create random wall amount between wall min and wall max
			int walls = randomGenerator.nextInt((dataSet.getWallMax() + 1) - dataSet.getWallMin())
					+ dataSet.getWallMin();
			for (int i = 0; i < walls; i++) {
				// create new random wall cell
				Cell wall = new Cell(randomGenerator.nextInt(size), randomGenerator.nextInt(size));
				if (!blockedCell.contains(wall)) {
					// set wall
					wall.setWall(true);
					// set wall in map
					map[wall.getX()][wall.getY()] = wall;
					// add to blocked cells
					blockedCell.add(wall);
				} else {
					// loop again
					i--;
				}
			}

			// create random camera amount between camera min and camera max
			int cameras = randomGenerator.nextInt((dataSet.getCameraMax() + 1) - dataSet.getCameraMin())
					+ dataSet.getCameraMin();
			for (int i = 0; i < cameras; i++) {
				// create new random camera cell
				Cell camera = new Cell(randomGenerator.nextInt(size), randomGenerator.nextInt(size));
				if (!blockedCell.contains(camera)) {
					// set camera in map
					camera.setCamera(true);
					// set camera in map
					map[camera.getX()][camera.getY()] = camera;
					// add to blocked cells
					blockedCell.add(camera);
					// add camera effects
					map = wrapper.addCameraEffects(camera);
				} else {
					// loop again
					i--;
				}
			}

			// create random pond amount between pond min and pond max
			int ponds = randomGenerator.nextInt((dataSet.getPondMax() + 1) - dataSet.getPondMin())
					+ dataSet.getPondMin();
			for (int i = 0; i < ponds; i++) {
				// create new random pond cell
				Cell pond = new Cell(randomGenerator.nextInt(size), randomGenerator.nextInt(size));
				if (!blockedCell.contains(pond)) {
					// set pond
					pond.setPond(true);
					// check camera radius
					if (map[pond.getX()][pond.getY()].isCameraRadius())
						pond.setCameraRadius(true);
					// set pond in map
					map[pond.getX()][pond.getY()] = pond;
					// add to blocked cells
					blockedCell.add(pond);
				} else {
					// loop again
					i--;
				}
			}
			// set map of paparazzi map
			paparazziMap.setMap(map);
		} while (!isMapSolvable(paparazziMap));
		// return paparazzi map
		return paparazziMap;
	}

	private boolean isMapSolvable(PaparazziMap map) {
		// check map with dijkstra
		Dijkstra dijkstra = (Dijkstra) SearchController.getInstance().dijkstra(map, false);
		// check if path was found
		if (dijkstra.getPathMap().containsKey(map.getEndCell())) {
			return true;
		}
		return false;
	}

	private Cell[][] fillMap(Cell[][] map, MapDataSets dataSet) {
		// iterate over map and add empty cell
		for (int i = 0; i < dataSet.getSize(); i++) {
			for (int j = 0; j < dataSet.getSize(); j++)
				// add empty cell
				map[i][j] = new Cell(j, i);
		}
		return map;
	}

	private boolean isOuterCell(Cell cell, int size) {
		if (cell.getX() == 0 || cell.getX() == (size - 1))
			return true;
		if (cell.getY() == 0 || cell.getY() == (size - 1))
			return true;
		return false;
	}

	/**
	 * Paparazzi Wrapper
	 * 
	 * @author Robert Johner
	 */
	private class PaparazziProblemWrapper {

		private Cell[][] wrapperMap;

		/**
		 * Creates a new PaparazziProblem Wrapper.
		 * 
		 * @author Robert Johner
		 * @param map 	the generated paparazzi map to solve
		 * @see PaparazziProblemWrapper
		 */
		public PaparazziProblemWrapper(Cell[][] map) {
			this.wrapperMap = map;
		}

		/**
		 * Creates a new PaparazziProblem Wrapper.
		 * 
		 * @author Robert Johner
		 * @param cell	cell from which neighbors should be blocked
		 * @return blocked cells as list
		 * @see PaparazziProblemWrapper
		 */
		public List<Cell> blockStartNeighbors(Cell cell) {
			return getNeighbors(cell);
		}

		/**
		 * Adds camera effects to cell neighbors
		 * 
		 * @author Robert Johner
		 * @param cell	cell from which neighbors should be marked as camera radius
		 * @return current cell map
		 * @see PaparazziProblemWrapper
		 */
		public Cell[][] addCameraEffects(Cell cell) {
			// get every cell around camera
			for (Cell cameraCell : getFreeNeighbors(cell)) {
				// set as camera
				this.wrapperMap[cameraCell.getX()][cameraCell.getY()].setCameraRadius(true);
			}
			return this.wrapperMap;
		}

		private List<Cell> getNeighbors(Cell cell) {
			// create empty neighbor list
			List<Cell> neighbors = new ArrayList<>(8);
			// try to get all 8 neighbors starting top left
			try {
				neighbors.add(getTopLeft(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getTop(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getTopRight(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getRight(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getBottomRight(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getBottom(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getBottomLeft(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getLeft(cell));
			} catch (Exception e) {
			}
			// returns neighbors if exists
			return neighbors;
		}

		private List<Cell> getFreeNeighbors(Cell cell) {
			List<Cell> neighbors = new ArrayList<>();
			for (Cell c : getNeighbors(cell))
				// do not add tree as neighbor
				if (!c.isWall())
					neighbors.add(c);
			return neighbors;
		}

		private Cell getTop(Cell cell) throws Exception {
			return wrapperMap[cell.getX() - 1][cell.getY()];
		}

		private Cell getTopLeft(Cell cell) throws Exception {
			return wrapperMap[cell.getX() - 1][cell.getY() - 1];
		}

		private Cell getTopRight(Cell cell) throws Exception {
			return wrapperMap[cell.getX() - 1][cell.getY() + 1];
		}

		private Cell getRight(Cell cell) throws Exception {
			return wrapperMap[cell.getX()][cell.getY() + 1];
		}

		private Cell getBottomRight(Cell cell) throws Exception {
			return wrapperMap[cell.getX() + 1][cell.getY() + 1];
		}
		
		private Cell getBottom(Cell cell) throws Exception {
			return wrapperMap[cell.getX() + 1][cell.getY()];
		}
		
		private Cell getBottomLeft(Cell cell) throws Exception {
			return wrapperMap[cell.getX() + 1][cell.getY() - 1];
		}
		
		private Cell getLeft(Cell cell) throws Exception {
			return wrapperMap[cell.getX()][cell.getY() - 1];
		}
	}
}