package utils;

import java.util.List;

import dao.SQLiteDAO;
import grabberApp.GrabberApp;
import grabberApp.javafx.fxmls.ControllerLandPage;
import javafx.scene.control.MenuButton;
import models.Library;

public class Utils {

	public static GrabberApp gApp;

	public static String dbFilePath, dbFolderPath, origin;

	public static MenuButton mbDownloads;

	public static SQLiteDAO sqlDao;

	public static Library selectedLibrary;

	public static List<Library> libraries;

	public static ControllerLandPage controllerLandPage;

//	/**
//	 * Comprueba que el systema operativo en el que estamos es Windows
//	 * 
//	 * @return true si estamos en Windows, false en caso de no
//	 */
//	public static boolean isOsWindows() {
//		return System.getProperty("os.name").contains("Windows");
//	}
//
//	/**
//	 * Comprueba que el sistema operativo en el que estamos no es Windows
//	 * 
//	 * @return true si no estamos en Windows, falso en caso de si
//	 */
//	public static boolean isOsUnix() {
//		return !isOsWindows();
//	}
//
//	/**
//	 * Comprueba que la base de datos existe y que el usuario ha introducido un
//	 * origen para las bibliotecas
//	 * 
//	 * @return true si no se ha configurado el origen, false si el origen existe
//	 */
//	public static boolean firstStart() {
//		String folderPath, user, filePath;
//
//		user = System.getProperty("user.name");
//		if (isOsWindows())
//			folderPath = "C:/Users/" + user + "/AppData/Local/JGrabber";
//		else
//			folderPath = "/home/" + user + "/.config/jgrabber";
//
//		filePath = folderPath + "/db.db";
//
//		File configFolder = new File(folderPath), config = new File(filePath);
//
//		return configFolder.exists() ? !config.exists() : true;
//	}
}
