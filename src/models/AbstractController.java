package models;

import grabberApp.GrabberApp;
import utils.BootPopUp;

public abstract class AbstractController {
	public GrabberApp gApp;

	public BootPopUp bootPopUp;

	public void setGrabberApp(GrabberApp gApp) {
		this.gApp = gApp;
	}

	public void setBootPopUp(BootPopUp bootPopUp) {
		this.bootPopUp = bootPopUp;
	}
}
