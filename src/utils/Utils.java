package utils;

import grabberApp.GrabberApp;
import javafx.scene.control.MenuButton;
import models.AbstractController;

/**
 * Static class used for the application necessities
 * 
 * @author Daniel Serrano Rodriguez
 */
public class Utils {
	/**
	 * Main application running
	 */
	public static GrabberApp gApp;

	/**
	 * Paths for the necessary files of the application.
	 * 
	 * - folderPath: path of the folder where the files are going to be
	 * 
	 * - dbFilePath: path of the DB file
	 * 
	 * - origin: path where the application libraries will reside
	 */
	public static String dbFilePath, folderPath, origin;

	/**
	 * Download menu button, that access to the downloads done
	 */
	public static MenuButton mbDownloads;

	/**
	 * Controller being used
	 */
	public static AbstractController controller;
}