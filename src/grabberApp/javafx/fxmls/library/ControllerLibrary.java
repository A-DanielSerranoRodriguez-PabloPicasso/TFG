package grabberApp.javafx.fxmls.library;

import java.util.List;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
import models.Video;
import models.javafx.LibraryPill;
import models.javafx.LineVideo;
import utils.ImgUtils;
import utils.Utils;
import utils.UtilsDownload;
import utils.UtilsPopup;

public class ControllerLibrary extends AbstractController {

	private Library library;
	private List<Video> videos;
	private List<Library> libraries;

	@FXML
	private VBox vbObjects;

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
	private GridPane gpLibraries;

	@FXML
	private Button btnDownload;

	@FXML
	private ImageView imgDownload;

	@FXML
	private Button btnAddLibrary;

	@FXML
	private ImageView imgAddLibrary;

	private Button btnBack;
	private Label lblBreadcrumb;

	public ControllerLibrary() {
		gLibrary = getGLibrary();
		gVideo = getGVideo();
		library = Utils.selectedLibrary;

		btnBack = new Button("<");
		lblBreadcrumb = new Label();
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

		imgAddLibrary.setImage(ImgUtils.getImage("/img/icon/add.png"));

		btnAddLibrary.setOnAction(event -> {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.LIBRARY;
			UtilsPopup.selectedLibrary = library;

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}

			UtilsPopup.selectedLibrary = null;

			fillLibraries();
		});

		fillVideos();
		fillLibraries();

		if (library.getLibParent() != null) {
			if (!hBoxBreadcrumb.getChildren().contains(btnBack))
				hBoxBreadcrumb.getChildren().add(btnBack);
			lblBreadcrumb.setText(library.getTree());
			if (!hBoxBreadcrumb.getChildren().contains(lblBreadcrumb)) {
				hBoxBreadcrumb.getChildren().add(lblBreadcrumb);
			}
			btnBack.setOnAction(event -> {
				Utils.selectedLibrary = library.getLibParent();
				library = Utils.selectedLibrary;
				initialize();
			});
		} else {
			hBoxBreadcrumb.getChildren().remove(btnBack);
			hBoxBreadcrumb.getChildren().remove(lblBreadcrumb);
		}
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

	private void fillLibraries() {
		gpLibraries.getChildren().clear();
		libraries = gLibrary.getChildren(library);

		if (libraries.size() > 0) {
			int i = 0;
			for (Library library : libraries) {
				gpLibraries.add(new LibraryPill(library, this), 0, i);
				i++;
			}
		}
	}
}
