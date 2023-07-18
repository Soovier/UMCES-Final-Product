package org.umces.umces;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CustomBackgroundPanel extends JPanel {

	private ImageIcon bgImage;

	public CustomBackgroundPanel() {
		// Initialization code
	}

	public void setBgImage(ImageIcon img) {
		this.bgImage = null; // clear previous
		this.bgImage = img;
	}


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), null);
//		g.drawImage(bgImage.getImage(), 0, 0, null);
	}


}