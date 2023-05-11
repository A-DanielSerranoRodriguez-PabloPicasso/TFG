package utils;

import java.util.HashMap;
import java.util.Map;

public class Routes {

	private static Map<String, String> routes;

	public static void fillRoutes() {
		routes = new HashMap<>();
		String baseRoute = "/grabberApp/javafx/fxmls/";
		routes.put("root", baseRoute + "Root.fxml");
		routes.put("blank", baseRoute + "Blank.fxml");
		routes.put("landpage", baseRoute + "LandPage.fxml");
		routes.put("fl-preparing", baseRoute + "firstBoot/Preparing.fxml");
		routes.put("fl-prepared", baseRoute + "firstBoot/Primed.fxml");
		routes.put("popup-root", baseRoute + "popups/Root.fxml");
		routes.put("popup-library-create", baseRoute + "popups/library/Create.fxml");
		routes.put("library", baseRoute + "library/Library.fxml");
	}

	public static String getRoute(String route) {
		return routes.get(route);
	}

}
