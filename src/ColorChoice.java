import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColorChoice {
	
	int colorMode;
	Julia julia;
	
	public ColorChoice(int colorMode ) {
		this.colorMode = colorMode;
	}
	
	/*public void generateFracte(BufferedImage image) {
		for (int i = 0; i < j.getWidth(); i++) {
			for (int j = 0; j < julia.getHeight(); j++) {
				Complex z = julia.toComplex2(i, j);// à modifier
				int it = julia.diverfgenceIndex2(z);// à verifier
				if (it == julia.getIterations()) {
					//img.setRGB(i, j, tab[it%956][100]);
					img.setRGB(i, j, (0 << 16) + (0 << 8) + 0);
					//img.setRGB(i, j, colors[400][(divergeRgb+200)%512]);//1000 avant
				} else {
					int rgb=Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
					int rgb2 = (it << 16) + (it << 8) + it;
					//int rgb4 = colors[i][j];
					img.setRGB(i, j, colors[200][(it+convergeRgb+131)%512]);
				}
			}
	}*/

}
