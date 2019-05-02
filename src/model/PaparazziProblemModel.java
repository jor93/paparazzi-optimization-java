/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 15 Apr 2019
 * File: PaparazziProblemModel.java
 */
package model;

import java.util.ArrayList;
import java.util.List;

import algorithms.AStar;
import algorithms.BellmanFord;
import algorithms.BestFirst;
import algorithms.Dijkstra;
import algorithms.FloydWarshall;
import enums.MapDataSets;
import init.Settings;

/**
 * Class that represents the list of paparazzi problems to solve.
 */
public class PaparazziProblemModel {

	private List<PaparazziProblem> paparazziProblems;

	/**
	 * Creates a new PaparazziProblemModel object.
	 * 
	 * @author Robert Johner
	 * @see PaparazziProblemModel
	 */
	public PaparazziProblemModel() {
		this.paparazziProblems = new ArrayList<PaparazziProblem>(Settings.numberOfTests * MapDataSets.values().length);
	}

	/**
	 * Returns paparazzi problem list as array
	 * 
	 * @author Robert Johner
	 * @return the paparazzi problem list as array
	 * @see PaparazziProblem
	 */
	public PaparazziProblem[] getPaparazziProblemArray() {
		return this.paparazziProblems.toArray(new PaparazziProblem[paparazziProblems.size()]);
	}

	/**
	 * Returns paparazzi problem list
	 * 
	 * @author Robert Johner
	 * @return the paparazzi problem list
	 * @see PaparazziProblem
	 */
	public List<PaparazziProblem> getPaparazziProblemList() {
		return this.paparazziProblems;
	}

	/**
	 * Adds a paparazzi problem to the list. Removes not needed attributes to save RAM.
	 * 
	 * @author Robert Johner
	 * @param pp the paparazzi problem to add
	 * @return boolean true if add was successful
	 * @see PaparazziProblem
	 */
	public boolean addPapparazziProblem(PaparazziProblem pp) {
		pp.paparazziMap = null;
		pp.pathMap = null;
		return this.paparazziProblems.add(pp);
	}

	/**
	 * Clears paparazzi problem list
	 * 
	 * @author Robert Johner
	 * @see PaparazziProblem
	 */
	public void clearPaparazziProblems() {
		this.paparazziProblems.clear();
	}
	
	/**
	 * Returns average execution time for every search algorithm as string.
	 * 
	 * @author Robert Johner
	 * @return string array with different average execution time per search
	 *         algorithm
	 * @see String
	 */
	public String[] calculateAverageExecutionTime() {
		// create lists to store results
		List<Long> dijkstra = new ArrayList<>(Settings.numberOfTests);
		List<Long> aStar = new ArrayList<>(Settings.numberOfTests);
		List<Long> bellmanFord = new ArrayList<>(Settings.numberOfTests);
		List<Long> bestFirst = new ArrayList<>(Settings.numberOfTests);
		List<Long> floydWarshall = new ArrayList<>(Settings.numberOfTests);

		for (PaparazziProblem pp : paparazziProblems) {
			if (pp instanceof Dijkstra)
				dijkstra.add(pp.getExecutionTime());
			if (pp instanceof AStar)
				aStar.add(pp.getExecutionTime());
			if (pp instanceof BellmanFord)
				bellmanFord.add(pp.getExecutionTime());
			if (pp instanceof BestFirst)
				bestFirst.add(pp.getExecutionTime());
			if (pp instanceof FloydWarshall)
				floydWarshall.add(pp.getExecutionTime());
		}

		// set results
		String[] results = new String[6];
		results[0] = "Average Execution Time (in ms):";
		results[1] = ("Dijkstra: " + (float) dijkstra.stream().mapToLong(a -> a).sum() / (float) Settings.numberOfTests);
		results[2] = ("A*: " + (float) aStar.stream().mapToLong(a -> a).sum() / (float) Settings.numberOfTests);
		results[3] = ("Bellman Ford: " + (float) bellmanFord.stream().mapToLong(a -> a).sum() / (float) Settings.numberOfTests);
		results[4] = ("Best First Search: " + (float) bestFirst.stream().mapToLong(a -> a).sum() / (float) Settings.numberOfTests);
		results[5] = ("Floyd Warshall: " + (float) floydWarshall.stream().mapToLong(a -> a).sum() / (float) Settings.numberOfTests);

		return results;
	}

	/**
	 * Returns average visited nodes for every search algorithm as string.
	 * 
	 * @author Robert Johner
	 * @return string array with different average visited nodes costs per search
	 *         algorithm
	 * @see String
	 */
	public String[] calculateAverageVisitedNodes() {
		// create lists to store results
		List<Integer> dijkstra = new ArrayList<>(Settings.numberOfTests);
		List<Integer> aStar = new ArrayList<>(Settings.numberOfTests);
		List<Integer> bellmanFord = new ArrayList<>(Settings.numberOfTests);
		List<Integer> bestFirst = new ArrayList<>(Settings.numberOfTests);
		List<Integer> floydWarshall = new ArrayList<>(Settings.numberOfTests);

		for (PaparazziProblem pp : paparazziProblems) {
			if (pp instanceof Dijkstra)
				dijkstra.add(pp.getVisitedNodes());
			if (pp instanceof AStar)
				aStar.add(pp.getVisitedNodes());
			if (pp instanceof BellmanFord)
				bellmanFord.add(pp.getVisitedNodes());
			if (pp instanceof BestFirst)
				bestFirst.add(pp.getVisitedNodes());
			if (pp instanceof FloydWarshall)
				floydWarshall.add(pp.getVisitedNodes());
		}

		// set results
		String[] results = new String[6];
		results[0] = "Average Visisted Nodes:";
		results[1] = ("Dijkstra: " + (float) dijkstra.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);
		results[2] = ("A*: " + (float) aStar.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);
		results[3] = ("Bellman Ford: " + (float) bellmanFord.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);
		results[4] = ("Best First Search: " + (float) bestFirst.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);
		results[5] = ("Floyd Warshall: " + (float) floydWarshall.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);

		return results;
	}

	/**
	 * Returns average shortest path costs for every search algorithm as string.
	 * 
	 * @author Robert Johner
	 * @return string array with different average shortest path costs per search
	 *         algorithm
	 * @see String
	 */
	public String[] calculateAverageShortestPath() {
		// create lists to store results
		List<Integer> dijkstra = new ArrayList<>(Settings.numberOfTests);
		List<Integer> aStar = new ArrayList<>(Settings.numberOfTests);
		List<Integer> bellmanFord = new ArrayList<>(Settings.numberOfTests);
		List<Integer> bestFirst = new ArrayList<>(Settings.numberOfTests);
		List<Integer> FloydWarshall = new ArrayList<>(Settings.numberOfTests);

		for (PaparazziProblem pp : paparazziProblems) {
			if (pp instanceof Dijkstra)
				dijkstra.add(pp.getTotalCosts());
			if (pp instanceof AStar)
				aStar.add(pp.getTotalCosts());
			if (pp instanceof BellmanFord)
				bellmanFord.add(pp.getTotalCosts());
			if (pp instanceof BestFirst)
				bestFirst.add(pp.getTotalCosts());
			if (pp instanceof FloydWarshall)
				FloydWarshall.add(pp.getTotalCosts());
		}

		// set results
		String[] results = new String[6];
		results[0] = "Average Shortest Path Costs:";
		results[1] = ("Dijkstra: " + (float) dijkstra.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);
		results[2] = ("A*: " + (float) aStar.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);
		results[3] = ("Bellman Ford: " + (float) bellmanFord.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);
		results[4] = ("Best First Search: " + (float) bestFirst.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);
		results[5] = ("Floyd Warshall: " + (float) FloydWarshall.stream().mapToInt(a -> a).sum() / (float) Settings.numberOfTests);
		return results;
	}

	/**
	 * Returns average calculated average for every search algorithm as string.
	 * 
	 * @author Robert Johner
	 * @return string array with different average accuracy per search algorithm
	 * @see String
	 */
	public String[] calculateAverageAccuracy() {
		// create lists to store results
		List<Boolean> dijkstra = new ArrayList<>(Settings.numberOfTests);
		List<Boolean> aStar = new ArrayList<>(Settings.numberOfTests);
		List<Boolean> bellmanFord = new ArrayList<>(Settings.numberOfTests);
		List<Boolean> bestFirst = new ArrayList<>(Settings.numberOfTests);
		List<Boolean> FloydWarshall = new ArrayList<>(Settings.numberOfTests);

		for (PaparazziProblem pp : paparazziProblems) {
			if(!pp.isShortestPath()) continue;
			if (pp instanceof Dijkstra) {
				dijkstra.add(true);
				continue;
			}
			if (pp instanceof AStar) {
				aStar.add(true);
				continue;
			}
			if (pp instanceof BellmanFord) {
				bellmanFord.add(true);
				continue;
			}
			if (pp instanceof BestFirst) {
				bestFirst.add(true);
				continue;
			}
			if (pp instanceof FloydWarshall) {
				FloydWarshall.add(true);
				continue;
			}
		}

		// set results
		String[] results = new String[6];
		results[0] = "Accuracy of finding the shortest Path:";
		results[1] = ("Dijkstra: " + (float) dijkstra.size() * 100f / (float) Settings.numberOfTests) + "%";
		results[2] = ("A*: " + (float) aStar.size() * 100f / (float) Settings.numberOfTests) + "%";
		results[3] = ("Bellman Ford: " + (float) bellmanFord.size() * 100f / (float) Settings.numberOfTests) + "%";
		results[4] = ("Best First Search: " + (float) bestFirst.size() * 100f / (float) Settings.numberOfTests) + "%";
		results[5] = ("Floyd Warshall: " + (float) FloydWarshall.size() * 100f / (float) Settings.numberOfTests) + "%";

		return results;
	}
}