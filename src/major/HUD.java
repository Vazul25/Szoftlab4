package major;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.List;

/**
 * Ez az objektum követi és nyilvántartja, hogy a robotok hány 
 * checkpoint-on mentek át, illetve kiírja a képernyőre a hátramaradó 
 * időt és a megtett körök számát. Feladata, hogy minden körben 
 * megvizsgálja, hogy a robotok elérték-e a következő checkpointot.
 * 
 * Felelősség:
 * A robotok megtett köreit és checkpontjait tartja számon.
 * Megvalósítja a checkpoint ellenőrzést.
 */
public class HUD {
	
	/**
	* Két robot esetén két elemű tömb, ami tárolja a teljesített checkpointokat.
	* Indexelés: Robot.id%2
	* Kezdő érték = 0
	* Max érték = Ahány checkpoint van.
	*/
	private int[] checkpointReached;
	
	/**
	* Két robot esetén két elemű tömb, ami tárolja, hogy hány kört tettek meg a robotok eddig.
	* Indexelés: Robot.id%2
	* Kezdő érték = 0
	*/
	private int[] lap;
	
	/**
	* Shape interfészű, de Polygon objektumokat tároló ArrayList.
	* Ezek a Polygonok a checkpointokat megvalósító objektumok.
	* MapBuilder hívja meg a setCheckpoints(List<Shape> checkobj) és ebben fog 
	* inicializálódni.
	* {@link #checkpointsearch()} függvényben használjuk, hogy a {@link Robot#hitbox}
	*/
	private List<Area> checkpoints;
	private List<Rectangle> checkpoints2;
	
	/**
	* Robot objektumokat tároló ArrayList. Célja, hogy a checkpointsearch() függvényben minden robotra elvégezzük a keresést.
	*/
	private List<Robot> robots;
	
	/**
	* Konstruktor, inicializálja a köröket számláló változót.
	* @param robs A játékban résztvevő robotok listája.
	*/
	public HUD(List<Robot> robs){
		lap = new int[robs.size()];
		
		robots = robs;
		for(Robot i: robots){
			lap[i.getId()%2] = 0;
		}
	}
	/**
	 * checkpoint Setter függvény
	 *
	 * Felelősség:
	 * Checkpointokat reprezentáló adatszerkezet betöltése.
	 * int[] checkpointReached inicializálása a checkpointok számától függően.
	 *
	 * Funkció:
	 * Phoebe hívja meg, miután lekérdezte a MapBuildertől a checkpointok tömbjét.
	 */
	public void setCheckpoints(List<Area> list){
		//Checkpointokat teljesítését számontartó adatszerkezet inicialziálása
		checkpointReached = new int[robots.size()];
		for(int i=0;i<robots.size();i++){
			checkpointReached[i] = 0;
		}
		//Checkpointok tárolása
		checkpoints = list;
	}
	
	
	
	
	public void setCheckpoints2(List<Rectangle> list){
		checkpointReached = new int[robots.size()];
		for(int i=0;i<robots.size();i++){
			checkpointReached[i] = 0;
		}
		checkpoints2 = list;
	}
	
	
	
	
	/**
	* setCheckpointsReached metódus
	*
	* Felelősség: 
	* Ha a paraméterként átadott robot következő 
	* checkpointja a célvonal (utolsó checkpoint) akkor 
	* lenullázza a checkpointReached-et és növeli a megtett 
	* körök számát, illetve ha nem akkor növeli az érintett 
	* checkpointok számát. 
	*
	* Funkció: 
	* A chechkpointSearch nevü metódus hívja meg, ha érzékelt 
	* következő checkpointtal ütközést.	
	* 
	* @param r Az a Robot, amelyik elérte a következő checkpointot. 
	*/
	private void setCheckpointReached(Robot r){
		int robotID = r.getId();
	//Ha az utolsó checkpointhoz érkeztünk nullázuk a checkpointokat és növeljük a körök számát eggyel
		if(checkpointReached[robotID%2] == (checkpoints.size()-1)) {
			checkpointReached[robotID%2] = 0;
			lap[robotID%2] += 1;
		}
	//Ha belelépünk egy checkpointba akkor növeljük a checkpointReached-et
		else{
			checkpointReached[robotID%2] += 1;
		}
	}
	
	/**
	 * checkpointSearch függvény
	 * 
	 * Felelősség:
	 * Minden híváskor ellenőrzi, hogy a robot és a checkpoint metszete üres-e.
	 * Ha nem üres, meghívja a setCheckpointReached metódust. 
	 * Ha eléri a checkpointot egy robot, akkor kap egy 
	 * olajat és egy ragacsot a készletébe.
	 * 
	 * Funkció: 
	 * A játékmotor hívja meg minden ciklusban, lépés után. 
	 */
	public void checkpointSearch(){
	//minden lépésnél vizsgáljuk, hogy benne van-e valamelyik robot a következő teljesítendő checkpoint mezőben
		for(Robot i : robots){			
	//kiszámoljuk a következő checkpoint indexét
			int nextCheckpoint;
			if(checkpointReached[i.getId()%2] < (checkpoints.size()-1) ){
				nextCheckpoint = checkpointReached[i.getId()%2] + 1;
			}
			else{
				nextCheckpoint = 0;
			}
			
	//TODO tesztelés
			Area robotarea = new Area(i.getHitbox());
			Area checkpointarea = new Area(checkpoints.get(nextCheckpoint)); 
			robotarea.intersect(checkpointarea);
			
	//Ha a checkpoint és a robot metszik egymást, akkor növeljük a 
	//náluk levő olaj, ragacskészletet és feljegyezzük a 
	//checkpoint teljesítését
			if(!robotarea.isEmpty()){
				System.out.println("Siker");
				setCheckpointReached(i);
				i.incNumGlue();
				i.incNumOil();
			}
		}		
	}
	
	
	
	public void checkpointSearch2(){
		
			for(Robot i : robots){			
				int nextCheckpoint;
				if(checkpointReached[i.getId()%2] < (checkpoints2.size()-1) ){
					nextCheckpoint = checkpointReached[i.getId()%2] + 1;
				}
				else{
					nextCheckpoint = 0;
				}
				
	
				Rectangle robotarea = new Rectangle(i.getHitbox());
				Rectangle checkpointarea = new Rectangle(checkpoints2.get(nextCheckpoint)); 
				robotarea.intersects(checkpointarea);
				
				for(Rectangle checks: checkpoints2)
					if(robotarea.intersects(checks)) System.out.print("There was a collision between:\n"+i.toString()+"\nand this checkpoint:"+checks.x+" "+checks.y);
				
	
				/*if(!robotarea.isEmpty()){
					setCheckpointReached(i);
					i.incNumGlue();
					i.incNumOil();
				}*/
			}		
		}
		
	
	/*
	 * endOfTheGame függvény
	 * Felelősség:
	 * A játék végén eldönti, hogy melyik játékos nyert. Visszatér egy számmal, 
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
