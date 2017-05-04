package com.decipher.agriculture.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class MyCombinationGenerator {

	private static List<String[]> combinationSet = new ArrayList<String[]>();
	private static List<int[]> subCombinationSet = null;
	@SuppressWarnings("unused")
	private static void generateCombination(String[][] array) {
		String[] combination = null;
		String[] mainCombination = null;
		int oldM = 0;
		int z = 0;
		for (int maxCombLength = 0; maxCombLength < array.length; maxCombLength++) {
			for (int j = 0; j < array.length; j++) {
				String[] subArray = new String[maxCombLength];
				for (int k = 0; k < array[j].length; k++) {
					if (maxCombLength == 0) {
						combination = new String[maxCombLength + 1];
						combination[maxCombLength] = array[j][k];
						combinationSet.add(combination);
					} else {
						int i=0;
						for (int m = j + 1; m < maxCombLength + j && m < array.length; m++) {
							/*for (int n = 0; n < array[m].length; n++) {
								combination = new String[maxCombLength + 1];
								combination[maxCombLength] = array[m][n];
								combinationSet.add(combination);
							}*/
//							subArray[i++] = array[]
						}
					}
				}
			}
		}
	}

	/*public static void main(String[] args) {
		String[][] arr = new String[][] { { "A1", "A2", "A3", "A4", "A5" },
				new String[] { "B1", "B2", "B3", "B4" },
				new String[] { "C1", "C2", "C3", "C4" } };
		generateCombination(arr);
		for(String[] array: combinationSet){
			System.out.println(Arrays.toString(array));
		}
	}*/
	
	public static void mainOld(String[] args) {
		String[][] arr = new String[][] { { "A1", "A2", "A3", "A4", "A5" },
				new String[] { "B1", "B2", "B3", "B4" },
				new String[] { "C1", "C2", "C3", "C4" } };
		generateCombination(arr, new int[arr.length]);
	}
	
	private static boolean generateCombination(String[][] array, int[] arrayInt) {
		int i = array.length - 1;
		System.out.println(Arrays.toString(arrayInt));
		subCombinationSet.add(arrayInt);
		arrayInt[i]++;
		while (array[i].length <= arrayInt[i]) {
			i--;
			arrayInt[i]++;
			arrayInt[arrayInt.length - 1] = 0;
			for (int l = 0; l < arrayInt.length; l++) {
				if (arrayInt[l] == arrayInt.length) {
					arrayInt[l] = 0;
					i--;
					arrayInt[i]++;
				}
			}
		}

		boolean flag = true;
		for (int l = 0; l < arrayInt.length; l++) {
			if (arrayInt[l] == arrayInt.length - 1) {
				flag = false;
			} else {
				flag = true;
				break;
			}
		}
		if (flag) {
			return generateCombination(array, arrayInt);
		} else {
			System.out.println(Arrays.toString(arrayInt));
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
//		String[][] fieldArray = { { "F-1 : C-1", "F-1 : C-2", "F-1 : C-3" },
//				{ "F-2 : C-1", "F-2 : C-2", "F-2 : C-3" },
//				{ "F-3 : C-1", "F-3 : C-2", "F-3 : C-3" },
//				{ "F-4 : C-1", "F-4 : C-2", "F-4 : C-3" } };
		
		String[][] arr = new String[][] {{ "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12", "A13", "A14", "A15" },
				new String[] { "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12", "B13", "B14", "B15" },
				new String[] { "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13", "C14", "C15" },
				new String[] { "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13", "D14", "D15" },
				new String[] { "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "E10", "E11", "E12", "E13", "E14", "E15" },
//				new String[] { "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", "F15" },
//				new String[]{"G1","G2","G3","G4","G5","G6","G7","G8","G9","G10","G11","G12","G13","G14","G15"},
				new String[] { "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15" }};
		
		Set<String> animals = ImmutableSet.of( "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12", "A13", "A14", "A15" );
		Set<String> animals1 = ImmutableSet.of( "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12", "B13", "B14", "B15" );
		Set<String> animals2 = ImmutableSet.of( "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13", "C14", "C15" );
		Set<String> animals3 = ImmutableSet.of( "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13", "D14", "D15" );
		Set<String> animals4 = ImmutableSet.of( "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "E10", "E11", "E12", "E13", "E14", "E15" );
		Set<String> animals5 = ImmutableSet.of( "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", "F15" );
//		Set<String> animals6 = ImmutableSet.of("G1","G2","G3","G4","G5","G6","G7","G8","G9","G10","G11","G12","G13","G14","G15");
		Set<String> animals7 = ImmutableSet.of( "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15" );
		List<Set<String>> set = new ArrayList<Set<String>>();
		set.add(animals);
		set.add(animals1);
		set.add(animals2);
		set.add(animals3);
		set.add(animals4);
		set.add(animals5);
//		set.add(animals6);
		set.add(animals7);
//		try {
//			generateCombination(fieldArray);
//		} catch (Exception exception) {
//
//		}
//		Set<List<String>> product = Sets.cartesianProduct(animals, animals1);
//		Set<String> animals = ImmutableSet.of("gerbil", "hamster");
//		Set<String> fruits = ImmutableSet.of("apple", "orange", "banana");
		Set<List<String>> product = Sets.cartesianProduct(set);
		System.out.println("Size Cartisian "+product.size());
//		for (List<String> array : product) {
//			System.out.println(Arrays.toString(array.toArray()));
//		}
//		System.out.println("Size Array "+generateCombinations(arr).length);
		
	}
	private static String[][] generateCombinations(String[]... arrays) {
	    if (arrays.length == 0) {
	        return new String[][]{{}};
	    }
	    int num = 1;
	    for (int i = 0; i < arrays.length; i++) {
	        num *= arrays[i].length;
	    }
//	    PlantingProfitLogger.info(num+"--------------"+arrays.length);
	    
	    String[][] result = new String[num][arrays.length];
	    // array containing the indices of the Strings
	    int[] combination = new int[arrays.length];

	    for (int i = 0; i < num; i++) {
	        String[] comb = result[i];
	        // fill array
	        for (int j = 0; j < arrays.length; j++) {
	            comb[j] = arrays[j][combination[j]];
//	            System.out.println(j+"--------"+Arrays.toString(comb));
	        }

	        // generate next combination
	        for (int j = 0; j < arrays.length; j++) {
	            int n = ++combination[j];
	            if (n >= arrays[j].length) {
	                // "digit" exceeded valid range -> back to 0 and continue incrementing
	                combination[j] = 0;
	            } else {
	                // "digit" still in valid range -> stop
	                break;
	            }
	        }
	    }
	    return result;
	}
	
	/*private static boolean generateCombination(String[][] array, int[] arrayInt){
		int i = array.length-1;
		arrayInt[i]++;
		while(array[i].length <= arrayInt[i]){
			i--;
			arrayInt[i]++;
			arrayInt[arrayInt.length-1] = 0;
		}
		System.out.println(Arrays.toString(arrayInt));
//		for(int j=0;j<arrayInt.length; j++){
//			if(array[j].length-1 != arrayInt[j]){
//				return false;
//			}
//		}
		return generateCombination(array, arrayInt);
	}
	public static void main(String[] args) {
		String[][] array = {{"1","2","3"}, {"4","5","6"}, {"7","8","9"}};
		generateCombination(array, new Integer[array.length]);
	}*/
}