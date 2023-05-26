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
import utils.UtilsPopup;

public class LibrarySearcher extends HBox {

	private TextField txfSearchBar;
	private ImageView imgSearch;

	public LibrarySearcher() {
		FileInputStream fis = null;
		File image = new File(this.getClass().getResource("/img/icon/search.png").getPath());

		try {
			fis = new FileInputStream(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		txfSearchBar = new TextField();
		imgSearch = new ImageView(new Image(fis));
		imgSearch.setFitWidth(32);
		imgSearch.setFitHeight(32);

		setBehaviour();
	}

	private void setBehaviour() {
		imgSearch.setOnMouseClicked(event -> {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.SELECT_LIBRARY;
			UtilsPopup.librarySearched = txfSearchBar.getText();
			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
