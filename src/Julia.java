import java.awt.image.BufferedImage;
import java.io.File;

public class Julia {
	
	private Complex c ;
	private int width,height;
	private int iterations;
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
	
	public Julia(int width,int height,int iterations,Parser parser) {
		this.width = width;
		this.height = height;
		this.iterations = iterations;
		this.parser = parser;
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

	public int diverfgenceIndex(Complex z0) {
		int ite =0;
		Complex zn = z0;
		while(ite <255 && zn.module() < 2 ) {
			zn =Complex.somme( c,Complex.multiplication(zn, zn) );
			ite ++;
		}
		return ite;
	}
	
	public int diverfgenceIndex2(Complex z0) {
		int ite =0;
		Complex zn = z0;
		while(ite <255 && zn.module() < 2 ) {
			zn =parser.calculer(zn);
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
	
	public Complex toComplex(double i,double j){
        double realPart = -1 + i * (1 -(-1)) / (width - 1);
        double imaginaryPart = -1 + j * (1 - (-1)) / (height - 1);
        return new Complex(realPart, imaginaryPart);
    }
	

}
