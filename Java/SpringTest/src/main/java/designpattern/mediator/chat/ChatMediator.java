package designpattern.mediator.chat;

/*
 * Mediator Pattern is one of the behavioral design pattern, so it deals with
 * the behaviors of objects. Mediator design pattern is used to provide a
 * centralized communication medium between different objects in a system.
 * According to GoF, mediator pattern intent is:
 * 
 * Allows loose coupling by encapsulating the way disparate sets of objects
 * interact and communicate with each other. Allows for the actions of each
 * object set to vary independently of one another.
 * 
 * Mediator design pattern is very helpful in an enterprise application where
 * multiple objects are interacting with each other. If the objects interact
 * with each other directly, the system components are tightly-coupled with each
 * other that makes maintainability cost higher and not flexible to extend
 * easily. Mediator pattern focuses on provide a mediator between objects for
 * communication and help in implementing lose-coupling between objects.
 */

public interface ChatMediator {

	public void sendMessage(String msg, User user);

	void addUser(User user);
}
