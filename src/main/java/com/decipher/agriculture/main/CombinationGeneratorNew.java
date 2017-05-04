package com.decipher.agriculture.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.decipher.agriculture.customclasses.CustomHashSet;

public class CombinationGeneratorNew {

	public static Set<String> generateCombinationsNew(String[]... arrays) {
		Set<String> arrayOutput = new HashSet<String>();
		for (int i = 0; i < arrays.length; i++) {
			for (int j = 0; j < arrays[i].length; j++) {
				arrayOutput.add(arrays[i][j]+"@@@@");
			}
		}

		for (int i = 0; i < arrays.length/2; i++) {
			for (int j = 0; j < arrays[i].length; j++) {
				for (int k = 0; k < arrays.length; k++) {
					if (k == i) {
						continue;
					} else {
						for (int l = 0; l < arrays[k].length; l++) {
							System.out.println(arrays[i][j] + "      "+ arrays[k][l]);
							arrayOutput.add(arrays[i][j]+"@@@@"+arrays[k][l]);
						}
					}
				}
			}
		}
		return arrayOutput;
	}
	
	public static Set<String> generateCombinations(String[]... arrays) {
		Set<String> arrayOutput = new HashSet<String>();
		String comb;
		for(int i=0;i<arrays.length;i++){
			for(int j=0;j<arrays[i].length;j++){
				comb=arrays[i][j];
				for(int k=0;k<arrays.length;k++){
					if(i==k){
						continue;
					}else{
						for(int l=0;l<arrays[k].length;l++){
							
						}
					}
				}
			}
		}
		
		return arrayOutput;
	}

	public static void main(String[] args) {

		String[] arr1 = {"A1","A2"};
		String[] arr2 = {"A2","A1"};
		String[] arr3 = {"A1","A3"};
		
		
		Set<String[]> array = new CustomHashSet();
		
		array.add(arr1);
		array.add(arr2);
		array.add(arr3);
		
		for(String[] arr:array){
			System.out.println(Arrays.toString(arr));
		}
		
//		array.add({"A1","A2"});
//		array.add({"A2","A1"});
//		array.add({"A2","A3"});
		
		System.out.println(array.size());
		/*String[][] fieldArray = { { "F-1 : C-1", "F-1 : C-2", "F-1 : C-3" },
				{ "F-2 : C-1", "F-2 : C-2", "F-2 : C-3" },
				{ "F-3 : C-1", "F-3 : C-2", "F-3 : C-3" },
				{ "F-4 : C-1", "F-4 : C-2", "F-4 : C-3" } };
		generateCombinationsNew(fieldArray);
System.out.println("test field1###Alfalfa@@@@test field2###Corn@@@@test field3###Celery".split("@@@@").length);*/
}
}
