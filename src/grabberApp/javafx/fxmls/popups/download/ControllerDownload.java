package grabberApp.javafx.fxmls.popups.download;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.AbstractPopupController;
import models.Library;
import models.javafx.CustomMenuItem;
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
		gVideo = getGVideo();
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
		popup.getStage().close();
		Library library = null;
		String namePath = choiceLibrary.getSelectionModel().getSelectedItem(), videoName = txfVideoName.getText();
		HBox hBox = new HBox();
		Button btnRemove = new Button("X"), btnVer = new Button("Ver");
		CustomMenuItem cmi;

		hBox.getChildren().add(new Text());
		hBox.getChildren().add(new VBox());

		cmi = new CustomMenuItem(hBox);

		for (int i = 0; i < libraries.size() && library == null; i++) {
			Library lib = libraries.get(i);

			library = namePath.equals(lib.getNamePath()) ? lib : null;
		}

		Utils.mbDownloads.getItems().add(0, cmi);

		Grabber grabber = new Grabber(txfUrl.getText(), library.getPath(), gVideo, videoName);
		grabber.run(cmi);

		hBox.getChildren().add(btnVer);
		hBox.getChildren().add(btnRemove);

		btnRemove.setOnAction(event -> {
			Utils.mbDownloads.getItems().remove(cmi);
		});

		btnVer.setOnAction(event -> {
//			UtilsPopup.page = UtilsPopup.POPUP_PAGE.VIDEO;
//			UtilsPopup.videoToPlay = cmi.getVideo();
//
//			try {
//				new Popup().start(new Stage());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

		});

		Utils.controllerLandPage.fillRecentVideos();

		handleCancelar();
	}

}
