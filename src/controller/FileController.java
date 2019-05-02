/**
git  * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 15 Apr 2019
 * File: FileController.java
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import enums.MapDataSets;
import enums.SearchAlgorithms;
import init.Settings;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;

/**
 * Main Controller for the PaparazziView.fxml view. Controller is implemented in
 * a Singleton pattern.
 */
public class FileController {

	// singleton
	private static FileController instance = null;

	private FileController() {
		// init result folder structure
		initResultFolderStructure();
	}

	/**
	 * Initialize and returns FileController instance.
	 * 
	 * @author Robert Johner
	 * @return the file controller singleton instance
	 * @see FileController
	 */
	public static FileController getInstance() {
		if (instance == null)
			instance = new FileController();
		return instance;
	}

	/**
	 * Initialize result folder structure on file system. If an old results has been
	 * found, it the folder gets deleted first. For every DataSet a new sub-folder
	 * is created under "results".
	 * 
	 * @author Robert Johner
	 */
	public void initResultFolderStructure() {
		// check if old results folder exists
		try {
			// check if settings are getting deleted before launching tests
			if (Settings.deleteResultsEveryTestRun)
				FileUtils.forceDelete(new File(Settings.urlResultFolder));
		} catch (IOException e) {
		}
		for (int i = 0; i < MapDataSets.values().length; i++)
			// create new folder
			new File("results/" + MapDataSets.values()[i]).mkdirs();
		try {
			if (Settings.deleteResultsEveryTestRun) {
				// delete old results file
				Files.deleteIfExists(Paths.get(Settings.urlResult));
			}
			// create new result file
			Files.createFile(Paths.get(Settings.urlResult));
		} catch (IOException e) {
		}

	}

	/**
	 * Export view to file on file system under /results/{DataSet}/ as *.png file.
	 * 
	 * @author Robert Johner
	 * @param borderPane      the view that should be saved
	 * @param dataSet         the data set to save file in correct result folder
	 * @param testNumber      the test number to identify test run
	 * @param searchAlgorithm the search algorithm enum
	 */
	public void exportViewToFile(BorderPane borderPane, MapDataSets dataSet, int testNumber,
			SearchAlgorithms searchAlgorithm) {
		try {
			// disable cache
			borderPane.setCache(false);
			ImageIO.setUseCache(false);
			// make snapshot of view
			WritableImage image = borderPane.snapshot(new SnapshotParameters(), null);
			String fileName = "";
			switch (searchAlgorithm) {
			case Input:
				fileName = "0_" + searchAlgorithm.getName();
				break;
			case AStar:
				fileName = "1_" + searchAlgorithm.getName();
				break;
			case BellmanFord:
				fileName = "2_" + searchAlgorithm.getName();
				break;
			case BestFirst:
				fileName = "3_" + searchAlgorithm.getName();
				break;
			case Dijkstra:
				fileName = "4_" + searchAlgorithm.getName();
				break;
			case FloydWarshall:
				fileName = "5_" + searchAlgorithm.getName();
				break;
			}
			// save snapshot as png to file
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File("results/" + dataSet.name() + "/" + testNumber + "_" + fileName+ "." + Settings.fileExtensionView));
			// flush images
			borderPane = null;
		} catch (IOException ex) {
		} finally {
			// call carbage collector
			System.gc();
		}
	}

	/**
	 * Export results to file on file system under /results/results.txt. Prints
	 * details about every tested algorithm.
	 * 
	 * @author Robert Johner
	 * @param results the string array that should be printed to the file
	 */
	public void exportResultsToFile(String[] results) {
		try {
			List<String> lines = Arrays.asList(results);
			Path file = Paths.get(Settings.urlResult);
			Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		} catch (IOException ex) {
		}

	}
}