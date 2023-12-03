package Characters;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import Objects.Animation;
import Objects.CombatHud;
import Objects.HealthBar;
import Objects.Rect;

public class Wolf extends Rect{
	
	public Rect sight;
	
	private int initialX;
    private int initialY;
    
    private int wolfX;
    private int wolfY;
    
    private boolean run = false;
    private boolean runningBack = false;
    private boolean handleRandom = true;
    private Random random = new Random();
    
    public boolean biteAttack = false;
    public boolean howlAttack = false;
    
    public boolean damage = false;
    public boolean inFight = false;
    
    public int delay = 0;
    public boolean turnOnDelay = false;
    
    CombatHud hud;
    
    private HealthBar health = new HealthBar(300, 870, 120, 20);
    
    Animation wolfIdle = new Animation("Wolf_Idle/Wolf_Idle_LT", 4, 15);
    //Wolf running from right
    Animation wolfRunLT = new Animation("WRL/Wolf_RUN_LT", 8, 5);
    Animation wolfRB_RT = new Animation("WRL/Wolf_RB_RT", 8, 5);
    //Wolf running from left
    Animation wolfRunRT = new Animation("WRR/Wolf_Run_RT", 8, 5);
    Animation wolfRB_LT = new Animation("WRR/Wolf_RB_LT", 8, 5);
    
    //Wolf Attack
    Animation wolfBite = new Animation("Wolf_ATK/Wolf_Bite", 15, 10);
    Animation wolfHowl = new Animation("Wolf_ATK/Wolf_Howl0", 9, 10);
    
    
    public Wolf(int x, int y, int w, int h, int chaseW, int chaseH) {
		
		super(x, y, w - 50, h - 50);
		
		sight = new Rect(x - 100 , y, chaseW, chaseH);
		
		   initialX = x;
	       initialY = y;
	       
	       wolfX = 870;
	       wolfY = 120;
	       
	       health.setHealth(20);
		
	}
	
	public void moveToInitialLocation() {
		
		if(getX() < initialX) moveRT(2);
		
		else if(getX() > initialX) moveLT(2);
		
		if(getY() < initialY) moveDN(2);
		
		else if(getY() > initialY) moveUP(2);
		
		if(getX() == initialX && getY() ==  initialY)
		runningBack = false;
		
	}
	
	public boolean activateRun() {
		
		runningBack = false;
		return run = true;
		
	}
	
	public boolean runBack() {
		
		run = false;
		return runningBack = true;
	}
	
	public void damage(int attack) {
		
		health.damage(attack);
		
		damage = true;
	}
	
	public int getHealth() {
		
		return health.getHealth();
	}
	
	public void randomAttack() {
		
		if(handleRandom) {
		
		int randomNumber = random.nextInt(2);
		
		if(randomNumber == 0) {
			biteAttack = true;
			howlAttack = false;
		}
		
		if(randomNumber == 1) {
			howlAttack = true;
			biteAttack = false;
		}
		handleRandom = false;
		}
	}
	
	public void draw(Graphics pen) {
		
//		super.draw(pen);
		
		if(health.getHealth() >= 0) {
		
		if(!hud.showHud) {
			
		//run
		if(run && getX() < initialX) pen.drawImage(wolfRunLT.getCurrentImage(), getX() - 50, getY() - 60, 200, 200, null);
		else if(run && getX() > initialX) pen.drawImage(wolfRunRT.getCurrentImage(), getX() - 50, getY() - 60, 200, 200, null);
		//run Back
		else if(runningBack && getX() < initialX) pen.drawImage(wolfRB_RT.getCurrentImage(), getX() - 50, getY() - 60, 200, 200, null);
		
		else if(runningBack && getX() > initialX) pen.drawImage(wolfRB_LT.getCurrentImage(), getX() - 50, getY() - 60, 200, 200, null);
		
		//run Back at middle
		else if (!runningBack && getX() == initialX && getY() != initialY) {
			pen.drawImage(wolfRunRT.getCurrentImage(), getX() - 50, getY() - 60, 200, 200, null);
			
		if (runningBack) pen.drawImage(wolfRB_LT.getCurrentImage(), getX() - 50, getY() - 60, 200, 200, null);
		}
		
		//idle
		else if (getX() == initialX && getY() ==  initialY) pen.drawImage(wolfIdle.getCurrentImage(), getX() - 57, getY() - 60, 200, 200, null);
		
		//run back middle
		else pen.drawImage(wolfRB_RT.getCurrentImage(), getX() - 50, getY() - 60, 200, 200, null);
		}
		
		//hud stuff
		if(hud.showHud) {
			
			inFight = true;
			
			if(damage) {
				
				randomAttack();
				
				if(delay == 0)
					
					pen.drawImage(wolfIdle.getCurrentImage(), wolfX, wolfY, 500, 500, null);
					
				if(turnOnDelay)
					
				delay++;
					
					if(biteAttack) {
					pen.drawImage(wolfIdle.getCurrentImage(), wolfX, wolfY, 500, 500, null);
					
					if(delay >= 20 && delay <= 140)
					pen.drawImage(wolfBite.getCurrentImage(), wolfX - 97, wolfY + 60, 500, 500, null);
					
					if(delay >= 142 && delay <= 240)
						pen.drawImage(wolfBite.getCurrentImage(), wolfX - 207, wolfY - 160, 500, 500, null);
					}
					if(howlAttack) {
					
					if(delay >= 40)
					pen.drawImage(wolfHowl.getCurrentImage(), wolfX, wolfY, 500, 500, null);
					
					else 
						pen.drawImage(wolfIdle.getCurrentImage(), wolfX, wolfY, 500, 500, null);
					}
					if(delay >= 260) {
					
					damage = false;
					handleRandom = true;
					turnOnDelay = false;
					delay = 0;
					}
				
			}
			else pen.drawImage(wolfIdle.getCurrentImage(), wolfX, wolfY, 500, 500, null);
			
			pen.setColor(Color.WHITE);
			pen.setFont(new Font("Arial", Font.PLAIN, 20));
			pen.drawString(health.showHealth(), wolfX, wolfY - 10);
			
			health.draw(pen);
		}
		}
//		sight.draw(pen);
	}

}
