package util;

public class LogUtil {

	public static void log(String str){
		System.out.println(str);
	}

	public static void log2(String str){
		System.out.print(str);
	}
	
	public static void writeline(){
		writeline('-', 50);
	}
	
	public static void writeline(char ch, int num){
		StringBuilder sb = new StringBuilder(); 
		for(int i =0; i<num; i++){
			sb.append(ch);
		}
		System.out.println(sb);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
