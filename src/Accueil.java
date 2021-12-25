import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.io.IOException;

public class Accueil extends JFrame {

	private JPanel contentPane;
	private JButton connexionButton;
	private JButton helpButton ;
	private Controlor controleur; 

	/**
	 * Create the frame.
	 */
	public Accueil(Controlor controleur) {
		this.controleur = controleur;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 700);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton connexionButton = new JButton("Connexion");
		connexionButton.setBounds(290, 217, 120, 30);
		contentPane.add(connexionButton);
		
		JButton helpButton = new JButton("Aide");
		helpButton.setBounds(290, 302, 120, 30);
		contentPane.add(helpButton);
		
		try {
			ImagePanel backGround = new ImagePanel("../images/abstract-fractal-background-11.png");
			backGround.setBounds(0,0,700,700);
			getContentPane().add(backGround);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println (JOptionPane.showInputDialog(this, "nom") );
		
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JButton getConnexionButton() {
		return connexionButton;
	}

	public JButton getHelpButton() {
		return helpButton;
	}
	
}
