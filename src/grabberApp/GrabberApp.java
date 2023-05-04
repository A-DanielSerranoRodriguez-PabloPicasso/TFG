package grabberApp;

import dao.GLibrary;
import dao.SQLiteDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
import utils.Routes;
import utils.Utils;

public class GrabberApp extends Application {
	private Stage primaryStage;
	private BorderPane rootPane;
	private AnchorPane centerPane;
	private GLibrary<Library> gLibrary;

	public static void main(String[] args) {
		launch(args);
	}

	public GLibrary<Library> getLibrary() {
		return gLibrary;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = arg0;
		Utils.gApp = this;
		Utils.sqlDao = SQLiteDAO.getDao();
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

			// TODO remove syso
			if (Utils.firstStart()) {
				System.out.println("Firs");
				viewSetCenter(Routes.getRoute("blank"));
			} else {
				System.out.println("No first");
				viewSetCenter(Routes.getRoute("basic"));
			}

			primaryStage.show();
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
