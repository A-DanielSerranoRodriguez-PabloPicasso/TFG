package models.javafx;

import java.io.File;

import grabberApp.javafx.fxmls.popups.Popup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
import utils.FileUtils;
import utils.Routes;
import utils.Utils;
import utils.UtilsPopup;

/**
 * Library pill to manage and access them
 * 
 * @author Daniel Serrano Rodriguez
 */
public class LibraryPill extends HBox {

	private boolean bDelete, bRename;
	private File libraryFile;

	private Library library;
	private AbstractController controller;

	private Button btnLibrary;
	private MenuButton mbtnMenu;
	private MenuItem miRename, miDelete;

	private TextField txfLibraryName;
	private Button btnConfirm, btnCancel;

	/**
	 * Creates a pill given a library and the controller we currently are
	 * 
	 * @param library    Library
	 * @param controller AbstractController
	 */
	public LibraryPill(Library library, AbstractController controller) {
		this.library = library;
		this.controller = controller;
		this.setSpacing(20);
		this.setPadding(new Insets(10));
		libraryFile = new File(library.getPath());

		btnLibrary = new Button(library.getName());
		btnLibrary.setWrapText(false);
		mbtnMenu = new MenuButton();
		miRename = new MenuItem("Renombrar");
		if (!libraryFile.exists())
			miRename.setDisable(true);
		miDelete = new MenuItem("Eliminar");
		miDelete.getStyleClass().add("label-delete");

		txfLibraryName = new TextField(library.getName());
		btnConfirm = new Button();
		btnCancel = new Button("Cancelar");

		btnLibrary.getStyleClass().add("no-bg");
		mbtnMenu.getStyleClass().add("no-bg");

		mbtnMenu.getItems().add(miRename);
		mbtnMenu.getItems().add(miDelete);

		setBehaviourButtons();

		this.setAlignment(Pos.CENTER);
		this.setFillHeight(false);
		this.getStyleClass().add("libraryPill");
		this.getStyleClass().add("slightRadius");
		this.getChildren().add(btnLibrary);
		this.getChildren().add(mbtnMenu);
	}

	/**
	 * Configures the buttons actions
	 */
	private void setBehaviourButtons() {
		// When clicked, we access the library view
		btnLibrary.setOnMouseClicked(event -> {
			if (!libraryFile.exists()) {
				UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
				UtilsPopup.errType = UtilsPopup.ERR_TYPE.LIBRARY_NOT_FOUND;
				UtilsPopup.selectedLibrary = library;

				try {
					new Popup().start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				controller.gApp.setCurrentLibrary(library);
				controller.gApp.viewSetCenter(Routes.getRoute("library"));
			}
		});

		// When renaming, we show the the necessary components
		miRename.setOnAction(event -> {
			bRename = true;
			bDelete = false;
			btnConfirm.setText("Renombrar");
			btnConfirm.getStyleClass().remove("btn-delete-confirm");

			this.getChildren().remove(btnLibrary);
			this.getChildren().remove(mbtnMenu);
			this.getChildren().add(txfLibraryName);
			this.getChildren().add(btnConfirm);
			this.getChildren().add(btnCancel);
		});

		// We show a confirmation before deleting
		miDelete.setOnAction(event -> {
			bDelete = true;
			bRename = false;
			btnConfirm.setText("¿Seguro?");
			btnConfirm.getStyleClass().add("btn-delete-confirm");

			this.getChildren().remove(mbtnMenu);
			this.getChildren().add(btnConfirm);
			this.getChildren().add(btnCancel);
		});

		/*
		 * If we are deleting, we just delete the library.
		 * 
		 * If we are renaming, we rename it and show the correct components
		 */
		btnConfirm.setOnAction(event -> {
			if (bDelete) {
				deleteLibrary(library);
			} else if (bRename) {
				renameLibrary();

				this.getChildren().add(btnLibrary);
				this.getChildren().add(mbtnMenu);
				this.getChildren().remove(txfLibraryName);
				this.getChildren().remove(btnConfirm);
				this.getChildren().remove(btnCancel);
			}

			bRename = false;
			bDelete = false;
		});

		// The action is cancelled
		btnCancel.setOnAction(event -> {
			if (bDelete) {
			} else if (bRename) {
				if (!txfLibraryName.getText().equals(library.getName()))
					renameLibrary();

				this.getChildren().add(btnLibrary);
				this.getChildren().remove(txfLibraryName);
			}

			bRename = false;
			bDelete = false;

			this.getChildren().add(mbtnMenu);
			this.getChildren().remove(btnConfirm);
			this.getChildren().remove(btnCancel);
		});

	}

	/*
	 * Renames the library
	 */
	private void renameLibrary() {
		if (!txfLibraryName.getText().equals(library.getName()))
			library.setName(txfLibraryName.getText());

		controller.reload();
	}

	/*
	 * Deletes a library
	 */
	private void deleteLibrary(Library library) {
		FileUtils.deleteFolder(library.getPath(), true);
		FileUtils.deleteFolder(Utils.folderPath + System.getProperty("file.separator") + library.getId(), false);

		controller.reload();
	}

}
