package models;

import dao.GLibrary;
import grabberApp.GrabberApp;

public abstract class AbstractController {
	public GrabberApp gApp;

	public GLibrary<Library> gLibrary;

	public void setGrabberApp(GrabberApp gApp) {
		this.gApp = gApp;
	}

	public GLibrary<Library> getLibrary() {
		return gLibrary;
	}

}