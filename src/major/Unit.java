package major;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * Unit osztály
 * Felelősség:
 * 
 * Ősosztályok:
 * 
 * Interfészek:
 * 
 */
public abstract class Unit implements iVisible{
	
	//Atribútumok
	 
	/*
	 * Pozíció koordináták
	 * Felelősség:
	 */
	protected int x,y;
	
	/*
	 * Pályaelem reprezentációja egy robotnak
	 * Felelősség:
	 * 
	 * Melyik függvény használja?:
	 * 
	 */
	protected Rectangle hitbox;
	
	/*
	 * Kép fájl
	 * Felelősség:
	 * 
	 * Melyik függvény használja?
	 * 
	 */

	
	/*
	 * Unit konstruktor
	 * @param x
	 * @param y
	 * @param imagelocation
	 * 
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public Unit(int x,int y){
		//TODO
		this.x=x;
		this.y=y;
	}
	
	/*
	 * Move függvény (abtsract)
	 */
	public abstract void move();

	/*
	 * intersect függvény
	 * @param u
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public boolean intersect(Unit u){
		//Paraméterként kapott Unit hitbox-szal vizsgálat, hogy this.hitbox-szal ütközés történt-e?
		return this.hitbox.intersects(u.hitbox);
	}
	
	
	public Rectangle getHitbox(){
		return hitbox;
	} 
}
