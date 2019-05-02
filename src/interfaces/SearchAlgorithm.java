/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: SearchAlgorithm.java
 */
package interfaces;

import model.PaparazziProblem;

/**
 * Interface to implement a search algorithm.
 */
public interface SearchAlgorithm {
	
	/**
	 * Start search algorithm
	 * 
	 * @author Robert Johner
	 * @return the solved paparazzi problem
	 * @see PaparazziProblem
	 */
	public PaparazziProblem startSearch();
	
	/**
	 * Set search algorithm
	 * 
	 * @author Robert Johner
	 */
	public void setSearchAlgorithm();
}