package models;

import dao.GLibrary;
import dao.GLibraryImp;
import dao.GVideo;
import dao.GVideoImp;
import grabberApp.GrabberApp;

/**
 * Abstract controller for the controllers.
 * 
 * @author Daniel Serrano Rodriguez
 */
public abstract class AbstractController {
	public GrabberApp gApp;

	public GLibrary gLibrary;

	public GVideo gVideo;

	/**
	 * Sets the main app
	 * 
	 * @param gApp GrabberApp
	 */
	public void setGrabberApp(GrabberApp gApp) {
		this.gApp = gApp;
	}

	/**
	 * Returns a GLibrary
	 * 
	 * @return GLibrary
	 */
	public GLibrary getGLibrary() {
		return GLibraryImp.getGestor();
	}

	/**
	 * Returns a GVideo
	 * 
	 * @return GVideo
	 */
	public GVideo getGVideo() {
		return GVideoImp.getGestor();
	}

	/**
	 * Reloads the controller
	 */
	public void reload() {
	}

}