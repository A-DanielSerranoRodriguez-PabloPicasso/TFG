package utils;

import grabberApp.javafx.fxmls.popups.Popup;

public class UtilsPopup {
	public enum POPUP_PAGE {
		NONE, LIBRARY, 	DOWNLOAD
	}

	public static Popup popup;
	
	public static POPUP_PAGE page;
	
	public static String downloadedVideoPath;
}
