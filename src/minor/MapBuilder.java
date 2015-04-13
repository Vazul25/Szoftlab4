package minor;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;
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
	private Area map;
	
	/**
	* Tárolja a checkpointokat reprezentáló objektumokat List 
	* adatszerkezetben. 
	*/
	private List<Shape> checkpoints;
	
	public List<Rectangle> paintableObjects;
	
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
	public Area getMap() {
		return map;
	}

	/**
	 * 
	 * A pályát reprezentáló objektum set-tere.
	 * @param map the map to set
	 */
	public void setMap(Area map) {
		this.map = map;
	}
	
	/*
	 * robotOutsideOfMap függvény
	 * 
	 *  @return igaz értékkel tér vissza, ha a robot leesett a pályáról
	 */
	public boolean robotOutsideOfMap(Robot r){
		Area area = new Area(map);
		Area otherArea = new Area(r.getHitbox());
		area.intersect(otherArea);
		//TODO revision
		return area.getBounds().getSize().equals(r.getHitbox().getBounds().getSize());
	}
	
	/*
	 * obstacleOutsideOfMap függvény
	 * 
	 *  @return igaz értékkel tér vissza, ha a robot leesett a pályáról
	 */
	public boolean obstacleOutsideOfMap(Obstacle r){
		Area area = new Area(map);
		Area otherArea = new Area(r.getHitbox());
		area.intersect(otherArea);
		//TODO revision
		return area.getBounds().getSize().equals( r.getHitbox().getBounds().getSize() );

	
	}

	public int[] getStartPosPlayer(int id) {
		//TODO
		return startPosPlayerOne;
	}
	
	public void building(int windowWidth, int windowHeight){
		
		Rectangle outerEdge = new Rectangle(0, 0, windowWidth, windowHeight);
		Rectangle innerEdge = new Rectangle(new Double(windowWidth*0.15).intValue(), new Double(windowHeight*0.2).intValue(),
											new Double(windowWidth*0.7).intValue(), new Double(windowHeight*0.6).intValue());
		//ábra megtalálható: TODO
		
		int checkpointWidth = new Double(windowWidth*0.15).intValue();
		int checkpointHeight = new Double(windowHeight*0.2).intValue();
		
		Rectangle checkpoint0 = new Rectangle(0, 0, checkpointWidth, checkpointHeight);
		
		Rectangle checkpoint1 = new Rectangle(new Double(windowWidth*0.85).intValue(), 0, checkpointWidth, checkpointHeight);
		
		Rectangle checkpoint2 = new Rectangle(new Double(windowWidth*0.85).intValue(), new Double(windowHeight*0.8).intValue(), checkpointWidth, checkpointHeight);
		
		Rectangle checkpoint3 = new Rectangle(0, new Double(windowHeight*0.8).intValue(), checkpointWidth, checkpointHeight);
		
		//értékadás, TODO lecserélni konstruktorban szerializált elemek betöltésére fájlból
		
		//Logikai reprezentáció (ezzel kell ellenőrizni), de nem ezt rajzoljuk ki
		map = new Area(outerEdge);
		map.subtract(new Area(innerEdge));		
		
		//Ezeket rajzoljuk ki
		paintableObjects = new ArrayList<Rectangle>();
		paintableObjects.add(outerEdge);
		paintableObjects.add(innerEdge);
		paintableObjects.add(checkpoint0);
		paintableObjects.add(checkpoint1);
		paintableObjects.add(checkpoint2);
		paintableObjects.add(checkpoint3);
	}

}
