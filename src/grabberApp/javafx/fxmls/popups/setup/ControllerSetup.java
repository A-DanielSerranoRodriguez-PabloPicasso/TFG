package grabberApp.javafx.fxmls.popups.setup;

import java.io.File;

import dao.GOrigin;
import dao.GOriginImp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import models.AbstractPopupController;
import utils.Routes;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerSetup extends AbstractPopupController {

	private File folder;

	private GOrigin gOrigin;

	@FXML
	private TextField txfRoute;

	@FXML
	private Button btnSelectRoute;

	@FXML
	private Button btnAccept;

	public ControllerSetup() {
		popup = UtilsPopup.popup;
		gApp = Utils.gApp;
		gOrigin = GOriginImp.gestor();
	}

	@FXML
	private void initialize() {
		txfRoute.setDisable(true);

		btnSelectRoute.setOnAction(event -> {
			folder = new DirectoryChooser().showDialog(popup.getStage());
			if (folder != null)
				txfRoute.setText(folder.getAbsolutePath());
		});

		btnAccept.setOnAction(event -> {
			String route = txfRoute.getText();
			if (!route.isEmpty()) {
				gOrigin.insert(route);
				Utils.origin = route;
				popup.getStage().close();
				Utils.gApp.viewSetCenter(Routes.getRoute("landpage"));
			}
		});

		popup.getStage().setOnCloseRequest(event -> {
			UtilsPopup.closed = true;
		});
	}

}
