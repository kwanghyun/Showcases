package designpattern.mycode.builder;

public class MessageBuilder {
	String message = "";
	
	public MessageBuilder appendMessage(String msg){
		this.message += msg;
		return this;
	}
	
	public MessageBuilder addNewLine(){
		this.message += "\n";
		return this;
	}

	public MessageBuilder cutPaper(){
		this.message += "\n-----------------------";
		return this;
	}
	
	public String build(){
		return message;
	}
	
	public static void main(String[] args) {
		MessageBuilder messageBuilder = new MessageBuilder();
		messageBuilder.appendMessage("This is the test message");
		messageBuilder.addNewLine();
		messageBuilder.appendMessage("This is the test message");
		messageBuilder.cutPaper();
		System.out.println(messageBuilder.build());
	}
}
