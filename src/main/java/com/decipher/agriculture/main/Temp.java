package com.decipher.agriculture.main;

import java.util.Arrays;


public class Temp {
    public static void main(String args[]) {
        String[][] str = new String[][]{{"1", "2", "3"}, {"1", "2", "3"}, {"1", "2", "3"}, {"1", "2", "3"}};


        generateCombination(str, new int[str.length]);


    }


    private static boolean generateCombination(String[][] array, int[] arrayInt) {
        int i = array.length - 1;
        System.out.println(Arrays.toString(arrayInt));
        arrayInt[i]++;
        while (array[i].length <= arrayInt[i]) {
            i--;
            arrayInt[i]++;
            arrayInt[arrayInt.length - 1] = 0;
            for (int l = 0; l < array[i].length; l++) {
                if (arrayInt[l] == arrayInt.length) {
                    arrayInt[l] = 0;
                    i--;
                    arrayInt[i]++;

                }
            }

        }
//			System.out.println(Arrays.toString(arrayInt)+" Ok ");
        return generateCombination(array, arrayInt);
    }
}
