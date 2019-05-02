/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: Dikjstra.java
 */
package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import controller.ViewController;
import controller.ViewGUIController;
import init.Settings;
import interfaces.SearchAlgorithm;
import model.Cell;
import model.PaparazziMap;
import model.PaparazziProblem;
import model.Vertice;

/**
 * Class that implements the Dijkstra search algorithm to solve the generated
 * paparazzi map
 */
@SuppressWarnings("unlikely-arg-type")
public class Dijkstra extends PaparazziProblem implements SearchAlgorithm {

	public Dijkstra(PaparazziMap paparazziMap, boolean updateGUI) {
		super(paparazziMap, updateGUI);
	}

	@Override
	public PaparazziProblem startSearch() {
		// set search algo
		setSearchAlgorithm();
		// set start and end cell from generated map
		Cell startCell = paparazziMap.getStartCell();
		Cell endCell = paparazziMap.getEndCell();
		// check if start and end cell are different
		if (startCell.equals(endCell))
			return null;
		// start timer
		long startTime = System.currentTimeMillis();
		// create blocked cell set
		Set<Cell> blockedCell = new HashSet<>();
		// init path list
		List<Vertice<Cell, Integer>> pathList = new ArrayList<Vertice<Cell, Integer>>();
		CostComparator costComparator = new CostComparator();
		// create prio queue to store shortest path sorting by path costs
		PriorityQueue<List<Vertice<Cell, Integer>>> pathKeeper = new PriorityQueue<List<Vertice<Cell, Integer>>>(100, costComparator);
		// create new prio queue sorting by costs
		PriorityQueue<List<Vertice<Cell, Integer>>> priorityQueue = new PriorityQueue<List<Vertice<Cell, Integer>>>(100, costComparator);
		// set start cell as current
		Cell current = startCell;
		// add start cell with costs to path
		pathList.add(new Vertice<Cell, Integer>(current, 0));
		// add start cell to prio queue
		priorityQueue.add(pathList);
		// do as long queue is not empty
		while (!priorityQueue.isEmpty()) {
			// check if stop has been requested
			if (ViewGUIController.exit)
				return null;
			// get first vertice from prio queue
			List<Vertice<Cell, Integer>> minPath = priorityQueue.remove();
			// get current cell
			current = minPath.get(minPath.size() - 1).getChild();
			// check if endcell has been reached
			if (current.equals(endCell)) {
				// store path in shortest path
				pathKeeper.add(minPath);
				// set shortest path
				pathList = pathKeeper.element();
				// check if improved dijkstra is enabled -- quit after 1 shortest path
				if (Settings.isImprovedDijkstraEnabled) {
					break;
				}
			}
			// check if cell is not blocked
			if (blockedCell.contains(current))
				continue;
			// get free vertices
			for (Vertice<Cell, Integer> vertice : getFreeVertices(current)) {
				// check if not in prio queue
				if (!priorityQueue.contains(vertice.getChild()) && !blockedCell.contains(vertice.getChild())) {
					// create new path from min path
					List<Vertice<Cell, Integer>> newPath = new ArrayList<Vertice<Cell, Integer>>(minPath);
					// set parent
					vertice.setParent(current);
					// add vertice
					newPath.add(vertice);
					// add to queue
					priorityQueue.add(newPath);
				}
			}
			// add to blocked
			blockedCell.add(current);
			// blockedCell.add(minPath.get(minPath.size() - 1).getChild());
			// check if update
			if (updateGUI)
				updateNextVisitedNode(Arrays.asList(current));

		}
		// init path map
		super.pathMap = new HashMap<>();
		// build pathMap
		int costs = 0;
		for (Vertice<Cell, Integer> vertice : pathList) {
			// skip first child
			if (vertice.getChild() == null || vertice.getParent() == null)
				continue;
			// add cells
			pathMap.put(vertice.getChild(), vertice.getParent());
			// calculate costs
			costs += vertice.getCosts();
		}
		// end timer
		long endTime = System.currentTimeMillis();
		// calculate execution time
		long executionTime = endTime - startTime;

		// set results
		setExecutionTime(executionTime);
		// set visited nodes
		setVisitedNodes(blockedCell.size());
		if (pathMap.containsKey(endCell)) {
			setShortestPath(true);
			List<Cell> pathToEndCell = buildPath();
			// check if update
			if (updateGUI)
				updatePath(pathToEndCell);
			setTotalCosts(costs);
			// set shortest path costs for other algorithms
			ViewController.shortestPathCosts = getTotalCosts();
		}
		// check if update
		if (updateGUI)
			updateResults(getExecutionTime(), getTotalCosts(), getVisitedNodes(), isShortestPath());
		return this;
	}

	@Override
	public void setSearchAlgorithm() {
		setSearchAlgorithm(this);
	}


	private class CostComparator implements Comparator<List<Vertice<Cell, Integer>>> {

		@Override
		public int compare(List<Vertice<Cell, Integer>> v1, List<Vertice<Cell, Integer>> v2) {
			Integer total1 = getTotalCosts(v1);
			Integer total2 = getTotalCosts(v2);
			if (total1 > total2)
				return 1;
			else if (total1 < total2)
				return -1;
			return 0;
		}
		
		private Integer getTotalCosts(List<Vertice<Cell, Integer>> list) {
			Integer result = 0;
			for (Vertice<Cell, Integer> v : list) {
				result += v.getCosts();
			}
			return result;
		}
	}
	
}
