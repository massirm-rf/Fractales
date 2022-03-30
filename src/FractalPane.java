import javax.swing.JFrame;

public class FractalPane extends JFrame implements Zoomable {


	private ImagePanel imagePanel ;
	private Controlor controleur;
	private ChoiseFrame choiseFrame;
    static double zoom = 1 ;
	
	public FractalPane(ChoiseFrame choiseFrame) {
		initialize();
		this.choiseFrame = choiseFrame;
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setResizable(false);
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
		    double longueur = (this.controleur.getJulia().getMaxI() - this.controleur.getJulia().getMinI()) *factor;
			Complex complexCenter = controleur.getJulia().toComplex(center);
			this.controleur.getJulia().setMinR(complexCenter.getReal() - largeur / 2);  
			this.controleur.getJulia().setMaxR(complexCenter.getReal() + largeur / 2);  
			this.controleur.getJulia().setMinI(complexCenter.getImaginary() - longueur / 2) ;
			this.controleur.getJulia().setMaxI(complexCenter.getImaginary() + longueur / 2) ;
			zoom /= factor; 
			
	}
	
	public void setTitle() {
		String title = "Fractale de Julia " + choiseFrame.getConstante().getText() + "      Zoom X "+zoom;
		setTitle(title);
	}
	
	public void deplace(Point center) {
		double largeur = (this.controleur.getJulia().getMaxR() - this.controleur.getJulia().getMinR()) ;
		double longueur = (this.controleur.getJulia().getMaxI() - this.controleur.getJulia().getMinI()) ;
		Complex complexCenter = controleur.getJulia().toComplex(center);
		this.controleur.getJulia().setMinR(complexCenter.getReal() - largeur / 2);  
		this.controleur.getJulia().setMaxR(complexCenter.getReal() + largeur / 2);  
		this.controleur.getJulia().setMinI(complexCenter.getImaginary() - longueur / 2) ;
		this.controleur.getJulia().setMaxI(complexCenter.getImaginary() + longueur / 2) ;
		 
	}
}
