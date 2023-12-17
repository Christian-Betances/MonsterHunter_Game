import java.awt.Color;
import java.awt.Graphics;

import Characters.Boss;
import Characters.Hero;
import Objects.Bomb;
import Objects.Coin;

public class Level3 extends Level {

	Hero hero;
	
	Coin coin;
	
	Bomb bomb;
	
	Boss boss = new Boss(800, 100, 100, 100);
	
	boolean pressing [];
	
	public Level3(Hero hero, boolean [] pressing, Coin coin, Bomb bomb) {
		
		this.hero = hero;
		
//		hero.setLocation(1000, 800);
		this.pressing = pressing;
		this.coin = coin;
		this.bomb =  bomb;
	}
	
	public void initialize() {}
	
	public void inGameLoop() {
		
		hero.moving = false;
		hero.swordSwing = false;
			
			bomb.BombLocation(hero.getX(), hero.getY());
				
			if(pressing[_W]) hero.moveUP(5);
			if(pressing[_S]) hero.moveDN(5);
			if(pressing[_A]) hero.moveLT(5);
			if(pressing[_D]) hero.moveRT(5);
			
			if(hero.swordSwing(boss.hitbox)) boss.takeDamage = true;
			//sword attack
			if(pressing[UP] || pressing[RT]) {
				hero.sword();	
				
			if(hero.swordSwing(boss.hitbox) && pressing[RT] ) {
				boss.damage(50);
				
				boss.takeDamage = false;
			}
			if(hero.swordSwing(boss.hitbox) && pressing[UP] ) {
				boss.damage(10);
				
				boss.takeDamage = false;
			}
			}
			
			//shoot attack
			if(pressing[RT]) {
				hero.sword();
				
				hero.shoot = true;
			}
			
			if(hero.overlaps(boss.battleArea)) {
				boss.sleeping = false;
				boss.delay++;
				
				if(boss.delay >= 150) {
					
				
				boss.canChase = true;
				boss.delay = 0;
				}
			}
			if(boss.canChase && boss.canMove) {
				
				boss.chase(hero);
				
				if(hero.overlaps(boss.hitbox)) boss.canMove = false;
			}
			
			if(boss.attackDelay >= 50 && hero.overlaps(boss.attack1Hitbox) && !boss.dead) {
				
				hero.heroDamage(10);
				
				boss.attackDelay = 0;
			}
			if(boss.attack2) {
				
				boss.attack2Hitbox.chase(hero);
			}
			
			if(hero.getX() <= boss.attack2Hitbox.getX()) boss.onRT = false;
			else boss.onRT = true;
			
			if(boss.attackDelay >= 50 && hero.overlaps(boss.attack2Hitbox) && !boss.dead) {
				
				hero.heroDamage(20);
				
				boss.attackDelay = 0;
			}
	}
	
	public void paint(Graphics pen) {
		
		pen.setColor(Color.BLACK);
		pen.fillRect(0, 0, 3000, 3000);
		
		hero.draw(pen);	
		
		boss.draw(pen);
		bomb.draw(pen);
		}
}