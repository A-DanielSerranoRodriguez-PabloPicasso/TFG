package grabberApp;

import java.sql.SQLException;
import java.util.List;

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
import utils.UtilsFirstBoot;

public class GrabberApp extends Application {
	private Stage primaryStage;
	private BorderPane rootPane;
	private AnchorPane centerPane, topPane;

	private Library currentLibrary;
	private List<Library> libraries;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = arg0;
		Utils.gApp = this;

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

			UtilsFirstBoot ufb = new UtilsFirstBoot();

			if (ufb.firstStart()) {
				viewSetCenter(Routes.getRoute("blank"));
			} else {
				viewSetCenter(Routes.getRoute("landpage"));
			}

			ufb = null;
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

	public Library getCurrentLibrary() {
		return currentLibrary;
	}

	public void setCurrentLibrary(Library library) {
		this.currentLibrary = library;
	}

	/**
	 * @return the libraries
	 */
	public List<Library> getLibraries() {
		return libraries;
	}

	/**
	 * @param libraries the libraries to set
	 */
	public void setLibraries(List<Library> libraries) {
		this.libraries = libraries;
	}

}
