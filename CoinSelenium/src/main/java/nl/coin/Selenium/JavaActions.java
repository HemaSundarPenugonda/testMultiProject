/**
 * 
 */
package nl.coin.Selenium;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import nl.coin.common.UtilitySel;
import nl.coin.reporting.LogFile;

/**
 * @author hemasundar
 *
 */
public class JavaActions {
	/**
	 * 
	 */
	public JavaActions() {
		super();
	}

	/**
	 * 
	 */
	public String getDossierID(String recepient, String donor, String contractID) {
		return recepient + "-" + donor + "-" + contractID + "-01";

	}

	/**
	 * 
	 */
	public String getDateFromFormat(String actualDate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("d");
		try {

			Date date = formatter.parse(actualDate);
			UtilitySel.printMessage(date.toString());
			String format2 = dateFormatter.format(date);

			return format2;
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * @throws ParseException
	 * 
	 */
	public String convertDateFormat(String actualDate, String actualFormat, String expectedFormat)
			throws ParseException {
		SimpleDateFormat actualFormatter = new SimpleDateFormat(actualFormat);

		try {
			SimpleDateFormat expectedFormatter = new SimpleDateFormat(expectedFormat);

			Date date = actualFormatter.parse(actualDate);
			UtilitySel.printMessage(date.toString());

			String format2 = expectedFormatter.format(date);
			return format2;
		} catch (ParseException e) {
			throw e;
		}
	}

	/**
	 * @param pattern
	 * @throws ParseException
	 * 
	 */
	public String reduceTime(String pattern, String currentTime, String minReduce) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat(pattern);
		Date date = new SimpleDateFormat(pattern).parse(currentTime);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.MINUTE, -20);

		String format = format1.format(calendar.getTime());
		return format;

	}

	/**
	 * @throws IOException
	 * 
	 */
	public void getEnvironment() throws IOException {
		Map<String, String> getenv = System.getenv();
		// Properties properties = System.getProperties();
		// properties.putAll(getenv);
		new UtilitySel(new LogFile()).writeFile("D:/getEnv.txt", getenv.toString());
	}

	public boolean checkInUnreadMails(String actualSubject) throws MessagingException, ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = formatter.parse(new Date(Calendar.getInstance().getTimeInMillis() - (5 * 60000)).toString());
		return checkInUnreadMails(actualSubject, date.toString(), "yyyy-MM-dd HH:mm:ss.S");

	}

	public boolean checkInUnreadMails(String actualSubject, String time, String format)
			throws MessagingException, ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = formatter.parse(time);

		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getInstance(props, null);
		Store store = session.getStore();
		store.connect("imap.gmail.com", "Testerxebia@gmail.com", "test@123");

		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);

		Flags seen = new Flags(Flags.Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		Message[] unreadMessages = inbox.search(unseenFlagTerm);

		SearchTerm todayMails = new ReceivedDateTerm(ComparisonTerm.GT, date);
		Message[] messages = inbox.search(todayMails, unreadMessages);

		// for (int i = messages.length - 1; i > messages.length - 6; i--) {
		for (Message message : messages) {
			Date receivedDate = message.getReceivedDate();
			boolean after = receivedDate.after(date);
			if (after) {
				String currentSubject = message.getSubject();
				if (currentSubject.toLowerCase().contains(actualSubject.toLowerCase())) {
					message.setFlag(Flags.Flag.SEEN, true);
					return true;
				}
			}
		}
		return false;
	}

	public boolean verifyMailContent(String actualSubject, String time, String format, String content)
			throws MessagingException, ParseException, IOException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = formatter.parse(time);

		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getInstance(props, null);
		Store store = session.getStore();
		store.connect("imap.gmail.com", "Testerxebia@gmail.com", "test@123");

		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);

		Flags seen = new Flags(Flags.Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		Message[] unreadMessages = inbox.search(unseenFlagTerm);

		SearchTerm todayMails = new ReceivedDateTerm(ComparisonTerm.GT, date);
		Message[] messages = inbox.search(todayMails, unreadMessages);

		for (Message message : messages) {
			Date receivedDate = message.getReceivedDate();
			boolean after = receivedDate.after(date);
			if (after) {
				String currentSubject = message.getSubject();
				if (currentSubject.toLowerCase().contains(actualSubject.toLowerCase())) {
					message.setFlag(Flags.Flag.SEEN, true);

					String convertStreamToString = new UtilitySel(null)
							.convertStreamToString(message.getInputStream());
					if (convertStreamToString.toLowerCase().contains(content.toLowerCase())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public String calculatePast14thWorkingDay(String currentDate, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = formatter.parse(currentDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int decrement = 0;
		while (decrement != 14) {
			cal.add(Calendar.DATE, -1);
			int week = cal.get(Calendar.DAY_OF_WEEK);

			if (week == 1 || week == 7) {
				continue;
			}
			int count = 0;
			if (count != 0) {
				continue;
			}

			decrement++;
		}

		return formatter.format(cal.getTime());

	}

	public String getPastTimeSlots(String spr_notification_emails_timeslots) throws ParseException {

		if (!(spr_notification_emails_timeslots.equalsIgnoreCase(""))) {
			String now = String.format("%tFT%<tT", new Date());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy' 'HH:mm:ss");
			String date = convertDateFormat(now, "yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy");
			String[] splitData = spr_notification_emails_timeslots.split(",");
			String resultantNotificationSlots = "";
			for (int i = 0; i < splitData.length; i++) {
				String todayMailTime = date + " " + splitData[i] + ":00";

				Date todayMailTime_Date = simpleDateFormat.parse(todayMailTime);
				todayMailTime_Date = new Date(todayMailTime_Date.getTime() + (3 * 60000));
				boolean before = new Date().after(todayMailTime_Date);
				if (before) {
					resultantNotificationSlots = resultantNotificationSlots + splitData[i] + ",";
				}
			}
			if (resultantNotificationSlots.endsWith(",")) {
				resultantNotificationSlots = resultantNotificationSlots.substring(0,
						resultantNotificationSlots.length() - 1);
			}
			return resultantNotificationSlots;
		} else {
			return "";
		}
	}

	public String array2String(String[] array) {
		String res = "";
		for (String str : array) {
			res = res + str + ",";
		}
		if (res.endsWith(",")) {
			res = res.substring(0, res.length() - 1);
		}
		return res;
	}

	public String[] getPastTimeRecords(String ctdId, String switchId, String reqDateTime, String donorId)
			throws ParseException {
		String[] res = { "", "", "", "" };

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");

		String[] splitID = ctdId.split(",");
		String[] splitSwitchID = switchId.split(",");
		String[] splitDate = reqDateTime.split(",");
		String[] splitDonorId = donorId.split(",");

		for (int i = 0; i < splitDate.length; i++) {

			Date todayMailTime_Date = simpleDateFormat.parse(splitDate[i]);
			todayMailTime_Date = new Date(todayMailTime_Date.getTime() + (3 * 60000));
			boolean before = new Date().after(todayMailTime_Date);

			if (before) {
				res[0] = res[0] + splitID[i] + ",";
				res[1] = res[1] + splitSwitchID[i] + ",";
				res[2] = res[2] + splitDate[i] + ",";
				res[3] = res[3] + splitDonorId[i] + ",";

			}

		}

		return res;
	}
}