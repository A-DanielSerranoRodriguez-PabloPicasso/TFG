package grabberApp.javafx.fxmls.popup;

import models.AbstractController;
import utils.Utils;

public class ControllerPrimed extends AbstractController {

	public ControllerPrimed() {
		gApp = Utils.gApp;
		bootPopUp = Utils.bootPopUp;
	}

	public void initialize() {
		bootPopUp.getStage().setOnShown(event -> {
			gApp.viewSetCenter("/grabberApp/javafx/fxmls/Basic.fxml");
		});
//		bootPopUp.getStage().close();
	}

}
