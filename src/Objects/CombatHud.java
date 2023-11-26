package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import Characters.Hero;
import Characters.Wolf;

public class CombatHud extends Rect implements MouseListener {
	
	private int mx;
	private int my;
	
	private boolean screenShake = false;
	private Random random = new Random();
	private int delay = 0;
	private boolean attackDelay = false;
	
	public static boolean showHud = false;
	
	public boolean canAttack = true;
	
	private Rect [] attackHud;
	
	Hero hero;
	
	Wolf wolf;
	
	private Rect border;
	
	public CombatHud() {
		
		int x = 900;
		int y = 675;
		
		int width = 300;
		int height = 100;
		
		attackHud = new Rect [] {
					new Rect(x, y, width, height),
					new Rect(x, y + 150, width, height),
					new Rect(x + 400, y, width, height),
					new Rect(x + 400, y + 150, width, height)
					};
		
		border = new Rect(-100, 650, 2100, 500);
	}
	
	public void setDelay(int x) {
		
		 this.delay = x;
	}
	
	public void getHero(Hero hero) {
		
		this.hero = hero;
	}
	
	public void getWolf(Wolf wolf) {
		
		this.wolf = wolf;
	}

	public void draw(Graphics pen) {
		
		if(!screenShake) {
		pen.setColor(Color.RED);
		pen.fillRect(border.x, border.y, border.w, border.h);
		
		
		for	(int i = 0; i < attackHud.length; i++) {
			pen.setColor(Color.BLACK);
			attackHud[i].draw(pen);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		
		mx = e.getX();
		my = e.getY();
		
		if(attackHud[0].contains(mx, my) && canAttack) {
			
			hero.slash = true;
			wolf.damage(30);
			
			System.out.println("slash");
				
			attackDelay = true;
			canAttack = false;
		}
		
		if(attackHud[1].contains(mx, my)) {
			
			hero.fireAttack = true;
			wolf.damage(50);
				
			System.out.println("Fire");
			
			attackDelay = true;
			canAttack = false;
		}
		
		if(attackHud[2].contains(mx, my)) {
			
			hero.flareAttack = true;
			wolf.damage(100);
			
			System.out.println("Flare");
			
			canAttack = false;
			
			screenShake = true;
		}
		
		if(attackHud[3].contains(mx, my)) {
			
			hero.healHealth = true;
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
