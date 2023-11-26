package Characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Objects.Animation;
import Objects.Rect;

public class Vendor extends Rect implements MouseListener{
	
	private int mx;
	private int my;
	
	private int delay = 0;
	
	private Hero hero;

	private Rect shop;
	private Rect [] shopItems;
	private String[] shopDescription;
	
	public Rect shopArea;
	
	private int vendorX;
	private int vendorY;
	
	private boolean canBuy = true;
	
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
					new Rect(200, 475, 100, 100)
			
		};
		
		shopDescription = new String [] {"This item will increased your health by 100",
										 "This item will increase your damage",
										 "This item will replenish your health by 50",
										 "This item will leave you broke"
		};
		
	}
	
	public void getHero(Hero hero) {
		
		this.hero = hero;
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
		
		shopArea.draw(pen);
		
		pen.drawImage(vendor.getCurrentImage(), vendorX, vendorY, 100, 100, null);
		super.draw(pen);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		mx = e.getX();
		my = e.getY();
		
		if(shopItems[0].contains(mx, my) && canBuy) {
			
			hero.increaseMax();
			hero.bought(300);
			System.out.println("Health Increased");
			
			canBuy = false;
		}
		
		if(shopItems[2].contains(mx, my)) {
			
			hero.increaseHealth(50);
			hero.bought(100);
			System.out.println("Health Increased");
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
