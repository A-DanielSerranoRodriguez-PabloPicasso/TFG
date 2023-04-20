package grabberApp.javafx.fxmls;

import grabberApp.GrabberApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.AbstractController;
import utils.BootPopUp;
import utils.Utils;

public class ControllerBlank extends AbstractController {

	public ControllerBlank() {
		gApp = Utils.gApp;
	}

	public void initialize() {
		gApp.getStage().setOnShown(arg0 -> {
			new BootPopUp().start();
//			Stage stage = new Stage();;
//			BorderPane bp;
//
//			stage.setResizable(false);
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.initOwner(gApp.getStage());
//
//			try {
//				FXMLLoader loader = new FXMLLoader();
//
//				loader.setLocation(GrabberApp.class.getResource("/grabberApp/javafx/fxmls/popup/Root.fxml"));
//				bp = (BorderPane) loader.load();
//
//				AbstractController controller = loader.getController();
//				if (controller != null)
//					controller.setGrabberApp(Utils.gApp);
//
//				stage.setScene(new Scene(bp));
//
//				loader = new FXMLLoader();
//				loader.setLocation(GrabberApp.class.getResource("/grabberApp/javafx/fxmls/popup/Preparing.fxml"));
//
//				bp.setCenter((AnchorPane) loader.load());
//
//				stage.show();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		});
	}
}
