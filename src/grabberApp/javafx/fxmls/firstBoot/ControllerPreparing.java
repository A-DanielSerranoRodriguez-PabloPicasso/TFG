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
		File dbFolder = new File(Utils.folderPath), dbFile = new File(Utils.dbFilePath);

		if (!dbFile.exists()) {
			if (!dbFolder.exists())
				dbFolder.mkdirs();

			try {
				Files.copy(new File(gApp.getClass().getResource("/db/db.db").getFile()), dbFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
