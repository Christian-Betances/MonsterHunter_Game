import java.awt.Color;
import java.awt.Graphics;

import Characters.Hero;
import Characters.Vendor;
import Characters.Viking;
import Characters.Wolf;
import Objects.Chest;
import Objects.Coin;
import Objects.CombatHud;
import Objects.ImageLayer;
import Objects.Rect;
import Objects.Trap;
import Objects.Wall;

public class Level2 extends Level {
	
	Wolf e2 = new Wolf(400, 800, 80, 80, 200, 200);
	
	Hero hero;
	
	Rect nextLevel;
	
	CombatHud hud;
	
	Vendor vendor = new Vendor(460, 170, 100, 100);
	
	Chest chest = new Chest(400, 300, 100, 100);
	
	Trap trap = new Trap(700, 200, 50, 50);
	
	Coin coin;
	
	Viking viking;
	
	Wall[] wall;
	
	boolean [] pressing;
	
	private boolean canMove = true;
	private boolean ePressed = false;
	private boolean inShop = false;
	
	private boolean gotCoin = false;
	
	ImageLayer stage2 = new ImageLayer("Map_Tiles/Level1.png", 1000, 20);
	
	public Level2(Hero hero, boolean [] pressing, CombatHud hud, Wolf e2, Viking viking, Coin coin) {
		
		this.hero = hero;
		this.pressing = pressing;
		this.e2 = e2;
		this.hud = hud;
		this.viking = viking;
		
		this.coin = coin;
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
			
			if(pressing[_E] && hero.overlaps(chest) && !gotCoin) {
				
				coin.increaseBalance();
				gotCoin = true;
				
				chest.open = true;
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
			
			//trap
			if(hero.overlaps(trap) && trap.damage) {
				
				if(trap.delay == 150)
				hero.heroDamage(20);
			}
			
			//viking
			if(hero.overlaps(viking.sight)) {
				
				viking.chaseDirection(hero);
			}
			if(!hero.overlaps(viking.sight)) {
				
				viking.patrol();
			}
			
			if(hero.overlaps(viking)) {
				
				hud.showHud = true;
				
				canMove = false;
				
				hero.inBattle = true;
			}
			
			if(viking.getHealth() <= 0) {
				
				hud.showHud = false;
				
				canMove = true;

				hero.inBattle = false;
			}
			
			//wolf
			if(hero.overlaps(e2.sight)) {
				
				e2.chase(hero);
				e2.activateRun();
			}
			
			if(!hero.overlaps(e2.sight)) {
				
				e2.runBack();
				e2.moveToInitialLocation();
			
			}
			
			if(hero.overlaps(e2)) {
				
				hud.showHud = true;
				canMove = false;
				
				hero.inBattle = true;
			}
			
			if(e2.getHealth() <= 0) {
				
				hud.showHud = false;
				canMove = true;

				hero.inBattle = false;
			}

	}
	
	public void paint(Graphics pen) {
		
		pen.setColor(Color.BLACK);
		pen.fillRect(0, 0, 3000, 3000);
		
		if(!canMove) {
			
			hero.draw(pen);
			hud.draw(pen);
			
			if(hero.overlaps(e2))
			e2.draw(pen);
			
			if(hero.overlaps(viking))
			viking.draw(pen);
			
			if(!hud.canAttack) {
				
				//Wolf damage
				if(e2.delay >= 259) {
					
					if(e2.biteAttack)
					hero.heroDamage(40);
					
					if(e2.howlAttack)
					hero.heroDamage(30);
					
					hud.canAttack = true;
				}
				//viking damage
				if(viking.delay >= 230) {
					
					if(viking.slashAtk)
					hero.heroDamage(50);
					
					if(viking.throwAtk)
					hero.heroDamage(70);
					
					hud.canAttack = true;
				}
			}
			
		}
		else{
			
			stage2.draw(pen);
			
			trap.draw(pen);
			
			hero.draw(pen);
			
			chest.draw(pen);
			
			if(hero.overlaps(chest)) {
				
				chest.openChest(pen);
			}
			
			viking.draw(pen);
			
			e2.draw(pen);
			
			vendor.draw(pen);
			
		}
	}
}