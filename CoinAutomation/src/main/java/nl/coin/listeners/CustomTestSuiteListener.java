/**
 * 
 */
package nl.coin.listeners;

import org.apache.log4j.Logger;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.testsuite.LoadTest;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestSuiteListener;
import com.eviware.soapui.security.SecurityTest;

/**
 * @author hemasundar
 *
 */
public class CustomTestSuiteListener implements TestSuiteListener {
    private Logger log;

    /**
     * 
     */
    public CustomTestSuiteListener() {
	log = SoapUI.log;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#loadTestAdded(com.
     * eviware.soapui.model.testsuite.LoadTest)
     */
    @Override
    public void loadTestAdded(LoadTest arg0) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#loadTestRemoved(com.
     * eviware.soapui.model.testsuite.LoadTest)
     */
    @Override
    public void loadTestRemoved(LoadTest arg0) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#securityTestAdded(
     * com.eviware.soapui.security.SecurityTest)
     */
    @Override
    public void securityTestAdded(SecurityTest arg0) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#securityTestRemoved(
     * com.eviware.soapui.security.SecurityTest)
     */
    @Override
    public void securityTestRemoved(SecurityTest arg0) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#testCaseAdded(com.
     * eviware.soapui.model.testsuite.TestCase)
     */
    @Override
    public void testCaseAdded(TestCase arg0) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#testCaseMoved(com.
     * eviware.soapui.model.testsuite.TestCase, int, int)
     */
    @Override
    public void testCaseMoved(TestCase arg0, int arg1, int arg2) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#testCaseRemoved(com.
     * eviware.soapui.model.testsuite.TestCase)
     */
    @Override
    public void testCaseRemoved(TestCase arg0) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#testStepAdded(com.
     * eviware.soapui.model.testsuite.TestStep, int)
     */
    @Override
    public void testStepAdded(TestStep arg0, int arg1) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#testStepMoved(com.
     * eviware.soapui.model.testsuite.TestStep, int, int)
     */
    @Override
    public void testStepMoved(TestStep arg0, int arg1, int arg2) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestSuiteListener#testStepRemoved(com.
     * eviware.soapui.model.testsuite.TestStep, int)
     */
    @Override
    public void testStepRemoved(TestStep arg0, int arg1) {
	// TODO Auto-generated method stub

    }

}