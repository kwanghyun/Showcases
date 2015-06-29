package algorithm.stringArray;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FirstNonrepeatedCharacterTest {
	String str;
	String str2;
	FirstNonrepeatedCharacter fnc = new FirstNonrepeatedCharacter() ;

	@Before
	public void setUp() throws Exception {
		str = "kajbakabakaebebfbcecgcgc"; // j is first char
		str2 = "abcdefg"; // a is first char
	}

	@Test
	public void testFirstNonRepeated() {
		Assert.assertEquals("First char should be j", new Character('j'),
				fnc.firstNonRepeated(str));
	}

	@Test
	public void testFirstNonRepeated2() {
		Assert.assertEquals("First char should be a", new Character('a'),
				fnc.firstNonRepeated(str2));
	}

}
