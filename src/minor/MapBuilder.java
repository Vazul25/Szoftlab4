package minor;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.List;

/*
 * MapBuilder osztály
 * Felelősség:
 * 
 * Ősosztály:
 * 
 * Interfészek:
 *  
 */
public class MapBuilder{
	
	//Adatszerkezetek
	private Shape map;
	private List<Shape> checkpoints;
	
	private int[] startPosPlayerOne;
	private int[] startPosPlayerTwo;
	
	/*
	 * MapBuilder konstruktor
	 * Felelősség:
	 * 
	 * Funkció:
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
	
	/*
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
	 * intersectsWithMap függvény
	 * 
	 *  @return igaz értékkel tér vissza, ha a robot leesett a pályáról
	 */
	public boolean fallingDown(Shape othershape){
		/*Area area = new Area(map);
		Area otherArea = new Area(othershape);
		area.intersect(otherArea);
		//TODO revision
		return area.getBounds().getSize().equals(othershape.getBounds().getSize());*/return false;
	}

	public int[] getStartPosPlayer(int id) {
		//TODO
		return startPosPlayerOne;
	}

}
