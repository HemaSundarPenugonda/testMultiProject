/**
 * 
 */
package nl.coin.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

import com.eviware.soapui.model.testsuite.TestProperty;

import nl.coin.reporting.LogFile;

/**
 * @author hemasundar
 *
 */
public class Utility extends UtilitySel {
	LogFile objLog;
	Property objProperty;
	Properties prop;

	/**
	* 
	*/
	public Utility(LogFile log, Property objProperty) {
		super(log);
		this.objLog = log;
		this.objProperty = objProperty;
		prop = new Properties();
	}

	/**
	 * Load the Property (uiautomation.properties file) for the framework.
	 * 
	 * @throws Exception
	 */
	public void collectKeyValuePair() throws Exception {
		try {
			/*
			 * Properties prop1 = new Properties(); prop1.load(new
			 * FileInputStream(objProperty.propertyFileLocation)); Set<Object>
			 * keySet = prop1.keySet(); for (Object indKeySet : keySet) { String
			 * value = prop1.getProperty(indKeySet.toString());
			 * prop.put(indKeySet.toString().toLowerCase(), value); }
			 */
			prop.load(new FileInputStream(objProperty.propertyFileLocation));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Populate Global hash map with the Key/Value given in
	 * uiautomation.properties file. Here we are creating a HashMap and data is
	 * supplied to it via an external file called uiautomation.properties.
	 * 
	 * @throws Exception
	 */
	public void populateGlobalMap() throws Exception {
		try {

			Enumeration em = prop.keys();
			Set keySet = prop.keySet();
			Object[] keys = keySet.toArray();
			int i = 0;
			while (em.hasMoreElements()) {
				String element = (String) em.nextElement();
				objProperty.globalVarMap.put(keys[i].toString().toLowerCase(), prop.getProperty(element));
				i++;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Store all the external parameters in global HashMap.
	 * 
	 * @pro1 Instance of Properties class
	 */
	public void addExternalProperties(Properties prop1) {
		try {
			Set Keyset = prop1.keySet();
			// Set ExistingKeySet = Property.globalVarMap.keySet();
			// Keyset.retainAll(ExistingKeySet);

			Object[] AllKeys = Keyset.toArray();

			for (Object key : AllKeys) {
				String keystring = (String) key;
				String value = prop1.getProperty(keystring);

				keystring = keystring.toLowerCase();
				objProperty.globalVarMap.put(keystring, value);
			}

		} catch (Exception e) {

		}
	}

	public void addExternalProperties(Map<String, String> prop1) {
		try {
			Set<String> Keyset = prop1.keySet();

			for (String indKey : Keyset) {

				String value = prop1.get(indKey);

				indKey = indKey.toLowerCase();
				objProperty.globalVarMap.put(indKey, value);
			}

		} catch (Exception e) {

		}
	}

	public static void printMessage(String msg) {
		// System.out.println(msg);
	}
}
