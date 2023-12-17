package Characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Objects.Animation;
import Objects.Bomb;
import Objects.Coin;

import Objects.HealthBar;
import Objects.ManaBar;
import Objects.Rect;
import Objects.Sprite;

public class Hero extends Sprite {
	
	private static String [] pose = {"DN", "LT", "RT", "UP","ATK_DN","ATK_LT","ATK_RT","ATK_UP"};
	
	private HealthBar health = new HealthBar(250, 50, 10, 50);
	private ManaBar mana = 	   new ManaBar(200, 50, 70, 50);
	
	public int falling = 0;
	
	private Coin coin;
	private Bomb bomb;
	
	//move hero to location
	public boolean moveLeft = false;
	public boolean moveRight= false;
	public boolean moveAbove= false;
	public boolean moveBelow = false;

	//combat
	public boolean showHud = false;
	public boolean inBattle = false;
	public boolean animationDone = false;
	public boolean shoot = false;
	
	private int distance = 0;
	private int delay = 0;
	
	//location
	private boolean down = false;
	private boolean up = false;
	private boolean right = false;
	private boolean left = false;
	
	Animation back = new Animation("BOH/Hero_ATK_UP", 3, 20);
	
	Animation heal = new Animation("Heal/Heal", 24, 3);
	
	Animation fire = new Animation("Fire/Fire_Ball", 14, 20);
	
	public Animation flare = new Animation("Flare/Solar_Flare", 36, 3);
	
	//abilities
	public boolean slash = false;
	public boolean healHealth = false;
	public boolean fireAttack = false;
	public boolean flareAttack = false;
	
	private Rect swordHitbox;
	
	private int swordWidth = 40;
	private int swordHeight = 40;
	
	private int originalW = 70;
	private int originalH = 70;
	
	public Hero(int x, int y) {
		
		super("Hero/Hero", pose, 3, x, y, 70, 70);

		mana.setMana(200);
		
		swordHitbox = new Rect(getX(), getY(), swordWidth, swordHeight);
	}
	
	public boolean swordSwing(Rect r) {
		
		if(r.overlaps(swordHitbox)) return true;
		
		return false;
	}
	
	public void setSwordLocation() {
		
	    if(getPose() == DN) {
	        
	    	down = true;
	    	up = false;
	    	left = false;
	    	right = false;
	    	
	        swordHitbox.setX(getX() + 15);
	        swordHitbox.setY(getY() + 50);
	    }
	    if(getPose() == LT) {
	    	
	    	down = false;
	    	up = false;
	    	left = true;
	    	right = false;
	        
	        swordHitbox.setX(getX() - 10);
	        swordHitbox.setY(getY() + 30);
	    }
	    if(getPose() == RT) {
	    	
	    	down = false;
	    	up = false;
	    	left = false;
	    	right = true;

	    	swordHitbox.setX(getX() + 30);
	        swordHitbox.setY(getY() + 30);
	    }
	    if(getPose() == UP) {
	    	
	    	down = false;
	    	up = true;
	    	left = false;
	    	right = false;
	    	
	    	swordHitbox.setX(getX() + 10);
	        swordHitbox.setY(getY() - 5);
	    }   
	}
	
	public void shootAttack() {
		
	    if (shoot) {

	    	delay++;
	    	if(down || up) {
	    	if(down) distance += 20;
	    	if(up) distance -= 20;
	    	
	    	swordHitbox.setY(swordHitbox.getY() + distance);
	    	}
	    	if(right || left) {
		    if(right) distance += 20;
		    if(left) distance -= 20;
		    	
		    swordHitbox.setX(swordHitbox.getX() + distance);
		    }
	    }
	    if (delay >= 20) {
    		
            distance = 0;
            delay = 0;
            shoot = false;
            
            if(down) {
            swordHitbox.setX(getX() + 15);
	        swordHitbox.setY(getY() + 50);
            }
	        if(up) {
	        swordHitbox.setX(getX() + 10);
		    swordHitbox.setY(getY() - 5);
	        }
	        if(right) {
	        swordHitbox.setX(getX() + 30);
		    swordHitbox.setY(getY() + 30);
	        }
	        if(left) {
	        swordHitbox.setX(getX() - 10);
	 	    swordHitbox.setY(getY() + 30);
	        }
        }
	}

	public void heroFalling() {
		
		falling++;
		
		if(falling <= 36 && getW() >= 0 && getH() >= 0) {
			
			int shrink = getW() - 2;
			
			super.setW(shrink);
			super.setH(shrink);			
		}
		
		else {
			
			if(moveLeft) super.setX(super.getX() - 100);
			
			if(moveRight) super.setX(super.getX() + 50);
			
			if(moveBelow) super.setY(super.getY() + 50);
			
			if(moveAbove) super.setY(super.getY() - 100);
			
			super.setW(originalW);
			super.setH(originalH);
			
			falling = 0;
		}
	}

	public void setLocation(int x, int y) {
		
		super.setX(x);
		super.setY(y);
	}
	
	public void getBomb(Bomb bomb) {
		
		this.bomb = bomb;
	}
	
	public void dropBomb() {
		 
		bomb.dropBomb = true;
	}
	
	public void heroDamage(int x) {
		
		health.damage(x);
	}
	
	public void increaseHealth(int x) {
		
		health.increaseHealth(x);
	}
	
	public void increaseMax() {
		
		health.increaseMaxHealth();
	}
	
	public int heroMana() {
		
		return mana.getMana();
	}
	
	public void increaseMaxMana(int x) {
		
		mana.increaseMaxMana(x);
	}
	
	public void increaseMana(int x) {
		
		mana.increaseMana(x);
	}
	
	public void decreaseMana(int x) {
		
		mana.decreaseMana(x);
	}
	
	public void getCoin(Coin coin) {
		
		this.coin = coin;
	}

	public void bought(int x) {
		
		coin.buy(x);
	}
	
	public void getMoney() {
		
		coin.increaseBalance();
	}
	
	public boolean alive() {
		
		return health.getHealth() > 0;
	}
	
	public void draw(Graphics pen) {
		swordHitbox.setColor(Color.GREEN);
		swordHitbox.draw(pen);
		
		setSwordLocation();
		shootAttack();
		
		if(alive()) {
			
		if (!showHud) {
			
			super.draw(pen);
			
			coin.draw(pen);
			
			pen.setColor(Color.ORANGE);
			pen.setFont(new Font("Arial", Font.PLAIN, 20));
			pen.drawString(coin.coinBalance(), 1750, 40);
			
		}
		
		if(showHud) {
			
			if(slash) {
				pen.drawImage(back.getCurrentImage(), 200, 200, 500, 500, null);
				
				if(back.animationFinish() || !showHud) slash = false;
			}
			
			if(healHealth) {
				
				pen.drawImage(back.getStaticImage(), 200, 200, 500, 500, null);
				pen.drawImage(heal.getCurrentImage(), 50, -100, 800, 500, null);
				
				if(heal.animationFinish() || !showHud) healHealth = false;
				
			}
			
			if(fireAttack) {
				
				for(int i = 0; i < 10; i++) 
				pen.drawImage(fire.getCurrentImage(), 10 + (i * 100), 100, 300, 300, null);
						
				pen.drawImage(back.getCurrentImage(), 200, 200, 500, 500, null);
				
				if(fire.animationFinish() || !showHud) fireAttack = false;
				
			}
			
			if(flareAttack) {
				
				pen.drawImage(flare.getCurrentImage(), 200, -500, 1500, 1500, null);
				pen.drawImage(back.getStaticImage(), 200, 200, 500, 500, null);
				
				if(flare.animationFinish() || !showHud) flareAttack = false;
			}
			
			else if(!slash && !healHealth && !fireAttack && !flareAttack) 
				pen.drawImage(back.getStaticImage(), 200, 200, 500, 500, null);
		}
			
		health.draw(pen);
		mana.draw(pen);
		
		pen.setColor(Color.WHITE);
		pen.setFont(new Font("Arial", Font.PLAIN, 20));
		pen.drawString(mana.showMana(), 50, 100);
		
		pen.setColor(Color.WHITE);
		pen.setFont(new Font("Arial", Font.PLAIN, 20));
		pen.drawString(health.showHealth(), 50, 40);
	}
		else {
			pen.setColor(Color.RED);
			pen.setFont(new Font("Arial", Font.PLAIN, 100));
			pen.drawString("You're Dead!", 500, 400);
			
		}
	}
}
