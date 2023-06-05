package grabberApp.javafx.fxmls.popups.download;

import javafx.fxml.FXML;
import models.AbstractPopupController;
import utils.UtilsPopup;

public class ControllerDownloadProgress extends AbstractPopupController {

	public ControllerDownloadProgress() {
		popup = UtilsPopup.popup;
	}

	@FXML
	private void initialize() {
//		popup.getStage().setOnCloseRequest(event -> {
//			UtilsPopup.grabber.run();
//		});
//		popup.getStage().close();
	}
}
