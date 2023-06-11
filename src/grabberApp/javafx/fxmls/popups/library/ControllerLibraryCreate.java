package grabberApp.javafx.fxmls.popups.library;

import java.io.File;

import dao.GLibrary;
import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.AbstractPopupController;
import models.Library;
import utils.FileUtils;
import utils.Utils;
import utils.UtilsPopup;

/**
 * Controller of the library creator
 * 
 * @author Daniel Serrano Rodriguez
 */
public class ControllerLibraryCreate extends AbstractPopupController {

	@FXML
	private TextField txfNombre;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnAceptar;

	/**
	 * Constructor
	 */
	public ControllerLibraryCreate() {
		popup = UtilsPopup.popup;
		gApp = Utils.gApp;
	}

	/**
	 * Initializer
	 */
	public void initialize() {
		btnCancelar.setOnMouseClicked(event -> {
			popup.getStage().close();
		});

		/*
		 * If the folder name is empty, or the library exists, it shows an error.
		 * 
		 * Else, it creates the library and the folder for the miniatures
		 */
		btnAceptar.setOnMouseClicked(event -> {
			Library lib = UtilsPopup.selectedLibrary;
			String folderRoute = lib != null ? lib.getPath() : Utils.origin, folderName = txfNombre.getText();
			if (folderName.isEmpty()) {
				UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
				UtilsPopup.errType = UtilsPopup.ERR_TYPE.LIBRARY_NAME_EMPTY;

				try {
					new Popup().start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (new File(folderRoute + System.getProperty("file.separator") + folderName).exists()) {
				UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
				UtilsPopup.errType = UtilsPopup.ERR_TYPE.LIBRARY_EXISTS;

				try {
					new Popup().start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				int idParent = 0;
				String pathParent;
				if (lib == null) {
					pathParent = null;
				} else {
					idParent = lib.getId();
					pathParent = lib.getPath();
				}

				GLibrary gLibrary = getGLibrary();
				Library library = new Library(folderRoute + "/" + folderName, folderName, pathParent, idParent,
						Utils.origin);
				gLibrary.insert(library);
				library = gLibrary.getByPath(library.getPath());
				FileUtils.createFolder(Utils.folderPath + "/" + library.getId());
				FileUtils.createFolder(folderRoute + "/" + folderName);
				popup.getStage().close();
			}
		});
	}
}
