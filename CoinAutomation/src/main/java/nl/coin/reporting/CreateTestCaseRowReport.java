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
public class CreateTestCaseRowReport {
    private String testCaseRow;

    public synchronized String getTestCaseRow() {
	return testCaseRow;
    }

    private Utility utility;

    /**
     * @param objProperty
     * 
     */
    public CreateTestCaseRowReport(Utility utility, Property objProperty) {
	this.utility = utility;
	testCaseRow = utility.readFile(objProperty.testCaseRowPath);
    }

    /**
     * 
     */
    public void creatingTestCaseRow(TestCaseRowDetails testCaseRowDetails) {

	testCaseRow = testCaseRow.replace("${TestCaseName}", testCaseRowDetails.getTestCaseName());
	testCaseRow = testCaseRow.replace("${TestCaseName_proper}", utility.createProperFileName(testCaseRowDetails.getTestSuiteName()) + "/" +utility.createProperFileName(testCaseRowDetails.getTestCaseName()));
	testCaseRow = testCaseRow.replace("${TestStepCount}", String.valueOf(testCaseRowDetails.getTestStepsCount()));
	testCaseRow = testCaseRow.replace("${TestCaseStartTime}", utility.milliSec2TimeFormat(testCaseRowDetails.getStartTime()));
	testCaseRow = testCaseRow.replace("${TestCaseExecutionTime}",
	        utility.milliSec2TimeFormat(testCaseRowDetails.getStartTime() + testCaseRowDetails.getTimeTaken()));
	String status = testCaseRowDetails.getStatus().name();
	testCaseRow = testCaseRow.replace("${TestCaseStatus}", status);

	if (status.equalsIgnoreCase("ok") || status.equalsIgnoreCase("Finished")) {
	    testCaseRow = testCaseRow.replace("${statusClass}", "pass");

	} else if (status.equalsIgnoreCase("FAILED")) {
	    testCaseRow = testCaseRow.replace("${statusClass}", "fail");

	}

	// testCaseRow = testCaseRow.replace("${TestStepPassCount}", "100");
	// testCaseRow = testCaseRow.replace("${TestStepFailCount}", "100");
	testCaseRow = testCaseRow.replace("${TestCaseRemarks}", testCaseRowDetails.getRemarks());
	testCaseRow = testCaseRow.replace("${TestCaseName}", testCaseRowDetails.getTestCaseName());
	testCaseRow = testCaseRow.replace("${TestCaseName}", testCaseRowDetails.getTestCaseName());
	testCaseRow = testCaseRow.replace("${TestCaseProperties}", testCaseRowDetails.getProperties());
	testCaseRow = testCaseRow.replace("${TestCaseName_NoSpace}", utility.removeSpecialChar(testCaseRowDetails.getTestCaseName()));

	testCaseRow = testCaseRow.replace("${TestStepPassCount}", String.valueOf(testCaseRowDetails.getPassCount()));
	testCaseRow = testCaseRow.replace("${TestStepFailCount}", String.valueOf(testCaseRowDetails.getFailCount()));
    }

    /**
     * @param excetionReportTemplate
     * 
     */
    public String writeTestCaseRowReport(String testCaseRowReplaceString, String excetionReportTemplate) {
	excetionReportTemplate = excetionReportTemplate.replace(testCaseRowReplaceString, testCaseRow);
	return excetionReportTemplate;
    }
}
