package grabberApp.javafx.fxmls.firstBoot;

import javafx.fxml.FXML;
import models.AbstractController;
import utils.Routes;
import utils.Utils;

/**
 * Controller of the primed view
 * 
 * @author Daniel Serrano Rodriguez
 */
public class ControllerPrimed extends AbstractController {

	/**
	 * Constructor
	 */
	public ControllerPrimed() {
		gApp = Utils.gApp;
	}

	/**
	 * Initializer
	 */
	public void initialize() {
	}

	/**
	 * Changes the view to the landpage
	 */
	@FXML
	private void handleOk() {
		gApp.viewSetCenter(Routes.getRoute("landpage"));
	}

}
