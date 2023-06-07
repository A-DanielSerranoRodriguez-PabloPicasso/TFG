package utils;

import java.io.File;

import dao.GOrigin;
import dao.GOriginImp;

/**
 * Class designed to be used when the program starts to obtain necessary paths
 * and checks
 * 
 * @author Daniel Serrano Rodriguez
 */
public class UtilsFirstBoot {
	private String userName, folderPath, dbFilePath;

	public UtilsFirstBoot() {
		userName = System.getProperty("user.name");

		if (isOsWindows())
			folderPath = "C:/Users/" + userName + "/AppData/Local/JGrabber";
		else
			folderPath = "/home/" + userName + "/.config/jgrabber";
	}

	/**
	 * Checks the OS is Windows
	 * 
	 * @return true/false
	 */
	public static boolean isOsWindows() {
		return System.getProperty("os.name").contains("Windows");
	}

	/**
	 * Checks the OS is NOT Windows
	 * 
	 * @return true/false
	 */
	public static boolean isOsLinux() {
		return !isOsWindows();
	}

	/**
	 * Checks the DB file exists and if there has been an origin inserted
	 * 
	 * @return true/false
	 */
	public boolean firstStart() {
		Utils.folderPath = folderPath;

		dbFilePath = folderPath + "/db.db";

		File dbFolder = new File(folderPath), dbFile = new File(dbFilePath);

		/*
		 * If the DB file, returns if the user has created an origin folder for the
		 * application
		 */
		if (dbFile.exists()) {
			Utils.dbFilePath = dbFilePath;
			GOrigin gOrigin = GOriginImp.gestor();
			Utils.origin = gOrigin.getOrigin();
			/**
			 * If the origin folder is missing, it will count as the first time the user
			 * executes the application
			 */
			if (Utils.origin != null) {
				File file = new File(Utils.origin);
				return !file.exists();
			} else
				return true;
		}

		/*
		 * If the DB folder exists, returns if the DB file does. If it exists, that
		 * means the program has already been started once
		 */
		return dbFolder.exists() ? !dbFile.exists() : true;
	}

}
