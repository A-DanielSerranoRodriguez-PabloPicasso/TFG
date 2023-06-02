package utils;

import java.util.List;

import dao.SQLiteDAO;
import grabberApp.GrabberApp;
import javafx.scene.control.MenuButton;
import models.AbstractController;
import models.Library;

public class Utils {

	public static GrabberApp gApp;

	public static String dbFilePath, dbFolderPath, origin;

	public static MenuButton mbDownloads;

	public static SQLiteDAO sqlDao;

	public static Library selectedLibrary;

	public static List<Library> libraries;

	public static AbstractController controller;
}