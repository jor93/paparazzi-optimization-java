/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 13 Apr 2019
 * File: Map.java
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import controller.ViewGUIController;
import enums.MapObjects;
import init.Settings;
import javafx.application.Platform;

/**
 * Abstract class that represents a paparazzi problem to solve.
 */
public abstract class PaparazziProblem {

	// max value
	protected static int MAX = 1000000;
	// paparazzi map
	protected PaparazziMap paparazziMap;
	// path map
	protected Map<Cell, Cell> pathMap;
	// update path
	protected boolean updateGUI;
	// shortest path
	protected int shortestPath;

	// search algorithm
	private PaparazziProblem searchAlgorithm;
	// execution time
	private long executionTime;
	// amount of visited nodes
	private int visitedNodes;
	// total costs
	private int totalCosts;
	// shortest path
	private boolean isShortestPath;
	
	/**
	 * Creates a new paparazzi problem
	 * 
	 * @author Robert Johner
	 * @param paparazziMap the paparazzi map to solve
	 * @param updateGUI	choose if you want to update the GUI
	 * 
	 */
	public PaparazziProblem(PaparazziMap paparazziMap, boolean updateGUI) {
		this.paparazziMap = paparazziMap;
		this.updateGUI = updateGUI;
	}

	/**
	 * Builds shortest path and returns it from start to end cell
	 * 
	 * @author Robert Johner
	 * @return the path as list
	 * @see Cell
	 */
	public List<Cell> buildPath() {
		// check if path map contains end cell
		if (pathMap.containsKey(paparazziMap.getEndCell())) {
			// create empty list
			List<Cell> path = new ArrayList<>();
			// get end cell
			Cell cell = paparazziMap.getEndCell();
			// reconstruct path
			while (!cell.equals(paparazziMap.getStartCell())) {
				cell = pathMap.get(cell);
				path.add(cell);
			}
			// remove last cell
			path.remove(path.size() - 1);
			// reverse order
			Collections.reverse(path);
			return path;
		} else
			return null;
	}

	/**
	 * Returns path map
	 * 
	 * @author Robert Johner
	 * @return the path as map
	 * @see Cell
	 */
	public Map<Cell, Cell> getPathMap() {
		return pathMap;
	}

	/**
	 * Returns path map
	 * 
	 * @author Robert Johner
	 * @return the paparazzi map
	 * @see Cell
	 */
	public PaparazziMap getPaparazziMap() {
		return paparazziMap;
	}

	/**
	 * Returns search algorithm
	 * 
	 * @author Robert Johner
	 * @return the search algorithm
	 * @see PaparazziProblem
	 */
	public PaparazziProblem getSearchAlgorithm() {
		return searchAlgorithm;
	}

	/**
	 * Returns execution time
	 * 
	 * @author Robert Johner
	 * @return the execution time
	 */
	public long getExecutionTime() {
		return executionTime;
	}

	/**
	 * Returns visited nodes
	 * 
	 * @author Robert Johner
	 * @return the visited nodes
	 */
	public int getVisitedNodes() {
		return visitedNodes;
	}

	/**
	 * Returns total path costs
	 * 
	 * @author Robert Johner
	 * @return the total path costs
	 */
	public int getTotalCosts() {
		return totalCosts;
	}

	/**
	 * Returns if shortest path is found
	 * 
	 * @author Robert Johner
	 * @return true if shortest path is found
	 */
	public boolean isShortestPath() {
		return isShortestPath;
	}

	protected int heuristic(Cell cell) {
		return Math.abs(cell.getX() - paparazziMap.getEndCell().getX()) + Math.abs(cell.getY() - paparazziMap.getEndCell().getY());
	}

	protected Cell getTop(Cell cell) throws Exception {
		return paparazziMap.getMap()[cell.getX() - 1][cell.getY()];
	}

	protected Cell getRight(Cell cell) throws Exception {
		return paparazziMap.getMap()[cell.getX()][cell.getY() + 1];
	}

	protected Cell getLeft(Cell cell) throws Exception {
		return paparazziMap.getMap()[cell.getX()][cell.getY() - 1];
	}

	protected Cell getBottom(Cell cell) throws Exception {
		return paparazziMap.getMap()[cell.getX() + 1][cell.getY()];
	}

	protected Cell getTopLeft(Cell cell) throws Exception {
		return paparazziMap.getMap()[cell.getX() - 1][cell.getY() - 1];
	}

	protected Cell getTopRight(Cell cell) throws Exception {
		return paparazziMap.getMap()[cell.getX() - 1][cell.getY() + 1];
	}

	protected Cell getBottomRight(Cell cell) throws Exception {
		return paparazziMap.getMap()[cell.getX() + 1][cell.getY() + 1];
	}
	
	protected Cell getBottomLeft(Cell cell) throws Exception {
		return paparazziMap.getMap()[cell.getX() + 1][cell.getY() - 1];
	}
	
	protected List<Cell> getNeighbors(Cell cell) {
		List<Cell> neighbors = new ArrayList<>();
		try {
			neighbors.add(getTop(cell));
		} catch (Exception ignored) {
		}
		try {
			neighbors.add(getRight(cell));
		} catch (Exception ignored) {
		}
		try {
			neighbors.add(getBottom(cell));
		} catch (Exception ignored) {
		}
		try {
			neighbors.add(getLeft(cell));
		} catch (Exception ignored) {
		}
		// check if diagonal movement is allowed
		if(Settings.isDiagonalMovementAllowed) {
			// add diagonal cells
			try {
				neighbors.add(getTopLeft(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getTopRight(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getBottomRight(cell));
			} catch (Exception e) {
			}
			try {
				neighbors.add(getBottomLeft(cell));
			} catch (Exception e) {
			}
		}
		return neighbors;
	}

	protected List<Cell> getFreeNeighbors(Cell cell) {
		List<Cell> neighbors = new ArrayList<>();
		for (Cell c : getNeighbors(cell))
			// do not add tree as neighbor
			if (!c.isWall())
				neighbors.add(c);
		return neighbors;
	}

	protected List<Vertice<Cell,Integer>> getFreeVertices(Cell cell) {
		List<Vertice<Cell,Integer>>  neighbors = new ArrayList<>();
		for (Cell c : getNeighbors(cell))
			// do not add tree as neighbor
			if (!c.isWall()) {
				neighbors.add(new Vertice<Cell, Integer>(c, c.getCost()));
			}
		return neighbors;
	}
	
	protected void updateNextVisitedNode(Iterable<Cell> cells) {
		// color next visited nodes
		try {
			for (Cell cell : cells) {
				if(ViewGUIController.exit) return;
				if (!cell.getStyle().contains("-fx-background-color: " + MapObjects.NEXTCELL.getColor().toRGB())) {
					if (!cell.isEndCell() && !cell.isStartCell()) {
						Platform.runLater(() -> cell.setStyle("-fx-background-color: " + MapObjects.NEXTCELL.getColor().toRGB()));
						Thread.sleep(Settings.refreshRateGUI);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updatePath(Iterable<Cell> cells) {
		// color path
		try {
			for (Cell cell : cells) {
				if(ViewGUIController.exit) return;
				if (!cell.isEndCell() && !cell.isStartCell()) {
					Platform.runLater(() -> cell.setStyle("-fx-background-color: " + MapObjects.PATH.getColor().toRGB()));
					Thread.sleep(Settings.refreshRateGUI);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updateResults(long executionTime, int costs, int visitedNodes, boolean isShortestPath) {
		if(ViewGUIController.exit) return;
		Platform.runLater(() -> ViewGUIController.getInstance().setResultsOnView(executionTime, costs, visitedNodes, isShortestPath));
	}
	
	protected void setSearchAlgorithm(PaparazziProblem searchAlgorithm) {
		this.searchAlgorithm = searchAlgorithm;
	}

	protected void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	protected void setVisitedNodes(int visitedNodes) {
		this.visitedNodes = visitedNodes;
	}

	protected void setTotalCosts(int totalCosts) {
		this.totalCosts = totalCosts;
	}

	protected void setShortestPath(boolean shortestPath) {
		isShortestPath = shortestPath;
	}

	@Override
	public String toString() {
		return "PaparazziProblem [paparazziMap=" + paparazziMap + ", pathMap=" + pathMap + ", updateGUI=" + updateGUI
				+ ", shortestPath=" + shortestPath + ", searchAlgorithm=" + searchAlgorithm + ", executionTime="
				+ executionTime + ", visitedNodes=" + visitedNodes + ", totalCosts=" + totalCosts + ", isShortestPath="
				+ isShortestPath + "]";
	}
}