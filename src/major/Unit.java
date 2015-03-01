package major;

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
public abstract class Unit {
	
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
	protected static BufferedImage image;
	
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
	public Unit(int x,int y,String imagelocation){
		//TODO
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
		return false;
	}
	
	public Rectangle getHitbox(){
		return hitbox;
	} 
}
