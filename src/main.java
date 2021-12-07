import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stubS
		Scanner scan = new Scanner(System.in);
		System.out.println("entrez une fonction pour generer l'ensemble");
		String str = scan.nextLine();
		Parser p = new Parser(str);
		p.parsing2();
		Complex c = Complex.stringToComplex("-0.269+0.1889i");
		int width,height,iterations;
		System.out.println("entrer la largeur de la matrice en pixel");
		width = scan.nextInt();
		System.out.println("entrer la longueur de la matrice en pixel");
		height = scan.nextInt();
		System.out.println("entrer le nombre d'iteration pour la divergence");
		iterations = scan.nextInt();
		Julia jul = new Julia(c);
		Julia julia =new Julia(width,height,iterations,p);
		var img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		int r = 64; int g = 224; int b = 208; //turquoise
		//int col = (r << 16) | (g << 8) | b;
		for(int i =0;i<img.getWidth();i++) {
			for(int j = 0;j<img.getHeight();j++) {
				Complex z = julia.toComplex(i, j);
				int it = julia.diverfgenceIndex2(z);
				int it2 = jul.diverfgenceIndex(z);
				System.out.print(it+" || ");
				System.out.println(" "+it2);
				//System.out.println(it);
				if(it>=1000) {
					img.setRGB(i,j,(0 << 16) + (0 << 8) + 0);
				}else {
					img.setRGB(i,j, (it << 16) + (it << 8) + it);
					
				}
			}
		}
		
		File f = new File("../Fractales generees/MyFile.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("votre fractale a ete bien generee");
		/*Parser p = new Parser("z^2+z^3+2-4i");
		p.parsing2();
		Stack<String> pile = (Stack<String>) p.getFileString().clone();
		while(!pile.isEmpty()) {
			System.out.println(pile.pop());
		}
		System.out.println(p.calculer(new Complex(2,1)) );*/	
	}

}
