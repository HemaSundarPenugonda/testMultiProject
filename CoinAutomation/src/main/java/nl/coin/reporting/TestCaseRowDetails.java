/**
 * 
 */
package nl.coin.reporting;

import com.eviware.soapui.model.testsuite.TestRunner.Status;

/**
 * @author hemasundar
 *
 */
public class TestCaseRowDetails {
	private String testSuiteName;
	private String testCaseName;
    private int testStepsCount;
    private long startTime;
    private long timeTaken;
    private Status status;
    private int passCount;
    private int failCount;
    private String remarks;
    private String properties;
    private String result;

	public synchronized String getTestSuiteName() {
		return testSuiteName;
	}

	public synchronized void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}
	
    public synchronized String getTestCaseName() {
	return testCaseName;
    }

    public synchronized void setTestCaseName(String testCaseName) {
	this.testCaseName = testCaseName;
    }

    public synchronized int getTestStepsCount() {
	return testStepsCount;
    }

    public synchronized void setTestStepsCount(int testStepsCount) {
	this.testStepsCount = testStepsCount;
    }

    public synchronized long getStartTime() {
	return startTime;
    }

    public synchronized void setStartTime(long startTime) {
	this.startTime = startTime;
    }

    public synchronized long getTimeTaken() {
	return timeTaken;
    }

    public synchronized void setTimeTaken(long timeTaken) {
	this.timeTaken = timeTaken;
    }

    public synchronized Status getStatus() {
	return status;
    }

    public synchronized void setStatus(Status testCaseStatus) {
	this.status = testCaseStatus;
    }

    public synchronized int getPassCount() {
	return passCount;
    }

    public synchronized void setPassCount(int passCount) {
	this.passCount = passCount;
    }

    public synchronized int getFailCount() {
	return failCount;
    }

    public synchronized void setFailCount(int failCount) {
	this.failCount = failCount;
    }

    public synchronized String getRemarks() {
	return remarks;
    }

    public synchronized void setRemarks(String remarks) {
	this.remarks = remarks;
    }

    public synchronized String getProperties() {
	return properties;
    }

    public synchronized void setProperties(String properties) {
	this.properties = properties;
    }

    public synchronized String getResult() {
	return result;
    }

    public synchronized void setResult(String result) {
	this.result = result;
    }
}
