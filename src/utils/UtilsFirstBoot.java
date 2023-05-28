package utils;

import java.io.File;

import dao.GOrigin;
import dao.GOriginImp;

public class UtilsFirstBoot {
	private static UtilsFirstBoot util;

	private String userName, dbFolderPath, dbFilePath, origin;

	/**
	 * Constructor de la clase
	 */
	private UtilsFirstBoot() {
		userName = System.getProperty("user.name");
	}

	/**
	 * Devuelve una instancia estatica de la clase
	 * 
	 * @return UtilsFirstBoot
	 */
	public static UtilsFirstBoot getUtil() {
		if (util == null)
			util = new UtilsFirstBoot();
		return util;
	}

	public static void close() {
		util = null;
	}

	public String getDbFolderPath() {
		return dbFolderPath;
	}

	public String getDbFilePath() {
		return dbFilePath;
	}

	public String getOrigin() {
		return origin;
	}

	/**
	 * Comprueba que el systema operativo en el que estamos es Windows
	 * 
	 * @return true si estamos en Windows, false en caso de no
	 */
	public boolean isOsWindows() {
		return System.getProperty("os.name").contains("Windows");
	}

	/**
	 * Comprueba que el sistema operativo en el que estamos no es Windows
	 * 
	 * @return true si no estamos en Windows, falso en caso de si
	 */
	public boolean isOsUnix() {
		return !isOsWindows();
	}

	/**
	 * Comprueba que la base de datos existe y que el usuario ha introducido un
	 * origen para las bibliotecas
	 * 
	 * @return true si no se ha configurado el origen, false si el origen existe
	 */
	public boolean firstStart() {
		if (isOsWindows())
			dbFolderPath = "C:/Users/" + userName + "/AppData/Local/JGrabber";
		else
			dbFolderPath = "/home/" + userName + "/.config/jgrabber";

		dbFilePath = dbFolderPath + "/db.db";

		File dbFolder = new File(dbFolderPath), dbFile = new File(dbFilePath);

		if (dbFile.exists()) {
			Utils.dbFilePath = dbFilePath;
			GOrigin gOrigin = GOriginImp.gestor();
			origin = gOrigin.getOrigin();
			return origin == null;
		}

		return dbFolder.exists() ? !dbFile.exists() : true;
	}

}
