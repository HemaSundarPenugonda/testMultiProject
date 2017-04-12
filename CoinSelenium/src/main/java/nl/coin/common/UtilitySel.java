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
public class UtilitySel {
	LogFile objLog;
	Properties prop;

	/**
	* 
	*/
	public UtilitySel(LogFile log) {
		this.objLog = log;
	}

	public String readFile(File file) {

		try {
			file.getParentFile().mkdirs();
			file.createNewFile();

			Scanner scanner = new Scanner(file);
			String str = scanner.useDelimiter("\\Z").next();
			scanner.close();
			return str;
		} catch (IOException e) {
			objLog.writeInfo("Exception occurred while creating file" + e);

			return "";
		}
	}

	public String readFile(String path) {

		File file = new File(path);
		return readFile(file);
	}

	public boolean writeFile(String path, String content) throws IOException {
		try {
			File write_FileObject = new File(path);
			if (!write_FileObject.exists()) {
				write_FileObject.getParentFile().mkdirs();
			} else {
				write_FileObject.delete();
			}
			return writeFile(write_FileObject, content);
		} catch (IOException e) {
			objLog.writeInfo("Exception occurred while creating file" + e);
			return false;
		}

	}

	public boolean writeFile(File file, String content) throws IOException {
		try {

			file.getParentFile().mkdirs();
			file.createNewFile();

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer.write(content);
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			objLog.writeInfo("Exception occurred while creating file" + e);
			return false;
		}

	}

	public boolean deleteAndWriteFile(File file, String content) throws IOException {
		try {

			if (file.exists()) {
				file.delete();
			}
			return writeFile(file, content);
		} catch (IOException e) {
			objLog.writeInfo("Exception occurred while creating file" + e);
			return false;
		}

	}

	public boolean deleteAndWriteFile(String path, String content) throws IOException {
		try {
			File file = new File(path);
			return deleteAndWriteFile(file, content);
		} catch (IOException e) {
			objLog.writeInfo("Exception occurred while creating file" + e);
			return false;
		}

	}

	/**
	 * 
	 */
	public boolean cleanDirectory(String dirPath) {
		try {
			FileUtils.cleanDirectory(new File(dirPath));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean copyFile2Dir(File source, File destination) {
		try {
			FileUtils.copyFileToDirectory(source, destination);
			return true;
		} catch (IOException e) {
			objLog.writeInfo("Exception occurred while copying the files : " + e);
			return false;
		}
	}

	public boolean copyFile2Dir(String source, String destination) {
		File sourceFile = new File(source);
		File destinationFile = new File(destination);
		return copyFile2Dir(sourceFile, destinationFile);
	}

	public boolean copyDir2Dir(String source, String destination) {
		try {

			File sourceFile = new File(source);
			File destinationFile = new File(destination);

			/*
			 * if (!(sourceFile.isDirectory() && destinationFile.isDirectory()))
			 * { objLog.writeInfo("Trying to copy files with Directory code ");
			 * return false; }
			 */

			FileUtils.copyDirectory(sourceFile, destinationFile);
			return true;
		} catch (IOException e) {
			objLog.writeInfo("Exception occurred while copying the files : " + e);
			return false;
		}
	}

	public String milliSec2TimeFormat(long timeInMilliSec) {
		Date date = new Date(timeInMilliSec);
		DateFormat formatter = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		String dateFormatted = formatter.format(date);
		return dateFormatted;

	}

	/**
	 * Generate and return current time stamp.
	 * 
	 * @return String - Current time stamp in format like 'Fri Dec 28 125258 IST
	 *         2012'.
	 */
	public String getCurrentTimeStamp() {
		Date currentDate = new Date();
		return currentDate.toString().replace(":", "");
	}

	/**
	 * @param FilePath
	 * @return
	 * @throws IOException
	 */
	public File createFile(String FilePath) throws IOException {
		return createFile(FilePath, false);
	}

	public File createFile(String FilePath, boolean checkDuplicate) throws IOException {
		File file = new File(FilePath);
		return createFile(file, checkDuplicate);
	}

	public File createFile(File file) throws IOException {
		return createFile(file, false);
	}

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private int i;

	public File createFile(File file, boolean checkDuplicate) throws IOException {
		String absolutePath2 = file.getAbsolutePath();
		String absolutePath = absolutePath2;
		String path = absolutePath;
		objLog.writeInfo("About to create the file : " + path);

		if (file.exists() && checkDuplicate) {
			String str = file.getAbsolutePath();
			str = str + i;
			i++;
			createFile(new File(str));
		} else if (!file.exists()) {
			i = 0;
			path.getBytes();
			file.getParentFile().mkdirs();
			boolean isCreated = file.createNewFile();
			if (isCreated) {
				objLog.writeDebug("file created successfully: " + path);
			}
		}
		return file;
	}

	/**
	 * @return
	 */
	public String removeSpecialChar(String str) {
		objLog.writeInfo("Removing spl chars from :" + str);
		str = str.replaceAll("[\\s\\.\\/+=]", "");
		objLog.writeInfo("After removing spl chars :" + str);
		return str;
	}

	/**
	 * @param status
	 * @param rowTemplate
	 * @return
	 */
	public String setStatusColor(String status, String rowTemplate) {
		if (status.equalsIgnoreCase("ok") || status.equalsIgnoreCase("Finished")) {
			rowTemplate = rowTemplate.replace("${statusClass}", "pass");

		} else if (status.equalsIgnoreCase("FAILED")) {
			rowTemplate = rowTemplate.replace("${statusClass}", "fail");
		}
		return rowTemplate;
	}

	/**
	 * @param testCasePropertyList
	 */
	public String properties2String(List<TestProperty> testCasePropertyList) {
		String str = "";
		Map<Object, Object> map = new Properties();
		for (TestProperty indTestProperty : testCasePropertyList) {
			String name = indTestProperty.getName();
			String value = (indTestProperty.getValue() == null) ? "" : indTestProperty.getValue();
			map.put(name, value);
			str = str + name + "\t\t = \t" + value + "\n";
		}
		/*
		 * String string = map.toString(); string = string.trim(); string =
		 * string.replaceAll(",", "\n");
		 */
		return str;
	}

	/**
	 * @throws IOException
	 * 
	 */
	public void zipReport(String folder, String zipFile) {
		try {
			objLog.writeInfo("Creating zip file of report");
			File createFile = createFile(zipFile);
			if (createFile.exists()) {
				objLog.writeInfo("Empty zip file created successfully");
			} else {
				objLog.writeInfo("Empty zip file not created");
			}

			FileOutputStream fos = new FileOutputStream(createFile);

			ZipOutputStream zos = new ZipOutputStream(fos);

			File reportFolder = new File(folder);
			File[] allReportFiles = reportFolder.listFiles();
			for (File indFile : allReportFiles) {
				addToZipFile(indFile, zos);
			}
			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			objLog.writeError(e.getStackTrace().toString());
		} catch (IOException e) {
			e.printStackTrace();
			objLog.writeError(e.getStackTrace().toString());
		}

	}

	public void addToZipFile(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

		objLog.writeInfo("Writing '" + file + "' to zip file");
		// File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(file.getName());

		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

	/**
	 * @param str
	 * @return
	 */
	public String createProperFileName(String str) {
		objLog.writeInfo("creating the proper filename for :" + str);
		str = str.replaceAll("[\\.\\/\\<\\>]", "");
		objLog.writeInfo("After removing spl chars: " + str);
		return str;
	}

	public String convertStreamToString(java.io.InputStream is) {
		@SuppressWarnings("resource")
		java.util.Scanner scanner = new java.util.Scanner(is);
		java.util.Scanner s = scanner.useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public List<String> getAllProjects(String baseLocation) {
		List<String> allProjects = new ArrayList<>();
		File parentFile = new File(baseLocation);

		File[] listFiles = parentFile.listFiles();

		for (File indFile : listFiles) {
			String path = indFile.getPath();
			String name = indFile.getName();
			if (path.endsWith(".xml") && !name.equalsIgnoreCase("COMP4.0-soapui-project.xml")) {
				allProjects.add(path);
			}
		}
		UtilitySel.printMessage(allProjects.toString());
		return allProjects;
	}

	public static void printMessage(String msg) {
		// System.out.println(msg);
	}
}
