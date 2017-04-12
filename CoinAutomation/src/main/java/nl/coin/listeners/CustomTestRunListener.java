/**
 * 
 */
package nl.coin.listeners;

import org.apache.log4j.Logger;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunListener;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;

/**
 * @author hemasundar
 *
 */
public class CustomTestRunListener implements TestRunListener {
    private Logger log;

    /**
     * 
     */
    public CustomTestRunListener() {
	log = SoapUI.log;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestRunListener#afterRun(com.eviware.
     * soapui.model.testsuite.TestCaseRunner,
     * com.eviware.soapui.model.testsuite.TestCaseRunContext)
     */
    @Override
    public void afterRun(TestCaseRunner arg0, TestCaseRunContext arg1) {
	log.info("After test case run:" + "\n\tTest Case Runner: " + arg0 + "\n\tTest Case Run Context: " + arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestRunListener#afterStep(com.eviware.
     * soapui.model.testsuite.TestCaseRunner,
     * com.eviware.soapui.model.testsuite.TestCaseRunContext,
     * com.eviware.soapui.model.testsuite.TestStepResult)
     */
    @Override
    public void afterStep(TestCaseRunner arg0, TestCaseRunContext arg1, TestStepResult arg2) {
	log.info("After step:" + "\n\tTest Case Runner: " + arg0 + "\n\tTest Case Run Context: " + arg1 + "\n\tTest Step Result: " + arg2);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestRunListener#beforeRun(com.eviware.
     * soapui.model.testsuite.TestCaseRunner,
     * com.eviware.soapui.model.testsuite.TestCaseRunContext)
     */
    @Override
    public void beforeRun(TestCaseRunner arg0, TestCaseRunContext arg1) {
	log.info("Before run:" + "\n\tTest Case Runner: " + arg0 + "\n\tTest Case Run Context: " + arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestRunListener#beforeStep(com.eviware
     * .soapui.model.testsuite.TestCaseRunner,
     * com.eviware.soapui.model.testsuite.TestCaseRunContext)
     */
    @Override
    public void beforeStep(TestCaseRunner arg0, TestCaseRunContext arg1) {
	log.info("Before step: " + "\n\tTest Case Runner: " + arg0 + "\n\tTest Case Run Context: " + arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestRunListener#beforeStep(com.eviware
     * .soapui.model.testsuite.TestCaseRunner,
     * com.eviware.soapui.model.testsuite.TestCaseRunContext,
     * com.eviware.soapui.model.testsuite.TestStep)
     */
    @Override
    public void beforeStep(TestCaseRunner arg0, TestCaseRunContext arg1, TestStep arg2) {
	log.info("Before step:" + "\n\tTest Case Runner: " + arg0 + "\n\tTest Case Run Context: " + arg1 + "\n\tTest Step: " + arg2);

    }

}
