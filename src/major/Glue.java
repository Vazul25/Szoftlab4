package major;

/*
 * Glue osztály
 * Felelősség:
 * 
 * Ősosztályok:
 * 
 * Interfészek:
 *  
 */
public class Glue extends Obstacle {

	/*
	 * Obstacle kontruktor
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 	
	 */
	public Glue(int x, int y, String imagelocation) {
		super(x, y, imagelocation);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Effect függvény
	 * @see major.Obstacle#effect(major.Robot)
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 *
	 */
	@Override
	public void effect(Robot r) {
		// TODO Auto-generated method stub
		r.setGlue();
	}



}
