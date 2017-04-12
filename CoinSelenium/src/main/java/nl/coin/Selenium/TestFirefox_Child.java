package nl.coin.Selenium;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;

public class TestFirefox_Child {
	static WebDriver driver;
	public static void main(String[] args) throws IOException {
		System.setProperty("webdriver.firefox.marionette", "false");
		driver = new FirefoxDriver();
		driver.get("http://google.com/");
	}
}
