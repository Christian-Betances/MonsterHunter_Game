
import java.awt.Color;
import java.awt.Graphics;

import Characters.Wolf;
import Characters.Hero;
import Characters.Vendor;
import Objects.CombatHud;
import Objects.ImageLayer;
import Objects.Wall;

public class Game extends GameBase {
	
	Wolf e1 = new Wolf(870, 120, 80, 80, 200, 200);
	
	Hero hero = new Hero(550, 550);
	
	CombatHud hud = new CombatHud();
	
	Vendor vendor = new Vendor(460, 170, 100, 100);
	
	Wall[] wall;
	
	private boolean canMove = true;
	private boolean ePressed = false;
	private boolean inShop = false;
	
	ImageLayer stage1 = new ImageLayer("Map_Tiles/Level1.png", 400, 20);
	
	public void initialize() {
		
		wall = new Wall[] {
				new Wall(470, 120, 750, 20),//horizontal - first room top
				new Wall(700, 350, 500, 20), //first room bottom
				new Wall(500, 640, 150, 30), //startin area bottom
				
				new Wall(700, 310, 40, 200),//vertical - first room right bottom
				new Wall(700, -20, 40, 200), //first room right top
				new Wall(485, 100, 40, 700),//first room bottom left
				new Wall(670, 380, 40, 250), //starting location right
				
				new Wall(950, -20, 20, 200),// second room right top
				new Wall(950, 270, 20, 200),// second room right bottom
				new Wall(1070, 120, 40, 200),// third room right bottom
				
		};
		
		//CombatHud Listener
		addMouseListener(hud);
		addMouseListener(vendor);
		
		hud.getHero(hero);
		hud.getWolf(e1);
		
		vendor.getHero(hero);
	}
	
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
			
			//Registers E only once when pressed
			if (pressing[_E]) ePressed = true;
		    else ePressed = false;
	
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
			
			//walls
			for(int i = 0; i < wall.length; i++)
			{
				if(hero.overlaps(wall[i]))
				{
					hero.pushedOutOf(wall[i]);
				}
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
			
			if(hero.overlaps(e1) && hero.swordSwing) 
				
				hero.damageWolf(e1);
			
			else if(e1.dead) {
				
				hud.showHud = false;
				canMove = true;

				hero.inBattle = false;
			}

	}
	
	public void paint(Graphics pen) {
		
		pen.setColor(Color.BLACK);
		pen.fillRect(0, 0, getWidth(), getHeight());
		
		if(!canMove && !e1.dead) {
			
			hero.draw(pen);
			hud.draw(pen);
			e1.draw(pen);
			
			if(!hud.canAttack) {
				
				//Wolf damage
				if(e1.delay >= 259) {
					
					if(e1.biteAttack)
					hero.heroDamage(60);
					
					if(e1.howlAttack)
					hero.heroDamage(30);
					
					hud.canAttack = true;
				}
			}
			
		}
		else{
			
			stage1.draw(pen);
			
			hero.draw(pen);
			
			e1.draw(pen);
			
			vendor.draw(pen);
			
			if(hero.overlaps(vendor.shopArea)) {
				
				vendor.vendorTalk(pen);
			}
			
//			pen.setColor(Color.RED);
//			for(int i = 0; i < wall.length; i++)
//			{
//				wall[i].draw(pen);
//			}
		}
	}
}
