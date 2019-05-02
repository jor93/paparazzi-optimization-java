/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: FloydWarshall.java
 */
package algorithms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import controller.ViewController;
import controller.ViewGUIController;
import interfaces.SearchAlgorithm;
import model.Cell;
import model.PaparazziMap;
import model.PaparazziProblem;

/**
 * Class that implements the Floyd Warshall search algorithm to solve the
 * generated paparazzi map
 */
public class FloydWarshall extends PaparazziProblem implements SearchAlgorithm {

	// adjacency matrix
	private int[][] adjacencyMatrix;
	// path
	private Set<Cell> pathToEndCell;

	public FloydWarshall(PaparazziMap paparazziMap, boolean updateGUI) {
		super(paparazziMap, updateGUI);
	}

	@Override
	public PaparazziProblem startSearch() {
		// set start and end cell from generated map
		Cell startCell = paparazziMap.getStartCell();
		Cell endCell = paparazziMap.getEndCell();
		// check if start and end cell are different
		if (startCell.equals(endCell))
			return null;
		// start timer
		long startTime = System.currentTimeMillis();
		// get size
		int size = getPaparazziMap().getDataSet().getSize();
		// set max size
		int maxSize = size * size;
		// build adjacency matrix
		int visitedNodes = buildAdjacencyMatrix(size);
		// duplicate matrix
		int D[][] = adjacencyMatrix.clone();
		// create path array
		int path[][] = new int[maxSize][maxSize];
		for (int i = 0; i < maxSize; i++) {
			for (int j = 0; j < maxSize; j++) {
				// init path to -1
				path[i][j] = -1;
			}
		}
		// iterate over
		for (int i = 0; i < maxSize; i++) {
			for (int j = 0; j < maxSize; j++) {
				for (int k = 0; k < maxSize; k++) {
					// check if stop has been requested
					if (ViewGUIController.exit)
						return null;
					// check costs
					if (D[j][k] > D[j][i] + D[i][k]) {
						// set costs
						D[j][k] = D[j][i] + D[i][k];
						// set path
						path[j][k] = i;
					}
				}
			}
		}
		// get x and y from start cell
		int startPoint = startCell.getX() * size + startCell.getY();
		// get x and y from start cell
		int stopPoint = endCell.getX() * size + endCell.getY();
		pathToEndCell = new HashSet<>();
		// build path
		buildFloydWarshallPath(pathToEndCell, path, startPoint, stopPoint, size);
		// end timer
		long endTime = System.currentTimeMillis();
		// calculate execution time
		long executionTime = endTime - startTime;
		// check if update
		if (updateGUI)
			updatePath(pathToEndCell);
		// set results
		setExecutionTime(executionTime);
		setVisitedNodes(visitedNodes);
		int costs = 0;
		for (Cell cell : pathToEndCell) {
			costs += cell.getCost();
		}
		setTotalCosts(costs + endCell.getCost());
		if (ViewController.shortestPathCosts == getTotalCosts())
			setShortestPath(true);
		else
			setShortestPath(false);
		// check if update
		if (updateGUI)
			updateResults(getExecutionTime(), getTotalCosts(), getVisitedNodes(), isShortestPath());
		return this;
	}

	@Override
	public void setSearchAlgorithm() {
		setSearchAlgorithm(this);
	}

	/**
	 * Returns path
	 * 
	 * @author Robert Johner
	 * @return the path as set of cells
	 */
	public Set<Cell> getPath() {
		return pathToEndCell;
	}

	private int buildAdjacencyMatrix(int size) {
		int maxSize = size * size;
		int visitedNodes = 0;
		adjacencyMatrix = new int[maxSize][maxSize];
		for (int i = 0; i < maxSize; i++) {
			for (int j = 0; j < maxSize; j++) {
				adjacencyMatrix[i][j] = MAX;
				if (i == j) {
					adjacencyMatrix[i][j] = 0;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell c = paparazziMap.getMap()[i][j];
				// update visited nodes
				if (!c.isWall())
					visitedNodes += 1;
				for (Cell cell : getNeighbors(c)) {
					// check if not wall
					if (!c.isWall() && !cell.isWall()) {
						// set costs
						adjacencyMatrix[cell.getX() * size + cell.getY()][c.getX() * size + c.getY()] = cell.getCost();
						adjacencyMatrix[c.getX() * size + c.getY()][cell.getX() * size + cell.getY()] = c.getCost();
						// check if update
						if (updateGUI)
							updateNextVisitedNode(Arrays.asList(c));
					}

				}

			}
		}
		return visitedNodes;
	}

	private void buildFloydWarshallPath(Set<Cell> indexPath, int[][] path, int f, int s, int size) {
		int k = path[f][s];
		if (k == -1)
			return;
		indexPath.add(paparazziMap.getMap()[k / size][k % size]);
		buildFloydWarshallPath(indexPath, path, f, k, size);
		buildFloydWarshallPath(indexPath, path, k, s, size);
	}

}
