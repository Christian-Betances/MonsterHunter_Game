package Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HealthBar extends Rect {
	
	private int maxHealth;
	private int currentHealth;
	
	private int x;
	private int y;
	private int h;
	
	public HealthBar(int maxHealth, int x, int y, int h) {
		
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		
		this.x = x;
		this.y = y;
		this.h = h;
	}
	
	public String showHealth() {
		
		return "Health:" + currentHealth + "/" + maxHealth;
	}
	
	public void setHealth(int health) {
		
		currentHealth = health;
	}
	
	public void increaseMaxHealth() {
		
		maxHealth = 300;
	}
	
	public int getHealth() {
		
		return currentHealth;
	}
	
	public void damage(int atkDamage) {
		
		if(currentHealth > 0) currentHealth -= atkDamage;
	}
	
	public void increaseHealth(int heal) {
		
		if(currentHealth < maxHealth)
			
		currentHealth += heal;
	}
	
	public void draw(Graphics pen) {
		
		pen.setColor(Color.RED);
		
		for(int i = 0; i < currentHealth; i++) {
			
			pen.fillRect(x + i, y, 1, h);
			
		}

	}

}
