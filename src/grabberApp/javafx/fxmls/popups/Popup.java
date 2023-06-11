package grabberApp.javafx.fxmls.popups;

import grabberApp.GrabberApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.AbstractPopupController;
import utils.Routes;
import utils.Utils;
import utils.UtilsDownload;
import utils.UtilsPopup;

/**
 * Creates a popup JavaFX application
 * 
 * @author Daniel Serrano Rodriguez
 */
public class Popup extends Application {
	private Stage primaryStage;
	private BorderPane rootPane;
	private AnchorPane centerPane;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starts the application.
	 * 
	 * We configure it assigning the GrabberApp as the main window.
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = arg0;

		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(Utils.gApp.getStage());
		UtilsPopup.popup = this;
//		primaryStage.setResizable(false);

		initLayout();
	}

	/**
	 * Initializes the layout.
	 */
	private void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(GrabberApp.class.getResource(Routes.getRoute("popup-root")));
			rootPane = (BorderPane) loader.load();

			AbstractPopupController controller = loader.getController();
			if (controller != null)
				controller.setPopup(this);

			primaryStage.setScene(new Scene(rootPane));

			/*
			 * Sets the page depending of the general page.
			 */
			switch (UtilsPopup.page) {
			case SETUP:
				primaryStage.setTitle("Crear origen");
				viewSetCenter(Routes.getRoute("popup-setup"));
				break;

			case LIBRARY:
				primaryStage.setTitle("Crear biblioteca");
				viewSetCenter(Routes.getRoute("popup-library-create"));
				break;

			case DOWNLOAD:
				primaryStage.setTitle("Descargar");
				viewSetCenter(Routes.getRoute("popup-download"));
				break;

			case ERR:
				errorSwitch();
				break;

			case SELECT_LIBRARY:
				primaryStage.setTitle("Seleccionar biblioteca");
				viewSetCenter(Routes.getRoute("popup-library-select"));
				break;

			case DOWNLOAD_PROGRESS:
				primaryStage.setTitle("Descargando");
				viewSetCenter(Routes.getRoute("popup-download-progress"));
				break;

			default:
				break;
			}

			primaryStage.setOnCloseRequest(event -> {
				UtilsPopup.selectedLibrary = null;
				UtilsDownload.targetLibrary = null;
			});

			primaryStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the view depending of the error page.
	 */
	private void errorSwitch() {
		switch (UtilsPopup.errType) {
		case VLC:
			primaryStage.setTitle("ERROR VLC");
			break;

		case LIBRARY_EXISTS:
			primaryStage.setTitle("BIBLIOTECA EXISTE");
			break;

		case VIDEO_EXISTS:
			primaryStage.setTitle("VIDEO EXISTE");
			break;

		case LIBRARY_NAME_EMPTY:
			primaryStage.setTitle("Biblioteca");
			break;

		case VIDEO_NAME_EMPTY:
			primaryStage.setTitle("Video sin nombre");
			break;

		case VIDEO_URL_EMPTY:
			primaryStage.setTitle("URL vacia");
			break;

		case VIDEO_URL_INVALID:
			primaryStage.setTitle("URL invalida");
			break;

		case VIDEO_LIBRARY_EMPTY:
			primaryStage.setTitle("Biblioteca vacia");
			break;

		case VIDEO_SAME_NAME:
			primaryStage.setTitle("Video con mismo nombre");
			break;

		case VIDEO_NOT_FOUND:
			primaryStage.setTitle("Video no encontrado");
			break;

		default:
			break;
		}
		viewSetCenter(Routes.getRoute("popup-error"));
	}

	/**
	 * Allows to change the center view.
	 * 
	 * @param view String
	 */
	public void viewSetCenter(String view) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GrabberApp.class.getResource(view));

			centerPane = (AnchorPane) loader.load();

			AbstractPopupController controller = loader.getController();
			if (controller != null)
				controller.setPopup(this);

			rootPane.setCenter(centerPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stage getStage() {
		return primaryStage;
	}
}
