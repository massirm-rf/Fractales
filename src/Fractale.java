import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;

//classe abstraite dans laquelle Julia et mandelbrot heritent 
public abstract class Fractale implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	protected int width;
	protected int height;
	protected int iterations;
	protected double pas,minI,maxI,minR,maxR;
	
	//transformer les (i,j) en un nombre complexe selon la longeur et la largeur de l'image (le nombre va etre compris entre min i et maxI....)
	public Complex toComplex(double i,double j){
		double realPart = (minI + i * (maxI -(minI)) / (width -1));
        double imaginaryPart = (minI + j *(maxI -minI) / (height -1) );
        return Complex.of(realPart, imaginaryPart);
    }
	
	public Complex toComplex(Point p){
        double real = this.minR + p.x * (this.maxR - this.minR) / (width - 1);
        double imaginary = this.maxI + p.y * (this.minI - this.maxI) / (height - 1);

        return Complex.of(real, imaginary);
    }
	
	public abstract int divergenceIndex(Complex z0);
	
	public int [][] divIndexes(){
		int longueur = (int) ( (maxI -minI)/pas +1 );
		int largeur = (int)  ((maxI -minI)/pas +1 );
		int res[][] = new int [longueur][largeur];
		double a=minI,b=minI;
		for(int i= 0;i<longueur;i++) {
			b=minI;
			for(int z =0;z<largeur;z++) {
				res[i][z]=divergenceIndex(Complex.of(a, b));
				b+=pas;
			}
			a+=pas;
		}
		return res;
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
	
	
}
