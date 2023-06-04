package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.scene.image.Image;

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

	public static boolean renameImage(String imagePath, String newImage) {
		File currentImage = new File(imagePath);

		return currentImage.renameTo(new File(newImage));
	}

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
}
