package grabberApp.javafx.fxmls.firstBoot;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import dao.GOriginImp;
import models.AbstractController;
import utils.Utils;

/**
 * Controller of the preparing view
 * 
 * @author Daniel Serrano Rodriguez
 */
public class ControllerPreparing extends AbstractController {

	/**
	 * Constructor
	 */
	public ControllerPreparing() {
		gApp = Utils.gApp;
	}

	/**
	 * Initializer.
	 * 
	 * If we don't find the file, we copy it.
	 */
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
			GOriginImp.gestor().nuke();
		}
	}

}
