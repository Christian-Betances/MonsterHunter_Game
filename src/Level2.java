import java.awt.Color;
import java.awt.Graphics;

import Characters.Slime;
import Characters.Hero;
import Characters.Mimic;
import Characters.Vendor;
import Characters.Viking;
import Characters.Wolf;
import Objects.Bomb;
import Objects.Chest;
import Objects.Coin;
import Objects.CombatHud;
import Objects.Floor;
import Objects.ImageLayer;
import Objects.MovingPlatform;
import Objects.Rect;
import Objects.Trap;
import Objects.Wall;

public class Level2 extends Level {
	
	Wolf wolf;
	
	Wolf wolf2 = new Wolf(450, 280,100,100, 50, 50);
	
	Hero hero;
	
	Bomb bomb;
	
	Rect nextLevel = new Rect(1260, 350, 50, 50);
	
	CombatHud hud;
	
	Mimic mimic;
	
	Vendor vendor = new Vendor(900, 620, 100, 100);
	
	Chest chest = new Chest(480, 450, 50, 50);
	
	Trap trap [] = new Trap [] {
				   new Trap(700, 300, 50, 50),
				   new Trap(650, 500, 50, 50),
	};
	
	Slime slime [] = new Slime [] {
		
		new Slime (600, 300, 50, 50),
		new Slime (700, 200, 50, 50),
		new Slime (700, 300, 50, 50),
	};
	
	Coin coin;
	
	Viking viking;
	
	Wall[] wall;
	
	Floor [] fall = new Floor[] {
			new Floor(620, 5, 400, 10),
			new Floor(940, 50, 200, 200),
			new Floor(820, 230, 340, 270),
			new Floor(800, 660, 80, 200),
			new Floor(900, 740, 800, 100),
	};
	
	MovingPlatform plat = new MovingPlatform(882, 50, 200, 200);
	
	boolean [] pressing;
	
	private boolean canMove = true;

	private boolean gotCoin = false;
	
	private int damageDelay = 0;
	private boolean slimeChase = false;
	
	ImageLayer stage2 = new ImageLayer("Map_Tiles/Level2.png", 400, 30, 1000, 800);
	ImageLayer stage2Destroy = new ImageLayer("Map_Tiles/Level2_Destroy.png", 400, 30, 1000, 800);
	
	public Level2(Hero hero, boolean [] pressing, CombatHud hud, Wolf wolf, Mimic mimic, Bomb bomb, Viking viking, Coin coin) {
		
		this.hero = hero;
		
		this.mimic = mimic;
		
//		hero.setLocation(600, 700);
		
		this.bomb = bomb;
		
		this.pressing = pressing;
		
		this.wolf = wolf;
		
		this.hud = hud;
		
		this.viking = viking;
		
		this.coin = coin;
		
		hud.getWolf(wolf);
		hud.getMimic(mimic);
		hud.getViking(viking);
	}
	
	public void initialize() {}
	
	public void inGameLoop() {
		
		hero.moving = false;
		hero.swordSwing = false;
		
		if(hero.overlaps(nextLevel)) {
			
			Game.level = Game.level3;
			
			hero.setLocation(1000, 800);
		}
		
			if(canMove) {
				
			bomb.BombLocation(hero.getX(), hero.getY());
				
			if(pressing[_W]) hero.moveUP(5);
			if(pressing[_S]) hero.moveDN(5);
			if(pressing[_A]) hero.moveLT(5);
			if(pressing[_D]) hero.moveRT(5);
			
			//sword attack
			if(pressing[UP] || pressing[RT]) {
				hero.sword();
				
				for(int i = 0; i < slime.length; i++)
				if(hero.swordSwing(slime[i])) slime[i].attack = true;
			}
			
			//shoot attack
			if(pressing[RT]) {
				hero.sword();
				
				hero.shoot = true;
			}
			
			//slime
			for(int i = 0; i < slime.length; i++)
			if(slimeChase) slime[i].chase(hero);
		
			damageDelay ++;
			
			for(int i = 0; i < slime.length; i++)
			if(damageDelay >= 30 && !slime[i].attack) {
			
			hero.heroDamage(1);
			
			damageDelay = 0;
			}
			}
			
			for(int i = 0; i < slime.length; i++)
			if(hero.overlaps(slime[i].sight)) {
				
				slimeChase = true;
			}
			//mimic
			if(hero.overlaps(mimic.onMimic)) {
				
				mimic.showHud = true;
				
				hero.showHud = true;
				
				canMove = false;
			}
			
			if(mimic.getHealth() <= 0) {
				
				mimic.showHud = false;
				
				canMove = true;

				hero.showHud = false;
			}
			
			//plaform
			if(hero.overlaps(plat.onPlatform) && plat.delay == 0) {
				
				hero.setX(plat.onPlatform.getX() + 10);
				hero.setY(plat.onPlatform.getY() - 10);
			}
			
			//falling
			if(hero.falling == 0) {

				canMove = true;
			}
		
			for(int i = 0; i < fall.length; i++) {
			if(hero.overlaps(fall[i]) && !hero.overlaps(plat)) {
				
				hero.heroFalling();
				
				canMove = false;
				
				if(hero.wasLeftOf(fall[i])) {
					
					hero.setX(hero.getX() + 2);
					hero.moveLeft = true;
					hero.moveAbove = false;
					hero.moveBelow = false;
					hero.moveRight = false;
				}
				
				if(hero.wasRightOf(fall[i])) {
					
					hero.moveRight = true;
					hero.moveLeft = false;
					hero.moveAbove = false;
					hero.moveBelow = false;
				}
				

				if(hero.wasBelow(fall[i])) {
					
					hero.moveBelow = true;
					hero.moveRight = false;
					hero.moveLeft = false;
					hero.moveAbove = false;
				}
				
				if(hero.wasAbove(fall[i])) {
					
					hero.setY(hero.getY() + 2);
					hero.moveAbove = true;
					hero.moveLeft = false;
					hero.moveBelow = false;
					hero.moveRight = false;		
				}
			}
			}
			
			//bomb
			if(pressing[LT]) hero.dropBomb();
			
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
			for(int i = 0; i < trap.length; i++) {
			if(hero.overlaps(trap[i]) && trap[i].damage) {
				
				if(trap[i].delay == 150)
				hero.heroDamage(20);
			}
			}
			//viking
			if(hero.overlaps(viking.sight)) {
				
				viking.chaseDirection(hero);
			}
			if(!hero.overlaps(viking.sight)) {
				
				viking.patrol();
			}
			
			if(hero.overlaps(viking)) {
				
				viking.showHud = true;
				
				viking.resetHealth();
				
				canMove = false;
				
				hero.showHud = true;
			}
			
			if(viking.getHealth() <= 0) {
				
				viking.showHud = false;
				
				canMove = true;

				hero.showHud = false;
				
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
		
		if(wolf.showHud || viking.showHud || mimic.showHud) {
			
			hero.draw(pen);
			hud.draw(pen);
			
			if(hero.overlaps(mimic.onMimic))
				//draw mimic
				mimic.draw(pen);
			
			if(hero.overlaps(wolf))
			wolf.draw(pen);
			
			if(hero.overlaps(viking))
			viking.draw(pen);
			
			if(!hud.canAttack) {
				
				//wolf damage
				if(wolf.delay >= 259) {
					
					if(wolf.biteAttack)
					hero.heroDamage(40);
					
					if(wolf.howlAttack)
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

				if(mimic.delay >= 190) {
					
					hero.heroDamage(50);
					 
					hud.canAttack = true;
				}
			}
		}
		else{
			
			//destroy wall
			if(!bomb.wallDestroy) stage2.draw(pen);
			
			else stage2Destroy.draw(pen);
			
			plat.draw(pen);
			for(int i = 0; i < trap.length; i++)
			trap[i].draw(pen);
			
			chest.draw(pen);
			
			for(int i = 0; i < slime.length; i++)
			slime[i].draw(pen);
			
			hero.draw(pen);
			
			if(hero.overlaps(chest)) {
				
				chest.openChest(pen);
			}
			
			for(int i = 0; i < fall.length; i++) {
				
			fall[i].draw(pen);
			}
			
			mimic.draw(pen);
			
			viking.draw(pen);
			
			wolf.draw(pen);
			
			vendor.draw(pen);
			
			wolf2.draw(pen);
			
			bomb.draw(pen);
			
			nextLevel.draw(pen);
		}
	}
}