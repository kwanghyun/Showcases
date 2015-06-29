package algorithm.recursion;

import algorithm.trees.TreeNode;


public class Basic {
	/* * * * 
	 * 1. Return with recursive call : Use when you need to keep track of return value. 
	 * 		(i.e. Return list of collected return value at the end) 
	 * 2. Without return with recursive call : Use when you want to handle the return value
	 * 		at that point.
	 * 		(i.e. Print the value when it meet the condition)
	 * */ 
	public String withoutReturnAtEnd(int start, int end, String str){
		
		if(start == end) 
			return str;
		
		str = str + start;
		withoutReturnAtEnd(start + 1, end, str);
				
		return str;
	}
	// After first return, it still have 4 method stacks, which will lead to
	// return first stack's str
	
	public String withReturnAtEnd(int start, int end, String str){
		
		if(start == end) 
			return str;
		
		str = str + start;
		str = withReturnAtEnd(start + 1, end, str);
		
		return str;
	}

	public String withReturnAlltogether(int start, int end, String str){
		
		if(start == end) 
			return str;

		return start + withReturnAtEnd(start + 1, end, str);
	}

	
	//All return the same value 
	public int sum(TreeNode root ){
		if(root == null)
			return 0;
		return  root.value + sum(root.left) + sum(root.right);
	}
	
	public int treeSum(TreeNode root){
		if(root == null) 
			return 0;		
		int sum =  root.value + treeSum(root.left) + treeSum(root.right);
		return sum;
	}
	
	public int treeSum(TreeNode root, int sum){
		if(root == null) 
			return 0;		
		sum =  root.value + treeSum(root.left, sum) + treeSum(root.right, sum);
		return sum;
	}

	
	//The reason why string is not work for this algorithm is
	//string can't remove previous string added when it return to the previous call.
	//in case of char array it simply overwrite that index element. 
	public void recurWithString(int open, int close, String str){
		if(open > 3 || close > 3)
			return;
		
		if(open == 3 && close == 3)
			System.out.println(str);
		else{
			str = str + '(';
			recurWithString(open + 1, close, str);
			
			str = str + ')';
			recurWithString(open, close + 1, str);			
		}
	}

	public void recurWithCharArr(int open, int close, char[] chArr, int index){
		if(open > 3 || close > 3)
			return;
		
		if(open == 3 && close == 3)
			System.out.println(chArr);
		else{
			chArr[index] = '(';
			recurWithCharArr(open + 1, close, chArr, index + 1);
			
			chArr[index] = ')';
			recurWithCharArr(open, close + 1, chArr, index + 1);
			
		}
	}
	


	

	public static void main(String args[]){
		Basic b = new Basic();
		System.out.println("----------------@WithoutReturn at the end@----------------");
		System.out.println("@Retured : " + b.withoutReturnAtEnd(0, 4, ""));
		System.out.println("----------------@WithReturn at the End@----------------");
		System.out.println("@Retured : " + b.withReturnAtEnd(0, 4, ""));
		System.out.println("----------------@WithReturn all together@----------------");
		System.out.println("@Retured : " + b.withReturnAlltogether(0, 4, ""));
		System.out.println("@Retured : " + b.withoutReturnAtEnd(0, 4, ""));
		System.out.println("----------------@recurWithString@----------------");
		b.recurWithString(0 ,0 ,"");
		System.out.println("----------------@recurWithCharArr@----------------");
		char[] chArr = new char[6];
		b.recurWithCharArr(0, 0, chArr, 0);
	}
}
