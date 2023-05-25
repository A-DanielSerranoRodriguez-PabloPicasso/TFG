package grabberApp.javafx.fxmls.popups.error;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.AbstractPopupController;
import utils.UtilsPopup;

public class ControllerNoVLC extends AbstractPopupController {

	@FXML
	private Label lblError;

	@FXML
	private Button btnAccept;

	@FXML
	private ImageView imgWarning;

	public ControllerNoVLC() {
		popup = UtilsPopup.popup;
	}

	public void initialize() {
		FileInputStream fis = null;
		File file = new File(gApp.getClass().getResource("/img/icon/warning.png").getPath());

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		imgWarning.setImage(new Image(fis));
	}

	@FXML
	private void handleAccept() {
		popup.getStage().close();
	}
}
