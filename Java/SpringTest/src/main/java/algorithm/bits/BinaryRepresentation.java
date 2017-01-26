package algorithm.bits;

/*Write a program to print Binary representation of a given number*/
public class BinaryRepresentation {

	public void print(int num) {
		for (int i = 31; i >= 0; i--) {
			if (((num >> i) & 1) == 1)
				System.out.print(1);
			else
				System.out.print(0);
		}
	}

	public static void main(String[] args) {
		BinaryRepresentation ob = new BinaryRepresentation();
		System.out.println("5 is ");
		ob.print(5);
		System.out.println("");
		System.out.println("8 is ");
		ob.print(8);
	}
}
