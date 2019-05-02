/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: SearchController.java
 */
package controller;

import algorithms.AStar;
import algorithms.BellmanFord;
import algorithms.BestFirst;
import algorithms.Dijkstra;
import algorithms.FloydWarshall;
import model.PaparazziMap;
import model.PaparazziProblem;

/**
 * Search Controller to trigger the different search algorithms. Controller is
 * implemented in a Singleton pattern.
 */
public class SearchController {

	// singleton instance
	private static SearchController instance = null;
	
	/**
	 * Initialize search controller in a singleton pattern
	 * 
	 * @author Robert Johner
	 * @return the search controller singleton instance 
	 * @see SearchController
	 */
	public static SearchController getInstance() {
		if (instance == null)
			instance = new SearchController();

		return instance;
	}

	/**
	 * Starts A* search algorithm on generated paparazzi map
	 * 
	 * @author Robert Johner
	 * @param paparazziMap	the paparazzi map to solve
	 * @param updateGUI		to update the GUI to show the next visited path
	 * @return the solved paparazzi problem
	 * @see PaparazziProblem
	 */
	public PaparazziProblem aStar(PaparazziMap paparazziMap, boolean updateGUI) {
		AStar aStar = new AStar(paparazziMap, updateGUI);
		return aStar.startSearch();
	}

	/**
	 * Starts Bellman Ford search algorithm on generated paparazzi map
	 * 
	 * @author Robert Johner
	 * @param paparazziMap	the paparazzi map to solve
	 * @param updateGUI		to update the GUI to show the next visited path
	 * @return the solved paparazzi problem
	 * @see PaparazziProblem
	 */
	public PaparazziProblem bellmanFord(PaparazziMap paparazziMap, boolean updateGUI) {
		BellmanFord bellmanFord = new BellmanFord(paparazziMap, updateGUI);
		return bellmanFord.startSearch();
	}

	/**
	 * Starts Best First search algorithm on generated paparazzi map
	 * 
	 * @author Robert Johner
	 * @param paparazziMap	the paparazzi map to solve
	 * @param updateGUI		to update the GUI to show the next visited path
	 * @return the solved paparazzi problem
	 * @see PaparazziProblem
	 */
	public PaparazziProblem bestFirst(PaparazziMap paparazziMap, boolean updateGUI) {
		BestFirst bestFirst = new BestFirst(paparazziMap, updateGUI);
		return bestFirst.startSearch();
	}

	/**
	 * Starts Dijkstra search algorithm on generated paparazzi map
	 * 
	 * @author Robert Johner
	 * @param paparazziMap	the paparazzi map to solve
	 * @param updateGUI		to update the GUI to show the next visited path
	 * @return the solved paparazzi problem
	 * @see PaparazziProblem 
	 */
	public PaparazziProblem dijkstra(PaparazziMap paparazziMap, boolean updateGUI) {
		Dijkstra dijkstra = new Dijkstra(paparazziMap, updateGUI);
		return dijkstra.startSearch();
	}

	/**
	 * Starts Floyd Warshall search algorithm on generated paparazzi map
	 * 
	 * @author Robert Johner
	 * @param paparazziMap	the paparazzi map to solve
	 * @param updateGUI		to update the GUI to show the next visited path
	 * @return the solved paparazzi problem
	 * @see PaparazziProblem
	 */
	public PaparazziProblem floydWarshall(PaparazziMap paparazziMap, boolean updateGUI) {
		FloydWarshall floydWarshall = new FloydWarshall(paparazziMap, updateGUI);
		return floydWarshall.startSearch();
	}
}