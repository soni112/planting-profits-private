package com.decipher.test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by abhishek on 3/12/15.
 */
public class test {

    public void showDetails()
    {
        test.Demo demoObj=new test.Demo();
        System.out.println("test.showDetails : "+demoObj);

    }
    public void displayMethodInfo() throws ClassNotFoundException
    {
        Class c=Class.forName("com.decipher.test.test");
        Field meth[]=c.getFields();


        for(Field methObj : meth)
        {
            System.out.println("Method : "+methObj.getName()+"\t Is static : "+ Modifier.isStatic(methObj.getModifiers()));
        }
    }
    public static void main(String... args)throws ClassNotFoundException
    {
        int i=10;
        String s="JAVA";
        new test().displayMethodInfo();

        /*Demo t=new test().new Demo();
         //t.s howDetails();
        Demo d1=t.new Demo();*/
//        String str = "$200.00";
//        System.out.println(str.replaceAll("\\$",""));

        /*Locale[] locales = Locale.getAvailableLocales();


int i = 0;
        for (Locale locale : locales) {

            if (locale.getDisplayCountry().equalsIgnoreCase("India")) {
                i++;
                System.out.println("called " + i);
                String iso = locale.getISO3Country();
                String code = locale.getCountry();
                String name = locale.getDisplayCountry();

                System.out.println("iso :" + iso);
                System.out.println("code :" + code);
                System.out.println("name :" + name);
            }
        }*/


        /*Map<Integer, String> map = new TreeMap<int, String>();

        // Add Items to the TreeMap
        map.put(new Integer(1), "One");
        map.put(new Integer(3), "Two");
        map.put(new Integer(2), "Three");

        // Iterate over them
        for (Map.Entry<int, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }*/

        /*int[] array = new int[]{9,5,8,7,6,3,7,22,55,77,88,02};

        int highest = int.MIN_VALUE;
        int secondHighest = int.MIN_VALUE;

// Loop over the array
        for (int i = 0; i < array.length; i++) {

            // If we've found a new highest number...
            if (array[i] > highest) {

                // ...shift the current highest number to second highest
                secondHighest = highest;

                // ...and set the new highest.
                highest = array[i];
            } else if (array[i] > secondHighest)
                // Just replace the second highest
                secondHighest = array[i];
        }

        System.out.println("highest = " + highest);
        System.out.println("secondHighest = " + secondHighest);*/

        /*double d = 0.013636363636364113;
        DecimalFormat formatter = new DecimalFormat("#.00");
        System.out.println("formatter.format(d) = " + formatter.format(d));*/


       /* String number = "";

        for (int i = 10000; i <= 20000; i++) {
            int counter = 0;
            for (int num = i; num >= 1; num--) {
                if (i % num == 0) {
                    counter = counter + 1;
                }
            }
            if (counter == 2) {
                //Appended the number to the String
                number = number + i + " ";
            }
        }
        System.out.println("numbers from 1 to 100 are :");
        System.out.println(number);*/

//       test test = new test();
//
//        System.out.println("getRandomNumber() = " + test.getRandomNumberDouble(54.0, 54.0));

    }

    Double getRandomNumberDouble(Double minimum, Double maximum) {
        return Math.floor(Math.random() * (maximum - minimum + 1)) + minimum;
    }

    class Demo{

        public  int[] arr;

        Demo(){
            arr = new int[5];
            arr[0] = 0;
            arr[1] = 0;
            arr[2] = 0;
        }

    }
}
