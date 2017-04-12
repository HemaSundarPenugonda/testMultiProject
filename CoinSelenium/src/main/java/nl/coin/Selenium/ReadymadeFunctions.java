package nl.coin.Selenium;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.xmlbeans.XmlException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestRunnable;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.support.XmlHolder;

import groovy.sql.GroovyRowResult;
import groovy.sql.Sql;
import nl.coin.common.UtilitySel;

public class ReadymadeFunctions {

	public ReadymadeFunctions() {
		super();
	}

	public String sendCTR(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String recipient, String donor, String contractId, String dossierType, String wishDate)
			throws Exception, ParseException {
		return sendCTR(seleniumHandle, javaHandle, browser, guiUrl, recipient, donor, dossierType, "COIN", "1389SS",
				"99999", null, false, contractId, null, wishDate, null, null, null);
	}

	public String sendCTR(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String recipient, String donor, String dossierType, String name, String zipCode, String houseNumber,
			String houseNumberExtention, boolean earlyTermination, String contractId, String iban, String wishDate,
			String note, String NumberSeriesStart, String numberSeriesEnd) throws Exception, ParseException {

		String DossierIdGui;
		DossierIdGui = fillCtrForm(seleniumHandle, javaHandle, browser, guiUrl, recipient, donor, dossierType, name,
				zipCode, houseNumber, houseNumberExtention, earlyTermination, contractId, iban, wishDate, note,
				NumberSeriesStart, numberSeriesEnd);

		boolean verifyText = seleniumHandle.verifyText("HomePage", "resultMessage", "Message Send successfully");
		UtilitySel.printMessage(String.valueOf(seleniumHandle.quitBrowser()));
		if (!verifyText) {
			DossierIdGui = "";
		}
		return DossierIdGui;

	}

	public boolean sendFailCTR(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String recipient, String donor, String dossierType, String name, String zipCode, String houseNumber,
			String houseNumberExtention, boolean earlyTermination, String contractId, String iban, String wishDate,
			String note, String NumberSeriesStart, String numberSeriesEnd, String errorMessage)
			throws Exception, ParseException {

		fillCtrForm(seleniumHandle, javaHandle, browser, guiUrl, recipient, donor, dossierType, name, zipCode,
				houseNumber, houseNumberExtention, earlyTermination, contractId, iban, wishDate, note,
				NumberSeriesStart, numberSeriesEnd);

		boolean verifyText = seleniumHandle.verifyText("HomePage", "ErrorresultMessage", errorMessage);
		UtilitySel.printMessage(String.valueOf(seleniumHandle.quitBrowser()));
		return verifyText;

	}

	private String fillCtrForm(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String recipient, String donor, String dossierType, String name, String zipCode, String houseNumber,
			String houseNumberExtention, boolean earlyTermination, String contractId, String iban, String wishDate,
			String note, String NumberSeriesStart, String numberSeriesEnd) throws Exception, ParseException {
		String DossierIdGui;

		openBrowserAndLogin(seleniumHandle, browser, guiUrl, recipient);

		// Click on Requests menu
		// adding sleep because some times failing to click on Requests tab
		// Thread.sleep(2000);
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "requests"));

		if (donor != null) {
			String[] donorCredentials = seleniumHandle.getCredentials(donor);
			// Enter dossier input details
			UtilitySel.printMessage(seleniumHandle.enterDataSelectDropDown("HomePage", "CTRDonor", donorCredentials[2]));
		}
		if (dossierType != null) {
			if (dossierType.equalsIgnoreCase("<business>Y</business>")) {
				UtilitySel.printMessage(seleniumHandle.enterDataSelectDropDown("HomePage", "CTRDossier", "Business"));
			} else {
				UtilitySel.printMessage(seleniumHandle.enterDataSelectDropDown("HomePage", "CTRDossier", "Consumer"));
			}
		}
		DossierIdGui = seleniumHandle.getText("HomePage", "CTRSwitchid") + "-01";

		// Enter address details
		if (name != null) {
			UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "CTRName", name));
		}
		if (zipCode != null) {
			UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "CTRZipCode", zipCode));
		}
		if (houseNumber != null) {
			UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "CTRHouseNumber", houseNumber));
		}
		if (houseNumberExtention != null) {
			UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "houseNumExt", houseNumberExtention));
		}

		if (earlyTermination == true) {
			UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "earlyTermination"));
		}

		// Enter validation Details
		if (contractId != null) {
			UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "CTRContractID", contractId));
		}
		if (iban != null) {
			UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "ctrIban", iban));
		}

		if (wishDate != null) {
			selectDate(seleniumHandle, javaHandle, wishDate, "HomePage", "CTRWishDateIcon", "HomePage",
					"CTRWishDateRightIcon", "HomePage", "CTRWishDateCalHeader", "HomePage", "ctrWishDate");
		}
		if (note != null) {
			UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "ctrNote", note));
		}

		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "ctrNumberSeriesStart", NumberSeriesStart));
		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "ctrNumberSeriesEnd", numberSeriesEnd));
		if (NumberSeriesStart != null || numberSeriesEnd != null) {
			if (NumberSeriesStart == null || numberSeriesEnd == null) {
				UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "addNumberRange"));
			}
			UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "addNumberRange"));
		}

		// Click on Send CTR button
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "CTRSendRequest"));
		return DossierIdGui;
	}

	public boolean sendCTROldData(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String recipient, String switchId, String donor, String dossierType, String name, String zipCode,
			String houseNumber, String houseNumberExtention, boolean earlyTermination, String contractId, String iban,
			String wishDate, String note, String NumberSeriesStart, String numberSeriesEnd)
			throws Exception, ParseException {

		openBrowserAndLogin(seleniumHandle, browser, guiUrl, recipient);

		searchAndOpenDossier(seleniumHandle, switchId);
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "NewRequestOldData"));

		String[] donorCredentials = seleniumHandle.getCredentials(donor);

		String selectedDonor = seleniumHandle.getSelectedOption("HomePage", "CTRDonor");
		assert selectedDonor.equalsIgnoreCase(donorCredentials[2]);

		String expDossierType;
		String selectedDossierType = seleniumHandle.getSelectedOption("HomePage", "CTRDossier");
		if (dossierType.equalsIgnoreCase("<business>Y</business>")) {
			expDossierType = "Business";
		} else {
			expDossierType = "Consumer";
		}
		assert selectedDossierType.equalsIgnoreCase(expDossierType);

		// get address details
		String actualName = seleniumHandle.getTextFieldData("HomePage", "CTRName");
		assert actualName.equalsIgnoreCase(name);

		String actualZipCode = seleniumHandle.getTextFieldData("HomePage", "CTRZipCode");
		assert actualZipCode.equalsIgnoreCase(zipCode);

		String actualHouseNumber = seleniumHandle.getTextFieldData("HomePage", "CTRHouseNumber");
		assert actualHouseNumber.equalsIgnoreCase(houseNumber);

		String actualHouseNumberExt = seleniumHandle.getTextFieldData("HomePage", "houseNumExt");
		assert actualHouseNumberExt.equalsIgnoreCase(houseNumberExtention);

		boolean actualEarlyTermination = seleniumHandle.isChecked("HomePage", "earlyTermination");
		assert actualEarlyTermination == earlyTermination;

		// Enter validation Details
		String actualContractId = seleniumHandle.getTextFieldData("HomePage", "CTRContractID");
		assert actualContractId.equalsIgnoreCase(contractId);

		String actualIban = seleniumHandle.getTextFieldData("HomePage", "ctrIban");
		assert actualIban.equalsIgnoreCase(iban);

		// String actualWishDate = seleniumHandle.getTextFieldData("HomePage",
		// "ctrIban");
		// need to improve
		String actualNote = seleniumHandle.getTextFieldData("HomePage", "ctrNote");
		// assert actualNote.equalsIgnoreCase(note);

		String actualNumStart = seleniumHandle.getTextFieldData("HomePage", "ctrNumberSeriesStart");
		assert actualNumStart.equalsIgnoreCase(NumberSeriesStart);

		String actualNumEnd = seleniumHandle.getTextFieldData("HomePage", "ctrNumberSeriesEnd");
		assert actualNumEnd.equalsIgnoreCase(numberSeriesEnd);
		// Need to update once CDV-849 is fixed
		// Click on Send CTR button
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "CTRSendRequest"));

		boolean verifyText = seleniumHandle.verifyText("HomePage", "resultMessage", "Message Send successfully");
		UtilitySel.printMessage(String.valueOf(seleniumHandle.quitBrowser()));
		return verifyText;

	}

	public boolean sendCTRA(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String donor, String dossierID, String donorDossierType, String blockingCode) throws Exception {
		return sendCTRA(seleniumHandle, javaHandle, browser, guiUrl, donor, dossierID, donorDossierType, blockingCode,
				null);
	}

	public boolean sendCTRA(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String donor, String dossierID, String donorDossierType, String blockingCode, String proposedDate)
			throws Exception {

		fillCTRAForm(seleniumHandle, javaHandle, browser, guiUrl, donor, dossierID, donorDossierType, blockingCode,
				proposedDate, donorDossierType);
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "sendRequestAnswer"));
		Thread.sleep(1000);

		boolean verification = seleniumHandle.verifyText("HomePage", "resultMessage", "Message Send successfully");
		UtilitySel.printMessage(String.valueOf(seleniumHandle.quitBrowser()));

		return verification;
	}

	private void fillCTRAForm(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String donor, String dossierID, String donorDossierType, String blockingCode, String proposedDate,
			String note) throws Exception, InterruptedException, ParseException {
		if (donorDossierType == null) {
			donorDossierType = "";
		}
		openBrowserAndLogin(seleniumHandle, browser, guiUrl, donor);

		searchAndOpenDossier(seleniumHandle, dossierID);

		if (donorDossierType.equalsIgnoreCase("<business>Y</business>")) {

			UtilitySel.printMessage(seleniumHandle.enterDataSelectDropDown("HomePage", "businessType", "Business"));
			UtilitySel.printMessage(
					seleniumHandle.enterDataSelectDropDownByValue("HomePage", "blockingCode", blockingCode));
		} else {

			UtilitySel.printMessage(seleniumHandle.enterDataSelectDropDown("HomePage", "businessType", "Consumer"));
			UtilitySel.printMessage(seleniumHandle.enterDataSelectDropDownByValue("HomePage", "ConsumerCTRAblockingCode",
					blockingCode));
		}

		if (proposedDate != null) {
			selectDate(seleniumHandle, javaHandle, proposedDate, "HomePage", "CTRAProposedDateIcon", "HomePage",
					"CTRAProposedDateRightIcon", "HomePage", "CTRAProposedDateCalHeader", "HomePage",
					"ctraProposedDate");
		}
		// Need to add verify element present for blocking code 11

		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "requestAnswerNote", dossierID));
		Thread.sleep(1000);
	}

	public boolean sendCTCH(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String recipient, String switchId, String wishDate) throws Exception {

		openBrowserAndLogin(seleniumHandle, browser, guiUrl, recipient);

		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "dossiers"));

		// Click on Performed menu
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "CancelMenu"));

		// Get the locator of viewDetails with current dossierID
		By byViewDetails = seleniumHandle.byLocator("HomePage", "viewDetailsProper", switchId);

		// Click on the view details button
		UtilitySel.printMessage(seleniumHandle.clickObject(byViewDetails));

		selectDate(seleniumHandle, javaHandle, wishDate, "HomePage", "CTCHWishDateIcon", "HomePage",
				"CTCHWishDateRightIcon", "HomePage", "CTCHWishDateCalHeader", "HomePage", "ctchWishDate");

		// Enter change note
		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "SendChangeNote", switchId));

		// click on Send change button
		UtilitySel.printMessage(seleniumHandle.clickObjectAtLocation("HomePage", "SendChangeButton"));

		boolean verification = seleniumHandle.verifyText("HomePage", "resultMessage", "Message Send successfully");
		UtilitySel.printMessage(String.valueOf(seleniumHandle.quitBrowser()));

		return verification;

	}

	public boolean sendCTCHA(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String donor, String dossierID, String blockingCode) throws Exception {
		return sendCTCHA(seleniumHandle, javaHandle, browser, guiUrl, donor, dossierID, blockingCode, null);
	}

	public boolean sendCTCHA(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String donor, String dossierID, String blockingCode, String proposedDate) throws Exception {

		openBrowserAndLogin(seleniumHandle, browser, guiUrl, donor);

		searchAndOpenDossier(seleniumHandle, dossierID);

		UtilitySel.printMessage(
				seleniumHandle.enterDataSelectDropDownByValue("HomePage", "changeAnswerBlockingCode", blockingCode));

		if (proposedDate != null) {

			selectDate(seleniumHandle, javaHandle, proposedDate, "HomePage", "ChangeAnswerScreenCalendarIcon",
					"HomePage", "CTCHAProposedDateRightIcon", "HomePage", "CTCHAProposedDateCalHeader", "HomePage",
					"ctchaProposedDate");
		}

		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "ChangeRequestAnswerNote", dossierID));
		Thread.sleep(1000);

		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "sendChangeAnswer"));
		Thread.sleep(1000);
		boolean verification = seleniumHandle.verifyText("HomePage", "resultMessage", "Message Send successfully");
		UtilitySel.printMessage(String.valueOf(seleniumHandle.quitBrowser()));

		return verification;
	}

	public boolean sendCTC(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String recipient, String switchId) throws Exception {

		// Open the browser
		WebDriver driver = seleniumHandle.openBrowser(browser);
		// Login to application
		seleniumHandle.browseURL(guiUrl);
		UtilitySel.printMessage(driver.getTitle());
		login2App(recipient, seleniumHandle);

		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "dossiers"));

		// Click on Cancel menu
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "CancelMenu"));

		// Get the locator of viewDetails with current dossierID
		By byViewDetails = seleniumHandle.byLocator("HomePage", "viewDetailsProper", switchId);

		// Click on the view details button
		UtilitySel.printMessage(seleniumHandle.clickObject(byViewDetails));

		// Enter Cancel note
		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "SendCancelNote", switchId));

		// click on Send Cancel button
		UtilitySel.printMessage(seleniumHandle.clickObjectAtLocation("HomePage", "SendCancelButton"));

		boolean verification = seleniumHandle.verifyText("HomePage", "resultMessage", "Message Send successfully");
		UtilitySel.printMessage(String.valueOf(seleniumHandle.quitBrowser()));

		return verification;
	}

	public boolean sendCTP(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String recipient, String switchId) throws Exception {

		// Open the browser
		WebDriver driver = seleniumHandle.openBrowser(browser);
		// Login to application
		seleniumHandle.browseURL(guiUrl);
		UtilitySel.printMessage(driver.getTitle());
		login2App(recipient, seleniumHandle);

		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "dossiers"));

		// Click on Cancel menu
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "CancelMenu"));

		UtilitySel.printMessage(switchId);
		// Get the locator of viewDetails with current dossierID
		By byViewDetails = seleniumHandle.byLocator("HomePage", "viewDetailsProper", switchId);

		// Click on the view details button
		UtilitySel.printMessage(seleniumHandle.clickObject(byViewDetails));

		// Enter performed note
		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "PerformedNote", switchId));

		// click on Send Performed button
		UtilitySel.printMessage(seleniumHandle.clickObjectAtLocation("HomePage", "SendPerformed"));

		boolean verification = seleniumHandle.verifyText("HomePage", "resultMessage", "Message Send successfully");
		UtilitySel.printMessage(String.valueOf(seleniumHandle.quitBrowser()));

		return verification;
	}

	private void selectDate(SeleniumActions seleniumHandle, JavaActions javaHandle, String wishDate, String iconParent,
			String iconLocator, String rightParent, String rightLocator, String headerParent, String headerLocator,
			String dateParent, String dateLocator) throws Exception, ParseException {
		// Enter wish date
		UtilitySel.printMessage(seleniumHandle.clickObject(iconParent, iconLocator));
		String onlyDate = javaHandle.getDateFromFormat(wishDate, "yyyy-MM-dd'T'HH:mm:ss");
		// String onlyDate = javaHandle.convertDateFormat(wishDate,
		// "yyyy-MM-dd", "dd");
		String monthYear = javaHandle.convertDateFormat(wishDate, "yyyy-MM-dd'T'HH:mm:ss", "MMMM yyyy");
		UtilitySel.printMessage(
				seleniumHandle.moveCalenderToMonth(rightParent, rightLocator, headerParent, headerLocator, monthYear));
		Thread.sleep(2000);
		By byLocator = seleniumHandle.byLocator(dateParent, dateLocator, onlyDate);
		UtilitySel.printMessage(seleniumHandle.clickObject(byLocator));
	}

	private void login2App(String recipient, SeleniumActions seleniumHandle) throws Exception {
		String[] sampleArray = seleniumHandle.getCredentials(recipient);
		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "userName", sampleArray[0]));
		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "passWord", sampleArray[1]));
		// UtilitySel.printMessage(seleniumHandle.clickObject("HomePage","8HrsSession");
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "login"));
	}

	public void openBrowserAndLogin(SeleniumActions seleniumHandle, String browser, String guiUrl,
			String serviceProvider) throws Exception {
		WebDriver driver = seleniumHandle.openBrowser(browser);
		seleniumHandle.browseURL(guiUrl);

		login2App(serviceProvider, seleniumHandle);
	}

	public void searchAndOpenDossier(SeleniumActions seleniumHandle, String dossierID)
			throws Exception, InterruptedException {
		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "dossiers"));

		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "SearchTab"));

		UtilitySel.printMessage(seleniumHandle.enterData("HomePage", "DossierID", dossierID));
		Thread.sleep(1000);

		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "SearchButton"));

		UtilitySel.printMessage(seleniumHandle.clickObject("HomePage", "ViewDetailsSearch"));
	}

	public String getDonorSoapVersionS2S(String donorIF, String DossierType4mTestData) {

		donorIF = donorIF.toLowerCase();
		switch (donorIF) {
		case "soapv2":
			return "v2";

		case "gui + soapv2":
			return "v2";

		case "gui + soapv2 + soapv3":
			if (DossierType4mTestData.equalsIgnoreCase("<business>Y</business>")) {
				return "v3";
			} else {
				return "v2";
			}

		case "gui + soapv3":
			return "v3";

		default:
			return null;
		}

	}

	public String getDonorSoapVersionS2SAndSetProperty(TestSuite testSuite, String donorIF,
			String DossierType4mTestData, String propertyName) {
		String res = getDonorSoapVersionS2S(donorIF, DossierType4mTestData);
		testSuite.setPropertyValue(propertyName, res);
		return res;
	}

	public String getDonorSoapVersionS2G(String donorIF, String dossierType4mTestData, String businessGuiOnly) {
		if (donorIF.equalsIgnoreCase("GUI") || donorIF.equalsIgnoreCase("Email + GUI")) {
			return "GUI";
		} else if ((donorIF.equalsIgnoreCase("GUI + SOAPv2") || donorIF.equalsIgnoreCase("GUI + SOAPv2 + SOAPv3"))
				&& businessGuiOnly.equalsIgnoreCase("true")
				&& dossierType4mTestData.equalsIgnoreCase("<business>Y</business>")) {
			return "GUI";
		} else if (donorIF.equalsIgnoreCase("GUI + SOAPv2") || donorIF.equalsIgnoreCase("SOAPv2")
				|| (donorIF.equalsIgnoreCase("GUI + SOAPv2 + SOAPv3")
						&& !dossierType4mTestData.equalsIgnoreCase("<business>Y</business>"))) {
			return "v2";
		} else {
			return "v3";
		}

	}

	public String getDonorSoapVersionS2GAndSetProperty(TestSuite testSuite, String donorIF,
			String dossierType4mTestData, String businessGuiOnly, String propertyName) {
		String donorSoapVersion = getDonorSoapVersionS2G(donorIF, dossierType4mTestData, businessGuiOnly);
		testSuite.setPropertyValue(propertyName, donorSoapVersion);
		return donorSoapVersion;
	}

	public String getDonorDossierTypeS2S(String donorIF, String DossierType4mTestData) {

		donorIF = donorIF.toLowerCase();
		switch (donorIF) {
		case "soapv2":
			return "";

		case "gui + soapv2":
			return "";

		case "gui + soapv2 + soapv3":
			if (DossierType4mTestData.equalsIgnoreCase("<business>Y</business>")) {
				if (!DossierType4mTestData.equalsIgnoreCase("<business>Y</business>")) {
					return "<business>N</business>";
				} else {
					return "<business>Y</business>";
				}
			} else {
				return "";
			}

		case "gui + soapv3":
			if (!DossierType4mTestData.equalsIgnoreCase("<business>Y</business>")) {
				return "<business>N</business>";
			} else {
				return "<business>Y</business>";
			}

		default:
			return null;
		}

		/*
		 * donorIF = donorIF.toLowerCase(); if
		 * (donorIF.equalsIgnoreCase("soapv2") ||
		 * donorIF.equalsIgnoreCase("gui + soapv2")) { return ""; } else if
		 * (donorIF.equalsIgnoreCase("gui + soapv2 + soapv3") &&
		 * !dossierType.equalsIgnoreCase("<business>Y</business>")) {
		 * 
		 * } else if (donorIF.equalsIgnoreCase("gui + soapv2 + soapv3") ||
		 * donorIF.equalsIgnoreCase("gui + soapv3")) { if
		 * (!dossierType.equalsIgnoreCase("<business>Y</business>")) { return
		 * "<business>N</business>"; } else { return "<business>Y</business>"; }
		 * } return null;
		 */

	}

	public String getDonorDossierTypeS2SAndSetProperty(TestSuite testSuite, String donorIF,
			String DossierType4mTestData, String propertyName) {
		String res = getDonorDossierTypeS2S(donorIF, DossierType4mTestData);
		testSuite.setPropertyValue(propertyName, res);
		return res;
	}

	public String getDonorDossierTypeS2G(String donorIF, String dossierType4mTestData, String businessGuiOnly) {
		if (donorIF.equalsIgnoreCase("GUI") || donorIF.equalsIgnoreCase("Email + GUI")) {
			return dossierType4mTestData;
		} else if ((donorIF.equalsIgnoreCase("GUI + SOAPv2") || donorIF.equalsIgnoreCase("GUI + SOAPv2 + SOAPv3"))
				&& businessGuiOnly.equalsIgnoreCase("true")
				&& dossierType4mTestData.equalsIgnoreCase("<business>Y</business>")) {
			return dossierType4mTestData;
		} else if (donorIF.equalsIgnoreCase("GUI + SOAPv2") || donorIF.equalsIgnoreCase("SOAPv2")
				|| (donorIF.equalsIgnoreCase("GUI + SOAPv2 + SOAPv3")
						&& !dossierType4mTestData.equalsIgnoreCase("<business>Y</business>"))) {
			return "";
		} else {
			if (dossierType4mTestData.equalsIgnoreCase("<business>Y</business>")) {
				return "<business>N</business>";
			} else {
				return dossierType4mTestData;
			}
		}

	}

	public String getDonorDossierTypeS2GAndSetProperty(TestSuite testSuite, String donorIF, String dossierType4mTestData, String businessGuiOnly, String propertyName) {
		String res = getDonorDossierTypeS2G(donorIF, dossierType4mTestData, businessGuiOnly);
		testSuite.setPropertyValue(propertyName, res);
		return res;
		
	}
	public String getOutgoingMsgType(String dossierType, String businessGuiOnly, String interfaceType) {
		String outGoingMsgType = null, ifMod;

		if (interfaceType.equalsIgnoreCase("GUI") || interfaceType.equalsIgnoreCase("Email + GUI")
				|| interfaceType.equalsIgnoreCase("GUI + SOAPv1")) {
			ifMod = "GUIONLY";
		} else if (interfaceType.equalsIgnoreCase("SOAPv2") || interfaceType.equalsIgnoreCase("SOAPv3")) {
			ifMod = "NOGUI";
		} else {
			ifMod = "WITHGUI";
		}

		if (dossierType.equalsIgnoreCase("<business>Y</business>")) {
			if (businessGuiOnly.equalsIgnoreCase("true")) {
				if (ifMod.equalsIgnoreCase("GUIONLY")) {
					outGoingMsgType = "EMAIL";
				} else if (ifMod.equalsIgnoreCase("WITHGUI")) {
					outGoingMsgType = "EMAIL";
				} else if (ifMod.equalsIgnoreCase("NOGUI")) {
					outGoingMsgType = "SOAP";
				}
			} else {
				if (ifMod.equalsIgnoreCase("GUIONLY")) {
					outGoingMsgType = "EMAIL";
				} else if (ifMod.equalsIgnoreCase("WITHGUI")) {
					outGoingMsgType = "SOAP";
				} else if (ifMod.equalsIgnoreCase("NOGUI")) {
					outGoingMsgType = "SOAP";
				}
			}
		} else {
			if (businessGuiOnly.equalsIgnoreCase("true")) {
				if (ifMod.equalsIgnoreCase("GUIONLY")) {
					outGoingMsgType = "EMAIL";
				} else if (ifMod.equalsIgnoreCase("WITHGUI")) {
					outGoingMsgType = "SOAP";
				} else if (ifMod.equalsIgnoreCase("NOGUI")) {
					outGoingMsgType = "SOAP";
				}
			} else {
				if (ifMod.equalsIgnoreCase("GUIONLY")) {
					outGoingMsgType = "EMAIL";
				} else if (ifMod.equalsIgnoreCase("WITHGUI")) {
					outGoingMsgType = "SOAP";
				} else if (ifMod.equalsIgnoreCase("NOGUI")) {
					outGoingMsgType = "SOAP";
				}
			}
		}
		return outGoingMsgType;
	}

	public void getOutgoingMsgTypeAndSetProperty(TestSuite testSuite, String dossierType, String businessGuiOnly,
			String interfaceType, String propertyName) {
		String outMsgType = getOutgoingMsgType(dossierType, businessGuiOnly, interfaceType);
		testSuite.setPropertyValue(propertyName, outMsgType);
	}

	public String getMessageType(String recif, String dossierType, String recbusguionly) {
		String recmsg;
		if (recif == "Email + GUI" || recif == "GUI"
				|| (dossierType == "<business>Y</business>" && recbusguionly == "true")) {
			recmsg = "GUI";
		} else {
			recmsg = "soap";
		}
		return recmsg;
	}

	public String getMessageTypeAndSetProperty(TestSuite testSuite, String recif, String dossierType,
			String recbusguionly, String propertyName) {
		String recmsg = getMessageType(recif, dossierType, recbusguionly);
		testSuite.setPropertyValue(propertyName, recmsg);
		return recmsg;
	}
	/*
	 * @DataProvider(name = "test1") public Object[][] createData1() throws
	 * Exception { return new Object[][] { { new SeleniumActions(), new
	 * JavaActions(), "http://192.168.100.21:8088/compv4_1", "ADMIN", "TP12",
	 * "Consumer" } }; }
	 */

	// @Test(dataProvider = "test1")
	public void editDossierTypePermission(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser,
			String guiUrl, String admin, String provider, String dossierType) throws Exception {
		// seleniumHandle = new SeleniumActions();
		// Open the browser
		WebDriver driver = seleniumHandle.openBrowser(browser);
		// Login to application
		seleniumHandle.browseURL(guiUrl);
		UtilitySel.printMessage(driver.getTitle());
		login2App(admin, seleniumHandle);

		seleniumHandle.clickObject("HomePage", "Users");
		String[] sampleArray = seleniumHandle.getCredentials(provider);
		seleniumHandle.enterData("HomePage", "UsersSearch", sampleArray[0]);
		seleniumHandle.clickObject("HomePage", "UsersSearchButton");
		Thread.sleep(2000);
		seleniumHandle.clickObject("HomePage", "editUserButton");
		Thread.sleep(2000);
		seleniumHandle.enterDataSelectDropDownByValue("HomePage", "dossierTypeDrpDwn", dossierType);
		seleniumHandle.clickObject("HomePage", "updateUser");
		seleniumHandle.verifyText("HomePage", "feedbackPanel", "User Updated Successfully.");

		// seleniumHandle.clickObject("HomePage", "dossiers");
		// boolean verification =
		// seleniumHandle.verifySelectDropDownOptions("HomePage",
		// "dossierDropDown", 3, new String[]{"consumer", "business", "consumer
		// and business"});

		driver.quit();
	}

	public void updateDomain(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String admin, String provider, String expDomain) throws Exception {

		// Open the browser
		WebDriver driver = seleniumHandle.openBrowser(browser);
		// Login to application
		seleniumHandle.browseURL(guiUrl);
		login2App(admin, seleniumHandle);

		seleniumHandle.clickObject("HomePage", "ProvidersLeftMenu");
		String[] sampleArray = seleniumHandle.getCredentials(provider);
		seleniumHandle.enterData("HomePage", "ProviderSearch", provider);
		seleniumHandle.clickObject("HomePage", "ProviderSearchButton");
		Thread.sleep(2000);
		seleniumHandle.clickObject("HomePage", "editProviderButton");
		Thread.sleep(2000);
		seleniumHandle.enterData("HomePage", "emailDomain", expDomain);

		seleniumHandle.clickObject("HomePage", "addProvider");
		// seleniumHandle.verifyText("HomePage", "feedbackPanel", "User Updated
		// Successfully.");

		driver.quit();
	}

	public boolean addUser(SeleniumActions seleniumHandle, JavaActions javaHandle, String browser, String guiUrl,
			String provider, String email, String user, boolean expResult, String message) throws Exception {

		boolean verification = false;
		// Open the browser
		WebDriver driver = seleniumHandle.openBrowser(browser);
		// Login to application
		seleniumHandle.browseURL(guiUrl);
		login2App(provider, seleniumHandle);

		seleniumHandle.clickObject("HomePage", "Users");
		seleniumHandle.enterData("Users", "userName", email);
		Thread.sleep(1000);
		seleniumHandle.enterDataSelectDropDown("Users", "userRole", user);
		seleniumHandle.enterDataSelectDropDownByIndex("Users", "provider", 1);
		Thread.sleep(1000);
		seleniumHandle.clickObject("HomePage", "updateUser");

		if (expResult) {
			verification = seleniumHandle.verifyText("HomePage", "feedbackPanel", message);
		} else {
			verification = seleniumHandle.verifyText("HomePage", "ErrorresultMessage", message);
		}

		driver.quit();
		return verification;
	}

	public String[] getDBValues(TestSuite testSuite) throws SQLException {
		WsdlTestSuite wsdlTestSuite = (WsdlTestSuite) testSuite;
		WsdlProject project = wsdlTestSuite.getProject();
		String dbhostname = project.getPropertyValue("DBHostname");
		String dbport = project.getPropertyValue("DBPort");
		String dbuser = project.getPropertyValue("DBUser");
		String dbpassword = project.getPropertyValue("DBPassword");
		String dbdatabase = project.getPropertyValue("DBDatabase");

		return new String[] { dbhostname, dbport, dbdatabase, dbuser, dbpassword };
	}

	public String[] getDBValues(TestCase testCase) throws SQLException {
		WsdlTestCase wsdlTestCase = (WsdlTestCase) testCase;
		WsdlTestSuite wsdlTestSuite = wsdlTestCase.getTestSuite();
		return getDBValues(wsdlTestSuite);
	}

	public Sql getDBConObject(String[] dbVlues) throws SQLException {
		Sql newInstance = Sql.newInstance("jdbc:postgresql://" + dbVlues[0] + ":" + dbVlues[1] + "/" + dbVlues[2]
				+ "?user=" + dbVlues[3] + "&password=" + dbVlues[4]);
		return newInstance;
	}

	public Sql createDBConObject(TestSuite testSuite) throws SQLException {
		String[] dbVlues = getDBValues(testSuite);
		Sql newInstance = Sql.newInstance("jdbc:postgresql://" + dbVlues[0] + ":" + dbVlues[1] + "/" + dbVlues[2]
				+ "?user=" + dbVlues[3] + "&password=" + dbVlues[4]);
		return newInstance;
	}

	public Sql createDBConObject(TestCase testCase) throws SQLException {
		String[] dbVlues = getDBValues(testCase);
		Sql newInstance = Sql.newInstance("jdbc:postgresql://" + dbVlues[0] + ":" + dbVlues[1] + "/" + dbVlues[2]
				+ "?user=" + dbVlues[3] + "&password=" + dbVlues[4]);
		return newInstance;
	}

	public String getTestDataAndIncreaseLinenumber(TestSuite testSuite, String directory) throws IOException {
		String testdatafile = testSuite.getPropertyValue("testdatafile");
		Integer linenumber = Integer.parseInt(testSuite.getPropertyValue("lineNumber"));
		BufferedReader fileReader = new BufferedReader(new FileReader(directory + "TestData/" + testdatafile));
		Object[] fileReaderReadLines = fileReader.lines().toArray();
		fileReader.close();
		int size = fileReaderReadLines.length;
		if (linenumber >= size) {
			linenumber = linenumber % size;
		}
		Object Line = fileReaderReadLines[linenumber];
		testSuite.setPropertyValue("testData", Line.toString());
		linenumber = linenumber + 1;
		testSuite.setPropertyValue("lineNumber", linenumber.toString());
		return Line.toString();
	}

	public void setRecipientAndDonorSoapCredentials(SeleniumActions seleniumHandle, TestSuite testSuite,
			String directory, String recipient, String donor) throws Exception {
		testSuite.setPropertyValue("recipient", recipient);
		String[] recipientCredentials = seleniumHandle.getCredentials(recipient,
				directory + "TestData/" + "soapCredentials.csv");
		testSuite.setPropertyValue("recipientUserName", recipientCredentials[0]);
		testSuite.setPropertyValue("recipientPassWord", recipientCredentials[1]);
		testSuite.setPropertyValue("donor", donor);
		String[] donorCredentials = seleniumHandle.getCredentials(donor,
				directory + "TestData/" + "soapCredentials.csv");
		testSuite.setPropertyValue("donorUserName", donorCredentials[0]);
		testSuite.setPropertyValue("donorPassword", donorCredentials[1]);
	}

	public String getRandomString() {
		return getRandomString("ABCDEFGRHIJKLMNOPQRSTUVWXYZ0123456789", 9, "");
	}

	public String getRandomString(int length, String extn) {
		return getRandomString("ABCDEFGRHIJKLMNOPQRSTUVWXYZ0123456789", length, extn);
	}

	public String getRandomString(String regex, int length, String extn) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(regex.charAt(random.nextInt(regex.length())));
		}
		return extn + sb.toString().toUpperCase();
	}

	public void setProperty4mDB(TestSuite testSuite, Sql con, String query, String columnName, String propertyName)
			throws SQLException {
		List<GroovyRowResult> recipientIDQuery = con.rows(query);
		testSuite.setPropertyValue(propertyName, recipientIDQuery.get(0).getProperty(columnName).toString());
	}

	public String[] setProperty4mDB(TestSuite testSuite, Sql con, String query, String[] columnName,
			String[] propertyName) throws Exception {
		String[] res = new String[10];
		List<GroovyRowResult> recipientIDQuery = con.rows(query);
		if (!(columnName.length == propertyName.length)) {
			throw new Exception("length of prop & column are not equal.");
		}
		for (int i = 0; i < columnName.length; i++) {
			res[i] = recipientIDQuery.get(0).getProperty(columnName[i]).toString();
			testSuite.setPropertyValue(propertyName[i], res[i]);
		}
		return res;
	}

	public String getValueAndSaveAsTestCaseProperty(XmlHolder holder, MessageExchange messageExchange, String xpath,
			String propertyName) throws XmlException {

		String Id = holder.getNodeValue(xpath);
		ModelItem modelItem = messageExchange.getModelItem();
		if (modelItem instanceof JdbcRequestTestStep) {
			JdbcRequestTestStep jdbcRequestTestStep = (JdbcRequestTestStep) modelItem;
			jdbcRequestTestStep.getTestStep().getTestCase().setPropertyValue(propertyName, Id);
			return Id;
		}
		return "";
	}

	public boolean verifyTimePeriod(String start, String end) throws ParseException {
		return verifyTimePeriod(start, end, 5);
	}

	public boolean verifyTimePeriod(String startStringFormat, String endStringFormat, int minutesDiff) throws ParseException {
		boolean verification = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDateFormat = dateFormat.parse(endStringFormat);
//		System.out.println(endDateFormat);
		Date startDateFormat = dateFormat.parse(startStringFormat);
//		System.out.println(startDateFormat);
		long difference = endDateFormat.getTime() - startDateFormat.getTime();
//		System.out.println(difference);
		long minutesdiff = TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS);
//		System.out.println(minutesdiff);
		if (minutesdiff < 5) {
//			System.out.println("pass");
			verification = true;
		} else {
			System.out.println("fail date is not less than "+ minutesDiff +" min");
			verification = false;
		}
//		System.out.println(verification);
		return verification;
	}

	public boolean verifyTimePeriod(XmlHolder holder, MessageExchange messageExchange, String startTestCasePropertyName,
			String endResponseXpath) throws ParseException, XmlException {
		String start = "";
		String msgDateTime = holder.getNodeValue(endResponseXpath);

		ModelItem modelItem = messageExchange.getModelItem();
		if (modelItem instanceof JdbcRequestTestStep) {
			JdbcRequestTestStep jdbcRequestTestStep = (JdbcRequestTestStep) modelItem;
			start = jdbcRequestTestStep.getTestStep().getTestCase().getPropertyValue(startTestCasePropertyName);
		}

		String end = msgDateTime;
		return verifyTimePeriod(start, end);
	}

	public boolean compareMessageString(XmlHolder holder, MessageExchange messageExchange,
			String expTestCasePropertyName, String actualXpath) throws XmlException {
		boolean verification = false;
		String expected = "";
		String actual = holder.getNodeValue(actualXpath).replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "")
				.trim();
		ModelItem modelItem = messageExchange.getModelItem();
		if (modelItem instanceof JdbcRequestTestStep) {
			JdbcRequestTestStep jdbcRequestTestStep = (JdbcRequestTestStep) modelItem;
			expected = jdbcRequestTestStep.getTestStep().getTestCase().getPropertyValue("ctraSentMessage")
					.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "").trim();
		}

		if (actual.equalsIgnoreCase(expected)) {

			verification = true;
		} else {

			verification = false;
		}
		return verification;
	}
}