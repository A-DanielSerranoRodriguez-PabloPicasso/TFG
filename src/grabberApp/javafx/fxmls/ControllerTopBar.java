package grabberApp.javafx.fxmls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.AbstractController;
import utils.Routes;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerTopBar extends AbstractController {

	@FXML
	private ImageView imgHome;

	@FXML
	private Button btnHome;

	@FXML
	private MenuButton btnDownloads;

	public ControllerTopBar() {
		gApp = Utils.gApp;
	}

	public void initialize() {
		FileInputStream fis = null;
		File file = new File(gApp.getClass().getResource("/img/icon/home.png").getPath());

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		imgHome.setImage(new Image(fis));
		Utils.mbDownloads = btnDownloads;
	}

	@FXML
	private void handleHome() {
		gApp.viewSetCenter(Routes.getRoute("landpage"));
	}

	@FXML
	private void handleNewDownload() {
		UtilsPopup.page = UtilsPopup.POPUP_PAGE.DOWNLOAD;
		try {
			new Popup().start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
