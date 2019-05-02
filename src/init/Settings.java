/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 14 Apr 2019
 * File: Settings.java
 */
package init;

/**
 * Class that holds general project settings
 */
public class Settings {

	// icon
	public static String urlIcon = "icon.png";
	
	// view options
	public static boolean showCosts = true;
	public static int cellHeight = 530;
	
	// test options 
	public static int numberOfTests = 50;
	public static boolean printResultsInResultsFolder = true;
	public static boolean deleteResultsEveryTestRun = false;
	
	// search algorithm
	public static int refreshRateGUI = 30;
	public static boolean isDiagonalMovementAllowed = false; 
	public static boolean isImprovedDijkstraEnabled = false;
	
	// export options
	public static String fileExtensionView = "png";
	public static String fileExtensionResult = "txt";
	public static String urlResultFolder = "results/";
	public static String urlTiny = urlResultFolder + "tiny/";
	public static String urlSmall = urlResultFolder + "small/";
	public static String urlMedium = urlResultFolder + "medium/";
	public static String urlLarge = urlResultFolder + "large/";
	public static String urlHuge = urlResultFolder + "huge/";
	public static String urlResult = urlResultFolder + "results" + "." + fileExtensionResult;
}
