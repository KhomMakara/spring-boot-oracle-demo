package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {
	/**
	 * DATE format : "yyyyMMdd"
	 */
	public final String FORMAT_DATE = "yyyyMMdd";


	/**
	 * <pre>
	 *  Get current system date as string type (yyyyMMdd)
	 * </pre>
	 * 
	 * @return
	 */
	public String getCurrentDate() {
		return getCurrentFormatDate(FORMAT_DATE);
	}
	/**
	 * <pre>
	 *  Get current system date using format
	 * </pre>
	 * 
	 * @return
	 */
	public String getCurrentFormatDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("en", "US"));
		return sdf.format(Calendar.getInstance().getTime());
	}


}
