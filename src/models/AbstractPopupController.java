package models;

import dao.GLibrary;
import dao.GLibraryImp;
import grabberApp.javafx.fxmls.popups.Popup;

public abstract class AbstractPopupController extends AbstractController {
	public Popup popup;

	public GLibrary gLibrary;

	public void setPopup(Popup popup) {
		this.popup = popup;
	}

	public GLibrary getGLibrary() {
		return GLibraryImp.getGestor();
	}

	public Popup getPopup() {
		return popup;
	}

}