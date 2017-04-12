/**
 * 
 */
package nl.coin.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hemasundar
 *
 */
public class Property {
    public String testSuiteName, testCaseName;
    public String testSuiteRunType;
    public String soapuiProjectPath, soapUIProjectRootFolder;
    public String fileSeparator, executionReportTemplatePath,
            testSuiteRowPath, testCaseRowPath, testCaseTemplatePath,
            testStepRowTemplatePath, HtmlReportPath, HtmlTemplatesPath,
            ExecutionReportPath, ScreenShotDirPath, reportZipPath,
            reportHistoryLocation, ObjectRepositoryFileLocation, testCaseDetailsTemplate;
    public String executionEnv;
    public String propertyFileLocation, suiteRunFileLocation, testDataFileLocation;
    public Map<String, String> globalVarMap;
    /* Values from custome.prop file */
    public String htmlTemplatesFolder, executionReportTemplate, testCaseDetailsTemplateFile, testCaseRowTemplate, testCaseTemplateName, testStepRowTemplate,
            testSuiteRowTemplate, tcDetailsTemplateFile = "",
            htmlReportFolder, historyReportFolder, executionReport, cssFileName, logFile, logFolder, screenShotFolder, objRepoFolder, objRepoFile,
            historyReportZipFolder, soapuiFolder, soapuiProjectFile, htmlReplaceString_testCaseDetails,
            htmlReplaceString_testCaseRow, htmlReplaceString_testStepRow, htmlReplaceString_testSuiteRow, browser;
	public String mainReportTemplateFilePath, mainReportFile;

    /**
     * 
     */
    public Property() {
	testSuiteName = "";
	testCaseName = "";
	fileSeparator = System.getProperty("file.separator");
	String projectBaseDir = System.getProperty("user.dir");
	propertyFileLocation = projectBaseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator
	        + "Custom.properties";
	suiteRunFileLocation = projectBaseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator
	        + "suiteRunTypes.properties";
	testDataFileLocation = projectBaseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator
	        + "testData.properties";
	globalVarMap = new HashMap<String, String>();

    }

    /**
     * 
     */
    public void prepareVariables() {
	fileSeparator = System.getProperty("file.separator");
	String projectBaseDir = System.getProperty("user.dir");
	// soapuiProjectFile = "COMP4.0-soapui-project.xml";
	/*
	 * SoapSuitePath = projectBaseDir + fileSeparator + "SoapProject" +
	 * fileSeparator + SoapSuiteName;
	 */

	soapUIProjectRootFolder = projectBaseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator
	        + soapuiFolder + fileSeparator;
	soapuiProjectPath = soapUIProjectRootFolder + soapuiProjectFile;
	HtmlReportPath = projectBaseDir + fileSeparator + "src" + fileSeparator + "test" + fileSeparator + "resources" + fileSeparator + htmlReportFolder
	        + fileSeparator;
	HtmlTemplatesPath = projectBaseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + htmlTemplatesFolder
	        + fileSeparator;
	executionReportTemplatePath = HtmlTemplatesPath + fileSeparator + executionReportTemplate;
	testSuiteRowPath = HtmlTemplatesPath + fileSeparator + testSuiteRowTemplate;
	testCaseDetailsTemplateFile = HtmlTemplatesPath + fileSeparator + tcDetailsTemplateFile;
	testCaseRowPath = HtmlTemplatesPath + fileSeparator + testCaseRowTemplate;
	testCaseTemplatePath = HtmlTemplatesPath + fileSeparator + testCaseTemplateName;
	testStepRowTemplatePath = HtmlTemplatesPath + fileSeparator + testStepRowTemplate;
	ExecutionReportPath = HtmlReportPath + executionReport;
	ScreenShotDirPath = projectBaseDir + fileSeparator + screenShotFolder + fileSeparator;
	reportZipPath = projectBaseDir + fileSeparator + historyReportZipFolder + fileSeparator + "Report_" + System.currentTimeMillis() + ".zip";
	reportHistoryLocation = projectBaseDir + fileSeparator + "src" + fileSeparator + "test" + fileSeparator + "resources" + fileSeparator
	        + historyReportFolder + fileSeparator + "Report_";
	ObjectRepositoryFileLocation = projectBaseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator
	        + objRepoFolder + fileSeparator + objRepoFile;
	
	mainReportTemplateFilePath = HtmlTemplatesPath + fileSeparator + mainReportFile;
    }
}