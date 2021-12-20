import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class main2 {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("entrez la constante c");
		String str = scan.nextLine();
		str.replace("\\s+", "");
		Complex c = Complex.stringToComplex(str);
		int width,height,iterations;
		System.out.println("entrer la largeur de la matrice en pixel");
		width = scan.nextInt();
		System.out.println("entrer la longueur de la matrice en pixel");
		height = scan.nextInt();
		System.out.println("entrer le nombre d'iteration pour la divergence");
		iterations = scan.nextInt();
		Julia julia = new Julia(c,width,height,iterations);
		var img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		int r = 64; int g = 224; int b = 208; //turquoise
		//int col = (r << 16) | (g << 8) | b;
		for(int i =0;i<img.getWidth();i++) {
			for(int j = 0;j<img.getHeight();j++) {
				Complex z = julia.toComplex2(i, j);
				int it = julia.diverfgenceIndex2(z);
				if(it==julia.getIterations()) {
					img.setRGB(i,j,(0 << 16) + (0 << 8) + 0);
				}else {
					int rgb=Color.HSBtoRGB((float)it/iterations, 0.7f, 0.7f);
					img.setRGB(i,j, rgb);
					
				}
			}
		}
		
		File f = new File("../Fractales generees/"+c+" "+width+"*"+height+" "+iterations+".png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("votre fractale a ete bien generee");
	}

}
