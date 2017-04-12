package nl.coin.dataReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * All Utility functions that assist in communicating with external files like
 * TestCase sheet, DataData sheet and Object Repository sheet etc are listed
 * here.
 * 
 * @author
 *
 */
public class ReaderUtility {

	private String objectRepositoryFileLocation, credentialsFileLocation;

	/**
	 * Set the location of all the external files (TestCase, TestData &
	 * ObjectRepository) needed for the framework.
	 * 
	 * @param reusableFlag
	 *            a boolean value used to locate the file when reusable
	 *            scenarios are in execution. Property.FileSeperator and
	 *            Property.ObjectRepositoryFileLocation values are used from
	 *            Property.java file
	 */
	public void setFileLocation() {
		
		String fileSeparator = System.getProperty("file.separator");

		objectRepositoryFileLocation = System.getProperty("projectfiledir") + fileSeparator + "ObjectRepository"
				+ fileSeparator + "ObjectRepository.csv";
		credentialsFileLocation = System.getProperty("projectfiledir") + fileSeparator + "TestData" + fileSeparator + "guiCredentials.csv";
		/*
		 * if (System.getenv("JENKINS_URL") != null) { Utility.
		 * printMessage("Currently execution going on JENKINS environment"); }
		 * else if (System.getProperty("soapui.home") != null) { Utility.
		 * printMessage("Currently execution going on SOAPUI environment"); }
		 * else { Utility.
		 * printMessage("Currently execution going on ECLIPSE environment"); }
		 */
	}

	/**
	 * Helper function that takes a file (excel) and name of sheet in that file
	 * and Returns a table representing data from given sheet in tabular format.
	 * 
	 */

	public synchronized List<String[]> getCompleteRows(String FilePath, String Sheet) throws Exception {

		File inFile = new File(FilePath);
		List<String[]> completeData = new ArrayList<String[]>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(inFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw e1;
		}
		try {
			String indRow = "";

			// Skip reading the header
			// reader.readLine();
			while ((indRow = reader.readLine()) != null) {
				String[] indRowAsArray = indRow.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				for (int i = 0; i < indRowAsArray.length; i++) {
					indRowAsArray[i] = indRowAsArray[i].trim();
					if (indRowAsArray[i].startsWith("\"") && indRowAsArray[i].endsWith("\"")) {
						indRowAsArray[i] = indRowAsArray[i].substring(1, indRowAsArray[i].length() - 1);
						indRowAsArray[i] = indRowAsArray[i].trim();
					}
					if (indRowAsArray[i].contains("\"\"")) {
						indRowAsArray[i] = indRowAsArray[i].replaceAll("\"\"", "\"");

					}
				}
				completeData.add(indRowAsArray);
			}
		} catch (Exception e) {

		} finally {
			reader.close();
		}
		return completeData;
	}

	/**
	 * Get the data from Object Repository, implicitly calls 'getRequiredRows()'
	 * 
	 * @return <b>ResultSet object of Data.</b>
	 * @throws Exception
	 */
	public List<String[]> getORData() throws Exception {
		try {
			List<String[]> objTb = getCompleteRows(objectRepositoryFileLocation, "ObjectRepository");

			return objTb;
		} catch (Exception e) {
			throw e;
		}

	}

	public List<String[]> getCredentialsData() throws Exception {
		try {
			List<String[]> objTb = getCompleteRows(credentialsFileLocation, "Credentials");

			return objTb;
		} catch (Exception e) {
			throw e;
		}

	}

	public String getCellValue(List<String[]> sheet, int row, String ColHeader) {
		int colNum = 0;

		String[] header = sheet.get(0);
		try {
			for (int i = 0; i < header.length; i++) {
				if (header[i].equalsIgnoreCase(ColHeader)) {
					colNum = i;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] specificRow = sheet.get(row);
		return specificRow[colNum];

	}

	public HashMap<String, String> getCurrentStepORData(List<String[]> orData, String parent, String testObject)
			throws Exception {
		try {
			HashMap<String, String> objDef = new HashMap<String, String>();

			int rowCount = orData.size();
			int iterativeRow = 0;
			while (iterativeRow < rowCount) {
				if (getCellValue(orData, iterativeRow, "parent").equals(parent)
						&& getCellValue(orData, iterativeRow, "testObject").equals(testObject)) {
					String how = getCellValue(orData, iterativeRow, "how");
					String what = getCellValue(orData, iterativeRow, "what");
					/*
					 * how = utility.replaceVariableInString(how); what =
					 * utility.replaceVariableInString(what);
					 */
					objDef.put("parent", parent);
					objDef.put("testObject", testObject);
					objDef.put("how", how);
					objDef.put("what", what);

					break;
				}
				iterativeRow++;
			}
			return objDef;
		} catch (Exception e) {
			throw e;
		}

	}

	public HashMap<String, String> getCurrentStepCredentialsData(List<String[]> credentialsData, String sprCode)
			throws Exception {
		try {
			HashMap<String, String> objDef = new HashMap<String, String>();

			int rowCount = credentialsData.size();
			int iterativeRow = 0;
			while (iterativeRow < rowCount) {
				if (getCellValue(credentialsData, iterativeRow, "sprcode").equalsIgnoreCase(sprCode)) {
					String userName = getCellValue(credentialsData, iterativeRow, "username");
					String password = getCellValue(credentialsData, iterativeRow, "password");
					String providerDropDownValue = getCellValue(credentialsData, iterativeRow, "providerDropDownValue");
					objDef.put("sprcode", sprCode);
					objDef.put("username", userName);
					objDef.put("password", password);
					objDef.put("providerDropDownValue", providerDropDownValue);
					break;
				}
				iterativeRow++;
			}
			return objDef;
		} catch (Exception e) {
			throw e;
		}

	}

}
