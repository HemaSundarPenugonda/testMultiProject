/**
 * 
 */
package nl.coin.reporting;

import com.eviware.soapui.model.testsuite.TestRunner.Status;

/**
 * @author hemasundar
 *
 */
public class TestCaseDetails {
    private String testCaseName;
    private Status status;
    private long testCaseStartTime;
    private long testCaseTimeTaken;
    private String testCaseExecutedOS;

    public synchronized String getTestCaseName() {
	return testCaseName;
    }

    public synchronized void setTestCaseName(String testCaseName) {
	this.testCaseName = testCaseName;
    }

    public synchronized Status getStatus() {
	return status;
    }

    public synchronized void setStatus(Status testCaseStatus) {
	this.status = testCaseStatus;
    }

    public synchronized long getTestCaseStartTime() {
	return testCaseStartTime;
    }

    public synchronized void setTestCaseStartTime(long testCaseStartTime) {
	this.testCaseStartTime = testCaseStartTime;
    }

    public synchronized long getTestCaseTimeTaken() {
	return testCaseTimeTaken;
    }

    public synchronized void setTestCaseTimeTaken(long testCaseTimeTaken) {
	this.testCaseTimeTaken = testCaseTimeTaken;
    }

    public synchronized String getTestCaseExecutedOS() {
	return testCaseExecutedOS;
    }

    public synchronized void setTestCaseExecutedOS(String testCaseExecutedOS) {
	this.testCaseExecutedOS = testCaseExecutedOS;
    }
}
