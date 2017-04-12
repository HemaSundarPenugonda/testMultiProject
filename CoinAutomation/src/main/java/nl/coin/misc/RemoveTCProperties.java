/**
 * 
 */
package nl.coin.misc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.model.testsuite.TestSuite.TestSuiteRunType;
import com.eviware.soapui.support.SoapUIException;

/**
 * @author hemasundar
 *
 */
public class RemoveTCProperties {

	private static String soapUIProjectDir;
	private static List<String> allProjects = new ArrayList<String>();

	/**
	 * @param args
	 * @throws IOException
	 * @throws SoapUIException
	 * @throws XmlException
	 */
	public static void main(String[] args) throws IOException, XmlException, SoapUIException {

		List<String> allProjects = new ArrayList<String>();
		allProjects = getAllProjects();
		List<String> testSuiteProp = new ArrayList<String>();
		testSuiteProp.add("DBDatabase".toLowerCase());
		testSuiteProp.add("DBHostname".toLowerCase());
		testSuiteProp.add("DBPassword".toLowerCase());
		testSuiteProp.add("DBPort".toLowerCase());
		testSuiteProp.add("DBUser".toLowerCase());
		testSuiteProp.add("Inactive Donor".toLowerCase());
		testSuiteProp.add("Inactive Recipient".toLowerCase());
		testSuiteProp.add("LineNumber".toLowerCase());
		testSuiteProp.add("MaxBusinessDays".toLowerCase());
		testSuiteProp.add("MaxDays".toLowerCase());
		testSuiteProp.add("MinDays".toLowerCase());
		testSuiteProp.add("PollTime".toLowerCase());
		testSuiteProp.add("InterfaceType".toLowerCase());
		testSuiteProp.add("RepairTime".toLowerCase());
		testSuiteProp.add("TimestampNOW".toLowerCase());
		testSuiteProp.add("testdatafile".toLowerCase());
		testSuiteProp.add("browser".toLowerCase());
		testSuiteProp.add("correctEmail".toLowerCase());
		testSuiteProp.add("singleDomain".toLowerCase());
		testSuiteProp.add("multiDomain".toLowerCase());
		testSuiteProp.add("correctEmail".toLowerCase());
		testSuiteProp.add("wrongEmail".toLowerCase());

		for (String indProjectFileName : allProjects) {

			WsdlProject project = new WsdlProject(indProjectFileName);
			project.setPropertyValue("UserIP", "");
			project.setPropertyValue("directory", "");

			project.setPropertyValue("SoapUrl", "");
			project.setPropertyValue("DBHostname", "");
			project.setPropertyValue("DBPort", "");
			project.setPropertyValue("DBUser", "");
			project.setPropertyValue("DBPassword", "");
			project.setPropertyValue("DBDatabase", "");
			project.setPropertyValue("GUIUrl", "");

			List<TestProperty> projectPropertyList = project.getPropertyList();

			/* Get all testSuites from project */
			List<TestSuite> testSuiteList = project.getTestSuiteList();

			/* for loop for each testSuite */
			for (TestSuite indTestsuite : testSuiteList) {
				WsdlTestSuite wsdlSuite = (WsdlTestSuite) indTestsuite;
				wsdlSuite.setRunType(TestSuiteRunType.SEQUENTIAL);
				/* Get the property list for the current testSuite */
				List<TestProperty> suitePropertyList = wsdlSuite.getPropertyList();

				/* for loop for each testSuite property */
				for (TestProperty suiteTestProperty : suitePropertyList) {
					String suiteName = wsdlSuite.getName();
					String propertyName = suiteTestProperty.getName();
					/*
					 * Properties not changed/touched: PollTime, lineNumber,
					 * SoapUrl
					 */
					/*
					 * Remove the properties: testData, recipient,
					 * recipientUserName, recipientPassWord, donor,
					 * donorUserName, donorPassword, soapVersion, dossierType,
					 * compensationOK, donorSoapVersion, donorDossierType,
					 * businessGuiOnly
					 */
					if (!testSuiteProp.contains(propertyName.toLowerCase())) {
						
						/*}
					if (propertyName.equalsIgnoreCase("testData") || propertyName.equalsIgnoreCase("recipient")
							|| propertyName.equalsIgnoreCase("recipientUserName")
							|| propertyName.equalsIgnoreCase("recipientPassWord")
							|| propertyName.equalsIgnoreCase("donor") || propertyName.equalsIgnoreCase("donorUserName")
							|| propertyName.equalsIgnoreCase("donorPassword")
							|| propertyName.equalsIgnoreCase("soapVersion")
							|| propertyName.equalsIgnoreCase("dossierType")
							|| propertyName.equalsIgnoreCase("compensationOK")
							|| propertyName.equalsIgnoreCase("donorSoapVersion")
							|| propertyName.equalsIgnoreCase("donorDossierType")
							|| propertyName.equalsIgnoreCase("businessGuiOnly")
							|| propertyName.equalsIgnoreCase("recipientId")
							|| propertyName.equalsIgnoreCase("donorId")) {*/

						wsdlSuite.removeProperty(propertyName);
						// suiteTestProperty.setValue("");
						System.out.println("Removed TestSuite property:\n\tTescase: " + suiteName + "\n\tProperty:"
								+ propertyName);
					} else if (propertyName.equalsIgnoreCase("linenumber")) {
						wsdlSuite.setPropertyValue(propertyName, "0");
					}
				}

				/* Get all the test cases from current test suite */
				List<TestCase> allTestCaseList = wsdlSuite.getTestCaseList();

				/* for loop for for each test case in current testSuite */
				for (TestCase testCase : allTestCaseList) {

					WsdlTestCase wsdlTestCase = ((WsdlTestCase) testCase);

					/* Get all properties of the current test case */
					List<TestProperty> testCasePropertyList = testCase.getPropertyList();

					/* for loop for each property of current test case */
					for (TestProperty testProperty : testCasePropertyList) {
						String testCaseName = testCase.getName();
						String propertyName = testProperty.getName();

						/* remove current property of test case property */
						wsdlTestCase.removeProperty(propertyName);
						// testProperty.setValue("");
						System.out.println("Removed TestCase property:\n\tTescase: " + testCaseName + "\n\tProperty:"
								+ propertyName);
					}
				}
			}
			project.save();
			project.release();
		}
		System.out.println("Completed execution.");
	}

	public static List<String> getAllProjects() {
		soapUIProjectDir = System.getProperty("user.dir") + "/src/main/resources/SOAPUIproject/";
		File parentFile = new File(soapUIProjectDir);

		File[] listFiles = parentFile.listFiles();
		for (File indFile : listFiles) {
			String path = indFile.getPath();
			String name = indFile.getName();
			if (path.endsWith(".xml") && !name.equalsIgnoreCase("COMP4.0-soapui-project.xml")
					&& !name.equalsIgnoreCase("project-temp-1305245174697441940.xml")) {
				allProjects.add(path);
			}

		}
		System.out.println(allProjects);
		return allProjects;
	}
}