/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: PaparazziProblemController.java
 */
package controller;

import java.util.List;
import model.PaparazziProblem;
import model.PaparazziProblemModel;

/**
 * Paparazzi problem controller to manage different data set runs. Controller holds and manages an PaparazziProblemModel.
 * Controller is implemented in a Singleton pattern.
 */
public class PaparazziProblemController {

	// singleton instance
	private static PaparazziProblemController instance = null;
	
	// paparazzi problem model
	private PaparazziProblemModel paparazziModel;

	private PaparazziProblemController() {
		// init problem model
		this.paparazziModel = new PaparazziProblemModel();
	}

	/**
	 * Initialize and returns PaparazziProblemController instance.
	 * 
	 * @author Robert Johner
	 * @return the paparazzi problem controller singleton instance
	 * @see PaparazziProblemController
	 */
	public static PaparazziProblemController getInstance() {
		if (instance == null)
			instance = new PaparazziProblemController();

		return instance;
	}

	/**
	 * Returns paparazzi problem list as array.
	 * 
	 * @author Robert Johner
	 * @return the paparazzi problem list as array
	 * @see PaparazziProblem
	 */
	public PaparazziProblem [] getPaparazziProblemArray() {
		return this.paparazziModel.getPaparazziProblemArray();
	}

	/**
	 * Returns paparazzi problem list.
	 * 
	 * @author Robert Johner
	 * @return the paparazzi problem list
	 * @see PaparazziProblem
	 */
	public List<PaparazziProblem> getPaparazziProblemList() {
		return this.paparazziModel.getPaparazziProblemList();
	}
	
	/**
	 * Add a paparazzi problem to the list.
	 * 
	 * @author Robert Johner
	 * @param pp the paparazzi problem to add
	 * @return true if add was successful
	 * @see PaparazziProblem
	 */
	public boolean addPapparazziProblem(PaparazziProblem pp) {
		return this.paparazziModel.addPapparazziProblem(pp);
	}

	/**
	 * Clears paparazzi problem list
	 * 
	 * @author Robert Johner
	 * @see PaparazziProblem
	 */
	public void clearPaparazziProblems() {
		this.paparazziModel.clearPaparazziProblems();
	}
	
	/**
	 * Returns average calculated average for every search algorithm as string.
	 * 
	 * @author Robert Johner
	 * @return string array with different average accuracy per search algorithm
	 * @see String
	 */
	public String [] calculateAverageAccuracy() {
		return this.paparazziModel.calculateAverageAccuracy();
	}
	
	/**
	 * Returns average shortest path costs for every search algorithm as string.
	 * 
	 * @author Robert Johner
	 * @return string array with different average shortest path costs per search algorithm
	 * @see String
	 */
	public String [] calculateAverageShortestPath() {
		return this.paparazziModel.calculateAverageShortestPath();
		
	}
	
	/**
	 * Returns average visited nodes for every search algorithm as string.
	 * 
	 * @author Robert Johner
	 * @return string array with different average visited nodes costs per search algorithm
	 * @see String
	 */
	public String [] calculateAverageVisitedNodes() {
		return this.paparazziModel.calculateAverageVisitedNodes();
		
	}
		
	/**
	 * Returns average execution time for every search algorithm as string.
	 * 
	 * @author Robert Johner
	 * @return string array with different average execution time per search algorithm
	 * @see String
	 */
	public String [] calculateAverageExecutionTime() {
		return this.paparazziModel.calculateAverageExecutionTime();	
	}	
}