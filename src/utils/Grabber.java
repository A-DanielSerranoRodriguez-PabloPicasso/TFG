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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.FFDriver;
import models.Library;
import models.Video;
import models.javafx.CustomMenuItem;

public class Grabber extends Thread {
//public class Grabber {
	private String url, outputFolder, videoName;
	private GVideo<Video> gVideo;
	private CustomMenuItem cmi;

	public Grabber(String url, String outputFolder, GVideo<Video> gVideo, String videoName, CustomMenuItem cmi) {
		this.url = url;
		this.outputFolder = outputFolder;
		this.gVideo = gVideo;
		this.videoName = videoName;
		this.cmi = cmi;
	}

	@Override
	public void run() {
//	public void run(CustomMenuItem cmi) {
		String selectorCssButtonUrl = "#gatsby-focus-wrapper > main > section:nth-child(1) > div > div.sm\\:text-center.md\\:max-w-2xl.md\\:mx-auto.lg\\:mx-0.lg\\:col-span-8.lg\\:text-left > div.mt-8.sm\\:mx-auto.sm\\:text-center.lg\\:mx-0.lg\\:text-left > form > button";
		boolean prepared = false, cached = false, downloaded = false;
		File outFolder = new File(outputFolder), fileFolder = new File(outputFolder + "/temp");
		String[] files;
		FFDriver ffDriver = new FFDriver(fileFolder.getAbsolutePath());

		WebDriver driver = ffDriver.getWebDriver();
		WebElement txfUrl, btnUrl, sepConverter, btnDownload, txtDownloadProcess;

		HBox hBox = (HBox) cmi.getContent();

		Label label = (Label) hBox.getChildren().get(0);

		label.setText("Procesando...");

		if (!outFolder.exists())
			outFolder.mkdirs();

		driver.get("https://www.savethevideo.com/es/home");
		String auida = driver.getWindowHandle();

		try {

			txfUrl = findElement(driver, By.cssSelector("#url"));

			for (int i = 0, l = url.length(); i < l; i++) {
				closeUnwanted(auida, driver);
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

			label.setText("Esperando descarga");

			closeUnwanted(auida, driver);

			while (!prepared)
				try {
					closeUnwanted(auida, driver);
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

			sepConverter = findElement(driver, By.id("react-tabs-2"));

			sepConverter.click();

			Select selectFormat = new Select(findElement(driver, By.id("convert-format")));
			selectFormat.selectByValue("mp4");

			btnDownload = findElement(driver, By.cssSelector("a.flex:nth-child(3)"));
			txtDownloadProcess = driver.findElement(By.cssSelector("p.text-sm:nth-child(4)"));

			Thread.sleep(100);

			label.setText("Procesando descarga");

			if (!btnDownload.getText().matches(".*(Descargar|Download).*")
					|| !btnDownload.getText().matches(".*(Haga click|click|Click).*")) {
				btnDownload.click();
				closeUnwanted(auida, driver);

				while (!cached) {
					closeUnwanted(auida, driver);
					try {
						if (!txtDownloadProcess.getText().matches(".*(A partir|%).*"))
							cached = true;
						Thread.sleep(100);
					} catch (Exception e) {
						cached = true;
					}
				}
			}

			if (!fileFolder.exists())
				fileFolder.mkdir();

			btnDownload.click();
			closeUnwanted(auida, driver);

			label.setText("Descargando");

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
					Thread.sleep(1000);

				downloaded = finished;

				files = fileFolder.list();
			}

			File file = new File(fileFolder.getAbsolutePath() + "/" + files[0]);
			Files.move(file, new File(outputFolder + "/" + videoName + ".mp4"));

			Library library = GLibraryImp.getGestor().getByPath(outputFolder);

			label.setText(videoName);

			Video video = new Video(1, videoName, videoName + ".mp4", library, url, true,
					Instant.now().getEpochSecond());

			cmi.setVideo(video);

			gVideo.insert(video);

			fileFolder.delete();
		} catch (Exception e) {
			e.printStackTrace();
			driver.quit();
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
