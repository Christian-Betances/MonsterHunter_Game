package Characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Objects.Animation;
import Objects.CombatHud;
import Objects.HealthBar;
import Objects.Rect;

public class Mimic extends Rect{
	
	Animation mimicIdle = new Animation("Mimic/mimic_idle", 20, 10);
	
	Animation mimicATK = new Animation("Mimic_ATK/mimic_Atk", 10, 10);
	
	CombatHud hud = new CombatHud();
	
	HealthBar health = new HealthBar(300, 870, 200, 20);
	
	private int x;
	private int y;
	
	private boolean damage = false;
	
	public Rect onMimic;
	
	public boolean openMimic;
	public int delay = 0;
	
	public Mimic(int x, int y, int w, int h) {
		
		super(x + 25, y, w, h);
		
		this.x = x;
		this.y = y;
		
		onMimic = new Rect(x + 5, y + 40, 50, 50);
		
		health.setHealth(100);
	}
	
	public int getHealth() {
		
		return health.getHealth();
	}
	
	public void damage(int x) {
		
		health.damage(x);
		
		damage = true;
	}
	
	public void openChest(Graphics pen) {
		
		pen.setColor(Color.WHITE);
		
		pen.drawString("Press E to Open", x - 30, y);
	}
	
	public void draw(Graphics pen) {
		
		if(health.getHealth() >= 0) {
		   if (!hud.showHud) {
		        super.draw(pen);
		        onMimic.draw(pen);
		        pen.drawImage(mimicIdle.getCurrentImage(), x, y, 60, 60, null);
		    }

		    if (hud.showHud) {
		    	
		    	if(damage) {
		    		
		    		delay++;
		    		
		    		if(delay >= 100) 
		    		pen.drawImage(mimicATK.getCurrentImage(), 870, 120, 300, 300, null);
		    		
		    		
		    		else  pen.drawImage(mimicIdle.getCurrentImage(), 870, 120, 300, 300, null);
		    		
		    		if(delay >= 200) {
		    			
		    			damage = false;
		    			delay = 0;
		    		}
		    	}
		    	else  pen.drawImage(mimicIdle.getCurrentImage(), 870, 120, 300, 300, null);
		    	
		        pen.setColor(Color.WHITE);
		        pen.setFont(new Font("Arial", Font.PLAIN, 20));
		        pen.drawString(health.showHealth(), 870, 190);
		        health.draw(pen);
		    }
		}
	}

}
