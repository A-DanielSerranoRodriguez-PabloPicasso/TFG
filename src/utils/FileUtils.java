package utils;

import java.io.File;

import dao.GLibrary;
import dao.GLibraryImp;
import dao.GVideoImp;
import models.Library;

/**
 * Util class relate to files in general
 * 
 * @author Daniel Serrano Rodriguez
 */
public class FileUtils {

	/**
	 * Creates a folder
	 * 
	 * @param route String
	 * @return true/false
	 */
	public static boolean createFolder(String route) {
		return new File(route).mkdirs();
	}

	/**
	 * Deletes a folder, including the files and folders that are inside of it
	 * 
	 * @param route        String
	 * @param syncDatabase true/false. If true, database queries are sent to delete
	 *                     videos and libraries.
	 */
	public static void deleteFolder(String route, boolean syncDatabase) {
		File folder = new File(route);
		String[] content = folder.list();
		Library library = null;
		GLibrary gLibrary;

		if (syncDatabase) {
			gLibrary = GLibraryImp.getGestor();
			library = gLibrary.getByPath(route);
		}

		if (content != null && content.length > 0) {
			for (String fileString : content) {
				File file = new File(
						syncDatabase ? library.getPath() : route + System.getProperty("file.separator") + fileString);
				if (file.isDirectory()) {
					deleteFolder(file.getAbsolutePath(), syncDatabase);
				} else {
					if (syncDatabase)
						GVideoImp.getGestor().delete(GVideoImp.getGestor().getByLibrary(library.getId(), fileString));
				}

				file.delete();
			}
		}

		folder.delete();

		if (syncDatabase)
			GLibraryImp.getGestor().delete(library);
	}

}
