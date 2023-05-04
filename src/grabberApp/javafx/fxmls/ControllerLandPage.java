package grabberApp.javafx.fxmls;

import java.util.List;

import dao.GLibrary;
import dao.GLibraryImp;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.AbstractController;
import models.Library;
import utils.Utils;

public class ControllerLandPage extends AbstractController {

	@FXML
	private HBox hBoxLibraries;

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
	}

	public void initialize() {
		GLibrary<Library> gLibrary = GLibraryImp.gestor();
		List<Library> libraries = gLibrary.getAll();

		if (libraries.size() == 0) {
			hBoxLibraries.getChildren().add(new Text("Ninguna"));
		}
	}

}
