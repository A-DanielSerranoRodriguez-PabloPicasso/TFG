package utils;

import grabberApp.javafx.fxmls.popups.Popup;
import models.Video;

public class UtilsPopup {
	public enum POPUP_PAGE {
		NONE, LIBRARY, DOWNLOAD, VIDEO, ERR_VLC
	}

	public static Popup popup;

	public static POPUP_PAGE page;

	public static String downloadedVideoPath;

	public static Video videoToPlay;
}
