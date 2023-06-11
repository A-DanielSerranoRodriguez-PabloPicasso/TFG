package grabberApp.javafx.fxmls;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.AbstractController;
import utils.ImgUtils;
import utils.Routes;
import utils.Utils;
import utils.UtilsDownload;
import utils.UtilsPopup;

/**
 * Controller of the top bar
 * 
 * @author Daniel Serrano Rodriguez
 */
public class ControllerTopBar extends AbstractController {

	@FXML
	private ImageView imgHome;

	@FXML
	private Button btnHome;

	@FXML
	private MenuButton btnDownloads;

	/**
	 * Constructor
	 */
	public ControllerTopBar() {
		gApp = Utils.gApp;
	}

	/**
	 * Initializer
	 */
	public void initialize() {
		imgHome.setImage(ImgUtils.getInternalImage("/img/icon/home.png"));
		Utils.mbDownloads = btnDownloads;
	}

	/**
	 * Handles the go back home request
	 */
	@FXML
	private void handleHome() {
		gApp.viewSetCenter(Routes.getRoute("landpage"));
	}

	/**
	 * Handles the download request.
	 */
	@FXML
	private void handleNewDownload() {
		UtilsPopup.page = UtilsPopup.POPUP_PAGE.DOWNLOAD;
		UtilsDownload.toTarget = false;

		try {
			new Popup().start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
