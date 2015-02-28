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
	
	public HUD(){
		lap = 0;
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

}
