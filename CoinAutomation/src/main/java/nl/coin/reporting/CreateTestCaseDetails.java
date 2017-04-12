/**
 * 
 */
package nl.coin.reporting;

import nl.coin.common.Property;
import nl.coin.common.Utility;

/**
 * @author hemasundar
 *
 */
public class CreateTestCaseDetails {
    private Utility utility;
    private String testCaseDetailsTemplate;

    public synchronized String getTestCaseDetailsTemplate() {
	return testCaseDetailsTemplate;
    }

    public CreateTestCaseDetails(Utility utility, Property objProperty) {
	this.utility = utility;
	// testSuiteRow = utility.readFile(objProperty.testSuiteRowPath);
	/*
	 * Need to create a separate template file for testcase details in
	 * individual test case report
	 */
	testCaseDetailsTemplate = utility.readFile(objProperty.testCaseDetailsTemplateFile);
    }

    /**
     * 
     */
    public void creatingTestCaseDetails(TestCaseDetails testCaseDetails) {
	testCaseDetailsTemplate = testCaseDetailsTemplate.replace("${TestCaseName}", testCaseDetails.getTestCaseName());
	testCaseDetailsTemplate = testCaseDetailsTemplate.replace("${TestCaseStatus}", testCaseDetails.getStatus().name());
	if (testCaseDetails.getStatus().name().equalsIgnoreCase("ok") || testCaseDetails.getStatus().name().equalsIgnoreCase("Finished")) {
	    testCaseDetailsTemplate = testCaseDetailsTemplate.replace("${statusClass}", "pass");
	} else if (testCaseDetails.getStatus().name().equalsIgnoreCase("FAILED")) {
	    testCaseDetailsTemplate = testCaseDetailsTemplate.replace("${statusClass}", "fail");
	}
	testCaseDetailsTemplate = testCaseDetailsTemplate.replace("${TestCaseStartTime}", utility.milliSec2TimeFormat(testCaseDetails.getTestCaseStartTime()));
	testCaseDetailsTemplate = testCaseDetailsTemplate.replace("${TestCaseEndTime}", "");
	testCaseDetailsTemplate = testCaseDetailsTemplate.replace("${OSEnv}", System.getProperty("os.name"));
	testCaseDetailsTemplate = testCaseDetailsTemplate.replace("${TestCaseExecutionTime}",
	        utility.milliSec2TimeFormat(testCaseDetails.getTestCaseStartTime() + testCaseDetails.getTestCaseTimeTaken()));

    }

    /**
     * 
     */
    public String writeTestCaseDetails(String testCaseDetails, String testCaseTemplate) {
	testCaseTemplate = testCaseTemplate.replace(testCaseDetails, testCaseDetailsTemplate);
	return testCaseTemplate;
    }
}