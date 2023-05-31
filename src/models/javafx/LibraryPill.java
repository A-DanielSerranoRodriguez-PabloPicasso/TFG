package models.javafx;

import java.io.File;
import java.util.List;

import dao.GLibraryImp;
import dao.GVideoImp;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import models.AbstractController;
import models.Library;
import models.Video;
import utils.Routes;
import utils.Utils;

public class LibraryPill extends HBox {

	private boolean bDelete, bRename, bConfirm;

	private Library library;
	private AbstractController controller;

	private Button btnLibrary;
	private MenuButton mbtnMenu;
	private MenuItem miRename, miDelete;

	private TextField txfLibraryName;
	private Button btnConfirm, btnCancel;

	public LibraryPill(Library library, AbstractController controller) {
		this.library = library;
		this.controller = controller;

		btnLibrary = new Button(library.getName());
		mbtnMenu = new MenuButton();
		miRename = new MenuItem("Renombrar");
		miDelete = new MenuItem("Eliminar");

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

	private void setBehaviourButtons() {
		btnLibrary.setOnMouseClicked(event -> {
			Utils.selectedLibrary = library;
			controller.gApp.viewSetCenter(Routes.getRoute("library"));
		});

		miRename.setOnAction(event -> {
			bRename = true;
			bDelete = false;
			btnConfirm.setText("Renombrar");

			this.getChildren().remove(btnLibrary);
			this.getChildren().remove(mbtnMenu);
			this.getChildren().add(txfLibraryName);
			this.getChildren().add(btnConfirm);
			this.getChildren().add(btnCancel);
		});

		miDelete.setOnAction(event -> {
			bDelete = true;
			bRename = false;
			btnConfirm.setText("¿Seguro?");

			this.getChildren().remove(mbtnMenu);
			this.getChildren().add(btnConfirm);
			this.getChildren().add(btnCancel);
		});

		btnConfirm.setOnAction(event -> {
			if (bDelete) {
				deleteLibrary(new File(library.getPath()));
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

		btnCancel.setOnAction(event -> {
			if (bDelete) {
			} else if (bRename) {
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

	private void renameLibrary() {
		library.setName(txfLibraryName.getText());

		controller.reload();
	}

	private void deleteLibrary(File folder) {
		String[] content = folder.list();

		if (content != null) {
			for (int i = 0, l = content.length; i < l; i++) {
				String fileName = content[i];
				File file = new File(folder.getAbsolutePath() + System.getProperty("file.separator") + fileName);
				if (file.isDirectory()) {
					List<Video> videos = GVideoImp.getGestor()
							.getByLibrary(GLibraryImp.getGestor().getByPath(folder.getPath()));
					for (Video video : videos) {
						GVideoImp.getGestor().delete(video);
					}
					GLibraryImp.getGestor().delete(GLibraryImp.getGestor().getByPath(library.getPath()));
					deleteLibrary(folder);
				} else {
					GVideoImp.getGestor().delete(
							GVideoImp.getGestor().getByPath(file.getParentFile().getAbsolutePath(), file.getName()));
				}

				file.delete();
			}
		}

		folder.delete();

		GLibraryImp.getGestor().delete(library);

		controller.reload();
	}

}
