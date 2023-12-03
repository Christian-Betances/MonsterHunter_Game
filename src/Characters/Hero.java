package Characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Objects.Animation;
import Objects.Coin;
import Objects.CombatHud;
import Objects.HealthBar;
import Objects.ManaBar;
import Objects.Sprite;

public class Hero extends Sprite {
	
	private static String [] pose = {"DN", "LT", "RT", "UP","ATK_DN","ATK_LT","ATK_RT","ATK_UP"};
	
	private HealthBar health = new HealthBar(250, 50, 10, 50);
	private ManaBar mana = 	   new ManaBar(200, 50, 70, 50);
	
	private Coin coin;
	
	private CombatHud hud;

	public boolean inBattle = false;
	public boolean animationDone = false;
	
	Animation back = new Animation("BOH/Hero_ATK_UP", 3, 20);
	
	Animation heal = new Animation("Heal/Heal", 24, 3);
	//check fire ball animation and make images 1,2,3 instead of 01,02,03
	Animation fire = new Animation("Fire/Fire_Ball", 14, 20);
	
	Animation flare = new Animation("Flare/Solar_Flare", 36, 3);
	
	//abilities
	public boolean slash = false;
	public boolean healHealth = false;
	public boolean fireAttack = false;
	public boolean flareAttack = false;
	
	public Hero(int x, int y) {
		
		super("Hero/Hero", pose, 3, x, y, 70, 70);
		
		//testing heal
		health.setHealth(100);
		
		mana.setMana(100);
	}
	
	public void setLocation(int x, int y) {
		
		super.setX(x);
		super.setY(y);
	}
	
	public void damageWolf(Wolf wolf) {
		
		//Fix out of battle attack
        
		if(!inBattle)
        wolf.damage(10);     
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
		
		if(alive()) {
			
		if (!inBattle) {
			
			super.draw(pen);
			
			coin.draw(pen);
			
			pen.setColor(Color.ORANGE);
			pen.setFont(new Font("Arial", Font.PLAIN, 20));
			pen.drawString(coin.coinBalance(), 1750, 40);
			
		}
		
		else if(inBattle) {
			
			if(slash) {
				pen.drawImage(back.getCurrentImage(), 200, 200, 500, 500, null);
				
				if(back.animationFinish()) slash = false;
			}
			
			if(healHealth) {
				
				pen.drawImage(back.getStaticImage(), 200, 200, 500, 500, null);
				pen.drawImage(heal.getCurrentImage(), 50, -100, 800, 500, null);
				
				if(heal.animationFinish()) healHealth = false;
				
			}
			
			if(fireAttack) {
				
				for(int i = 0; i < 10; i++) 
				pen.drawImage(fire.getCurrentImage(), 10 + (i * 100), 100, 300, 300, null);
						
				pen.drawImage(back.getCurrentImage(), 200, 200, 500, 500, null);
				
				if(fire.animationFinish()) fireAttack = false;
				
			}
			
			if(flareAttack) {
				
				pen.drawImage(flare.getCurrentImage(), 200, -500, 1500, 1500, null);
				pen.drawImage(back.getStaticImage(), 200, 200, 500, 500, null);
				
				if(flare.animationFinish()) flareAttack = false;
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
			
			hud.showHud = false;
		}
	}
}
