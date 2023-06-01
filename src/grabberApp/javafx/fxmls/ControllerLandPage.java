package grabberApp.javafx.fxmls;

import java.util.List;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
import models.Video;
import models.javafx.LibraryPill;
import models.javafx.LineVideo;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerLandPage extends AbstractController {

	private List<Library> libraries;

	private List<Video> recentVideos;

	@FXML
	private HBox hBoxLibraries;

	@FXML
	private Button btnAddLibrary;

	@FXML
	private GridPane gpLibraries;

	@FXML
	private GridPane gpRecentVideos;

	public ControllerLandPage() {
		Utils.controllerLandPage = this;
		gApp = Utils.gApp;
		gLibrary = getGLibrary();
		gVideo = getGVideo();

		Utils.libraries = gLibrary.getTop();

		btnAddLibrary = new Button("Añadir");
		btnAddLibrary.setOnMousePressed(event -> {
			reloadLibraryList();
		});
	}

	public void initialize() {
		libraries = Utils.libraries;

		fillLibraries();
		fillRecentVideos();

		hBoxLibraries.getChildren().add(btnAddLibrary);
	}

	private void reloadLibraryList() {
		try {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.LIBRARY;
			new Popup().start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		hBoxLibraries.getChildren().clear();

		fillLibraries();

		hBoxLibraries.getChildren().add(btnAddLibrary);
	}

	private void fillLibraries() {
		Utils.libraries = gLibrary.getTop();
		libraries = Utils.libraries;
		gpLibraries.getChildren().clear();
		for (int i = 0, s = libraries.size(); i < s; i++) {
			gpLibraries.add(new LibraryPill(libraries.get(i), this), i, 0);
		}
	}

	@FXML
	public void fillRecentVideos() {
		gpRecentVideos.getChildren().clear();
		recentVideos = gVideo.getRecent(10);
		LineVideo cardVideo;
		int i = 0;

		for (Video video : recentVideos) {
			cardVideo = new LineVideo(video, this);
			gpRecentVideos.add(cardVideo, 0, i);
			i++;
		}
	}

	@Override
	public void reload() {
		fillLibraries();
		fillRecentVideos();
	}

}
