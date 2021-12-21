import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class Controleur {
	
	private Fenetre fenetre;
	private Julia julia;
	
	public Controleur(Fenetre fenetre) {
		
		this.fenetre = fenetre;
		addActions();
	}
	
	
	public Fenetre getFenetre() {
		return fenetre;
	}


	public void setFenetre(Fenetre fenetre) {
		this.fenetre = fenetre;
	}


	public Julia getJulia() {
		return julia;
	}



	public void setJulia(Julia julia) {
		this.julia = julia;
	}



	public void initialiserJulia() {
		
		Complex constante = Complex.stringToComplex(fenetre.getConstante().getText());
		int largeur = Integer.valueOf(fenetre.getLargeur().getText());
		int longueur =  Integer.valueOf(fenetre.getLongueur().getText());
		int itterations =(int) fenetre.getItterations().getValue(); 
		
		this.julia = new Julia(constante, largeur, longueur, itterations);
		this.julia.setMaxI(1);
		this.julia.setMinI(-1);
		this.julia.setMinR(-1);
		this.julia.setMaxR(1);
	}
	
	public void addActions() {
		this.fenetre.getCalculerButton().addActionListener(l ->dessiner());
		this.fenetre.getRedSlider().addChangeListener(l->modifyred());
		this.fenetre.getSaveButton().addActionListener(l->saveButtonAction());
		this.fenetre.getGreenSlider().addChangeListener(l->modifyGreen());
		zoomListner();
	}
	
	public void dessiner() {
		initialiserJulia();
		var img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int i =0;i<julia.getWidth();i++) {
			for(int j = 0;j<julia.getHeight();j++) {
				Complex z = julia.toComplex2(i, j);//à modifier
				int it = julia.diverfgenceIndex2(z);//à verifier
				if(it>=julia.getIterations()) {
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
	
	public void dessiner2() {
		//initialiserJulia();
		var img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int i =0;i<julia.getWidth();i++) {
			for(int j = 0;j<julia.getHeight();j++) {
				Complex z = julia.toComplex(new Point(i, j));//à modifier
				int it = julia.diverfgenceIndex2(z);//à verifier
				if(it>=julia.getIterations()) {
					img.setRGB(i,j,(0 << 16) + (0 << 8) + 0);
				}else {
					//int rgb=Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
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
				Complex z = julia.toComplex2(i, j);
				int it = julia.diverfgenceIndex2(z);
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
	
	public void modifyGreen() {
		var img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		for(int i =0;i<julia.getWidth();i++) {
			for(int j = 0;j<julia.getHeight();j++) {
				Complex z = julia.toComplex2(i, j);
				int it = julia.diverfgenceIndex2(z);
				//int green = fenetre.getImagePane().getImage().getRGB(i, j);
				if(it>=1000) {
					img.setRGB(i,j,0 << 16 + (fenetre.getGreenSlider().getValue() << 8) + 0);
				}else {
					int rgb=Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
					int rgb2 =  (it << fenetre.getRedSlider().getValue()) + (fenetre.getGreenSlider().getValue() << 8) + it;
					img.setRGB(i,j, rgb2);
				}
			}
		}
		fenetre.getImagePane().setImage(img);
	}
	
	public void saveButtonAction() {
		File f = new File("../Fractales generees/"+fenetre.getConstante().getText()+" "+fenetre.getLargeur().getText()+
				"*"+fenetre.getLongueur().getText()+" "+fenetre.getItterations().getValue()+".png");
		try {
			ImageIO.write(fenetre.getImagePane().getImage(), "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Votre image est enregestree avec succes", "Sauvegarde", 
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/done.png"));
	}
	
	public void zoomListner() {
		fenetre.getImagePane().addMouseListener(new MouseInputListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Point p = new Point(e.getPoint().x, e.getPoint().y );

                //Si l'utilisateur clique sur le bouton gauche
                if(SwingUtilities.isLeftMouseButton(e)){
                    fenetre.zoom(p, 0.5);
                    dessiner2();
                    fenetre.getImagePane().repaint();
                    //System.out.println("Bouton gauche clicked");
                }
                
                if(SwingUtilities.isRightMouseButton(e)){
                    fenetre.zoom(p, 1.5);
                    dessiner2();
                    fenetre.getImagePane().repaint();
                    //System.out.println("Bouton droit clicked");
                }
				
			}
		});
	}

}
