package algorithm.recursion;

import java.util.Arrays;

/*
 * Implement the "paint fill" function that one might see on many image 
 * editing programs. That is, given a screen (represented by a 2-dimensional 
 * array of Colors), a point, and a new color, fill in the surrounding area until 
 * you hit a border of that color.
 */

public class PaintFill {

	static Color[][] screen = {
			{ Color.Black, Color.Black, Color.Black, Color.White},
			{ Color.Black, Color.White, Color.White, Color.Black },
			{ Color.Black, Color.White, Color.White, Color.Black },
			{ Color.Black, Color.Black, Color.Black, Color.Black } };

	enum Color {
		Black, White, Red, Yellow, Green
	}

	boolean paint(Color[][] screen, int x, int y, Color oldColor, Color newColor) {

		if (x < 0 || x >= screen[0].length || y < 0 || y >= screen.length) {
			return false;
		}

		// by putting all recursive into inside of the block, not able to paint
		// over the boundary, only able to move 4 directions(up, down, left, right)
		if (screen[y][x] == oldColor) { 
			screen[y][x] = newColor;
			paint(screen, x - 1, y, oldColor, newColor); // left
			paint(screen, x + 1, y, oldColor, newColor); // right
			paint(screen, x, y - 1, oldColor, newColor); // top
			paint(screen, x, y + 1, oldColor, newColor); // bottom
		}
		return true;
	}

	public static void main(String args[]) {
		PaintFill obj = new PaintFill();
		obj.paint(screen, 1, 1, Color.White, Color.Green);
		for (Color[] arr : obj.screen)
			System.out.println(Arrays.toString(arr));
	}
}
