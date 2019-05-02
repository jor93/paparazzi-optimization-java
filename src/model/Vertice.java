/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: Vertice.java
 */
package model;

/**
 * Class that holds a vertice from the paparazzi map. A vertice consists of a
 * child cell, a parent cell and the costs from cell to cell.
 */
@SuppressWarnings("rawtypes")
public class Vertice<V1, V2> {

	// holds child cell
	private V1 child;
	// holds parent cell
	private V1 parent;
	// holds costs
	private V2 costs;

	/**
	 * Creates a vertice with no parent cell
	 * 
	 * @author Robert Johner
	 * @param child the child cell
	 * @param costs  the cell costs
	 * @see Vertice
	 */
	public Vertice(V1 child, V2 costs) {
		this.child = child;
		this.costs = costs;
	}

	/**
	 * Creates a vertice with parent
	 * 
	 * @author Robert Johner
	 * @param child  the child cell
	 * @param parent the parent cell
	 * @param costs the costs of the verticeja ic
	 * @see Vertice
	 */
	public Vertice(V1 child, V1 parent, V2 costs) {
		this.child = child;
		this.parent = parent;
		this.costs = costs;
	}

	/**
	 * Returns child cell
	 * 
	 * @author Robert Johner
	 * @return the cell
	 */
	public V1 getChild() {
		return child;
	}

	/**
	 * Sets child cell
	 * 
	 * @author Robert Johner
	 * @param child the child cell
	 */
	public void setChild(V1 child) {
		this.child = child;
	}

	/**
	 * Returns parent cell
	 * 
	 * @author Robert Johner
	 * @return the parent cell
	 */
	public V1 getParent() {
		return parent;
	}

	/**
	 * Sets parent cell
	 * 
	 * @author Robert Johner
	 * @param parent the costs of the cell
	 */
	public void setParent(V1 parent) {
		this.parent = parent;
	}

	/**
	 * Returns costs of vertice
	 * 
	 * @author Robert Johner
	 * @return the cell cell
	 */
	public V2 getCosts() {
		return costs;
	}

	/**
	 * Sets costs of vertice
	 * 
	 * @author Robert Johner
	 * @param costs the costs of the vertice
	 */
	public void setCosts(V2 costs) {
		this.costs = costs;
	}

	@Override
	public int hashCode() {
		return child.hashCode() * parent.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// check if vertice
		if (obj instanceof Vertice) {
			// type cast
			Vertice v = (Vertice) obj;
			return v.child.equals(child);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Vertice [child=" + child + ", parent=" + parent + ", costs=" + costs + "]";
	}
}