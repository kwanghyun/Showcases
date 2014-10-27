package algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * A circus is designing a tower routine consisting of people standing atop 
 * one another¡¯s shoulders. For practical and aesthetic reasons, 
 * each person must be both shorter and lighter than the person 
 * below him or her. Given the heights and weights of each person 
 * in the circus, write a method to compute the largest possible 
 * number of people in such a tower.
 * 
 * EXAMPLE:
 * Input (ht, wt): 
 * (65, 100) (70, 150) (56, 90) (75, 190) (60, 95) (68, 110)
 * Output: The longest tower is length 6 and includes from top to bottom: 
 * (56, 90) (60,95) (65,100) (68,110) (70,150) (75,190)
 */
public class Circus {
	// Sort with merge sort O(log N)
	// list1 = Sorted by Hight
	// list2 = Sorted by Weight
	public List<Person> find(List<Person> list1, List<Person> list2){
		int length = list1.size();
		int list1_count = 0;
		int list2_count = 0;
		
		for( int i = 1; i <length; i++){
			if(list1.get(i-1).weight < list1.get(i).weight){
				list1_count++;
			}			
			if(list2.get(i-1).hight < list2.get(i).hight){
				list2_count++;
			}
		}
		return list1_count > list2_count ? list1 : list2;
	}
	
	public static void main(String args[]){
		int [][] hightBasedList = {{56, 90},{60,95},{63,92}, {65,100}, {68,110}, {70,150}, {75,190}};
		int [][] weightBasedList = {{56, 90},{60,95},{63,97}, {65,100}, {68,110}, {70,150}, {75,190}};
		
		ArrayList<Person> peopleByHight = new ArrayList<Person>();
		ArrayList<Person> peopleByWeight = new ArrayList<Person>();
		for(int[] data :hightBasedList){
			Person person = new Person(data[0], data[1]);
			peopleByHight.add(person);
		}
		for(int[] data :weightBasedList){
			Person person = new Person(data[0], data[1]);
			peopleByWeight.add(person);
		}
		
		Circus cs = new Circus();
		List<Person> list = cs.find(peopleByHight, peopleByWeight);
		
		System.out.println(list.size());
		System.out.println(Arrays.toString(list.toArray()));
	}
}



