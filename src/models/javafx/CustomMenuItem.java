package models.javafx;

import javafx.scene.Node;
import models.Video;

/**
 * CustomMenuItem extension to allow it to have a video
 * 
 * @author Daniel Serrano Rodriguez
 */
public class CustomMenuItem extends javafx.scene.control.CustomMenuItem {

	private Video video;

	/**
	 * Empty constructor
	 */
	public CustomMenuItem() {
		super();
	}

	/**
	 * Constructor with video
	 * 
	 * @param video Video
	 */
	public CustomMenuItem(Video video) {
		this.video = video;
	}

	/**
	 * Constructor with a node
	 * 
	 * @param node Node
	 */
	public CustomMenuItem(Node node) {
		super(node);
	}

	/**
	 * Retrieves the video
	 * 
	 * @return Video
	 */
	public Video getVideo() {
		return video;
	}

	/**
	 * Sets the video
	 * 
	 * @param video Video
	 */
	public void setVideo(Video video) {
		this.video = video;
	}

}
