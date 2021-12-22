import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class Controlor {

	private ChoiseFrame choiseFrame;
	private FractalPane fractalPane;
	private static Julia julia;

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
		
		ArrayList<Thread> threadsList = new ArrayList<Thread>();
		
		Thread.UncaughtExceptionHandler eHandler = new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread th, Throwable ex) {
            th.interrupt();
            threadsList.remove(th);
            if(threadsList.isEmpty())fractalPane.getImagePane().repaint();
        }
		};    
		
		//creation de 4 threads (chacun va calculer 1/4 des pixels de l'image)
		for(int i=0;i<2;i++) {
			for(int j = 0;j<2;j++) {
				int startX = i * julia.getWidth()/2;
				int endX = (i+1) * julia.getWidth()/2;
				int startY = j * julia.getHeight()/2;
				int endY = (j+1) * julia.getHeight()/2;
				Thread th = new Thread(new MyRunnable(startX, startY, endX, endY, img));
				th.setUncaughtExceptionHandler(eHandler);
				threadsList.add(th);
			}
		}
		
		
		
		for(int i=0;i<4;i++) {
			threadsList.get(i).start();
			try {
				threadsList.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
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
					
				}

			}
		});
	}
	
	
	static class MyRunnable implements Runnable{
		
		private int startX,startY,endX,endY;
		private BufferedImage image;
		
		public MyRunnable(int startX, int startY, int endX, int endY, BufferedImage image) {
			super();
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
			this.image = image;
		}


		@Override
		public void run() {

			for (int i = startX; i < endX; i++) {
				for (int j = startY; j < endY; j++) {
					Complex z = julia.toComplex2(i, j);
					int it = julia.diverfgenceIndex2(z);
					if (it >= julia.getIterations()) {
						image.setRGB(i, j, (0 << 16) + (0 << 8) + 0);
					} else {
						int rgb=Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
						int rgb2 = (it << 16) + (it << 8) + it;
						image.setRGB(i, j, rgb);
						
					}
				}
			}
		}
		
	}

}
