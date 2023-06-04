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
import models.javafx.FlowVideos;
import models.javafx.LibraryPill;
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

	@FXML
	private HBox hbVideos;

	private FlowVideos fpRecentVideos;

	public ControllerLandPage() {
		gApp = Utils.gApp;
		gLibrary = getGLibrary();
		gVideo = getGVideo();
		Utils.libraries = gLibrary.getTop();
		Utils.controller = this;

		recentVideos = gVideo.getRecent(10);
		fpRecentVideos = new FlowVideos();

		btnAddLibrary = new Button("Añadir");
		btnAddLibrary.setOnMousePressed(event -> {
			reloadLibraryList();
		});
	}

	public void initialize() {
		libraries = Utils.libraries;

		hbVideos.getChildren().remove(gpRecentVideos);
		hbVideos.getChildren().add(fpRecentVideos);

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
