package major;

import java.awt.Rectangle;

/*
 * Obstacle osztály
 * Felelősség:A pályán/játékosoknál lévő különböző akadályokat(ragacs,olaj esetleg késöbb rakéta) 
 * összefogó ősosztály
 * 
 * Ősosztályok: Unit
 * 
 * Interfészek:még nincs
 *  
 */

public abstract class Obstacle extends Unit {
	protected static  int WIDTH;
	protected static int HEIGHT;
	/*
	 * Obstacle kontruktor
	 * Felelősség:meghívja a unit konstruktorát a megadott adatokkal
	 * @param x kezdő koordináta
	 * @param y kezdő koordináta
	 * @param imagelocation az akadályhoz tartozó kép helye
	 * Funkció(ki hívja meg és mikor?):a leszármaztatott osztályok a konstruktoraikban
	 * 	
	 */
	public Obstacle(int x, int y) {
		super(x, y);
		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Effect függvény (abstract)
	 * Felelősség:ez határozza meg hogy milyen hatással van a robotra ha érintkezik egy ilyen tárgyal
	 * @param r azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat
	 * Funkció(ki hívja meg és mikor?):ütközéskor hívja meg az ütközést vizsgáló függvénye a Robot osztálynak
	 * 	
	 */
	public abstract  void effect(Robot r);
	
	/*
	 * Move függvény
	 * @see major.Unit#move()
	 * Felelősség:Amennyiben kell mozgatni az akadályokat(pl rakéta) ez fogja 
	 * elvégezni(ha nem akkor üres függvény) 
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 	
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}
	


}
