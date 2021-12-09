import java.awt.Color;
import java.awt.image.BufferedImage;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Julia julia = new Julia(new Complex(-0.7269,0.1889),1000,1000,1000);
		var img=new BufferedImage(julia.getWidth(), julia.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int i =0;i<img.getWidth();i++) {
			for(int j = 0;j<img.getHeight();j++) {
				Complex z = julia.toComplex(i, j);
				int it = julia.diverfgenceIndex(z);
				//System.out.println(it);
				if(it>=1000) {
					img.setRGB(i,j,(0 << 16) + (0 << 8) + 0);
				}else {
					int rgb=Color.HSBtoRGB((float)it/1000, 0.7f, 0.7f);
					img.setRGB(i,j, rgb);
					
				}
			}
         }*/
		//ImagePanel imagePanel = new ImagePanel(img);
		Fenetre f = new Fenetre();
		f.setVisible(true);
		
	}

}
