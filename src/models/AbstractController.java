package models;

import dao.GLibrary;
import dao.GLibraryImp;
import dao.GVideo;
import dao.GVideoImp;
import grabberApp.GrabberApp;

public abstract class AbstractController {
	public GrabberApp gApp;

	public GLibrary<Library> gLibrary;
	
	public GVideo<Video> gVideo;

	public void setGrabberApp(GrabberApp gApp) {
		this.gApp = gApp;
	}

	public GLibrary<Library> getGLibrary() {
		return GLibraryImp.gestor();
	}
	
	public GVideo<Video> getGVideo(){
		return GVideoImp.getGestor();
	}
	
	public void reload() {
	}

}