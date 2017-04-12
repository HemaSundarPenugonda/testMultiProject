package nl.coin.Selenium;

import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WaitFunctions {
	private By by;

	public WaitFunctions(By by) {
		this.by = by;
	}

	Function<WebDriver, Boolean> functionIsDisplayed = new Function<WebDriver, Boolean>() {
		@Override
		public Boolean apply(WebDriver driver) {
			return driver.findElement(by).isDisplayed();
		}
	};

	
}
