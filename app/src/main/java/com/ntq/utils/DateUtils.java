package com.ntq.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * @author TUNGDX
 *
 */

/**
 * 
 * All utils for {@link Date}
 * 
 */
public class DateUtils {
	/**
	 * Compare day between 2 {@link Date}. This is difference with
	 * {@link Date#after(Date)} or {@link Date#before(Date)}. Because it only
	 * compare by day (not compare hour, minutes, seconds).
	 * 
	 * @param dateRoot
	 *            This is root
	 * @param dateToCompare
	 *            Date to compare with root.
	 * @return true if dateToCompare > date root, false otherwise.
	 */
	public static boolean checkLargerByDay(Date dateRoot, Date dateToCompare) {
		Calendar calenadar = Calendar.getInstance();
		calenadar.setTime(dateRoot);

		Calendar calenaderToCompare = Calendar.getInstance();
		calenaderToCompare.setTime(dateToCompare);

		int lastYear = calenadar.get(Calendar.YEAR);
		int newYear = calenaderToCompare.get(Calendar.YEAR);
		if (lastYear == newYear) {
			int dayOfYear = calenadar.get(Calendar.DAY_OF_YEAR);
			int dayOfNewYearToCompare = calenaderToCompare
					.get(Calendar.DAY_OF_YEAR);
			if (dayOfYear < dayOfNewYearToCompare)
				return true;
		} else if (lastYear < newYear) {
			return true;
		}
		return false;
	}

	/**
	 * Convert time string to other format
	 * 
	 * @param time
	 *            in string
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 */
	public static String convertFormatDateTime(String time, String fromFormat,
			String toFormat) {
		SimpleDateFormat from = new SimpleDateFormat(fromFormat,
				Locale.getDefault());
		SimpleDateFormat to = new SimpleDateFormat(toFormat,
				Locale.getDefault());
		try {
			Date date = from.parse(time);
			return to.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Get time in GMT as template
	 * 
	 * @param template
	 *            date in GMT. Example: yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getTimeInGMT(String template) {
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		Date date = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(template,
				Locale.getDefault());
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}

	/**
	 * Convert time in GMT to Locale.
	 * 
	 * @param time
	 *            Time to converted.
	 * @return Time in Locale.
	 */
	public static String convertGMTtoLocale(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS",
				Locale.getDefault());
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			Date date = dateFormat.parse(time);
			dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS",
					Locale.getDefault());
			dateFormat.setTimeZone(TimeZone.getDefault());
			return dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Convert time in locale to GMT.
	 * 
	 * @param time
	 *            Time to converted.
	 * @return Time in GMT.
	 */
	public static String convertLocaleToGMT(String time) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS",
				Locale.getDefault());
		Date date;
		try {
			date = dateFormat.parse(time);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String d = dateFormat.format(date);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}
	}

	/**
	 * Get {@link Date} in GMT.
	 * 
	 * @return date in GMT.
	 */
	public static Date getDateTimeInGMT() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS",
				Locale.getDefault());
		Date date = Calendar.getInstance().getTime();
		try {
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String d = dateFormat.format(date);

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmssSSS", Locale.getDefault());
			Date date2 = simpleDateFormat.parse(d);
			return date2;

		} catch (ParseException e) {
			e.printStackTrace();
			// if exception -> get time local
			return null;
		}
	}
}
