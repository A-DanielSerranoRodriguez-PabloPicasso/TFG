package models.javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Video;
import utils.ImgUtils;

/**
 * Creates a card with details of the video
 * 
 * @author Daniel Serrano Rodriguez
 */
public class CardVideo extends AnchorPane {

	private Video video;
	private Image videoImage;

	private ImageView imgMiniature;
	private Label lblName;
	private HBox hbBetween;
	private VBox vbContent;

	/**
	 * Constructor that receives a video
	 * 
	 * @param video Video
	 */
	public CardVideo(Video video) {
		this.video = video;
		imgMiniature = new ImageView();
		lblName = new Label();
		hbBetween = new HBox();
		vbContent = new VBox(10);
		setup();
		initialize();
	}

	/**
	 * Sets up how the card is going to display
	 */
	private void setup() {
		this.setMinHeight(400);
		this.getStyleClass().add("blackBackground");
		this.getStyleClass().add("slightRadius");
		vbContent.setAlignment(Pos.TOP_CENTER);
		vbContent.setPadding(new Insets(20));
		AnchorPane.setBottomAnchor(vbContent, 0.0);
		AnchorPane.setLeftAnchor(vbContent, 0.0);
		AnchorPane.setRightAnchor(vbContent, 0.0);
		AnchorPane.setTopAnchor(vbContent, 0.0);

		vbContent.setOnMouseClicked(event -> {
			video.watch();
		});

		vbContent.getChildren().add(imgMiniature);
		vbContent.getChildren().add(hbBetween);
		vbContent.getChildren().add(lblName);

		VBox.setVgrow(hbBetween, Priority.ALWAYS);
		this.getChildren().add(vbContent);
	}

	/**
	 * Initializes the card.
	 * 
	 * The image's height and width are obtained to show the miniature correctly
	 */
	private void initialize() {
		videoImage = ImgUtils.getImage(video.getMiniaturePath());
		imgMiniature.setImage(videoImage);
		hbBetween.setFillHeight(true);
		lblName.setText(video.getName());

		if (videoImage.getHeight() > videoImage.getWidth()) {
			imgMiniature.setFitWidth(200);
			imgMiniature.setFitHeight(300);
		} else if (videoImage.getHeight() < videoImage.getWidth()) {
			imgMiniature.setFitWidth(300);
			imgMiniature.setFitHeight(150);
		} else {
			imgMiniature.setFitWidth(200);
			imgMiniature.setFitHeight(200);
		}
	}

}
