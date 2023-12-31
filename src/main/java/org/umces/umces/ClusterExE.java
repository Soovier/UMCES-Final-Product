package org.umces.umces;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// This was made by Stephen Osunkunle 2023 Internship
// Creates Files In The SAME DIRECTORY (RELATIVE) IT WAS FIRED IN!!
public class ClusterExE {
	private JPanel logsPanel;
	private HashMap<String, String> hashMap;
	private JFrame frame;
	UnixHandler sameputty;

	public ClusterExE() {
		System.err.println("Cluster Opened Not Parameters");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ClusterExE(JPanel panel, HashMap map, JFrame frame, UnixHandler handle) {
		this.logsPanel = panel;
		this.hashMap = map;
		this.frame = frame;
		this.sameputty = handle;
	}

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		String Directory = "C:/Users/Stephen Osunkunle/Desktop/Unix_Java"; // DEFAULT
//		String TaxonomyLocation = "12S_Combined.tax"; // DEFAULT args[0]
//		String FastaLocation = "12S_Combined.fa"; // DEFAULT args[1]
//		int K = 5; // DEFAULT args[2]
		ClusterExE test = new ClusterExE();

		test.Input_TestData(Integer.parseInt(args[2]), args[0], Directory, "TAX"); // tax
		test.Input_TestData(Integer.parseInt(args[2]), args[1], Directory, "FASTA"); // fasta
		test.testData(Integer.parseInt(args[2]), Directory, "TAX");
		test.testData(Integer.parseInt(args[2]), Directory, "FASTA");
		System.out.println(System.currentTimeMillis() - time + " ms");
	}

	public void InsertIntoUnix() {
		JOptionPane.showMessageDialog(null, "We Are Check Your Files For You! Sit Back and Enjoy!", "Prompt: 801",
				JOptionPane.INFORMATION_MESSAGE);
		new Thread(new Runnable() {
			@Override
			public void run() {
				hashMap.forEach(new BiConsumer<String, String>() {
					@Override
					public void accept(String key, String value) {
						sameputty.LocaltoRemote(key);
					}
				});
			}
		}).start();
		new Swing().playSound("UI-Items/Conformation.aifc");

//		for (Map.Entry<String, String> entry : hashMap.entrySet()) {
//			sameputty.LocaltoRemote(entry.getKey());
//		}
	}

	@SuppressWarnings("rawtypes")
	private List<String> TypeChecking(String Type) {
		String newFileName = "";
		String ending = "";
		if (Type == "TAX") {
			newFileName = "Test_Taxonomy";
			ending = ".tax";
		} else if (Type == "FASTA") {
			newFileName = "Test_Fasta";
			ending = ".fa";
		} else {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<String> newString = new ArrayList();
		newString.add(newFileName);
		newString.add(ending);
		return newString;
	}

	public void testData(int K, String mainDirectory, String Type) {
		String newFileName = "";
		String ending = "";

		if (Type == "TAX") {
			newFileName = "Training_Taxonomy";
			ending = ".tax";
		} else if (Type == "FASTA") {
			newFileName = "Training_Fasta";
			ending = ".fa";
		} else {
			return;
		}

		try {
			for (int i = 1; i <= K; i++) {
				String trainData_Name = i + newFileName + ending;
				File trainedTaxfile = new File(trainData_Name);
				if (trainedTaxfile.createNewFile()) {
					System.out.printf("%s Data Has Been Made! \n", trainData_Name);
					AddToHashMap(trainData_Name, getCurrentTimeString());
					enterFileInFile(trainData_Name, K, mainDirectory, i, Type);
				} else {
					System.out.printf("%s Was Already Created \n", trainData_Name);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Take a parameter and it tells us which testing data to ignore and fill if up
	 * with the other data to create out training data
	 */
	public void enterFileInFile(String createdFile, int K, String Directory, int Position, String type) {
		List<String> findType = TypeChecking(type);
		String currentFileString = findType.get(0);
		String ending = findType.get(1);
		
		for (int i = 1; i <= K; i++) {
			if (i == Position) {
				continue;
			}

			String currString = i + currentFileString + ending;
			AddToHashMap(currString, getCurrentTimeString());
			File currentFile = new File(currString);
			FileInputStream fileInputStream;
			try {
				FileWriter fw = new FileWriter(createdFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				fileInputStream = new FileInputStream(currentFile);
				byte[] byteValue = new byte[(int) currentFile.length()];
				fileInputStream.read(byteValue);
				fileInputStream.close();

				String fileContent = new String(byteValue, "UTF-8");
				bw.write((fileContent));
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Creates The File and Inputs Data Into the File Either Fasta and Or Taxonomy
	 * File
	 * 
	 * @param K
	 * @param mainFile
	 * @param Directory
	 * @param type
	 * 
	 *                  K is the number of times the data is split; mainFile is
	 *                  either is fasta file or the taxonomy type can either be
	 *                  "TAX" or "FASTA" is not return Directory is where you want
	 *                  all the files to go to
	 */
	public void Input_TestData(int K, String mainFile, String Directory, String type) {
		int counter = 0;

		String FillDesc = "";
		String ending = "";

		if (type.equals("TAX")) {
			FillDesc = "Test_Taxonomy";
			ending = ".tax";
		} else if (type.equals("FASTA")) {
			FillDesc = "Test_Fasta";
			ending = ".fa";
		} else {
			return;
		}

		try {
			List<String> lines = Files.readAllLines(Paths.get(mainFile));
			int LinesPerFile = Math.round(lines.size() / K);
			v1: for (int i = 1; i <= K; i++) {
				File currFile = new File(i + FillDesc + ending);
				FileWriter fw = new FileWriter(currFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				for (int j = counter; j < LinesPerFile * i; j++) {
					if (j >= lines.size() * i) {
						continue v1;
					}
					counter = counter + 1;
					String line = lines.get(j);
					if (!line.isEmpty()) {
						bw.write(line);
						if (line.contains(">")) {
							bw.newLine();
							bw.write(lines.get(j + 1));
							j = j + 1;
							counter = counter + 1;
						}
						bw.newLine();
					}
				}
				bw.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void AddToHashMap(String key, String val) {
		JLabel v1 = new JLabel(key + " | : " + val);
		v1.setBackground(Color.gray);
		v1.setForeground(Color.BLACK);
		v1.setFont(new Font("Century Gothic", Font.BOLD, 9));
		logsPanel.add(v1);
		hashMap.put(key, val);

		int contentHeight = 0;
		for (Component component : logsPanel.getComponents()) {
			contentHeight += component.getPreferredSize().height;
		}

		// Set the preferred height of the logsPanel
		logsPanel.setPreferredSize(new Dimension(200, 6000));
//		Math.max(contentHeight, frame.getPreferredSize().height))
		logsPanel.revalidate();
		logsPanel.repaint();
	}

	public static String getCurrentTimeString() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
		return now.format(formatter);
	}

}