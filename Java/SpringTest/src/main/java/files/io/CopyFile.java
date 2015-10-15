package files.io;

import java.io.*;

public class CopyFile {
	/*
	 * - Object -> InputStream -> FileInputStream 
	 * - Object -> OutputStream ->
	 * FileOutputStream Following constructor takes a file name as a string to
	 * create an input stream object to read the file.: 
	 * 		InputStream f = new
	 * 		FileInputStream("C:/java/hello"); 
	 * 
	 * Following constructor takes a file object to create an input stream 
	 * object to read the file. First we create a file object using File() method as follows: 
	 * 		File f = new File("C:/java/hello"); 
	 * 		InputStream f = new FileInputStream(f);
	 */
	public static void copyFile4FileStream() throws IOException {
		FileInputStream in = null;
		FileOutputStream out = null;
		
		try {
			in = new FileInputStream("input.txt");
//			out = new FileOutputStream("output.txt");
		
			int c;
			while ((c = in.read()) != -1) {
				System.out.print((char) c);
				out.write((char) c);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	/*
	 * internally FileReader uses FileInputStream and FileWriter uses
	 * FileOutputStream but here major difference is that FileReader reads two
	 * bytes at a time and FileWriter writes two bytes at a time.
	 */
	public static void copyFile4File() throws IOException {
		FileReader in = null;
		FileWriter out = null;

		try {
			in = new FileReader("C:/test.txt");
			out = new FileWriter("C:/testCopy.txt");
			
			int c;
			while ((c = in.read()) != -1) {
				System.out.print((char) c);
				out.write((char) c);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	
	private static void readLineByLine() throws IOException {
		
		File file = new File("C:/test.txt");
		FileInputStream input = new FileInputStream(file);
		//Construct BufferedReader from InputStreamReader
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
	}


	public static void main(String[] args) {
		try {
//			CopyFile.writeFileByLine();
//			CopyFile.readLineByLine();
			CopyFile.copyFile4File();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		


	public static void streamTest() {

		try {
			byte bWrite[] = { 11, 21, 3, 40, 5 };
			OutputStream os = new FileOutputStream("test.txt");
			
			for (int x = 0; x < bWrite.length; x++) {
				os.write(bWrite[x]); // writes the bytes
			}
			os.close();

			InputStream is = new FileInputStream("test.txt");
			// Gives the number of bytes that can be read from this file input
			// stream. Returns an int.
			int size = is.available();

			for (int i = 0; i < size; i++) {
				System.out.print((char) is.read() + "  ");
			}
			
			is.close();
			
		} catch (IOException e) {
			System.out.print("Exception");
		}
	}
	
	

}
