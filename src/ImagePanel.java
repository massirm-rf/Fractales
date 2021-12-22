import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5564818820574092960L;
	
	private BufferedImage image;
	
	public ImagePanel() {
		super();
	}
	
	public ImagePanel(BufferedImage image) {
        super();
        setImage(image);
    }
	
	public ImagePanel(String path) throws IOException {
        super();
        setImage(path);
    }
    
	
	public void setImage(BufferedImage image) {
        this.image = image;
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
       // repaint();
    }
	
	public void setImage(String path) throws IOException {
        try {
            this.image = ImageIO.read(new File(path));
            repaint();
        } 
        catch (IOException e) {
            throw new IOException(path+" introuvable", e);
        }
    }
	
	 public BufferedImage getImage() {
	        return image;
	 }
	 
	 @Override
		public void paintComponent(Graphics g) {

			if (image != null) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}

			/*
			 * super.paintComponent(g); g.drawImage(image, 0, 0, image.getWidth(),
			 * image.getHeight(), null);
			 */
		}
	 
	
}
