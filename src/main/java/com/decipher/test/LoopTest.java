package com.decipher.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 6/2/17 6:13 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
public class LoopTest {

    public static void main(String[] args) {

        List<Integer> integerList1 = new ArrayList<>();
        List<Integer> integerList2 = new ArrayList<>();
        List<Integer> integerList3 = new ArrayList<>();
        List<Integer> integerList4 = new ArrayList<>();
        List<Integer> integerList5 = new ArrayList<>();
        List<Integer> integerList6 = new ArrayList<>();


        for (int i = 0; i < 200000; i++) {
            integerList1.add(i);
            integerList2.add(i);
            integerList3.add(i);
            integerList4.add(i);
            integerList5.add(i);
            integerList6.add(i);

        }
        long start = System.currentTimeMillis();
        for (Integer integer1 : integerList1) {
            for (Integer integer2 : integerList2) {
                for (Integer integer3 : integerList3) {
                    for (Integer integer4 : integerList4) {
                        /*for (Integer integer5 : integerList5) {
                            for (Integer integer6 : integerList6) {





                            }

                        }*/
                    }
                }
            }
        }

        long stop = System.currentTimeMillis();
        System.out.println("Time taken to process : " + ( stop - start ));


    }

}
