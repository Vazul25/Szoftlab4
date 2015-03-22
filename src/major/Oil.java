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
		System.out.println("> \t ->[:Oil].Oil(int,int):");		
		System.out.println("< \t <-[:Oil].Oil(int,int)");
	}
	

	/**
	 * Effect függvény
	 * 
	 * Felelősség:
	 * Meghatározza, milyen hatással van a robotra, ha beleugrik egy olajfoltba. Ebben az esetben letiltja 
	 * a játékost, hogy irányt váltson.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A Phoebe hivja meg a robotra ami ütközött. Robot.collisionWithObstacle()
	 *
	 * @param r Azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat
	 * 	
	 */
	@Override
	public void effect(Robot r) {
		System.out.println(">\t\t\t->[:Oil].effect(Robot):");
		r.setOiled();
		System.out.println("<\t\t\t<-[:Oil].effect(Robot)");
	}
}
