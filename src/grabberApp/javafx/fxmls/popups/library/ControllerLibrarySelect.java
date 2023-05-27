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

	private List<Library> libraries;

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
		gLibrary = getGLibrary();
		gVideo = getGVideo();
	}

	public void initialize() {
		btnBack.setVisible(false);
		btnSelectThis.setDisable(true);
		btnListChildren.setDisable(true);
		imgSearch.setImage(ImgUtils.getImage("/img/icon/search.png"));
		imgSearch.setFitWidth(32);
		imgSearch.setFitHeight(32);

		libraries = Utils.libraries;

		fillLibraries();

		gvLibraries.setOnMouseClicked(event -> {
			Library library = UtilsPopup.selectedLibrary;
			List<Library> librar = getGLibrary().getChildren(UtilsPopup.selectedLibrary);
			if (library != null) {
				btnSelectThis.setDisable(false);
				if (librar.size() != 0 && !togChildren.isSelected()) {
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

			this.libraries = libraries;

			fillLibraries();
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
			libraries = getGLibrary().getChildren(UtilsPopup.previousLibrary);

			fillLibraries();
		});

		btnBack.setOnAction(event -> {
			UtilsPopup.selectedLibrary = UtilsPopup.previousLibrary;
			UtilsPopup.previousLibrary = UtilsPopup.selectedLibrary.getLibParent();

			if (UtilsPopup.previousLibrary == null)
				btnBack.setVisible(false);
		});

		imgSearch.setOnMouseClicked(event -> {
			gvLibraries.getChildren().clear();
			UtilsPopup.selectedLibrary = null;
			btnSelectThis.setDisable(true);
			btnListChildren.setDisable(true);
			if (UtilsPopup.previousLibrary != null) {
				libraries = getGLibrary().getFromName(UtilsPopup.previousLibrary, txfSearchBar.getText());
			} else {
				if (togChildren.isSelected())
					libraries = gLibrary.getFromNameEverywhere(txfSearchBar.getText());
				else
					libraries = gLibrary.getFromName(txfSearchBar.getText());
			}

			fillLibraries();
		});
	}

	private void closeView() {
		popup.getStage().close();
	}

	private void fillLibraries() {
		int i = 0;
		for (Library library : libraries) {
			gvLibraries.add(new LineLibrary(library), 0, i);
			i++;
		}
	}

}
