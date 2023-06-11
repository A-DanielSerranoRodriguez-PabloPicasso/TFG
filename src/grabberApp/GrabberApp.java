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
import utils.ImgUtils;
import utils.Routes;
import utils.Utils;
import utils.UtilsFirstBoot;

/**
 * Main app
 * 
 * @author Daniel Serrano Rodriguez
 */
public class GrabberApp extends Application {
	private Stage primaryStage;
	private BorderPane rootPane;
	private AnchorPane centerPane, topPane;

	private Library currentLibrary;
	private List<Library> libraries;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starts the app.
	 * 
	 * Sets a minimum height and width, an icon and a title.
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = arg0;
		Utils.gApp = this;

		primaryStage.setMinWidth(1000);
		primaryStage.setMinHeight(500);
		primaryStage.setMaximized(true);
		primaryStage.setTitle("jGrabber");
		primaryStage.getIcons().add(ImgUtils.getInternalImage("/img/icon/app.png"));
		primaryStage.setOnCloseRequest(event -> {
			try {
				SQLiteDAO.getDao().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		initLayout();
	}

	/**
	 * Initializer of the layout.
	 * 
	 * If an origin has been set, we go to the landpage.
	 */
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

	/**
	 * Changes the top pane.
	 * 
	 * @param view String
	 */
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

	/**
	 * Changes the middle pane.
	 * 
	 * @param view String
	 */
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

	/**
	 * Returns the primary stage
	 * 
	 * @return Stage
	 */
	public Stage getStage() {
		return primaryStage;
	}

	/**
	 * Returns the current library
	 * 
	 * @return Library
	 */
	public Library getCurrentLibrary() {
		return currentLibrary;
	}

	/**
	 * Sets the current library
	 * 
	 * @param library Library
	 */
	public void setCurrentLibrary(Library library) {
		this.currentLibrary = library;
	}

	/**
	 * Returns the libraries
	 * 
	 * @return the libraries
	 */
	public List<Library> getLibraries() {
		return libraries;
	}

	/**
	 * Sets the libraries
	 * 
	 * @param libraries the libraries to set
	 */
	public void setLibraries(List<Library> libraries) {
		this.libraries = libraries;
	}

}
