import java.awt.Color;
import java.awt.image.BufferedImage;

public class Controleur {
	
	private Fenetre fenetre;
	private Julia julia;
	
	public Controleur(Fenetre fenetre) {
		
		this.fenetre = fenetre;
		
		addActions();
	}
	
	public void initialiserJulia() {
		
		Complex constante = Complex.stringToComplex(fenetre.getConstante().getText());
		int largeur = Integer.valueOf(fenetre.getLargeur().getText());
		int longueur =  Integer.valueOf(fenetre.getLongueur().getText());
		int itterations =(int) fenetre.getItterations().getValue(); 
		
		this.julia = new Julia(constante, largeur, longueur, itterations);
	}
	
	public void addActions() {
		this.fenetre.getCalculerButton().addActionListener(l ->dessiner());
	}
	
	public void dessiner() {
		System.out.println("cliqué");initialiserJulia();
		
		var img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int i =0;i<julia.getWidth();i++) {
			for(int j = 0;j<julia.getHeight();j++) {
				Complex z = julia.toComplex(i, j);
				int it = julia.diverfgenceIndex(z);
				//System.out.println(it);
				if(it>=1000) {
					img.setRGB(i,j,(0 << 16) + (0 << 8) + 0);
				}else {
					int rgb=Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
					int rgb2 =  (it << 16) + (it << 8) + it;
					img.setRGB(i,j, rgb2);
				}
			}
		}
		fenetre.getImagePane().setImage(img);
	}
	
	

}
