package grabberApp.javafx.fxmls.popups.download;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.AbstractPopupController;
import models.Library;
import utils.Grabber;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerDownload extends AbstractPopupController {

	private List<Library> libraries;

	@FXML
	private TextField txfVideoName;

	@FXML
	private TextField txfUrl;

	@FXML
	private ChoiceBox<String> choiceLibrary;

	public ControllerDownload() {
		gLibrary = getGLibrary();
		popup = UtilsPopup.popup;
		libraries = Utils.libraries;
	}

	public void initialize() {
		ObservableList<String> olLibraries = FXCollections.observableArrayList();

		for (Library library : libraries) {
			olLibraries.add(library.getNamePath());
		}

		choiceLibrary.setItems(olLibraries);
	}

	@FXML
	private void handleCancelar() {
		popup.getStage().close();
	}

	@FXML
	private void handleAceptar() {
		Library library = null;
		String namePath = choiceLibrary.getSelectionModel().getSelectedItem();

		for (int i = 0; i < libraries.size() && library == null; i++) {
			Library lib = libraries.get(i);

			library = namePath.equals(lib.getNamePath()) ? lib : null;
		}

		Grabber grabber = new Grabber(txfUrl.getText(), library.getPath(), 0);
		grabber.run(Utils.mbDownloads);

		handleCancelar();
	}

}
