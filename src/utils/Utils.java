package utils;

import java.io.File;

import dao.SQLiteDAO;
import grabberApp.GrabberApp;

public class Utils {

	public static GrabberApp gApp;
	
	public static SQLiteDAO sqlDao;

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

	// TODO configurar base de datos
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

//	public static void viewBorderPaneSetCenter(BorderPane pane, String view) {
//		AnchorPane centerPane;
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(GrabberApp.class.getResource(view));
//
//			centerPane = (AnchorPane) loader.load();
////			AbstractController controller = loader.getController();
//
////			if (controller != null)
////				controller.setGrabberApp(this);
//
//			pane.setCenter(centerPane);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
