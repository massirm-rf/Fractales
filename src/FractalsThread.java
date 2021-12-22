import java.awt.image.BufferedImage;

public class FractalsThread extends Thread {
	
	private int startX,startY,endX,endY;
	private BufferedImage image;
	
	
	public FractalsThread(int startX, int startY, int endX, int endY, BufferedImage image) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.image = image;
	}


	@Override
	public void run() {
		
		}
	
	
	
	
	

}
