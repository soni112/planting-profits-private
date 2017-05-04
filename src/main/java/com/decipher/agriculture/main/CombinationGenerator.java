package com.decipher.agriculture.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CombinationGenerator {

	public static void main1(String[] args) {

		String[][][] fieldArray = { { { "F-1" }, { "C-1", "C-2", "C-3" } },
				{ { "F-2" }, { "C-1", "C-3" } },
				{ { "F-3" }, { "C-1", "C-2" } } };

		for (int i = 0; i < fieldArray.length; i++) {
			for (int j = 0; j < fieldArray.length; j++) {
				if (i == j) {
					continue;
				} else {
					for (int k = 0; k < fieldArray[j].length; k++) {
						System.out.println(fieldArray[i] + "-------------"
								+ fieldArray[j] + "-------------"
								+ fieldArray[j][k] + "-------------");
					}
				}
			}
		}
	}

	public static void main2(String[] args) {
		String[][] fieldArray = { { "F-1 : C-1", "F-1 : C-2", "F-1 : C-3" },
				{ "F-2 : C-1", "F-2 : C-2", "F-2 : C-3" },
				{ "F-3 : C-1", "F-3 : C-2", "F-3 : C-3" } };
		for (int i = 0; i < fieldArray.length; i++) {
			System.out.print(fieldArray[i] + "----------");
			for (int j = 0; j < fieldArray.length; j++) {
				if (i == j) {
					continue;
				} else {
					for (int k = 0; k < fieldArray[j].length; k++) {
						System.out.println(fieldArray[j][k] + "----------");
					}
				}
			}
		}
	}

	public static void generateCombinations(String[]... arrays) {
		// array containing the indices of the Strings
		int[] combination = new int[arrays.length];
		int num = 1;
		for (int i = 0; i < arrays.length; i++) {
			num *= arrays[i].length;
		}
		for (int i = 0; i < num; i++) {
			String[] comb = new String[arrays.length];
			for (int j = 0; j < arrays.length; j++) {
				comb[j] = arrays[j][combination[j]];
			}
			for (int j = 0; j < arrays.length; j++) {
				int n = ++combination[j];
				if (n >= arrays[j].length) {
					combination[j] = 0;
				} else {
					break;
				}
			}
			System.out.println(Arrays.toString(comb));
		}
	}
	@Override
	public void finalize(){
		System.out.println("Finalize called");
	}

	public static void main(String[] args) {
		String[][] fieldArray = { { "F-1 : C-1", "F-1 : C-2", "F-1 : C-3" },
				{ "F-2 : C-1", "F-2 : C-2", "F-2 : C-3" },
				{ "F-3 : C-1", "F-3 : C-2", "F-3 : C-3" } };
		generateCombinations(fieldArray);
		// array = generateCombinations(array);
		// for(String[] innerArray: array){
		// System.out.println(Arrays.toString(innerArray));
		// }
	}
	
	public static Set<String[]> generateCombinationsOLD(String[]... arrays) {
	    Set<String[]> combinationSet = new HashSet<String[]>();
		if (arrays.length == 0) {
	        return combinationSet;
	    }
	    int[] intarray = new int[arrays.length];
	    boolean flag= true;
	    String combination = null;
		while(flag){
			combination = "";
			for(int i=0; i<intarray.length;i++){
				combination += arrays[i][intarray[i]];
			}
			for(int i=intarray.length-1; i>=0;i--){
				if(arrays[i].length==intarray[i]){
				}
			}
		}
		
		return combinationSet;
	}
	
	public static void mainOLD(String[] args) {
//		Set<String[]> array = generateCombinationsOLD(new String[] { "A1", "A2", "A3", "A4"},
//				new String[] { "B1", "B2", "B3", "B4"},
//				new String[] { "C1", "C2", "C3", "C4"}
//	       );
//		String[][] fieldArray = { { "F-1 : C-1", "F-1 : C-2", "F-1 : C-3" },{ "F-2 : C-1", "F-2 : C-2", "F-2 : C-3" },{ "F-3 : C-1", "F-3 : C-2", "F-3 : C-3" } };
////		array = generateCombinationsOLD(array);
//		for(String[] innerArray: array){
//			System.out.println(Arrays.toString(innerArray));
//		}
		for(int i=0;i<10;i++){
			if(i==5){
				break;
			}
			System.out.println("Hello");
		}
	}
}
