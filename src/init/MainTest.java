/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 14 Apr 2019
 * File: MainTest.java
 */
package init;

import java.text.SimpleDateFormat;
import algorithms.*;
import controller.FileController;
import controller.ViewTestController;
import controller.MapController;
import controller.PaparazziProblemController;
import controller.SearchController;
import enums.MapDataSets;
import enums.SearchAlgorithms;
import helpers.SystemOverviewPrinter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.PaparazziMap;
import model.PaparazziProblem;

/**
 * Class that launches the application in Test mode
 */
public class MainTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PaparazziViewTest.fxml"));
		ViewTestController controller = ViewTestController.getInstance();
		loader.setController(controller);
		Parent root = loader.load();
		primaryStage.setTitle("Paparazzi Problem");
		primaryStage.getIcons().add(new Image(Settings.urlIcon));
		Scene scene = new Scene(root);

		// show view
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

		// init testing
		long startTime = initTesting();

		// start testing
		startTesting(controller);

		// finalize testing
		finalizeTesting(startTime);

		// close view
		primaryStage.close();
		// stop application
		stop();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private long initTesting() {
		// disable diagonal movement for test
		Settings.isDiagonalMovementAllowed = false;

		// print system overview
		FileController.getInstance().exportResultsToFile(SystemOverviewPrinter.printSystemOverview());

		// time formatters
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

		// get starting time
		long startTime = System.currentTimeMillis();
		FileController.getInstance()
				.exportResultsToFile(new String[] { "\n\n-------------------------------------------------",
						"TEST START", "-------------------------------------------------",
						"Start Time: " + sdf1.format(startTime) });
		return startTime;
	}

	private void finalizeTesting(long startTime) {
		// time formatters
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss.SSS");
		// get end time
		long endTime = System.currentTimeMillis();
		FileController.getInstance().exportResultsToFile(new String[] { "End Time: " + sdf1.format(endTime) });
		// calculate execution time
		long executionTime = endTime - startTime;
		FileController.getInstance().exportResultsToFile(new String[] { "Execution Time: " + sdf2.format(executionTime),
				"TEST END", "-------------------------------------------------", });
	}

	private void startTesting(ViewTestController controller) {
		// init controllers
		FileController fileController = FileController.getInstance();
		MapController mapController = MapController.getInstance();
		PaparazziProblemController problemController = PaparazziProblemController.getInstance();
		SearchController searchController = SearchController.getInstance();
		// iterate over data sets and calculate results
		for (int i = 0; i < MapDataSets.values().length; i++) {
			// if data set is large --> disable costs on view
			if (MapDataSets.values()[i] == MapDataSets.LARGE) 
				Settings.showCosts = false;
			// iterate over number of tests
			for (int j = 0; j < Settings.numberOfTests; j++) {
				// generate random paparazzi map
				PaparazziMap map = mapController.generateMap(MapDataSets.values()[i]);
				// draw input map
				controller.drawInputPaparazziMap(map);
				// save input map image in result folder
				if (Settings.printResultsInResultsFolder) {
					fileController.exportViewToFile(controller.getBorderPane(), MapDataSets.values()[i], j,SearchAlgorithms.Input);
				}
				
				// Dijkstra
				Dijkstra dijkstra = (Dijkstra) searchController.dijkstra(map, false);
				if (Settings.printResultsInResultsFolder) {
					controller.drawSolvedPaparazziMap(dijkstra, map, SearchAlgorithms.Dijkstra);
					fileController.exportViewToFile(controller.getBorderPane(), MapDataSets.values()[i], j, SearchAlgorithms.Dijkstra);
					controller.drawInputPaparazziMap(map);
				}

				// A*
				AStar aStar = (AStar) searchController.aStar(map, false);
				if (Settings.printResultsInResultsFolder) {
					controller.drawSolvedPaparazziMap(aStar, map, SearchAlgorithms.AStar);
					FileController.getInstance().exportViewToFile(controller.getBorderPane(), MapDataSets.values()[i], j, SearchAlgorithms.AStar);
					controller.drawInputPaparazziMap(map);
				}
				// Bellman Ford
				BellmanFord bellmanFord = (BellmanFord) searchController.bellmanFord(map, false);
				if (Settings.printResultsInResultsFolder) {
					controller.drawSolvedPaparazziMap(bellmanFord, map, SearchAlgorithms.BellmanFord);
					FileController.getInstance().exportViewToFile(controller.getBorderPane(), MapDataSets.values()[i], j, SearchAlgorithms.BellmanFord);
					controller.drawInputPaparazziMap(map);
				}
				// Best First
				BestFirst bestFirst = (BestFirst) searchController.bestFirst(map, false);
				if (Settings.printResultsInResultsFolder) {
					controller.drawSolvedPaparazziMap(bestFirst, map, SearchAlgorithms.BestFirst);
					FileController.getInstance().exportViewToFile(controller.getBorderPane(), MapDataSets.values()[i], j, SearchAlgorithms.BestFirst);
					controller.drawInputPaparazziMap(map);
				}
				// Floyd Warshall
				FloydWarshall floydWarshall = (FloydWarshall) searchController.floydWarshall(map, false);
				if (Settings.printResultsInResultsFolder) {
					controller.drawSolvedPaparazziMap(floydWarshall, map, SearchAlgorithms.FloydWarshall);
					fileController.exportViewToFile(controller.getBorderPane(), MapDataSets.values()[i], j, SearchAlgorithms.FloydWarshall);
					controller.drawInputPaparazziMap(map);
				}
				
				// add problems
				problemController.addPapparazziProblem((PaparazziProblem) dijkstra);
				problemController.addPapparazziProblem((PaparazziProblem) aStar);
				problemController.addPapparazziProblem((PaparazziProblem) bellmanFord);
				problemController.addPapparazziProblem((PaparazziProblem) bestFirst);
				problemController.addPapparazziProblem((PaparazziProblem) floydWarshall);
				
				// call carbage collector
				System.gc();
				
				System.out.println("Test run "+ j + " for " + MapDataSets.values()[i] +" successfull");
			}

			// print results for dataset
			fileController.exportResultsToFile(new String[] { "DataSet: " + MapDataSets.values()[i].toString(),
					"-------------------------------------------------" });
			// print results
			fileController.exportResultsToFile(problemController.calculateAverageExecutionTime());
			fileController.exportResultsToFile(problemController.calculateAverageVisitedNodes());
			fileController.exportResultsToFile(problemController.calculateAverageShortestPath());
			fileController.exportResultsToFile(problemController.calculateAverageAccuracy());
			fileController.exportResultsToFile(new String[] { "-------------------------------------------------\n" });

			// clear dataset
			problemController.clearPaparazziProblems();

			// call carbage collector
			System.gc();
		}		
	}
}
