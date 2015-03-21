package major;

import java.awt.Rectangle;

/*
 * Obstacle osztály
 * Felelősség:
 * A pályán/játékosoknál lévő különböző akadályokat (ragacs,olaj) összefogó ősosztály.
 * 
 * Ősosztályok: Unit
 *  
 */

public abstract class Obstacle extends Unit 
//implements iVisible 
{
	/*
	 * WIDTH : Az akadályokat jellemző szélesség. Szükség van rá,
	 *		   hogy létrehozzuk a leszármazottak hitbox-át(sokszög pályaelem).
	 * HEIGHT : Az akadályokat jellemző hosszúság. Szükség van rá, 
	 * 		   hogy létrehozzuk a leszármazottak hitbox-át(sokszög pályaelem).
	 */
	protected static  int WIDTH=40;
	protected static int HEIGHT=40;
	protected int lifetime;//ezt növeljük körönként
	/*
	 * Obstacle kontruktor
	 * 
	 * Felelősség:
	 * Meghívja a Unit konstruktorát a megadott adatokkal és létrehoz egy sokszög elemet ami 
	 * reprezentálja a pályán majd.
	 *
	 * Funkció(ki hívja meg és mikor?):
	 * A leszármaztatott osztályok a konstruktoraikban.
	 * 
	 * @param x kezdő koordináta
	 * @param y kezdő koordináta
	 *	
	 */
	public Obstacle(int x, int y) {
		super(x, y);
		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Effect függvény (abstract)
	 * 
	 * Felelősség:
	 * Meghatározza, milyen hatással van a robotra, ha érintkezik egy Obstacle-lel.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * Ütközéskor hívja meg az ütközést vizsgáló függvénye a Robot osztálynak.
	 *
	 * @param r azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat
	 */
	public abstract  void effect(Robot r);
	
	/*
	 * Move függvény
	 * @see major.Unit#move()
	 * Felelősség:
	 * Mozgatásért felelős függvény. Ősosztályból öröklött, felülírt függvény. 
	 * Mivel nem lehetséges az akadályok mozgása, ezért üres a függvény törzse.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * Rendes működés során sehol nem hívódik meg, csupán azért van implementálva, 
	 * mert absztrakt az ősosztályban
	 * 	
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "Obstacle [x=" + x + ", y=" + y + ", Width=" + WIDTH +", Height=" + HEIGHT + "]";
	}
	


}
