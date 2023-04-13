package grabberApp.javafx.fxmls;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import grabberApp.GrabberApp;
import grabberApp.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import utils.AbstractController;
import utils.ArchivoUtils;
import utils.Grabber;

public class BasicController extends AbstractController {

	private GrabberApp gapp;

	private File fs, ds;

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
	private CheckBox cbxArchivoQ;

	@FXML
	private TextField txfArchivo;

	@FXML
	private TextField txfUrl;

	@FXML
	private TextField txfDestinationDirectory;

	@FXML
	private Button btnSelArchivo;

	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnAcceder;

	public BasicController() {
		this.gapp = Util.gapp;
	}

	@FXML
	public void initialize() {
		cbxArchivoQ.setSelected(false);
		apArchivo.setVisible(false);
	}

	@FXML
	private void handleCbxArchivoQ() {
		if (cbxArchivoQ.isSelected()) {
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
		txfArchivo.setText(fs.getAbsolutePath());
	}

	@FXML
	private void handleBtnDestination() {
		DirectoryChooser dc = new DirectoryChooser();
		ds = dc.showDialog(gapp.getStage());
		txfDestinationDirectory.setText(ds.getAbsolutePath());
	}

	@FXML
	private void handleBtnAceptar() {
		if (txfDestinationDirectory.getText().isEmpty()) {
			System.out.println("Ruta de descarga obligatoria");
		} else {
			System.out.println("ruta de descarga: " + txfDestinationDirectory.getText());
			if (cbxArchivoQ.isSelected()) {
				int nThreads = ArchivoUtils.getNumberOfLines(txfArchivo.getText());
				List<String> lines = ArchivoUtils.getLines(txfArchivo.getText());
				System.out.println("elegiste archivo: " + txfArchivo.getText());
				Thread[] threads = new Thread[nThreads];

				for (int i = 0; i < nThreads; i++) {
					threads[i] = new Grabber(lines.get(i), txfDestinationDirectory.getText(), i);
				}

				for (int i = 0; i < nThreads; i++) {
					threads[i].start();
				}
			} else {
				System.out.println("elegiste url: " + txfUrl.getText());
				Thread thread = new Grabber(txfUrl.getText(), txfDestinationDirectory.getText(), 0);
				thread.start();
			}
		}
	}

	@FXML
	private void handleBtnAcceder() {
		gApp.viewSwapMiddle("/grabberApp/javafx/fxmls/Blank.fxml");
	}

}
