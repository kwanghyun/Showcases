package files.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadFileExample {

	public static void readFileJRE1_7() {

		File file = new File("C:/test.txt");

		try (FileInputStream fis = new FileInputStream(file)) {

			System.out.println("Total file size to read (in bytes) : "
					+ fis.available());

			int content;
			while ((content = fis.read()) != -1) {
				// convert to char and display it
				System.out.print((char) content);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}