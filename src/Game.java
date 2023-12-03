
import java.awt.Color;
import java.awt.Graphics;

import Characters.Wolf;
import Characters.Hero;
import Characters.Mimic;
import Characters.Vendor;
import Characters.Viking;
import Objects.Coin;
import Objects.CombatHud;
import Objects.ImageLayer;
import Objects.Rect;
import Objects.Wall;

public class Game extends GameBase {
	
	static Wolf e1 = new Wolf(870, 120, 80, 80, 200, 200);
	//fix wolf e2 not taking damage
	static Wolf e2 = new Wolf(400, 800, 80, 80, 200, 200);
	
	static Hero hero = new Hero(550, 550);
	
	static CombatHud hud = new CombatHud();
	
	static Mimic mimic = new Mimic(600, 150, 5, 5);
	
	static Viking viking = new Viking(300, 600, 50, 30);
	
	Vendor vendor = new Vendor(460, 170, 100, 100);
	
	static private Coin coin = new Coin(1700, 10, 50, 50);
	
	Wall[] wall;
	
	static Level1 level1 = new Level1(hero, pressing, hud, e1, mimic, coin);
	static Level2 level2 = new Level2(hero, pressing, hud, e2, viking, coin);
	//change level back
	static Level level = level2;
	
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
				new Wall(950, 280, 20, 200),// second room right bottom
				new Wall(1070, 120, 40, 200),// third room right bottom
				
		};
		
		//CombatHud Listener
		addMouseListener(hud);
		addMouseListener(vendor);
		
		hud.getHero(hero);
		hud.getVendor(vendor);
		hud.getWolf(e1);
		hud.getWolf(e2);
		hud.getViking(viking);
		hud.getMimic(mimic);
		
		hero.getCoin(coin);
		
		vendor.getHero(hero);
		vendor.getCoin(coin);
	}
	
	public void inGameLoop() {
		
		level.inGameLoop();
		
		if(level == level1) {
			
		for(int i = 0; i < wall.length; i++)
		{
			if(hero.overlaps(wall[i]))
			{
				hero.pushedOutOf(wall[i]);
			}
		}
		}
		
		//fix or scrap
//		if(hero.overlaps(e1) && hero.swordSwing) 
//			
//			hero.damageWolf(e1);
//		

	}
	
	public void paint(Graphics pen) {
		
		level.paint(pen);
		
	}
}
