package grabberApp.javafx.fxmls.popups.download;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.AbstractPopupController;
import models.javafx.CustomMenuItem;
import models.javafx.LibrarySearcher;
import utils.Grabber;
import utils.Utils;
import utils.UtilsDownload;
import utils.UtilsPopup;

public class ControllerDownload extends AbstractPopupController {

	@FXML
	private VBox vbComponents;

	@FXML
	private TextField txfVideoName;

	@FXML
	private TextField txfUrl;

	public ControllerDownload() {
		gLibrary = getGLibrary();
		gVideo = getGVideo();
		popup = UtilsPopup.popup;
	}

	public void initialize() {
		if (UtilsDownload.targetLibrary == null)
			vbComponents.getChildren().add(vbComponents.getChildren().size() - 1, new LibrarySearcher(null));
		else
			UtilsPopup.selectedLibrary = UtilsDownload.targetLibrary;
	}

	@FXML
	private void handleCancelar() {
		popup.getStage().setOnCloseRequest(event -> {
			UtilsPopup.selectedLibrary = null;
			UtilsDownload.targetLibrary = null;
		});

		popup.getStage().close();
	}

	@FXML
	private void handleAceptar() {
		if (UtilsPopup.selectedLibrary != null) {
			MenuButton mbDownloads = Utils.mbDownloads;
			String videoName = txfVideoName.getText();
			HBox hBox = new HBox();
			Button btnRemove = new Button("X"), btnVer = new Button("Ver");
			CustomMenuItem cmi;

			hBox.getChildren().add(new Label());
			hBox.getChildren().add(new VBox());

			cmi = new CustomMenuItem(hBox);

			mbDownloads.getItems().add(0, cmi);

			mbDownloads.setText(Integer.toString(Integer.parseInt(mbDownloads.getText()) + 1));

			Grabber grabber = new Grabber(txfUrl.getText(),
					UtilsDownload.targetLibrary != null ? UtilsDownload.targetLibrary.getPath()
							: UtilsPopup.selectedLibrary.getPath(),
					gVideo, videoName, cmi);
			grabber.start();

			hBox.getChildren().add(btnVer);
			hBox.getChildren().add(btnRemove);

			btnRemove.setOnAction(event -> {
				mbDownloads.getItems().remove(cmi);
				mbDownloads.setText(Integer.toString(Integer.parseInt(mbDownloads.getText()) - 1));
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

}
