package grabberApp;

import java.sql.SQLException;

import dao.SQLiteDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.AbstractController;
import utils.Routes;
import utils.Utils;
import utils.UtilsFirstBoot;

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

		primaryStage.setMinWidth(1000);
		primaryStage.setMinHeight(500);
		primaryStage.setMaximized(true);
		primaryStage.setTitle("jGrabber");
		primaryStage.setOnCloseRequest(event -> {
			try {
				SQLiteDAO.getDao().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
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

			UtilsFirstBoot ufb = UtilsFirstBoot.getUtil();

			if (ufb.firstStart()) {
				Utils.dbFolderPath = ufb.getDbFolderPath();
				Utils.dbFilePath = ufb.getDbFilePath();
				viewSetCenter(Routes.getRoute("blank"));
			} else {
				Utils.dbFilePath = ufb.getDbFilePath();
				Utils.origin = ufb.getOrigin();
				UtilsFirstBoot.close();
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
