package designpattern.decorator.starbuzz;

public abstract class Beverage {
	String description = "Basic Beverage";
  
	public String getDescription() {
		return description;
	}
 
	public abstract double cost();
}
