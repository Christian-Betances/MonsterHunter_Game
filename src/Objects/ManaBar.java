package Objects;

import java.awt.Color;
import java.awt.Graphics;

public class ManaBar extends Rect{
	
	private int maxMana;
	private int currentMana;
	
	private int x;
	private int y;
	private int h;
	
	public ManaBar(int maxMana, int x, int y, int h) {
		
		this.maxMana = maxMana;
		this.currentMana = maxMana;
		
		this.x = x;
		this.y = y;
		this.h = h;
	}
	
	public String showMana() {
		
		return "Mana:" + currentMana + "/" + maxMana;
	}
	
	public void setMana(int mana) {
		
		currentMana = mana;
	}
	
	public int getMana() {
		
		return currentMana;
	}
	
	public void increaseMaxMana(int mana) {
		
		maxMana += mana;
	}
	
	public void increaseMana(int mana) {
		
		if(currentMana < maxMana) currentMana += mana;
	}
	
	public void decreaseMana(int mana) {
		
		if(currentMana > 0) currentMana -= mana;
	}
	
	public void draw(Graphics pen) {
		
		pen.setColor(Color.CYAN);
		
		for(int i = 0; i < maxMana; i++) {
			
			pen.fillRect(x + i, y, 1, h);
			
		}
		
		pen.setColor(Color.BLUE);
		
		for(int i = 0; i < currentMana; i++) {
			
			pen.fillRect(x + i, y, 1, h);
			
		}
	}
}
