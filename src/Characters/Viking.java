package Characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import Objects.Animation;
import Objects.CombatHud;
import Objects.HealthBar;
import Objects.Rect;

public class Viking extends Rect{
	
	public Rect sight;
	
	private int initialX;
	private int initialY;
	private int healthReset = 0;
	
	public int delay = 0; 
	
	private boolean movingRight = true;
	private boolean facingHeroRT = false;
	private boolean facingHeroLT = false;
	
	private boolean damage = false;
	
	public boolean inFight = false;
	public boolean slashAtk = false;
	public boolean throwAtk = false;
	
	private Random random = new Random();
	private boolean handleRandom = false;
	
	public boolean turnOnDelay = false;
	public boolean showHud = false;
	private boolean defeated = false;
	
	HealthBar health = new HealthBar (150, 870, 200, 20);
	CombatHud hud;
	
	Animation vikingRT = new Animation ("Viking_Move/Viking_RT", 8, 10);
	Animation vikingLT = new Animation ("Viking_Move/Viking_LT", 8, 10);
	Animation vikingID = new Animation ("Viking_Move/Viking_Idle", 10, 10);
	
	Animation vikingAtk = new Animation("Viking_ATK/Viking_Atk", 10, 10);
	Animation vikingThrow = new Animation("Viking_ATK/Viking_ATK_T", 10, 10);
	
	public Viking(int x, int y, int w, int h) {
		
		super(x,y,w,h);
		
		initialX = x;
		initialY = y;
		
		sight = new Rect(initialX - 80, initialY, 200, 50);
		
		health.setHealth(10);
	}
	
	public void setLocation(int x, int y) {
		
		super.setX(x);
		super.setY(y);
	}
	
	public void damage(int x) {
		
		health.damage(x);
		damage = true;
	}
	
	public int getHealth() {
		
		return health.getHealth();
	}
	
	public void resetHealth() {
		
		healthReset++;
		if(healthReset == 1)
		health.resetHealth();
	}
	
	public void patrol() {
		
		if(movingRight) {
			
			super.moveRT(1);
			
			if(getX() >= sight.getX() + sight.getW() - 50) movingRight = false;
				
		}
		else {
			
			super.moveLT(1);
			
			if(getX() <= sight.getX()) movingRight = true;
		}	
	}
	
	public void chaseDirection(Rect r) {
		
        if (r.getX() >= getX() && facingHeroRT) {
        	
            super.chase(r);  
        } 
        
        else if (r.getX() <= getX() && facingHeroLT) {
        	
            super.chase(r);   
        }
        
        else patrol();
    }
	
	public void randomAttack() {
		
		if(handleRandom) {
			
			int randomNumber = random.nextInt(2);
			
			if(randomNumber == 0) {
				slashAtk = true;
				throwAtk = false;
			}
			
			if(randomNumber == 1) {
				throwAtk = true;
				slashAtk = false;
			}
			handleRandom = false;
		}
	}
	
	public void draw(Graphics pen) {
		
		super.draw(pen);
		
		if(!defeated) {
			
		if(!showHud) {
			
		if(movingRight) {
		pen.drawImage(vikingRT.getCurrentImage(), getX() - 10, initialY - 50, 100, 100, null);
		
		facingHeroRT = true;
		facingHeroLT = false;
		}
		if(!movingRight) {
		pen.drawImage(vikingLT.getCurrentImage(), getX() - 25, initialY - 50, 100, 100, null);
		
		facingHeroLT = true;
		facingHeroRT = false;
		}
		
		pen.setColor(Color.RED);
		sight.draw(pen);
		}
		
		if(showHud) {
			
		inFight = true;
		
		if(damage) {
			
			randomAttack();
			
				delay++;
				
				if(slashAtk) {
					
					if(delay >= 90)
					pen.drawImage(vikingAtk.getCurrentImage(), 870, 120, 300, 300, null);

					else 
						pen.drawImage(vikingID.getCurrentImage(), 870, 120, 300, 300, null);
				}
				if(throwAtk) {
				
				if(delay >= 90)
					pen.drawImage(vikingThrow.getCurrentImage(), 870, 120, 300, 300, null);
				
				else 
					pen.drawImage(vikingID.getCurrentImage(), 870, 120, 300, 300, null);
				}
				if(delay >= 250) {
				
				damage = false;
				handleRandom = true;
				delay = 0;
				}
			
		}
		else pen.drawImage(vikingID.getCurrentImage(), 870, 120, 300, 300, null);
		
			
			pen.setColor(Color.WHITE);
			pen.setFont(new Font("Arial", Font.PLAIN, 20));
			pen.drawString(health.showHealth(), 870, 190);
			
			health.draw(pen);
			
			}
		}
		if(health.getHealth() <= 0) {
			defeated = true;
			
		}
	}
}
