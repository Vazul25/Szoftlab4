package major;
/**
 * Glue osztály
 * 
 * Felelősség:
 * Ez az objektum az Obstacle osztály leszármazottja. Hasonlóan a Glue objektumhoz, egy adott hatást 
 * valósít meg, ami letiltja a következő körben történő irányítását a robotnak, ami belelépett.
 * Ha belelép egy játékos egy ilyen olajfoltba, az effect függvény letiltja a mozgatást az adott roboton a 
 * következő ugrásig.
 * 
 * Ősosztályok: 
 * Unit <- Obstacle
 *
 * Interfésze:
 * Unit-ból származott IVisible
 *  
 */
public class Oil extends Obstacle {

	/**
	 * Oil kontruktor
	 *
	 * Felelősség:
	 * Egy Oil elem létrehozásáért felelős.
	 *
	 * @param x kezdő koordináta
	 * @param y kezdő koordináta
	 
	 * Funkció(ki hívja meg és mikor?):
	 * A játék motor hívja meg mikor új olajat tesznek a pályára.
	 * 	
	 */
	public Oil(int x, int y) {
		super(x, y);
	
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * Effect függvény
	 * 
	 * Felelősség:
	 * Meghatározza, milyen hatással van a robotra, ha beleugrik egy olajfoltba. Ebben az esetben letiltja 
	 * a játékost, hogy irányt váltson.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A Phoebe hivja meg a robotra ami ütközött.
	 *
	 * @param r Azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat
	 * 	
	 */
	@Override
	public void effect(Robot r) {
		// TODO Auto-generated method stub
		System.out.println("\t->[:Oil].effect(robots.get(0)):");
		r.setOiled();
		System.out.println("\t<-[:Oil].effect(robots.get(0)):");
	}
}
