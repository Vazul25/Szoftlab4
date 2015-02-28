package minor;

import java.awt.Polygon;
import java.awt.Shape;
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
	
	/*
	 * MapBuilder konstruktor
	 * Felelősség:
	 * 
	 * Funkció:
	 */
	public MapBuilder(){
		//Fájlból olvasás		
		//...
		//Polygon létrehozása (pálya)
		int[] xpoints = null;
		int[] ypoints = null;
		int npoints = 0;
		setMap(new Polygon(xpoints, ypoints, npoints));
		
		//checkpointok létrehozása
		//...
		int numberOfCheckpoints = 5;
		for(int i=1;i<=numberOfCheckpoints;i++){
			//Checkpointot határoló pontok beolvasása
			//...
			checkpoints.add(new Polygon());
		}
		//...
		
	}
	
	public List<Shape> getCheckpoints(){
		return checkpoints;
	}

	/**
	 * @return the map
	 */
	public Shape getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Shape map) {
		this.map = map;
	}
	
	

}
