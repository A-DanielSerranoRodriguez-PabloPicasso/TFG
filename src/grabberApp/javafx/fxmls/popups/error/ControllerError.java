package grabberApp.javafx.fxmls.popups.error;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.AbstractPopupController;
import models.Library;
import models.Video;
import models.javafx.CustomMenuItem;
import utils.FileUtils;
import utils.Grabber;
import utils.ImgUtils;
import utils.Utils;
import utils.UtilsPopup;

/**
 * Controller of the error pop-up
 * 
 * @author Daniel Serrano Rodriguez
 */
public class ControllerError extends AbstractPopupController {

	private boolean isDownload, isCreateLibrary;

	private Video video;
	private Library library;

	@FXML
	private Label lblError;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnAccept;

	@FXML
	private ImageView imgWarning;

	/**
	 * Constructor
	 */
	public ControllerError() {
		popup = UtilsPopup.popup;
		gApp = Utils.gApp;
		isDownload = false;
		isCreateLibrary = false;
	}

	/**
	 * Initializer
	 */
	public void initialize() {
		imgWarning.setImage(ImgUtils.getInternalImage("/img/icon/warning.png"));

		/*
		 * Depending of the error type, it shows one error or another
		 */
		switch (UtilsPopup.errType) {
		case VLC:
			lblError.setText("Es necesario instalar VLC para reproducir videos");
			break;

		case LIBRARY_EXISTS:
			lblError.setText("La biblioteca ya existe");
			break;

		case VIDEO_EXISTS:
			lblError.setText("El video ya existe");
			break;

		case LIBRARY_NAME_EMPTY:
			lblError.setText("Introduce un nombre para la biblioteca");
			break;

		case VIDEO_NAME_EMPTY:
			lblError.setText("Introduce un nombre para el video");
			break;

		case VIDEO_URL_EMPTY:
			lblError.setText("Introduce una URL");
			break;

		case VIDEO_URL_INVALID:
			lblError.setText("Introduce una URL válida");
			break;

		case VIDEO_LIBRARY_EMPTY:
			lblError.setText("Selecciona una biblioteca para el video");
			break;

		case VIDEO_SAME_NAME:
			lblError.setText("Ya existe un video con ese nombre en la biblioteca");
			break;

		// If the video isn't downloaded, it lets you do so
		case VIDEO_NOT_FOUND:
			lblError.setText("Video no encontrado en el equipo.\n¿Desde descargarlo?");
			isDownload = true;
			video = UtilsPopup.video[0];
			btnCancel.setVisible(true);
			break;

		// If the library is missing, the user can create it again
		case LIBRARY_NOT_FOUND:
			lblError.setText("Biblioteca no disponible.\n¿Desdea volver a crearla?");
			isCreateLibrary = true;
			library = UtilsPopup.selectedLibrary;
			btnCancel.setVisible(true);
			btnCancel.setText("No");
			btnAccept.setText("Sí");
			break;

		default:
			break;
		}
	}

	@FXML
	private void handleAccept() {
		/*
		 * If the video is missing, and the user wants, the video gets downloaded
		 */
		if (isDownload) {
			MenuButton mbDownloads = Utils.mbDownloads;
			HBox hBox = new HBox();
			Button btnRemove = new Button("X"), btnVer = new Button("Ver");
			CustomMenuItem cmi;

			hBox.getChildren().add(new Label());
			hBox.getChildren().add(new VBox());

			cmi = new CustomMenuItem(hBox);
			Grabber grabber = new Grabber(video.getUrl(), video.getLibrary().getPath(), video.getName(), cmi, false);

			mbDownloads.getItems().add(0, cmi);

			mbDownloads.setText(Integer.toString(Integer.parseInt(mbDownloads.getText()) + 1));
			hBox.getChildren().add(btnVer);
			hBox.getChildren().add(btnRemove);

			btnRemove.setOnAction(event -> {
				mbDownloads.getItems().remove(cmi);
				mbDownloads.setText(Integer.toString(Integer.parseInt(mbDownloads.getText()) - 1));
			});
			grabber.run();
			
			/*
			 * If the library is missing, and the user wants, the folder gets created again
			 */
		} else if (isCreateLibrary)
			FileUtils.createFolder(library.getPath());

		popup.close();
	}

	/**
	 * Closes the pop-up
	 */
	@FXML
	private void handleCancel() {
		UtilsPopup.video = null;
		UtilsPopup.selectedLibrary = null;
		isDownload = false;
		isCreateLibrary = false;
		handleAccept();
	}
}
