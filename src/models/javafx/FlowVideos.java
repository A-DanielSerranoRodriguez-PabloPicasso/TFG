package models.javafx;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import models.Video;

public class FlowVideos extends FlowPane {

	private List<Video> videos;

	public FlowVideos() {
		super(Orientation.VERTICAL);
		setup();
	}

	public FlowVideos(List<Video> videos) {
		super(Orientation.VERTICAL);
		this.videos = videos;
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
		for (Video video : videos) {
			this.getChildren().add(new CardVideo(video));
		}
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
