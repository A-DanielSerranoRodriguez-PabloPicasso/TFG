package utils;

import grabberApp.javafx.fxmls.popups.Popup;
import models.Library;
import models.Video;

/**
 * Static library to access utilities for the pop-up windows
 * 
 * @author Daniel Serrano Rodriguez
 */
public class UtilsPopup {
	/**
	 * Declares the views that the pop-up can have
	 * 
	 * @author Daniel Serrano Rodríguez
	 */
	public enum POPUP_PAGE {
		NONE, SETUP, LIBRARY, DOWNLOAD, DOWNLOAD_PROGRESS, SELECT_LIBRARY, ERR, WARN
	}

	/**
	 * Declares the ERR views that the pop-up can have
	 * 
	 * @author Daniel Serrano Rodriguez
	 */
	public enum ERR_TYPE {
		VLC, LIBRARY_EXISTS, VIDEO_EXISTS, LIBRARY_NAME_EMPTY, VIDEO_NAME_EMPTY, VIDEO_URL_EMPTY, VIDEO_URL_INVALID,
		VIDEO_LIBRARY_EMPTY, VIDEO_SAME_NAME, VIDEO_NOT_FOUND
	}

	/**
	 * Declares the WARN views that the pop-up can have
	 * 
	 * @author Daniel Serrano Rodriguez
	 */
	public enum WARN_TYPE {
		VIDEO_EXISTS
	}

	/**
	 * Access to the pop-up window
	 */
	public static Popup popup;

	/**
	 * Page that the pop-up access
	 */
	public static POPUP_PAGE page;

	/**
	 * Page that the pop-up access when navigating to an error
	 */
	public static ERR_TYPE errType;

	/**
	 * Page that the pop-up access when navigating to a warn
	 */
	public static WARN_TYPE warnType;

	/**
	 * Library used for selection of libraries in modification or download of videos
	 */
	public static Library selectedLibrary;

	/**
	 * Access to downloder class when the video exists in the database
	 */
	public static Grabber grabber;

	/**
	 * Utility video array
	 */
	public static Video[] video;
}
