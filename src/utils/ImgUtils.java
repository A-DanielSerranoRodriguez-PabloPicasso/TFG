package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.aspose.imaging.imageoptions.JpegOptions;

import javafx.scene.image.Image;
import models.Library;

public class ImgUtils {
	/**
	 * Returns an image from the project given a path
	 * 
	 * @param path String
	 * @return Image
	 */
	public static Image getInternalImage(String path) {
		Image image = null;
		File file = new File(Utils.gApp.getClass().getResource(path).getPath());
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		image = new Image(fis);

		return image;
	}

	/**
	 * Returns an image given a path
	 * 
	 * @param path String
	 * @return Image
	 */
	public static Image getImage(String path) {
		Image image = null;
		File file = new File(path);
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			image = new Image(fis);
		} catch (FileNotFoundException | NullPointerException e) {
//			e.printStackTrace();
			image = getInternalImage("/img/miniatures/no-image-available.jpg");
		}

		return image;
	}

	/**
	 * Renames a given image using paths
	 * 
	 * @param imagePath String with old image
	 * @param newImage  String with new image
	 * @return true/false
	 */
	public static boolean renameImage(String imagePath, String newImage) {
		File currentImage = new File(imagePath);

		return currentImage.renameTo(new File(newImage));
	}

	/**
	 * Moves a given image using paths
	 * 
	 * @param imagePath String with old image
	 * @param newImage  String with new image
	 * @return true/false
	 */
	public static boolean moveImage(String imagePath, String newImage) {
		boolean ok = false;

		File newFile = new File(newImage);
		try {
			newFile.createNewFile();
			Files.move(Paths.get(imagePath), newFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
			ok = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ok;
	}

	/**
	 * Downloads an image to the correct library with the name of the video
	 * 
	 * @param urlString String
	 * @param library   Library
	 * @param videoName String
	 * @return true/false
	 */
	public static boolean downloadImage(String urlString, Library library, String videoName) {
		boolean ok = false;
		URL url = null;
		InputStream is = null;
		OutputStream fos = null;
		String downloadFileLocation = Utils.folderPath + System.getProperty("file.separator") + library.getId()
				+ System.getProperty("file.separator") + videoName + ".webp";

		// We get the url
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

		// We create the file where the image is going to be downloaded
		File downloadedFile = new File(downloadFileLocation);

		try {
			downloadedFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		/*
		 * We write to the file the contents of the url
		 */
		try {
			fos = new FileOutputStream(downloadedFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int ch;
		try {
			while ((ch = is.read()) != -1) {
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

		convertWebpJpeg(downloadFileLocation, library, videoName);

		return ok;
	}

	/**
	 * We transform the image from webp to jpeg
	 * 
	 * @param path      String
	 * @param library   Library
	 * @param videoName String
	 */
	private static void convertWebpJpeg(String path, Library library, String videoName) {
		File file = new File(path);
		com.aspose.imaging.Image image = com.aspose.imaging.Image.load(path);
		image.save(Utils.folderPath + System.getProperty("file.separator") + library.getId()
				+ System.getProperty("file.separator") + videoName + ".jpeg", new JpegOptions());
		file.delete();
	}
}
