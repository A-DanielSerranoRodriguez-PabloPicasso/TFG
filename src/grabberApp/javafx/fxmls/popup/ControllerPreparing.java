package grabberApp.javafx.fxmls.popup;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import models.AbstractController;
import utils.Utils;

public class ControllerPreparing extends AbstractController {

	public ControllerPreparing() {
		bootPopUp = Utils.bootPopUp;
	}

	public void initialize() {
		bootPopUp.getStage().setOnShown(event -> {
			System.out.println("blyat");
			File configFolder = new File(Utils.getFolderPath()), configFile = new File(Utils.getPath());

			while (!configFile.exists()) {
				if (!configFolder.exists())
					configFolder.mkdirs();

				try {
					Files.copy(new File(bootPopUp.getClass().getResource("/boot.properties").getFile()), configFile);
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			bootPopUp.viewSetCenter("/grabberApp/javafx/fxmls/popup/Primed.fxml");
		});
	}

}
