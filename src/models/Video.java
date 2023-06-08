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

public class Video {

	public void setLibrary(Library library) {
		this.library = library;
	}

	private int id, idLibrary;
	private String name, fileName, url, miniaturePath;
	private Library library;
	private File video;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public String getMiniaturePath() {
		return miniaturePath;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Library getLibrary() {
		return library;
	}

	public File getVideo() {
		return video;
	}

	public int getIdLibrary() {
		return idLibrary;
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

	private String getMiniatureRoot() {
		return Utils.folderPath + System.getProperty("file.separator") + library.getId()
				+ System.getProperty("file.separator");
	}

	public void setName(String name) {
		this.fileName = name + ".mp4";
		this.name = name;
		ImgUtils.renameImage(miniaturePath, getMiniatureRoot() + name + ".jpeg");
		miniaturePath = getMiniatureRoot() + name + ".jpeg";
		renameVideo();
		video = new File(library.getPath() + "/" + fileName);
	}

	public void renameVideo() {
		video.renameTo(new File(library.getPath() + "/" + fileName));
	}

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

	public void watch() {
		if (video.exists()) {
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
