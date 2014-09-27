package algorithm.stringArray;

public class RemoveSpecifiedCharacters {
	
	public String removeChars(String str, String remove) {
		char targetArr[] = str.toCharArray();
		boolean needToDeletes[] = new boolean[targetArr.length]; 
		char removeArr[] = remove.toCharArray();
		int matchedCount = 0;
		
		for(int i =0; i<targetArr.length; i++){
			for(int j=0; j<removeArr.length; j++){
				if(targetArr[i] == removeArr[j]){
					needToDeletes[i] = true;
					matchedCount++;
					break;
				}else{
					needToDeletes[i] = false;
				}
			}
		}
		
		char resultArr[] =  new char[targetArr.length - matchedCount]; 
		
		int index = 0;
		for(int i = 0; i< targetArr.length; i++){
			if(needToDeletes[i] == false){
				resultArr[index] = targetArr[i];
				index++;	
			}
		}
		return String.valueOf(resultArr);
	}

	public static void main(String args[]) {
		String remove = "aeiou";
		String str = "Battle of the Vowels: Hawaii vs. Grozny";
		RemoveSpecifiedCharacters rsc = new RemoveSpecifiedCharacters();
		String result = rsc.removeChars(str, remove);
		System.out.println("STR : " + str);
		System.out.println("RMV : " + result);
	}

}
