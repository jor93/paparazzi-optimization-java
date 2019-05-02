/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 18 Apr 2019
 * File: ViewController.java
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import algorithms.BellmanFord;
import algorithms.FloydWarshall;
import enums.MapObjects;
import enums.SearchAlgorithms;
import init.Settings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Cell;
import model.PaparazziMap;
import model.PaparazziProblem;

/**
 * Abstract class that represents a view controller.
 */
public abstract class ViewController implements Initializable {
	
	public static int shortestPathCosts = 0;
	
	@FXML
	protected BorderPane borderPane;
	@FXML
	protected AnchorPane anchorPane;
	@FXML
	protected Label idLabelDataSet;
	@FXML
	protected Label idLabelMapType;
	@FXML
	protected Label idLabelColorStartCell;
	@FXML
	protected Label idLabelColorEndCell;
	@FXML
	protected Label idLabelColorPond;
	@FXML
	protected Label idLabelColorWall;
	@FXML
	protected Label idLabelColorCamera;
	@FXML
	protected Label idLabelColorCameraRadius;
	@FXML
	protected Label idLabelColorPath;
	@FXML
	protected Label idLabelResultExecutionTime;
	@FXML
	protected Label idLabelResultCosts;
	@FXML
	protected Label idLabelResultNodes;
	@FXML
	protected Label idLabelResultShortestPath;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {			
		// set data set
		this.idLabelDataSet.setText("Data Set: ");
		// reset results
		resetResultsOnView();
	}
	
	/**
	 * Draws input map with generated map
	 * 
	 * @param map the map to draw on the view
	 */
	public void drawInputPaparazziMap(PaparazziMap map) {
		// set title
		this.idLabelMapType.setText("Input Map");
		if(this.idLabelDataSet!= null)
			this.idLabelDataSet.setText("Data Set: " + map.getDataSet().toString());
		// reset results
		resetResultsOnView();
		// clear anchor pane
		this.anchorPane.getChildren().clear();
		// draw map
		Cell[][] temp = map.getMap();
		int size = map.getDataSet().getSize();
		Cell current;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				current = temp[j][i];
				current.setPrefWidth((Settings.cellHeight / (size)) - (2));
				current.setPrefHeight((Settings.cellHeight / (size)) - (2));
				if (Settings.showCosts) current.setText(current.getCost() + "");
				current.setStyle("-fx-background-color:" + current.getCellType().getColor().toRGB());
				current.setLayoutX(i * Settings.cellHeight / size + 1);
				current.setLayoutY(j * Settings.cellHeight / size + 1);
				this.anchorPane.getChildren().add(current);
			}
		}
	}

	/**
	 * Draws solved paparazzi map
	 * 
	 * @param problem 	the problem object
	 * @param map 		the map object
	 * @param algorithm the algorithm object
	 */
	public void drawSolvedPaparazziMap(PaparazziProblem problem, PaparazziMap map, SearchAlgorithms algorithm) {
		// set title
		this.idLabelMapType.setText(algorithm.getName());
		// set data set
		if(this.idLabelDataSet!= null)
			this.idLabelDataSet.setText("Data Set: " + map.getDataSet().toString());

		List<Cell> path;
		switch (algorithm) {
		case BellmanFord:
			path = ((BellmanFord)problem).getPath();
			break;
		case FloydWarshall:
			path = new ArrayList<Cell> (((FloydWarshall)problem).getPath());
			break;
		default:
			path = problem.buildPath();
			break;
		}

		// color path
		for (Cell cell : path) {
			// skip to color start or end cell
			if(cell.isEndCell() || cell.isStartCell()) continue;
			cell.setStyle("-fx-background-color:" + MapObjects.PATH.getColor().toRGB());
		}

		// set results
		setResultsOnView(problem.getExecutionTime(), problem.getTotalCosts(), problem.getVisitedNodes(), problem.isShortestPath());
	}

	/**
	 * Returns border pane
	 * 
	 * @return the border pane object
	 */
	public BorderPane getBorderPane() {
		return borderPane;
	}
	
	/**
	 * Updates the result pane on the GUI
	 * 
	 * @param executionTime the calculated execution time
	 * @param costs the calculated total costs
	 * @param visitedNodes the amount of visited nodes
	 * @param isShortestPath the shortest path found
	 */
	public void setResultsOnView(long executionTime, int costs, int visitedNodes, boolean isShortestPath) {
		// set results
		this.idLabelResultExecutionTime.setText("Execution Time: " + (executionTime == 0 ? "<0" : executionTime) + "ms");
		this.idLabelResultCosts.setText("Total Costs: " + costs);
		this.idLabelResultNodes.setText("Traversed Nodes: " + visitedNodes);
		this.idLabelResultShortestPath.setText("Shortest Path: " + isShortestPath);
	}
	
	protected void resetResultsOnView() {
		// set results
		this.idLabelResultExecutionTime.setText("Execution Time: ");
		this.idLabelResultCosts.setText("Total Costs: ");
		this.idLabelResultNodes.setText("Traversed Nodes: ");
		this.idLabelResultShortestPath.setText("Shortest Path: ");
	}
	
	protected void drawPath(PaparazziMap map) {
		Cell[][] temp = map.getMap();
		int size = temp.length;
		Cell current;
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++) {
				current = temp[i][j];
				current.setPrefWidth((Settings.cellHeight / size) - (2));
				current.setPrefHeight((Settings.cellHeight / size) - (2));
				if (Settings.showCosts)
					current.setText(current.getCost() + "");
				current.setStyle("-fx-background-color:" + current.getCellType().getColor().toRGB());
				current.setLayoutX(i * Settings.cellHeight / size + 1);
				current.setLayoutY(j * Settings.cellHeight / size + 1);
				this.anchorPane.getChildren().add(current);
			}
		}
	}
}
