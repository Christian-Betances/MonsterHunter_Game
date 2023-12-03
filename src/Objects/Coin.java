package Objects;

import java.awt.Graphics;

public class Coin extends Rect {
	
	private int balance;
	
	Animation spin = new Animation("Coin/coin", 7, 3);
	
	public Coin(int x, int y, int w, int h) {
		
		super(x, y, w, h);
		
		balance = 500;
	}
	
	public void buy(int x) {
		
		if(balance > 0 && balance - x >= 0)
		balance -= x;
	}
	
	public int getBalance() {
		
		return balance;
	}
	
	public void increaseBalance() {
		
		balance += 50;
	}
	
	public String coinBalance() {
		
		return "Currency | " + balance;
	}
	
	public void draw(Graphics pen) {
		
//		super.draw(pen);
		
		pen.drawImage(spin.getCurrentImage(),x, y, w, h, null);
	}

}
