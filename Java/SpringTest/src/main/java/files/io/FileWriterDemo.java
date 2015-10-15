package files.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileWriterDemo {
	private static void writeFile(String fileName) throws IOException {

		File file = new File(fileName);

		// Construct BufferedReader from FileReader
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//		writeSequences(writer);
		writeCustomerInfo(writer);
		writer.close();
	}

	private static void writeSequences(BufferedWriter writer)
			throws IOException {
		int num = 0;

		for (int j = 0; j < 10; j++) {
			StringBuilder line = new StringBuilder();
			for (int i = 1; i <= 10; i++) {
				num = j * 10 + i;
				line.append(num + " ");
			}
			System.out.println(line);
			writer.write(line.toString());
			writer.newLine();
		}
	}
	
	private static void writeCustomerInfo(BufferedWriter writer)
			throws IOException {

		for (int j = 0; j < 100000; j++) {
			String line = "customer-" + getRandomNum(1, 10001) + ",product-" + getRandomNum(1, 6) + "," + getRandomNum(100, 10001);
//			System.out.println(line);
			writer.write(line);
			writer.newLine();
		}
	}
	
	private static int getRandomNum(int min, int max){
		Random r = new Random();
		return r.nextInt(max - min) + min;
	}
	
	public static void main(String[] args) {
		for(int i = 1; i <=3; i ++){
			try {
				FileWriterDemo.writeFile("C:/TestFiles/cust" + i + ".txt");
				System.out.println("DONE");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
