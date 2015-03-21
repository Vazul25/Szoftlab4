package major;

/**
 * A „Glue” objektum megvalósít egy adott tulajdonságú 
 * akadályt. Amely robot belemegy, annak a sebességét megfelezi. 
 *
 * Felelősség: 
 * A pályára lerakható ragacs megvalósítása.
 * A játékban szereplő Ragacs foltok viselkedését leíró osztály.
 * 
 * Ősosztályok: 
 * Az Obstacle ami a Unit leszármazottja
 * 
 * Interfészek:
 * nincs 
 * @see major.Obstacle
 */

public class Glue extends Obstacle {

	/**
	 * Felelősség:
	 * Egy Glue elem létrehozása
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A játék motor hívja meg mikor új ragacsot tesznek a pályára.
	 *
	 * @see major.Unit#Unit()
	 * @param x kezdő koordináta
	 * @param y kezdő koordináta
	 */
	public Glue(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Felelősség:
	 * Ez határozza meg hogy milyen hatással van a robotra a ragacs
	 * Funkció(ki hívja meg és mikor?):Ütközéskor hívja meg az ütközést vizsgáló függvénye a Robot osztálynak.
	 * @param r azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat	 
	 * 
	 * @see major.Obstacle#effect(major.Robot)	 
	 */
	@Override
	public void effect(Robot r) {
		System.out.println("\t->[:Glue].effect(robots.get(0)):");
		r.setGlue();
		System.out.println("\t<-[:Glue].effect(robots.get(0)):");
	}
}
