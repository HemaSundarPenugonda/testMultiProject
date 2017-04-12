package nl.coin.testScripts;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.config.OperationConfig;
import com.eviware.soapui.config.SettingsConfig;
import com.eviware.soapui.config.TestAssertionConfig;
import com.eviware.soapui.config.TestSuiteConfig;
import com.eviware.soapui.impl.settings.XmlBeansSettingsImpl;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.panels.teststeps.JdbcResponse;
import com.eviware.soapui.impl.wsdl.submit.transports.http.WsdlResponse;
import com.eviware.soapui.impl.wsdl.support.ExternalDependency;
import com.eviware.soapui.impl.wsdl.support.soap.SoapVersion;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestSuiteRunContext;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestSuiteRunner;
import com.eviware.soapui.impl.wsdl.teststeps.AMFRequestTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.AMFTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.JdbcTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.ManualTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.ManualTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.PropertyTransfersTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.PropertyTransfersTestStep.PropertyTransferResult;
import com.eviware.soapui.impl.wsdl.teststeps.RestRequestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlGotoTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageExchangeTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlMockResponseTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlSingleMessageExchangeTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepWithProperties;
import com.eviware.soapui.impl.wsdl.teststeps.assertions.TestAssertionRegistry.AssertableType;
import com.eviware.soapui.impl.wsdl.teststeps.registry.ProPlaceholderStepFactory.WsdlProPlaceholderTestStep;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.environment.EnvironmentListener;
import com.eviware.soapui.model.iface.Attachment;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.project.ProjectListener;
import com.eviware.soapui.model.testsuite.Assertable.AssertionStatus;
import com.eviware.soapui.model.testsuite.AssertedXPath;
import com.eviware.soapui.model.testsuite.ProjectRunListener;
import com.eviware.soapui.model.testsuite.TestAssertion;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestPropertyListener;
import com.eviware.soapui.model.testsuite.TestRunListener;
import com.eviware.soapui.model.testsuite.TestRunner.Status;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestStepResult.TestStepStatus;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.model.testsuite.TestSuite.TestSuiteRunType;
import com.eviware.soapui.model.testsuite.TestSuiteListener;
import com.eviware.soapui.model.testsuite.TestSuiteRunContext;
import com.eviware.soapui.model.testsuite.TestSuiteRunListener;
import com.eviware.soapui.model.testsuite.TestSuiteRunner;
import com.eviware.soapui.support.SoapUIException;
import com.eviware.soapui.support.XmlHolder;
import com.eviware.soapui.support.action.swing.ActionList;
import com.eviware.soapui.support.types.StringToObjectMap;
import com.eviware.soapui.support.types.StringToStringMap;
import com.eviware.soapui.support.types.StringToStringsMap;

import nl.coin.common.Property;
import nl.coin.common.TestCaseComparator;
import nl.coin.common.Utility;
import nl.coin.listeners.CustomEnvironmentListener;
import nl.coin.listeners.CustomProjectListener;
import nl.coin.listeners.CustomProjectRunListener;
import nl.coin.listeners.CustomPropertyChangeListener;
import nl.coin.listeners.CustomTestPropertyListener;
import nl.coin.listeners.CustomTestRunListener;
import nl.coin.listeners.CustomTestSuiteListener;
import nl.coin.listeners.CustomTestSuiteRunListener;
import nl.coin.reporting.CreateTestCaseDetails;
import nl.coin.reporting.CreateTestCaseRowReport;
import nl.coin.reporting.CreateTestStepRowReport;
import nl.coin.reporting.CreateTestSuiteRowReport;
import nl.coin.reporting.LogFile;
import nl.coin.reporting.TestCaseDetails;
import nl.coin.reporting.TestCaseRowDetails;
import nl.coin.reporting.TestStepRowDetails;
import nl.coin.reporting.TestSuiteRowDetails;

public class TestEngine {

	Utility utility;
	Property objProperty;
	LogFile objLog;
	String mainReportTemplate;

	@BeforeSuite
	public void beforeSuite(ITestContext ctx) {
		/*
		 * TestRunner runner = (TestRunner) ctx; runner.setOutputDirectory(
		 * "D:/Xebia_Projects/Projects/CoinAutomation/src/main/resources/TestNGOutput"
		 * );
		 */
//		set soapuiLog4j seetings to empty xml file
		System.setProperty("soapui.log4j.config","src/main/resources/log4j.xml");
		System.setProperty("projectfiledir",System.getProperty("user.dir") + "/src/main/resources/SOAPUIproject/");
		objLog = new LogFile();
		objLog.prepareLogger();
		objProperty = new Property();
		utility = new Utility(objLog, objProperty);
		objLog.writeDebug("Log4J object created successfully.");
		objLog.writeInfo("Log4J object created successfully.");

		// Setting the soapui log level to ERROR
		SoapUI.log.setLevel(Level.ERROR);

	}

	@BeforeTest
	public void beforeTest() {
		try {
			utility.collectKeyValuePair();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			utility.populateGlobalMap();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Properties prop = System.getProperties();// Get all the System prop
		utility.addExternalProperties(prop);

		Map<String, String> getenv = System.getenv();
		utility.addExternalProperties(getenv);

		assignProp2Variables();

		objProperty.prepareVariables();
		
		/* Deleting the existing HTML report files */
		File source = new File(objProperty.HtmlTemplatesPath + objProperty.cssFileName);
		boolean isDirectoryCleaned = utility.cleanDirectory(objProperty.HtmlReportPath);
		if (isDirectoryCleaned == true) {
			objLog.writeInfo("Previous report files in HTML Report directory cleaned properly.");
		} else {
			objLog.writeInfo("previous files in HTML Report dir are not deleted completely.");
		}
		boolean isFileCopied = utility.copyFile2Dir(source, new File(objProperty.HtmlReportPath));
		if (isFileCopied == true) {
			objLog.writeInfo("CSS file copied to HTML Report directory properly.");
		} else {
			objLog.writeInfo("CSS file not copied to HTML report directory properly");
		}
		mainReportTemplate = utility.readFile(objProperty.mainReportTemplateFilePath);
	}

	// Need to accept the parameter to Run testSuite in Suquential from xml file
	/* TestSuite needs to be sent from xml file as parameter */
	// @Parameters({ "TestSuite" })
	@SuppressWarnings({ "unchecked", "static-access" })
	@Test
	public void test() throws XmlException, IOException, SoapUIException {
		Properties testDataCountProp = new Properties();
		testDataCountProp.load(new FileInputStream(objProperty.testDataFileLocation));
		
		// objLog.writeInfo(objProperty.globalVarMap.toString());
		objProperty.executionEnv = objProperty.globalVarMap.get("environment");
		objLog.writeInfo("Execution environment is : " + objProperty.executionEnv);

		String executeAllTCs = objProperty.globalVarMap.get("executealltcs");
		objLog.writeInfo("executeAllTCs : " + executeAllTCs);

		String executeAllProjects = objProperty.globalVarMap.get("executeallprojects");
		objLog.writeInfo("executeAllProjects : " + executeAllProjects);
		
		String executeAllTestSuites = objProperty.globalVarMap.get("executealltestsuites");
		objLog.writeInfo("executeAllTestSuites : " + executeAllTestSuites);
		
		String executeAllTestData = objProperty.globalVarMap.get("executealltestdata");
		objLog.writeInfo("executeAllTestData : " + executeAllTestData);
		
		objProperty.testSuiteName = objProperty.globalVarMap.get("testsuite");
		objLog.writeInfo("TestSuite executing is : " + objProperty.testSuiteName);

		objProperty.testCaseName = objProperty.globalVarMap.get("testcase");
		objLog.writeInfo("TestCase executing is : " + objProperty.testCaseName);

		objProperty.testSuiteRunType = objProperty.globalVarMap.get("testsuiteruntype");
		objLog.writeInfo("TestSuite run type is : " + objProperty.testSuiteRunType);

		List<String> allProjects = new ArrayList<String>();
		if (executeAllProjects.equalsIgnoreCase("true")) {
			allProjects = utility.getAllProjects(objProperty.soapUIProjectRootFolder);

		} else {
			String[] splitProjects = objProperty.soapuiProjectFile.split(",");
			for (String indProject : splitProjects) {
				allProjects.add(objProperty.soapUIProjectRootFolder + indProject);
			}
//			allProjects.add(objProperty.soapuiProjectPath);
		}
		String lineNumber_Custom = objProperty.globalVarMap.get("linenumbercustom");
		for (String indProjectPath : allProjects) {

			WsdlProject project = new WsdlProject(indProjectPath);
			if (objProperty.globalVarMap.get("listeners").equalsIgnoreCase("true")) {

				addListeners(project);
			}
			//Set custom properties of project
			project = setProjectProperties(project);
			
			List<Object[]> testSuiteList = new ArrayList<Object[]>();
			
			if (executeAllTestSuites.equalsIgnoreCase("true") && executeAllTestData.equalsIgnoreCase("false")) {
				
				List<TestSuite> testSuiteListTotal = project.getTestSuiteList();
				for (TestSuite indTestSuite : testSuiteListTotal) {
					
					WsdlTestSuite wsdlTestSuite = (WsdlTestSuite)indTestSuite;
					if (!wsdlTestSuite.isDisabled()) {
						
						testSuiteList.add(new Object[]{wsdlTestSuite, lineNumber_Custom});
					}
				}
			} else if (executeAllTestSuites.equalsIgnoreCase("false") && executeAllTestData.equalsIgnoreCase("true")) {
				
				
//				WsdlTestSuite wsdlTestSuite = project.getTestSuiteByName(objProperty.testSuiteName);
				String[] split = objProperty.testSuiteName.split(",");
				for (String string : split) {
					WsdlTestSuite wsdlTestSuite = project.getTestSuiteByName(string);	
				
					if (wsdlTestSuite == null) {
						System.out.println("Mentioned test suite is not there in the project.\n\tProject: "+project.getName()+"\n\tTestSuite: "+objProperty.testSuiteName);
						continue;
					}
					Object object3 = testDataCountProp.get(wsdlTestSuite.getName());
					if (object3 != null) {
						String object2 = object3.toString();
						int parseInt = Integer.parseInt(object2);
						if (!wsdlTestSuite.isDisabled()) {
							for (int i = 0; i < parseInt; i++) {
								testSuiteList.add(new Object[]{wsdlTestSuite, i});	
							}
						}
					} else {
						testSuiteList.add(new Object[]{wsdlTestSuite, ""});
					}
				}
					
			} else if (executeAllTestSuites.equalsIgnoreCase("true") && executeAllTestData.equalsIgnoreCase("true")) {
				
				List<TestSuite> testSuiteListTotal = project.getTestSuiteList();
				for (TestSuite indTestSuite : testSuiteListTotal) {
					WsdlTestSuite wsdlTestSuite = (WsdlTestSuite)indTestSuite;
					Object object3 = testDataCountProp.get(indTestSuite.getName());
					if (object3 != null) {
						String object2 = object3.toString();
						int parseInt = Integer.parseInt(object2);
						if (!wsdlTestSuite.isDisabled()) {
							for (int i = 0; i < parseInt; i++) {
								testSuiteList.add(new Object[]{wsdlTestSuite, i});	
							}
						}
					} else {
						testSuiteList.add(new Object[]{wsdlTestSuite, ""});
					}					
				}
			} else {
				String[] split = objProperty.testSuiteName.split(",");
				for (String string : split) {
					WsdlTestSuite testSuiteByName = project.getTestSuiteByName(string);
					if (testSuiteByName == null) {
						continue;
					}
					testSuiteList.add(new Object[]{testSuiteByName, lineNumber_Custom});
				}
//				testSuiteList.add(new Object[]{project.getTestSuiteByName(objProperty.testSuiteName), lineNumber_Custom});
			}
			for (Object[] testSuiteObject : testSuiteList) {
				TestSuite testSuite = (TestSuite)testSuiteObject[0];
				String currentLineNumber_Custom = String.valueOf(testSuiteObject[1]);
				String currentTestSuiteName = testSuite.getName();
				WsdlTestSuite wsdlSuite = (WsdlTestSuite) testSuite;

				wsdlSuite.setPropertyValue("browser", objProperty.browser);
				//Set testSuite runType
				if (objProperty.testSuiteRunType.equalsIgnoreCase("default")) {
					Properties prop = new Properties();
					prop.load(new FileInputStream(objProperty.suiteRunFileLocation));

					Object object = prop.get(currentTestSuiteName);
					if (object == null) {
						object = "sequential";
					}
					TestSuiteRunType valueOf = TestSuiteRunType.valueOf(object.toString().toUpperCase());
					if (objProperty.globalVarMap.get("sequence2sec").equalsIgnoreCase("true")) {
						wsdlSuite.setPropertyValue("PollTime", "2000");
					}
					wsdlSuite.setRunType(valueOf);
				} else if (objProperty.testSuiteRunType.equalsIgnoreCase("parallel")) {

					wsdlSuite.setRunType(TestSuiteRunType.PARALLEL);
				} else {

					wsdlSuite.setRunType(TestSuiteRunType.SEQUENTIAL);
				}
				//Set line number of test suite
				TestProperty testDataLineNumber = wsdlSuite.getProperty("lineNumber");

				if (testDataLineNumber != null) {
					
					if (!currentLineNumber_Custom.equalsIgnoreCase("")) {
						wsdlSuite.setPropertyValue("lineNumber", currentLineNumber_Custom);
					}
					testDataLineNumber = wsdlSuite.getProperty("lineNumber");
				}
				//Set poll time of test suite
				String pollTime_Custom = objProperty.globalVarMap.get("polltimecustom");
				if (!pollTime_Custom.equalsIgnoreCase("")) {

					wsdlSuite.setPropertyValue("PollTime", pollTime_Custom);
				}

				StringToObjectMap context = new StringToObjectMap();
				TestSuiteRunner testSuiteRunner;
				WsdlTestCaseRunner run = null;
				List<TestCaseRunner> testCaseRunnerinSuite = new ArrayList<TestCaseRunner>();

				if (objProperty.testCaseName.equalsIgnoreCase("") || objProperty.testCaseName == null) {
					System.out.println("Testsuite started executing: "+wsdlSuite.getName());
					testSuiteRunner = wsdlSuite.run(context, false);
				} else {
					System.out.println("TestCases started executing: "+objProperty.testCaseName);
					testSuiteRunner = new WsdlTestSuiteRunner(wsdlSuite, context);
					TestSuiteRunContext runContext = new WsdlTestSuiteRunContext(testSuiteRunner, context);
					Object runSetupScript, runTearDownScript;
					try {
						runSetupScript = wsdlSuite.runSetupScript(runContext, testSuiteRunner);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					String[] allTestCasesByName = objProperty.testCaseName.split(",");
					for (String indTestCaseByName : allTestCasesByName) {
						WsdlTestCase testCaseByName = wsdlSuite.getTestCaseByName(indTestCaseByName);

						run = testCaseByName.run(context, false);
						testCaseRunnerinSuite.add(run);
					}
					try {
						runTearDownScript = wsdlSuite.runTearDownScript(runContext, testSuiteRunner);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				int testCasesCount = testSuiteRunner.getTestSuite().getTestCaseCount();
				long testSuiteStartTime = testSuiteRunner.getStartTime();
				long testSuiteTimeTaken = testSuiteRunner.getTimeTaken();
				Status testSuiteStatus = testSuiteRunner.getStatus();
				int testCasePassCount = 0, testCaseFailCount = 0;
				String testSuiteRemarks = ((testSuiteRunner.getReason() == null) ? "" : testSuiteRunner.getReason());
				String setupScript = (wsdlSuite.getSetupScript() == null) ? "" : wsdlSuite.getSetupScript();
				String tearDownScript = (wsdlSuite.getTearDownScript() == null) ? "" : wsdlSuite.getTearDownScript();

				List<TestProperty> suitePropertyList = wsdlSuite.getPropertyList();

				String suitePropMap = utility.properties2String(suitePropertyList);

				TestProperty suiteTestData = wsdlSuite.getProperty("testData");

				/* Read HTML templates for total Suite report & TestSuiteRow */
				String excetionReportTemplate = utility.readFile(objProperty.executionReportTemplatePath);
				
				/*******************************************************************************************************************/

				List<TestCaseRunner> allTestCaseRunner = testSuiteRunner.getResults();
				if (allTestCaseRunner.isEmpty()) {
					allTestCaseRunner = new ArrayList<TestCaseRunner>();
					allTestCaseRunner.addAll(testCaseRunnerinSuite);
				}
				TestCaseComparator testCaseComparator = new TestCaseComparator();
				Collections.sort(allTestCaseRunner, testCaseComparator);
				objLog.writeInfo(String.valueOf(allTestCaseRunner.size()));
				for (TestCaseRunner indTestCaseRunner : allTestCaseRunner) {
					int testStepPassCount = 0, testStepFailCount = 0;
					/* details for testCase table */
					WsdlTestCase indWsdlTestCase = ((WsdlTestCase) indTestCaseRunner.getTestCase());

					String testCaseName = indWsdlTestCase.getLabel();
					int testStepsCount = indWsdlTestCase.getTestStepCount();
					objLog.writeInfo("Test step count : " + testStepsCount);
					long testCaseStartTime = indTestCaseRunner.getStartTime();
					long testCaseTimeTaken = indTestCaseRunner.getTimeTaken();
					Status testCaseStatus = indTestCaseRunner.getStatus();

					String testCaseRemarks = ((indTestCaseRunner.getReason() == null) ? ""
							: indTestCaseRunner.getReason());

					List<TestProperty> testCasePropertyList = indWsdlTestCase.getPropertyList();
					String testCasePropMap = utility.properties2String(testCasePropertyList);

					if (testCaseStatus.name().equalsIgnoreCase("ok")
							|| testCaseStatus.name().equalsIgnoreCase("Finished")) {
						// testCaseRow = testCaseRow.replace("${statusClass}",
						// "pass");
						testCasePassCount++;
					} else if (testCaseStatus.name().equalsIgnoreCase("FAILED")) {
						// testCaseRow = testCaseRow.replace("${statusClass}",
						// "fail");
						testCaseFailCount++;
					}

					/**********************************************************
					 ******** Create a separate html file for test case********
					 *********************************************************/

					/* Read the HTML template for individual test case report */
					String testCaseTemplate = utility.readFile(objProperty.testCaseTemplatePath);

					/* Load Test case details */
					TestCaseDetails testCaseDetails = new TestCaseDetails();
					testCaseDetails.setTestCaseName(testCaseName);
					testCaseDetails.setStatus(testCaseStatus);
					testCaseDetails.setTestCaseStartTime(testCaseStartTime);
					testCaseDetails.setTestCaseTimeTaken(testCaseTimeTaken);
					testCaseDetails.setTestCaseExecutedOS(System.getProperty("os.name"));

					/* Write the data to HTML template string */
					CreateTestCaseDetails createTestCaseDetails = new CreateTestCaseDetails(utility, objProperty);
					createTestCaseDetails.creatingTestCaseDetails(testCaseDetails);
					testCaseTemplate = createTestCaseDetails
							.writeTestCaseDetails(objProperty.htmlReplaceString_testCaseDetails, testCaseTemplate);

					List<TestStepResult> allTestStepResultsInTestCase = indTestCaseRunner.getResults();

					// objLog.writeInfo(String.valueOf(allTestStepResultsInTestCase.size()));

					for (TestStepResult indTestStepResultInTestCase : allTestStepResultsInTestCase) {

						/* Get details of individual test step */
						TestStep testStep = indTestStepResultInTestCase.getTestStep();
						WsdlTestStep wsdlTestStep = ((WsdlTestStep) testStep);
						String projectName = wsdlTestStep.getProject().getName();
						List<TestProperty> testStepPropertyList = wsdlTestStep.getPropertyList();
						String testStepPropMap = utility.properties2String(testStepPropertyList);
						String testStepName = testStep.getLabel();
						long testStepTimeStamp = indTestStepResultInTestCase.getTimeStamp();
						long testStepTimeTaken = indTestStepResultInTestCase.getTimeTaken();
						TestStepStatus testStepStatus = indTestStepResultInTestCase.getStatus();
						String testStepRemarks = ((indTestStepResultInTestCase.getError() == null) ? ""
								: indTestStepResultInTestCase.getError().getMessage());
						String testStepResponse = "", testStepRequest = "", allAssertions_String = "";
						StringToStringMap properties2 = new StringToStringMap();

						WsdlTestStepWithProperties wsdlTestStepWithProperties = ((WsdlTestStepWithProperties) wsdlTestStep);
						Map<String, TestProperty> properties = wsdlTestStepWithProperties.getProperties();

						Set<String> keySet = properties.keySet();
						Properties prop1 = new Properties();

						for (String indKey : keySet) {
							if (!(indKey.equalsIgnoreCase("RawRequest") || indKey.equalsIgnoreCase("Response")
									|| indKey.equalsIgnoreCase("Request")
									|| indKey.equalsIgnoreCase("ResponseAsXml"))) {
								TestProperty indTestProperty = properties.get(indKey);
								String indValue = (indTestProperty.getValue() == null) ? ""
										: indTestProperty.getValue();
								prop1.setProperty(indKey, indValue);
							}

						}

						checkInstanceOfTestStep(testStep);
						if (indTestStepResultInTestCase instanceof AMFTestStepResult) {

							objLog.writeInfo("instance of AMFTestStepResult");
						} else if (indTestStepResultInTestCase instanceof JdbcTestStepResult) {

							objLog.writeInfo("instance of JdbcTestStepResult");
							JdbcTestStepResult jdbcTestStepResult = (JdbcTestStepResult) indTestStepResultInTestCase;
							// testStepRequest =
							// jdbcTestStepResult.getRequestContent();

							byte[] rawRequestData = (jdbcTestStepResult.getRawRequestData() == null) ? "".getBytes()
									: jdbcTestStepResult.getRawRequestData();
							testStepRequest = new String(rawRequestData);
							// objLog.writeInfo(testStepRequest);
							JdbcResponse jdbcResponse = jdbcTestStepResult.getResponse();
							try {
								testStepResponse = jdbcResponse.getContentAsString();
							} catch (NullPointerException e) {
								objLog.writeError("JDBS response is null");
								testStepResponse = "";
							}
							// objLog.writeInfo(testStepResponse);
							// objLog.writeInfo(jdbcResponse.getContentAsXml());
							JdbcRequestTestStep jdbcTestStep = ((JdbcRequestTestStep) jdbcTestStepResult.getTestStep());

							List<TestAssertion> assertionList = jdbcTestStep.getAssertionList();
							for (TestAssertion indTestAssertion : assertionList) {
								allAssertions_String = allAssertions_String + "Assertion: \t ";

								WsdlMessageAssertion test = ((WsdlMessageAssertion) indTestAssertion);
								TestAssertionConfig config = test.getConfig();
								AssertionStatus status = test.getStatus();
								allAssertions_String = allAssertions_String + status + "\n \n";
								allAssertions_String = allAssertions_String + config.toString();

								// XmlObject configuration =
								// test.getConfiguration();
								// objLog.writeInfo("test step");
								allAssertions_String = allAssertions_String + "\n \n \n";
							}
							// objLog.writeInfo(allAssertions_String);
							objLog.writeInfo("Sample step");

						} else if (indTestStepResultInTestCase instanceof ManualTestStepResult) {

							objLog.writeInfo("instance of ManualTestStepResult");
						} else if (indTestStepResultInTestCase instanceof PropertyTransferResult) {

							objLog.writeInfo("instance of PropertyTransferResult");
						} else if (indTestStepResultInTestCase instanceof RestRequestStepResult) {

							objLog.writeInfo("instance of RestRequestStepResult");
						} else if (indTestStepResultInTestCase instanceof WsdlMessageExchangeTestStepResult) {

							objLog.writeInfo("instance of WsdlMessageExchangeTestStepResult");
						} else if (indTestStepResultInTestCase instanceof WsdlSingleMessageExchangeTestStepResult) {

							objLog.writeInfo("instance of WsdlSingleMessageExchangeTestStepResult");
						} else if (indTestStepResultInTestCase instanceof WsdlTestRequestStepResult) {

							objLog.writeInfo("instance of WsdlTestRequestStepResult");
							WsdlTestRequestStepResult wsdlTestRequestStepResult = (WsdlTestRequestStepResult) indTestStepResultInTestCase;
							WsdlResponse response = wsdlTestRequestStepResult.getResponse();
							try {
								testStepResponse = response.getContentAsString();
							} catch (NullPointerException e) {
								objLog.writeError("SOAP response is null");
								testStepResponse = "";
							}
							
							/* Get request after filling property values */
							testStepRequest = wsdlTestRequestStepResult.getRequestContentAsXml();
							// objLog.writeInfo(testStepRequest);

							WsdlTestRequestStep soapTestStep = ((WsdlTestRequestStep) indTestStepResultInTestCase
									.getTestStep());

							List<TestAssertion> assertionList = soapTestStep.getAssertionList();
							for (TestAssertion indTestAssertion : assertionList) {
								allAssertions_String = allAssertions_String + "Assertion: \t ";

								WsdlMessageAssertion test = ((WsdlMessageAssertion) indTestAssertion);
								TestAssertionConfig config = test.getConfig();
								AssertionStatus assertionStatus = test.getStatus();
								allAssertions_String = allAssertions_String + assertionStatus + "\n \n";
								allAssertions_String = allAssertions_String + config.toString();

								allAssertions_String = allAssertions_String + "\n \n \n";
							}

						} else if (testStep instanceof WsdlGroovyScriptTestStep) {
							WsdlGroovyScriptTestStep groovyStep = ((WsdlGroovyScriptTestStep) testStep);
							if (!(testStepStatus.name().equalsIgnoreCase("ok"))) {

								String fileSeparator = System.getProperty("file.separator");
								String name = utility.createProperFileName(projectName + "_" + currentTestSuiteName
										+ "_" + testCaseName + "_" + testStepName);
								String screenshotPath;
								String jenkinsUrl = System.getenv("JENKINS_URL");
								String workSpace = System.getenv("WORKSPACE");
								String jobName = System.getenv("JOB_NAME");

								if (jenkinsUrl == null || jenkinsUrl.equalsIgnoreCase("")) {
									screenshotPath = System.getProperty("user.dir") + fileSeparator + "src"
											+ fileSeparator + "test" + fileSeparator + "resources" + fileSeparator
											+ "HTML_Report" + fileSeparator + "Screenshots" + fileSeparator + name
											+ ".png";
									screenshotPath = screenshotPath.replace(" ", "");
								} else {
									screenshotPath = jenkinsUrl + fileSeparator + "job" + fileSeparator + jobName
											+ fileSeparator + "ws" + fileSeparator + "src" + fileSeparator + "test"
											+ fileSeparator + "resources" + fileSeparator + "HTML_Report"
											+ fileSeparator + "Screenshots" + fileSeparator + name + ".png";
									screenshotPath = screenshotPath.replace(" ", "");
								}

								objLog.writeInfo("Screenshot file path set :" + screenshotPath);

								String screenShotLink = "<a href=\"" + screenshotPath + "\">Screenshot</a>";
								testStepRemarks = testStepRemarks + "\n" + screenShotLink;
							}
							testStepRequest = groovyStep.getScript();

						} else {

							objLog.writeInfo("Step result not an instance of any thing mentioned.");
						}

						if (testStepStatus.name().equalsIgnoreCase("ok")
								|| testStepStatus.name().equalsIgnoreCase("Finished")) {

							testStepPassCount++;
						} else if (testStepStatus.name().equalsIgnoreCase("FAILED")) {

							testStepFailCount++;
						}

						/* Fill the TestStepRowDetails class object values */
						TestStepRowDetails testStepRowDetails = new TestStepRowDetails();
						testStepRowDetails.setTestStepName(testStepName);
						testStepRowDetails.setStatus(testStepStatus);
						testStepRowDetails.setStartTime(testStepTimeStamp);
						testStepRowDetails.setTimeTaken(testStepTimeTaken);
						testStepRowDetails.setRequest(testStepRequest);
						testStepRowDetails.setResponse(testStepResponse);
						testStepRowDetails.setAssertions(allAssertions_String);
						testStepRowDetails.setRemarks(testStepRemarks);

						CreateTestStepRowReport createTestStepRowReport = new CreateTestStepRowReport(utility,
								objProperty);
						createTestStepRowReport.creatingTestStepRow(testStepRowDetails);
						testCaseTemplate = createTestStepRowReport
								.writeTestStepRow(objProperty.htmlReplaceString_testStepRow, testCaseTemplate);
						/*
						 * Writing the test step execution info to a text file &
						 * saving it into Html report location
						 */
						String stepResultPath = objProperty.HtmlReportPath
								+ utility.createProperFileName(currentTestSuiteName) + objProperty.fileSeparator
								+ utility.createProperFileName(testCaseName) + objProperty.fileSeparator
								+ utility.createProperFileName(testStepName) + ".txt";
						utility.createFile(stepResultPath);
						PrintWriter writer = new PrintWriter(stepResultPath);
						indTestStepResultInTestCase.writeTo(writer);
						writer.flush();
						writer.close();

					}

					/*
					 * Write the individual test case report to
					 * teastCaseName.html file
					 */
					String testCaseReportPath = objProperty.HtmlReportPath + "/" + utility.createProperFileName(currentTestSuiteName) + "/" + utility.createProperFileName(testCaseName)
							+ ".html";
					boolean writeFile = utility.writeFile(testCaseReportPath, testCaseTemplate);
					if (writeFile) {
						objLog.writeInfo("Writing individual HTML file test case Report is successful");
					}

					/* Fill the TestCaseRowDetails class object */
					TestCaseRowDetails testCaseRowDetails = new TestCaseRowDetails();
					testCaseRowDetails.setTestSuiteName(currentTestSuiteName);
					testCaseRowDetails.setTestCaseName(testCaseName);
					testCaseRowDetails.setTestStepsCount(testStepsCount);
					testCaseRowDetails.setStartTime(testCaseStartTime);
					testCaseRowDetails.setTimeTaken(testCaseTimeTaken);
					testCaseRowDetails.setStatus(testCaseStatus);
					testCaseRowDetails.setRemarks(testCaseRemarks);
					testCaseRowDetails.setProperties(testCasePropMap.toString());
					testCaseRowDetails.setPassCount(testStepPassCount);
					testCaseRowDetails.setFailCount(testStepFailCount);

					/* Replace the TestCaseRow in total suite report */
					CreateTestCaseRowReport createTestCaseReport = new CreateTestCaseRowReport(utility, objProperty);
					createTestCaseReport.creatingTestCaseRow(testCaseRowDetails);
					excetionReportTemplate = createTestCaseReport
							.writeTestCaseRowReport(objProperty.htmlReplaceString_testCaseRow, excetionReportTemplate);
					// removeTCProperties(indTestCaseRunner,
					// testCasePropertyList);
				}

				/* Filling the TestSuiteRowDetails class object */
				TestSuiteRowDetails testSuiteRowDetails = new TestSuiteRowDetails();
				testSuiteRowDetails.setSuiteName(currentTestSuiteName);
				testSuiteRowDetails.setTestCasesCount(testCasesCount);
				testSuiteRowDetails.setStartTime(testSuiteStartTime);
				testSuiteRowDetails.setTimeTaken(testSuiteTimeTaken);
				testSuiteRowDetails.setStatus(testSuiteStatus);
				testSuiteRowDetails.setRemarks(testSuiteRemarks);
				testSuiteRowDetails.setSetUpScript(setupScript);
				testSuiteRowDetails.setTearDownScript(tearDownScript);
				testSuiteRowDetails.setProperties(suitePropMap.toString());
				try {
					testSuiteRowDetails.setTestData("LineNumber : " + testDataLineNumber.getValue() + "\nTestData : "
							+ suiteTestData.getValue());
				} catch (NullPointerException e) {
					testSuiteRowDetails.setTestData("No line number parameter displayed for testSuite");
				}
				testSuiteRowDetails.setPassCount(testCasePassCount);
				testSuiteRowDetails.setFailCount(testCaseFailCount);

				CreateTestSuiteRowReport createTestSuiteRowReport = new CreateTestSuiteRowReport(utility, objProperty);
				createTestSuiteRowReport.creatingTestSuiteRow(testSuiteRowDetails);

				/* Replace the TestSuiteRow in total suite report */
				excetionReportTemplate = createTestSuiteRowReport
						.writeTestSuiteRow(objProperty.htmlReplaceString_testSuiteRow, excetionReportTemplate);

				/* Write the total suite report to ExecutionReport.html file */
				mainReportTemplate = mainReportTemplate.replace("<!--${mainReport}-->", excetionReportTemplate);
				/*
				 * boolean writeFile =
				 * utility.writeFile(objProperty.ExecutionReportPath,
				 * excetionReportTemplate); if (writeFile) { objLog.writeInfo(
				 * "Writing HTML file Execution Report is successful"); }
				 */
				// removeCustomProperties(testSuiteRunner);
			}

//			project.save();
		}
		boolean writeFile = utility.writeFile(objProperty.ExecutionReportPath, mainReportTemplate);
		if (writeFile) {
			objLog.writeInfo("Writing HTML file Execution Report is successful");
		}
		/* Prepare history report folder name */
		// objProperty.reportHistoryLocation = objProperty.reportHistoryLocation
		// + currentTestSuiteName + "_"
		// + lineNumber_final + "_" + System.currentTimeMillis();
		objProperty.reportHistoryLocation = objProperty.reportHistoryLocation + "_" + System.currentTimeMillis();
	}

	private WsdlProject setProjectProperties(WsdlProject project) {
		project.setPropertyValue("DBUser", "comp");
		project.setPropertyValue("DBPassword", "comp");
		project.setPropertyValue("directory", objProperty.soapUIProjectRootFolder);
		project.setPropertyValue("GUIUrl", objProperty.executionEnv);
		if (objProperty.executionEnv.equalsIgnoreCase("https://testComp.coin.nl/comp/")) {

			project.setPropertyValue("DBHostname", "127.0.0.1");
			project.setPropertyValue("DBDatabase", "comp");
			project.setPropertyValue("DBPort", "8088");
			project.setPropertyValue("SoapUrl", "https://testComp.coin.nl:4444/comp/MessageService");
		} else if (objProperty.executionEnv.equalsIgnoreCase("http://192.168.100.21:8080/compv4_1/")) {

			project.setPropertyValue("DBHostname", "192.168.100.21");
			project.setPropertyValue("DBDatabase", "comp");
			project.setPropertyValue("DBPort", "5432");
			project.setPropertyValue("SoapUrl", "http://192.168.100.21:8080/compv4_1/MessageService");
		} else if (objProperty.executionEnv.equalsIgnoreCase("http://localhost:8080/compv3_4/")
				|| objProperty.executionEnv.equalsIgnoreCase("http://127.0.0.1:8080/compv3_4/")) {

			project.setPropertyValue("DBHostname", "127.0.0.1");
			project.setPropertyValue("DBDatabase", "compv3_4");
			project.setPropertyValue("DBPort", "5432");
			project.setPropertyValue("SoapUrl", "http://localhost:8080/compv3_4/MessageService");
		} else if (objProperty.executionEnv.equalsIgnoreCase("http://192.168.100.21:8088/compv4_0/")) {

			project.setPropertyValue("DBHostname", "192.168.100.21");
			project.setPropertyValue("DBDatabase", "comp");
			project.setPropertyValue("DBPort", "5432");
			project.setPropertyValue("SoapUrl", "http://192.168.100.21:8088/compv4_0/MessageService");
		} else if (objProperty.executionEnv.equalsIgnoreCase("http://192.168.100.21:8088/compv4_1/")) {

			project.setPropertyValue("DBHostname", "192.168.100.21");
			project.setPropertyValue("DBDatabase", "comp");
			project.setPropertyValue("DBPort", "5432");
			project.setPropertyValue("SoapUrl", "http://192.168.100.21:8088/compv4_1/MessageService");
		} else if (objProperty.executionEnv.equalsIgnoreCase("http://192.168.100.21:8088/comp/")) {

			project.setPropertyValue("DBHostname", "192.168.100.21");
			project.setPropertyValue("DBDatabase", "comp");
			project.setPropertyValue("DBPort", "5432");
			project.setPropertyValue("SoapUrl", "http://192.168.100.21:8088/comp/MessageService");
		} else if (objProperty.executionEnv.equalsIgnoreCase("http://61.12.1.197:8080/comp/")) {

			project.setPropertyValue("DBHostname", "61.12.1.197");
			project.setPropertyValue("DBDatabase", "comp");
			project.setPropertyValue("DBPort", "5432");
			project.setPropertyValue("SoapUrl", "http://61.12.1.197:8080/compv4_1/MessageService");
		} else if (objProperty.executionEnv.equalsIgnoreCase("http://61.12.1.197:8088/comp/")) {

			project.setPropertyValue("DBHostname", "61.12.1.197");
			project.setPropertyValue("DBDatabase", "comp");
			project.setPropertyValue("DBPort", "5432");
			project.setPropertyValue("SoapUrl", "http://61.12.1.197:8088/comp/MessageService");
		} else if (objProperty.executionEnv.equalsIgnoreCase("https://testComp.coin.nl/compv4_1/")) {

			project.setPropertyValue("DBHostname", "127.0.0.1");
			project.setPropertyValue("DBDatabase", "compv4_0");
			project.setPropertyValue("DBPort", "8088");
			project.setPropertyValue("SoapUrl", "https://testComp.coin.nl:4444/compv4_1/MessageService");
		}
		return project;
	}

	/**
	 * @param testSuiteRunner
	 * 
	 */
	private void removeCustomProperties(TestSuiteRunner testSuiteRunner) {
		TestSuite wsdlSuite = testSuiteRunner.getTestSuite();
		List<TestProperty> suitePropertyList = wsdlSuite.getPropertyList();
		for (TestProperty testSuiteProperty : suitePropertyList) {
			String name = testSuiteProperty.getName();
			if (name.equalsIgnoreCase("testData") || name.equalsIgnoreCase("recipient")
					|| name.equalsIgnoreCase("recipientUserName") || name.equalsIgnoreCase("recipientPassWord")
					|| name.equalsIgnoreCase("donor") || name.equalsIgnoreCase("donorUserName")
					|| name.equalsIgnoreCase("donorPassword") || name.equalsIgnoreCase("soapVersion")
					|| name.equalsIgnoreCase("dossierType") || name.equalsIgnoreCase("compensationOK")
					|| name.equalsIgnoreCase("donorSoapVersion") || name.equalsIgnoreCase("donorDossierType")
					|| name.equalsIgnoreCase("businessGuiOnly")) {
				testSuiteProperty.setValue("");
			}
		}
		List<TestCaseRunner> allTestCaseRunner = testSuiteRunner.getResults();
		for (TestCaseRunner testCaseRunner : allTestCaseRunner) {
			testCaseRunner.getRunContext().removeProperty(testCaseRunner.getTestCase().getName());
			// testProperty.setValue("");

		}
	}

	/**
	 * @param indTestCaseRunner
	 * @param testCasePropertyList
	 */
	public void removeTCProperties(TestCaseRunner indTestCaseRunner, List<TestProperty> testCasePropertyList) {
		for (TestProperty testProperty : testCasePropertyList) {
			String name = testProperty.getName();
			TestCaseRunContext runContext = indTestCaseRunner.getRunContext();
			Object removeProperty = runContext.removeProperty(name);
			objLog.writeInfo(testProperty.getValue());
		}
	}

	/**
	 * @param project
	 */
	public void addListeners(WsdlProject project) {
		EnvironmentListener environmentListener = new CustomEnvironmentListener();
		ProjectListener projectListener = new CustomProjectListener();
		ProjectRunListener projectRunListener = new CustomProjectRunListener();
		PropertyChangeListener propertyChangeListener = new CustomPropertyChangeListener();
		String str = "";
		TestPropertyListener testPropertyListener = new CustomTestPropertyListener();
		TestSuiteRunListener testSuiteRunListener = new CustomTestSuiteRunListener();
		TestSuiteListener testSuiteListener = new CustomTestSuiteListener();
		TestRunListener testRunListener = new CustomTestRunListener();

		/* Add Project listeners */
		project.addEnvironmentListener(environmentListener);
		project.addProjectListener(projectListener);
		project.addProjectRunListener(projectRunListener);
		project.addPropertyChangeListener(propertyChangeListener);
		project.addPropertyChangeListener(str, propertyChangeListener);
		project.addTestPropertyListener(testPropertyListener);
		List<TestSuite> testSuiteList = project.getTestSuiteList();
		for (TestSuite indTestSuite : testSuiteList) {
			/* Add test suite listeners */
			indTestSuite.addPropertyChangeListener(propertyChangeListener);
			indTestSuite.addPropertyChangeListener(str, propertyChangeListener);
			indTestSuite.addTestPropertyListener(testPropertyListener);
			indTestSuite.addTestSuiteListener(testSuiteListener);
			indTestSuite.addTestSuiteRunListener(testSuiteRunListener);
			List<TestCase> testCaseList = indTestSuite.getTestCaseList();
			for (TestCase indTestCase : testCaseList) {
				/* Add test case listeners */
				indTestCase.addPropertyChangeListener(propertyChangeListener);
				indTestCase.addPropertyChangeListener(str, propertyChangeListener);
				indTestCase.addTestPropertyListener(testPropertyListener);
				indTestCase.addTestRunListener(testRunListener);
				List<TestStep> testStepList = indTestCase.getTestStepList();
				for (TestStep indTestStep : testStepList) {
					indTestStep.addPropertyChangeListener(propertyChangeListener);
					indTestStep.addPropertyChangeListener(str, propertyChangeListener);
					indTestStep.addTestPropertyListener(testPropertyListener);
				}
			}
		}
	}

	@AfterTest
	public void afterTest() {
		// utility.zipReport(objProperty.HtmlReportPath,
		// objProperty.reportZipPath);
		utility.copyDir2Dir(objProperty.HtmlReportPath, objProperty.reportHistoryLocation);
	}

	@AfterSuite
	public void afterSuite() {

	}

	/**
	 * @param testStep
	 * 
	 */
	private void checkInstanceOfTestStep(TestStep testStep) {
		if (testStep instanceof AMFRequestTestStep) {
			objLog.writeInfo("testSTep is instance of AMFRequestTestStep");
		} else if (testStep instanceof HttpTestRequestStep) {
			objLog.writeInfo("testStep is instance of HttpTestRequestStep");
		} else if (testStep instanceof JdbcRequestTestStep) {
			objLog.writeInfo("testStep is instance of JdbcRequestTestStep");
		} else if (testStep instanceof ManualTestStep) {
			objLog.writeInfo("testStep is instance of ManualTestStep");
		} else if (testStep instanceof PropertyTransfersTestStep) {
			objLog.writeInfo("testSTep is instance of PropertyTransfersTestStep");
		} else if (testStep instanceof RestTestRequestStep) {
			objLog.writeInfo("testStep is instance of RestTestRequestStep");
		} else if (testStep instanceof WsdlDelayTestStep) {
			objLog.writeInfo("testStep is instance of WsdlDelayTestStep");
		} else if (testStep instanceof WsdlGotoTestStep) {
			objLog.writeInfo("testStep is instance of WsdlGotoTestStep");
		} else if (testStep instanceof WsdlGroovyScriptTestStep) {
			objLog.writeInfo("testStep is instance of WsdlGroovyScriptTestStep");
		} else if (testStep instanceof WsdlMockResponseTestStep) {
			objLog.writeInfo("testStep is instance of WsdlMockResponseTestStep");
		} else if (testStep instanceof WsdlProPlaceholderTestStep) {
			objLog.writeInfo("testStep is instance of WsdlProPlaceholderTestStep");
		} else if (testStep instanceof WsdlTestRequestStep) {
			objLog.writeInfo("testStep is instance of WsdlTestRequestStep");
		}
	}

	/**
	 * 
	 */
	private void assignProp2Variables() {
		objProperty.htmlTemplatesFolder = objProperty.globalVarMap.get("html.templates.folder".toLowerCase());
		objProperty.executionReportTemplate = objProperty.globalVarMap.get("html.template.entiresuite".toLowerCase());
		objProperty.mainReportFile = objProperty.globalVarMap.get("html.template.main".toLowerCase());
		objProperty.tcDetailsTemplateFile = objProperty.globalVarMap.get("html.template.testCaseDetails".toLowerCase());
		objProperty.testCaseRowTemplate = objProperty.globalVarMap.get("html.template.testCaseRow".toLowerCase());
		objProperty.testCaseTemplateName = objProperty.globalVarMap.get("html.template.testcase".toLowerCase());
		objProperty.testStepRowTemplate = objProperty.globalVarMap.get("html.template.testStepRow".toLowerCase());
		objProperty.testSuiteRowTemplate = objProperty.globalVarMap.get("html.template.testSuiteRow".toLowerCase());
		objProperty.htmlReportFolder = objProperty.globalVarMap.get("html.report.folder".toLowerCase());
		objProperty.historyReportFolder = objProperty.globalVarMap.get("html.report.history".toLowerCase());
		objProperty.historyReportZipFolder = objProperty.globalVarMap.get("html.report.historyZip".toLowerCase());
		objProperty.executionReport = objProperty.globalVarMap.get("html.report.main".toLowerCase());
		objProperty.cssFileName = objProperty.globalVarMap.get("html.report.css".toLowerCase());
		objProperty.logFile = objProperty.globalVarMap.get("text.report.file".toLowerCase());
		objProperty.logFolder = objProperty.globalVarMap.get("text.report.folder".toLowerCase());
		objProperty.screenShotFolder = objProperty.globalVarMap.get("folder.screenShots".toLowerCase());
		objProperty.objRepoFolder = objProperty.globalVarMap.get("folder.objectRepo".toLowerCase());
		objProperty.objRepoFile = objProperty.globalVarMap.get("file.objectRepo".toLowerCase());
		objProperty.soapuiFolder = objProperty.globalVarMap.get("folder.soapui.project".toLowerCase());
		objProperty.soapuiProjectFile = objProperty.globalVarMap.get("file.soapui.project".toLowerCase());
		objProperty.htmlReplaceString_testCaseDetails = objProperty.globalVarMap
				.get("replace.html.testCaseDetails".toLowerCase());
		objProperty.htmlReplaceString_testCaseRow = objProperty.globalVarMap
				.get("replace.html.testCaseRow".toLowerCase());
		objProperty.htmlReplaceString_testStepRow = objProperty.globalVarMap
				.get("replace.html.testStepRow".toLowerCase());
		objProperty.htmlReplaceString_testSuiteRow = objProperty.globalVarMap
				.get("replace.html.testSuiteRow".toLowerCase());
		objProperty.browser = objProperty.globalVarMap.get("browser".toLowerCase());
	}
}