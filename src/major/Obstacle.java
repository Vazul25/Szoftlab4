package major;

/*
 * Obstacle osztály
 * Felelősség:
 * 
 * Ősosztályok:
 * 
 * Interfészek:
 *  
 */
public abstract class Obstacle extends Unit {

	/*
	 * Obstacle kontruktor
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 	
	 */
	public Obstacle(int x, int y, String imagelocation) {
		super(x, y, imagelocation);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Effekt függvény (abstract)
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 	
	 */
	public abstract  void effect(Robot r);
	
	/*
	 * Move függvény
	 * @see major.Unit#move()
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 	
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}



}
