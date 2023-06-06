package models.javafx;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import models.Video;

/**
 * FlowPane extension to setup how the videos are displayed automatically
 * 
 * @author Daniel Serrano Rodriguez
 */
public class FlowVideos extends FlowPane {

	private List<Video> videos;

	/**
	 * Empty constructor
	 */
	public FlowVideos() {
		super(Orientation.VERTICAL);
		setup();
	}

	/**
	 * Constructor with the videos to display
	 * 
	 * @param videos List<Video>
	 */
	public FlowVideos(List<Video> videos) {
		super(Orientation.VERTICAL);
		this.videos = videos;
		setup();
		initialize();
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
	 * Initalizes the pane
	 */
	private void initialize() {
		for (Video video : videos) {
			this.getChildren().add(new CardVideo(video));
		}
	}

	/**
	 * Reloads the pane with new videos
	 * 
	 * @param videos List<Video>
	 */
	public void reload(List<Video> videos) {
		this.videos = videos;
		clear();
		initialize();
	}

	/**
	 * Clears the pane
	 */
	public void clear() {
		this.getChildren().clear();
	}

}
