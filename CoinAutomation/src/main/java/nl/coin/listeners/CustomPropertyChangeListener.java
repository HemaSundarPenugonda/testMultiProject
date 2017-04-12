/**
 * 
 */
package nl.coin.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.log4j.Logger;

import com.eviware.soapui.SoapUI;

/**
 * @author hemasundar
 *
 */
public class CustomPropertyChangeListener implements PropertyChangeListener {
    private Logger log;

    /**
     * 
     */
    public CustomPropertyChangeListener() {
	log = SoapUI.log;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
     * PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
	log.info("property change: " + evt);

    }

}
