package org.umces.umces;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class Swing extends JPanel {
	/**
	 * Starts the UI and Has JPanel for the main panel
	 */
	private static final long serialVersionUID = 1L;
	protected String TaxonomyFile = "";
	protected String FastaFile = "";
	private static int K_Amount;
	public boolean Test_Train_Made;
	public static HashMap<String, String> hashMap;
	public JTextField searchField;
	public String verifiedClass;

	JPanel logsPanel;
	JFrame frame;
	CustomBackgroundPanel centerPanel;
	JPanel gridPanel;
	UnixHandler putty;

	public Swing(UnixHandler handle) {
		this.putty = handle;
	}

	public Swing() {

	}

	// Setting up the layout for the whole UI and setting the default value type
	{
		this.verifiedClass = "";
		this.searchField = new JTextField(20);
		this.logsPanel = new JPanel(new FlowLayout());
		hashMap = new HashMap<>();
		Test_Train_Made = false;
//		K_Amount = 3;
		frame = new JFrame("UMCES K-Foldanator By: Stephen Osunkunle");
//		centerPanel = new JPanel() {
//			@Override
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				ImageIcon background = new ImageIcon("UI-Items/newbackground.jpg");
//				Image img = background.getImage();
//				g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
//			}
//
//		};
		gridPanel = new JPanel(new GridLayout(10, 2, 0, 5));
		gridPanel.setBackground(new Color(19, 108, 150));
		gridPanel.setPreferredSize(new Dimension(250, 500));
//		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(gridPanel, BorderLayout.EAST);
	}

//	public static void main(String[] args) {
//		Swing test = new Swing();
//		test.Settings();
//	}

	// THE MAIN OF THIS CLASS
	public void Settings() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ImageIcon backgrImage = new ImageIcon("UI-Items/newbackground.jpg");
		centerPanel = new CustomBackgroundPanel();
		centerPanel.setBgImage(backgrImage);
		frame.add(centerPanel, BorderLayout.CENTER);
		
		ImageIcon icon = new ImageIcon("UI-Items/UMCESLogo.jpg");
		frame.setPreferredSize(new Dimension(950, 520));
		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(icon.getImage());

		addDirectoryExplorer();
		gridPanelMethod();
		
		this.logsPanel.setPreferredSize(new Dimension(200, frame.getPreferredSize().height));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		searchField.setText("Search In Logs");
		mainPanel.add(searchField, BorderLayout.NORTH);

		// Add the JLabel
		JLabel titleLabel = new JLabel("Logs");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		titleLabel.setForeground(Color.BLACK);
		logsPanel.add(titleLabel);

		// Wrap the logsPanel in a JScrollPane
		JScrollPane scrollPane = new JScrollPane(logsPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// Add a vertical glue to push the logsPanel to the bottom
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(scrollPane);

		searchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search();
			}

			private void search() {
				String query = searchField.getText().toLowerCase();
				for (Component component : logsPanel.getComponents()) {
					if (component instanceof JLabel) {
						JLabel label = (JLabel) component;
						String labelText = label.getText().toLowerCase();
						label.setVisible(labelText.contains(query));
					}
				}
				logsPanel.revalidate();
				logsPanel.repaint();
			}
		});
		
		frame.add(mainPanel, BorderLayout.WEST);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
	}

	private void gridPanelMethod() {
		// Creating buttons and adding them to the grid panel
		JButton b1 = new JButton("Get Alignment Data");
		gridPanel.add(b1);
		b1.setBackground(Color.black);
		b1.setForeground(Color.white);
		JButton b2 = new JButton("Get Annotation Data");
		gridPanel.add(b2);
		b2.setBackground(Color.black);
		b2.setForeground(Color.white);
		JButton b3 = new JButton("Get Trimmed Data");
		gridPanel.add(b3);
		b3.setBackground(Color.black);
		b3.setForeground(Color.white);
		JButton b4 = new JButton("Add/Change Taxonomy Database");
		gridPanel.add(b4);
		b4.setBackground(Color.black);
		b4.setForeground(Color.white);
		JButton b5 = new JButton("Add/Change Fasta Database");
		gridPanel.add(b5);
		b5.setBackground(Color.black);
		b5.setForeground(Color.white);
		JButton b6 = new JButton("Set/Change K Fold");
		gridPanel.add(b6);
		b6.setBackground(Color.black);
		b6.setForeground(Color.white);
		JButton b10 = new JButton("Set/Change Classification");
		gridPanel.add(b10);
		b10.setBackground(Color.black);
		b10.setForeground(Color.white);
		JButton b7 = new JButton("Create Test and Train Data");
		gridPanel.add(b7);
		b7.setBackground(Color.black);
		b7.setForeground(Color.green);
		JButton b8 = new JButton("Remove All Data From Directory");
		gridPanel.add(b8);
		b8.setBackground(Color.black);
		b8.setForeground(Color.red);
		JButton b9 = new JButton("Get FDR");
		gridPanel.add(b9);
		b9.setBackground(Color.black);
		b9.setForeground(Color.white);

		// Setting font styles for the buttons
		b1.setFont(new Font("Times New Roman\r\n", Font.BOLD, 18));
		b2.setFont(new Font("Times New Roman\r\n", Font.BOLD, 18));
		b3.setFont(new Font("Times New Roman\r\n", Font.BOLD, 18));
		b4.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 15));
		b5.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 15));
		b6.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 15));
		b7.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 15));
		b8.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 15));
		b9.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 15));
		b10.setFont(new Font("Baskerville Old Face" + "", Font.BOLD, 15));

		// Setting focusability for the buttons
		b1.setFocusable(false);
		b2.setFocusable(false);
		b3.setFocusable(false);
		b4.setFocusable(false);
		b5.setFocusable(false);
		b6.setFocusable(false);
		b7.setFocusable(false);
		b8.setFocusable(false);
		b9.setFocusable(false);
		b10.setFocusable(false);

		// Adding action listeners to the buttons
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean ifRan = HasRequirments("Aligment Data Request Has Been Sent!");
				fileLogs log = new fileLogs(ifRan, putty, logsPanel);
				log.getType(1, TaxonomyFile, FastaFile, getK_Amount(), verifiedClass);
			}
		});

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean ifRan = HasRequirments("Annotation Data Request Has Been Sent!");
				fileLogs log = new fileLogs(ifRan, putty, logsPanel);
				log.getType(2, TaxonomyFile, FastaFile, getK_Amount(), verifiedClass);
			}
		});

		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean ifRan = HasRequirments("Trimmed Annotation Data Request Has Been Sent!");
				fileLogs log = new fileLogs(ifRan, putty, logsPanel);
				log.getType(3, TaxonomyFile, FastaFile, getK_Amount(), verifiedClass);
			}
		});

		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Prompting the user to enter a file name for the taxonomy database
				String taxfileName = JOptionPane.showInputDialog("Enter File Name:", TaxonomyFile);
				if (!taxfileName.equals("null")) {
					setTaxonomyFile(taxfileName);
				}
			}
		});

		b5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Prompting the user to enter a file name for the fasta database
				String fasta = JOptionPane.showInputDialog("Enter File Name:", FastaFile);
				if (!fasta.equals("null")) {
					setFastaFile(fasta);
				}
			}
		});

		b6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Prompting the user to enter a value for K amount
				String amount = JOptionPane.showInputDialog("Enter K Amount:", K_Amount);
				try {
					K_Amount = Integer.valueOf(amount);
					String soundFilePath = "UI-Items/Conformation.aifc";
					playSound(soundFilePath);
				} catch (Exception e2) {
					if (amount.equals("null")) {
						e2.printStackTrace();
					} else {
						String soundFilePath = "UI-Items/ErrorSound.aifc";
						playSound(soundFilePath);
						JOptionPane.showMessageDialog(null, "Invalid Entry", "Error: 201", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		b7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Test_Train_Made = true;
				Boolean ifRan = HasRequirments("Test and Train Data Created!");
				if (ifRan) {
					String command = "java " + putty.getPath("UMCES-Final-Product/MainJavaClasses/ClusterExE.java")
							+ " " + TaxonomyFile
							+ " " + FastaFile + " " + K_Amount + " " + putty.getPath();
					putty.executeCommand(command);
					String[] labels = { "Training_Fasta", "Training_Taxonomy", "Test_Fasta", "Test_Taxonomy" };
					for (int i = 1; i <= K_Amount; i++) {
						for (int k = 0; k < labels.length; k++) {
							AddToHashMap(i + labels[k], getCurrentTimeString());
						}
					}
				} else {
					Test_Train_Made = false;
				}
			}
		});

		b8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int cn = 0;
				String newCommand = "find " + putty.getPath()
						+ " -type f \\( -iname \"*.tax\" -o -iname \"*.fa\" -o -iname \"*.txt\" \\) ! -name \"12S_Combined.tax\" ! -name \"12S_Combined.fa\" -exec rm -f {} \\;";
				putty.executeCommand(newCommand);
				putty.RemoveFromDiretory("FDRfile");
//				putty.executeCommand("find " + putty.getPath() + " -type f \\(rm FDRfile)");

				// Just clearing the hashmap and the JPanel
				hashMap.clear();
				Component[] components = logsPanel.getComponents();
				for (Component component : components) {
					if (component instanceof JLabel) {
						JLabel label = (JLabel) component;
						String labelText = label.getText();

						if (labelText.contains("Logs")) {
							continue;
						} else {
							logsPanel.remove(label);
							cn += 1;
						}


//						String[] dataname = { "Training_Fasta", "Training_Taxonomy", "Test_Fasta", "Test_Taxonomy" };
//						for (int i = 0; i < dataname.length; i++) {
//							cn += 1;
//							if (labelText.contains(dataname[i])) {
//								logsPanel.remove(label);
//							}
//						}
					}
				}

				logsPanel.revalidate();
				logsPanel.repaint();

				String message;
				String statusmessage;
				String soundFilePath;
				int OPTION;
				if (cn > 0) {
					message = "Test and Train Files Have Been Removed!";
					statusmessage = "Sucess 102";
					OPTION = JOptionPane.INFORMATION_MESSAGE;
					soundFilePath = "UI-Items/Conformation.aifc";
				} else {
					message = "No Files To Remove";
					statusmessage = "Error 501";
					OPTION = JOptionPane.ERROR_MESSAGE;
					soundFilePath = "UI-Items/ErrorSound.aifc";
				}
				Test_Train_Made = false;

				playSound(soundFilePath);
				JOptionPane.showMessageDialog(null, message, statusmessage, OPTION);
			}
		});
		
		b9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean ifRan = HasRequirments("FDR Data Request Has Been Sent!");
				fileLogs log = new fileLogs(ifRan, putty, logsPanel);
				log.getType(4, TaxonomyFile, FastaFile, getK_Amount(), verifiedClass);
				
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						File imgFile = new File("chart_output.png");
						if (!imgFile.exists()) {
							System.out.println("Image file not found");
						}
						// Read image file into bytes
						try {
							byte[] imgBytes = Files.readAllBytes(imgFile.toPath());
							ImageIcon Icon = new ImageIcon(imgBytes);
							centerPanel.setBgImage(Icon);
							centerPanel.repaint();
							centerPanel.revalidate(); // refresh

						} catch (IOException e) {
							e.printStackTrace();
						}

					}

				});

			}
		});

		b10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Kingdom", "Phylum", "Class", "Order", "Family", "Genus", "Specices"

				};
				int rc = JOptionPane.showOptionDialog(null, "Please Select What Classification You Want To Work With!",
						"Confirmation", JOptionPane.PLAIN_MESSAGE,
						0, null, buttons, buttons[2]);
				String soundFilePath = "UI-Items/Conformation.aifc";
				switch (rc) {
				case (0):
					verifiedClass = "k";
					break;
				case (1):
					verifiedClass = "p";
					break;
				case (2):
					verifiedClass = "c";
					break;
				case (3):
					verifiedClass = "o";
					break;
				case (4):
					verifiedClass = "f";
					break;
				case (5):
					verifiedClass = "g";
					break;
				case (6):
					verifiedClass = "s";
					break;
				default:
					soundFilePath = "UI-Items/ErrorSound.aifc";
					break;
				}
				playSound(soundFilePath);

			}
		});

	}


	/**
	 * setting the default error messages if we don't have certain files / Type 1
	 * checks for errors
	 */

	public boolean HasRequirments(String sucessMessage) {
		String soundFilePath = "UI-Items/ErrorSound.aifc";
		if (TaxonomyFile.equals("") && FastaFile.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Import Taxonomy Database and Fasta Database.";
			JOptionPane.showMessageDialog(null, Error, "Error: 101", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (TaxonomyFile.equals("") && !FastaFile.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Import Taxonomy Database.";
			JOptionPane.showMessageDialog(null, Error, "Error: 102", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!TaxonomyFile.equals("") && FastaFile.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Import Fasta Database.";
			JOptionPane.showMessageDialog(null, Error, "Error: 103", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!Test_Train_Made) {
			playSound(soundFilePath);
			String Error = "No Train or Testing Data.";
			JOptionPane.showMessageDialog(null, Error, "Error: 109", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (K_Amount <= 0) {
			playSound(soundFilePath);
			String Error = "Please Set Your K-Amount.";
			JOptionPane.showMessageDialog(null, Error, "Error: 111", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (verifiedClass.equals("")) {
			playSound(soundFilePath);
			String Error = "Please Set Classification.";
			JOptionPane.showMessageDialog(null, Error, "Error: 116", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		String corret_music = "UI-Items/Conformation.aifc";
		playSound(corret_music);
		JOptionPane.showMessageDialog(null, sucessMessage, "Succession: 101", JOptionPane.INFORMATION_MESSAGE);
		return true;
	}

	// PROMPT BROWSING FOR FILES //
	private void addDirectoryExplorer() {
		// Creating a text field to display the file path
		final JTextField textField = new JTextField("LOCAL DIRECTORY PATH!!");
		textField.setFont(new Font("Times New Roman\r\n" + "" + "", Font.ITALIC, 12));
		textField.setEditable(false);
		frame.add(textField, BorderLayout.NORTH);

		// Creating a button for browsing directories
		JButton button = new JButton("Browse Directory");
		button.setFont(new Font("Times New Romana" + "" + "", Font.LAYOUT_RIGHT_TO_LEFT, 12));

		frame.add(button, BorderLayout.SOUTH);
		// Adding an action listener to the button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Opening a file chooser dialog
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int result = fileChooser.showOpenDialog(frame);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					textField.setText(selectedFile.getAbsolutePath());
				}
			}
		});
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
//		Math.max(contentHeight, frame.getPreferredSize().height))
		logsPanel.revalidate();
		logsPanel.repaint();
	}

	public static String getCurrentTimeString() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
		return now.format(formatter);
	}

	// Method for playing sound
	public void playSound(String soundFilePath) {
		try {
			File soundFile = new File(soundFilePath);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	public String getTaxonomyFile() {
		return TaxonomyFile;
	}

	public void setTaxonomyFile(String taxonomyFile) {
		TaxonomyFile = taxonomyFile;
	}

	public String getFastaFile() {
		return FastaFile;
	}

	public void setFastaFile(String fastaFile) {
		FastaFile = fastaFile;
	}

	public int getK_Amount() {
		return K_Amount;
	}

	public void setK_Amount(int k_Amount) {
		K_Amount = k_Amount;
	}

}
