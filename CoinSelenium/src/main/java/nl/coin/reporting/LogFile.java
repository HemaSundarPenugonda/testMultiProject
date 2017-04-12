package nl.coin.reporting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.commons.io.output.TeeOutputStream;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import nl.coin.common.UtilitySel;

public class LogFile {

	private Logger log;
	public UtilitySel utility;
	String pathLogFile;

	/**
	* 
	*/
	public LogFile() {
		log = Logger.getLogger("LOG");
		// log = SoapUI.log;
//		DOMConfigurator.configure("log4j.xml");
		BasicConfigurator.configure();

	}

	/*********************************************************************************************
	 * Step action name: prepareLogger Description: Prepare a logger with a new
	 * file name in the Execution_Log folder
	 *********************************************************************************************/

	// Prepare a logger with a new file name.
	public void prepareLogger() {
		try {
			log.setLevel(Level.ERROR);
			PatternLayout layoutHelper = new PatternLayout();
			layoutHelper.setConversionPattern("%-5p [%t]: %m%n");
			layoutHelper.activateOptions();

			/* Console apenders */
			/*
			 * ConsoleAppender consoleApender = new ConsoleAppender();
			 * consoleApender.activateOptions(); //
			 * mainLogger.getAppender("ConsoleAppender").setLayout(layoutHelper)
			 * ; consoleApender.setLayout(layoutHelper);
			 * log.addAppender(consoleApender);
			 */
			/* Create log file */
			String FilePath = "Logs/qaLog_" + new UtilitySel(this).getCurrentTimeStamp() + ".dat";
			File myLogFile = new UtilitySel(this).createFile(FilePath);

			/* File apender */
			FileAppender fileAppender = new FileAppender(layoutHelper, myLogFile.getAbsolutePath(), true);
			// log.addAppender(fileAppender);
			// SoapUI.log.addAppender(fileAppender);
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(FilePath);
				TeeOutputStream myOut = new TeeOutputStream(System.out, fileOutputStream);
				System.setOut(new PrintStream(myOut, true));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/*********************************************************************************************
	 * Step action name: writeInfo Description: Log info
	 *********************************************************************************************/

	public void writeInfo(String info) {
		log.info(info);
	}

	/*********************************************************************************************
	 * Step action name: writeError Description: Log error
	 *********************************************************************************************/

	public void writeError(String error) {
		log.error(error);
	}

	/*********************************************************************************************
	 * Step action name: writeDebug Description: Log debug
	 *********************************************************************************************/

	public void writeDebug(String debug) {
		log.debug(debug);
	}

}
