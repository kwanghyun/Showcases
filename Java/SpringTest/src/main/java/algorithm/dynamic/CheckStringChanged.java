package algorithm.dynamic;

/*Given two strings, return true if they are one edit away from each other, 
 * else return false. An edit is insert/replace/delete a character. 
Ex. {"abc","ab"}->true, {"abc","adc"}->true, {"abc","cab"}->false
 */
public class CheckStringChanged {

	public boolean isChanged(String org, String dest) {
		boolean isChangedOnce = true;
		int diffCount = 0;

		if (Math.abs(org.length() - dest.length()) > 1)
			return false;

		
		for (int i = 0; i < org.length(); i++) {
			if (org.charAt(i) != dest.charAt(i)) {
				diffCount++;
				System.out.println("diffCount :: " + diffCount);
				if (diffCount > 2){
					isChangedOnce = false;
				}
					
			}
		}
		
		return isChangedOnce;
	}

}
