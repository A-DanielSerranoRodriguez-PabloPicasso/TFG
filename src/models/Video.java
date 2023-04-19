package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Video {

	private Library library;
	private String name, fileName;
	private File image, video;
	private List<String> categories;
	private boolean downloaded;

	public Video(Library library, String name, String fileName, File image, File video, boolean downloaded) {
		this.library = library;
		this.name = name;
		this.fileName = name;
		this.image = image;
		this.video = video;
		this.downloaded = downloaded;
		categories = new ArrayList<>();
		categories.add(library.getCategory());
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

	public List<String> getCategories() {
		return categories;
	}

	public boolean getDownloaded() {
		return downloaded;
	}

}
