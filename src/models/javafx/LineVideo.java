package models.javafx;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.GVideo;
import dao.GVideoImp;
import grabberApp.javafx.fxmls.popups.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.AbstractController;
import models.Library;
import models.Video;
import utils.Utils;
import utils.UtilsPopup;

public class LineVideo extends AnchorPane {

	private boolean videoExists;

	private AbstractController controller;
	private Video video;
	private HBox hbContent, hbEdit, hbDelete;
	private Label lblName, lblLibrary, lblLibraryTree, lblDate;
	private Button btnEdit, btnDelete, btnAcceptEdit, btnCancelEdit, btnAcceptDelete, btnCancelDelete;
	private TextField txfName;
	private ChoiceBox<String> choiceLibrary;

	public LineVideo(Video video, AbstractController controller) {
		this.video = video;
		this.controller = controller;
		videoExists = video.getVideo().exists();

		hbContent = new HBox(10);
		hbContent.setAlignment(Pos.CENTER);

		lblName = new Label(video.getName());
		lblLibrary = new Label(video.getLibrary().getName() + ":");
		lblLibraryTree = new Label(video.getLibrary().getTree());
		Date date = new Date(video.getDateCreated() * 1000);
		DateFormat df = new SimpleDateFormat("dd/M/yyyy H:mm");
		lblDate = new Label(df.format(date));
		btnEdit = new Button("Editar");
		btnDelete = new Button("Eliminar");

		lblName.setOnMouseClicked(event -> {
			// TODO alerta video no disponible
			if (videoExists) {
				video.watch();
			}
		});

		hbContent.getChildren().add(lblName);
		hbContent.getChildren().add(lblLibrary);
		hbContent.getChildren().add(lblLibraryTree);
		hbContent.getChildren().add(lblDate);
		hbContent.getChildren().add(btnEdit);
		hbContent.getChildren().add(btnDelete);

		// TODO alerta video no disponible
		if (!videoExists)
			btnEdit.setDisable(true);

		hbEdit = new HBox(10);
		hbEdit.setAlignment(Pos.CENTER);

		txfName = new TextField(video.getName());
		choiceLibrary = new ChoiceBox<>();
		btnAcceptEdit = new Button("Confirmar cambios");
		btnCancelEdit = new Button("Cancelar");

		hbEdit.getChildren().add(txfName);
		hbEdit.getChildren().add(new LibrarySearcher(video.getLibrary()));
		hbEdit.getChildren().add(btnAcceptEdit);
		hbEdit.getChildren().add(btnCancelEdit);

		hbDelete = new HBox(10);
		hbDelete.setAlignment(Pos.CENTER);

		btnAcceptDelete = new Button("¿Seguro?");
		btnCancelDelete = new Button("Cancelar");

		hbDelete.getChildren().add(btnAcceptDelete);
		hbDelete.getChildren().add(btnCancelDelete);

		buttonFunctions();

		ObservableList<String> olLibraries = FXCollections.observableArrayList();

		List<Library> libraries = Utils.libraries;

		for (Library library : libraries) {
			olLibraries.add(library.getName());
		}

		choiceLibrary.setItems(olLibraries);

		choiceLibrary.getSelectionModel().select(video.getLibrary().getName());

		this.getChildren().add(hbContent);
		this.setVisible(true);
	}

	private void buttonFunctions() {
		btnEdit.setOnAction(event -> {
			this.getChildren().remove(hbContent);
			hbEdit.getChildren().add(2, lblDate);
			this.getChildren().add(hbEdit);
		});

		btnDelete.setOnAction(event -> {
			this.getChildren().remove(hbContent);
			hbDelete.getChildren().add(0, lblName);
			hbDelete.getChildren().add(1, lblLibrary);
			hbDelete.getChildren().add(2, lblLibraryTree);
			hbDelete.getChildren().add(3, lblDate);
			this.getChildren().add(hbDelete);
		});

		btnAcceptEdit.setOnAction(event -> {
			Library library = UtilsPopup.selectedLibrary == null ? video.getLibrary() : UtilsPopup.selectedLibrary;
			File newFile = new File(
					library.getPath() + System.getProperty("file.separator") + txfName.getText() + ".mp4");

			if (txfName.getText().isEmpty()) {
				UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
				UtilsPopup.errType = UtilsPopup.ERR_TYPE.VIDEO_NAME_EMPTY;

				try {
					new Popup().start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (newFile.exists()) {
				UtilsPopup.page = UtilsPopup.POPUP_PAGE.ERR;
				UtilsPopup.errType = UtilsPopup.ERR_TYPE.VIDEO_EXISTS;

				try {
					new Popup().start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				this.getChildren().remove(hbEdit);
				GVideo<Video> gVideo = GVideoImp.getGestor();

				video.setName(txfName.getText());
				while (!video.getVideo().getName().equals(video.getFileName())) {
				}

				if (!video.getLibrary().equals(library)) {
					video.moveVideo(library);
					video.setLibrary(library);
				}

				gVideo.update(video);
				controller.reload();
				lblName.setText(video.getName());
				lblLibrary.setText(video.getLibrary().getName());
				hbContent.getChildren().add(3, lblDate);
				this.getChildren().add(hbContent);
			}
		});

		btnCancelEdit.setOnAction(event -> {
			this.getChildren().remove(hbEdit);
			hbContent.getChildren().add(3, lblDate);
			this.getChildren().add(hbContent);
		});

		btnAcceptDelete.setOnAction(event -> {
			this.getChildren().remove(hbDelete);
			GVideo<Video> gVideo = GVideoImp.getGestor();
			gVideo.delete(video);
			controller.reload();
			this.getChildren().add(hbContent);
		});

		btnCancelDelete.setOnAction(event -> {
			this.getChildren().remove(hbDelete);
			hbContent.getChildren().add(0, lblName);
			hbContent.getChildren().add(1, lblLibrary);
			hbContent.getChildren().add(2, lblLibraryTree);
			hbContent.getChildren().add(3, lblDate);
			this.getChildren().add(hbContent);
		});
	}

}
