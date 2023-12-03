import java.awt.Color;
import java.awt.Graphics;

import Characters.Hero;
import Characters.Mimic;
import Characters.Vendor;
import Characters.Wolf;
import Objects.Coin;
import Objects.CombatHud;
import Objects.ImageLayer;
import Objects.Rect;
import Objects.Wall;

public class Level1 extends Level {
	
	Wolf e1 = new Wolf(870, 120, 80, 80, 200, 200);
	
	Hero hero = new Hero(550, 550);
	
	Rect nextLevel = new Rect(995, 130, 50, 50);
	
	CombatHud hud = new CombatHud();
	
	Vendor vendor = new Vendor(460, 170, 100, 100);
	
	Mimic m = new Mimic(600, 200, 5, 5);
	
	Coin coin;
	
	Level level;
	
	boolean [] pressing;
	
	private boolean canMove = true;
	private boolean ePressed = false;
	private boolean inShop = false;
	
	ImageLayer stage1 = new ImageLayer("Map_Tiles/Level1.png", 400, 20);
	
	public Level1(Hero hero, boolean [] pressing, CombatHud hud, Wolf e1, Mimic m, Coin coin) {
		
		this.hero = hero;
		this.pressing = pressing;
		this.hud = hud;
		this.m = m;
		this.e1 = e1;
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
			
			if(hero.overlaps(nextLevel)) {
				
				Game.level = Game.level2;
				
				hero.setLocation(900, 900);;
			}
			
			//mimic
			if(hero.overlaps(m.onMimic)) {
				
				if(pressing[_E]) {
					
				System.out.println("E pressed");
					
				hud.showHud = true;
				
				canMove = false;
				
				hero.inBattle = true;
				}
			}
			
			if(m.getHealth() <= 0) {
				
				hud.showHud = false;
				
				canMove = true;

				hero.inBattle = false;
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
			if(hero.overlaps(e1.sight)) {
				
				e1.chase(hero);
				e1.activateRun();
			}
			
			if(!hero.overlaps(e1.sight)) {
				
				e1.runBack();
				e1.moveToInitialLocation();
			
			}
			
			if(hero.overlaps(e1)) {
				
				hud.showHud = true;
				
				canMove = false;
				
				hero.inBattle = true;
			}
			
			if(e1.getHealth() <= 0) {
				
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
			
			if(hero.overlaps(e1))
			//draw wolf
			e1.draw(pen);
			
			
			if(hero.overlaps(m.onMimic))
			//draw mimic
			m.draw(pen);
			
			if(!hud.canAttack) {
				
				//Wolf damage
				if(e1.delay >= 259) {
					
					if(e1.biteAttack)
					hero.heroDamage(40);
					
					if(e1.howlAttack)
					hero.heroDamage(30);
					
					hud.canAttack = true;
				}
				
				if(m.delay >= 190) {
					
					hero.heroDamage(50);
					hud.canAttack = true;
				}
			}
			
		}
		else{
			
			stage1.draw(pen);
			
			pen.setColor(Color.GREEN);
			nextLevel.draw(pen);
			
			m.draw(pen);
			
			if(hero.overlaps(m.onMimic) && m.getHealth() > 0) {
				
				m.openChest(pen);	
			}
			
			hero.draw(pen);
			
			e1.draw(pen);
			
			vendor.draw(pen);
			
//			pen.setColor(Color.RED);
//			for(int i = 0; i < wall.length; i++)
//			{
//				wall[i].draw(pen);
//			}
		}
	}
}
