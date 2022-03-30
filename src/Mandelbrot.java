import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mandelbrot extends Fractale {

	@Override
	public int divergenceIndex(Complex z0) {
		int ite = 0;
		Complex zn = z0;
		double arg = Complex.of(this.minI, this.maxI).module();
		while (ite < iterations && zn.module() < 2) {
			zn = Complex.somme(z0, Complex.multiplication(zn, zn));
			ite++;
		}
		return ite;
	}
	
	public Complex toComplex2(double i,double j){
		double realPart = (-1 + i * (1 -(-1)) / (width -1));
        double imaginaryPart = (-1 + j *(1 -(-1)) / (height -1) );
        return Complex.of(realPart, imaginaryPart);
    }

	public static class Builder {

		private int width, height;
		private int iterations;
		private double pas, minI, maxI, minR, maxR;

		public Builder(int iterations) {
			this.iterations = iterations;
		}

		public Builder rectangle(double minR, double maxR, double minI, double maxI, double pas) {
			this.minR = minR;
			this.maxR = maxR;
			this.minI = minI;
			this.maxI = maxI;
			this.pas = pas;
			
			  this.width = (int) ( (maxR -minR)/pas +1 ) ; this.height = (int) ( (maxI
			  -minI)/pas +1 );
			 
			return this;
		}

		public Builder matrices(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		public Mandelbrot build() {
			return new Mandelbrot(this);
		}

	}

	private Mandelbrot(Builder builder) {
		iterations = builder.iterations;
		minR = builder.minR;
		maxR = builder.maxR;
		minI = builder.minI;
		maxI = builder.maxI;
		width = builder.width;
		height = builder.height;
	}
	
	public Mandelbrot(int iterations,double minR , double maxR, double minI, double maxI, double pas) {
		this.iterations = iterations;
		this.minI = minI;
		this.maxI = maxI;
		this.pas = pas;
		this.minR = minR;
		this.maxR = maxR;
		this.width = (int) ( (maxR -minR)/pas +1 ) ;
		this.height = (int) ( (maxI -minI)/pas +1 );
	}
	
	public void generateImage() {
		//var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// remplissage de l'image
		int[][] matrice = this.divIndexes();
		var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				if (matrice[i][j] >= 1000) {
					img.setRGB(i, j, (0 << 16) + (0 << 8) + 0);
				} else {
					int rgb = Color.HSBtoRGB((float) matrice[i][j] / iterations, 0.7f, 0.7f);
					img.setRGB(i, j, rgb);
				}
			}
		}

		File f = new File("../Fractales generees/"+ "Mandelbrot " +  + width + "*" + height + " " + iterations + ".png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("votre fractale a ete bien generee.\nVous la trouverez dans "
				+ "le repertoire Fractales generees sous le nom " + "Mandelbrot" + " " + width + "*" + height + " " + iterations
				+ ".png");
	}
	
	public void generatFractale() {
		
		var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Complex z = toComplex2(i, j);
				int it = divergenceIndex(z);
				if(it == iterations) {
					img.setRGB(i,j,(0 << 16) + (0 << 8) + 0);
				}else {
					int rgb=Color.HSBtoRGB((float)it/iterations, 0.7f, 0.7f);
					img.setRGB(i,j, rgb);
					
				}
			}
		}

		File f = new File("../Fractales generees/" + "Mandelbrot "  + width + "*" + height + " " + iterations + ".png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("votre fractale a ete bien generee.\nVous la trouverez dans "
				+ "le repertoire Fractales generees sous le nom " + "Mandelbrot" + " " + width + "*" + height + " " + iterations
				+ ".png");
	}

}
