package com.decipher.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class AgricultureStandardUtils {

	public static String commaSeparaterForNumber(String pattern) {
		String number = pattern;
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		return formatter.format(number);
	}

	public static String commaSeparaterForPrice(String pattern) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		return formatter.format(Double.parseDouble(pattern));
	}

	// add by rohit on 22-04-15
	public static String commaSeparaterForPriceWithOneDecimal(String pattern) {
		DecimalFormat formatter = new DecimalFormat("#,###.0");
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		return formatter.format(Double.parseDouble(pattern));
	}

	public static String commaSeparatedForPriceWithThreeDecimal(String pattern) {
		DecimalFormat formatter = new DecimalFormat("#,###.###");
//		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
//		symbols.setGroupingSeparator(',');
		return formatter.format(Double.parseDouble(pattern));
	}

	public static String priceWithOneDecimal(String pattern) {
		DecimalFormat formatter = new DecimalFormat("####.0");
		return formatter.format(Double.parseDouble(pattern));
	}

	public static String commaSeparaterForField(String pattern) {
		/**
		 * @changed - Abhishek
		 * @Date - 26-11-2015
		 * */
		
//		PlantingProfitLogger.info("pattern : " + pattern);
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		
		return formatter.format(Double.parseDouble(pattern));
	}

	public static String commaSeparaterForDoublePrice(Double value) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		return formatter.format(value);
	}

	public static String commaSeparatedUptoOneDecimalForDoublePrice(Double value) {
		DecimalFormat formatter = new DecimalFormat("#,###.0");
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		return formatter.format(value);
	}

	public static String commaSeparaterForInteger(Double value) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		return formatter.format(value);
	}

	public static int doubleToInteger(Double value) {
//		DecimalFormat formatter = new DecimalFormat("#,###");
//		return Integer.parseInt(formatter.format(value));
		return value.intValue();
	}
	
	public static Double doubleWithOneDecimal(Double value) {
		DecimalFormat formatter = new DecimalFormat("#.0");
//		PlantingProfitLogger.info(formatter.format(value));
		try{
			value = Double.parseDouble(formatter.format(value));
		}catch(Exception exception){
			value = new Double(0);
		}
		return value;
	}

	/**
	 * @Added- Abhishek @ 27-11-2015
	 * @param - Double value with multiple decimal point
	 * @return - Double value with single digit after decimal point
	 */
	public static Double doubleUptoSingleDecimalPoint(Double value){
		DecimalFormat formatter =  new DecimalFormat("#.0");
		try {
			value = Double.parseDouble(formatter.format(value));
		} catch (Exception e) {
			value = new Double(0);
		}
		return value;
	}
	
	
	public static String commaSeparaterForInteger(int value) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		return formatter.format(value);
	}

	public static String removeAllCommas(String value) {
		return value.replaceAll(",", "");
	}

	public static String withoutDecimalAndComma(String pattern) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		return formatter.format(Double.parseDouble(pattern));
	}

	public static String withoutDecimalAndComma(Double value) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		return formatter.format(value);
	}

	public static Long withoutDecimalAndCommaToLong(Double value) {
		DecimalFormat formatter = new DecimalFormat("####");
		return Long.parseLong(formatter.format(value));
	}

	public static String withoutDecimalAndWithoutComma(String pattern) {
		DecimalFormat formatter = new DecimalFormat("####");
		return formatter.format(Double.parseDouble(pattern));
	}
	
	public static long stringToLong(String pattern) {
		pattern = removeAllCommas(pattern);
		DecimalFormat formatter = new DecimalFormat("####");
		return Long.parseLong(formatter.format(Double.parseDouble(pattern)));
	}

	public static long stringToLongWithComma(String pattern) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		return Long.parseLong(formatter.format(removeAllCommas(pattern)));
	}
	public static String commaSeparaterForLong(long acresWithoutContract) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		return formatter.format(acresWithoutContract);
	}

	/**
	 * @added  Abhishek
	 * @date  12-04-2016
	 * @param  unsortedMap
	 * @return  Sorted map in descending order
	 */
	public static Map<String, String> sortUsingComparatorByValue(Map<String, String> unsortedMap) {

		// Convert Map to List
		List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(unsortedMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
							   Map.Entry<String, String> o2) {

				String[] splitO1 = o1.getValue().split(" ");
				String[] splitO2 = o2.getValue().split(" ");

				Long o1Acreage = Long.parseLong(AgricultureStandardUtils.removeAllCommas(splitO1[0]));
				Long o2Acreage = Long.parseLong(AgricultureStandardUtils.removeAllCommas(splitO2[0]));


				return (o2Acreage).compareTo(o1Acreage);
			}
		});

		// Convert sorted map back to a Map
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		for (Iterator<Map.Entry<String, String>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

}
