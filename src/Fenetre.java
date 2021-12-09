import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.TextField;
import java.io.IOException;
import java.awt.Label;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.UIManager;

public class Fenetre extends JFrame {

	private ImagePanel imagePane;
	private JPanel panneauInfo;
	
	private JTextField constante;
	private JTextField largeur;
	private JTextField longueur;
	private JSpinner itterations;
	private JLabel constanteLabel;
	private JLabel LargeurLabel;
	private JLabel longueurLabel;
	private JLabel itterationsLabel;
	private JSlider redSlider;
	private JSlider greenSlider;
	private JSlider blueSlider;
	private JButton CalculerButton;
	private Controleur controleur;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fenetre frame = new Fenetre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Fenetre(/*ImagePanel image*/) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		panneauInfo = new JPanel();
		panneauInfo.setToolTipText("");
		panneauInfo.setBackground(UIManager.getColor("textText"));
		
		//this.imagePane = new ImagePanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		//getContentPane().setLayout(new GridLayout());
		getContentPane().setLayout(null);
		//imagePane = image;
		
		
		panneauInfo.setBounds(846, 0, 400, 800);
		panneauInfo.setLayout(null);
		//panneauInfo.setBounds(0, 0, 800, 800);
	    try {
			imagePane = (new ImagePanel("abstract-fractal-background-11.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		constante = new JTextField(10);
		constante.setText("-0.7269+0.1889i");
		//constante.setForeground(Color.WHITE);
		//constante.setBackground(Color.WHITE);
		constante.setBounds(220, 30, 120, 25);
		panneauInfo.add(constante);
		constante.setColumns(10);
		
		getContentPane().add(panneauInfo);
		
		constanteLabel = new JLabel("La constante");
		constanteLabel.setForeground(Color.WHITE);
		constanteLabel.setBackground(Color.WHITE);
		constanteLabel.setBounds(100, 30, 120, 25);
		panneauInfo.add(constanteLabel);
		
	    largeur = new JTextField();
	    largeur.setText("600");
		largeur.setToolTipText("");
		largeur.setBounds(220, 60, 120, 25);
		panneauInfo.add(largeur);
		
		LargeurLabel = new JLabel("Largeur");
		LargeurLabel.setForeground(Color.WHITE);
		LargeurLabel.setBounds(100, 60, 120, 25);
		panneauInfo.add(LargeurLabel);
		
		longueur = new JTextField();
		longueur.setText("600");
		longueur.setBounds(220, 90, 120, 25);
		panneauInfo.add(longueur);
		
		longueurLabel = new JLabel("Longueur");
		longueurLabel.setForeground(Color.WHITE);
		longueurLabel.setBounds(100, 90, 120, 25);
		panneauInfo.add(longueurLabel);
		
		itterations = new JSpinner();
		itterations.setBounds(220, 120, 120, 25);
		panneauInfo.add(itterations);
		
		itterationsLabel = new JLabel("Itterations");
		itterationsLabel.setForeground(Color.WHITE);
		itterationsLabel.setBounds(100, 120, 120, 25);
		panneauInfo.add(itterationsLabel);
		
		CalculerButton = new JButton("Calculer");
		CalculerButton.setBounds(156, 159, 117, 25);
		panneauInfo.add(CalculerButton);
		
		redSlider = new JSlider();
		redSlider.setMaximum(255);
		redSlider.setMajorTickSpacing(25);
		redSlider.setPaintLabels(true);
		redSlider.setPaintTicks(true);
		redSlider.setBounds(60, 250, 280, 60);
		panneauInfo.add(redSlider);
		
		greenSlider = new JSlider();
		greenSlider.setMaximum(255);
		greenSlider.setMajorTickSpacing(25);
		greenSlider.setPaintLabels(true);
		greenSlider.setPaintTicks(true);
		greenSlider.setBounds(60, 350, 280, 60);
		panneauInfo.add(greenSlider);
		
		blueSlider = new JSlider();
		blueSlider.setMaximum(255);
		blueSlider.setMajorTickSpacing(25);
		blueSlider.setPaintLabels(true);
		blueSlider.setPaintTicks(true);
		blueSlider.setBounds(60, 450, 280, 60);
		panneauInfo.add(blueSlider);
		
		this.controleur = new Controleur(this);
		imagePane.setBounds(0, 0, 846, 800);
		getContentPane().add(imagePane);
		
	}

	public ImagePanel getImagePane() {
		return imagePane;
	}

	public JTextField getConstante() {
		return constante;
	}

	public JPanel getPanneauInfo() {
		return panneauInfo;
	}


	public JTextField getLargeur() {
		return largeur;
	}

	public JTextField getLongueur() {
		return longueur;
	}

	public JSpinner getItterations() {
		return itterations;
	}

	public JLabel getConstanteLabel() {
		return constanteLabel;
	}

	public JLabel getLargeurLabel() {
		return LargeurLabel;
	}

	public JLabel getLongueurLabel() {
		return longueurLabel;
	}

	public JLabel getItterationsLabel() {
		return itterationsLabel;
	}

	public JSlider getRedSlider() {
		return redSlider;
	}

	public JSlider getGreenSlider() {
		return greenSlider;
	}

	public JSlider getBlueSlider() {
		return blueSlider;
	}

	public JButton getCalculerButton() {
		return CalculerButton;
	}
	
	
	
	
}
