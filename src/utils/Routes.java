package utils;

import java.util.HashMap;
import java.util.Map;

public class Routes {

	private static Map<String, String> routes;

	public static void fillRoutes() {
		routes = new HashMap<>();
		String baseRoute = "/grabberApp/javafx/fxmls/";
		routes.put("root", baseRoute + "Root.fxml");
		routes.put("basic", baseRoute + "Basic.fxml");
		routes.put("blank", baseRoute + "Blank.fxml");
		routes.put("fl-preparing", baseRoute + "popup/Preparing.fxml");
		routes.put("fl-prepared", baseRoute + "popup/Primed.fxml");
		routes.put("landpage", baseRoute + "LandPage.fxml");
	}

	public static String getRoute(String route) {
		return routes.get(route);
	}

}
