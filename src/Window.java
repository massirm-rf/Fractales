import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame{
	
	ImagePanel image ;
	
	JLabel constante,width,height,pas,maxIterations;
	JTextField constantField,widthField,heightField,pasField,maxIterationsField;
	
	public Window(ImagePanel image) {
		this.setTitle("Generateur de Fractales");
		
		setSize(image.getImage().getWidth(),image.getImage().getWidth());
		getContentPane().add(image);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
}
