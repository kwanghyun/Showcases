package algorithm.etc;

/*Given an integer between 0 and 999,999, print an English phrase that 
 * describes the integer (eg, ¡°One Thousand, Two Hundred and Thirty Four¡±).
 * 
 */
public class CC_19_6_NumberToString {

	String[] arr1 = { "One", "Two", "Three", "Four", "Five", "Six", "Seven",
			"Eight", "Nine", "Ten" };
	String[] arr11 = { "Eleven", "Twowelve", "Thirteen", "FourTeen", "Fifteen",
			"Sixteen", "Eightteen", "Nineteen" };
	String[] arr10 = { "Twenty", "Thirty", "Fourty", "Fifty", "Sixty",
			"Seventy", "Eighty", "Ninety" };
	String[] arrbig = { "Hundred", "Thousand" };

	public String convertNumber(int num) {
		StringBuilder sb = new StringBuilder();

		if(num > 1000){
			int temp = 0;
			temp = num/1000;
			sb= convert(sb, temp);
			sb.append( " " + arrbig[1] + " ");
		}
		num = num%1000;
		sb = convert(sb, num);
		
		return sb.toString();
	}

	public StringBuilder convert(StringBuilder sb, int num){
		System.out.println("num : "+num);
		boolean flag = true;
		if (num / 100 > 0) {
			sb.append(arr1[num / 100 - 1] + " "+ arrbig[0] + " ");
			num %= 100;
		}
		if (num > 10 && num < 20) {
			sb.append(arr11[(num % 10) - 1] + " ");
			num %= 10;
			flag = false;
		} else if (num > 20) {
			sb.append(arr10[num / 10 - 2] + " ");
			num %= 10;
		} 
		
		if(flag)
			sb.append(arr1[num - 1] + " ");
		
		return sb;
	}
	
	public static void main(String args[]) {
		CC_19_6_NumberToString nts = new CC_19_6_NumberToString();

		System.out.println(nts.convertNumber(1925));
	}
}
