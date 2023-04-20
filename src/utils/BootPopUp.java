package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.AbstractController;

public class BootPopUp {
	private Stage primaryStage;
	private BorderPane rootPane;
	private AnchorPane centerPane;

	public BootPopUp() {
		primaryStage = new Stage();
		primaryStage.setResizable(false);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(Utils.gApp.getStage());
		Utils.bootPopUp = this;
	}

	public void start() {
		initLayout();
	}

	private void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(BootPopUp.class.getResource("/grabberApp/javafx/fxmls/popup/Root.fxml"));
			rootPane = (BorderPane) loader.load();

			AbstractController controller = loader.getController();
			if (controller != null)
				controller.setBootPopUp(this);

			primaryStage.setScene(new Scene(rootPane));

			viewSetCenter("/grabberApp/javafx/fxmls/popup/Preparing.fxml");

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewSetCenter(String view) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(BootPopUp.class.getResource(view));

			centerPane = (AnchorPane) loader.load();

			AbstractController controller = loader.getController();
			if (controller != null)
				controller.setBootPopUp(this);

			rootPane.setCenter(centerPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stage getStage() {
		return primaryStage;
	}

}
