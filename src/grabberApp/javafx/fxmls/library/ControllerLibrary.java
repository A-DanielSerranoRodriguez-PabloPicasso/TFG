package grabberApp.javafx.fxmls.library;

import java.util.List;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
import models.Video;
import models.javafx.LineVideo;
import utils.ImgUtils;
import utils.Utils;
import utils.UtilsDownload;
import utils.UtilsPopup;

public class ControllerLibrary extends AbstractController {

	private Library library;
	private List<Video> videos;

	@FXML
	private Label lblLibrary;

	@FXML
	private HBox hBoxFolders;

	@FXML
	private HBox hBoxVideos;

	@FXML
	private HBox hBoxBreadcrumb;

	@FXML
	private GridPane gpVideos;

	@FXML
	private Button btnDownload;

	@FXML
	private ImageView imgDownload;

	public ControllerLibrary() {
		gLibrary = getGLibrary();
		gVideo = getGVideo();
		library = Utils.selectedLibrary;

//		gpVideos = new GridPane();
	}

	public void initialize() {
		lblLibrary.setText(library.getName());

		imgDownload.setImage(ImgUtils.getImage("/img/icon/download.png"));

		btnDownload.setOnAction(event -> {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.DOWNLOAD;
			UtilsDownload.targetLibrary = library;

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}

			fillVideos();
		});

		fillVideos();

//		Button btnLibrary;
//		List<Library> subLibraries = gLibrary.getChildren(Utils.selectedLibrary);
//
//
//		for (Library library : subLibraries) {
//			btnLibrary = new Button(library.getName());
//			hBoxFolders.getChildren().add(btnLibrary);
//			btnLibrary.setOnMouseClicked(event -> {
//				Utils.selectedLibrary = library;
//				gApp.viewSetCenter("library");
//			});
//		}
	}

	@Override
	public void reload() {
		fillVideos();
	}

	private void fillVideos() {
		gpVideos.getChildren().clear();
		videos = gVideo.getByLibrary(library);

		if (videos.size() > 0) {
			int i = 0;
			for (Video video : videos) {
				gpVideos.add(new LineVideo(video, this), 0, i);
				i++;
			}
		}
	}
}
