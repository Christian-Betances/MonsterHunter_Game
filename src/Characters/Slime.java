package Characters;

import java.awt.Graphics;

import Objects.Animation;
import Objects.Rect;

public class Slime extends Rect {
	
	public boolean attack = false;
	private boolean dead = false;
	
	Animation chase = new Animation("Slime/SlimeChase", 6, 10);
	Animation destroy = new Animation("Slime/Slime_Destroy", 6, 10);
	
	private int w;
	private int h;
	
	public Rect sight;
	
	public Slime(int x, int y, int w, int h) {
		
		super(x, y, w, h);
		
		this.w = w;
		this.h = h;
		
		sight = new Rect(x, y, 100, 300);
	}
	
	public void draw(Graphics pen) {
		
//		sight.draw(pen);
		if(!dead && !attack) {
			
		pen.drawImage(chase.getCurrentImage(), getX(), getY(), w, h, null);
		super.draw(pen);
		}
		if(attack && !dead) {
			pen.drawImage(destroy.getCurrentImage(), getX(), getY(), w, h, null);
		
		if(destroy.animationFinish()) dead = true;
		}
	}

}
