package files.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Sample {
	public static String readTextFile(String fileName) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(fileName)));
		return content;
	}

//	public static List<String> readTextFileByLines(String fileName)
//			throws IOException {
//		List<String> lines = Files.readAllLines(Paths.get(fileName));
//		return lines;
//	}

	public static void writeToTextFile(String fileName, String content)
			throws IOException {
		Files.write(Paths.get(fileName), content.getBytes(),
				StandardOpenOption.CREATE);
	}

	public static void main(String[] args) throws IOException {

		String input = Sample.readTextFile("file.txt");
		System.out.println(input);
		Sample.writeToTextFile("copy.txt", input);

		System.out.println(Sample.readTextFile("copy.txt"));

//		Sample.readTextFileByLines("file.txt");
//		Path path = Paths.get("file.txt");
	}
}
