import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FractalPane extends JFrame implements Zoomable {


	private ImagePanel imagePanel ;
	private Controlor controleur;
	private ChoiseFrame choiseFrame;
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FractalPane window = new FractalPane();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public FractalPane(ChoiseFrame choiseFrame) {
		initialize();
		this.choiseFrame = choiseFrame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.imagePanel = new ImagePanel();
		
		this.getContentPane().add(imagePanel);
		
	}

	public ImagePanel getImagePane() {
		return imagePanel;
	}

	public void setImagePanel(ImagePanel imagePanel) {
		this.imagePanel = imagePanel;
	}
	

	public Controlor getControleur() {
		return controleur;
	}

	public void setControleur(Controlor controleur) {
		this.controleur = controleur;
	}

	public ChoiseFrame getChoiseFrame() {
		return choiseFrame;
	}

	public void setChoiseFrame(ChoiseFrame choiseFrame) {
		this.choiseFrame = choiseFrame;
	}

	public ImagePanel getImagePanel() {
		return imagePanel;
	}

	@Override
	public int height() {
		return 0;
	}

	@Override
	public int width() {
		return 0;
	}

	@Override
	public void zoom(Point center, double factor) {
		
		double largeur = (this.controleur.getJulia().getMaxR() - this.controleur.getJulia().getMinR()) * factor;
			Complex complexCenter = controleur.getJulia().toComplex(center);
			this.controleur.getJulia().setMinR(complexCenter.getReal() - largeur / 2);  
			this.controleur.getJulia().setMaxR(complexCenter.getReal() + largeur / 2);  
			this.controleur.getJulia().setMinI(complexCenter.getImaginary() + largeur / 2) ;
			this.controleur.getJulia().setMaxI(complexCenter.getImaginary() - largeur / 2) ;
			
	}
	
	
}
