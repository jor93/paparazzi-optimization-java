/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: BellmanFord.java
 */
package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import controller.ViewController;
import controller.ViewGUIController;
import interfaces.SearchAlgorithm;
import model.Cell;
import model.PaparazziMap;
import model.PaparazziProblem;

/**
 * Class that implements the Bellman Ford search algorithm to solve the
 * generated paparazzi map
 */
public class BellmanFord extends PaparazziProblem implements SearchAlgorithm {

	// adjacency list
	private ArrayList<Integer>[] adjacencyList;
	// path
	private List<Cell> pathToEndCell;

	public BellmanFord(PaparazziMap paparazziMap, boolean updateGUI) {
		super(paparazziMap, updateGUI);
	}

	@Override
	public PaparazziProblem startSearch() {
		// get start and end cell from generated map
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
		// build adjacency list
		int visitedNodes = buildAdjacencyList(size);
		// get x and y from start cell
		int startPoint = startCell.getX() * size + startCell.getY();
		// get x and y from end cell
		int stopPoint = endCell.getX() * size + endCell.getY();
		// create cost array
		int costs[] = new int[maxSize];
		// create path array
		int path[] = new int[maxSize];
		for (int i = 0; i < maxSize; i++) {
			// init costs to integer max value
			costs[i] = MAX;
			// init path to -1
			path[i] = -1;
		}
		// set start cell costs
		costs[startPoint] = 0;
		int cellCost = 0;
		for (int i = 0; i < maxSize - 1; i++) {
			for (int j = 0; j < maxSize; j++) {
				//cellCost = ((Cell) paparazziMap.getMap()[i/size][j/size]).getCost();
				for (int k : adjacencyList[j]) {
					// check if stop has been requested
					if (ViewGUIController.exit)
						return null;
					// get costs
					cellCost = ((Cell) paparazziMap.getMap()[k/size][k%size]).getCost();
					if (costs[k] > costs[j] + cellCost) {
						// set better costs
						costs[k] = costs[j] + cellCost;
						// set path
						path[k] = j;
					}
				}
			}
		}
		pathToEndCell = new LinkedList<>();
		// build path
		buildBellmanFordPath(pathToEndCell, path, stopPoint, size);
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
		int cost = 0;
		for (Cell cell : pathToEndCell) {
			cost += cell.getCost();
		}
		setTotalCosts(cost + endCell.getCost());
		if (ViewController.shortestPathCosts == cost + endCell.getCost())
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
	 * @return the path as list of cells
	 */
	public List<Cell> getPath() {
		return pathToEndCell;
	}

	@SuppressWarnings("unchecked")
	private int buildAdjacencyList(int size) {
		int maxSize = size * size;
		int visitedNodes = 0;
		adjacencyList = new ArrayList[maxSize];
		for (int i = 0; i < maxSize; i++) {
			adjacencyList[i] = new ArrayList<>();
		}
		ArrayList<Integer> myList;
		Cell[][] map = paparazziMap.getMap();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell c = map[i][j];
				// update visited nodes
				if (!c.isWall())
					visitedNodes += 1;
				myList = adjacencyList[c.getX() * size + c.getY()];
				for (Cell cell : getNeighbors(c)) {
					// check if not wall
					if (!c.isWall() && !cell.isWall()) {
						// add to list
						myList.add(cell.getX() * size + cell.getY());
						// check if update
						if (updateGUI) updateNextVisitedNode(Arrays.asList(c));
					}


				}
			}
		}
		return visitedNodes;
	}

	private void buildBellmanFordPath(List<Cell> indexPath, int[] path, int s, int size) {
		int k = path[s];
		if (k == -1)
			return;
		indexPath.add(paparazziMap.getMap()[k / size][k % size]);
		buildBellmanFordPath(indexPath, path, k, size);
	}
}
