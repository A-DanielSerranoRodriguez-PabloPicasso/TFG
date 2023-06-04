package grabberApp.javafx.fxmls.popups.download;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import models.AbstractPopupController;
import utils.UtilsPopup;

public class ControllerDownloadProgress extends AbstractPopupController {

	private boolean downloaded;

	@FXML
	private ProgressBar pbProgressBar;

	@FXML
	private Label lblProgressText;

	@FXML
	private Button btnAccept;

	public ControllerDownloadProgress() {
		popup = UtilsPopup.popup;
		downloaded = false;

		UtilsPopup.grabber.setController(this);
	}

	@FXML
	private void initialize() {
		lblProgressText.setText("Esperando comienzo");
		pbProgressBar.setProgress(0);
		btnAccept.setText("Comenzar");
	}

	@FXML
	private void handleAccept() {
		if (downloaded)
			popup.getStage().close();
		else {
			btnAccept.setDisable(true);
			btnAccept.setText("Aceptar");
			UtilsPopup.grabber.start();
		}
	}

	public void update(String text, double percentaje) {
		lblProgressText.setText(text);
		pbProgressBar.setProgress(percentaje);

		if (percentaje == 100) {
			btnAccept.setDisable(false);
			downloaded = true;
		}
	}
}
