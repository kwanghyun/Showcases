package concurrency.test.run;

import java.util.ArrayList;
import java.util.Random;

public class WorkerThread implements Runnable {

    private String command;
    private int iteration;
    private boolean printMode;
    
    public WorkerThread(String command, int iteration, boolean printMode){
        this.command=command;
        this.iteration = iteration;
        this.printMode = printMode;
    }

    public void run() {
        System.out.println("["+Thread.currentThread().getName()+"] Command = "+command);
        processCommand();
        System.out.println("["+Thread.currentThread().getName()+"] End.");
    }

    private void processCommand() {
        try {
//            Thread.sleep(1000);
            doSomething();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.command;
    }
    
    private void doSomething() throws InterruptedException{
    	
    	Random randomGenerator = new Random();
    	int index=1;
    	int done= 9000000;
//    	int done= 9;
    	int iteration = this.iteration;
    	int repeat = 0;
    	while(index<done && repeat < iteration){
    		ArrayList<String> list = new ArrayList<String>();
    		int randomInt = randomGenerator.nextInt(100000);
    		String value = "["+Thread.currentThread().getName()+"]::" + "No." + index +" ::RANDOM value is "+randomInt;
    		list.add(value);
    		if(printMode==true) System.out.println(value);	
    		index++;
    		if(index == done){
    			index = 0;
    			repeat++;
    		}

    	}
    	
    }
//    public static void main(String args[]) throws InterruptedException{
//    	WorkerThread thread = new WorkerThread("Main"); 
//    	thread.doSomething();
//    }
}