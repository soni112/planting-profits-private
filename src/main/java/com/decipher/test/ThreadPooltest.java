package com.decipher.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by abhishek on 17/10/16.
 */
public class ThreadPooltest {

    private static final ExecutorService es = Executors.newCachedThreadPool();
    private static List<Integer> array = new ArrayList<>();
    private static int count = 0;

    public static synchronized void incrementCount() {
        count++;
    }

    private void process(List<Integer> strings) {
        TempListHolder t1 = new TempListHolder();
        t1.setIntegerList(strings);

        Thread thread = new Thread(t1);
//        thread.start();
        es.execute(thread);
    }


    public static void main(String[] args) throws Exception {

        int i, j, limit;
        for (j = 0; j < 954321; j++) {
            array.add(j);
        }

        List<List<Integer>> combinationsListForCalculation = processCombinationsForCalculation(array);

//        ExecutorService es = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        for (i = 0, limit = combinationsListForCalculation.size(); i < limit; i++) {
//            TempListHolder.setIntegerList(combinationsListForCalculation.get(i));
            System.out.println("i = " + i);

            ThreadPooltest threadPooltest = new ThreadPooltest();
            threadPooltest.process(combinationsListForCalculation.get(i));
        }
        es.shutdown();
        boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        long stop = System.currentTimeMillis();
        if (finished) {

            System.out.println("task complete " + (stop - start));
        } else {
            System.out.println("task incomplete");
        }


    }

    private class TempListHolder implements Runnable {
        private List<Integer> integerList;

        private synchronized List<Integer> getIntegerList() {
            return integerList;
        }

        private void setIntegerList(List<Integer> integerList) {
            this.integerList = integerList;
        }

        @Override
        public void run() {
            List<Integer> integerList = getIntegerList();


            for (int integer : integerList) {

            }
            System.out.println("integerList = " + integerList.get(0));
            incrementCount();

        }
    }

    private static List<List<Integer>> processCombinationsForCalculation(List<Integer> array) {

        Integer combinationLength = array.size();

        String comLenStr = combinationLength.toString();
        List<List<Integer>> setsLists = new ArrayList<>();

        if (comLenStr.length() == 4) {
            Double nearestThousand = Math.ceil((combinationLength + 999) / 1000) * 1000;
//            System.out.println("Nearest Thousand = " + nearestThousand);
//            System.out.println("Nearest Thousand int Value= " + nearestThousand.intValue());

            Double tenPercent = (nearestThousand * 10) / 100;

//            System.out.println("Ten Percent = " + tenPercent);

            Double sets = nearestThousand / tenPercent;
//            System.out.println("Sets = " + sets);


            getSubList(sets.intValue(), combinationLength, tenPercent.intValue(), setsLists);


        } else if (comLenStr.length() >= 5) {

            Double nearestThousand = Math.ceil((combinationLength + 999) / 1000) * 1000;
//            System.out.println("Nearest Thousand = " + nearestThousand);
//            System.out.println("Nearest Thousand int Value= " + nearestThousand.intValue());

            Double fivePercent = (nearestThousand * 3) / 100;

//            System.out.println("Five Percent = " + fivePercent.intValue());
            Double sets = nearestThousand / fivePercent;
//            System.out.println("Sets = " + sets);

            getSubList(sets.intValue(), combinationLength, fivePercent.intValue(), setsLists);

//            System.out.println("setsLists = " + setsLists);
        }

        return setsLists;
    }

    private static void getSubList(int noOfSets, int combinationLength, int percentage, List<List<Integer>> collectionToHold) {
        System.out.println("******************");
        for (int j = 1; j <= noOfSets; j++) {
            int limit, startLimit;
            if (j == noOfSets) {
                limit = combinationLength;
            } else {
                limit = j * percentage;
            }

            if (j == 1) {
                startLimit = 0;
            } else {
                startLimit = ((j * percentage) - percentage) + 1;
            }

//            System.out.println("startLimit = " + startLimit);
//            System.out.println("limit = " + limit);

            collectionToHold.add(array.subList(startLimit, limit));

        }
    }

}
