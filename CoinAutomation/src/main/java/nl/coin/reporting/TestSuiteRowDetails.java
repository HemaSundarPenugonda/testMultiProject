/**
 * 
 */
package nl.coin.reporting;

import com.eviware.soapui.model.testsuite.TestRunner.Status;

/**
 * @author hemasundar
 *
 */
public class TestSuiteRowDetails {
    private String suiteName;
    private int testCasesCount;
    private long startTime;
    private long timeTaken;
    private Status status;
    private int passCount;
    private int failCount;
    private String remarks;
    private String setUpScript;
    private String tearDownScript;
    private String properties;
    private String testData;

    public synchronized String getSuiteName() {
	return suiteName;
    }

    public synchronized void setSuiteName(String suiteName) {
	this.suiteName = suiteName;
    }

    public synchronized int getTestCasesCount() {
	return testCasesCount;
    }

    public synchronized void setTestCasesCount(int testCasesCount) {
	this.testCasesCount = testCasesCount;
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

    public synchronized void setStatus(Status testSuiteStatus) {
	this.status = testSuiteStatus;
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

    public synchronized String getSetUpScript() {
	return setUpScript;
    }

    public synchronized void setSetUpScript(String setUpScript) {
	this.setUpScript = setUpScript;
    }

    public synchronized String getTearDownScript() {
	return tearDownScript;
    }

    public synchronized void setTearDownScript(String tearDownScript) {
	this.tearDownScript = tearDownScript;
    }

    public synchronized String getProperties() {
	return properties;
    }

    public synchronized void setProperties(String properties) {
	this.properties = properties;
    }

    public synchronized String getTestData() {
	return testData;
    }

    public synchronized void setTestData(String testData) {
	this.testData = testData;
    }
}
