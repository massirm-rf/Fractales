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
	private JTextField Constante;
	private JPanel panneauInfo;

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
	public Fenetre(ImagePanel image) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		panneauInfo = new JPanel();
		panneauInfo.setToolTipText("");
		panneauInfo.setBackground(UIManager.getColor("textText"));
		
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		getContentPane().setLayout(new GridLayout());
		imagePane = image;
		getContentPane().add(imagePane);
		panneauInfo.setLayout(null);
		//panneauInfo.setBounds(0, 0, 800, 800);
		
		Constante = new JTextField();
		Constante.setForeground(Color.WHITE);
		Constante.setBackground(Color.WHITE);
		Constante.setBounds(470, 30, 120, 25);
		panneauInfo.add(Constante);
		Constante.setColumns(10);
		
		getContentPane().add(panneauInfo);
		
		JLabel ConstanteLabel = new JLabel("La constante");
		ConstanteLabel.setForeground(Color.WHITE);
		ConstanteLabel.setBackground(Color.WHITE);
		ConstanteLabel.setBounds(359, 30, 120, 25);
		panneauInfo.add(ConstanteLabel);
		
		JSpinner Largeur = new JSpinner();
		Largeur.setToolTipText("");
		Largeur.setBounds(470, 60, 120, 25);
		panneauInfo.add(Largeur);
		
		JLabel LargeurLabel = new JLabel("Largeur");
		LargeurLabel.setForeground(Color.WHITE);
		LargeurLabel.setBounds(359, 60, 120, 25);
		panneauInfo.add(LargeurLabel);
		
		JSpinner longueur = new JSpinner();
		longueur.setBounds(470, 90, 120, 25);
		panneauInfo.add(longueur);
		
		JLabel longueurLabel = new JLabel("Longueur");
		longueurLabel.setForeground(Color.WHITE);
		longueurLabel.setBounds(359, 90, 120, 25);
		panneauInfo.add(longueurLabel);
		
		JSpinner Itteration = new JSpinner();
		Itteration.setBounds(470, 120, 120, 25);
		panneauInfo.add(Itteration);
		
		JLabel ItterationsLabel = new JLabel("Itterations");
		ItterationsLabel.setForeground(Color.WHITE);
		ItterationsLabel.setBounds(359, 120, 120, 25);
		panneauInfo.add(ItterationsLabel);
		
		JButton CalculerButton = new JButton("Calculer");
		CalculerButton.setBounds(183, 49, 117, 25);
		panneauInfo.add(CalculerButton);
		
		JSlider redSlider = new JSlider();
		redSlider.setMaximum(255);
		redSlider.setMajorTickSpacing(25);
		redSlider.setPaintLabels(true);
		redSlider.setPaintTicks(true);
		redSlider.setBounds(300, 250, 280, 60);
		panneauInfo.add(redSlider);
		
		JSlider greenSlider = new JSlider();
		greenSlider.setMaximum(255);
		greenSlider.setMajorTickSpacing(25);
		greenSlider.setPaintLabels(true);
		greenSlider.setPaintTicks(true);
		greenSlider.setBounds(300, 350, 280, 60);
		panneauInfo.add(greenSlider);
		
		JSlider blueSlider = new JSlider();
		blueSlider.setMaximum(255);
		blueSlider.setMajorTickSpacing(25);
		blueSlider.setPaintLabels(true);
		blueSlider.setPaintTicks(true);
		blueSlider.setBounds(300, 450, 280, 60);
		panneauInfo.add(blueSlider);
		
	}
}
