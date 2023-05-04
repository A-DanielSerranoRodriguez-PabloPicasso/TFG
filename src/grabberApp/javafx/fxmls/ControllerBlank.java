package grabberApp.javafx.fxmls;

import java.io.File;

import models.AbstractController;
import utils.Routes;
import utils.Utils;

public class ControllerBlank extends AbstractController {

	public ControllerBlank() {
		gApp = Utils.gApp;
	}

	public void initialize() {
		gApp.getStage().setOnShown(arg0 -> {
			File configFile = new File(Utils.getPath());
			gApp.viewSetCenter(Routes.getRoute("fl-preparing"));
			while (!configFile.exists()) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			gApp.viewSetCenter(Routes.getRoute("fl-prepared"));
//			new BootPopUp().start();
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
