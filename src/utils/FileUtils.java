package utils;

import java.io.File;

public class FileUtils {

	public static boolean createFolder(String route) {
		return new File(route).mkdirs();
	}
}
