package com.decipher.agriculture.main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class ClassToBeSorted implements Comparable<ClassToBeSorted>{
	int value;
	
	ClassToBeSorted(int value){
		this.value = value;
	}
	@Override
	public String toString(){
		return this.value+"";
	}
	@Override
	public int compareTo(ClassToBeSorted o) {
		return (this.value < o.value) ? -1 : ((this.value == o.value) ? 0 : 1);
	}
	
}
class AsscendingSort implements Comparator<ClassToBeSorted>{

	@Override
	public int compare(ClassToBeSorted o1, ClassToBeSorted o2) {
		return (o1.value < o2.value) ? -1 : ((o1.value == o2.value) ? 0 : 1);
	}
	
}
class DescendingSort implements Comparator<ClassToBeSorted>{

	@Override
	public int compare(ClassToBeSorted o1, ClassToBeSorted o2) {
		return (o2.value < o1.value) ? -1 : ((o2.value == o1.value) ? 0 : 1);
	}
}

class SortingComparator {

	class DescendinSortForClassToBeSorted implements
			Comparator<ClassToBeSorted> {

		@Override
		public int compare(ClassToBeSorted o1, ClassToBeSorted o2) {
			return (o2.value < o1.value) ? -1
					: ((o2.value == o1.value) ? 0 : 1);
		}
	}
}
public class MainClass{
	public static void main1(String[] args) {
		List<ClassToBeSorted> classToBeSorteds = new ArrayList<ClassToBeSorted>();
		classToBeSorteds.add(new ClassToBeSorted(10));
		classToBeSorteds.add(new ClassToBeSorted(5));
		classToBeSorteds.add(new ClassToBeSorted(6));
		classToBeSorteds.add(new ClassToBeSorted(12));
		classToBeSorteds.add(new ClassToBeSorted(8));
		classToBeSorteds.add(new ClassToBeSorted(15));
		classToBeSorteds.add(new ClassToBeSorted(20));
		Comparator<ClassToBeSorted> asscendingSort = new AsscendingSort();
		java.util.Collections.sort(classToBeSorteds, asscendingSort);
		System.out.println(classToBeSorteds);
		Comparator<ClassToBeSorted> descendingSort = new DescendingSort();
		java.util.Collections.sort(classToBeSorteds, descendingSort);
		System.out.println(classToBeSorteds);
		java.util.Collections.sort(classToBeSorteds);
		System.out.println(classToBeSorteds);
		SortingComparator sortingComparator = new SortingComparator();
		Comparator<ClassToBeSorted> innerdescendingSort = sortingComparator.new DescendinSortForClassToBeSorted();
		java.util.Collections.sort(classToBeSorteds, innerdescendingSort);
		System.out.println(classToBeSorteds);
	}
	public static void main(String[] args) {
		Set<Integer> hello = new HashSet<Integer>();
		hello.add(10);
		hello.add(20);
		hello.add(30);
		hello.add(40);
		hello.add(50);
		hello.add(60);
		hello.add(70);
		/*for(int integer : hello){
			System.out.println(integer);
			if(integer == 20){
				hello.remove(integer);
//				break;
			}
		}*/
		System.out.println(hello.size());
		Iterator<Integer> iterator = hello.iterator();
		while(iterator.hasNext()){
			int var = iterator.next();
			System.out.println(var);
			if(var == 20){
				iterator.remove();
//				break;
			}
		}
		System.out.println(hello.size());
	}
}