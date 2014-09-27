package algorithm.recursion;

import java.util.ArrayList;

public class Basic {
	/*
	 * @input : 4 
	 * @output : 4, 3, 2, 1, [1] 1 , [2] 2 , [2] 2 
	 */
	public static int recursionCheck(int count){
		//Stopping point
		if(count == 0) return 0;
		
		System.out.println("1. count : " + count);
		
		recursionCheck(count -1);
		
		for(int i = 0; i <count; i++){
			System.out.println("2. count["+count+"] : " + i);
		}
		return count;
	}
	
	public static int recursionCheck2(int count){
		//Stopping point
		if(count == 0) return 0;
		
		System.out.println("1. count : " + count);
		
		count = recursionCheck2(count -1);
		
		for(int i = 0; i <count; i++){
			System.out.println("2. count["+count+"] : " + i);
		}
		return count;
	}
	
	public static int recursionCheck3(int count, String str){
		//Stopping point
		if(count == 0) return 0;
		
		System.out.println("1. count : " + count);
		
		recursionCheck3(count -1, str);
		
		for(int i = 0; i <count; i++){
			str += "["+ count +"] : " + i + ", ";
		}
		
		System.out.println("Result : " + str);
		return count;
	}
	
	
	public static String recursionCheck4(int count, String str){
		//Stopping point
		if(count == 0) return str;
		
		str += count + " : ";
		
		str = recursionCheck4(count -1, str);
		
		for(int i = 0; i <count; i++){
			str += "["+ count +"] : " + i + ", ";
		}
		
		System.out.println("Result : " + str);
		return str;
	}
	
	public static int recursionCheckWithArray(int count, ArrayList<Integer> list){
		//Stopping point
		if(count == 0) return 0;
		
		list.add(count);
		
		recursionCheck(count -1);
		
		for(int i = 0; i <count; i++){
			System.out.println("3. count["+count+"] : " + i);
		}
		return count;
	}
	
	
	public static void main(String args[]){
//		ArrayList<String> list = getPerm("123");
//		for(String s : list)
//			System.out.println(s);
		System.out.println("----------------@Plain@----------------");
		System.out.println("@Retured : " + recursionCheck(4));
		System.out.println("----------------@With return@----------------");
		System.out.println("@Retured : " + recursionCheck2(4));
		System.out.println("----------------@String without return@----------------");
		System.out.println("@Retured : " + recursionCheck3(4, ""));
		System.out.println("----------------@String with return@----------------");
		System.out.println("@Retured : " + recursionCheck4(4, ""));
	}
}
