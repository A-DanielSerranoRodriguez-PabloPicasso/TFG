package models;

import grabberApp.GrabberApp;

public abstract class AbstractController {
	public GrabberApp gApp;

	public void setGrabberApp(GrabberApp gApp) {
		this.gApp = gApp;
	}

	public GrabberApp getGrabberApp() {
		return gApp;
	}
}
