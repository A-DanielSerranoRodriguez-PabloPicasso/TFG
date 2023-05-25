package models;

import java.io.File;
import java.util.List;

public class Video {

	public void setLibrary(Library library) {
		this.library = library;
	}

	private int id;
	private String name, fileName, strLibrary, url;
	private Library library;
	private File image, video;
	private List<Category> categories;
	private boolean downloaded;
	private long dateCreated;

	public Video(int id, String name, String fileName, Library library, String url, boolean downloaded,
			long dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.library = library;
		this.downloaded = downloaded;
		video = new File(library.getPath() + "/" + fileName);
		this.dateCreated = dateCreated;
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStrLibrary() {
		return strLibrary;
	}

	public Library getLibrary() {
		return library;
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

	public long getDateCreated() {
		return dateCreated;
	}

	public void setName(String name) {
		// TODO ver que es nulo
		video.renameTo(new File(library.getPath() + "/" + name));
		this.name = name;
	}

}
