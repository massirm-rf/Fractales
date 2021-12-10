import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
		this.fenetre.getRedSlider().addChangeListener(l->modifyred());
		this.fenetre.getSaveButton().addActionListener(l->saveButtonAction());
	}
	
	public void dessiner() {
		initialiserJulia();
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
		fenetre.getSaveButton().setEnabled(true);
	}
	
	public void modifyred() {
		
		var img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int i =0;i<julia.getWidth();i++) {
			for(int j = 0;j<julia.getHeight();j++) {
				Complex z = julia.toComplex(i, j);
				int it = julia.diverfgenceIndex(z);
				//int green = fenetre.getImagePane().getImage().getRGB(i, j);
				if(it>=1000) {
					img.setRGB(i,j,fenetre.getRedSlider().getValue() + (0 << 8) + 0);
				}else {
					int rgb=Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
					int rgb2 =  (it << fenetre.getRedSlider().getValue()) + (it << 8) + it;
					img.setRGB(i,j, rgb2);
				}
			}
		}
		fenetre.getImagePane().setImage(img);
	}
	
	public void saveButtonAction() {
		File f = new File("../Fractales generees/"+fenetre.getConstante().getText()+" "+fenetre.getLargeur().getText()+
				"*"+fenetre.getLongueur().getText()+" "+fenetre.getItterations().getValue()+".png");
		//File f = new File("../Fractales generees/"+"fractales.png");
		try {
			ImageIO.write(fenetre.getImagePane().getImage(), "PNG", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Votre image est enregestree avec succes", "Sauvegarde", 
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/done.png"));
	}

}
