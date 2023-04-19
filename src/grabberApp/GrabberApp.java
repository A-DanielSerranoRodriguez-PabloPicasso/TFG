package grabberApp;

import dao.GLibrary;
import dao.GLibraryImp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
import utils.Utils;

public class GrabberApp extends Application {
	private Stage primaryStage;
	private AnchorPane centerPane;
	private BorderPane rootPane;
	private GLibrary<Library> gLibrary;

	public static void main(String[] args) {
		launch(args);
	}

	public GLibrary<Library> getLibrary() {
		return gLibrary;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		gLibrary = new GLibraryImp();
		primaryStage = arg0;
		Utils.gapp = this;
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
			
			Stage preparing = new Stage();
			
			preparing.setResizable(false);

			if (Utils.firstStart()) {
				System.out.println("Firs");
				viewSwapMiddle("/grabberApp/javafx/fxmls/Blank.fxml");
			} else {
				System.out.println("No first");
				viewSwapMiddle("/grabberApp/javafx/fxmls/Basic.fxml");
			}

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
