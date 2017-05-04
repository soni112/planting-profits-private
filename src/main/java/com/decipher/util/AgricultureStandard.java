package com.decipher.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public interface AgricultureStandard {
	public static String DATE_FORMAT = "MM/dd/yyyy";
	public static DateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	public static String pattern = "#.00";
	public static DecimalFormat  decimalFormat=new DecimalFormat(pattern);
}
