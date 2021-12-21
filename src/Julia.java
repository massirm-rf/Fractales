import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Julia {
	
	private Complex c ;
	private int width,height;
	private int iterations;
	private double pas,minI,maxI,minR,maxR;
	private Parser parser;
	
	
	public Julia (Complex c) {
		this.c = c;
	}
	
	public Julia(Complex c,int width,int height,int iterations) {
		this.c = c;
		this.width = width;
		this.height = height;
		this.iterations = iterations;
	}
	
	public Julia(Complex c,int width,int height,int iterations,double minI, double maxI, double pas) {
		this.c = c;
		this.width = width;
		this.height = height;
		this.iterations = iterations;
		this.minI = minI;
		this.maxI = maxI;
		this.pas = pas;
	}
	
	public Julia(int width,int height,int iterations,Parser parser) {
		this.width = width;
		this.height = height;
		this.iterations = iterations;
		this.parser = parser;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public double getPas() {
		return pas;
	}

	public void setPas(double pas) {
		this.pas = pas;
	}

	public double getMinR() {
		return minR;
	}

	public void setMinR(double minR) {
		this.minR = minR;
	}

	public double getMaxR() {
		return maxR;
	}

	public void setMaxR(double maxR) {
		this.maxR = maxR;
	}

	public double getMinI() {
		return minI;
	}

	public void setMinI(double minI) {
		this.minI = minI;
	}

	public double getMaxI() {
		return maxI;
	}

	public void setMaxI(double maxI) {
		this.maxI = maxI;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public int diverfgenceIndex(Complex z0) {
		int ite =0;
		Complex zn = z0;
		double arg = new Complex(this.minI,this.maxI).module();
		while(ite <iterations && zn.module() < arg ) {
			zn =Complex.somme( c,Complex.multiplication(zn, zn) );
			ite ++;
		}
		return ite;
	}
	
	public int diverfgenceIndex2(Complex z0) {
		int ite =0;
		Complex zn = z0;
		while(ite <iterations && zn.module() < 2 ) {
			zn  = Complex.somme( c,Complex.multiplication(zn, zn) );
			//System.out.println(zn);
			ite ++;
		}
		return ite;
	}
	
	/*public int divergenceIndex2(Complex z0) {
		int ite =0;
		Complex zn = z0;
		while(ite <iterations && zn.module() < 2 ) {
			zn =Complex.somme( c,Complex.multiplication(zn, zn) );
			ite ++;
		}
		return ite;
	}*/
	
	public Complex toComplex2(double i,double j){
        double realPart = -1 + i * (1 -(-1)) / (width - 1);
        double imaginaryPart = -1 + j * (1 - (-1)) / (height - 1);
        return new Complex(realPart, imaginaryPart);
    }
	//toComplex2
	public Complex toComplex(double i,double j){
		double realPart = (minI + i * (maxI -(minI)) / (width -1));
        double imaginaryPart = (minI + j *(maxI -minI) / (height -1) );
        return new Complex(realPart, imaginaryPart);
    }
	
	public int [][] divIndexes(){
		int longueur = (int) ( (maxI -minI)/pas +1 );
		int largeur = (int)  ((maxI -minI)/pas +1 );
		int res[][] = new int [longueur][largeur];
		double a=minI,b=minI;
		for(int i= 0;i<longueur;i++) {
			b=minI;
			for(int z =0;z<largeur;z++) {
				res[i][z]=diverfgenceIndex(new Complex(a, b));
				b+=pas;
			}
			a+=pas;
			//if(i==longueur-1)System.out.println("a =="+a + "b=="+b);
		}
		return res;
	}
	
	public void generateImage() {
		var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// remplissage de l'image
		int[][] matrice = this.divIndexes();
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

		File f = new File("../Fractales generees/" + c + " " + width + "*" + height + " " + iterations + ".png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("votre fractale a ete bien generee.\nVous la trouverez dans "
				+ "le repertoire Fractales generees sous le nom " + c + " " + width + "*" + height + " " + iterations
				+ ".png");
	}
	
	public void generatFractale() {
		var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Complex z = toComplex2(i, j);
				int it = diverfgenceIndex2(z);
				if(it == iterations) {
					img.setRGB(i,j,(0 << 16) + (0 << 8) + 0);
				}else {
					int rgb=Color.HSBtoRGB((float)it/iterations, 0.7f, 0.7f);
					img.setRGB(i,j, rgb);
					
				}
			}
		}

		File f = new File("../Fractales generees/" + c + " " + width + "*" + height + " " + iterations + ".png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("votre fractale a ete bien generee.\nVous la trouverez dans "
				+ "le repertoire Fractales generees sous le nom " + c + " " + width + "*" + height + " " + iterations
				+ ".png");
	}
	
	public Complex toComplex(Point p){
        double real = this.minR + p.x * (this.maxR - this.minR) / (width - 1);
        double imaginary = this.maxI + p.y * (this.minI - this.maxI) / (height - 1);

        return new Complex(real, imaginary);
    }
	

}
