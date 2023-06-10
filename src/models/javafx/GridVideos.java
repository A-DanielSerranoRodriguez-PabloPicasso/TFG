package models.javafx;

import java.util.List;

import dao.GVideoImp;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.AbstractController;
import models.Library;
import models.Video;
import utils.ImgUtils;

/**
 * GridPane extension to setup how the videos are displayed automatically
 * 
 * @author Daniel Serrano Rodriguez
 */
public class GridVideos extends GridPane {

	private Boolean ordered, ordName, ordDate;
	private List<Video> videos;
	private Library library;
	private AbstractController controller;
	private HBox buttons;
	private Button btnOrdName, btnOrdDate, btnClearOrd, btnOrder;

	/**
	 * Constructor with just the controller to assign the pane
	 * 
	 * @param controller AbstractController
	 */
	public GridVideos(AbstractController controller, Library library) {
		this.controller = controller;
		this.library = library;
		ordered = false;
		ordName = false;
		ordDate = false;
		setup();
	}

	/**
	 * Sets up the pane
	 */
	private void setup() {
		this.setPadding(new Insets(30));
		this.setHgap(10);
		this.setVgap(10);
		this.getStyleClass().add("topBar");
		this.getStyleClass().add("slightRadius");
	}

	/**
	 * Initializes the pane in the view
	 */
	private void initialize() {
		buttons = new HBox(10);
		initButtons();
		initImages();
		this.add(buttons, 0, 0);
		for (int i = 1, s = videos.size(); i < s + 1; i++)
			this.add(new LineVideo(videos.get(i - 1), controller), 0, i);
	}

	/**
	 * Initializes the images
	 */
	private void initImages() {
		ImageView imgView = new ImageView(ImgUtils.getInternalImage("/img/icon/x.png"));
		if (ordered) {
			btnOrder.setVisible(true);
			
			if (ordName || ordDate)
				imgView = new ImageView(ImgUtils.getInternalImage("/img/icon/arrow_down.png"));
			else
				imgView = new ImageView(ImgUtils.getInternalImage("/img/icon/arrow_up.png"));
		} else
			btnOrder.setVisible(false);

		imgView.setFitWidth(32);
		imgView.setFitHeight(32);
		btnOrder.setGraphic(imgView);
	}

	/**
	 * Initializes the buttons
	 */
	private void initButtons() {
		btnOrdName = new Button();
		btnOrdDate = new Button();
		btnClearOrd = new Button();
		btnOrder = new Button();
		btnOrder.setDisable(true);
		btnOrder.setVisible(false);

		btnClearOrd.setDisable(true);

		ImageView imgView = new ImageView(ImgUtils.getInternalImage("/img/icon/sort_alpha.png"));
		imgView.setFitWidth(32);
		imgView.setFitHeight(32);
		btnOrdName.setGraphic(imgView);

		imgView = new ImageView(ImgUtils.getInternalImage("/img/icon/sort_date.png"));
		imgView.setFitWidth(32);
		imgView.setFitHeight(32);
		btnOrdDate.setGraphic(imgView);

		imgView = new ImageView(ImgUtils.getInternalImage("/img/icon/x.png"));
		imgView.setFitWidth(32);
		imgView.setFitHeight(32);
		btnClearOrd.setGraphic(imgView);

		imgView = new ImageView(ImgUtils.getInternalImage("/img/icon/x.png"));
		imgView.setFitWidth(32);
		imgView.setFitHeight(32);
		initImages();

		buttons.getChildren().add(btnClearOrd);
		buttons.getChildren().add(btnOrdName);
		buttons.getChildren().add(btnOrdDate);
		buttons.getChildren().add(btnOrder);

		if (ordered)
			btnClearOrd.setDisable(false);

		setButtonBehaviour();
	}

	/**
	 * Sets up the button behaviour
	 */
	private void setButtonBehaviour() {
		btnClearOrd.setOnAction(event -> {
			ordered = false;
			ordName = false;
			ordDate = false;

			btnClearOrd.setDisable(true);

			reload(GVideoImp.getGestor().getByLibrary(library));
		});

		btnOrdName.setOnAction(event -> {
			if (!ordered) {
				ordered = true;
				btnClearOrd.setDisable(false);
			}

			ordDate = false;

			ordName = !ordName;
			reload(GVideoImp.getGestor().getByLibraryOrderedName(library.getId(), ordName));
		});

		btnOrdDate.setOnAction(event -> {
			if (!ordered) {
				ordered = true;
				btnClearOrd.setDisable(false);
			}

			ordName = false;

			ordDate = !ordDate;
			reload(GVideoImp.getGestor().getByLibraryOrderedDate(library.getId(), ordDate));
		});
	}

	/**
	 * Reloads the view with the new videos
	 * 
	 * @param videos List<Video>
	 */
	public void reload(List<Video> videos) {
		this.videos = videos;
		clear();
		initialize();
	}

	/**
	 * Clears all the videos of the view
	 */
	public void clear() {
		this.getChildren().clear();
	}
}
