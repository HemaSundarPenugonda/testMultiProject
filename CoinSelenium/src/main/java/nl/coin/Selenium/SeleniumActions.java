/**
 * 
 */
package nl.coin.Selenium;

import nl.coin.common.UtilitySel;
import nl.coin.dataReader.ReaderUtility;
import nl.coin.reporting.LogFile;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.btr.proxy.selector.direct.NoProxySelector;

import java.io.File;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.HashMap;
import java.util.List;

/**
 * @author hemasundar
 *
 */
public class SeleniumActions implements GUIOperations {
	WebDriver driver;
	WebDriverWait wait;
	ReaderUtility reader = new ReaderUtility();
	List<String[]> orData, credentialsData;
	private String screenShotPath;
	private Logger objLog;

	/**
	 * @throws Exception
	 * 
	 */
	public SeleniumActions() throws Exception {
		reader.setFileLocation();
		orData = reader.getORData();
		credentialsData = reader.getCredentialsData();
		objLog = Logger.getLogger("LOG");
		BasicConfigurator.configure();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#openBrowser()
	 */
	@Override
	public WebDriver openBrowser() throws Exception {
		return openBrowser("chrome");
	}

	public WebDriver openBrowser(String browser) throws Exception {
		try {
			UtilitySel.printMessage(System.getenv("PATH"));
			// ProxySelector.setDefault(null);

			switch (browser) {
			case "firefox":

				ProxySelector.setDefault(NoProxySelector.getInstance());
				UtilitySel.printMessage(ProxySelector.getDefault().toString());

				System.setProperty("webdriver.firefox.marionette", "false");
				driver = new FirefoxDriver();

				break;
			case "chrome":

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver_linux64");
				driver = new ChromeDriver();

				break;

			default:
				driver = new FirefoxDriver();
				break;
			}
			wait = new WebDriverWait(driver, 10);
			driver.manage().window().maximize();
			return driver;
		} catch (Exception e) {
			UtilitySel.printMessage(e.getMessage());
			e.printStackTrace();
			// takeScreenShot();
			// driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#quitBrowser()
	 */
	@Override
	public boolean quitBrowser() throws Exception {
		try {
			if (driver != null) {
				driver.quit();
			}

			return true;
		} catch (Exception e) {
			// takeScreenShot();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#browseURL(java.lang.String)
	 */
	@Override
	public void browseURL(String url) throws Exception {
		try {
			driver.get(url);
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#enterData(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public String enterData(String parent, String locator, String inputText) throws Exception {
		if (inputText == null) {
			return "No content to enter in webElement: " + parent + " . " + locator;
		}
		By byLocator = getLocatorAndReturnBy(parent, locator);

		return enterData(byLocator, inputText);
	}

	public boolean isChecked(String parent, String locator) throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);

		try {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			boolean isChecked = driver.findElement(byLocator).isSelected();
			return isChecked;

		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	public String getTextFieldData(String parent, String locator) throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);

		try {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			String text = driver.findElement(byLocator).getAttribute("value");
			return text;

		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#enterData(org.openqa.selenium.By,
	 * java.lang.String)
	 */
	@Override
	public String enterData(By byLocator, String inputText) throws Exception {
		try {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			driver.findElement(byLocator).clear();
			driver.findElement(byLocator).sendKeys(inputText);
			Thread.sleep(1000);
			return "Entering [" + inputText + "] is successfull in webElement: " + byLocator;
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.coin.Selenium.GUIOperations#enterDataSelectDropDown(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public String enterDataSelectDropDown(String parent, String locator, String selectText) throws Exception {
		if (selectText == null) {

			return "No content passed to select: " + parent + " . " + locator;
		}
		By byLocator = getLocatorAndReturnBy(parent, locator);

		return enterDataSelectDropDown(byLocator, selectText);
	}

	public String getSelectedOption(String parent, String locator) throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);
		try {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			WebElement element = driver.findElement(byLocator);
			Select select = new Select(element);
			Thread.sleep(1000);
			String selectedText = select.getFirstSelectedOption().getText();
			return selectedText;

		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#enterDataSelectDropDown(org.openqa.
	 * selenium.By, java.lang.String)
	 */
	@Override
	public String enterDataSelectDropDown(By byLocator, String selectText) throws Exception {
		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			WebElement element = driver.findElement(byLocator);
			Select select = new Select(element);
			Thread.sleep(1000);
			select.selectByVisibleText(selectText);
			Thread.sleep(1000);
			return "Selecting [" + selectText + "] from drop down is successfull: " + byLocator;
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.coin.Selenium.GUIOperations#enterDataSelectDropDownByValue(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	@Override
	public String enterDataSelectDropDownByValue(String parent, String locator, String value) throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);

		return enterDataSelectDropDownByValue(byLocator, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.coin.Selenium.GUIOperations#enterDataSelectDropDownByValue(org.openqa.
	 * selenium.By, java.lang.String)
	 */
	@Override
	public String enterDataSelectDropDownByValue(By byLocator, String value) throws Exception {
		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));4
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			WebElement element = driver.findElement(byLocator);
			Select select = new Select(element);
			Thread.sleep(1000);
			select.selectByValue(value);
			Thread.sleep(1000);
			return "Selecting [" + value + "] from drop down is successfull: " + byLocator;
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}

	}

	public String enterDataSelectDropDownByIndex(String parent, String locator, int index) throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);

		return enterDataSelectDropDownByIndex(byLocator, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.coin.Selenium.GUIOperations#enterDataSelectDropDownByValue(org.openqa.
	 * selenium.By, java.lang.String)
	 */

	public String enterDataSelectDropDownByIndex(By byLocator, int index) throws Exception {
		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			WebElement element = driver.findElement(byLocator);
			Select select = new Select(element);
			Thread.sleep(1000);
			select.selectByIndex(index);
			Thread.sleep(1000);
			return "Selecting [" + index + "]th option from drop down is successfull: " + byLocator;
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}

	}

	public boolean verifySelectDropDownOptions(String parent, String locator, int expSize, String[] expOptions)
			throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);

		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			WebElement element = driver.findElement(byLocator);
			Select select = new Select(element);
			Thread.sleep(1000);
			List<WebElement> actualOptions = select.getOptions();

			if (actualOptions.size() == expSize) {
				for (int i = 0; i < expOptions.length; i++) {
					String actualText = actualOptions.get(i).getText();
					if (!actualText.equalsIgnoreCase(expOptions[i])) {
						takeScreenShot();
						return false;
					}
				}
			} else {
				takeScreenShot();
				return false;
			}
			return true;

		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#getText(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String getText(String parent, String locator) throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);

		return getText(byLocator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#getText(org.openqa.selenium.By)
	 */
	@Override
	public String getText(By byLocator) throws Exception {
		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			String text = driver.findElement(byLocator).getText();
			return text;
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#clickObject(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String clickObject(String parent, String locator) throws Exception {
		By byLocator = getLocatorAndReturnBy(parent, locator);
		return clickObject(byLocator);
	}

	@Override
	public String clickObjectWitoutQuit(String parent, String locator) throws Exception {
		By byLocator = getLocatorAndReturnBy(parent, locator);
		return clickObjectWithoutQuit(byLocator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.coin.Selenium.GUIOperations#clickObjectAtLocation(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String clickObjectAtLocation(String parent, String locator) throws Exception {
		By byLocator = getLocatorAndReturnBy(parent, locator);
		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			WebElement findElement = driver.findElement(byLocator);
			new Actions(driver).moveToElement(findElement, 100, 2).click().build().perform();
			Thread.sleep(1000);
			return "Clicking on the element successfull." + byLocator;
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#clickObject(java.lang.String,
	 * java.lang.String, boolean)
	 */
	@Override
	public String clickObject(String how, String what, boolean sendDirectLocator) throws Exception {
		By byLocator = byLocator(how, what);
		return clickObject(byLocator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#clickObject(org.openqa.selenium.By)
	 */
	@Override
	public String clickObject(By byLocator) throws Exception {
		try {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			driver.findElement(byLocator).click();
			Thread.sleep(1000);
			return "Clicking on the element successfull." + byLocator;
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}

	}

	public String clickObjectWithoutQuit(By byLocator) throws Exception {
		try {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			driver.findElement(byLocator).click();
			Thread.sleep(1000);
			return "Clicking on the element successfull." + byLocator;
		} catch (Exception e) {
			takeScreenShot();
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#clickObject(org.openqa.selenium.
	 * WebElement)
	 */
	@Override
	public String clickObject(WebElement element) throws Exception {
		try {
			element.click();
			return "Clicking on the element successfull." + element;
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#byLocator(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public By byLocator(String locatorType, String locatorValue) {
		locatorType = locatorType.toUpperCase().trim();
		By by = null;
		LocatorType locatorType_enum = LocatorType.valueOf(locatorType);
		switch (locatorType_enum) {
		default:
			UtilitySel.printMessage("Locator stgategy not defined");
		case CSS:
			by = By.cssSelector(locatorValue);
			break;
		case XPATH:
			by = By.xpath(locatorValue);
			break;
		case CLASS:
			by = By.className(locatorValue);
			break;
		case ID:
			by = By.id(locatorValue);
			break;
		case NAME:
			by = By.name(locatorValue);
			break;
		case TEXT:
			by = By.linkText(locatorValue);
			break;
		case TAG:
			by = By.tagName(locatorValue);
			break;
		}
		return by;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#byLocator(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public By byLocator(String parent, String locator, String options) throws Exception {
		HashMap<String, String> currentStepORData = reader.getCurrentStepORData(orData, parent, locator);
		String locatorValue = currentStepORData.get("what").replace("${data}", options);
		return byLocator(currentStepORData.get("how"), locatorValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.coin.Selenium.GUIOperations#getLocatorAndReturnBy(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public By getLocatorAndReturnBy(String parent, String locator) throws Exception {
		HashMap<String, String> currentStepORData = reader.getCurrentStepORData(orData, parent, locator);

		return byLocator(currentStepORData.get("how"), currentStepORData.get("what"));
	}

	/**
	 * 
	 */
	public String setScreenShotName(String name) {
		String fileSeparator = System.getProperty("file.separator");
		name = new UtilitySel(new LogFile()).createProperFileName(name);
		String jenkinsUrl = System.getenv("JENKINS_URL");
		String workSpace = System.getenv("WORKSPACE");
		if (jenkinsUrl == null || jenkinsUrl.equalsIgnoreCase("")) {
			screenShotPath = System.getProperty("user.dir") + fileSeparator + "src" + fileSeparator + "test"
					+ fileSeparator + "resources" + fileSeparator + "HTML_Report" + fileSeparator + "Screenshots"
					+ fileSeparator + name + ".png";
			screenShotPath = screenShotPath.replace(" ", "");
		} else {
			screenShotPath = workSpace + fileSeparator + "src" + fileSeparator + "test" + fileSeparator + "resources"
					+ fileSeparator + "HTML_Report" + fileSeparator + "Screenshots" + fileSeparator + name + ".png";
			screenShotPath = screenShotPath.replace(" ", "");
		}

		objLog.info("Screenshot file path set :" + screenShotPath);

		return "Screenshot path is " + name;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#takeScreenShot()
	 */
	@Override
	public String takeScreenShot() throws IOException {

		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		UtilitySel.printMessage(screenShotPath);
		File file = new UtilitySel(new LogFile()).createFile(screenShotPath, true);
		FileUtils.copyFile(screenshot, file);
		return "screen shot taken successfully.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#getElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	/* Need to remove this method */
	@Override
	public WebElement getElement(String parent, String locator, String options) throws Exception {
		try {
			HashMap<String, String> currentStepORData = reader.getCurrentStepORData(orData, parent, locator);

			String locatorType = currentStepORData.get("how");
			String locatorValue = currentStepORData.get("what");
			if (locatorValue.contains("${data}")) {
				locatorValue = locatorValue.replace("${data}", options);
			}
			By byLocator = byLocator(locatorType, locatorValue);

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			WebElement findElement = driver.findElement(byLocator);
			return findElement;
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#verifyText(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean verifyText(String parent, String locator, String expected) throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);

		return verifyText(byLocator, expected);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#verifyText(org.openqa.selenium.By,
	 * java.lang.String)
	 */
	@Override
	public boolean verifyText(By byLocator, String expected) throws Exception {
		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			wait.until(new WaitFunctions(byLocator).functionIsDisplayed);
			String actual = driver.findElement(byLocator).getText();
			if (expected.equalsIgnoreCase(actual)) {
				UtilitySel.printMessage("Status is displayed as expected");
				return true;
			} else {
				UtilitySel.printMessage("Verification failed. Expected: " + expected + "\n Actual value: " + actual);
				return false;
			}
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#moveCalenderToMonth(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String moveCalenderToMonth(String parent1, String locator1, String parent2, String locator2,
			String expectedMonth) throws Exception {
		By byLocator1 = getLocatorAndReturnBy(parent1, locator1);
		By byLocator2 = getLocatorAndReturnBy(parent2, locator2);

		return moveCalenderToMonth(byLocator1, byLocator2, expectedMonth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.coin.Selenium.GUIOperations#moveCalenderToMonth(org.openqa.selenium.
	 * By, org.openqa.selenium.By, java.lang.String)
	 */
	@Override
	public String moveCalenderToMonth(By byLocator1, By byLocator2, String expectedMonth) throws Exception {
		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator1));
			wait.until(new WaitFunctions(byLocator1).functionIsDisplayed);
			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator2));
			wait.until(new WaitFunctions(byLocator2).functionIsDisplayed);

			expectedMonth = expectedMonth.trim();
			for (int i = 0; i < 100; i++) {
				Thread.sleep(2000);
				String actualTest = driver.findElement(byLocator2).getAttribute("innerHTML");
				String actual = driver.findElement(byLocator2).getText();
				String[] split = actual.split("\n");
				String actualString;
				try {
					actualString = split[1];
				} catch (ArrayIndexOutOfBoundsException e) {
					actualString = split[0];
				}
				actualString = actualString.trim();
				if (expectedMonth.equalsIgnoreCase(actualString)) {
					return "Displayed required month in calender successfully";
				} else {
					driver.findElement(byLocator1).click();
					continue;
				}
			}
			takeScreenShot();
			driver.quit();
			throw new Exception("Not found the required month in future even after 100 months.");
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	/*
	 * public String getDossierID(String parent, String locator) throws
	 * IOException { try { HashMap<String, String> rightIconORData =
	 * reader.getCurrentStepORData(orData, parent, locator); By byLocator =
	 * byLocator(rightIconORData.get("how"), rightIconORData.get("what"));
	 * wait.until(ExpectedConditions.visibilityOfElementLocated( byLocator));
	 * String text = driver.findElement(byLocator).getText(); return text +
	 * "-01";
	 * 
	 * } catch (Exception e) { takeScreenShot(); driver.quit(); throw e;
	 * 
	 * } }
	 */

	public String[] getCredentials(String sprCode) throws Exception {
		String userName = "", passWord = "", providerDropDownValue = "";
		try {
			HashMap<String, String> currentStepCredentialsData = reader.getCurrentStepCredentialsData(credentialsData,
					sprCode);
			userName = currentStepCredentialsData.get("username");
			passWord = currentStepCredentialsData.get("password");

			providerDropDownValue = currentStepCredentialsData.get("providerDropDownValue");
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
		return new String[] { userName, passWord, providerDropDownValue };
	}

	public String[] getCredentials(String sprCode, String path) throws Exception {
		String userName = "", passWord = "", providerDropDownValue = "";
		try {
			HashMap<String, String> currentStepCredentialsData = reader
					.getCurrentStepCredentialsData(reader.getCompleteRows(path, "credentials"), sprCode);
			userName = currentStepCredentialsData.get("username");
			passWord = currentStepCredentialsData.get("password");
			try {
				providerDropDownValue = currentStepCredentialsData.get("providerDropDownValue");
			} catch (Exception e) {
				UtilitySel.printMessage("no provider drop down value");
			}

		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
		return new String[] { userName, passWord, providerDropDownValue };
	}

	@Override
	public String VerifyElementPresent(String parent, String locator) throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);

		return VerifyElementPresent(byLocator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#VerifyElementPresent(org.openqa.
	 * selenium.By, java.lang.String)
	 */
	@Override
	public String VerifyElementPresent(By byLocator) throws Exception {
		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			// WebElement element = driver.findElement(byLocator);
			if (driver.findElement(byLocator).isDisplayed()) {
				return "Element is present";
			} else {
				throw new IOException("Element is not present");
			}

		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}

	}

	@Override
	public String verifyAttributeValue(String parent, String locator, String AttributeName, String expected)
			throws Exception {

		By byLocator = getLocatorAndReturnBy(parent, locator);

		return verifyAttributeValue(byLocator, AttributeName, expected);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.coin.Selenium.GUIOperations#verifyText(org.openqa.selenium.By,
	 * java.lang.String)
	 */
	@Override
	public String verifyAttributeValue(By byLocator, String AttributeName, String expected) throws Exception {
		try {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			String actual = driver.findElement(byLocator).getAttribute(AttributeName);
			if (actual.contains(expected)) {
				return "Attribute value is same as expected";
			} else {
				return "Verification failed. Expected: " + expected + "\n Actual value: " + actual;
			}
		} catch (Exception e) {
			takeScreenShot();
			driver.quit();
			throw e;
		}
	}

	@Override
	public String verifyAttributeValue(String how, String what, boolean sendDirectLocator, String AttributeName,
			String expected) throws Exception {
		By byLocator = byLocator(how, what);
		return verifyAttributeValue(byLocator, AttributeName, expected);
	}

	@Override
	public boolean verifyElementDoesNotExist(String how, String what, boolean sendDirectLocator) throws Exception {
		By byLocator = byLocator(how, what);
		return verifyElementDoesNotExist(byLocator);
	}

	@Override
	public boolean verifyElementDoesNotExist(By byLocator) throws Exception {
		try {
			int size = driver.findElements(byLocator).size();
			if (size == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// takeScreenShot();
			driver.quit();
			throw e;
		}

	}

	public enum LocatorType {
		CSS, XPATH, ID, CLASS, NAME, TEXT, TAG

	}
}