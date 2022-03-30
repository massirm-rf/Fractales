import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.DefaultComboBoxModel;

public class ChoiseFrame extends JFrame {

	private JTextField width;
	private JTextField height;
	private JTextField constante;
	private JTextField maxIteration;
	private JButton drawButton;
	private JButton saveButton;
	private JButton saveAsButton;
	private JButton aboutButton;
	private JButton helpButton;
	private JButton restoreButton;
	private JButton exitButton;
	private JComboBox colorBox;
	private JButton favoriteButton;
	private JButton myFavoritsButton;
	private JSlider convergeColorSlider;
	private Controlor controleur;
	private JComboBox fractalTypeBox;


	/**
	 * Create the application.
	 */
	public ChoiseFrame(Controlor controleur) {
		getContentPane().setForeground(Color.LIGHT_GRAY);
		getContentPane().setBackground(Color.DARK_GRAY);
		initialize(controleur);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize(Controlor controleur) {
		setTitle("Fractal Generator");
		setBounds(0, 0, 450, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);

		width = new JTextField();
		width.setText("1000");
		width.setBounds(308, 120, 120, 25);
		getContentPane().add(width);
		width.setColumns(10);

		height = new JTextField();
		height.setText("1000");
		height.setBounds(160, 120, 120, 25);
		getContentPane().add(height);
		height.setColumns(10);

		constante = new JTextField();
		constante.setText("0.285+0.01i");
		constante.setBounds(10, 120, 120, 25);
		getContentPane().add(constante);
		constante.setColumns(10);

		JLabel constanteLabel = new JLabel("La constante");
		constanteLabel.setForeground(Color.WHITE);
		constanteLabel.setBounds(15, 90, 120, 20);
		getContentPane().add(constanteLabel);

		JLabel widthLabel = new JLabel("Largeur");
		widthLabel.setForeground(Color.WHITE);
		widthLabel.setBounds(180, 90, 120, 20);
		getContentPane().add(widthLabel);

		JLabel heightLabel = new JLabel("Longueur");
		heightLabel.setForeground(Color.WHITE);
		heightLabel.setBounds(334, 90, 120, 20);
		getContentPane().add(heightLabel);

		drawButton = new JButton("Dessiner");
		drawButton.setToolTipText("generer la fractale");
		drawButton.setBounds(163, 180, 117, 25);
		getContentPane().add(drawButton);

		maxIteration = new JTextField();
		maxIteration.setText("255");
		maxIteration.setBounds(10, 180, 120, 25);
		getContentPane().add(maxIteration);
		maxIteration.setColumns(10);

		colorBox = new JComboBox();
		colorBox.setToolTipText("Choisir la couleur de la fractale");
		colorBox.setModel(new DefaultComboBoxModel(new String[] {"Noir et Blanc", "Fond rouge", "Multicouleur"}));
		colorBox.setSelectedIndex(0);
		colorBox.setBounds(309, 180, 119, 24);
		getContentPane().add(colorBox);

		JLabel IterationsLabel = new JLabel("Iterations");
		IterationsLabel.setForeground(Color.WHITE);
		IterationsLabel.setBounds(33, 153, 120, 25);
		getContentPane().add(IterationsLabel);

		JLabel colorLabel = new JLabel("Couleurs");
		colorLabel.setForeground(Color.WHITE);
		colorLabel.setBounds(337, 153, 70, 25);
		getContentPane().add(colorLabel);

	    myFavoritsButton = new JButton();
	    myFavoritsButton.setToolTipText("Ma liste de fractales");
		myFavoritsButton.setFont(new Font("Dialog", Font.BOLD, 11));
		myFavoritsButton.setText("Mes Fractales");
		myFavoritsButton.setBounds(10, 54, 124, 25);
		getContentPane().add(myFavoritsButton);

		favoriteButton = new JButton(new ImageIcon("../images/star.png"));
		favoriteButton.setToolTipText("Ajouter à mes fractales");
		favoriteButton.setContentAreaFilled(false);
		favoriteButton.setFocusable(false);
		favoriteButton.setBounds(388, 37, 40, 40);
		getContentPane().add(favoriteButton);
		initialiserToolBar();
		
		aboutButton.addActionListener(l->aboutAction());
		helpButton.addActionListener(l->helpAction());

		
		this.controleur = controleur;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialiserToolBar() {
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(Color.WHITE);
		toolBar.setBounds(0, 0, 454, 25);
		toolBar.setLayout(new GridLayout());
		
		
		saveButton = new JButton(new ImageIcon("../images/save.png"));
		saveButton.setFocusable(false);
		saveButton.setToolTipText( "Save (CTRL+S)" );
		toolBar.add(saveButton);
		
		saveAsButton = new JButton( new ImageIcon( "../images/saveAs-icone.png" ) );
        saveAsButton.setToolTipText( "Save As..." );
        toolBar.add( saveAsButton );
        
        restoreButton = new JButton(new ImageIcon("../images/undo.png"));
        restoreButton.setToolTipText( "Revenir à la fractale initiale " );
        toolBar.add(restoreButton);
        
        helpButton = new JButton(new ImageIcon("../images/help.png"));
        helpButton.setToolTipText( "Aide" );
        toolBar.add(helpButton);
        
        aboutButton = new JButton(new ImageIcon("../images/about.png"));
        aboutButton.setToolTipText("A propos de l'application");
        toolBar.add(aboutButton);
        
        toolBar.addSeparator();
        
        exitButton = new JButton(new ImageIcon("../images/exit.png"));
        exitButton.setToolTipText("Deconnecxion");
        toolBar.add(exitButton);
		
        getContentPane().add(toolBar);
        
        convergeColorSlider = new JSlider();
        convergeColorSlider.setBackground(new Color(47, 79, 79));
        convergeColorSlider.setForeground(Color.WHITE);
        convergeColorSlider.setEnabled(false);
        convergeColorSlider.setMaximum(512);
        convergeColorSlider.setValue(0);
        convergeColorSlider.setBounds(12, 234, 416, 32);
        getContentPane().add(convergeColorSlider);
        
        fractalTypeBox = new JComboBox();
        fractalTypeBox.setModel(new DefaultComboBoxModel(new String[] {"Julia", "Mandelbrot"}));
        fractalTypeBox.setSelectedIndex(0);
        fractalTypeBox.setBounds(180, 53, 120, 24);
        getContentPane().add(fractalTypeBox);
		
	}

	public JTextField getLargeur() {
		return this.width;
	}

	public void setWidth(JTextField width) {
		this.width = width;
	}

	public JTextField getLongueur() {
		return height;
	}

	public void setHeight(JTextField height) {
		this.height = height;
	}

	public JTextField getConstante() {
		return constante;
	}

	public void setConstante(JTextField constante) {
		this.constante = constante;
	}

	public JTextField getMaxIteration() {
		return maxIteration;
	}

	public void setMaxIteration(JTextField maxIteration) {
		this.maxIteration = maxIteration;
	}
	
	public JButton getDrawButton() {
		return this.drawButton;
	}

	public Controlor getControleur() {
		return controleur;
	}

	public void setControleur(Controlor controleur) {
		this.controleur = controleur;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JButton getSaveAsButton() {
		return saveAsButton;
	}

	public JButton getAboutButton() {
		return aboutButton;
	}

	public JButton getHelpButton() {
		return helpButton;
	}

	public JButton getRestoreButton() {
		return restoreButton;
	}

	public JButton getExitButton() {
		return exitButton;
	}

	public JComboBox getColorBox() {
		return colorBox;
	}

	public JButton getFavoriteButton() {
		return favoriteButton;
	}

	public JButton getMyFavoritsButton() {
		return myFavoritsButton;
	}

	public JSlider getConvergeColorSlider() {
		return convergeColorSlider;
	}

	public JComboBox getFractalTypeBox() {
		return fractalTypeBox;
	}
	
	private void aboutAction() {
		JOptionPane.showMessageDialog(null, "Fractal Generator est une application developee par CHALAL Massinissa \n"
				+ "C'est une application qui permet de creer facilement vos fractales. Vous en avez peut-etre deja entendu parler", "a propos de l'application",
				JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	private void helpAction() {
		JOptionPane.showMessageDialog(null, "Ci dessous, une liste de constantes qu'on vous suggere afin "
				+ "de generer des fractales d'ensemble de julia, essayer les et admirer!\n- 0.7269 + 0.1889i\n"
				+ "0.3 + 0.5 i\n"
				+ "0.285 + 0.01 i\n- 1.476\n"
				+ "- 1.417022285618 + 0.0099534 i\n- 0.038088 + 0.9754633 i\n 0,285 + 0,013 i\n- 0.8i\n- 1 + 0.2i\n- 0.8 + 0.4i\n- 0.6 + 0.6i"
				+ "\n0.39 + 0.4i\n- 0.8 + 0.2i\0.39 + 0.6i\n- 0.8+0.156i"
				, "Fractales suggestions",
				JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	
	
	
	
}
