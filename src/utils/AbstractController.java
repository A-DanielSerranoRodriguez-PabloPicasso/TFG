package utils;

import grabberApp.GrabberApp;

public abstract class AbstractController {
	public GrabberApp gApp;

	public void setGrabberApp(GrabberApp gApp) {
		this.gApp = gApp;
	}

	public GrabberApp getGrabberApp() {
		System.out.println("Abstract: " + gApp != null);
		return gApp;
	}
}
