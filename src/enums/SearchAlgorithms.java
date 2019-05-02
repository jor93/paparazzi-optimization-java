/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 15 Apr 2019
 * File: SerachAlgorithms.java
 */
package enums;

/**
 * Enumeration that holds the different search algorithms.
 */
public enum SearchAlgorithms {

	AStar("AStar"),
	BellmanFord("Bellman Ford"),
	BestFirst("Best First Search"),
	Dijkstra("Dijkstra"),
	FloydWarshall("Floyd Warshall"),
	Input("Input Map");

	private String name;

	private SearchAlgorithms(String name) {
		this.name = name;
	}

	/**
	 * Returns name of search algorithm
	 * 
	 * @author Robert Johner
	 * @return the search algorithm name
	 */
	public String getName() {
		return name;
	}
}