package org.umces.umces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class test {
	public static void main(String[] args) throws IOException {
		File v1 = new File("ds");
		try {
			Scanner UIS = new Scanner(v1);
			String Line = UIS.nextLine();
			FileWriter fw = new FileWriter(v1, true);

			int v21 = Integer.valueOf(Line.split(";")[0].substring(3));
			int v22 = Integer.valueOf(Line.split(";")[1].substring(3));

			fw.write("TP=" + (v21 + 1020) + ";FP=" + (v22 + 1290390) + ";");
			fw.close();
			System.out.println(v21);
			System.out.println(v22);
		} catch (FileNotFoundException e) {
			System.err.println("NOT WORKING");
			e.printStackTrace();
		}

	}
}
