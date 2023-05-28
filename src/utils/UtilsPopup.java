package utils;

import grabberApp.javafx.fxmls.popups.Popup;
import models.Library;
import models.Video;

public class UtilsPopup {
	public enum POPUP_PAGE {
		NONE, SETUP, LIBRARY, DOWNLOAD, SELECT_LIBRARY, ERR_VLC
	}
	
	public static boolean closed;

	public static Popup popup;

	public static POPUP_PAGE page;

	public static String downloadedVideoPath, librarySearched;

	public static Video videoToPlay;

	public static Library selectedLibrary, previousLibrary;
}
