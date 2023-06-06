package models.javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.Library;
import utils.UtilsPopup;

/**
 * Creates a line that holds the library, thought to be used in the library
 * selector
 * 
 * @author Daniel Serrano Rodriguez
 */
public class LineLibrary extends AnchorPane {

	private HBox hbContent;
	private Label lblName, lblPath;

	/**
	 * Creates a line
	 * 
	 * @param library Library
	 */
	public LineLibrary(Library library) {
		hbContent = new HBox(10);
		hbContent.setPadding(new Insets(20));
		hbContent.setAlignment(Pos.CENTER);

		lblName = new Label(library.getName());
		lblPath = new Label(library.getTree());

		hbContent.getChildren().add(lblName);
		hbContent.getChildren().add(lblPath);

		this.setOnMouseClicked(event -> {
			if (UtilsPopup.page == UtilsPopup.POPUP_PAGE.SELECT_LIBRARY) {
				UtilsPopup.selectedLibrary = library;
			} else {

			}
		});

		this.getChildren().add(hbContent);
		this.setVisible(true);
	}
}
