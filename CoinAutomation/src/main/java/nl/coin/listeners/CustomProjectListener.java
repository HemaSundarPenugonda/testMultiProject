/**
 * 
 */
package nl.coin.listeners;

import org.apache.log4j.Logger;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.environment.Environment;
import com.eviware.soapui.model.iface.Interface;
import com.eviware.soapui.model.mock.MockService;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.model.project.ProjectListener;
import com.eviware.soapui.model.testsuite.TestSuite;

/**
 * @author hemasundar
 *
 */
public class CustomProjectListener implements ProjectListener {
    private Logger log;

    /**
    * 
    */
    public CustomProjectListener() {
	log = SoapUI.log;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#afterLoad(com.eviware.
     * soapui.model.project.Project)
     */
    @Override
    public void afterLoad(Project arg0) {
	log.info("loading project done: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#beforeSave(com.eviware.
     * soapui.model.project.Project)
     */
    @Override
    public void beforeSave(Project arg0) {
	log.info("before saving the project" + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#environmentAdded(com.
     * eviware.soapui.model.environment.Environment)
     */
    @Override
    public void environmentAdded(Environment arg0) {
	log.info("New Environment added: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#environmentRemoved(com.
     * eviware.soapui.model.environment.Environment, int)
     */
    @Override
    public void environmentRemoved(Environment arg0, int arg1) {
	log.info("Environment removed: " + arg0 + " & int value is: " + arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#environmentRenamed(com.
     * eviware.soapui.model.environment.Environment, java.lang.String,
     * java.lang.String)
     */
    @Override
    public void environmentRenamed(Environment arg0, String arg1, String arg2) {
	log.info("Environment renamed:\nEnvironment: " + arg0 + "\nInitial value: " + arg1 + "\nCurrent value: " + arg2);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#environmentSwitched(com.
     * eviware.soapui.model.environment.Environment)
     */
    @Override
    public void environmentSwitched(Environment arg0) {
	log.info("Environment switched: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eviware.soapui.model.project.ProjectListener#interfaceAdded(com.
     * eviware.soapui.model.iface.Interface)
     */
    @Override
    public void interfaceAdded(Interface arg0) {
	log.info("New interface added: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#interfaceRemoved(com.
     * eviware.soapui.model.iface.Interface)
     */
    @Override
    public void interfaceRemoved(Interface arg0) {
	log.info("Interface removed: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#interfaceUpdated(com.
     * eviware.soapui.model.iface.Interface)
     */
    @Override
    public void interfaceUpdated(Interface arg0) {
	log.info("Interface updated: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#mockServiceAdded(com.
     * eviware.soapui.model.mock.MockService)
     */
    @Override
    public void mockServiceAdded(MockService arg0) {
	log.info("New mock service added: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#mockServiceRemoved(com.
     * eviware.soapui.model.mock.MockService)
     */
    @Override
    public void mockServiceRemoved(MockService arg0) {
	log.info("Mock service removed: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eviware.soapui.model.project.ProjectListener#testSuiteAdded(com.
     * eviware.soapui.model.testsuite.TestSuite)
     */
    @Override
    public void testSuiteAdded(TestSuite arg0) {
	log.info("new test suite added: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eviware.soapui.model.project.ProjectListener#testSuiteMoved(com.
     * eviware.soapui.model.testsuite.TestSuite, int, int)
     */
    @Override
    public void testSuiteMoved(TestSuite arg0, int arg1, int arg2) {
	log.info("testSuite moved: " + arg0 + "from: " + arg1 + " to: " + arg2);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.project.ProjectListener#testSuiteRemoved(com.
     * eviware.soapui.model.testsuite.TestSuite)
     */
    @Override
    public void testSuiteRemoved(TestSuite arg0) {
	log.info("testCase removed: " + arg0);

    }

}
