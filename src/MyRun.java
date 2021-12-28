import java.awt.Color;
import java.awt.image.BufferedImage;

public class MyRun implements Runnable {

	private int startX,startY,endX,endY;
	private BufferedImage image;
	private Controlor controleur;
	
	public MyRun(int startX, int startY, int endX, int endY, BufferedImage image,Controlor controleur) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.image = image;
		this.controleur = controleur;
	}


	@Override
	public void run() {
		//int divergeRgb = controleur.getChoiseFrame().getDivergeColorSlider().getValue();
		int colorMode = controleur.getChoiseFrame().getColorBox().getSelectedIndex();
		int convergeRgb = controleur.getChoiseFrame().getConvergeColorSlider().getValue();
		int[][]colors = controleur.getColor();
		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++) {
				Julia julia =controleur.getJulia();
				Complex z = julia.toComplex(new Point(i, j));
				int it = julia.diverfgenceIndex2(z);
				if (it >= julia.getIterations()) {
					image.setRGB(i, j, (0 << 16) + (0 << 8) + 0);
					
				} else {
					int rgb;
					if(colorMode==0)rgb= (it << 16) + (it << 8) + it;
					else if(colorMode ==1) rgb = Color.HSBtoRGB((float)it/julia.getIterations(), 0.7f, 0.7f);
					else rgb = colors[200][(it+convergeRgb+131)%512];
					image.setRGB(i, j, rgb);
				}
			}
		}
		//controleur.getFractalPane().getImagePane().repaint();
		 //throw new RuntimeException();
	}

}
