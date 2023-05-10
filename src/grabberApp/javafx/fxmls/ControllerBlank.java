package grabberApp.javafx.fxmls;

import java.io.File;

import models.AbstractController;
import utils.Routes;
import utils.Utils;

public class ControllerBlank extends AbstractController {

	public ControllerBlank() {
		gApp = Utils.gApp;
	}

	public void initialize() {
		gApp.getStage().setOnShown(arg0 -> {
			File configFile = new File(Utils.getPath());
			gApp.viewSetCenter(Routes.getRoute("fl-preparing"));
			while (!configFile.exists()) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			gApp.viewSetCenter(Routes.getRoute("fl-prepared"));
		});
	}
}
