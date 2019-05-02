/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: ViewGUIController.java
 */
package controller;

import java.net.URL;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

import enums.MapDataSets;
import enums.MapObjects;
import init.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.PaparazziMap;

/**
 * View Main Controller for the PaparazziViewGUI.fxml view. Controller is
 * implemented in a Singleton pattern.
 */
public class ViewGUIController extends ViewController implements Initializable {

	// singleton
	private static ViewGUIController instance = null;
	// map
	private PaparazziMap map;
	// current GUI thread
	private Thread currentThread;
	// GUI threads
	private PriorityQueue<Thread> threads;
	// stop thread
	public static boolean exit = false;

	// fx elements
	@FXML
	protected Label idLabelMapSize;
	@FXML
	private Label idLabelColorNextCell;
	@FXML
	private ComboBox<String> idComboDataSet;
	@FXML
	private Button idButtonAStar;
	@FXML
	private Button idButtonDijkstra;
	@FXML
	private Button idButtonBellmanFord;
	@FXML
	private Button idButtonBestFirst;
	@FXML
	private Button idButtonFloydWarshall;
	@FXML
	private Button idButtonReset;

	private ViewGUIController() {
		this.threads = new PriorityQueue<Thread>();
	}

	/**
	 * Initialize main controller in a singleton pattern
	 * 
	 * @return MainController the main controller singleton instance
	 * @see ViewGUIController
	 */
	public static ViewGUIController getInstance() {
		if (instance == null)
			instance = new ViewGUIController();
		return instance;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// set legend colors
		this.idLabelColorStartCell.setStyle("-fx-background-color:" + MapObjects.STARTCELL.getColor().toRGB());
		this.idLabelColorEndCell.setStyle("-fx-background-color:" + MapObjects.ENDCELL.getColor().toRGB());
		this.idLabelColorPond.setStyle("-fx-background-color:" + MapObjects.POND.getColor().toRGB());
		this.idLabelColorWall.setStyle("-fx-background-color:" + MapObjects.WALL.getColor().toRGB());
		this.idLabelColorCamera.setStyle("-fx-background-color:" + MapObjects.CAMERA.getColor().toRGB());
		this.idLabelColorCameraRadius.setStyle("-fx-background-color:" + MapObjects.CAMERARADIUS.getColor().toRGB());
		this.idLabelColorPath.setStyle("-fx-background-color:" + MapObjects.PATH.getColor().toRGB());
		this.idLabelColorNextCell.setStyle("-fx-background-color:" + MapObjects.NEXTCELL.getColor().toRGB());

		idComboDataSet.getItems().removeAll(idComboDataSet.getItems());
		// add map sizes
		idComboDataSet.getItems().addAll(MapDataSets.values()[0].toString(), MapDataSets.values()[1].toString(),
				MapDataSets.values()[2].toString(), MapDataSets.values()[3].toString(),
				MapDataSets.values()[4].toString());
		// set default values
		idComboDataSet.getSelectionModel().select(MapDataSets.values()[1].toString());

		// generate random paparazzi map
		PaparazziMap map = MapController.getInstance().generateMap(MapDataSets.values()[1]);
		// set map
		this.map = map;
		// draw input map
		drawInputPaparazziMap(map);
	}

	@FXML
	private void onItemSelected(ActionEvent event) {
		
		MapDataSets set = null;
		switch (this.idComboDataSet.getValue()) {
		case "TINY":
			set = MapDataSets.TINY;
			Settings.showCosts = true;
			break;
		case "SMALL":
			set = MapDataSets.SMALL;
			Settings.showCosts = true;
			break;
		case "MEDIUM":
			set = MapDataSets.MEDIUM;
			Settings.showCosts = true;
			break;
		case "LARGE":
			set = MapDataSets.LARGE;
			Settings.showCosts = false;
			break;
		case "HUGE":
			set = MapDataSets.HUGE;
			Settings.showCosts = false;
			break;
		}
		// generate random paparazzi map
		PaparazziMap map = MapController.getInstance().generateMap(set);
		// set map
		this.map = map;
		// draw input map
		drawInputPaparazziMap(map);
	}

	@FXML
	private void onClickedAStar(MouseEvent event) {
		// set exit
		exit = true;
		// re-draw input map
		drawInputPaparazziMap(map);
		// clear results
		resetResultsOnView();
		// wait for 100ms
		try {
			Thread.sleep(100);
			// reset exit
			exit = false;
			// start search
			currentThread = new Thread(() -> SearchController.getInstance().aStar(map, true));
			this.threads.add(currentThread);
			this.threads.poll().start();
		} catch (InterruptedException e) {
		}

	}

	@FXML
	private void onClickedDijkstra(MouseEvent event) {
		// set exit
		exit = true;
		// re-draw input map
		drawInputPaparazziMap(map);
		// clear results
		resetResultsOnView();
		// wait for 100ms
		try {
			Thread.sleep(100);
			// reset exit
			exit = false;
			// start search
			currentThread = new Thread(() -> SearchController.getInstance().dijkstra(map, true));
			this.threads.add(currentThread);
			this.threads.poll().start();
		} catch (InterruptedException e) {
		}
	}

	@FXML
	private void onClickedBellmanFord(MouseEvent event) {
		// set exit
		exit = true;
		// re-draw input map
		drawInputPaparazziMap(map);
		// clear results
		resetResultsOnView();
		// wait for 100ms
		try {
			Thread.sleep(100);
			// reset exit
			exit = false;
			// start search
			currentThread = new Thread(() -> SearchController.getInstance().bellmanFord(map, true));
			this.threads.add(currentThread);
			this.threads.poll().start();
		} catch (InterruptedException e) {
		}
	}

	@FXML
	private void onClickedBestFirst(MouseEvent event) {
		// set exit
		exit = true;
		// re-draw input map
		drawInputPaparazziMap(map);
		// clear results
		resetResultsOnView();
		// wait for 100ms
		try {
			Thread.sleep(100);
			// reset exit
			exit = false;
			// start search
			currentThread = new Thread(() -> SearchController.getInstance().bestFirst(map, true));
			this.threads.add(currentThread);
			this.threads.poll().start();
		} catch (InterruptedException e) {
		}
	}

	@FXML
	private void onClickedFloydWarshall(MouseEvent event) {
		// set exit
		exit = true;
		// re-draw input map
		drawInputPaparazziMap(map);
		// clear results
		resetResultsOnView();
		// wait for 100ms
		try {
			Thread.sleep(100);
			// reset exit
			exit = false;
			// start search
			currentThread = new Thread(() -> SearchController.getInstance().floydWarshall(map, true));
			this.threads.add(currentThread);
			this.threads.poll().start();
		} catch (InterruptedException e) {
		}
	}

	@FXML
	private void onClickedReset(MouseEvent event) {
		// stop thread
		exit = true;
		// clear thread
		this.currentThread = null;
		// clear queue
		this.threads.clear();
		// re-draw input map
		drawInputPaparazziMap(map);
		// clear results
		resetResultsOnView();
	}
}