package Objects;

import java.awt.Color;
import java.awt.Graphics;

public class Chest extends Rect{
	
	//Add chest
	Animation chest = new Animation("Chest/Chest", 6, 10);
	
	private int x;
	private int y;
	private int w;
	private int h;
	
	public boolean open = false;
	
	private boolean gotCoin = false;
	
	public Chest(int x, int y, int w, int h) {
		
		super(x, y, w, h);
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setLocation(int x, int y) {
		
		super.setX(x);
		super.setY(y);
	}
	
	public void openChest(Graphics pen) {
		
		pen.setColor(Color.WHITE);
		
		pen.drawString("Press E to Open", x - 30, y);
	}
	
	public void draw(Graphics pen) {
		
		super.draw(pen);
		
		if(!open && !gotCoin)
		pen.drawImage(chest.getStaticImage(), x, y, w, h, null);
		
		if(open) {
			pen.drawImage(chest.getCurrentImage(), x, y, w, h, null);
		
			if(chest.animationFinish()) {
		
				open = false;
				gotCoin = true;
			}
			}
		else if(!open && gotCoin) pen.drawImage(chest.getFinalImage(), x, y, w, h, null);
	}
}
