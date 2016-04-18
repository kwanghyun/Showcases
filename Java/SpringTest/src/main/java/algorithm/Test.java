package algorithm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE/180);
	}
//	public static void main(String[] args) throws InterruptedException {
//		AtomicInteger idx = new AtomicInteger();
//		int pathSize = 64;
//		
//		int preVal = 0;
////		int preVal = Integer.MAX_VALUE - 5;
//		
//
//		
//		while(true){
//
//			Thread.sleep(1);
//			int min = preVal + idx.getAndAdd(pathSize);
//			int max = min + pathSize;
//			System.out.println("# preVal => " + preVal);
//			System.out.println("# min => " + min);
//			System.out.println("# max => " + max);
//			int pathSizeTemp = pathSize;
//
//			while(min < max && pathSizeTemp > 0){
//				min ++;
//				pathSizeTemp --;
////				System.out.println("Loop preVal => " + preVal);
////				System.out.println("Loop min => " + min);
////				System.out.println("Loop max => " + max);
//			}
//			if(max < 0)
//				break;
//		}
//
//	}
}
