package com.decipher.agriculture.main;

public class SingleForLoopH {

	public static void main(String[] args) {
		int rows = 5;
		int columns = (5);
		System.out.println("Single loop for H");
		System.out.println("rows = " + rows + " columns = " + columns);
		for (int i = 0, j = 0; i < rows;) {
			if (j == 0 || j == columns - 1 || i == rows/2) {
				System.out.print("**");
				j++;
			} else {
				System.out.print("  ");
				j++;
			}
			if (j == columns) {
				System.out.println();
				i++;
				j = 0;
			}
		}

	}

}
