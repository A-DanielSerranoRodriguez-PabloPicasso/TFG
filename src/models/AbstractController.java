package models;

import dao.GLibrary;
import dao.GLibraryImp;
import dao.GVideo;
import dao.GVideoImp;
import grabberApp.GrabberApp;

public abstract class AbstractController {
	public GrabberApp gApp;

	public GLibrary gLibrary;

	public GVideo gVideo;

	public void setGrabberApp(GrabberApp gApp) {
		this.gApp = gApp;
	}

	public GLibrary getGLibrary() {
		return GLibraryImp.getGestor();
	}

	public GVideo getGVideo() {
		return GVideoImp.getGestor();
	}

	public void reload() {
	}

}