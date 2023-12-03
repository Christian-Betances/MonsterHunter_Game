package Objects;

import java.awt.Color;
import java.awt.Graphics;

public class Trap extends Rect {
	
	public boolean damage = false;
	
	public int delay = 0;
	
	private int x;
	private int y;
	
	Animation trap = new Animation("Trap/Trap_", 15, 10);
	
	public Trap(int x, int y, int w, int h) {
		
		super(x, y, 100, 100);
		
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics pen) {
		
		delay++;
		
		pen.drawImage(trap.getStaticImage(), x, y, 100, 100, null);
		
		if(delay >= 40)
		pen.drawImage(trap.getCurrentImage(), x, y, 100, 100, null);
		
		if(delay >= 130) {
			
			pen.setColor(Color.RED);
			
			damage = true;
		}
		if(delay >= 190) {
			
			damage = false;
			delay = 0;
		}
		super.draw(pen);
	}

}
