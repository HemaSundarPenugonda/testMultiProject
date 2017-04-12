/**
 * 
 */
package nl.coin.listeners;

import org.apache.log4j.Logger;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestSuiteRunContext;
import com.eviware.soapui.model.testsuite.TestSuiteRunListener;
import com.eviware.soapui.model.testsuite.TestSuiteRunner;

/**
 * @author hemasundar
 *
 */
public class CustomTestSuiteRunListener implements TestSuiteRunListener {
    private Logger log;

    /**
     * 
     */
    public CustomTestSuiteRunListener() {
	log = SoapUI.log;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteRunListener#afterRun(com.
     * eviware.soapui.model.testsuite.TestSuiteRunner,
     * com.eviware.soapui.model.testsuite.TestSuiteRunContext)
     */
    @Override
    public void afterRun(TestSuiteRunner arg0, TestSuiteRunContext arg1) {
	log.info("after test suite run:\ntest suite runner: " + arg0 + "\ntest suite run context: " + arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteRunListener#afterTestCase(com
     * .eviware.soapui.model.testsuite.TestSuiteRunner,
     * com.eviware.soapui.model.testsuite.TestSuiteRunContext,
     * com.eviware.soapui.model.testsuite.TestCaseRunner)
     */
    @Override
    public void afterTestCase(TestSuiteRunner arg0, TestSuiteRunContext arg1, TestCaseRunner arg2) {
	log.info("after test suite:\nTest Suite Runner: " + arg0 + "\nTest Suite Run Context: " + arg1 + "\nTestCaseRunner" + arg2);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteRunListener#beforeRun(com.
     * eviware.soapui.model.testsuite.TestSuiteRunner,
     * com.eviware.soapui.model.testsuite.TestSuiteRunContext)
     */
    @Override
    public void beforeRun(TestSuiteRunner arg0, TestSuiteRunContext arg1) {
	log.info("Before test suite run:\nTest Suite Runner: " + arg0 + "\nTest Suite Run Context: " + arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteRunListener#beforeTestCase(
     * com.eviware.soapui.model.testsuite.TestSuiteRunner,
     * com.eviware.soapui.model.testsuite.TestSuiteRunContext,
     * com.eviware.soapui.model.testsuite.TestCase)
     */
    @Override
    public void beforeTestCase(TestSuiteRunner arg0, TestSuiteRunContext arg1, TestCase arg2) {
	log.info("Before test case:\n\tTest Suite Runner: " + arg0 + "\n\tTest Suite Run Context: " + arg1 + "\n\tTest Case: " + arg2);

    }
}