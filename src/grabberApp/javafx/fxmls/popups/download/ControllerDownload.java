package grabberApp.javafx.fxmls.popups.download;

import java.io.File;
import java.io.IOException;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.AbstractPopupController;
import models.Library;
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
		gApp = Utils.gApp;
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
		if (txfUrl.getText().isEmpty()) {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
			UtilsPopup.errType = UtilsPopup.ERR_TYPE.VIDEO_URL_EMPTY;

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (!txfUrl.getText().matches(
				"^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$")) {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
			UtilsPopup.errType = UtilsPopup.ERR_TYPE.VIDEO_URL_INVALID;

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (txfVideoName.getText().isEmpty()) {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
			UtilsPopup.errType = UtilsPopup.ERR_TYPE.VIDEO_NAME_EMPTY;

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (UtilsPopup.selectedLibrary != null) {
			Library library = UtilsDownload.targetLibrary != null ? UtilsDownload.targetLibrary
					: UtilsPopup.selectedLibrary;
			File newFile = new File(
					library.getPath() + System.getProperty("file.separator") + txfVideoName.getText() + ".mp4");

			if (newFile.exists()) {
				UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
				UtilsPopup.errType = UtilsPopup.ERR_TYPE.VIDEO_SAME_NAME;

				try {
					new Popup().start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
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

				Grabber grabber = new Grabber(txfUrl.getText(), library.getPath(), gVideo, videoName, cmi);
				grabber.start();

				hBox.getChildren().add(btnVer);
				hBox.getChildren().add(btnRemove);

				btnRemove.setOnAction(event -> {
					mbDownloads.getItems().remove(cmi);
					mbDownloads.setText(Integer.toString(Integer.parseInt(mbDownloads.getText()) - 1));
				});

				btnVer.setOnAction(event -> {
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec("vlc " + cmi.getVideo().getVideo().getAbsolutePath());
					} catch (IOException e) {
						UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
						UtilsPopup.errType = UtilsPopup.ERR_TYPE.VLC;
						try {
							new Popup().start(new Stage());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				});

				Utils.controllerLandPage.reload();

				handleCancelar();
			}
		} else {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
			UtilsPopup.errType = UtilsPopup.ERR_TYPE.VIDEO_LIBRARY_EMPTY;

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
