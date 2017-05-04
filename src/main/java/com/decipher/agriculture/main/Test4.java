package com.decipher.agriculture.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

class Test4 {
    static Set<String> s = new HashSet<String>();

    public static void main(String str[]) {
        Set<String> inputSet = new HashSet<String>();
        for (int i = 0; i < str.length; i++) {
            inputSet.add(str[i]);
        }
        for (int i = 0; i < str.length; i++) {
            inputSet.add(str[i]);
        }


        List<Set<String>> subSets = new ArrayList<Set<String>>();
        for (String addToSets : inputSet) {
            List<Set<String>> newSets = new ArrayList<Set<String>>();
            for (Set<String> curSet : subSets) {
                Set<String> copyPlusNew = new HashSet<String>();
                copyPlusNew.addAll(curSet);
                copyPlusNew.add(addToSets);
                newSets.add(copyPlusNew);
            }
            Set<String> newValSet = new HashSet<String>();
            newValSet.add(addToSets);
            newSets.add(newValSet);
            subSets.addAll(newSets);
        }

        String str1 = "";

        for (Set<String> set : subSets) {
            int count = 0;
            for (String setEntry : set) {
                count++;
                if (count == 3) {
                    continue;
                }
                s.add(setEntry);
                //System.out.print(setEntry + " ");
                str1 += setEntry;
            }
            s.add(str1.toString());
            str1 = "";
            //System.out.println();
        }


    }


    public static void d() {
        Iterator<String> it = s.iterator();
        while (it.hasNext()) {
            String str = it.next();
            //JOptionPane.showMessageDialog(null, str);
            System.out.println(str);
        }

        System.out.println("total " + s.size());

    }

}