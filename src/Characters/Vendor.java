package Characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Objects.Animation;
import Objects.Coin;
import Objects.Rect;

public class Vendor extends Rect implements MouseListener{
	
	private int mx;
	private int my;
	
	private int delay = 0;
	
	private Hero hero;
	private Coin coin;

	private Rect shop;
	private Rect [] shopItems;
	private String[] shopDescription;
	
	public Rect shopArea;
	public boolean powerUP = false;
	
	private int vendorX;
	private int vendorY;
	
	private boolean canBuy1 = true;
	private boolean canBuy2 = true;
	private boolean canBuy3 = true;
	
	public boolean showShop = false;
	
	Animation vendor = new Animation("Vendor/Merchant_Idle0", 6, 10);
	
	public Vendor(int x, int y, int w, int h) {
		
		super(x, y, w - 40, h);
		
		vendorX = x;
		vendorY = y;
		
		shopArea = new Rect(x, y, w + 50, h);
		
		shop = new Rect(100, 70, 500, 800);
		
		shopItems = new Rect [] {
					new Rect(200, 100, 100, 100),
					new Rect(200, 225, 100, 100),
					new Rect(200, 350, 100, 100),
					new Rect(200, 475, 100, 100),
					new Rect(200, 600, 100, 100),
			
		};
		
		shopDescription = new String [] {"[ 200 coins] Increase max health by 100",
										 "[ 200 coins] Increase damage by 10",
										 "[ 100 coins] Replenish max health",
										 "[ 200 coins] Increase max mana by 50",
										 "[ 100 coins] Replenish mana by 50"
		};
		
	}
	
	public void setLocation(int x, int y) {
		
		super.setX(x);
		super.setY(y);
	}
	
	public void getHero(Hero hero) {
		
		this.hero = hero;
	}
	
	public void getCoin(Coin coin) {
		
		this.coin = coin;
	}
	
	public void vendorTalk(Graphics pen) {
		
		delay ++;
		
		pen.setColor(Color.WHITE);
		
		if(delay <= 49) {
			
			pen.drawString("Welcome", vendorX - 13, vendorY - 20);
		}
		
		if(delay >= 50) {
			
			pen.drawString("Selling goods", vendorX - 30, vendorY - 20);
		}
		
		if(delay == 200) delay = 0;
	}
	
	public void draw(Graphics pen) {
		
		if(showShop) {
			shop.draw(pen);
			
			for(int i = 0; i < shopItems.length; i++) {
				
				shopItems[i].draw(pen);
				
				pen.drawString(shopDescription[i], 200, shopItems[i].getY() - 5);
				
			}
		}
		
//		shopArea.draw(pen);
		else {
			
		
		pen.drawImage(vendor.getCurrentImage(), vendorX, vendorY, 100, 100, null);
		vendorTalk(pen);
		}
//		super.draw(pen);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		mx = e.getX();
		my = e.getY();
		
		if(shopItems[0].contains(mx, my) && canBuy1 && coin.getBalance() >= 200) {
			
			hero.increaseMax();
			hero.bought(200);
			System.out.println("Health Increased");
			
			canBuy1 = false;
		}
		
		if(shopItems[1].contains(mx, my) && canBuy2 && coin.getBalance() >= 200) {
			
			hero.bought(200);
			System.out.println("Damage Increase");
			
			powerUP = true;
			canBuy2 = false;
		}
		
		if(shopItems[2].contains(mx, my) && coin.getBalance() >= 100) {
			
			hero.resetHealth();
			hero.bought(100);
			System.out.println("Health Increased");
		}
		
		if(shopItems[3].contains(mx, my) && canBuy3 && coin.getBalance() >= 100) {
			
			hero.increaseMaxMana(50);
			hero.bought(200);
			System.out.println("Max Mana Increased");
			
			canBuy3 = false;
		}
		
		if(shopItems[4].contains(mx, my) && coin.getBalance() >= 100) {
			
			hero.increaseMana(50);
			hero.bought(100);
			System.out.println("Mana Increased");
			
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
