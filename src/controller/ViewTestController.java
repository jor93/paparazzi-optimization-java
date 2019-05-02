/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 12 Apr 2019
 * File: ViewTestController.java
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import enums.MapObjects;
import javafx.fxml.Initializable;

/**
 * View Test Controller for the PaparazziViewTest.fxml view. Controller is implemented in
 * a Singleton pattern.
 */
public class ViewTestController extends ViewController implements Initializable {

	// singleton
	private static ViewTestController instance = null;
		
	/**
	 * Initialize main controller in a singleton pattern
	 * 
	 * @return MainController the main controller singleton instance
	 * @see ViewTestController
	 */
	public static ViewTestController getInstance() {
		if (instance == null)
			instance = new ViewTestController();
		return instance;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// set title
		this.idLabelMapType.setText("");
		
		// set legend colors
		this.idLabelColorStartCell.setStyle("-fx-background-color:" + MapObjects.STARTCELL.getColor().toRGB());
		this.idLabelColorEndCell.setStyle("-fx-background-color:" + MapObjects.ENDCELL.getColor().toRGB());
		this.idLabelColorPond.setStyle("-fx-background-color:" + MapObjects.POND.getColor().toRGB());
		this.idLabelColorWall.setStyle("-fx-background-color:" + MapObjects.WALL.getColor().toRGB());
		this.idLabelColorCamera.setStyle("-fx-background-color:" + MapObjects.CAMERA.getColor().toRGB());
		this.idLabelColorPath.setStyle("-fx-background-color:" + MapObjects.PATH.getColor().toRGB());		
	}
}
