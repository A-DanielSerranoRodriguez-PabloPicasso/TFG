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
	private String userName, folderPath, dbFilePath, origin;

	public UtilsFirstBoot() {
		userName = System.getProperty("user.name");

		if (isOsWindows())
			folderPath = "C:/Users/" + userName + "/AppData/Local/JGrabber";
		else
			folderPath = "/home/" + userName + "/.config/jgrabber";
	}

	public String getDbFolderPath() {
		return folderPath;
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
	public boolean isOsLinux() {
		return !isOsWindows();
	}

	/**
	 * Comprueba que la base de datos existe y que el usuario ha introducido un
	 * origen para las bibliotecas
	 * 
	 * @return true si no se ha configurado el origen, false si el origen existe
	 */
	public boolean firstStart() {
		Utils.folderPath = folderPath;

		dbFilePath = folderPath + "/db.db";

		File dbFolder = new File(folderPath), dbFile = new File(dbFilePath);

		if (dbFile.exists()) {
			Utils.dbFilePath = dbFilePath;
			GOrigin gOrigin = GOriginImp.gestor();
			origin = gOrigin.getOrigin();
			return origin == null;
		}

		return dbFolder.exists() ? !dbFile.exists() : true;
	}

}
