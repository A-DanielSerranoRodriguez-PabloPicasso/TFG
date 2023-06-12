package models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import dao.GVideoImp;
import grabberApp.javafx.fxmls.popups.Popup;
import javafx.stage.Stage;
import utils.ImgUtils;
import utils.Utils;
import utils.UtilsFirstBoot;
import utils.UtilsPopup;

/**
 * Represents a video of the database
 * 
 * @author Daniel Serrano Rodriguez
 */
public class Video {

	private int id, idLibrary;
	private String name, fileName, url, miniaturePath;
	private Library library;
	private File video;
	private List<Category> categories;
	private boolean downloaded;
	private long dateCreated;

	/**
	 * Constructor of a video
	 * 
	 * @param id          int
	 * @param name        String
	 * @param fileName    String
	 * @param library     Library
	 * @param url         String
	 * @param downloaded  boolean
	 * @param dateCreated long
	 */
	public Video(int id, String name, String fileName, Library library, String url, boolean downloaded,
			long dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.library = library;
		this.downloaded = downloaded;
		// The video file is assigned dinamically
		video = new File(library.getPath() + "/" + fileName);
		this.dateCreated = dateCreated;
		this.url = url;
		// The video miniature is assigned dinamically
		miniaturePath = Utils.folderPath + System.getProperty("file.separator") + library.getId()
				+ System.getProperty("file.separator") + name + ".jpeg";
	}

	public Video(String name, Library library, String url) {
		super();
		this.name = name;
		fileName = name + ".mp4";
		this.library = library;
		video = new File(library.getPath() + "/" + fileName);
		this.url = url;
		miniaturePath = Utils.folderPath + System.getProperty("file.separator") + library.getId()
				+ System.getProperty("file.separator") + name + ".jpeg";
	}

	/**
	 * Gets the name of the file
	 * 
	 * @return String
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the name of the file
	 * 
	 * @param fileName String
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the URL of the video
	 * 
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Gets the path of the miniature
	 * 
	 * @return String
	 */
	public String getMiniaturePath() {
		return miniaturePath;
	}

	/**
	 * Gets the id of the video in the database
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the name of the video
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the library
	 * 
	 * @return Library
	 */
	public Library getLibrary() {
		return library;
	}

	/**
	 * Sets the library of the video
	 * 
	 * @param library Library
	 */
	public void setLibrary(Library library) {
		this.library = library;
	}

	/**
	 * Gets the video file
	 * 
	 * @return File
	 */
	public File getVideo() {
		return video;
	}

	/**
	 * Gets the id of the library
	 * 
	 * @return int
	 */
	public int getIdLibrary() {
		return idLibrary;
	}

	/**
	 * Gets the categories of the video
	 * 
	 * @return List<Category>
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * Gets if the video is downloaded or not
	 * 
	 * @return boolean
	 */
	public boolean getDownloaded() {
		return downloaded;
	}

	/**
	 * Gets the creation date of the video
	 * 
	 * @return long
	 */
	public long getDateCreated() {
		return dateCreated;
	}

	/**
	 * Gets the root of the miniature
	 * 
	 * @return String
	 */
	private String getMiniatureRoot() {
		return Utils.folderPath + System.getProperty("file.separator") + library.getId()
				+ System.getProperty("file.separator");
	}

	/**
	 * Sets the name of the video.
	 * 
	 * During the process, it changes the file name, renames the image and the video
	 * 
	 * @param name String
	 */
	public void setName(String name) {
		this.fileName = name + ".mp4";
		this.name = name;
		ImgUtils.renameImage(miniaturePath, getMiniatureRoot() + name + ".jpeg");
		miniaturePath = getMiniatureRoot() + name + ".jpeg";
		renameVideo();
		video = new File(library.getPath() + "/" + fileName);
	}

	/**
	 * Renames the video
	 */
	private void renameVideo() {
		video.renameTo(new File(library.getPath() + "/" + fileName));
	}

	/**
	 * Moves the video to a new library.
	 * 
	 * It also moves the image.
	 * 
	 * @param newLibrary Library
	 */
	public void moveVideo(Library newLibrary) {
		try {
			File newFile = new File(newLibrary.getPath() + "/" + getFileName());
			newFile.createNewFile();
			Files.move(Paths.get(library.getPath() + "/" + video.getName()), newFile.toPath(),
					StandardCopyOption.ATOMIC_MOVE);
			video = newFile;
			String newMiniaturePath = Utils.folderPath + System.getProperty("file.separator") + newLibrary.getId()
					+ System.getProperty("file.separator") + getName() + ".jpeg";

			ImgUtils.moveImage(miniaturePath, newMiniaturePath);
			miniaturePath = newMiniaturePath;
			library = newLibrary;
			GVideoImp.getGestor().update(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lets the user watch the video
	 */
	public void watch() {
		File libraryFile = new File(library.getPath());
		if (!libraryFile.exists()) {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
			UtilsPopup.errType = UtilsPopup.ERR_TYPE.LIBRARY_NOT_FOUND;
			UtilsPopup.selectedLibrary = library;

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (video.exists()) {
			Runtime runtime = Runtime.getRuntime();
			try {
				if (UtilsFirstBoot.isOsWindows()) {
					runtime.exec("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe " + video.getAbsolutePath());
				} else
					runtime.exec("vlc " + video.getAbsolutePath());
			} catch (IOException e) {
				UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
				UtilsPopup.errType = UtilsPopup.ERR_TYPE.VLC;
				try {
					new Popup().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
//				e.printStackTrace();
			}
		} else {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
			UtilsPopup.errType = UtilsPopup.ERR_TYPE.VIDEO_NOT_FOUND;
			UtilsPopup.video = new Video[] { this };

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
