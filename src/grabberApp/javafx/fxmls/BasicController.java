package grabberApp.javafx.fxmls;

import java.awt.Checkbox;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import grabberApp.GrabberApp;
import grabberApp.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class BasicController {

	private GrabberApp gapp;

	private File fs;

	@FXML
	private AnchorPane apArchivo;

	@FXML
	private AnchorPane apUrl;

	@FXML
	private Label lblTitle;

	@FXML
	private Label lblArchivoQ;

	@FXML
	private Label lblArchivo;

	@FXML
	private Label lblUrl;

	@FXML
	private Checkbox cbxArchivoQ;

	@FXML
	private TextField txfArchivo;

	@FXML
	private TextField txfUrl;

	@FXML
	private Button btnSelArchivo;

	@FXML
	private Button btnAceptar;

	public BasicController() {
		this.gapp = Util.gapp;
	}

	@FXML
	public void initialize() {
//		apArchivo.setVisible(false);
		cbxArchivoQ.setState(false);
	}

	@FXML
	private void handleCbxArchivoQ() {
		if (cbxArchivoQ.getState()) {
			apArchivo.setVisible(true);
			apUrl.setVisible(false);
			txfUrl.clear();
		} else {
			apArchivo.setVisible(false);
			apUrl.setVisible(true);
			txfArchivo.clear();
			fs = null;
		}
	}

	@FXML
	private void handleBtnArchivo() {
		List<String> lista = new ArrayList<>();
		lista.add("txt");
		ExtensionFilter ef = new ExtensionFilter("Seleccionables", lista);
		FileChooser fc = new FileChooser();
		fc.setSelectedExtensionFilter(ef);
		fs = fc.showOpenDialog(gapp.getStage());
	}

	@FXML
	private void handleBtnAceptar() {
		if (cbxArchivoQ.getState())
			System.out.println("elegiste archivo: " + fs.getAbsolutePath());
		else
			System.out.println("elegiste url");
	}

}
