/**
 * 
 */
package nl.coin.common;

import java.util.Comparator;

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;

/**
 * @author hemasundar
 *
 */
public class TestCaseComparator implements Comparator {
    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Object obj1, Object obj2) {
	TestCaseRunner tcr1 = (TestCaseRunner) obj1;
	TestCaseRunner tcr2 = (TestCaseRunner) obj2;
	int nameComp = ((WsdlTestCase) tcr1.getTestCase()).getLabel().compareTo(((WsdlTestCase) tcr2.getTestCase()).getLabel());

	/*
	 * return ((nameComp == 0) ?
	 * obj1.getLastName().compareTo(obj2.getLastName()) : nameComp);
	 */
	return nameComp;
    }
}