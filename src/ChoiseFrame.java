import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;

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
	private Controlor controleur;


	/**
	 * Create the application.
	 */
	public ChoiseFrame() {
		getContentPane().setForeground(Color.WHITE);
		getContentPane().setBackground(Color.DARK_GRAY);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Fractal Generator");
		setBounds(0, 0, 450, 300);
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
		constante.setText("-0.7269+0.1889i");
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
		drawButton.setBounds(163, 180, 117, 25);
		getContentPane().add(drawButton);

		maxIteration = new JTextField();
		maxIteration.setText("255");
		maxIteration.setBounds(10, 180, 120, 25);
		getContentPane().add(maxIteration);
		maxIteration.setColumns(10);

		JComboBox colorBox = new JComboBox();
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
		
		initialiserToolBar();
		this.controleur = new Controlor(this);
		
	}
	
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
        exitButton.setToolTipText("Exit (ALT+F4) ");
        toolBar.add(exitButton);
		
        getContentPane().add(toolBar);
		
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
}
