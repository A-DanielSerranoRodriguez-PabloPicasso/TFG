package grabberApp.javafx.fxmls;

import java.util.List;

import dao.GLibrary;
import dao.GLibraryImp;
import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
import utils.Routes;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerLandPage extends AbstractController {

	private List<Library> libraries;

	@FXML
	private HBox hBoxLibraries;

	@FXML
	private Button btnAddLibrary;

	/*
	 * TODO
	 * 
	 * - crear lista bibliotecas generales
	 * 
	 * - crear lista recientes de cada biblioteca
	 * 
	 * - permitir usuario esconder listas
	 */

	public ControllerLandPage() {
		gApp = Utils.gApp;
		gLibrary = GLibraryImp.gestor();

		btnAddLibrary = new Button("Añadir");
		btnAddLibrary.setOnMousePressed(event -> {
			reloadLibraryList();
		});
	}

	public void initialize() {
		GLibrary<Library> gLibrary = GLibraryImp.gestor();
		libraries = gLibrary.getAll();

		fillLibraries();

		hBoxLibraries.getChildren().add(btnAddLibrary);
	}

	private void reloadLibraryList() {
		try {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.LIBRARY;
			new Popup().start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		hBoxLibraries.getChildren().clear();
		libraries = gLibrary.getAll();

		fillLibraries();

		hBoxLibraries.getChildren().add(btnAddLibrary);
	}

	private void fillLibraries() {
		Button btnLibrary;
		for (Library library : libraries) {
			btnLibrary = new Button(library.getName());
			hBoxLibraries.getChildren().add(btnLibrary);
			btnLibrary.setOnMouseClicked(event -> {
				Utils.selectedLibrary = library;
				gApp.viewSetCenter(Routes.getRoute("library"));
			});
		}
	}

}
