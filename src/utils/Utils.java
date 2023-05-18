package utils;

import java.io.File;
import java.util.List;

import dao.SQLiteDAO;
import grabberApp.GrabberApp;
import javafx.scene.control.MenuButton;
import models.Library;

public class Utils {

	public static GrabberApp gApp;

	public static MenuButton mbDownloads;

	public static SQLiteDAO sqlDao;

	public static Library selectedLibrary;

	public static List<Library> libraries;

	private static String system = System.getProperty("os.name"), user = System.getProperty("user.name"), folderPath,
			path;

	public static String getFolderPath() {
		return folderPath;
	}

	public static String getPath() {
		return path;
	}

	public static boolean isInWindows() {
		return system.contains("Windows");
	}

	public static boolean isInLinux() {
		return !isInWindows();
	}

	public static boolean firstStart() {
		if (isInWindows())
			folderPath = "C:/Users/" + user + "/AppData/Local/JGrabber";
		else
			folderPath = "/home/" + user + "/.config/jgrabber";

		path = folderPath + "/config.properties";

		File configFolder = new File(folderPath), config = new File(path);

		if (configFolder.exists())
			return !config.exists();
		else
			return true;
	}

}
