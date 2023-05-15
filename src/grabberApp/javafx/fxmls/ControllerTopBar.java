package grabberApp.javafx.fxmls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.AbstractController;
import utils.Routes;
import utils.Utils;

public class ControllerTopBar extends AbstractController {

	@FXML
	private ImageView imgHome;

	@FXML
	private ImageView imgSwitchType;

	@FXML
	private ImageView imgSearch;

	@FXML
	private Button btnHome;

	@FXML
	private Button btnSwitchType;

	@FXML
	private Button btnSearch;

	@FXML
	private TextField txfSearch;

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

		file = new File(gApp.getClass().getResource("/img/icon/play.png").getPath());

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		imgSwitchType.setImage(new Image(fis));

		file = new File(gApp.getClass().getResource("/img/icon/search.png").getPath());

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		imgSearch.setImage(new Image(fis));
	}

	@FXML
	private void handleHome() {
		Utils.selectedLibrary = null;
		gApp.viewSetCenter(Routes.getRoute("landpage"));
	}

}
