import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class Controlor {

	private ChoiseFrame choiseFrame;
	private FractalPane fractalPane;
	private  Fractale fractal;
	private String savePath;
	private ListeUsers listeUsers = new ListeUsers();
	private User user;
	private Accueil accueil;
	private ListeUsers listUsers;
	private int[][] colors ;
	
	public Controlor(Accueil accueil) {
		this.accueil = accueil;
		this.choiseFrame = new ChoiseFrame(this);
		this.fractalPane = new FractalPane(this.choiseFrame);
		this.fractalPane.setControleur(this);
		this.savePath = "../Fractales generees/";
		loadColors();
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

	public Fractale getJulia() {
		return fractal;
	}

	public void setFractale(Fractale fractal) {
		this.fractal = fractal;
	}
	
	public int[][] getColor(){
		return colors;
	}

	public boolean initialisateJulia() {
		
		try {
			
			
			int largeur = Integer.valueOf(choiseFrame.getLargeur().getText());
			int longueur = Integer.valueOf(choiseFrame.getLongueur().getText());
			int itterations = Integer.valueOf(choiseFrame.getMaxIteration().getText());
			FractalPane.zoom = 1;
			if(largeur<100 || longueur <100)throw new InputException();
			if(choiseFrame.getFractalTypeBox().getSelectedIndex()==0) {
				Complex constante = Complex.stringToComplex(choiseFrame.getConstante().getText());
				fractal = new Julia.Builder(constante, itterations).
						matrices(largeur, longueur).rectangle(-1, 1, -1, 1, 0.01).build();
			}
			else {
				fractal = new Mandelbrot.Builder(itterations).rectangle(-1.5, 1.5, -1.5, 1.5, 0.01).
						matrices(largeur,longueur).build();
			}
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
		if(!initialisateJulia())return false;
		int colorMode = choiseFrame.getColorBox().getSelectedIndex();
		fractalPane.setTitle();
		var img = new BufferedImage(fractal.getWidth(), fractal.getHeight(), BufferedImage.TYPE_INT_RGB);
		int convergeRgb = choiseFrame.getConvergeColorSlider().getValue();
		for (int i = 0; i < fractal.getWidth(); i++) {
			for (int j = 0; j < fractal.getHeight(); j++) {
				Complex z = fractal.toComplex(i, j);
				int it = fractal.divergenceIndex(z);
				if (it == fractal.getIterations()) {
					img.setRGB(i, j, (0 << 16) + (0 << 8) + 0);
				} else {
					int rgb;
					if(colorMode==0)rgb= (it << 16) + (it << 8) + it;
					else if(colorMode ==1) rgb = Color.HSBtoRGB((float)it/fractal.getIterations(), 0.7f, 0.7f);
					else rgb = colors[200][(it+convergeRgb+131)%512];
					img.setRGB(i, j, rgb);
				}
			}
		}	

		fractalPane.getImagePane().setImage(img);
		fractalPane.getImagePane().repaint();
		return true;
	}

	public void update() {
		var img = new BufferedImage(fractal.getWidth(), fractal.getHeight(), BufferedImage.TYPE_INT_RGB);
		fractalPane.setTitle();
		ArrayList<Thread> threadsList = new ArrayList<Thread>();

		// creation de 4 threads (chacun va calculer 1/4 des pixels de l'image)
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				int startX = i * fractal.getWidth() / 2;
				int endX = (i + 1) * fractal.getWidth() / 2;
				int startY = j * fractal.getHeight() / 2;
				int endY = (j + 1) * fractal.getHeight() / 2;
				Thread th = new Thread(new MyRun(startX, startY, endX, endY, img, this));
				threadsList.add(th);
				th.start();
			}
		}

		threadsList.forEach(th -> {
			try {
				th.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		fractalPane.getImagePane().setImage(img);
		fractalPane.getImagePane().repaint();
	}
	

	public void addActions() {
		this.choiseFrame.getDrawButton().addActionListener(l -> {
			if (!draw())return;
			fractalPane.setBounds(800, 0, fractal.getWidth(), fractal.getHeight()+10);
			fractalPane.getImagePane().setBounds(0, 0, fractal.getWidth(), fractal.getHeight());
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
		accueil.getConnexionButton().addActionListener(l->connect());
		choiseFrame.getFavoriteButton().addActionListener(l->favoriteAction());
		choiseFrame.getMyFavoritsButton().addActionListener(l->loadFavoriteAction());
		choiseFrame.getColorBox().addActionListener(l->colorModeAction());
		choiseFrame.getFractalTypeBox().addActionListener(l->typeConstantListner());
		slidersActions();
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
		new Accueil().setVisible(true);
		
	}
	
	public boolean testImageInpanel() {
		if(fractalPane.getImagePane().getImage()==null) {
			JOptionPane.showMessageDialog(null, "Aucune fractale a enregestrer ", "Sauvegarde echouee",
					JOptionPane.ERROR_MESSAGE, new ImageIcon("../images/warning.png"));
			return false;
		}
		return true;
	}
	
	public void connect() {
		String userName = "";
		while (userName.equals("")) {
			userName = JOptionPane.showInputDialog(accueil, "Veuiller saisir votre username");
			if(userName==null)return;//pour eviter la NullPointerException
		}
		
		if (!listeUsers.Existe(userName)) {
			user = new User(userName);
			listeUsers.ajouter(user);
		} else {
			user = listeUsers.recupUser(userName);
		}
		accueil.dispose();
		choiseFrame.setVisible(true);

	}
	
	public void favoriteAction() {
		if(this.fractal == null)return;//exception à gerer
		if(this.fractal instanceof Julia) {
			Julia j = (Julia) this.fractal;
			if (user.dejaSauvegarde(j)) {
				user.removeFractal(j);
			}
			listeUsers.addFratalExpression(j, user.getName());
			JOptionPane.showMessageDialog(null, "Votre fractale a ete ajoutee a la liste des favoris avec succes", "Favoris",
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/done.png"));
		}
		else {
			JOptionPane.showMessageDialog(null, "Retrouver la fractale en selectionnant le type Mandelbrot", "Favoris",
					JOptionPane.NO_OPTION, null);	
		}
		
		
				
	}
	
	public void loadFavoriteAction() {
		LinkedList<Julia> usersJulias = listeUsers.recupUser(user.getName()).getFractalList();
		ArrayList<String> constantsList = (ArrayList<String>) usersJulias.stream().map(j->j.
				getC().toString() ).collect(Collectors.toList());
		if(constantsList.size()==0) {
			JOptionPane.showMessageDialog(null, "La liste de favoris est vide ", "Warning",
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/not done.png"));
			return;
		}
		String input = (String) JOptionPane.showInputDialog(null, "Mes fractales",
		        "Choisir la constante de votre fractale", JOptionPane.QUESTION_MESSAGE, null,                                                  
		        constantsList.toArray(), 
		        constantsList.get(0)); 
		if(input==null)return;
		Julia julia=null;
		for(Julia x : usersJulias) {
			if(x.getC().toString().equals(input))julia=x;
		}
		fillBoxes(julia);
	}
	
	public void fillBoxes(Julia j) {
		choiseFrame.getConstante().setText(j.getC().toString());
		choiseFrame.getLargeur().setText(""+j.getWidth());
		choiseFrame.getLongueur().setText(""+j.getHeight());
		choiseFrame.getMaxIteration().setText(""+j.getIterations());
		
	}

	public void loadColors() {
		try {
			FileInputStream fis = new FileInputStream("../data colors/texture-colors");
			ObjectInputStream ois = new ObjectInputStream(fis);
			colors = (int[][]) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void slidersActions() {
		//if(fractalPane.getImagePane().getImage()==null)return;
		
		choiseFrame.getConvergeColorSlider().addChangeListener(l->
			update());
	}
	
	public void colorModeAction() {
		if(choiseFrame.getColorBox().getSelectedIndex()!=2)choiseFrame.getConvergeColorSlider().setEnabled(false);
		else choiseFrame.getConvergeColorSlider().setEnabled(true);
		//draw();
		if(fractalPane.getImagePane().getImage()==null)return;
		update();
	}
	
	public void typeConstantListner() {
		if(choiseFrame.getFractalTypeBox().getSelectedIndex()==0)choiseFrame.getConstante().setEnabled(true);
		else choiseFrame.getConstante().setEnabled(false);
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
				fractalPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

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
				fractalPane.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
				
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
					fractalPane.zoom(p, 2);
					update();
					fractalPane.getImagePane().repaint();
				}

			}
		});
	}
		

}
