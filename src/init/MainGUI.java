/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 14 Apr 2019
 * File: MainGUI.java
 */
package init;

import controller.ViewGUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class that launches the application in Demo mode
 */
public class MainGUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PaparazziViewGUI.fxml"));
		ViewGUIController controller = ViewGUIController.getInstance();
		loader.setController(controller);
		Parent root = loader.load();
		primaryStage.setTitle("Paparazzi Problem");
		primaryStage.getIcons().add(new Image(Settings.urlIcon));
		Scene scene = new Scene(root);

		// show view
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
