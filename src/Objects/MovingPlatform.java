package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class MovingPlatform extends Rect {
	
	Image platform = Toolkit.getDefaultToolkit().getImage("MovingPlatform/Moving_Platform.png");
	 
	private boolean goingUP = true;
	public int delay = 0;
	
	private Rect movingLocation;
	public Rect onPlatform = new Rect();
	 
	public MovingPlatform(int x, int y, int w, int h) {
		
		super(x, y, w, h);
		
		movingLocation = new Rect(x, y, w, 530);
	}
	
	public void move() {
		
		onPlatform = new Rect(getX() + 50, getY() + 45, 110, 80);
		
		if(goingUP) {
			if(getY() <= movingLocation.getY()) {
				delay++;
				if(delay >= 300) {
				goingUP = false;
				delay = 0;
				}
			}
			else moveUP(2);
		}
		else if(getY() + getH() >= movingLocation.getY() + movingLocation.getH()) {
				delay++;
				if(delay >= 300) {
				goingUP = true;
				delay = 0;
				}
			}
			else moveDN(2);
		}
		
		
	public void draw(Graphics pen) {
		
		move();
		super.setColor(Color.BLUE);
		super.draw(pen);
		
		movingLocation.setColor(Color.RED);
		movingLocation.draw(pen);
		
		pen.drawImage(platform, getX(), getY(), getW(), getH(), null);
		
		onPlatform.setColor(Color.YELLOW);
		onPlatform.draw(pen);
	}
}
