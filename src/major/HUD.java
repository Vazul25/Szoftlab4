package major;

import java.awt.Shape;
import java.util.List;

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
	Robot robot;
	
	public HUD(Robot r){
		lap = 0;
		robot = r;
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
	 * Funkció:
	 */
	public void checkpointSearch(){
		
	}

}
