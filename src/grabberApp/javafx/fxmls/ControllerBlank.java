package grabberApp.javafx.fxmls;

import java.io.File;

import dao.GOriginImp;
import grabberApp.javafx.fxmls.popups.Popup;
import javafx.stage.Stage;
import models.AbstractController;
import utils.Routes;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerBlank extends AbstractController {

	public ControllerBlank() {
		gApp = Utils.gApp;
	}

	public void initialize() {
		gApp.getStage().setOnShown(arg0 -> {
			GOriginImp.gestor().nuke();
			File dbFile = new File(Utils.dbFilePath);
			gApp.viewSetCenter(Routes.getRoute("fl-preparing"));
			while (!dbFile.exists()) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			UtilsPopup.page = UtilsPopup.POPUP_PAGE.SETUP;
			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (GOriginImp.gestor().getOrigin() == null)
				gApp.getStage().close();
			else
				gApp.viewSetCenter(Routes.getRoute("landpage"));
		});
	}
}
