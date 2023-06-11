package grabberApp.javafx.fxmls.popups.library;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import models.AbstractPopupController;
import models.Library;
import models.javafx.LineLibrary;
import utils.ImgUtils;
import utils.UtilsPopup;

/**
 * Controller of the library selector
 * 
 * @author Daniel Serrano Rodriguez
 */
public class ControllerLibrarySelect extends AbstractPopupController {

	private List<Library> libraries;
	private Library currentLibrary, previousLibrary;

	@FXML
	private TextField txfSearchBar;

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

	/**
	 * Constructor
	 */
	public ControllerLibrarySelect() {
		popup = UtilsPopup.popup;
		gLibrary = getGLibrary();
		gVideo = getGVideo();
	}

	/**
	 * Initializer
	 */
	public void initialize() {
		// If the library has a parent, the back button is visible
		if (UtilsPopup.selectedLibrary != null) {
			if (UtilsPopup.selectedLibrary.getLibParent() != null)
				btnBack.setVisible(false);
		}
		btnSelectThis.setDisable(true);
		btnListChildren.setDisable(true);
		imgSearch.setImage(ImgUtils.getInternalImage("/img/icon/search.png"));
		imgSearch.setFitWidth(32);
		imgSearch.setFitHeight(32);

		libraries = gLibrary.getTop();

		fillLibraries();

		/*
		 * When a library is selected, if it has sub libraries, the button is enabled.
		 */
		gvLibraries.setOnMouseClicked(event -> {
			Library library = UtilsPopup.selectedLibrary;
			List<Library> librar = getGLibrary().getChildren(UtilsPopup.selectedLibrary);
			if (library != null) {
				btnSelectThis.setDisable(false);
				if (librar.size() != 0) {
					btnListChildren.setDisable(false);
				}
			}
		});

		setBehaviourButtons();
	}

	/**
	 * Sets the button behaviour
	 */
	private void setBehaviourButtons() {
		btnCancel.setOnAction(event -> {
			UtilsPopup.selectedLibrary = null;
			closeView();
		});

		btnSelectThis.setOnAction(event -> {
			closeView();
		});

		// Reloads the view with the sub libraries
		btnListChildren.setOnAction(event -> {
			previousLibrary = UtilsPopup.selectedLibrary;
			currentLibrary = UtilsPopup.selectedLibrary;
			libraries = getGLibrary().getChildren(previousLibrary);

			btnBack.setVisible(true);
			btnListChildren.setDisable(true);
			btnSelectThis.setDisable(true);

			fillLibraries();
		});

		/*
		 * Goes back to the previous library. If it doesn't has sub libraries, the
		 * button is hidden.
		 */
		btnBack.setOnAction(event -> {
			UtilsPopup.selectedLibrary = currentLibrary;
			previousLibrary = UtilsPopup.selectedLibrary.getLibParent();

			if (previousLibrary == null) {
				btnBack.setVisible(false);
				btnSelectThis.setDisable(true);
				libraries = getGLibrary().getTop();
			} else {
				libraries = getGLibrary().getChildren(previousLibrary);
			}

			fillLibraries();

//			if (UtilsPopup.previousLibrary == null)
//				btnBack.setVisible(false);
		});

		/*
		 * When the image is clicked, if the text bar is empty, it shows the root
		 * libraries.
		 * 
		 * If not, search for libraries containing the text.
		 */
		imgSearch.setOnMouseClicked(event -> {
			if (!txfSearchBar.getText().isEmpty()) {
				UtilsPopup.selectedLibrary = null;
				previousLibrary = null;

				gvLibraries.getChildren().clear();
				btnSelectThis.setDisable(true);
				btnListChildren.setDisable(true);

				libraries = gLibrary.getFromName(txfSearchBar.getText());
			} else
				libraries = gLibrary.getTop();

			fillLibraries();
		});
	}

	/**
	 * Closes the pop-up
	 */
	private void closeView() {
		popup.getStage().close();
	}

	/**
	 * Fills the libraries
	 */
	private void fillLibraries() {
		gvLibraries.getChildren().clear();
		int i = 0;
		for (Library library : libraries) {
			gvLibraries.add(new LineLibrary(library), 0, i);
			i++;
		}
	}

}
