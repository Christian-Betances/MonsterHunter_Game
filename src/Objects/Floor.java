package Objects;

import java.awt.Color;
import java.awt.Graphics;

public class Floor extends Rect {

	public Floor(int x, int y, int w, int h) {
		
		super(x,y,w,h);
	}
	
	public void draw(Graphics pen) {
		
		super.setColor(Color.GREEN);
		super.draw(pen);
	}
}
