package Objects;

import java.awt.Color;
import java.awt.Graphics;

public class Rect {
	
	int x;
	int y;
	int w;
	int h;
	
	int vx = 0;
	int vy = 0;
	
	Color c;
	
	public Rect() {
		
	}

	public Rect(int x, int y, int w, int h) {
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public int getX() {
		
		return this.x;
		
	}
	
	public int getY() {
		
		return this.y;
	}
	
	public void moveUP(int dy) {
	    this.y += -dy;
	    this.vy = -dy;
	}

	public void moveDN(int dy) {
	    this.y += dy;
	    this.vy = dy;
	}

	public void moveLT(int dx) {
	    this.x += -dx;
	    this.vx = -dx;
	}

	public void moveRT(int dx) {
	    this.x += dx;
	    this.vx = dx;
	}
	
	public boolean overlaps(Rect r) {
		
		return (x <= r.x + r.w) && 
			   (y <= r.y + r.h) && 
			   (x + w >= r.x  ) && 
			   (y + h >= r.y  );
	}
	
	public boolean wasLeftOf(Rect r)
	{
		return x - vx < r.x - w + 1;
	}
	
	public boolean wasRightOf(Rect r)
	{
		return x - vx > r.x + r.w - 1;
	}
	
	public boolean wasAbove(Rect r)
	{
		return y - vy < r.y - h + 1;
	}
	
	public boolean wasBelow(Rect r)
	{
		return y - vy > r.y + r.h - 1;
	}
	
	public void pushedOutOf(Rect r)
	{
		if(wasLeftOf(r))    pushLeftOf(r);
		if(wasRightOf(r))   pushRightOf(r);
		if(wasAbove(r))     pushAbove(r);
		if(wasBelow(r))     pushBelow(r);
	}
	
	public void pushLeftOf(Rect r)
	{
		this.x = r.x - w - 1;
	}
	
	public void pushRightOf(Rect r)
	{
		this.x = r.x + r.w + 1;
	}
	
	public void pushAbove(Rect r)
	{
		this.y = r.y - h - 1;
	}
	
	public void pushBelow(Rect r)
	{
		this.y = r.y +  r.h + 1;
	}
	
	public void setColor(Color c) {
		
		this.c = c;
	}
	
	public boolean isLeftOf(Rect r)
	{
		return x < r.x - w + 1;
	}
	public boolean isRightOf(Rect r)
	{
		return x > r.x + r.w - 1;
	}
	
	public boolean isAbove(Rect r)
	{
		return y < r.y - h + 1;
	}
	
	public boolean isBelow(Rect r)
	{
		return y > r.y + r.h - 1;
	}
	
	public void chase(Rect r)
	{
		if(r.isAbove  (this))   moveUP(4);
		if(r.isBelow  (this))   moveDN(4);
		if(r.isLeftOf (this))   moveLT(4);
		if(r.isRightOf(this))   moveRT(4);
	}
	
	public boolean contains(int mx, int my)
	{
		return (mx >= x  )   && 
			   (mx <= x+w)   && 			   
			   (my >= y  )   && 
			   (my <= y+h);
	}
	
	public void draw(Graphics pen) {
		
		pen.setColor(c);
		pen.drawRect(x, y, w, h);
	}
}