import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in).useLocale(Locale.US);
		System.out.println("entrez la constante c");
		String str = scan.nextLine();
		str.replace("\\s+", "");
		Complex c = Complex.stringToComplex(str);
		Julia julia;
		int width,height,iterations,choix;
		double pas,minR,maxR,minI,maxI;
		System.out.println("Choisissez le mode de de generation de fractale\n");
		System.out.println("1- L'utilisateur saisie le pas et rectangle de travail sur C");
		System.out.println("2- L'utilisateur saisie la dimensions de l'image (le pas sera adpte par l'application )");
		choix = scan.nextInt();
		switch(choix) {
		
		case 1 : 
			System.out.println("entrer le nombre d'iteration pour la divergence");
			iterations = scan.nextInt();
			System.out.println("entrer le pas de discretisation");
			pas = scan.nextDouble();
			System.out.println("entrer le minR du rectanngle du travail");
			minR = scan.nextDouble();
			System.out.println("entrer le maxR du rectanngle du travail");
			maxR = scan.nextDouble();
			System.out.println("entrer le minI du rectanngle du travail");
			minI = scan.nextDouble();
			System.out.println("entrer le maxI du rectanngle du travail");
			maxI = scan.nextDouble();
			julia = new Julia.Builder(c, iterations).rectangle(minR, maxR, minI, maxI, pas).build();
			julia = new Julia(c,iterations,minR,maxR,minI,maxI,pas);
			julia.generateImage();
			break;
		case 2 : 
			System.out.println("entrer le nombre d'iteration pour la divergence");
			iterations = scan.nextInt();
			System.out.println("entrer la largeur de la matrice en pixel");
			width = scan.nextInt();
			System.out.println("entrer la longueur de la matrice en pixel");
			height = scan.nextInt();
			///julia = new Julia(c,width,height,iterations);
			julia = new Julia.Builder(c, iterations).matrices(width, height).build();
			julia.generatFractale();
			break;
		default :
			break;
		}
		
	}
}
