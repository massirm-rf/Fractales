import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;
public class main {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in).useLocale(Locale.US);
		System.out.println("entrez la constante c");
		String str = scan.nextLine();
		str.replace("\\s+", "");
		Complex c = Complex.stringToComplex(str);
		Julia julia = new Julia(c);
		int width,height,iterations,choix;
		double pas,minI,maxI;
		System.out.println("Choisissez le mode de de generation de fractale\n");
		System.out.println("1- L'utilisateur saisivar img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);e le pas et rectangle de travail sur C");
		System.out.println("2- L'utilisateur saisie la dimensions de l'image (le pas sera adpte par l'application )");
		choix = scan.nextInt();
		switch(choix) {
		
		case 1 : 
			System.out.println("entrer le nombre d'iteration pour la divergence");
			iterations = scan.nextInt();
			System.out.println("entrer le pas de discretisation");
			pas = scan.nextDouble();
			System.out.println("entrer le minI du rectanngle du travail");
			minI = scan.nextDouble();
			System.out.println("entrer le maxI du rectanngle du travail");
			maxI = scan.nextDouble();
			julia = new Julia(c,iterations,minI,maxI,pas);
			julia.generateImage();
			break;
		case 2 : 
			System.out.println("entrer le nombre d'iteration pour la divergence");
			iterations = scan.nextInt();
			System.out.println("entrer la largeur de la matrice en pixel");
			width = scan.nextInt();
			System.out.println("entrer la longueur de la matrice en pixel");
			height = scan.nextInt();
			julia = new Julia(c,width,height,iterations);
			julia.generatFractale();
			break;
		default :
			break;
		}
		
	}
}
