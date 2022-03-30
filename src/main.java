import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class main {
	static Scanner scan = new Scanner(System.in).useLocale(Locale.US);
	static Complex c;
	static int width, height, iterations, choix;
	static double pas, minR, maxR, minI, maxI;

	public static void main(String[] args) {

		Julia julia;
		
		int fractalType = fractalTypeChoice();
		choix = generationMode();
		switch (choix) {
		case 1:
			initializeParam();
			if (fractalType == 1) {
				julia = new Julia.Builder(c, iterations).rectangle(minR, maxR, minI, maxI, pas).build();
				julia = new Julia(c, iterations, minR, maxR, minI, maxI, pas);
				julia.generateImage();
			} else {
				Mandelbrot mandelbrot = new Mandelbrot.Builder(iterations).rectangle(minR, maxR, minI, maxI, pas)
						.build();
				mandelbrot = new Mandelbrot(iterations, minR, maxR, minI, maxI, pas);
				mandelbrot.generateImage();
			}
			break;
		case 2:
			initializeWidthHeight();
			/// julia = new Julia(c,width,height,iterations);
			if (fractalType == 1) {
				julia = new Julia.Builder(c, iterations).matrices(width, height).build();
				julia.generatFractale();
			} else {
				Mandelbrot mandelbrot = new Mandelbrot.Builder(iterations).matrices(width, height).build();
				mandelbrot.generatFractale();
				break;
			}

		default:
			break;
		}

	}
	
	private static int fractalTypeChoice() {
		int fractalType = 0;
		scan = new Scanner(System.in).useLocale(Locale.US);
		System.out.println("Veuiller choisir l'ensemble à génerer\n1- Ensemble de Julia\n2-Ensemble de Mandelbrot");
		try {
			fractalType = scan.nextInt();
		} catch (InputMismatchException e) {
			fractalTypeChoice();
		}

		if (fractalType != 1 && fractalType != 2) {
			fractalTypeChoice();
		}

		if (fractalType == 1) {
			initializeConstant();
		}
		return fractalType;
	}
	
	private static int generationMode() {
		int choix=0;
		try {
			do {
				System.out.println("Choisissez le mode de de generation de fractale\n");
				System.out.println("1- L'utilisateur saisie le pas et rectangle de travail sur C");
				System.out.println(
						"2- L'utilisateur saisie la dimensions de l'image (le pas sera adpte par l'application )");
				choix = scan.nextInt();
			} while (choix != 1 && choix != 2);

		} catch (InputMismatchException e) {
			generationMode();
		}
		return choix;
	}
	
	private static void initializeConstant() {
		
		try{
			System.out.println("entrer la constante c");
		String str = scan.next();
		str.replace("\\s+", "");
	    c = Complex.stringToComplex(str);
		}catch(NumberFormatException e) {
			initializeConstant();
		}
	}
	
	private static void initializeParam() {
		int it=0;
		double p=0,mini=0,maxr=0,minr=0,maxi=0;
		scan = new Scanner(System.in).useLocale(Locale.US);
		try {
			System.out.println("entrer le nombre d'iteration pour la divergence");
			it = scan.nextInt();
			System.out.println("entrer le pas de discretisation");
			p = scan.nextDouble();
			System.out.println("entrer le minR du rectanngle du travail");
			mini = scan.nextDouble();
			System.out.println("entrer le maxR du rectanngle du travail");
			maxr = scan.nextDouble();
			System.out.println("entrer le minI du rectanngle du travail");
			mini = scan.nextDouble();
			System.out.println("entrer le maxI du rectanngle du travail");
			maxi = scan.nextDouble();
		} catch (NumberFormatException | InputMismatchException e) {
			System.out.println("reverifier votre saisie");
			initializeParam();
		}
		pas = p;minI=mini;maxR=maxr;minR=minr;maxI=maxi;iterations=it;
	}
	
	private static void initializeWidthHeight() {
		scan = new Scanner(System.in).useLocale(Locale.US);
		try {
			System.out.println("entrer le nombre d'iteration pour la divergence");
			iterations = scan.nextInt();
			System.out.println("entrer la largeur de la matrice en pixel");
			width = scan.nextInt();
			System.out.println("entrer la longueur de la matrice en pixel");
			height = scan.nextInt();
			
		} catch (NumberFormatException|InputMismatchException e) {
			System.out.println("reverifier votre saisie");
			initializeWidthHeight();
		}
	}
}
