package models.javafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Library;
import utils.UtilsPopup;

/**
 * Shows a library searcher and the current library for the user to select them
 * with ease
 * 
 * @author Daniel Serrano Rodriguez
 */
public class LibrarySearcher extends HBox {

	private Library library;

	private TextField txfSearchBar;
	private Button btnSearch;
	private ImageView imgSearch;

	/**
	 * Constructor that receives the current library
	 * 
	 * @param library Library
	 */
	public LibrarySearcher(Library library) {
		this.library = library;
		FileInputStream fis = null;
		File image = new File(this.getClass().getResource("/img/icon/search.png").getPath());

		try {
			fis = new FileInputStream(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		txfSearchBar = new TextField(library != null ? library.getName() : "");
		txfSearchBar.setDisable(true);

		imgSearch = new ImageView(new Image(fis));
		imgSearch.setFitWidth(26);
		imgSearch.setFitHeight(26);

		btnSearch = new Button("", imgSearch);
		btnSearch.getStyleClass().add("no-bg");

		setBehaviour();
		this.getChildren().add(txfSearchBar);
		this.getChildren().add(btnSearch);
		this.setAlignment(Pos.CENTER);
		this.setVisible(true);
	}

	/**
	 * Sets the behaviour of the buttons
	 */
	private void setBehaviour() {
		btnSearch.setOnAction(event -> {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.SELECT_LIBRARY;

			try {
				new Popup().start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (UtilsPopup.selectedLibrary != null)
				txfSearchBar.setText(UtilsPopup.selectedLibrary.getName());
			else
				txfSearchBar.setText(library != null ? library.getName() : "");
		});
	}
}
