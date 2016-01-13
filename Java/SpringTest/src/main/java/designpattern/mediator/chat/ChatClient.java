package designpattern.mediator.chat;

public class ChatClient {

/*	Notice that client program is very simple and it has no idea how the message is getting handled and if mediator is getting user or not.

	Output of the above program is:

	Pankaj: Sending Message=Hi All
	Lisa: Received Message:Hi All
	Saurabh: Received Message:Hi All
	David: Received Message:Hi All*/
	public static void main(String[] args) {
		ChatMediator mediator = new ChatMediatorImpl();
		User user1 = new UserImpl(mediator, "Pankaj");
		User user2 = new UserImpl(mediator, "Lisa");
		User user3 = new UserImpl(mediator, "Saurabh");
		User user4 = new UserImpl(mediator, "David");
		mediator.addUser(user1);
		mediator.addUser(user2);
		mediator.addUser(user3);
		mediator.addUser(user4);

		user1.send("Hi All");

	}

}