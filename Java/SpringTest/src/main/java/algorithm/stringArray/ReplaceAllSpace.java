package algorithm.stringArray;

public class ReplaceAllSpace {
//	Write a method to replace all spaces in a string with ¡®%20¡¯.
	
	public String solution(String str){
		if(str == null)
			throw new IllegalArgumentException();
		StringBuilder sb = new StringBuilder();

		for(int i =0; i < str.length(); i++){
			if(str.charAt(i)==' '){
				sb.append("%20");
			}else{
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
	}
	
	public static void main(String args[]){
		ReplaceAllSpace rls = new ReplaceAllSpace();
		System.out.println(rls.solution("asdf"));
		System.out.println(rls.solution("a d "));
		System.out.println(rls.solution("a  f"));
		System.out.println(rls.solution("a   "));
		System.out.println(rls.solution("    f"));
		System.out.println(rls.solution("    "));
//		System.out.println(rls.solution(null));
	}
}
