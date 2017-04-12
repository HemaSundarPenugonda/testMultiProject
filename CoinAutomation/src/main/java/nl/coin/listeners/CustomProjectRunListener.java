/**
 * 
 */
package nl.coin.listeners;

import org.apache.log4j.Logger;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.testsuite.ProjectRunContext;
import com.eviware.soapui.model.testsuite.ProjectRunListener;
import com.eviware.soapui.model.testsuite.ProjectRunner;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.model.testsuite.TestSuiteRunner;

/**
 * @author hemasundar
 *
 */
public class CustomProjectRunListener implements ProjectRunListener {
    private Logger log;

    /**
     * 
     */
    public CustomProjectRunListener() {
	log = SoapUI.log;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eviware.soapui.model.testsuite.ProjectRunListener#afterRun(com.
     * eviware.soapui.model.testsuite.ProjectRunner,
     * com.eviware.soapui.model.testsuite.ProjectRunContext)
     */
    @Override
    public void afterRun(ProjectRunner arg0, ProjectRunContext arg1) {
	log.info("after running the project: \nProject runner: " + arg0 + "\nproject run context: " + arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.ProjectRunListener#afterTestSuite(com.
     * eviware.soapui.model.testsuite.ProjectRunner,
     * com.eviware.soapui.model.testsuite.ProjectRunContext,
     * com.eviware.soapui.model.testsuite.TestSuiteRunner)
     */
    @Override
    public void afterTestSuite(ProjectRunner arg0, ProjectRunContext arg1, TestSuiteRunner arg2) {
	log.info("After test suite:\nproject runner: " + arg0 + "\nproject run context: " + arg1 + "\ntest suite runner: " + arg2);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eviware.soapui.model.testsuite.ProjectRunListener#beforeRun(com.
     * eviware.soapui.model.testsuite.ProjectRunner,
     * com.eviware.soapui.model.testsuite.ProjectRunContext)
     */
    @Override
    public void beforeRun(ProjectRunner arg0, ProjectRunContext arg1) {
	log.info("before project run:\nproject runner: " + arg0 + "\nproject run context: " + arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.ProjectRunListener#beforeTestSuite(com
     * .eviware.soapui.model.testsuite.ProjectRunner,
     * com.eviware.soapui.model.testsuite.ProjectRunContext,
     * com.eviware.soapui.model.testsuite.TestSuite)
     */
    @Override
    public void beforeTestSuite(ProjectRunner arg0, ProjectRunContext arg1, TestSuite arg2) {
	log.info("before test suite:\nproject runner: " + arg0 + "\nproject run context: " + arg1 + "\ntest suite: " + arg2);

    }

}
