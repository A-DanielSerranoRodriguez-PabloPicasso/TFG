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

/**
 * Controller of the Library.
 * 
 * @author Daniel Serrano Rodriguez
 */
public class ControllerLibrary extends AbstractController {
	/*
	 * Depending of the VideoPane, we show on grid or another.
	 */
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

	/**
	 * Constructor.
	 * 
	 * It puts the default pane as the Flow.
	 */
	public ControllerLibrary() {
		gLibrary = getGLibrary();
		gVideo = getGVideo();
		gApp = Utils.gApp;
		Utils.controller = this;
		library = gApp.getCurrentLibrary();
		videos = gVideo.getByLibrary(library);
		videoPane = VideoPane.FLOW;

		gridVideos = new GridVideos(this, library);
		flowVideos = new FlowVideos();

		btnBack = new Button("<");
		lblBreadcrumb = new Label();
	}

	/**
	 * Initializer.
	 */
	public void initialize() {
		vbVideos.getChildren().add(gridVideos);
		vbVideos.getChildren().add(flowVideos);

		lblLibrary.setText(library.getName());

		imgDownload.setImage(ImgUtils.getInternalImage("/img/icon/download.png"));

		btnDownload.setOnAction(event -> {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.DOWNLOAD;
			UtilsDownload.targetLibrary = library;
			UtilsDownload.toTarget = true;

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

		/*
		 * If the library has a parent, shows the back button and the breadcrumb.
		 */
		if (library.getLibParent() != null) {
			if (!hBoxBreadcrumb.getChildren().contains(btnBack))
				hBoxBreadcrumb.getChildren().add(btnBack);
			lblBreadcrumb.setText(library.getTree());
			if (!hBoxBreadcrumb.getChildren().contains(lblBreadcrumb)) {
				hBoxBreadcrumb.getChildren().add(lblBreadcrumb);
			}
			btnBack.setOnAction(event -> {
				library = library.getLibParent();
//				gApp.setCurrentLibrary(library);
				reload();
			});
		} else {
			hBoxBreadcrumb.getChildren().remove(btnBack);
			hBoxBreadcrumb.getChildren().remove(lblBreadcrumb);
		}

		fillVideos();
	}

	/**
	 * Reloads the library view.
	 */
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
				library = library.getLibParent();
//				gApp.setCurrentLibrary(library);
				reload();
			});
		} else {
			hBoxBreadcrumb.getChildren().remove(btnBack);
			hBoxBreadcrumb.getChildren().remove(lblBreadcrumb);
		}

		fillVideos();
		fillLibraries();
	}

	/**
	 * Fills the videos.
	 * 
	 * Depending on the pane, changes the image of the button.
	 */
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

	/**
	 * Handles the swap of the video pane.
	 */
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

	/**
	 * Fills the library pane.
	 */
	private void fillLibraries() {
		gpLibraries.getChildren().clear();
		libraries = gLibrary.getChildren(library);

		if (libraries.size() > 0) {
			int i = 0;
			for (Library library : libraries) {
				gpLibraries.add(new LibraryPill(library, this), i, 0);
				i++;
			}
		}
	}
}
