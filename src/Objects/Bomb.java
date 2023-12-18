package Objects;

import java.awt.Graphics;

public class Bomb extends Rect {
	
	public boolean dropBomb = false;
	private boolean following = true;
	
	public int delay = 0;
	public boolean wallDestroy = false;
	
	Animation bombIdle = new Animation("Bomb/Bomb_Idle", 2, 5);
	Animation bombJump = new Animation("Bomb/Bomb_Jump", 2, 5);
	Animation bombExplode= new Animation("Bomb/Bomb_EX", 4, 5);
	
	public Bomb(int x, int y, int w, int h) {
		
		super(x, y, w, h);
	}
	
	public void BombLocation(int x, int y) {
		
		if(following) {
			
		super.setX(x - 30);
		super.setY(y);
		}
	}
	
	public void draw(Graphics pen) {
		
		super.draw(pen);

		if(dropBomb) {
			following = false;
			delay ++;
			
			if(delay <= 40) {
			
			pen.drawImage(bombIdle.getCurrentImage(), getX(), getY(), w, h, null);
		}
		if(delay >= 40 && delay <=70) {
			
			delay++;
			pen.drawImage(bombJump.getCurrentImage(), getX(), getY(), w, h, null);
			}
		if(delay >= 70) {
			pen.drawImage(bombExplode.getCurrentImage(), getX(), getY(), w, h, null);
			
			}
		if(delay >= 80) {
			
			delay = 0;
			following = true;
			dropBomb = false;
		}
	}
	}
}
