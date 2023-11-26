package Objects;

import java.awt.Graphics;

public class Coins extends Rect {
	
	private int balance;
	
	public Coins(int x, int y, int w, int h) {
		
		super(x, y, w, h);
		
		balance = 500;
	}
	
	public void buy(int x) {
		
		if(balance > 0)
		balance -= x;
	}
	
	public int getBalance() {
		
		return balance;
	}
	
	public String coinBalance() {
		
		return "Currency | " + balance;
	}
	
	public void draw(Graphics pen) {
		
		super.draw(pen);
	}

}
