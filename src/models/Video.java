package models;

import java.io.File;
import java.util.List;

public class Video {

	private int id;
	private String name, fileName, path, strLibrary, url;
	private Library library;
	private File image, video;
	private List<Category> categories;
	private boolean downloaded;
	private long dateCreated;

	public Video(int id, String name, String fileName, String path, String strLibrary, boolean downloaded) {
		super();
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.path = path;
		this.strLibrary = strLibrary;
		this.downloaded = downloaded;
		video = new File(path);
		dateCreated = 0;
	}

	public Video(int id, String name, String fileName, String path, String strLibrary, boolean downloaded,
			long dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.path = path;
		this.strLibrary = strLibrary;
		this.downloaded = downloaded;
		this.dateCreated = dateCreated;
		video = new File(path);
	}

	public Video(int id, String name, String fileName, String path, String strLibrary, String url, boolean downloaded) {
		super();
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.path = path;
		this.strLibrary = strLibrary;
		this.downloaded = downloaded;
		video = new File(path);
		dateCreated = 0;
		this.url = url;
	}

	public Video(int id, String name, String fileName, String path, Library library, String url, boolean downloaded,
			long dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.path = path;
		this.library = library;
		this.downloaded = downloaded;
		video = new File(path);
		this.dateCreated = dateCreated;
		this.url = url;
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

	public String getFileName() {
		return fileName;
	}

	public String getPath() {
		return path;
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

}
