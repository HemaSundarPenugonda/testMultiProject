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
public class CreateTestStepRowReport {
    private String testStepRow;

    public synchronized String getTestStepRow() {
	return testStepRow;
    }

    private Utility utility;

    /**
     * @param objProperty
     * 
     */
    public CreateTestStepRowReport(Utility utility, Property objProperty) {
	this.utility = utility;
	testStepRow = utility.readFile(objProperty.testStepRowTemplatePath);
    }

    /**
     * 
     */
    public void creatingTestStepRow(TestStepRowDetails testStepRowDetails) {
	testStepRow = testStepRow.replace("${TestStepName}", testStepRowDetails.getTestStepName());
	testStepRow = testStepRow.replace("${TestStepName_NoSpace}", utility.removeSpecialChar(testStepRowDetails.getTestStepName()));
	testStepRow = testStepRow.replace("${TestStepStatus}", testStepRowDetails.getStatus().name());
	// testStepRow = setStatusColor(testStepStatus.name(),
	// testStepRow);
	if (testStepRowDetails.getStatus().name().equalsIgnoreCase("ok") || testStepRowDetails.getStatus().name().equalsIgnoreCase("Finished")) {

	    testStepRow = testStepRow.replace("${statusClass}", "pass");
	} else if (testStepRowDetails.getStatus().name().equalsIgnoreCase("FAILED")) {

	    testStepRow = testStepRow.replace("${statusClass}", "fail");
	}
	testStepRow = testStepRow.replace("${TestStepStartTime}", utility.milliSec2TimeFormat(testStepRowDetails.getStartTime()));
	testStepRow = testStepRow.replace("${TestStepExecutionTime}",
	        utility.milliSec2TimeFormat(testStepRowDetails.getStartTime() + testStepRowDetails.getTimeTaken()));
	testStepRow = testStepRow.replace("${TestStepRequest}", testStepRowDetails.getRequest());
	testStepRow = testStepRow.replace("${TestStepResponse}", testStepRowDetails.getResponse());
	testStepRow = testStepRow.replace("${TestStepAssertions}", testStepRowDetails.getAssertions());
	/*
	 * testStepRow = testStepRow.replace("${TestStepProperties}",
	 * testStepPropMap);
	 */
	testStepRow = testStepRow.replace("${TestStepRemarks}", testStepRowDetails.getRemarks());
    }

    /**
     * 
     */
    public String writeTestStepRow(String testStepRowReplaceString, String testCaseTemplate) {
	testCaseTemplate = testCaseTemplate.replace(testStepRowReplaceString, testStepRow);
	return testCaseTemplate;

    }
}
