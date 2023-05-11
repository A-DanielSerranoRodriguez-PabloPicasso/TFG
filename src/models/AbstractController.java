package models;

import dao.GLibrary;
import dao.GLibraryImp;
import grabberApp.GrabberApp;

public abstract class AbstractController {
	public GrabberApp gApp;

	public GLibrary<Library> gLibrary;

	public void setGrabberApp(GrabberApp gApp) {
		this.gApp = gApp;
	}

	public GLibrary<Library> getLibrary() {
		return GLibraryImp.gestor();
	}

}