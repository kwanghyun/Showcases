package algorithm.recursion;

import java.util.Arrays;

/*
 * Implement the "paint fill" function that one might see on many image 
 * editing programs. That is, given a screen (represented by a 2-dimensional 
 * array of Colors), a point, and a new color, fill in the surrounding area until 
 * you hit a border of that color.
 */

public class PaintFill {

	enum Color {
		Black, White, Red, Yellow, Green
	}

	static Color[][] screen = {
			{ Color.Black, Color.Black, Color.Black, Color.White},
			{ Color.Black, Color.White, Color.White, Color.Black },
			{ Color.Black, Color.White, Color.White, Color.Black },
			{ Color.Black, Color.Black, Color.White, Color.Black } };
	
	
	void paint(Color[][] screen, int x, int y, Color oldColor, Color newColor) {
	
		if (x < 0 || x >= screen[0].length || y < 0 || y >= screen.length) {
			return;
		}else if (screen[y][x] == oldColor) { 
			screen[y][x] = newColor;
			paint(screen, x + 1, y, oldColor, newColor); // right
			paint(screen, x, y + 1, oldColor, newColor); // bottom
			/*
			 * Minus are when the start x,y is other than 0,0.
			 * You can not travel back to 0,0 with Plus recursion only
			 */
			paint(screen, x - 1, y, oldColor, newColor); // left
			paint(screen, x, y - 1, oldColor, newColor); // top
			
		}
	}

	public static void main(String args[]) {
		PaintFill obj = new PaintFill();
		obj.paint(screen, 2, 2, Color.White, Color.Green);
		for (Color[] arr : obj.screen)
			System.out.println(Arrays.toString(arr));
	}
}
