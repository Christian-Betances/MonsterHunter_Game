package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import Characters.Hero;
import Characters.Mimic;
import Characters.Vendor;
import Characters.Viking;
import Characters.Wolf;

public class CombatHud extends Rect implements MouseListener {
	
	private int mx;
	private int my;
	
	private String [] manaDisc;
	
	private boolean screenShake = false;
	private Random random = new Random();
	private int delay = 0;
	private boolean attackDelay = false;
	
	private int attackIncrease = 0;
	
	public boolean canAttack = true;
	
	private Rect [] attackHud;
	
	private boolean powerUP = false;
	
	private int number;
	
	Hero hero;
	
	Vendor vendor;
	
	Wolf wolf;
	
	Mimic mimic;
	
	Viking viking;
	
	private Rect border;
	
	public CombatHud() {
		
		int x = 900;
		int y = 675;
		
		int width = 300;
		int height = 100;
		
		attackHud = new Rect[4];
		
        attackHud[0] = new Rect(x, y, width, height);
        attackHud[1] = new Rect(x, y + 150, width, height);
        attackHud[2] = new Rect(x + 400, y, width, height);
        attackHud[3] = new Rect(x + 400, y + 150, width, height);
		
		border = new Rect(-100, 650, 2100, 500);
	}
	
	public void updateDisc() {	
		
		if(powerUP) {
        	number = 10;
        }
		 
		manaDisc = new String [] {
									"Mana: +50 | ATK: " + (30 + number),
									"Mana: -50 | ATK: " + 50 + number,
									"Mana: -100 | ATK: " + 70 + number,
									"Mana: -150 | Heal: " + 100 + number,
									};
	}
	
	public void setDelay(int x) {
		
		 this.delay = x;
	}
	
	public void getHero(Hero hero) {
		
		this.hero = hero;
	}
	
	public void getVendor(Vendor vendor) {
		
		this.vendor = vendor;
	}
	
	public void getWolf(Wolf wolf) {
		
		this.wolf = wolf;
	}
	
	public void getViking(Viking viking) {
		
		this.viking = viking;
	}
	
	public void getMimic(Mimic mimic) {
		
		this.mimic = mimic;
	}

	public void draw(Graphics pen) {
		
		updateDisc();
		
		if(hero.alive()) {	
		
		if(!screenShake) {
		pen.setColor(Color.RED);
		pen.fillRect(border.x, border.y, border.w, border.h);
		
		for	(int i = 0; i < attackHud.length; i++) {
			pen.setColor(Color.WHITE);
			attackHud[i].drawFill(pen);
			
			pen.setColor(Color.BLACK);
			pen.drawString(manaDisc[i], attackHud[i].x, attackHud[i].y - 5);
		}
		
		if(attackDelay) delay++;
		
		if(delay >= 100) {
			
			attackDelay = false;
			wolf.turnOnDelay = true;
			delay = 0;
		}
		
		}
		if(screenShake) {
			
			delay++;
			
			int shakeX = random.nextInt(10);
			int shakeY = random.nextInt(10);
			
			shakeX += border.x;
			shakeY += border.y;
			
			pen.setColor(Color.RED);
		    pen.fillRect(shakeX, shakeY, border.w, border.h);
		    
		    if(delay >= 100) {
		    	
		    	screenShake = false;
		    	wolf.turnOnDelay = true;
		    	delay = 0;
		    }
		}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		mx = e.getX();
		my = e.getY();
		
		if(vendor.powerUP) {
			powerUP = true;
			this.attackIncrease = 30;
		}
		if(attackHud[0].contains(mx, my)) {
			
			hero.increaseMana(25);
			
			hero.slash = true;
			
			if(wolf.inFight) wolf.damage(10 + attackIncrease);
			if(viking.inFight) viking.damage(10 + attackIncrease);
			if(mimic.inFight) mimic.damage(10 + attackIncrease);
			
			mimic.damage(10 + attackIncrease);
			
			System.out.println("slash");
				
			attackDelay = true;
			canAttack = false;
		}
		
		if(attackHud[1].contains(mx, my) && hero.heroMana() >= 50) {
			
			hero.decreaseMana(50);
			
			hero.fireAttack = true;
			
			if(wolf.inFight) wolf.damage(30 + attackIncrease);
			if(viking.inFight) viking.damage(30 + attackIncrease);
			if(mimic.inFight) mimic.damage(30 + attackIncrease);
			if(mimic.inFight) mimic.damage(30 + attackIncrease);
			System.out.println("Fire");
			
			attackDelay = true;
			canAttack = false;
		}
		
		if(attackHud[2].contains(mx, my) && hero.heroMana() >= 100) {
			
			hero.decreaseMana(100);
			
			hero.flareAttack = true;
			if(wolf.inFight) wolf.damage(50 + attackIncrease);
			if(viking.inFight) viking.damage(50 + attackIncrease);
			if(mimic.inFight) mimic.damage(50 + attackIncrease);
			
			System.out.println("Flare");
			
			canAttack = false;
			
			screenShake = true;
		}
		
		
		if(attackHud[3].contains(mx, my) && hero.heroMana() >= 150) {
			
			hero.decreaseMana(150);
			hero.increaseHealth(100);
			
			hero.healHealth = true;
			
//			wolf.damage (0);
//			viking.damage(0);
//			mimic.damage(0);
//			
			System.out.println("Heal");
			
			attackDelay = true;
			
			canAttack = false;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
