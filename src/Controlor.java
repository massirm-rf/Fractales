import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class Controlor {

	private ChoiseFrame choiseFrame;
	private FractalPane fractalPane;
	private Julia julia;

	public Controlor(ChoiseFrame choiseframe) {
		this.choiseFrame = choiseframe;
		this.fractalPane = new FractalPane(choiseframe);
		this.fractalPane.setControleur(this);
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

	public void initialisateJulia() {

		Complex constante = Complex.stringToComplex(choiseFrame.getConstante().getText());
		int largeur = Integer.valueOf(choiseFrame.getLargeur().getText());
		int longueur = Integer.valueOf(choiseFrame.getLongueur().getText());
		int itterations = Integer.valueOf(choiseFrame.getMaxIteration().getText());
		this.julia = new Julia(constante, largeur, longueur, itterations);
		this.julia.setMaxI(1);
		this.julia.setMinI(-1);
		this.julia.setMinR(-1);
		this.julia.setMaxR(1);
	}

	public void draw() {
		initialisateJulia();
		var img = new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < julia.getWidth(); i++) {
			for (int j = 0; j < julia.getHeight(); j++) {
				Complex z = julia.toComplex2(i, j);// à modifier
				int it = julia.diverfgenceIndex2(z);// à verifier
				if (it >= julia.getIterations()) {
					img.setRGB(i, j, (0 << 16) + (0 << 8) + 0);
				} else {
					int rgb = Color.HSBtoRGB((float) it / julia.getIterations(), 0.7f, 0.7f);
					int rgb2 = (it << 16) + (it << 8) + it;
					img.setRGB(i, j, rgb);
				}
			}
		}
		//ImagePanel panelImage = new ImagePanel(img)
		fractalPane.getImagePane().setImage(img);
	}

	public void update() {
		var img = new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < julia.getWidth(); i++) {
			for (int j = 0; j < julia.getHeight(); j++) {
				Complex z = julia.toComplex(new Point(i, j));// à modifier
				int it = julia.diverfgenceIndex2(z);// à verifier
				if (it >= julia.getIterations()) {
					img.setRGB(i, j, (0 << 16) + (0 << 8) + 0);
				} else {
					int rgb=Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
					int rgb2 = (it << 16) + (it << 8) + it;
					img.setRGB(i, j, rgb);
				}
			}
		}
		fractalPane.getImagePane().setImage(img);
	}

	public void addActions() {
		this.choiseFrame.getDrawButton().addActionListener(l -> {
			draw();
			fractalPane.setSize(julia.getWidth(),julia.getHeight());
			fractalPane.getImagePane().setBounds(0, 0, julia.getWidth(),julia.getHeight());
			fractalPane.setVisible(true);
		});
		zoomListner();
	}

	public void zoomListner() {
		fractalPane.getImagePane().addMouseListener(new MouseInputListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// Auto-generated method stub

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
					// System.out.println("Bouton droit clicked");
				}

			}
		});
	}

}
