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
public class CreateTestSuiteRowReport {
	private String testSuiteRow;

	public synchronized String getTestSuiteRow() {
		return testSuiteRow;
	}

	private Utility utility;

	/**
	 * @param objProperty
	 * 
	 */
	public CreateTestSuiteRowReport(Utility utility, Property objProperty) {
		this.utility = utility;
		testSuiteRow = utility.readFile(objProperty.testSuiteRowPath);
	}

	/**
	 * 
	 */
	public void creatingTestSuiteRow(TestSuiteRowDetails testSuiteRowDetails) {

		testSuiteRow = testSuiteRow.replace("${SuiteName}", testSuiteRowDetails.getSuiteName());
		testSuiteRow = testSuiteRow.replace("${SuiteName_NoSpace}",
				utility.removeSpecialChar(testSuiteRowDetails.getSuiteName()));
		testSuiteRow = testSuiteRow.replace("${TestCaseCount}",
				String.valueOf(testSuiteRowDetails.getTestCasesCount()));
		testSuiteRow = testSuiteRow.replace("${TestSuiteStartTime}",
				utility.milliSec2TimeFormat(testSuiteRowDetails.getStartTime()));
		testSuiteRow = testSuiteRow.replace("${TestSuiteExecutionTime}",
				utility.milliSec2TimeFormat(testSuiteRowDetails.getStartTime() + testSuiteRowDetails.getTimeTaken()));
		testSuiteRow = testSuiteRow.replace("${TestSuiteStatus}", testSuiteRowDetails.getStatus().name());
		testSuiteRow = utility.setStatusColor(testSuiteRowDetails.getStatus().name(), testSuiteRow);
		// testSuiteRow = testSuiteRow.replace("${TestCasePassCount}", "100");
		// testSuiteRow = testSuiteRow.replace("${TestCaseFailCount}", "100");
		testSuiteRow = testSuiteRow.replace("${TestSuiteRemarks}", testSuiteRowDetails.getRemarks());
		testSuiteRow = testSuiteRow.replace("${SetUpScript}", testSuiteRowDetails.getSetUpScript());
		testSuiteRow = testSuiteRow.replace("${TearDownScript}", testSuiteRowDetails.getTearDownScript());
		testSuiteRow = testSuiteRow.replace("${TestSuiteProperties}", testSuiteRowDetails.getProperties());
		testSuiteRow = testSuiteRow.replace("${SuiteTestData}", testSuiteRowDetails.getTestData());

		testSuiteRow = testSuiteRow.replace("${TestCasePassCount}", String.valueOf(testSuiteRowDetails.getPassCount()));
		testSuiteRow = testSuiteRow.replace("${TestCaseFailCount}", String.valueOf(testSuiteRowDetails.getFailCount()));
	}

	/**
	 * @param excetionReportTemplate
	 * 
	 */
	public String writeTestSuiteRow(String testSuiteRow, String excetionReportTemplate) {
		excetionReportTemplate = excetionReportTemplate.replace(testSuiteRow, this.testSuiteRow);
		return excetionReportTemplate;
	}
}
