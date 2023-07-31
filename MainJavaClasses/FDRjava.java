package MainJavaClasses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FDRjava {
	private int TP_Positive;
	private int TP_Negative;
	
	public static void main(String[] args) {
		FDRjava test = new FDRjava();
		test.getClassification(args[0], args[1]);
		test.ShellCommandExample(args[2], args[3]);
		/**
		 * args[0] = Confusion_Data args[1] = CLASSIFICATITION (IMPORTANT) args[2] =
		 * PATH TO REMOTE DIRECTORY (ROOT) args[3] = new FILE NAME
		 */
	}

	public void getClassification(String fileName, String classification) {
		try {
			Scanner UIS = new Scanner(new File(fileName));
			int classNum;

			switch (classification) {
			case ("k"):
				classNum = 1;
				System.out.println("k");
				break;
			case ("p"):
				classNum = 2;
				break;
			case ("c"):
				classNum = 3;
				break;
			case ("o"):
				classNum = 4;
				break;
			case ("f"):
				classNum = 5;
				break;
			case ("g"):
				classNum = 6;
				break;
			case ("s"):
				classNum = 7;
				break;
			default:
				classNum = 0;
				break;
			}

			while (UIS.hasNextLine()) {
				String newData = UIS.nextLine().trim().split("[\t;]")[classNum].substring(3);

				if (newData.equals("TPP")) {
					TP_Positive += 1;
				} else if (newData.equals("TPN")) {
					TP_Negative += 1;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ShellCommandExample(String path, String fileName) {
		try {
			File FDRfile = new File(path + fileName);
			FileWriter fw = new FileWriter(FDRfile);
			Scanner UIS = new Scanner(FDRfile);

			if (!fileIsEmpty(FDRfile)) {
				String Line = UIS.nextLine();
				int currentTP = Integer.valueOf(Line.split(";")[0].substring(3));
				int currentFP = Integer.valueOf(Line.split(";")[1].substring(3));
				fw.write("TP=" + (this.TP_Positive + currentTP) + ";FP=" + (this.TP_Negative + currentFP));
			} else {
				fw.write("TP=" + this.TP_Positive + ";" + "FP=" + this.TP_Negative);
			}
			fw.close();
			UIS.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	boolean fileIsEmpty(File file) {
		return file.length() == 0;
		}

}
