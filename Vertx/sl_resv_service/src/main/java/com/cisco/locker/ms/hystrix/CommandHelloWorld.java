package com.cisco.locker.ms.hystrix;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws InterruptedException {
//    	Thread.sleep(500L);
        // a real example would do work like a network call here
    	Thread.sleep(2000L); //Change to timeout
        return "Hello " + name + "!";
    }
    
    @Override
    protected String getFallback() {
        return "Fallback!!!" + name + "!";
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	
    	Future<String> fs = new CommandHelloWorld("World").queue();
    	String str = fs.get();
    	System.out.println(str);
    	
    	String s = new CommandHelloWorld("World").execute();
    	System.out.println(s);
	}
}