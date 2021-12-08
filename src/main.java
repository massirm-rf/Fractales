import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stubS
		/*Scanner scan = new Scanner(System.in);
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
				Complex z = julia.toComplex(i, j);
				int it = julia.diverfgenceIndex(z);
				//System.out.println(it);
				if(it>=1000) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("votre fractale a ete bien generee");
		*/
		Complex[][] tab = new Complex[201][201]; 
		double a=-1,b=-1;
		for(int i= 0;i<=200;i++) {
			b=-1;
			for(int j =0;j<=200;j++) {
				tab[i][j]=new Complex(a, b);
				b+=0.01;
			}
			a+=0.01;
			
		}
		//System.out.println("a = "+a+" b="+b);
		Julia julia = new Julia(new Complex(-0.7269,0.1889),1001,1001,1000);
		var img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		int x=0,y=0;
		for(int i = 0;i<1000;i++) {
			if(i!=0) {
				if((1000-i+1)%5 ==0)x++;
			}
			for(int j=0;j<1000;j++) {
				if(j!=0) {
					if((1000-j+1)%5 ==0)y++;
				}
				//System.out.println("i="+i+" j="+j);
				int it = julia.diverfgenceIndex(tab[x][y]);
				//System.out.println(it);
				if(it>=1000) {
					img.setRGB(i,j,(0 << 16) + (0 << 8) + 0);
				}else {
					int rgb=Color.HSBtoRGB((float)it/1000, 0.7f, 0.7f);
					img.setRGB(i,j, rgb);	
				}
			}
			y=0;
		}
		File f = new File("../Fractales generees/"+"fractale.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
