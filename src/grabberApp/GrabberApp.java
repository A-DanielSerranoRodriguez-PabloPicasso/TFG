package grabberApp;

import dao.GGenerales;
import dao.Gestor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Generales;

public class GrabberApp extends Application {
	private Stage primaryStage;
	private AnchorPane primerPanel;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		Gestor<Generales> g = GGenerales.gestor;
		primaryStage = arg0;
		Util.gapp = this;
		initLayout();
	}

	private void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(GrabberApp.class.getResource("/grabberApp/javafx/fxmls/Basic.fxml"));
			primerPanel = (AnchorPane) loader.load();
			
			primaryStage.setScene(new Scene(primerPanel));
			loader.getController();
			
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stage getStage() {
		return primaryStage;
	}

}
