package com.decipher.agriculture.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Prems {
    private static String[][] array = new String[][] {{ "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12", "A13", "A14", "A15" },
		new String[] { "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12", "B13", "B14", "B15" },
		new String[] { "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13", "C14", "C15" },
		new String[] { "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13", "D14", "D15" },
		new String[] { "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "E10", "E11", "E12", "E13", "E14", "E15" },
//		new String[] { "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", "F15" },
//		new String[]{"G1","G2","G3","G4","G5","G6","G7","G8","G9","G10","G11","G12","G13","G14","G15"},
		new String[] { "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15" }};
//    private static int combinationLength = 3;
//    private static List<List<String>> combinations = new ArrayList<List<String>>();
    public static void main(String[] args) {
    	System.out.println("Start time = "+new Date());
    	List<String[]> combinations = generateCombinations(array);
    	System.out.println("End time = "+new Date());
    	System.out.println("Size = "+combinations.size());
    }
    
	private static List<String[]> generateCombinations(String[][] array) {
		List<String[]> combinations = new ArrayList<String[]>();
		long length = 0l;
		for (int combinationLength = array.length; combinationLength >= 1; combinationLength--) {
			for (int i = 0; i < Math.pow(2, array.length); ++i) {
				int c = 0;
				for (int j = 1; j <= Math.pow(2, array.length); j <<= 1)
					if ((i & j) != 0)
						++c;
				if (c == combinationLength) {
					String[][] maskedArray = new String[combinationLength][];
					for (int l = 1, j = 0, k = 0; l <= Math
							.pow(2, array.length); l <<= 1, ++j)
						if ((i & l) != 0) {
							maskedArray[k++] = array[j];
						}
					int l = 1;
					for (int j = 0; j < maskedArray.length; ++j)
						l *= maskedArray[j].length;
					for (int j = 0; j < l; ++j) {
//						String s = "";
						String[] combination = new String[combinationLength];
						int index=0;
						int m = j;
						for (int k = maskedArray.length - 1; k >= 0; --k) {
							combination[index] = maskedArray[k][m % maskedArray[k].length];
							m /= maskedArray[k].length;
						}
						length++;
						combinations.add(combination);
					}
				}
			}
		}
		System.out.println(length);
		return combinations;
	}
}