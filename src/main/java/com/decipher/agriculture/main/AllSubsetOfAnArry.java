package com.decipher.agriculture.main;

import javax.swing.JOptionPane;

public class AllSubsetOfAnArry {
	static String[][] fieldArray = { { "A1", "B1", "C1" },
			{ "A2", "B2", "C2" }, { "A3", "B3", "C3" }

	};

	static String arr[] = new String[fieldArray.length];

	public static void main(String args[]) {
		System.out.println("Start : " + new java.util.Date());
		for (int i = 0; i < fieldArray.length; i++) {
			for (int j = 0; j < fieldArray[0].length; j++) {
				generate(i, j, fieldArray);
			}
		}
		Test4.d();
		System.out.println("End  : " + new java.util.Date());
	}

	public static void generate(int i, int j, String[][] fieldArrays) {
		String str[] = new String[fieldArray.length - i];
		str[0] = fieldArrays[i][j];
		int length = fieldArray[0].length;
		for (int l = 0; l < length; l++) {
			for (int m = 1; m < fieldArrays.length - i; m++) {
				str[m] = fieldArrays[m + i][l];
			}
			// JOptionPane.showMessageDialog(null, str);
			Test4.main(str);
		}
	}
}