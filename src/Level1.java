import java.awt.Color;
import java.awt.Graphics;

import Characters.Hero;
import Characters.Vendor;
import Characters.Wolf;
import Objects.Coin;
import Objects.CombatHud;
import Objects.ImageLayer;
import Objects.Rect;

public class Level1 extends Level {
	
	Wolf wolf = new Wolf(870, 120, 80, 80, 200, 200);
	
	Hero hero;
	
	Rect nextLevel = new Rect(995, 130, 50, 50);
	
	CombatHud hud = new CombatHud();
	
	Vendor vendor = new Vendor(460, 170, 100, 100);
	
//	Mimic mimic;
	
	Coin coin;
	
//	Level level;
	
	boolean [] pressing;
	
	private boolean canMove = true;
	
	ImageLayer stage1= new ImageLayer("Map_Tiles/Level1.png", 400, 20, 800, 800);
	
	public Level1(Hero hero, boolean [] pressing, CombatHud hud, Wolf wolf, Coin coin) {
		
		this.hero = hero;
		this.pressing = pressing;
		this.hud = hud;
		this.wolf = wolf;
		this.coin = coin;
		
		hud.getWolf(wolf);
	}
	
	public void initialize() {}
	
	public void inGameLoop() {
		
		hero.moving = false;
		hero.swordSwing = false;
		
			if(canMove) {
				
			if(pressing[_W]) hero.moveUP(5);
			if(pressing[_S]) hero.moveDN(5);
			if(pressing[_A]) hero.moveLT(5);
			if(pressing[_D]) hero.moveRT(5);
			
			if(pressing[_F]) hero.sword();
			}
			
			if(hero.overlaps(nextLevel)) {
				
				Game.level = Game.level2;
				
				hero.setLocation(600, 700);
			}
			
			//vendor
			if(hero.overlaps(vendor)) {
				
				hero.pushedOutOf(vendor);
					
			}
			
			if(pressing[_E] && hero.overlaps(vendor.shopArea)) {
				
				vendor.showShop = true;
				
			}
			
			if(!hero.overlaps(vendor.shopArea)) {
				
				vendor.showShop = false;
			}
			
			//wolf
			if(hero.overlaps(wolf.sight)) {
				
				wolf.chase(hero);
				wolf.activateRun();
			}
			
			if(!hero.overlaps(wolf.sight)) {
				
				wolf.runBack();
				wolf.moveToInitialLocation();
			
			}
			
			if(hero.overlaps(wolf)) {
				
				wolf.showHud = true;

				wolf.resetHealth();
				
				canMove = false;
				
				hero.showHud = true;
			}
			
			if(wolf.getHealth() <= 0) {
				
				wolf.showHud = false;
				
				canMove = true;

				hero.showHud = false;
			}

	}
	
	public void paint(Graphics pen) {
		
		pen.setColor(Color.BLACK);
		pen.fillRect(0, 0, 3000, 3000);
		
		if(wolf.showHud) {
			
			hero.draw(pen);
			hud.draw(pen);
			
			if(hero.overlaps(wolf))
			//draw wolf
			wolf.draw(pen);
						
			if(!hud.canAttack) {
				
				//Wolf damage
				if(wolf.delay >= 259) {
					
					if(wolf.biteAttack)
					hero.heroDamage(40);
					
					if(wolf.howlAttack)
					hero.heroDamage(30);
					
					hud.canAttack = true;
				}
				
			}
			
		}
		else{
			
			stage1.draw(pen);
			
			pen.setColor(Color.GREEN);
			nextLevel.draw(pen);
					
			hero.draw(pen);
			
			wolf.draw(pen);
			
			vendor.draw(pen);
		}
	}
}

