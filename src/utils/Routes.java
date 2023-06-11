package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Class designed to grant access to all the views of the app
 * 
 * @author Daniel Serrano Rodriguez
 */
public class Routes {

	/**
	 * Map with all the routes
	 */
	private static Map<String, String> routes;

	/**
	 * Fills the map with the application routes
	 */
	private static void fillRoutes() {
		routes = new HashMap<>();
		String baseRoute = "/grabberApp/javafx/fxmls/";
		routes.put("root", baseRoute + "Root.fxml");
		routes.put("topBar", baseRoute + "TopBar.fxml");
		routes.put("blank", baseRoute + "Blank.fxml");
		routes.put("landpage", baseRoute + "LandPage.fxml");
		routes.put("fl-preparing", baseRoute + "firstBoot/Preparing.fxml");
		routes.put("fl-prepared", baseRoute + "firstBoot/Primed.fxml");
		routes.put("popup-root", baseRoute + "popups/Root.fxml");
		routes.put("popup-setup", baseRoute + "popups/setup/Setup.fxml");
		routes.put("popup-library-create", baseRoute + "popups/library/Create.fxml");
		routes.put("popup-library-select", baseRoute + "popups/library/Select.fxml");
		routes.put("popup-download", baseRoute + "popups/download/Download.fxml");
//		routes.put("popup-download-progress", baseRoute + "popups/download/Progress.fxml");
//		routes.put("popup-video", baseRoute + "popups/video/Video.fxml");
		routes.put("popup-error", baseRoute + "popups/error/Error.fxml");
//		routes.put("popup-error-no-vlc", baseRoute + "popups/error/NoVLC.fxml");
		routes.put("popup-warn-video-exists", baseRoute + "popups/warn/VideoExists.fxml");
		routes.put("library", baseRoute + "library/Library.fxml");
	}

	/**
	 * Returns the route of a view
	 * 
	 * @param route String with the unique name of the view
	 * @return String with the route
	 */
	public static String getRoute(String route) {
		if (routes == null)
			fillRoutes();

		return routes.get(route);
	}

}
