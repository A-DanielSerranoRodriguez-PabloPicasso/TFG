package utils;

import grabberApp.javafx.fxmls.popups.Popup;
import models.Library;
import models.Video;

public class UtilsPopup {
	public enum POPUP_PAGE {
		NONE, SETUP, LIBRARY, DOWNLOAD, SELECT_LIBRARY, ERR
	}

	public enum ERR_TYPE {
		VLC, LIBRARY_EXISTS, VIDEO_EXISTS, LIBRARY_NAME_EMPTY, VIDEO_NAME_EMPTY, VIDEO_URL_EMPTY, VIDEO_URL_INVALID,
		VIDEO_LIBRARY_EMPTY, VIDEO_SAME_NAME
	}

	public static boolean closed;

	public static Popup popup;

	public static POPUP_PAGE page;

	public static ERR_TYPE errType;

	public static String downloadedVideoPath, librarySearched;

	public static Video videoToPlay;

	public static Library selectedLibrary, previousLibrary;
}
