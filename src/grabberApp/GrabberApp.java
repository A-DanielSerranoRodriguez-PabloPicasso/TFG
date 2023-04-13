package grabberApp;

import dao.GLibrary;
import dao.GLibraryImp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.AbstractController;

public class GrabberApp extends Application {
	private Stage primaryStage;
	private AnchorPane centerPane;
	private BorderPane rootPane;
	private GLibrary<String> gLibrary;

	public static void main(String[] args) {
		launch(args);
	}

	public GLibrary<String> getLibrary() {
		return gLibrary;
	}

	@Override
	public void start(Stage arg0) throws Exception {
//		Gestor<Generales> g = GGenerales.gestor;
		gLibrary = new GLibraryImp();
		primaryStage = arg0;
		Util.gapp = this;
		initLayout();
	}

	private void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(GrabberApp.class.getResource("/grabberApp/javafx/fxmls/Root.fxml"));
			rootPane = (BorderPane) loader.load();

			AbstractController controller = loader.getController();
			if (controller != null)
				controller.setGrabberApp(this);

			primaryStage.setScene(new Scene(rootPane));
			viewSwapMiddle("/grabberApp/javafx/fxmls/Basic.fxml");

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stage getStage() {
		return primaryStage;
	}

	public void viewSwapMiddle(String view) {
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

}
