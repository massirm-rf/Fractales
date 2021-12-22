import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.List;
import java.awt.Choice;
import javax.swing.JComboBox;

public class ChoiseFrame extends JFrame {

	private JTextField width;
	private JTextField height;
	private JTextField constante;
	private JTextField maxIteration;
	private JButton drawButton;
	private Controlor controleur;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChoiseFrame window = new ChoiseFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChoiseFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		constanteLabel.setBounds(15, 90, 120, 20);
		getContentPane().add(constanteLabel);

		JLabel widthLabel = new JLabel("Largeur");
		widthLabel.setBounds(180, 90, 120, 20);
		getContentPane().add(widthLabel);

		JLabel heightLabel = new JLabel("Longueur");
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
		IterationsLabel.setBounds(33, 153, 120, 25);
		getContentPane().add(IterationsLabel);

		JLabel colorLabel = new JLabel("Couleurs");
		colorLabel.setBounds(337, 153, 70, 25);
		getContentPane().add(colorLabel);

		this.controleur = new Controlor(this);
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
	
	
	
	
}
