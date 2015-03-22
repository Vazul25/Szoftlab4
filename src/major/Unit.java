package major;

import java.awt.Rectangle;

/*
 * Unit osztály
 * Felelősség:
 * A pályán található objektumokért felel és azok viszonyáról (például ütközésükről).
 * 
 */
public abstract class Unit 
{
	
	//Atribútumok
	 
	/*
	 * Pozíció koordináták
	 * Felelősség:
	 * Az egység (x,y) koordinátájának tárolása
	 */
	protected int x,y;
	
	/*
	 * Pályaelem reprezentációja egy robotnak
	 * Felelősség:
	 * 
	 * Melyik függvény használja?:
	 * intersect(Unit)
	 * 
	 */
	protected Rectangle hitbox;
	
	/*
	 * Unit konstruktor
	 * 
	 * Felelősség:
	 * A Unit osztály konstruktora. Feladata, hogy eltárolja az x,y koordinátát.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * Amikor létrehozunk egy leszármazott osztályt, annak konstruktora.
	 *
	 * @param x X koordináta
	 * @param y Y koordináta 
	 */
	public Unit(int x,int y){
		System.out.println("> \t ->[:Unit].Unit(int,int):");
		this.x=x;
		this.y=y;
		System.out.println("< \t <-[:Unit].Unit(int,int)");
	}
	
	/*
	 * Move függvény (abtsract)
	 * Felelősség:
	 * Absztrakt függvény, mely a leszármazottakban fog megvalósulni. Az egységek mozgásáért felelős.
	 * 
	 * Funkció: 
	 * A Phoebe hívja meg minden körben.
	 */
	public abstract void move();

	/*
	 * intersect függvény
	 * 
	 * Felelősség:
	 * Két egység ütközését meghatározó függvény.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * CollisionWithObstacle(), minden körben
	 * CollisionWithRobot(), minden körben
	 *
	 * @param u Egység, amivel vizsgálni kell az ütközést.
	 */
	public boolean intersect(Unit u){
		//Paraméterként kapott Unit hitbox-szal vizsgálat, hogy this.hitbox-szal ütközés történt-e?		
		return this.hitbox.intersects(u.hitbox);
	}
}
