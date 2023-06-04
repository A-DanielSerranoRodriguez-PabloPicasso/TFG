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
import models.javafx.FlowVideos;
import models.javafx.GridVideos;
import models.javafx.LibraryPill;
import utils.ImgUtils;
import utils.Utils;
import utils.UtilsDownload;
import utils.UtilsPopup;

public class ControllerLibrary extends AbstractController {
	private enum VideoPane {
		FLOW, GRID
	}

	private Library library;
	private List<Video> videos;
	private List<Library> libraries;
	private VideoPane videoPane;

	@FXML
	private VBox vbObjects;

	@FXML
	private Label lblLibrary;

	@FXML
	private HBox hBoxVideos;

	@FXML
	private HBox hBoxBreadcrumb;

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

	@FXML
	private ImageView imgSwapVideoPane;

	@FXML
	private VBox vbVideos;

	private Button btnBack;
	private Label lblBreadcrumb;

	private GridVideos gridVideos;
	private FlowVideos flowVideos;

	public ControllerLibrary() {
		gLibrary = getGLibrary();
		gVideo = getGVideo();
		library = Utils.selectedLibrary;
		videos = gVideo.getByLibrary(library);
		Utils.controller = this;
		videoPane = VideoPane.FLOW;

		gridVideos = new GridVideos(this);
		flowVideos = new FlowVideos();

		btnBack = new Button("<");
		lblBreadcrumb = new Label();
	}

	public void initialize() {
		vbVideos.getChildren().add(gridVideos);
		vbVideos.getChildren().add(flowVideos);

		lblLibrary.setText(library.getName());

		imgDownload.setImage(ImgUtils.getInternalImage("/img/icon/download.png"));

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

		imgAddLibrary.setImage(ImgUtils.getInternalImage("/img/icon/add.png"));

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
				reload();
			});
		} else {
			hBoxBreadcrumb.getChildren().remove(btnBack);
			hBoxBreadcrumb.getChildren().remove(lblBreadcrumb);
		}

		fillVideos();
	}

	@Override
	public void reload() {
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
				reload();
			});
		} else {
			hBoxBreadcrumb.getChildren().remove(btnBack);
			hBoxBreadcrumb.getChildren().remove(lblBreadcrumb);
		}

		fillVideos();
		fillLibraries();
	}

	private void fillVideos() {
		videos = gVideo.getByLibrary(library);

		flowVideos.clear();
		gridVideos.clear();

		switch (videoPane) {
		case FLOW:
			imgSwapVideoPane.setImage(ImgUtils.getInternalImage("/img/icon/swap-list.png"));
			vbVideos.getChildren().remove(gridVideos);
			flowVideos.reload(videos);
			break;

		case GRID:
			imgSwapVideoPane.setImage(ImgUtils.getInternalImage("/img/icon/swap-cards.png"));
			vbVideos.getChildren().remove(flowVideos);
			gridVideos.reload(videos);
			break;

		default:
			break;
		}
	}

	@FXML
	private void handleSwapVideoPane() {
		switch (videoPane) {
		case FLOW:
			vbVideos.getChildren().add(gridVideos);
			videoPane = VideoPane.GRID;
			break;

		case GRID:
			vbVideos.getChildren().add(flowVideos);
			videoPane = VideoPane.FLOW;
			break;
		default:
			break;
		}

		fillVideos();
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
