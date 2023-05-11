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
				viewSetCenter(Routes.getRoute("popup-library-create"));
				break;

			default:
				break;
			}

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
