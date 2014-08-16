package designpattern;

public class Principles {

	/**
	 * ####Principles####
	 * 1. Encapsulate what varies.
	 * 2. Favor composition over inheritance.
	 * 3. Program to interfaces, not implementations.
	 * 4. Open0Clised Principle
	 * 	Classes should be open for extension, but closed for modification.
	 * 5. Dependency Inversion
	 * 	Depend on abstractions. Do not depend on concrete classes.
	 * 6. Principle of Least Knowledge - talk only to your immediate friends.
	 * 7. The Hollywood Principle - Don’t call us, we’ll call you.
	 * 8. Single Responsibility - A class should have only one reason to change.
	 */
	

	// 1. Strategy - defines a family of algorithms, encapsulates each one, and
	// makes them interchangeable. Strategy lets the algorithm vary
	// independently from clients that use it.
	
	// 2. Observer - defines a one-to-many dependency between objects so that
	// when one object changes state, all its dependents are notified and
	// updated automatically.
	
	// 3. Decorator Pattern - attaches additional responsibilities to an object
	// dynamically. Decorators provide a flexible alternative to subclassing for
	// extending functionality. (i.e, Java I/O InputStream)
	
	// 4. Factory Method Pattern defines an interface for creating an object,
	// but lets subclasses decide which class to instantiate. Factory Method
	// lets a class defer instantiation to subclasses.
	
	// 5. Abstract Factory - Provide an interface for creating families of related
	// or dependent object without specifying their concrete classes.
	
	// 6. Factory Method - Define an interface for creating an object, but let
	// subclasses decide which class to instantiate. Factory Method lets a class
	// defer instantiation to the subclasses.
	
	/* ###Factory Method VS Abstract Factory ### */
	// Factory Method relies on inheritance: object creation is delegated to
	// subclasses which implement the factory method to create objects.
	// Abstract Factory relies on object composition: object creation is
	// implemented in methods exposed in the factory interface.
	
	// 7. Singleton - Ensure a class only has one instance and provide a global
	// point of access to it.
	// Java’s implementation of the Singleton Pattern makes use of a private
	// constructor, a static method combined with a static variable.
	
	// 8. Command - Encapsulates a request as an object, thereby letting you
	// parameterize clients with different requests, queue or log requests, and
	// support un-doable operations.
	// When you need to decouple an object making requests from the objects that
	// know how to perform the requests, use the Command Pattern.
	
	// 9. Adapter Pattern converts the interface of a class into another
	// interface the clients expect. Adapter lets classes work together that
	// couldn't otherwise because of incompatible interfaces.
	
	// 10. Facade Pattern provides a unified interface to a set of interfaces in
	// a sub-system. Facade defines a higher-level interface that makes the
	// subsystem easier to use.
	
	// 11. The Template Method Pattern defines the skeleton of an algorithm in a
	// method, deferring some steps to subclasses. Template Method lets
	// subclasses redefine certain steps of an algorithm without changing the
	// algorithms structure.
	
	/* ###The Strategy VS Template Method Patterns### */
	// The Strategy and Template Method Patterns both encapsulate algorithms,
	// one by inheritance and one by composition.
	
	// 12. The Iterator Pattern provides a way to access the elements of an
	// aggregate object sequentially without exposing its underlying
	// representation.
	
	// 13. Composite Pattern allows you to compose objects into tree structures
	// to represent part-whole hierarchies. Composite lets clients treat
	// individual objects and compositions of objects uniformly.
	
	// 14. Composite Pattern allows you to compose objects into tree structures
	// to represent part-whole hierarchies. Composite lets clients treat
	// individual objects and compositions of objects uniformly.
	
	// 15. State - Allow an object to alter its behavior when its internal state
	// changes. The object will appear to change its class.
	
	// 16. Proxy Pattern provides a surrogate or placeholder for another object
	// to control access to it.

	// A remote proxy controls access to a remote object.
	// A virtual proxy controls access to a resource that is expensive to
	// create.
	// A protection proxy controls access to a resource based on access rights.
	
	// Proxy is structurally similar to Decorator, but the two differ in their
	// purpose.
	// The Decorator Pattern adds behavior to an object, while a Proxy controls
	// access.
	
	// 17. Compound Pattern combines two or more patterns into a solution that
	// solves a recurring or general problem.
	// The Model View Controller Pattern (MVC) is a compound pattern consisting
	// of the Observer, Strategy and Composite patterns.
	

}
