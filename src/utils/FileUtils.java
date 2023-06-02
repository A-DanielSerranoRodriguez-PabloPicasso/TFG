package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import dao.GLibrary;
import dao.GLibraryImp;
import dao.GVideoImp;
import models.Library;

public class FileUtils {

	public static boolean createFolder(String route) {
		return new File(route).mkdirs();
	}

	public static void deleteFolder(String route, boolean syncDatabase) {
		File folder = new File(route);
		String[] content = folder.list();
		Library library = null;
		GLibrary<Library> gLibrary;

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

	public static boolean downloadImage(String urlString, Library library, String videoName) {
		boolean ok = false;
		URL url = null;
		InputStream is = null;
		OutputStream fos = null;
		String downloadFileLocation = Utils.folderPath + System.getProperty("file.separator") + library.getId()
				+ System.getProperty("file.separator") + videoName + ".jpeg";

		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// Read image from specified URL using InputStream
		try {
			is = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File downloadedFile = new File(downloadFileLocation);
		
		try {
			downloadedFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Write image to file using FileOutputStream
		try {
			fos = new FileOutputStream(downloadedFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int ch;
		try {
			while ((ch = is.read()) != -1) { // read till end of file
				fos.write(ch);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			is.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		BufferedImage image = null;
//		try {
//
//			// read the url
//			image = ImageIO.read(url);
//
//			if (image != null) {
////            for png
//				ImageIO.write(image, "png", new File("/tmp/have_a_question.png"));
//
//				// for jpg
//				ImageIO.write(image, "jpg", new File("/tmp/have_a_question.jpg"));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return ok;
	}
}
