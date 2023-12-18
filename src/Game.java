import java.awt.Graphics;

import Characters.Wolf;
import Characters.Hero;
import Characters.Mimic;
import Characters.Vendor;
import Characters.Viking;
import Objects.Bomb;
import Objects.Coin;
import Objects.CombatHud;
import Objects.Wall;

public class Game extends GameBase {
	
	static Wolf wolf = new Wolf(870, 120, 80, 80, 200, 200);
	
	static Wolf wolf2 = new Wolf(1200, 500, 80, 80, 200, 200);
	
	static Hero hero = new Hero(550, 550);
	
	static Bomb bomb = new Bomb(hero.getX(), hero.getY(), 100, 100);
	
	static CombatHud hud = new CombatHud();
	static CombatHud hud2 = new CombatHud();
	
	static Mimic mimic = new Mimic(800, 50, 5, 5);
	static Mimic mimic2 = new Mimic(800, 50, 5, 5);
	
	static Viking viking = new Viking(500, 400, 50, 30);
	static Viking viking2 = new Viking(700, 150, 50, 30);
	
	static Vendor vendor = new Vendor(460, 170, 100, 100);
	
	static private Coin coin = new Coin(1700, 10, 50, 50);
	
	Wall[] wall = new Wall[] {
			new Wall(470, 120, 750, 20),//horizontal - first room top
			new Wall(700, 350, 500, 20), //first room bottom
			new Wall(500, 640, 150, 30), //starting area bottom
			
			new Wall(700, 310, 40, 200),//vertical - first room right bottom
			new Wall(700, -20, 40, 200), //first room right top
			new Wall(485, 100, 40, 700),//first room bottom left
			new Wall(670, 380, 40, 250), //starting location right
			
			new Wall(950, -20, 20, 200),// second room right top
			new Wall(950, 280, 20, 200),// second room right bottom
			new Wall(1070, 120, 40, 200),// third room right bottom
			
	};
	
	Wall[] wall2 = new Wall[] {
			new Wall(580, 800, 300, 40),
			new Wall(580, 50, 30, 370),
			new Wall(580, 420, 30, 100), //exploding wall
			new Wall(580, 520, 30, 300),
			new Wall(900, 500, 1, 220), //thin wall
			new Wall(1350, 350, 30, 350),
			new Wall(1200, 320, 50, 30),
			new Wall(1250, 300, 50, 30),
	};
	
	static Level1 level1 = new Level1(hero, pressing, hud, wolf, coin);
	static Level2 level2 = new Level2(hero, pressing, hud2, wolf2, mimic2, bomb, viking2, coin);
	static Level3 level3 = new Level3(hero, pressing, coin, bomb);
	//change level back
	static Level level = level1;
	
	public void initialize() {
		
		//CombatHud Listener
		addMouseListener(hud);
		addMouseListener(hud2);
		addMouseListener(vendor);
		
		hud.getHero(hero);
		hud.getVendor(vendor);
		hud.getWolf(wolf);
		hud.getViking(viking);
		hud.getMimic(mimic);
		
		hud2.getHero(hero);
		hud2.getVendor(vendor);	
		hud2.getWolf(wolf2);
		hud2.getViking(viking2);
		hud2.getMimic(mimic2);
		
		hero.getCoin(coin);
		hero.getBomb(bomb);
		
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
		
		if(level == level2) {
		for(int i = 0; i < wall2.length; i++)
		{
			if(hero.overlaps(wall2[i]))
			{
				hero.pushedOutOf(wall2[i]);
			}
		}
		}
		
		if(bomb.overlaps(wall2[2]) && bomb.dropBomb) {
			
			if(bomb.delay >= 70) {
				
				wall2[2] = new Wall(0,0,0,0);
				
				bomb.wallDestroy = true;
			}
		}
		}
	
	public void paint(Graphics pen) {
		
		level.paint(pen);
		
//		for(int i = 0; i < wall2.length; i++)
//		{
//			wall2[i].draw(pen);
//		}
	}
}
