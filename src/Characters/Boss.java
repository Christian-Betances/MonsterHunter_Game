package Characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import Objects.Animation;
import Objects.HealthBar;
import Objects.Rect;

public class Boss extends Rect {
	
	HealthBar health = new HealthBar(1500, 300, 60, 30);
	
	Animation wakeUP = new Animation("Boss/BossWake_", 8, 10);
	
	Animation death = new Animation("Boss/BossDead_", 8, 10);
	
	Animation idle = new Animation("Boss/BossIdle_", 4, 10);
	
	Animation laser = new Animation("Boss/laser_", 6, 20);
	
	Animation ATK1 = new Animation("Boss/BossATK_", 7, 15);
	
	Animation ATK2 = new Animation("Boss/BossShoot_", 10, 20);
	
	Image armRT = Toolkit.getDefaultToolkit().getImage("Boss/arm_projectile.png");
	Image armLT = Toolkit.getDefaultToolkit().getImage("Boss/arm_projectile_LT.png");
			
	public boolean canChase = false;
	public boolean canMove = true;
	public boolean sleeping = true;
	public boolean inCombat = false;
	public boolean onRT = true;
	public boolean dead = false;
	
	public int delay = 0;
	public int attackDelay = 0;
	
	private boolean handleRandom = true;
	
	private Random random = new Random();
	
	public boolean attack1;
	public boolean attack2;
	public boolean takeDamage = true;
			
	public Rect battleArea = new Rect();
	public Rect hitbox = new Rect();
	public Rect attack1Hitbox = new Rect();
	public Rect attack2Hitbox = new Rect();
	
	public Boss(int x, int y, int w, int h) {
		super(x,y,w,h);
		
		battleArea = new Rect(x - 350, y + 100, w + 700, h + 400);
		
		attack2Hitbox = new Rect(getX() + 100, getY(), 50, 50);
	}
	
	public void damage(int x) {
		
		if(takeDamage)
		health.damage(x);
	}
	
	public void randomAttack() {
		if(handleRandom) {
			
			int randomNumber = random.nextInt(2);
			
			if(randomNumber == 0) {
				attack1 = true;
				attack2 = false;
			}
			
			if(randomNumber == 1) {
				attack1 = false;
				attack2 = true;
			}
			handleRandom = false;
			}
	}
	
	public void draw(Graphics pen) {
				
		pen.setFont(new Font("Arial", Font.PLAIN, 20));	
		health.draw(pen);
		pen.setColor(Color.WHITE);
		pen.drawString(health.showHealth(), 700, 80);
		
		if(health.getHealth() <= 0) dead = true;
		
//		attack2Hitbox.setColor(Color.ORANGE);
//		attack2Hitbox.draw(pen);
		if(!attack2)
		attack2Hitbox = new Rect(getX() + 150, getY() - 20, 50, 50);
		
		int bossX = getX() - 150;
		int bossY = getY() - 150;
		
//		attack1Hitbox.setColor(Color.RED);
//		attack1Hitbox.draw(pen);
		attack1Hitbox = new Rect(bossX + 100, getY() + getH(), 200, 50);
		
		battleArea.setColor(Color.RED);
		battleArea.draw(pen);
		
		hitbox = new Rect(bossX + 100, bossY + 120, 180, 160);
		
		if(!dead) {
		if(sleeping)
		pen.drawImage(wakeUP.getStaticImage(), bossX, bossY, 400, 400, null);
		
		if(!sleeping && !inCombat)
			pen.drawImage(wakeUP.getCurrentImage(), bossX, bossY, 400, 400, null);
		
		if(wakeUP.animationFinish()) {
			
			inCombat = true;
		}
		if(inCombat && canMove) {
			pen.drawImage(idle.getCurrentImage(), bossX, bossY, 400, 400, null);
		}
		if(!canMove) {
			
			randomAttack();
			
			if(attack1) { 
			
			attackDelay++;
			pen.drawImage(ATK1.getCurrentImage(), bossX, bossY, 400, 400, null);
			System.out.println("ATK1");
			
			pen.drawImage(laser.getCurrentImage(), attack1Hitbox.getX() - 50, attack1Hitbox.getY() - 75, 300, 300, null);

			
			if(ATK1.animationFinish()) {
				
			//	pen.drawImage(laser.getCurrentImage(), attack1Hitbox.getX(), attack1Hitbox.getY(), 400, 400, null);
				handleRandom = true;
				canMove = true;
			}
			}
			
			if(attack2) {
				attackDelay++;
				pen.drawImage(ATK2.getCurrentImage(), bossX, bossY, 400, 400, null);
				System.out.println("ATK2");
				
			if(ATK2.animationFinish()) {
	
				handleRandom = true;
				canMove = true;
			}

			if(onRT)
				pen.drawImage(armRT, attack2Hitbox.getX() - 120,  attack2Hitbox.getY() - 50, 200, 200, null);
				if(!onRT)
				pen.drawImage(armLT, attack2Hitbox.getX(),  attack2Hitbox.getY() - 100, 200, 200, null);
			}		
		}
		}
		if(dead) {
			
			pen.drawImage(wakeUP.getStaticImage(), bossX, bossY, 400, 400, null);
		}
		hitbox.setColor(Color.cyan);
		hitbox.draw(pen);
		}
	}