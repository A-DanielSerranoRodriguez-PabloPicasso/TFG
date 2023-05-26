package grabberApp.javafx.fxmls.popups.library;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import models.AbstractPopupController;
import models.Library;
import models.javafx.LineLibrary;
import utils.ImgUtils;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerLibrarySelect extends AbstractPopupController {

	@FXML
	private TextField txfSearchBar;

	@FXML
	private ToggleButton togChildren;

	@FXML
	private GridPane gvLibraries;

	@FXML
	private ImageView imgSearch;

	@FXML
	private Button btnSelectThis;

	@FXML
	private Button btnListChildren;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnBack;

	public ControllerLibrarySelect() {
		popup = UtilsPopup.popup;
	}

	public void initialize() {
		btnBack.setVisible(false);
		btnSelectThis.setDisable(true);
		btnListChildren.setDisable(true);
		imgSearch.setImage(ImgUtils.getImage("/img/icon/search.png"));
		imgSearch.setFitWidth(32);
		imgSearch.setFitHeight(32);

		List<Library> libraries = Utils.libraries;

		int i = 0;
		for (Library library : libraries) {
			gvLibraries.add(new LineLibrary(library), 0, i);
			i++;
		}

		gvLibraries.setOnMouseClicked(event -> {
			Library library = UtilsPopup.selectedLibrary;
			if (!library.equals(null)) {
				btnSelectThis.setDisable(false);
				if (!library.getParent().equals(null) && !togChildren.isPressed()) {
					btnListChildren.setDisable(false);
				}
			}
		});

		setBehaviourButtons();
	}

	private void setBehaviourButtons() {
		togChildren.setOnMouseClicked(event -> {
			boolean isPressed = togChildren.isSelected();
			List<Library> libraries;

			gvLibraries.getChildren().clear();
			UtilsPopup.selectedLibrary = null;
			UtilsPopup.previousLibrary = null;

			if (!isPressed) {
				libraries = Utils.libraries;
			} else {
				btnBack.setVisible(false);
				libraries = getGLibrary().getChildless();
			}

			int i = 0;
			for (Library library : libraries) {
				gvLibraries.add(new LineLibrary(library), 0, i);
				i++;
			}
		});

		btnCancel.setOnAction(event -> {
			UtilsPopup.selectedLibrary = null;
			UtilsPopup.previousLibrary = null;
			closeView();
		});

		btnSelectThis.setOnAction(event -> {
			UtilsPopup.previousLibrary = null;
			closeView();
		});

		btnListChildren.setOnAction(event -> {
			UtilsPopup.previousLibrary = UtilsPopup.selectedLibrary;
			List<Library> libraries = getGLibrary().getChildren(UtilsPopup.previousLibrary);

			gvLibraries.getChildren().clear();
			int i = 0;

			for (Library library : libraries) {
				gvLibraries.add(new LineLibrary(library), 0, i);
				i++;
			}
		});

		btnBack.setOnAction(event -> {
			UtilsPopup.selectedLibrary = UtilsPopup.previousLibrary;
			UtilsPopup.previousLibrary = UtilsPopup.selectedLibrary.getLibParent();

			if (UtilsPopup.previousLibrary.equals(null))
				btnBack.setVisible(false);
		});
	}

	private void closeView() {
		popup.getStage().close();
	}

}
