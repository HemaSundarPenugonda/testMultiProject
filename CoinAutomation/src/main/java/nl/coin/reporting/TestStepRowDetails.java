/**
 * 
 */
package nl.coin.reporting;

import com.eviware.soapui.model.testsuite.TestStepResult.TestStepStatus;

/**
 * @author hemasundar
 *
 */
public class TestStepRowDetails {
    private String testStepName;
    private TestStepStatus status;
    private long startTime;
    private long timeTaken;
    private String request;
    private String response;
    private String assertions;
    private String remarks;

    public synchronized String getTestStepName() {
	return testStepName;
    }

    public synchronized void setTestStepName(String testStepName) {
	this.testStepName = testStepName;
    }

    public synchronized TestStepStatus getStatus() {
	return status;
    }

    public synchronized void setStatus(TestStepStatus testStepStatus) {
	this.status = testStepStatus;
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

    public synchronized String getRequest() {
	return request;
    }

    public synchronized void setRequest(String request) {
	this.request = request;
    }

    public synchronized String getResponse() {
	return response;
    }

    public synchronized void setResponse(String response) {
	this.response = response;
    }

    public synchronized String getAssertions() {
	return assertions;
    }

    public synchronized void setAssertions(String assertions) {
	this.assertions = assertions;
    }

    public synchronized String getRemarks() {
	return remarks;
    }

    public synchronized void setRemarks(String remarks) {
	this.remarks = remarks;
    }

}
