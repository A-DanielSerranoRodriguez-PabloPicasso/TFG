package grabberApp.javafx.fxmls.popups.error;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.AbstractPopupController;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerError extends AbstractPopupController {

	@FXML
	private Label lblError;

	@FXML
	private Button btnAccept;

	@FXML
	private ImageView imgWarning;

	public ControllerError() {
		popup = UtilsPopup.popup;
		gApp = Utils.gApp;
	}

	public void initialize() {
		FileInputStream fis = null;
		File file = new File(gApp.getClass().getResource("/img/icon/warning.png").getPath());

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		imgWarning.setImage(new Image(fis));

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

		default:
			break;
		}
	}

	@FXML
	private void handleAccept() {
		popup.getStage().close();
	}
}
