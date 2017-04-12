/**
 * 
 */
package nl.coin.listeners;

import org.apache.log4j.Logger;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.testsuite.TestPropertyListener;

/**
 * @author hemasundar
 *
 */
public class CustomTestPropertyListener implements TestPropertyListener {
    private Logger log;

    /**
     * 
     */
    public CustomTestPropertyListener() {
	log = SoapUI.log;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestPropertyListener#propertyAdded(
     * java.lang.String)
     */
    @Override
    public void propertyAdded(String arg0) {

	log.info("added the property : " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestPropertyListener#propertyMoved(
     * java.lang.String, int, int)
     */
    @Override
    public void propertyMoved(String arg0, int arg1, int arg2) {
	log.info("moved the property: " + arg0 + "and the int values are: " + arg1 + " & " + arg2);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestPropertyListener#propertyRemoved(
     * java.lang.String)
     */
    @Override
    public void propertyRemoved(String arg0) {
	log.info("property removed: " + arg0);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.eviware.soapui.model.testsuite.TestPropertyListener#propertyRenamed(
     * java.lang.String, java.lang.String)
     */
    @Override
    public void propertyRenamed(String arg0, String arg1) {
	log.info("property renamed from: " + arg0 + " to: " + arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eviware.soapui.model.testsuite.TestPropertyListener#
     * propertyValueChanged(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public void propertyValueChanged(String arg0, String arg1, String arg2) {
	log.info("property value changed : \n\tProperty name: " + arg0 + " \n\tInitial value: " + arg1 + "\n\tFinal value: " + arg2);

    }
}