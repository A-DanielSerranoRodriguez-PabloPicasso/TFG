package grabberApp.javafx.fxmls.firstBoot;

import javafx.fxml.FXML;
import models.AbstractController;
import utils.Routes;
import utils.Utils;

public class ControllerPrimed extends AbstractController {

	public ControllerPrimed() {
		gApp = Utils.gApp;
	}

	public void initialize() {
	}

	@FXML
	private void handleOk() {
		gApp.viewSetCenter(Routes.getRoute("landpage"));
	}

}
