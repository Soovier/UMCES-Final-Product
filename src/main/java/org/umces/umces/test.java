package org.umces.umces;

import java.io.FileNotFoundException;
import java.util.List;

public class test {
	public static void main(String[] args) throws FileNotFoundException {
		UnixHandler v1 = new UnixHandler("sosunkunle", "APwK#r@7i7H$", "sosunkunle-01.al.umces.edu", 22);
		v1.startConnection("/home/sosunkunle/SAOfile");
		List<String> v2 = v1.listFilesInDirectory();
		System.out.println(v2);

	}
	

}
