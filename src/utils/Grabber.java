package utils;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.common.io.Files;

import dao.GLibraryImp;
import dao.GVideo;
import dao.GVideoImp;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.FFDriver;
import models.Library;
import models.Video;
import models.javafx.CustomMenuItem;

/**
 * Class that automates the download of videos
 * 
 * @author Daniel Serrano Rodriguez
 */
public class Grabber {
//public class Grabber extends Thread {
	private String url, outputFolder, videoName;
	private GVideo<Video> gVideo;
	private CustomMenuItem cmi;
	private boolean newVideo;

	/**
	 * Constructor that receives the url, the folder, the video name and the
	 * download menu component.S
	 * 
	 * @param url          String
	 * @param outputFolder String
	 * @param videoName    String
	 * @param cmi          CustomMenuItem (models.javafx)
	 * @param newVideo     boolean
	 */
	public Grabber(String url, String outputFolder, String videoName, CustomMenuItem cmi, boolean newVideo) {
		this.url = url;
		this.outputFolder = outputFolder;
		this.gVideo = GVideoImp.getGestor();
		this.videoName = videoName;
		this.cmi = cmi;
		this.newVideo = newVideo;
	}

//	@Override
	public void run() {
		// Download button
		String selectorCssButtonUrl = "#gatsby-focus-wrapper > main > section:nth-child(1) > div > div.sm\\:text-center.md\\:max-w-2xl.md\\:mx-auto.lg\\:mx-0.lg\\:col-span-8.lg\\:text-left > div.mt-8.sm\\:mx-auto.sm\\:text-center.lg\\:mx-0.lg\\:text-left > form > button";
		// Checkers of the download progress
		boolean prepared = false, cached = false, downloaded = false;
		// Output
		File outFolder = new File(outputFolder), fileFolder = new File(outputFolder + "/temp");
		String[] files;
		FFDriver ffDriver = new FFDriver(fileFolder.getAbsolutePath());

		UtilsPopup.page = UtilsPopup.POPUP_PAGE.DOWNLOAD_PROGRESS;

		WebDriver driver = ffDriver.getWebDriver();
		WebElement txfUrl, btnUrl, sepConverter, btnDownload, txtDownloadProcess, imgMiniature;

		HBox hBox = (HBox) cmi.getContent();

		Label label = (Label) hBox.getChildren().get(0);

		if (!outFolder.exists())
			outFolder.mkdirs();

		// We access the download page and obtain the title
		driver.get("https://www.savethevideo.com/es/home");
		String pageHandler = driver.getWindowHandle();

		try {
			// We get the url text input and send the characters
			txfUrl = findElement(driver, By.cssSelector("#url"));

			/*
			 * The characters are sent one by one every 10 milliseconds due to the
			 * interaction between Selenium and JavaScript.
			 * 
			 * If a tab is opened, we return to the correct one.
			 */
			for (int i = 0, l = url.length(); i < l; i++) {
				closeUnwanted(pageHandler, driver);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					driver.quit();
				}

				txfUrl.sendKeys(url.charAt(i) + "");
			}

			btnUrl = findElement(driver, By.cssSelector(selectorCssButtonUrl));
			btnUrl.click();

			closeUnwanted(pageHandler, driver);

			/*
			 * Whilst the download modal hasn't appeared (the loading svg inserted in the
			 * button is there), we lock the process
			 */
			while (!prepared)
				try {
					closeUnwanted(pageHandler, driver);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					btnUrl.findElement(By.name("svg"));
				} catch (Exception e) {
					prepared = true;
					Thread.sleep(1000);
				}

			// We select the conversion tab that allows us to download any video with ease
			sepConverter = findElement(driver, By.id("react-tabs-2"));

			sepConverter.click();

			// If the video has a miniature, we capture it
			imgMiniature = findElement(driver, By.cssSelector("img.w-full"));
			String urlMiniature = imgMiniature != null ? imgMiniature.getAttribute("src") : "";

			// We select the download format we want for the video and download it
			Select selectFormat = new Select(findElement(driver, By.id("convert-format")));
			selectFormat.selectByValue("mp4");

			btnDownload = findElement(driver, By.cssSelector("a.flex:nth-child(3)"));
			txtDownloadProcess = driver.findElement(By.cssSelector("p.text-sm:nth-child(4)"));

			Thread.sleep(100);

			/*
			 * We click the button to start the download, and whilst the download label
			 * indicates that we still have percentage to go, we lock the process
			 */
			if (!btnDownload.getText().matches(".*(Descargar|Download).*")
					|| !btnDownload.getText().matches(".*(Haga click|click|Click).*")) {
				btnDownload.click();
				closeUnwanted(pageHandler, driver);

				while (!cached) {
					closeUnwanted(pageHandler, driver);
					try {
						if (!txtDownloadProcess.getText().matches(".*(A partir|%).*"))
							cached = true;
						Thread.sleep(100);
					} catch (Exception e) {
						cached = true;
					}
				}
			}

			// We create a temporary folder to make downloads more accessible
			if (!fileFolder.exists())
				fileFolder.mkdir();

			btnDownload.click();
			closeUnwanted(pageHandler, driver);

			/*
			 * When a download occurs, there is a temporary file created alongside the
			 * downloaded file. Until this temporal file exists, we lock the process;
			 */
			files = fileFolder.list();
			while (!downloaded) {
				boolean finished = true;

				if (files.length != 0)
					for (String f : files) {
						if (f.matches(".*part$"))
							finished = false;
					}
				else
					finished = false;

				if (!finished)
					Thread.sleep(500);

				downloaded = finished;

				files = fileFolder.list();
			}

			/*
			 * Once the temporal file is gone, we move the downloaded file to the correct
			 * folder and insert it in the database
			 */
			File file = new File(fileFolder.getAbsolutePath() + System.getProperty("file.separator") + files[0]);
			Files.move(file, new File(outputFolder + System.getProperty("file.separator") + videoName + ".mp4"));
			fileFolder.delete();

			Library library = GLibraryImp.getGestor().getByPath(outputFolder);
			if (!urlMiniature.isEmpty())
				ImgUtils.downloadImage(urlMiniature, library, videoName);

			label.setText(videoName);
			Video video = null;
			if (newVideo) {
				video = new Video(1, videoName, videoName + ".mp4", library, url, true, Instant.now().getEpochSecond());
				gVideo.insert(video);
			} // else {
//				video = gVideo.getByUrl(url);
//			}
			// The video is set in the CustomMenuItem to be able to watch it from it
			cmi.setVideo(video);

		} catch (Exception e) {
			e.printStackTrace();
			driver.quit();
			try {
				Profiler.removeProfile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {
			Profiler.removeProfile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		driver.quit();
	}

	private WebElement findElement(WebDriver driver, By by) {
		WebElement element = null;

		try {
			element = driver.findElement(by);
		} catch (Exception e) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.getStackTrace();
			}

			element = findElement(driver, by);
		}

		return element;
	}

	private void closeUnwanted(String page, WebDriver driver) {
		if (driver.getWindowHandles().size() > 1) {
			driver.switchTo().window(page);
		}
	}
}
