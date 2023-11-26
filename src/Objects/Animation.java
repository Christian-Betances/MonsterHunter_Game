package Objects;

import java.awt.Image;
import java.awt.Toolkit;

public class Animation {
	
	private Image[] images;
	
	private int current = 0;
	
	private int duration;
	private int delay;
	
	public Animation(String name, int count, int duration) {
		
		this.duration = duration;
		this.delay    = duration;
		
		
		images = new Image[count];
		
		for(int i = 0; i < count; i++)
		{
		    images[i] = Toolkit.getDefaultToolkit().getImage(name + i + ".png");
		}
	}
	
	
	public Image getStaticImage() {
		
		return images[0];
	}
	
	//goes to static image after animation plays
	public boolean animationFinish() {
		
		if(current == images.length - 1) {
			
			current = 0;
			return true;
		}
			return false;
	}

	public Image getCurrentImage() {
		
		delay--;
		
		if(delay == 0)
		{
			current++;
			
			if(current == images.length)  current = 0;
			
			delay = duration;
		}
		
		return images[current];
	}
}