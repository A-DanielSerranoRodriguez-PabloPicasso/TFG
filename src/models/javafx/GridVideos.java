package models.javafx;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import models.AbstractController;
import models.Video;

public class GridVideos extends GridPane {

	private List<Video> videos;
	private AbstractController controller;

	public GridVideos(AbstractController controller) {
		this.controller = controller;
		setup();
	}

	public GridVideos(List<Video> videos, AbstractController controller) {
		this.videos = videos;
		this.controller = controller;
		setup();
		initialize();
	}

	private void setup() {
		this.setPadding(new Insets(30));
		this.setHgap(10);
		this.setVgap(10);
		this.getStyleClass().add("topBar");
		this.getStyleClass().add("slightRadius");
	}

	private void initialize() {
		for (int i = 0, s = videos.size(); i < s; i++)
			this.add(new LineVideo(videos.get(i), controller), 0, i);
	}

	public void reload(List<Video> videos) {
		this.videos = videos;
		clear();
		initialize();
	}

	public void clear() {
		this.getChildren().clear();
	}
}
