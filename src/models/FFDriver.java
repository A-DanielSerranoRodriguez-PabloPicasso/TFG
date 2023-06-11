package models;

import org.openqa.selenium.WebDriver;

import utils.Profiler;

/**
 * Firefox Driver
 * 
 * @author Daniel Serrano Rodriguez
 */
public class FFDriver {

	private WebDriver webDriver;

	/**
	 * Constructor of a driver
	 * 
	 * @param path String
	 */
	public FFDriver(String path) {
		webDriver = Profiler.setFirefoxOptions(path);
	}

	/**
	 * Returns the webdriver
	 * 
	 * @return WebDriver
	 */
	public WebDriver getWebDriver() {
		return webDriver;
	}

}
