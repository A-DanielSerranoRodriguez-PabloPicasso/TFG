package grabberApp.javafx.fxmls.popups.library;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import models.AbstractPopupController;
import utils.UtilsPopup;

public class ControllerLibraryCreate extends AbstractPopupController {

	private File folder;

//	private String folderName;

	@FXML
	private TextField txfNombre;

	@FXML
	private TextField txfRuta;

	@FXML
	private Button btnElegirCarpeta;

	public ControllerLibraryCreate() {

	}

	public void initialize() {
		btnElegirCarpeta.setOnMouseClicked(event -> {
			folder = new DirectoryChooser().showDialog(UtilsPopup.popup.getStage());
			txfRuta.setText(folder.getAbsolutePath());
		});
	}
}
