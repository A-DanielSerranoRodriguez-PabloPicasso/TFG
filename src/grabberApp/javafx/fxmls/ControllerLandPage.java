package grabberApp.javafx.fxmls;

import java.util.List;

import dao.GLibrary;
import dao.GLibraryImp;
import grabberApp.javafx.fxmls.popups.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
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

		for (Library library : libraries) {
			hBoxLibraries.getChildren().add(new Text(library.getName()));
		}

		hBoxLibraries.getChildren().add(btnAddLibrary);
	}

	private void reloadLibraryList() {
		hBoxLibraries.getChildren().clear();
		try {
			UtilsPopup.page = UtilsPopup.POPUP_PAGE.LIBRARY;
			new Popup().start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		};
//		gLibrary.insert(new Library("/", "Root"));
		libraries = gLibrary.getAll();

		for (Library library : libraries) {
			hBoxLibraries.getChildren().add(new Text(library.getName()));
		}

		hBoxLibraries.getChildren().add(btnAddLibrary);
	}

}
