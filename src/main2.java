import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

public class main2 {

	public static void main(String[] args) {
		//ChoiseFrame choiseFrame = new ChoiseFrame();
		//Controlor controleur = new Controlor(choiseFrame);
		Accueil acceuil = new Accueil();
		acceuil.setVisible(true);
		/*BufferedImage image;
		try {
			image = ImageIO.read(new File("../images/multicolor.png"));
			int width = image.getWidth();
			int height = image.getHeight();
			int[][] result = new int[height][width];

			for (int row = 0; row < height; row++) {
				for (int col = 0; col < width; col++) {
					int c = image.getRGB(col, row);
					int b = c & 0xff;
					int g = (c & 0xff00) >> 8;
					int r = (c & 0xff0000) >> 16;
					int co = (r << 16) | (g << 8) | b;
					result[row][col] = co;
					FileOutputStream fos = new FileOutputStream("../saves/colors");
					
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(result);
					oos.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		
	
		//choiseFrame.setVisible(true);
	}
}
