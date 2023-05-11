package grabberApp.javafx.fxmls.library;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.AbstractController;
import models.Library;
import utils.Utils;

public class ControllerLibrary extends AbstractController {

	private Library library;

	@FXML
	private Text txtLibrary;

	@FXML
	private HBox hBoxFolders;

	@FXML
	private HBox hBoxVideos;

	public ControllerLibrary() {
		gLibrary = getLibrary();
		library = Utils.selectedLibrary;
	}

	public void initialize() {
		Button btnLibrary;
		List<Library> subLibraries = gLibrary.getChildren(Utils.selectedLibrary);

		txtLibrary.setText(library.getName());

		for (Library library : subLibraries) {
			btnLibrary = new Button(library.getName());
			hBoxFolders.getChildren().add(btnLibrary);
			btnLibrary.setOnMouseClicked(event -> {
				Utils.selectedLibrary = library;
				gApp.viewSetCenter("library");
			});
		}
	}
}
