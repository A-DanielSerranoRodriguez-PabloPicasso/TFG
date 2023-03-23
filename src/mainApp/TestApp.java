package mainApp;

import java.io.File;

import org.openqa.selenium.firefox.FirefoxDriver;

public class TestApp {
	public static void main(String[] args) {
//		Gestor<Generales> gestor = new GGenerales();
//		Generales general = new Generales("https://es.pornhub.com/view_video.php?viewkey=ph628b6da0c2ec8");
//		List<Generales> generales;
//
//		gestor.insert(general);
//		generales = gestor.getByNotDownloaded();
//
//		for (Generales generales2 : generales) {
//			System.out.println(generales2.getUrl());
//		}
		
		System.out.println(new File("resources/web/uBlock0_1.46.1b8.firefox.signed.xpi").exists());
		
		FirefoxDriver fDriver = new FirefoxDriver();
		fDriver.getTitle();
	}
}
