package models;

import dao.GLibrary;
import dao.GLibraryImp;
import grabberApp.javafx.fxmls.popups.Popup;

/**
 * Abstract ontroller for the pop-ups. Extends from a general
 * AbstractController.
 * 
 * @author Daniel Serrano Rodriguez
 */
public abstract class AbstractPopupController extends AbstractController {
	public Popup popup;

	public GLibrary gLibrary;

	/**
	 * Sets the pop-up it should interact with
	 * 
	 * @param popup Popup
	 */
	public void setPopup(Popup popup) {
		this.popup = popup;
	}

	/**
	 * Returns a GLibrary
	 */
	public GLibrary getGLibrary() {
		return GLibraryImp.getGestor();
	}

	/**
	 * Returns a Popup
	 * 
	 * @return Popup
	 */
	public Popup getPopup() {
		return popup;
	}

}