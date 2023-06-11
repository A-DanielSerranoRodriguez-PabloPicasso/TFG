package grabberApp.javafx.fxmls;

import java.util.List;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
import models.Video;
import models.javafx.FlowVideos;
import models.javafx.LibraryPill;
import utils.ImgUtils;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerLandPage extends AbstractController {

	private List<Library> libraries;

	private List<Video> recentVideos;

	@FXML
	private ImageView imgAddLibrary;

	@FXML
	private GridPane gpLibraries;

	@FXML
	private VBox vbVideos;

	@FXML
	private HBox hbVideos;

	private FlowVideos fpRecentVideos;

	public ControllerLandPage() {
		gApp = Utils.gApp;
		Utils.controller = this;
		gLibrary = getGLibrary();
		gVideo = getGVideo();
		gApp.setLibraries(gLibrary.getTop());

		recentVideos = gVideo.getRecent(10);
		fpRecentVideos = new FlowVideos();
	}

	public void initialize() {
		libraries = gApp.getLibraries();
		vbVideos.getChildren().add(fpRecentVideos);
		imgAddLibrary.setImage(ImgUtils.getInternalImage("/img/icon/add.png"));

		fillLibraries();
		fillRecentVideos();
	}

	@FXML
	private void handleAddLirary() {
		try {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.LIBRARY;
			new Popup().start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		fillLibraries();
	}

	private void fillLibraries() {
		libraries = gLibrary.getTop();
		gApp.setLibraries(libraries);

		gpLibraries.getChildren().clear();
		for (int i = 0, s = libraries.size(); i < s; i++) {
			gpLibraries.add(new LibraryPill(libraries.get(i), this), i, 0);
		}
	}

	@FXML
	public void fillRecentVideos() {
		recentVideos = gVideo.getRecent(10);
		fpRecentVideos.reload(recentVideos);
	}

	@FXML
	private void changeList() {

	}

	@Override
	public void reload() {
		fillLibraries();
		fillRecentVideos();
	}

}
