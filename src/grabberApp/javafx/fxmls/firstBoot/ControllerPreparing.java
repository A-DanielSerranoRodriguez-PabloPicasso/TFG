package grabberApp.javafx.fxmls.firstBoot;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import models.AbstractController;
import utils.Utils;

public class ControllerPreparing extends AbstractController {

	public ControllerPreparing() {
		gApp = Utils.gApp;
	}

	public void initialize() {
		File configFolder = new File(Utils.getFolderPath()), configFile = new File(Utils.getPath());

		if (!configFile.exists()) {
			if (!configFolder.exists())
				configFolder.mkdirs();

			try {
				Files.copy(new File(gApp.getClass().getResource("/boot.properties").getFile()), configFile);
				Files.copy(new File(gApp.getClass().getResource("/db/db.db").getFile()),
						new File(Utils.getFolderPath() + "/db.db"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
