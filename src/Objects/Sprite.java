package Objects;

import java.awt.Graphics;

public class Sprite extends Rect {
	
	Animation [] animation;
	
	final static int DN = 0;
	final static int LT = 1;
	final static int RT = 2;
	final static int UP = 3;
	
	final static int ATK_DN = 4;
	final static int ATK_LT = 5;
	final static int ATK_RT = 6;
	final static int ATK_UP = 7;
	
	final static int Ball0 = 1;
	
	private int pose = DN;
	
	public boolean moving = false;
	
	public boolean swordSwing = false;

	public Sprite(String name, String[] pose, int imageCount, int x, int y, int w, int h) {
		
		super(x, y, w, h);
		
		animation = new Animation[pose.length];
		
		for(int i = 0; i < pose.length; i++) {
			
			animation[i] = new Animation(name + "_" + pose[i], imageCount, 20);
			
		}	
	}
	
	public void moveUP(int dy) {

		pose = UP;
		
		this.y += -dy;
	    this.vy = -dy;
	    
	    moving = true;
	    
	  
	}

	public void moveDN(int dy) {

		pose = DN;
		
		this.y += dy;
	    this.vy = dy;
	    
	    moving = true;

	}

	public void moveLT(int dx) {
		
		pose = LT;
		
		this.x += -dx;
	    this.vx = -dx;
	    
	    moving = true;

	}

	public void moveRT(int dx) {
		
		pose = RT;
		
		this.x += dx;
	    this.vx = dx;
	    
	    moving = true;
	}
	
	public void sword() {
		
		if(pose == DN)
		pose = ATK_DN;
		
		if(pose == LT)
		pose = ATK_LT;
		
		if(pose == RT)
		pose = ATK_RT;
		
		if(pose == UP)
		pose = ATK_UP;
		
		swordSwing = true;
	}
	
	public void draw(Graphics pen) {
		
		pen.drawRect(x, y, w, h);
		
		if(!moving) {
			if(swordSwing) 
				pen.drawImage(animation[pose].getCurrentImage(), x, y, w, h, null);
		
		   else pen.drawImage(animation[pose].getStaticImage(), x, y, w, h, null);
		}
		
		else pen.drawImage(animation[pose].getCurrentImage(), x, y, w, h, null);
	}

}
