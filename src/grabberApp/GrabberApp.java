package grabberApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.AbstractController;
import utils.Routes;
import utils.Utils;

public class GrabberApp extends Application {
	private Stage primaryStage;
	private BorderPane rootPane;
	private AnchorPane centerPane, topPane;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = arg0;
		Utils.gApp = this;
		Routes.fillRoutes();
		initLayout();
	}

	private void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(GrabberApp.class.getResource(Routes.getRoute("root")));
			rootPane = (BorderPane) loader.load();

			AbstractController controller = loader.getController();
			if (controller != null)
				controller.setGrabberApp(this);

			primaryStage.setScene(new Scene(rootPane));

			viewSetTop(Routes.getRoute("topBar"));

			if (Utils.firstStart()) {
				viewSetCenter(Routes.getRoute("blank"));
			} else {
				viewSetCenter(Routes.getRoute("landpage"));
			}

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewSetTop(String view) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GrabberApp.class.getResource(view));

			topPane = (AnchorPane) loader.load();

			AbstractController controller = loader.getController();
			if (controller != null)
				controller.setGrabberApp(this);

			rootPane.setTop(topPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewSetCenter(String view) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GrabberApp.class.getResource(view));

			centerPane = (AnchorPane) loader.load();

			AbstractController controller = loader.getController();
			if (controller != null)
				controller.setGrabberApp(this);

			rootPane.setCenter(centerPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stage getStage() {
		return primaryStage;
	}

}
