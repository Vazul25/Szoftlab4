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
	private int[] numGlue;
	private int[] numOil;
	private int numOfCheckpoints;
	private int[] lap;
	private List<Shape> checkpoints;
	private List<Robot> robots;
	
	public HUD(List<Robot> robs){
		lap = new int[robs.size()];
		
		robots = robs;
		numGlue = new int[robots.size()];
		numOil = new int[robots.size()];
		for(Robot i: robots){
			lap[i.getId()%2] = 0;
			numGlue[i.getId()%2] = 3;
			numOil[i.getId()%2] = 3;
		}
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
	
	private void setCheckpointReached(Robot r){
		int robotID = r.getId();
		//Ha az utolsó checkpointhoz érkeztünk nullázuk a checkpointokat és növeljük a körök számát eggyel
		if(checkpointReached[robotID%2] == (checkpoints.size()-1)) {
			checkpointReached[robotID%2] = 0;
			lap[robotID%2] += 1;
		}
		//Ha belelépünk egy checkpointba akkor nveljük a checkpointReached-et
		else{
			checkpointReached[robotID%2] += 1;
		}
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
			Area robotarea = new Area(i.getHitbox());
			//TODO ki kell keresni a következő checkpointot, majd annak a hitbox-ával összemetszeni a robot hitboxát
			/*	Hibás kód -1-gyel indexelés
			 * Area checkpointarea = new Area(checkpoints.get(checkpointReached[robotID%2]-1));
			 */
			
			//robotarea.intersect(checkpointarea);
			
			//Az kezdőhelyen található checkpoint az utolsó
			if(!robotarea.isEmpty()){
				setCheckpointReached(i);
			}
		}		
	}
	
	/*
	 * endOfTheGame függvény
	 * Felelősség:
	 * A játé végén eldönti, hogy melyik játékos nyert. Visszatér egy számmal, 
	 * amiből egyértelműen eldönthető, hogy ki nyert. Ha negatív akkor az 1-es számú játékos nyert, 
	 * ha nulla akkor döntetlen, ha pozitív akkor a 2-es számú játékos nyert.
	 * 
	 * Funkció: Amikor a Phoebe ended értéke igazzá válik, a run metódus fogja meghívni.
	 * 
	 */
	public int endOfTheGame(){
		return 0;
	}
}
