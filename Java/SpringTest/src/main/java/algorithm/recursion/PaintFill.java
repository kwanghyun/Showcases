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

	static Color[][] screen = { { Color.Black, Color.Black, Color.Black, Color.White },
			{ Color.Black, Color.White, Color.White, Color.Black },
			{ Color.Black, Color.White, Color.White, Color.Black },
			{ Color.Black, Color.Black, Color.White, Color.Black } };

	public void paint(Color[][] screen, int c, int r, Color oldColor, Color newColor) {

		if (c < 0 || c >= screen[0].length || r < 0 || r >= screen.length)
			return;

		if (screen[r][c] == oldColor) {
			screen[r][c] = newColor;
			paint(screen, c + 1, r, oldColor, newColor);
			paint(screen, c, r + 1, oldColor, newColor);
			paint(screen, c - 1, r, oldColor, newColor); 
			paint(screen, c, r - 1, oldColor, newColor); 
		}
	}

	public static void main(String args[]) {
		PaintFill obj = new PaintFill();
		obj.paint(screen, 2, 2, Color.White, Color.Green);
		for (Color[] arr : obj.screen)
			System.out.println(Arrays.toString(arr));
	}
}
