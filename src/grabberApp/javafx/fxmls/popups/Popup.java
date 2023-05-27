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

public class Popup extends Application {
	private Stage primaryStage;
	private BorderPane rootPane;
	private AnchorPane centerPane;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = arg0;
		UtilsPopup.popup = this;

		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(Utils.gApp.getStage());
		primaryStage.setResizable(false);

		initLayout();
	}

	private void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(GrabberApp.class.getResource(Routes.getRoute("popup-root")));
			rootPane = (BorderPane) loader.load();

			AbstractPopupController controller = loader.getController();
			if (controller != null)
				controller.setPopup(this);

			primaryStage.setScene(new Scene(rootPane));

			switch (UtilsPopup.page) {
			case LIBRARY:
				primaryStage.setTitle("Crear biblioteca");
				viewSetCenter(Routes.getRoute("popup-library-create"));
				break;

			case DOWNLOAD:
				primaryStage.setTitle("Descargar video");
				viewSetCenter(Routes.getRoute("popup-download"));
				break;

			case VIDEO:
				viewSetCenter(Routes.getRoute("popup-video"));
				break;

			case ERR_VLC:
				primaryStage.setTitle("ERROR VLC");
				viewSetCenter(Routes.getRoute("popup-error-no-vlc"));
				break;

			case SELECT_LIBRARY:
				primaryStage.setTitle("Seleccionar biblioteca");
				viewSetCenter(Routes.getRoute("popup-library-select"));
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
