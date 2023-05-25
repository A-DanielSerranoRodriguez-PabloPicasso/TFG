package grabberApp.javafx.fxmls.popups.video;

import javafx.fxml.FXML;
import javafx.scene.media.MediaView;
import models.AbstractPopupController;

public class ControllerVideo extends AbstractPopupController {

	@FXML
	private MediaView mvPane;

	public ControllerVideo() {
	}

	public void initialize() {
		// TODO averiguar porque intenta ir a traves de la raiz del proyecto
		// TODO Usar reproductor externo (VLC)
//		File file = new File("file:" + UtilsPopup.videoToPlay.getPath());
//		System.out.println(file.toURI().toString());
//		System.out.println(UtilsPopup.videoToPlay.getPath());
//		try {
//			System.out.println(file.toURI().toURL().toExternalForm());
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		MediaPlayer mp = null;
//		mp = new MediaPlayer(new Media(file.toPath().toString()));
//		mvPane.setMediaPlayer(mp);
//		mp.play();
	}

}
