package major;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class Unit {
	int x,y;
	Rectangle hitbox;
	BufferedImage image;
	public Unit(int x,int y,String imagelocation){}//implementation needed
	public abstract void move();
	public abstract boolean intersect();
}
