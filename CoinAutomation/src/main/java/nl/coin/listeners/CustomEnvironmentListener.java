/**
 * 
 */
package nl.coin.listeners;

import org.apache.log4j.Logger;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.environment.EnvironmentListener;
import com.eviware.soapui.model.environment.Property;

/**
 * @author hemasundar
 *
 */
public class CustomEnvironmentListener implements EnvironmentListener {
    private Logger log;

    /**
     * 
     */
    public CustomEnvironmentListener() {
	log = SoapUI.log;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eviware.soapui.model.environment.EnvironmentListener#
     * propertyValueChanged(com.eviware.soapui.model.environment.Property)
     */
    @Override
    public void propertyValueChanged(Property arg0) {
	log.info("property value changed: " + arg0);

    }

}
