package models;

import java.io.File;
import java.util.List;

public class Video {

	private Library library;
	private String name, fileName;
	private File image, video;
	private List<Category> categories;
	private boolean downloaded;

	public Video(Library library, String name, String fileName, File image, File video, List<Category> categories,
			boolean downloaded) {
		this.library = library;
		this.name = name;
		this.fileName = name;
		this.image = image;
		this.video = video;
		this.categories = categories;
		this.downloaded = downloaded;
	}

	public Library getLibrary() {
		return library;
	}

	public String getName() {
		return name;
	}

	public String getFileName() {
		return fileName;
	}

	public File getImage() {
		return image;
	}

	public File getVideo() {
		return video;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public boolean getDownloaded() {
		return downloaded;
	}

}
