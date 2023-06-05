package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

/**
 * Class that works with the Firefox profile system and Selenium configuration
 * to set up the Grabber
 * 
 * @author Daniel Serrano Rodriguez
 *
 */
public class Profiler {
	/**
	 * Route of the Firefox profile to be used
	 */
	private static final String profileName = "web/0aqy6coi.selenium-profile";
	private static String profileIniLocation, pilBackup;
	private static int profileCount = 0;

	/**
	 * Writes in the Firefox profile file of the user, creating a backup first
	 */
	public static void writeProfile() {
		File profile, profilesIni, pilCopy;
		String os = System.getProperty("os.name"), username = System.getProperty("user.name"), linea, folderPath = "";
		profileCount = 0;

		if (os.equals("Linux")) {
			folderPath = "/home/" + username + "/.mozilla/firefox/";
		} else if (os.contains("Windows")) {
			folderPath = "C:/Users/" + username + "/AppData/Roaming/Mozilla/Firefox/";
		}

		try {
			profileIniLocation = folderPath + "profiles.ini";
			if (!isWritten()) {
				profile = new File(profileName);
				profilesIni = new File(profileIniLocation);
				pilCopy = new File(profileIniLocation + ".bak");
				pilBackup = pilCopy.getAbsolutePath();
				if (!pilCopy.exists())
					Files.copy(profilesIni.toPath(), pilCopy.toPath());

				BufferedReader br = new BufferedReader(new FileReader(profilesIni));

				while ((linea = br.readLine()) != null)
					if (linea.matches("\\[Profile[0-9]+\\]"))
						profileCount++;

				br.close();

				BufferedWriter bw = new BufferedWriter(new FileWriter(profilesIni, true));

				bw.write("[Profile" + profileCount + "]\n");
				bw.write("Name=selenium-profile\n");
				bw.write("IsRelative=0\n");
				bw.write("Path=" + profile.getAbsolutePath() + "\n");

				bw.flush();
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configures the Firefox driver
	 * 
	 * @param path String with the download path of the file
	 * @return FirefoxDriver
	 */
	public static FirefoxDriver setFirefoxOptions(String path) {
		writeProfile();
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile testprofile = profile.getProfile("selenium-profile");
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		try {
			Thread.sleep(100);
//			if (!isWritten()) {
////				testprofile.addExtension(new File("resources/web/uBlock0_1.46.1b8.firefox.signed.xpi"));
//			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		testprofile.setPreference("browser.download.dir", path);
		// Forces Firefox to download in the specified path directly
		testprofile.setPreference("browser.download.folderList", 2);
		FirefoxOptions fOptions = new FirefoxOptions();

		if (System.getProperty("os.name").contains("Windows")) {
//			FirefoxBinary fBinary = fOptions.getBinary();
//			fBinary.addCommandLineOptions("-headless");
//			fOptions.setBinary(fBinary);
		} else {
//			fOptions.setHeadless(true);
		}

		fOptions.setLogLevel(FirefoxDriverLogLevel.FATAL);

		fOptions.setProfile(testprofile);
		return new FirefoxDriver(fOptions);
	}

	/**
	 * Reverts the changes on the profile file and reverts it to the backup
	 * 
	 * @throws IOException
	 */
	public static void removeProfile() throws IOException {
		File bak = new File(pilBackup), prof = new File(profileIniLocation);
		Path profPath = prof.toPath();
		prof.delete();
		Files.copy(bak.toPath(), profPath);
		new File(pilBackup).delete();
	}

	/**
	 * Checks if the profile file is written with the configuration
	 * 
	 * @return true/false
	 * @throws IOException
	 */
	private static boolean isWritten() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(profileIniLocation)));
		String linea;

		while ((linea = br.readLine()) != null)
			if (linea.matches(profileName)) {
				br.close();
				return true;
			}

		br.close();
		return false;
	}

}
