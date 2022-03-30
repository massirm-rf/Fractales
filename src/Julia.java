import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Julia extends Fractale{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Complex c ;
	
	public Julia (Complex c) {
		this.c = c;
	}
	
	public Julia(Complex c,int width,int height,int iterations) {
		this.c = c;
		this.width = width;
		this.height = height;
		this.iterations = iterations;
	}
	
	public Julia(Complex c,int iterations,double minR , double maxR, double minI, double maxI, double pas) {
		this.c = c;
		this.iterations = iterations;
		this.minI = minI;
		this.maxI = maxI;
		this.pas = pas;
		this.minR = minR;
		this.maxR = maxR;
		this.width = (int) ( (maxR -minR)/pas +1 ) ;
		this.height = (int) ( (maxI -minI)/pas +1 );

	}
	
	
	public Complex getC() {
		return c;
	}

	public void setC(Complex c) {
		this.c = c;
	}

	
	@Override
	public int divergenceIndex(Complex z0) {
		int ite =0;
		Complex zn = z0;
		double arg = Complex.of(this.minI,this.maxI).module();
		while(ite <iterations && zn.module() < 2 ) {
			zn  = Complex.somme( c,Complex.multiplication(zn, zn) );
			ite ++;
		}
		return ite;
	}
	
	
	public Complex toComplex2(double i,double j){
        double realPart = -1 + i * (1 -(-1)) / (width - 1);
        double imaginaryPart = -1 + j * (1 - (-1)) / (height - 1);
        return Complex.of(realPart, imaginaryPart);
    }
	//toComplex2
	/*public Complex toComplex(double i,double j){
		double realPart = (minI + i * (maxI -(minI)) / (width -1));
        double imaginaryPart = (minI + j *(maxI -minI) / (height -1) );
        return Complex.of(realPart, imaginaryPart);
    }*/
	
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

		File f = new File("../Fractales generees/" +"Julia"+ c + " " + width + "*" + height + " " + iterations + ".png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("votre fractale a ete bien generee.\nVous la trouverez dans "
				+ "le repertoire Fractales generees sous le nom " + "Julia"+c + " " + width + "*" + height + " " + iterations
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

		File f = new File("../Fractales generees/" +"Julia "+ c + " " + width + "*" + height + " " + iterations + ".png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("votre fractale a ete bien generee.\nVous la trouverez dans "
				+ "le repertoire Fractales generees sous le nom " +"Julia"+ c + " " + width + "*" + height + " " + iterations
				+ ".png");
	}
	
	public Complex toComplex(Point p){
        double real = this.minR + p.x * (this.maxR - this.minR) / (width - 1);
        double imaginary = this.maxI + p.y * (this.minI - this.maxI) / (height - 1);

        return Complex.of(real, imaginary);
    }
	
	public static class Builder{
		
		private Complex c ;
		private int width,height;
		private int iterations;
		private double pas,minI,maxI,minR,maxR;
		
		public Builder(Complex c,int itterations) {
			this.c = c;
			this.iterations = itterations;
		}
		
		public Builder rectangle(double minR,double maxR,double minI,double maxI,double pas) {
			this.minR = minR;
			this.maxR = maxR;
			this.minI = minI;
			this.maxI = maxI;
			this.pas = pas;
			/*this.width = (int) ( (maxR -minR)/pas +1 ) ;
			this.height = (int) ( (maxI -minI)/pas +1 );*/
			return this;
		}
		
		public Builder matrices(int width,int height) {
			this.width = width;
			this.height = height;
			return this;
		}
		
		public Julia build() {
			return new Julia(this);
		}
		
	}
	
	private Julia (Builder builder) {
		c = builder.c;
		iterations = builder.iterations;
		minR = builder.minR;
		maxR = builder.maxR;
		minI = builder.minI;
		maxI = builder.maxI;
		width = builder.width;
		height = builder.height;
	}



}
