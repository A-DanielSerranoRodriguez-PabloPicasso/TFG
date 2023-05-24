package grabberApp.javafx.fxmls.popups.library;

import java.io.File;

import dao.GLibrary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import models.AbstractPopupController;
import models.Library;
import utils.FileUtils;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerLibraryCreate extends AbstractPopupController {

	private File folder;

	@FXML
	private TextField txfNombre;

	@FXML
	private TextField txfRuta;

	@FXML
	private Button btnElegirCarpeta;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnAceptar;

	public ControllerLibraryCreate() {
		popup = UtilsPopup.popup;
	}

	public void initialize() {
		btnElegirCarpeta.setOnMouseClicked(event -> {
			folder = new DirectoryChooser().showDialog(UtilsPopup.popup.getStage());
			txfRuta.setText(folder.getAbsolutePath());
		});

		btnCancelar.setOnMouseClicked(event -> {
			popup.getStage().close();
		});

		btnAceptar.setOnMouseClicked(event -> {
			String folderRoute = folder.getAbsolutePath(), folderName = txfNombre.getText();
			GLibrary<Library> gLibrary = getGLibrary();
			gLibrary.insert(new Library(folderRoute + "/" + folderName, folderName));
			Utils.libraries = gLibrary.getAll();
			FileUtils.createFolder(folderRoute + "/" + folderName);
			popup.getStage().close();
		});
	}
}
