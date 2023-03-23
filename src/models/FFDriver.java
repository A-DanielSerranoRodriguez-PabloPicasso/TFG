package models;

import org.openqa.selenium.WebDriver;

import utils.Profiler;

public class FFDriver {

	private WebDriver webDriver;

	public FFDriver(String path) {
		webDriver = Profiler.setFirefoxOptions(path);
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

}
