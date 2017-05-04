package com.decipher.agriculture.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class RecursiveFunctions {

	public static int product(int n1, int n2) {
		System.out.println("n1 = "+n1+" n2 = "+n2);
		if (n2 > 1) {
			n1 += product(n1, n2 - 1);
		}
		return n1;
	}

	public static void main1(String[] args) {
//		System.out.println(product(5, 10));
		int[] arr = {2,3,4,5,6};
		int[] arrTotal = new int[arr.length];
//		for(int i=0; i<arr.length; i++){
//			arrTotal[i] *= 5;
//			
//		}
		Arrays.fill(arrTotal, 10);
		System.out.println(Arrays.toString(arrTotal));
	}

	public static void main(String[] args) {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;
		try {
			System.out.println("date : " + new Date());
			date = formatter.parse(formatter.format(new Date()));
			System.out.println("date = " + date);
		} catch (ParseException e) {
			e.printStackTrace();
		}



	}
}