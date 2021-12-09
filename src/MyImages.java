import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JPanel;

public class MyImages extends JPanel {
	
	BufferedImage image;
	public  MyImages(String chemin) {
		super();
		try {
			
			image = ImageIO.read(new File(chemin));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		this.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
		this.setLayout(new GridBagLayout());
		
	}
	
	
	public BufferedImage getImage() {
		return image;
	}


	public void setImage(BufferedImage image) {
		this.image = image;
	}


	public void paintComponent(Graphics g) {
	     super.paintComponent(g);
	       // Draw the background image.
	       g.drawImage(image, 0, 0, this);
	    }
}
