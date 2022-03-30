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
	public Accueil() {
		
		setResizable(false);
		setTitle("Fractal Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 700);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		connexionButton = new JButton("Connexion");
		connexionButton.setBounds(290, 217, 120, 30);
		contentPane.add(connexionButton);
		
		JButton helpButton = new JButton("A propos");
		helpButton.setBounds(290, 302, 120, 30);
		contentPane.add(helpButton);
		
		this.controleur = new Controlor(this);
		if(controleur == null)System.out.println("controleur is null");
		
		try {
			ImagePanel backGround = new ImagePanel("../images/accueil-back5.jpeg");
			backGround.setBounds(0,0,700,700);
			getContentPane().add(backGround);
		} catch (IOException e) {
			e.printStackTrace();
		}
		helpButton.addActionListener(l->aProposAction());
		
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
	
	private void aProposAction() {
			JOptionPane.showMessageDialog(null, "Fractal Generator est une application developee par Mr CHALAL Massinissa \n"
					+ "C'est une application qui permet de creer facilement vos fractales. Vous en avez peut-etre deja entendu parler."
					+ "\nConnectez-vous et admirez !!", "a propos de l'application",
					JOptionPane.INFORMATION_MESSAGE, null);
	}
	
}
