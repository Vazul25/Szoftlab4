package major;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.List;


import minor.Timer;

/*
 * HUD osztály
 * Felelősség:
 * 
 * Ősosztály:
 * 
 * Interfészek:
 *  
 */
public class HUD {
	
	private boolean[] checkpointReached;
	private int numOfCheckpoints;
	private int lap;
	private List<Shape> checkpoints;
	List<Robot> robots;
	
	public HUD(List<Robot> robs){
		lap = 0;
		robots = robs;
	}
	/*
	 * setCheckpoints függvény
	 * Felelősség:
	 * 
	 * Funkció:
	 * 
	 */
	public void setCheckpoints(List<Shape> checkObj){
		//Checkpointokat teljesítését számontartó adatszerkezet inicialziálása
		numOfCheckpoints = checkObj.size();
		checkpointReached = new boolean[numOfCheckpoints];
		for(int i=0;i<numOfCheckpoints;i++){
			checkpointReached[i] = false;
		}
		//Checkpointok tárolása
		checkpoints = checkObj;
		
		//
	}
	
	/*
	 * checkpointSearch függvény
	 * Felelősség:
	 * 
	 * Funkció: Akkor hívódik meg, miután léptünk 
	 * 
	 */
	public void checkpointSearch(){
		//minden lépésnél vizsgál, hogy benne vagyunk-e a következő teljesítendő checkpoint mezőben
		Area area = new Area(robots.get(i).hitbox);//végig iterálni az összesen
		//következő checkpoint értékének megkeresése
		int i=0;
		//...
		Area checkpoint = new Area(checkpoints.get(i));
		area.intersect(checkpoint);
		//A teljesített checkpoint adminisztrálása
		if(!area.isEmpty()) checkpointReached[i] = true; 
		//Ha minden checkpoint teljesítve, akkor lap növelése eggyel és checkpointReached tömb inicializálása
		//...
	}

}
