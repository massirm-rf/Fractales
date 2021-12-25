import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class Controlor {

	private ChoiseFrame choiseFrame;
	private FractalPane fractalPane;
	private  Julia julia;
	private String savePath;
	private ListeUsers listeUsers = new ListeUsers();
	private User user;
	private Accueil accueil;

	public Controlor(ChoiseFrame choiseframe) {
		this.accueil = new Accueil(this);
		this.choiseFrame = choiseframe;
		this.fractalPane = new FractalPane(choiseframe);
		this.fractalPane.setControleur(this);
		this.savePath = "../Fractales generees/";
		addActions();
	}

	public ChoiseFrame getChoiseFrame() {
		return choiseFrame;
	}

	public void setChoiseFrame(ChoiseFrame choiseFrame) {
		this.choiseFrame = choiseFrame;
	}

	public FractalPane getFractalPane() {
		return fractalPane;
	}

	public void setFractalPane(FractalPane fractalPane) {
		this.fractalPane = fractalPane;
	}

	public Julia getJulia() {
		return julia;
	}

	public void setJulia(Julia julia) {
		this.julia = julia;
	}

	public boolean initialisateJulia() {
		try {
			
			Complex constante = Complex.stringToComplex(choiseFrame.getConstante().getText());
			int largeur = Integer.valueOf(choiseFrame.getLargeur().getText());
			int longueur = Integer.valueOf(choiseFrame.getLongueur().getText());
			int itterations = Integer.valueOf(choiseFrame.getMaxIteration().getText());
			FractalPane.zoom = 1;
			if(largeur<100 || longueur <100)throw new InputException();
			//this.julia = new Julia(constante, largeur, longueur, itterations);
			julia = new Julia.Builder(constante, itterations).
					matrices(largeur, longueur).rectangle(-1, 1, -1, 1, 0.01).build();
			return true ;
			
		} catch (NumberFormatException | InputException e) {
			
			if (e instanceof NumberFormatException) {
				JOptionPane.showMessageDialog(null, "Merci de reverifier votre saisie ", "Warning",
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/not done.png"));
			}
			else {
				JOptionPane.showMessageDialog(null, " La longueur et la largeur ne peuvent pas etre inferieures a 100", "Warning",
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/warning.png"));
			}
			return false;
		}
		
	}
	
	public boolean draw() {
		long startTime = System.currentTimeMillis();
		if(!initialisateJulia())return false;
		fractalPane.setTitle();
		var img = new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < julia.getWidth(); i++) {
			for (int j = 0; j < julia.getHeight(); j++) {
				Complex z = julia.toComplex2(i, j);// à modifier
				int it = julia.diverfgenceIndex2(z);// à verifier
				if (it == julia.getIterations()) {
					img.setRGB(i, j, (0 << 16) + (0 << 8) + 0);
				} else {
					int r= (255*it)/julia.getIterations(); int g=0; int b=0;
					int rgb3 = (int)(4280*(Math.sin(it+600))+700) << 16 |
                         (int) (2056*((Math.sin(it+600))+700)) << 8 |
                          (int) (150000 *(Math.sin(it+600))+700);
					int rgb=Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
					int rgb2 = (it << 16) + (it << 8) + it;
					img.setRGB(i, j, rgb3);
				}
			}
		}	

		fractalPane.getImagePane().setImage(img);
		fractalPane.getImagePane().repaint();
		long endTime = System.currentTimeMillis();
	       System.out.println("Total execution time: " + (endTime-startTime) + "ms"); 
		return true;
	}

	public void update() {
		long startTime = System.currentTimeMillis();
		var img = new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		fractalPane.setTitle();
		ArrayList<Thread> threadsList = new ArrayList<Thread>();

		// creation de 4 threads (chacun va calculer 1/4 des pixels de l'image)
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				int startX = i * julia.getWidth() / 2;
				int endX = (i + 1) * julia.getWidth() / 2;
				int startY = j * julia.getHeight() / 2;
				int endY = (j + 1) * julia.getHeight() / 2;
				Thread th = new Thread(new MyRun(startX, startY, endX, endY, img,this));
				threadsList.add(th);
				th.start();
			}
		}
		
		/*boolean stop;
		  do
	        {
	            stop=true;
	            for(int j=0;j<4;j++)
	            {
	                if (threadsList.get(j).isAlive())stop=false;
	            }
	        }while(!stop);*/
		threadsList.forEach(th->{
			try {
				th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		fractalPane.getImagePane().setImage(img);
		fractalPane.getImagePane().repaint();
		long endTime = System.currentTimeMillis();
	       System.out.println("Total execution time: " + (endTime-startTime) + "ms"); 
	}

	public void addActions() {
		this.choiseFrame.getDrawButton().addActionListener(l -> {
			if (!draw())return;
			// fractalPane.setSize(julia.getWidth(),julia.getHeight());
			fractalPane.setBounds(800, 0, julia.getWidth(), julia.getHeight());
			fractalPane.getImagePane().setBounds(0, 0, julia.getWidth(), julia.getHeight());
			fractalPane.setVisible(true);
		});
		choiseFrame.getSaveButton().addActionListener(l->saveAction());
		choiseFrame.getSaveAsButton().addActionListener(l->saveAsAction());
		choiseFrame.getRestoreButton().addActionListener(l->{
			if(fractalPane.getImagePane().getImage()==null)return;
			draw();
			fractalPane.getImagePane().repaint();
		});
		choiseFrame.getExitButton().addActionListener(l->exitListner());
		//accueil.getConnexionButton().addActionListener(l->connect());
		zoomListner();
	}
	
	public void saveAction() {
		if(!testImageInpanel())return;
		String fileName = savePath+"Fractale " +choiseFrame.getConstante().getText()
				+" "+choiseFrame.getLargeur().getText()+
				"*"+choiseFrame.getLongueur().getText()+" "+choiseFrame.getMaxIteration().getText()+".png";
		File f = new File(fileName);
		try {
			ImageIO.write(fractalPane.getImagePane().getImage(), "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Votre image est enregestree avec succes", "Sauvegarde", 
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/done.png"));
	}
	
	public void saveAsAction() {
		
		if(!testImageInpanel())return;
		String fileName = choiseFrame.getConstante().getText() + " " + choiseFrame.getLargeur().getText()
				+ "*" + choiseFrame.getLongueur().getText() + " " + choiseFrame.getMaxIteration().getText() + ".png";

		JFileChooser fileChooser = new JFileChooser(savePath);
		fileChooser.setDialogTitle("Enregistrer sous...");
		fileChooser.setSelectedFile(new File("Fractale"));
		int userSelection = fileChooser.showSaveDialog(choiseFrame);
		
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			try {
				ImageIO.write(fractalPane.getImagePane().getImage(), "png",
						new File(fileToSave.getAbsolutePath() + fileName));
				this.savePath = fileToSave.getAbsolutePath() ;
				JOptionPane.showMessageDialog(null, "Votre image est enregestree avec succes", "Sauvegarde",
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/done.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			;
		}
	}
	
	public void exitListner() {
		choiseFrame.dispose();
		fractalPane.dispose();
	}
	
	public boolean testImageInpanel() {
		if(fractalPane.getImagePane().getImage()==null) {
			JOptionPane.showMessageDialog(null, "Aucune fractale a enregestrer ", "Sauvegarde",
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/warning.png"));
			return false;
		}
		return true;
	}
	
	public void connect() {
		String userName = JOptionPane.showInputDialog(accueil, "Veuiller saisir votre username");
		if(userName !=null ) {
			if(!listeUsers.Existe(userName)) {
				user = new User(userName);
				listeUsers.ajouter(user);
			}
			else {
				user = listeUsers.recupUser(userName);
			}
			accueil.dispose();
			choiseFrame.setVisible(true);
		}
	}

	public void zoomListner() {
		
		fractalPane.getImagePane().addMouseListener(new MouseInputListener() {
			int prevX,prevY;
			@Override
			public void mouseMoved(MouseEvent arg0) {
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Point p = new Point(prevX,prevY);
				if(e.getX()!=prevX || e.getY() !=prevY) {
					fractalPane.deplace(p);
					update();
					fractalPane.getImagePane().repaint();
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				prevX = e.getX();
				prevY = e.getY();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				Point p = new Point(e.getPoint().x, e.getPoint().y);

				// Si l'utilisateur clique sur le bouton gauche
				if (SwingUtilities.isLeftMouseButton(e)) {
					fractalPane.zoom(p, 0.5);
					update();
					fractalPane.getImagePane().repaint();
				}

				if (SwingUtilities.isRightMouseButton(e)) {
					fractalPane.zoom(p, 1.5);
					update();
					fractalPane.getImagePane().repaint();
					
				}

			}
		});
	}
		

}
