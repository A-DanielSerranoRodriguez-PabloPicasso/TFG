package grabberApp.javafx.fxmls.popups.warn;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.AbstractPopupController;
import models.Video;
import utils.UtilsPopup;

/**
 * Controller of the video exists view
 * 
 * @author Daniel Serrano Rodriguez
 */
public class ControllerVideoExists extends AbstractPopupController {

	@FXML
	private Button btnAction;

	@FXML
	private Button btnCancel;

	/**
	 * Constructor
	 */
	public ControllerVideoExists() {
		popup = UtilsPopup.popup;
	}

	/**
	 * Initializes the view
	 */
	@FXML
	private void initialize() {
		Video newVideo = UtilsPopup.video[1], supposedVideo = UtilsPopup.video[0];
		btnCancel.setOnAction(event -> {
			popup.getStage().close();
		});

		/**
		 * If the video exists, it lets you move it.
		 * 
		 * If not, it lets you download it.
		 */
		if (supposedVideo.getVideo().exists()) {
			btnAction.setText("Mover");
			btnAction.setOnAction(event -> {
				supposedVideo.setName(newVideo.getName());
				supposedVideo.moveVideo(newVideo.getLibrary());
				popup.getStage().close();
			});
		} else {
			btnAction.setText("Descargar");
			btnAction.setOnAction(event -> {
				UtilsPopup.grabber.run();
				popup.getStage().close();
			});
		}
	}
}
