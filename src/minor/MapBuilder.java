package minor;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.List;

import major.Obstacle;
import major.Robot;

/*
 * MapBuilder osztály
 * Felelősség:
 * Fájlból beolvassa és létrehozza a memóriában a pályát, a 
 * kezdő pozíciókat és a checkpointokat reprezentáló 
 * objektumokat.  Mivel a  MapBuilder objektum tárolja a pályát 
 * így feladat, hogy vizsgálja a robotok azon belül tartózkodását. 
 *  
 */
public class MapBuilder{
	
	//Adatszerkezetek
	/**
	* Shape interfészű pályát tároló objektum
	*/
	private Shape map;
	
	/**
	* Tárolja a checkpointokat reprezentáló objektumokat List 
	* adatszerkezetben. 
	*/
	private List<Shape> checkpoints;
	
	/*
	* Meghatároz egy (x,y) koordinátát, ahol az első játékos kezd.
	*/
	private int[] startPosPlayerOne;
	/*
	* Meghatároz egy (x,y) koordinátát, ahol az második játékos kezd.
	*/
	private int[] startPosPlayerTwo;
	
	/*
	 * MapBuilder konstruktor
	 * Felelősség:
	 * Konstruktor, a pálya beolvasása fájlból, majd létrehozása.
	 * A robotok kezdőkoordinátáját, pályán található checkpointokat.
	 * 
	 * Funkció:
	 * A Játékmotor indulása közben jön létre. A robotoknak 
	 * szolgáltatja a kezdőkoordinátájukat, a HUD-nak 
	 * szolgáltatja a checkpointokat.
	 */
	public MapBuilder(){
		//Fájlból olvasás		
		//...
		//Kezdő koordináták beolvasása robotonként
		int[] temp = {200, 300};
 		startPosPlayerOne = temp;
 		startPosPlayerTwo = temp;
		//Polygon létrehozása (pálya)
		int[] xpoints = null;
		int[] ypoints = null;
		int npoints = 0;
	//	setMap(new Polygon(xpoints, ypoints, npoints)); // null pointerekkel nem fordul
		
		//checkpointok létrehozása
		//...
		int numberOfCheckpoints = 5;
		for(int i=1;i<=numberOfCheckpoints;i++){
			//Checkpointot határoló pontok beolvasása
			//...
		//	checkpoints.add(new Polygon()); null pointerekkel nem megy
		}
		//...
		
	}
	
	/**
	 * 
	 * @return visszaadja a Checkpointokat tartalmazó listát
	 */
	public List<Shape> getCheckpoints(){
		return checkpoints;
	}

	/**
	 * 
	 * A pályát reprezentáló objetum get-tere
	 * @return the map
	 */
	public Shape getMap() {
		return map;
	}

	/**
	 * 
	 * A pályát reprezentáló objektum set-tere.
	 * @param map the map to set
	 */
	public void setMap(Shape map) {
		this.map = map;
	}
	
	/*
	 * robotOutsideOfMap függvény
	 * 
	 *  @return igaz értékkel tér vissza, ha a robot leesett a pályáról
	 */
	public boolean robotOutsideOfMap(Robot r){
		//Area area = new Area(map);
		//Area otherArea = new Area(r.getHitbox());
		//area.intersect(otherArea);
		//TODO revision
		//return area.getBounds().getSize().equals(r.getHitbox().getBounds().getSize());
		//TODO kérdés
		return true;
	}
	
	/*
	 * obstacleOutsideOfMap függvény
	 * 
	 *  @return igaz értékkel tér vissza, ha a robot leesett a pályáról
	 */
	public boolean obstacleOutsideOfMap(Obstacle r){
		//Area area = new Area(map);
		//Area otherArea = new Area(r.getHitbox());
		//area.intersect(otherArea);
		//TODO revision
		//return area.getBounds().getSize().equals( r.getHitbox().getBounds().getSize() );
		//TODO kérdés
		return true;
	
	}

	public int[] getStartPosPlayer(int id) {
		//TODO
		return startPosPlayerOne;
	}

}
