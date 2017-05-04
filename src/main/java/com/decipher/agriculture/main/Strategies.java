package com.decipher.agriculture.main;

import java.util.Scanner;

public class Strategies {
	static int total_resource;
	static Scanner s;
	static int total_available_resource[];
	static int resource_for_first_strategy[];
	static int resource_for_second_strategy[];
	static float output_of_strategy_first=0;
	static float output_of_strategy_second=0;
	
	public static void main(String args[]){
		 s=new Scanner(System.in);
		 getResource();
		 getResourcesRequireForFirstStrategi();
		 getResourcesRequireForSecondStrategi();
		 showRecords();
		 calculation(); 
	}
	
	public static void getResource(){
		System.out.println("Enter Total Number Of Resources !!!");
		total_resource=s.nextInt();
		total_available_resource=new int[total_resource];
		for(int i=0;i<total_resource;i++){
			System.out.println("Enter The Resource On "+(i+1));
			total_available_resource[i]=s.nextInt();
		}
	}
	
	public static void getResourcesRequireForFirstStrategi(){
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("Enter Resources For Strategy First ");
		resource_for_first_strategy=new int[total_resource];
		for(int i=0;i<total_resource;i++){
			System.out.println("Enter Amount Of Resource Out Of ["+total_available_resource[i]+"]");
			resource_for_first_strategy[i]=s.nextInt();
		}
	}
	
	public static void getResourcesRequireForSecondStrategi() {
		System.out
				.println("-----------------------------------------------------------------------------------------");
		System.out.println("Enter Resources For Strategy Second ");
		resource_for_second_strategy = new int[total_resource];
		for (int i = 0; i < total_resource; i++) {
			System.out.println("Enter Amount Of Resource Out Of ["
					+ total_available_resource[i] + "]");
			resource_for_second_strategy[i] = s.nextInt();
		}
	}
	
	public static void showRecords(){
		System.out.println("Total Resources         Strategy1				Strategy2 ");
		for(int i=0;i<total_resource;i++){
			System.out.println(total_available_resource[i]+"			"+resource_for_first_strategy[i]+"			"+resource_for_second_strategy[i]);
		}
	}
	
	public static void calculation(){
		for(int i=0;i<total_resource;i++){
			output_of_strategy_first+=(resource_for_first_strategy[i]*100)/total_available_resource[i];
			output_of_strategy_second+=(resource_for_second_strategy[i]*100)/total_available_resource[i];
			System.out.println(output_of_strategy_first+"	"+output_of_strategy_second);
		}
		output_of_strategy_first=output_of_strategy_first/total_resource;
		output_of_strategy_second=output_of_strategy_second/total_resource;
		
		System.out.println();
		System.out.println("Final Result Of Strategy 1  "+output_of_strategy_first);
		System.out.println("Final Result Of Strategy 2  "+output_of_strategy_second);
		
		if(output_of_strategy_first>output_of_strategy_second)
			System.out.println("Strategy First Is Better !!! ");
		else
			System.out.println("Strategy Second Is Better !!! ");
	}
}
