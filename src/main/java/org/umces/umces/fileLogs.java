package org.umces.umces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class fileLogs extends Swing {
	private boolean canRun;
	UnixHandler putty;
	private JPanel logsPanel;

	/**
	 * @param passed
	 * @param Type   
	 */
	public fileLogs(Boolean passed, UnixHandler handler, JPanel panel) {
		canRun = passed;
		this.putty = handler;
		this.logsPanel = panel;
	}

	public fileLogs(JFrame frame) {
        this.frame = frame;
		this.canRun = false;
	}


	public void getType(int Type, String Tax, String Fa) {
		if (!this.canRun) {
			return;
		}
		// Case O(n) Worst Case O(n)
		switch (Type) {
		case (1):

			// Run the alignment K times
			for (int i = 1; i <= getK_Amount(); i++) {// Takes Test and Training Fasta Data
				putty.executeCommand(
						"vsearch --usearch_global " + putty.getPath(i + "Test_Fasta.fa") + " --db "
								+ (putty.getPath(i + "Training_Fasta.fa"))
								+ " --id 0.70  --maxaccepts 100 --maxrejects 50 --maxhits 1 --gapopen 0TE --gapext 0TE --userout "
								+ (putty.getPath(i + "TestAlignments.txt"))
								+ " --userfields query+target+id+alnlen+mism+opens+qlo+qhi+tlo+thi+evalue+bits+qcov --query_cov 0.8 --threads 28"
								+ "");
				AddToHashMap(String.valueOf(i) + "TestAlignments.txt", getCurrentTimeString());
			}
			super.playSound("UI-Items/Conformation.aifc");
			String error3 = "Alignment Data Has Been Added!";
			JOptionPane.showMessageDialog(null, error3, "Sucess: 701", JOptionPane.INFORMATION_MESSAGE);
			break;

		case (2):

			for (int i = 1; i <= getK_Amount(); i++) {
				String command = "java " + putty.getPath("UMCES-Final-Product/MainJavaClasses/VsearchToMetaxa_3.java")
						+ " "
						+ (putty.getPath() + i + "TestAlignments.txt") + " "
						+ Tax + " " + (putty.getPath() + i + "Annotation_Data.tax");
				putty.executeCommand(command);
				AddToHashMap(i + "Annotation_Data.tax", getCurrentTimeString());
			}
			super.playSound("UI-Items/Conformation.aifc");
			String error2 = "Annotation Data Has Been Added!";
			JOptionPane.showMessageDialog(null, error2, "Sucess: 702", JOptionPane.INFORMATION_MESSAGE);
			break;

		case (3):

			for (int i = 1; i <= getK_Amount(); i++) {
				String command = "java "
						+ putty.getPath("UMCES-Final-Product/MainJavaClasses/TrimMtxa2IDs_4.java") + " "
						+ (putty.getPath() + i + "Annotation_Data.tax") + " "
						+ (putty.getPath() + i + "Trimmed_Annotation.txt");
				putty.executeCommand(command);
				AddToHashMap(String.valueOf(i) + "Trimmed_Annotation.txt", getCurrentTimeString());
			}
			super.playSound("UI-Items/Conformation.aifc");
			String error1 = "Trim Added Has Been Made";
			JOptionPane.showMessageDialog(null, error1, "Sucess: 703", JOptionPane.INFORMATION_MESSAGE);
			break;

		default:

			super.playSound("UI-Items/ErrorSound.aifc");
			String error = "Case Erorr, Please Contant Developer";
			JOptionPane.showMessageDialog(null, error, "Error: 707", JOptionPane.ERROR_MESSAGE);
			break;
			}
	}

		public static String getCurrentTimeString() {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
			return now.format(formatter);
		}

		public void updateLogsPanel(HashMap<String, String> hashMap) {
			logsPanel.removeAll();

			// Add the title label
			JLabel titleLabel = new JLabel("Logs");
			titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
			titleLabel.setForeground(Color.BLACK);
			logsPanel.add(titleLabel);

			// Add the contents of the HashMap
			for (Map.Entry<String, String> entry : hashMap.entrySet()) {
				JLabel label = new JLabel(entry.getKey() + " | : " + entry.getValue());
				label.setBackground(Color.gray);
				label.setForeground(Color.BLACK);
				label.setFont(new Font("Century Gothic", Font.BOLD, 9));
				logsPanel.add(label);
			}

			logsPanel.revalidate();
			logsPanel.repaint();
		}

		public void AddToHashMap(String key, String val) {
			JLabel v1 = new JLabel(key + " | : " + val);
			v1.setBackground(Color.gray);
			v1.setForeground(Color.BLACK);
			v1.setFont(new Font("Century Gothic", Font.BOLD, 9));
			logsPanel.add(v1);
			hashMap.put(key, val);

			// Set the preferred height of the logsPanel
			logsPanel.setPreferredSize(new Dimension(200, 6000));

			logsPanel.revalidate();
			logsPanel.repaint();
		}

	}
