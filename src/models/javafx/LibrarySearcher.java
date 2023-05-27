package models.javafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Library;
import utils.UtilsPopup;

public class LibrarySearcher extends HBox {

	private Library library;
	private TextField txfSearchBar;
	private ImageView imgSearch;

	public LibrarySearcher(Library library) {
		this.library = library;
		FileInputStream fis = null;
		File image = new File(this.getClass().getResource("/img/icon/search.png").getPath());

		try {
			fis = new FileInputStream(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		txfSearchBar = new TextField(library.getName());
		txfSearchBar.setDisable(true);
		imgSearch = new ImageView(new Image(fis));
		imgSearch.setFitWidth(32);
		imgSearch.setFitHeight(32);

		setBehaviour();
		this.getChildren().add(txfSearchBar);
		this.getChildren().add(imgSearch);
		this.setVisible(true);
	}

	private void setBehaviour() {
		imgSearch.setOnMouseClicked(event -> {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.SELECT_LIBRARY;

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (UtilsPopup.selectedLibrary != null)
				txfSearchBar.setText(UtilsPopup.selectedLibrary.getName());
			else
				txfSearchBar.setText(library.getName());
		});
	}
}
