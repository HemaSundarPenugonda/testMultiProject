package nl.coin.misc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestSuiteRunner;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.model.testsuite.TestSuite.TestSuiteRunType;
import com.eviware.soapui.support.SoapUIException;
import com.eviware.soapui.support.types.StringToObjectMap;

public class ExecuteAllTCs {
	private static String soapUIProjectDir;
	private static List<String> allProjects = new ArrayList<String>();

	public static void main(String[] args) throws XmlException, IOException, SoapUIException {
		List<String> allProjects = new ArrayList<String>();
		allProjects = getAllProjects();
		for (String indProjectFileName : allProjects) {
			WsdlProject project = new WsdlProject(indProjectFileName);
			project.setPropertyValue("UserIP", "10.10.20.137");
			project.setPropertyValue("directory",
					System.getProperty("user.dir") + "/src/main/resources/SOAPUIproject/");
			project.setPropertyValue("SoapUrl", "http://192.168.100.21:8080/compv4_0/MessageService");
			project.setPropertyValue("DBHostname", "192.168.100.21");
			project.setPropertyValue("DBPort", "5432");
			project.setPropertyValue("DBUser", "comp");
			project.setPropertyValue("DBPassword", "comp");
			project.setPropertyValue("DBDatabase", "comp");
			project.setPropertyValue("GUIUrl", "http://192.168.100.21:8080/compv4_0/");
			List<TestSuite> testSuiteList = project.getTestSuiteList();
			for (TestSuite indTestSuite : testSuiteList) {
				StringToObjectMap context = new StringToObjectMap();
				WsdlTestSuite wsdlSuite = (WsdlTestSuite) indTestSuite;
				if (wsdlSuite.getName().equalsIgnoreCase("Rainy Day-Inactive Providers")) {
					wsdlSuite.setRunType(TestSuiteRunType.SEQUENTIAL);
				} else {
					wsdlSuite.setRunType(TestSuiteRunType.PARALLEL);
				}

				List<TestProperty> propertyList = wsdlSuite.getPropertyList();
				for (TestProperty indTestProperty : propertyList) {
					if (indTestProperty.getName().equalsIgnoreCase("linenumber")) {
						// indTestProperty.setValue("0");
						for (int i = 0; i < 29; i++) {
							indTestProperty.setValue(String.valueOf(i));
							WsdlTestSuiteRunner testSuiteRunner = wsdlSuite.run(context, false);
						}
					}
				}

				WsdlTestSuiteRunner testSuiteRunner = wsdlSuite.run(context, false);
			}

		}

	}

	public static List<String> getAllProjects() {
		soapUIProjectDir = System.getProperty("user.dir") + "/src/main/resources/SOAPUIproject/";
		File parentFile = new File(soapUIProjectDir);

		File[] listFiles = parentFile.listFiles();
		for (File indFile : listFiles) {
			String path = indFile.getPath();
			String name = indFile.getName();
			if (path.endsWith(".xml") && !name.equalsIgnoreCase("COMP4.0-soapui-project.xml")
					&& !name.equalsIgnoreCase("project-temp-1305245174697441940.xml")) {
				allProjects.add(path);
			}

		}
		System.out.println(allProjects);
		return allProjects;
	}
}
