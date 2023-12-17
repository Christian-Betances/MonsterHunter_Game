package Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ImageLayer {
	
	Image image;
	
	int x;
	int y;
	int w;
	int h;
	
	public ImageLayer(String name, int x, int y, int w, int h) {
		
		image = Toolkit.getDefaultToolkit().createImage(name);
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void draw(Graphics pen) {
		
		pen.drawImage(image, x, y, w, h, null);
	}

}
