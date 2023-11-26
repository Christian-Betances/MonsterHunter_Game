package Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ImageLayer {
	
	Image image;
	
	int x;
	int y;
	
	public ImageLayer(String name, int x, int y) {
		
		image = Toolkit.getDefaultToolkit().createImage(name);
		
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics pen) {
		
		pen.drawImage(image, x, y, 800, 800, null);
	}

}
