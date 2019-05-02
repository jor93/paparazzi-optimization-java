/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: AStar.java
 */
package algorithms;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import controller.ViewController;
import controller.ViewGUIController;
import interfaces.SearchAlgorithm;
import model.Cell;
import model.PaparazziMap;
import model.PaparazziProblem;
import model.Vertice;

/**
 * Class that implements the A* search algorithm to solve the generated
 * paparazzi map
 */
public class AStar extends PaparazziProblem implements SearchAlgorithm {

	public AStar(PaparazziMap paparazziMap, boolean updateGUI) {
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
		// create open list
		List<Vertice<Cell, Integer>> openList = new LinkedList<>();
		// create close list
		List<Cell> closeList = new LinkedList<>();
		// create heuristic cost list
		Map<Cell, Integer> gScore = new HashMap<>();
		// init path map
		super.pathMap = new HashMap<>();
		// init current cell
		Cell current;
		// add start cell to heuristic map
		gScore.put(startCell, 0);
		// add start cell to open list
		openList.add(new Vertice<>(startCell, heuristic(startCell)));
		// do as long open list is not empty
		while (!openList.isEmpty()) {
			// check if stop has been requested
			if (ViewGUIController.exit) return null;
			// sort list by cost
			openList.sort(Comparator.comparingInt(Vertice::getCosts));
			// get cell from open list
			current = openList.get(0).getChild();
			// add cell to close list
			closeList.add(current);
			// remove cell from open list
			openList.remove(0);
			// iterate over free child cells from cell
			for (Cell child : getFreeNeighbors(current)) {
				// check if close list does not contain cell
				if (!closeList.contains(child)) {
					// check if open list does not contain cell
					if (!openList.contains(new Vertice<>(child, 0))) {
						// add to g score list
						gScore.put(child, gScore.get(current) + child.getCost());
						// add to open list with costs
						openList.add(new Vertice<>(child, gScore.get(current) + child.getCost() + heuristic(child)));
						// add child to path map
						pathMap.put(child, current);
					} else {
						// update heuristic value if better
						if (openList.get(openList.indexOf(new Vertice<>(child, 0))).getCosts() > (gScore.get(current) + child.getCost() + heuristic(child))) {
							openList.get(openList.indexOf(new Vertice<>(child, 0))).setCosts(gScore.get(current) + child.getCost() + heuristic(child));
							pathMap.replace(child, current);
							gScore.replace(child, gScore.get(current) + child.getCost());
						}
					}
				}
			}
			// check if update
			if (updateGUI)
				updateNextVisitedNode(pathMap.keySet());
			// check if end cell has been reached
			if (pathMap.containsKey(endCell)) {
				break;
			}
		}
		// end timer
		long endTime = System.currentTimeMillis();
		// calculate execution time
		long executionTime = endTime - startTime;

		// set results
		setExecutionTime(executionTime);
		setVisitedNodes(pathMap.size());
		List<Cell> pathToEndCell = buildPath();
		// check if update
		if (updateGUI) updatePath(pathToEndCell);
		int costs = 0;
		for (Cell cell : pathToEndCell) {
			costs += cell.getCost();
		}
		setTotalCosts(costs + endCell.getCost());
		if (ViewController.shortestPathCosts == costs + endCell.getCost())
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
}
