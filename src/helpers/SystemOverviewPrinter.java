/**
 * Author: Robert Johner
 * Project: Paparazzi-Optimization
 * Date: 20 Apr 2019
 * File: SystemOverviewPrinter.java
 */
package helpers;

/**
 * Helper class to print system overview
 */
public class SystemOverviewPrinter {

	/**
	 * Returns system overview (OS, CPU, RAM and Java) in a string array
	 *
	 * @return string array with results
	 */
	public static String[] printSystemOverview() {
		long gigabytes =  1024 * 1024 * 1024;
		String[] results = new String[12];
		results[0] = "System Overview:";
		results[1] = "OS";
		results[2] = "\tOS: " + System.getProperty("os.name");
		results[3] = "\tOS Version: " + System.getProperty("os.version");
		results[4] = "\tOS Architecture: " + System.getProperty("os.arch");
		results[5] = "CPU";
		results[6] = "\tCPU Cores: " + Runtime.getRuntime().availableProcessors();
		results[7] = "RAM";
		results[8] = "\tRAM Max: " +  Runtime.getRuntime().maxMemory() / gigabytes + "GB";
		results[9] = "Java";
		results[10] = "\tJava version: " +  System.getProperty("java.version");
		results[11] = "\tJavaFX version: " +  System.getProperty("javafx.runtime.version");
		return results;
	}
}