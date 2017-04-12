/**
 * 
 */
package nl.coin.Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

/**
 * @author hemasundar
 *
 */
public interface GUIOperations {

    WebDriver openBrowser() throws IOException, Exception;

    boolean quitBrowser() throws IOException, Exception;

    void browseURL(String url) throws IOException, Exception;

    /* Click Methods */
    String clickObject(String parent, String locator) throws IOException, Exception;

    String clickObjectAtLocation(String parent, String locator) throws IOException, Exception;

    String clickObject(String how, String what, boolean sendDirectLocator) throws IOException, Exception;

    String clickObject(By byLocator) throws IOException, Exception;

    String clickObject(WebElement element) throws IOException, Exception;

    /* Enter Data in textFields & drop downs */
    String enterData(String parent, String locator, String inputText) throws IOException, Exception;

    String enterData(By byLocator, String inputText) throws IOException, Exception;

    String enterDataSelectDropDown(String parent, String locator, String selectText) throws InterruptedException, IOException, Exception;

    String enterDataSelectDropDown(By byLocator, String selectText) throws InterruptedException, IOException, Exception;

    String enterDataSelectDropDownByValue(String parent, String locator, String value) throws Exception, Exception;

    String enterDataSelectDropDownByValue(By byLocator, String value) throws Exception, Exception;

    /* Get Element Locator */
    By byLocator(String locatorType, String locatorValue)throws Exception;

    By byLocator(String parent, String locator, String options) throws IOException, Exception;

    By getLocatorAndReturnBy(String parent, String locator) throws IOException, Exception;

    WebElement getElement(String parent, String locator, String options) throws IOException, Exception;

    /* Get Element texts & Verify */
    String getText(String parent, String locator) throws IOException, Exception;

    String getText(By byLocator) throws IOException, Exception;

    boolean verifyText(String parent, String locator, String expected) throws IOException, Exception;

    boolean verifyText(By byLocator, String expected) throws IOException, Exception;

    String takeScreenShot() throws IOException, Exception;

    String moveCalenderToMonth(String parent1, String locator1, String parent2, String locator2, String expectedMonth) throws Exception;

    String moveCalenderToMonth(By byLocator1, By byLocator2, String expectedMonth) throws Exception;

	String VerifyElementPresent(By byLocator) throws InterruptedException, IOException, Exception;

	String VerifyElementPresent(String parent, String locator) throws InterruptedException, IOException, Exception;

	
	String verifyAttributeValue(String parent, String locator, String AttributeName, String expected)
			throws IOException, Exception;

	String verifyAttributeValue(By byLocator, String AttributeName, String expected) throws IOException, Exception;

	String verifyAttributeValue(String how, String what, boolean sendDirectLocator, String AttributeName,
			String expected) throws IOException, Exception;

    boolean verifyElementDoesNotExist(String how, String what, boolean sendDirectLocator) throws Exception;

    boolean verifyElementDoesNotExist(By byLocator) throws Exception;

	String clickObjectWitoutQuit(String parent, String locator) throws Exception;

}