package major;

import java.awt.Shape;
import java.awt.geom.Area;
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
	
	private int[] checkpointReached;
	private int numOfCheckpoints;
	private int lap;
	private List<Shape> checkpoints;
	private List<Robot> robots;
	
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
		checkpointReached = new int[robots.size()];
		for(int i=0;i<robots.size();i++){
			checkpointReached[i] = 0;
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
		//végig megyünk minden roboton
		for(Robot i : robots){
			//Lekérjük az ID-t
			int robotID = i.getId();
			Area robotarea = new Area(i.getHitbox());
			//következő checkpoint értékének megkeresése
			
			/*	Hibás kód -1-gyel indexelés
			 * Area checkpointarea = new Area(checkpoints.get(checkpointReached[robotID%2]-1));
			 */
			
			robotarea.intersect(checkpointarea);
			
			//Az kezdőhelyen található checkpoint az utolsó
			if(!robotarea.isEmpty()){
				//Ha az utolsó checkpointhoz érkeztünk nullázuk a checkpointokat és növeljük a körök számát eggyel
				if(checkpointReached[robotID%2] == (checkpoints.size()-1)) {
					checkpointReached[robotID%2] = 0;
					lap += 1;
				}
				//Ha belelépünk egy checkpointba akkor nveljük a checkpointReached-et
				else{
					checkpointReached[robotID%2] += 1;
				}
			}
		}		
	}
}
