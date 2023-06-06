package models.javafx;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import models.AbstractController;
import models.Video;

/**
 * GridPane extension to setup how the videos are displayed automatically
 * 
 * @author Daniel Serrano Rodriguez
 */
public class GridVideos extends GridPane {

	private List<Video> videos;
	private AbstractController controller;

	/**
	 * Constructor with just the controller to assign the pane
	 * 
	 * @param controller AbstractController
	 */
	public GridVideos(AbstractController controller) {
		this.controller = controller;
		setup();
	}

	/**
	 * Constructor with the videos and controller
	 * 
	 * @param videos     List<Video>
	 * @param controller AbstractController
	 */
	public GridVideos(List<Video> videos, AbstractController controller) {
		this.videos = videos;
		this.controller = controller;
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
	 * Initializes the pane in the view
	 */
	private void initialize() {
		for (int i = 0, s = videos.size(); i < s; i++)
			this.add(new LineVideo(videos.get(i), controller), 0, i);
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
