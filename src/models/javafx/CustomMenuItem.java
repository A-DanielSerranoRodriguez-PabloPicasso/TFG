package models.javafx;

import javafx.scene.Node;
import models.Video;

public class CustomMenuItem extends javafx.scene.control.CustomMenuItem {

	private Video video;

	public CustomMenuItem() {
		super();
	}

	public CustomMenuItem(Video video) {
		this.video = video;
	}
	
	public CustomMenuItem(Node node) {
		super(node);
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
