package spring.config;

public class SequenceGenerator {

	private PrefixGenerator pg;
	private String suffix;
	private int initial;
	private int counter;
	
	public SequenceGenerator() {}
	
	public SequenceGenerator(PrefixGenerator pg, String suffix, int initial) {
		this.pg = pg;
		this.suffix = suffix;
		this.initial = initial;
	}
	public void setPrefixGenerator(PrefixGenerator pg) {
		this.pg = pg;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public void setInitial(int initial) {
		this.initial = initial;
	}
	
	public synchronized String getSequence(){
		StringBuffer sb = new StringBuffer();
		sb.append(pg.getPrefix());
		sb.append(initial + counter++);
		sb.append(suffix);
		return sb.toString();
	}
	
}
