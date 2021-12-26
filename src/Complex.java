import java.io.Serializable;

public class Complex implements Serializable{
	
	private final double real,imaginary;
	public final static Complex ONE = new Complex(1,0);
	public final static Complex ZERO = new Complex(0,0);
	public final static Complex I = new Complex(0,1);
	
	private Complex(double x,double y) {
		real = x;
		imaginary = y;
	}
	
	//fabrique statique 
	public static Complex of(double x,double y) {
		return new Complex(x, y);
	}

	public double getImaginary() {
		return imaginary;
	}
	
	public String toString(){
		String stringOfReal = ""+real;
		String stringOfImaginary = (imaginary >= 0 ? "+"+imaginary : ""+imaginary)+"i"; 
		return stringOfReal+stringOfImaginary;
	}
	
	public static Complex somme(Complex z1, Complex z2) {
		return new Complex(z1.real+z2.real,z1.imaginary+z2.imaginary);
	}
	
	public static Complex soustraction(Complex z1, Complex z2) {
		return new Complex(z1.real-z2.real,z1.imaginary-z2.imaginary);
	}
	
	public static Complex multiplication(Complex z1, Complex z2) {
		return new Complex((z1.real * z2.real)-(z1.imaginary * z2.imaginary), 
				(z1.real*z2.imaginary + z1.imaginary * z2.real ));
	}
	
	public static Complex division (Complex z1, Complex z2) {
		
		double r1 = (z1.real * z2.real + z1.imaginary * z2.imaginary )/ 
				(z2.real * z2.real + z2.imaginary * z2.imaginary );// - à confirmer !
		double i2 = (z2.real * z1.imaginary - z1.real * z2.imaginary)/
				(z2.real * z2.real + z2.imaginary * z2.imaginary );
		return new Complex(r1,i2);
 	}
	
	public boolean equals(Object other) {
		if(!(other instanceof Complex))return false;
		Complex z = (Complex)other;
		return z.real == real && z.imaginary ==imaginary;  
	}
	
	public double getReal() {
		return real;
	}
	
	public double getImaginaire(){
		return imaginary;
	}
	
	public Complex getConjugue(){
		return new Complex(real,(-1) * imaginary);
	}
	
	public double module() {
		return Math.sqrt(real*real + imaginary*imaginary);
	}
	
	public double argument() {
		if (imaginary>=0) return Math.acos(real/this.module());
		else return - Math.acos(real/this.module());
	}
	
	public static Complex fromPolarCoordinates(double rho,double theta) {
		return new Complex(rho*Math.cos(theta),rho*Math.sin(theta));
	}
	
	public static Complex stringToComplex(String s) {
		s=s.replaceAll("\\s+", "");
		if(s.length() == 0) return new Complex(0,0);
		double real=0,imaginary=0;
		int tmp = 0;
		String chaine = "";
		while(tmp < s.length()) {
			if( (s.charAt(tmp)=='+' || s.charAt(tmp) =='-' ) && tmp != 0)break;
			chaine = chaine + s.charAt(tmp);
			tmp ++;
		}
		if(chaine.endsWith("i")) {
			chaine = chaine.replaceAll("i", "");
			imaginary = Double.valueOf(chaine);
		}
		else {
			real = Double.valueOf(chaine);
		}
		chaine = s.substring(tmp);;
		if(chaine.endsWith("i")) {
			chaine = chaine.replaceAll("i", "");
			if(chaine.length()==1)imaginary =Double.valueOf( s.charAt(tmp)+"1" );			
			else imaginary = Double.valueOf(chaine);
		}
		else {
			if(chaine.length()!=0) real = Double.valueOf(chaine);
		}
		
		return new Complex(real,imaginary);
	}
	
}

