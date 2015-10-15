package algorithm.etc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class OoneDS {

	/* Java program to design a data structure that support folloiwng operations
	   in the 0(1) time
	   a) Insert
	   b) Delete
	   c) Search
	   d) getRandom */
		 
	class MyDS
	{
	   ArrayList<Integer> arr;
	 
	   // A hash where keys are array elements and vlaues are
	   // indexes in arr[]
	   HashMap<Integer, Integer>  hash;
	 
	   public MyDS()
	   {
	       arr = new ArrayList<Integer>();
	       hash = new HashMap<Integer, Integer>();
	   }
	 
	   void add(int x)
	   {
	      if (hash.get(x) != null)
	          return;
	 
	      int idx = arr.size();
	      arr.add(x);
	 
	      hash.put(x, idx);
	   }
	 
	   void remove(int x)
	   {
	       Integer index = hash.get(x);
	       if (index == null)
	          return;
	 
	       hash.remove(x);
	 
	       int size = arr.size();
	       Integer last = arr.get(size-1);
	       Collections.swap(arr, index,  size-1);

	       arr.remove(size-1);
	       hash.put(last, index);
	    }
	 
	    int getRandom()
	    {
	       Random rand = new Random();  
	       int index = rand.nextInt(arr.size());
	 
	       return arr.get(index);
	    }
	 
	    Integer search(int x)
	    {
	       return hash.get(x);
	    }
	}
}
