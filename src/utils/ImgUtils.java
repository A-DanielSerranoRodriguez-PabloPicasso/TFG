package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class ImgUtils {

	/**
	 * Returns an image given a path
	 * 
	 * @param path String
	 * @return Image
	 */
	public static Image getImage(String path) {
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

}
