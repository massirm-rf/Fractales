import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
	private  Julia julia;
	private String savePath;
	private ListeUsers listeUsers = new ListeUsers();
	private User user;
	private Accueil accueil;
	private ListeUsers listUsers;
	private int[][] colors = new int[1300][957];

	public Controlor(ChoiseFrame choiseframe) {
		this.choiseFrame = choiseframe;
		this.fractalPane = new FractalPane(choiseframe);
		this.fractalPane.setControleur(this);
		this.savePath = "../Fractales generees/";
		this.listeUsers = new ListeUsers();		//this.accueil = new Accueil();
		addActions();
	}
	
	public Controlor(Accueil accueil) {
		this.accueil = accueil;
		this.choiseFrame = new ChoiseFrame(this);
		this.fractalPane = new FractalPane(this.choiseFrame);
		this.fractalPane.setControleur(this);
		this.savePath = "../Fractales generees/";
		/*try {
			FileInputStream fis = new FileInputStream("../saves/colors");
			ObjectInputStream ois = new ObjectInputStream(fis);
			colors = (int[][]) ois.readObject();
			System.out.println(colors[0][10]);
		}
        catch (IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}*/
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
		int[][] tab = getColors();
		var img = new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < julia.getWidth(); i++) {
			for (int j = 0; j < julia.getHeight(); j++) {
				Complex z = julia.toComplex2(i, j);// à modifier
				int it = julia.diverfgenceIndex2(z);// à verifier
				if (it == julia.getIterations()) {
					//img.setRGB(i, j, tab[it%956][100]);
					img.setRGB(i, j, (0 << 16) + (0 << 8) + 0);
					img.setRGB(i, j, tab[400][1000]);
				} else {
					int r= (255*it)/julia.getIterations(); int g=0; int b=0;
					int rgb3 = (int)(4280*(Math.sin(it+600))+700) << 16 |
                         (int) (2056*((Math.sin(it+600))+700)) << 8 |
                          (int) (150000 *(Math.sin(it+600))+700);
					int rgb=Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
					int rgb2 = (it << 16) + (it << 8) + it;
					//int rgb4 = colors[i][j];
					img.setRGB(i, j, tab[550][(it+380)%1024]);
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
		
		threadsList.forEach(th->{
			try {
				th.join();
			} catch (InterruptedException e) {
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
		accueil.getConnexionButton().addActionListener(l->connect());
		choiseFrame.getFavoriteButton().addActionListener(l->favoriteAction());
		choiseFrame.getMyFavoritsButton().addActionListener(l->loadFavoriteAction());
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
		if(this.julia == null)return;//exception à gerer
		Julia j = this.julia;
		if(user.dejaSauvegarde(j)) {
			user.removeFractal(j);
		}
		listeUsers.addFratalExpression(j, user.getName());
		JOptionPane.showMessageDialog(null, "Votre fractale a ete ajoutee a la liste des favoris avec succes", "Favoris",
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("../images/done.png"));
	}
	
	public void loadFavoriteAction() {
		LinkedList<Julia> usersJulias = listeUsers.recupUser(user.getName()).getFractalList();
		ArrayList<String> constantsList = (ArrayList<String>) usersJulias.stream().map(j->j.
				getC().toString() ).collect(Collectors.toList());
		if(constantsList.size()==0)return;//exception à gérer
		String input = (String) JOptionPane.showInputDialog(null, "Mes fractales",
		        "Choisir la constante de votre fractale", JOptionPane.QUESTION_MESSAGE, null, // Use
		                                                                        // default
		                                                                        // icon
		        constantsList.toArray(), // Array of choices
		        constantsList.get(0)); // Initial choice
		if(input==null)return;
		Julia julia2=null;
		for(Julia x : usersJulias) {
			if(x.getC().toString().equals(input))julia2=x;
		}
		fillBoxes(julia2);
	}
	
	public void fillBoxes(Julia j) {
		choiseFrame.getConstante().setText(j.getC().toString());
		choiseFrame.getLargeur().setText(""+j.getWidth());
		choiseFrame.getLongueur().setText(""+j.getHeight());
		choiseFrame.getMaxIteration().setText(""+j.getIterations());
		
	}

	public int[][] getColors() {
		BufferedImage image;

		try {
			image = ImageIO.read(new File("../images/multicolor.jpg"));
			int width = image.getWidth();
			int height = image.getHeight();
			int[][] result = new int[height][width];

			for (int row = 0; row < height; row++) {
				for (int col = 0; col < width; col++) {
					int c = image.getRGB(col, row);
					int b = c & 0xff;
					int g = (c & 0xff00) >> 8;
					int r = (c & 0xff0000) >> 16;
					int co = (r << 16) | (g << 8) | b;
					result[row][col] = co;

				}
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
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
					fractalPane.zoom(p, 1.5);
					update();
					fractalPane.getImagePane().repaint();
				}

			}
		});
	}
		

}
