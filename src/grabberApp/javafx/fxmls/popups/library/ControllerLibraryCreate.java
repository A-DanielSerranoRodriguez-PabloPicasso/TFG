package grabberApp.javafx.fxmls.popups.library;

import dao.GLibrary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.AbstractPopupController;
import models.Library;
import utils.FileUtils;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerLibraryCreate extends AbstractPopupController {

	@FXML
	private TextField txfNombre;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnAceptar;

	public ControllerLibraryCreate() {
		popup = UtilsPopup.popup;
	}

	public void initialize() {
		btnCancelar.setOnMouseClicked(event -> {
			popup.getStage().close();
		});

		btnAceptar.setOnMouseClicked(event -> {
			Library lib = UtilsPopup.selectedLibrary;
			String folderRoute = lib != null ? lib.getPath() : Utils.origin, folderName = txfNombre.getText();
			GLibrary<Library> gLibrary = getGLibrary();
			gLibrary.insert(new Library(folderRoute + "/" + folderName, folderName, lib != null ? lib.getPath() : null,
					Utils.origin));
			Utils.libraries = gLibrary.getAll();
			FileUtils.createFolder(folderRoute + "/" + folderName);
			popup.getStage().close();
		});
	}
}
