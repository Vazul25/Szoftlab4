package major;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.List;

/**
 * Ez az objektum követi és nyilvántartja, hogy a robotok hány checkpoint-on mentek át, 
 * mennyi olaj és ragacs van náluk amit felhasználhatnak, illetve kiírja a képernyőre a 
 * hátramaradó időt és a megtett körök számát. Feladata, hogy minden körben megvizsgálja, 
 * hogy a robotok elérték-e a következő checkpointot.
 * 
 * Felelősség:
 * A robotok ragacs- és olajkészletét, illetve megtett köreit és checkpontjait számolja. 
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
	* Két robot esetén két elemű tömb, ami tárolja a még felhasználatlan ragacsokat.
	* Indexelés: Robot.id%2
	* Kezdő érték = 1
	* Maximális érték = 3
	*/
	//TODO kell három függvény hozzá (növelés, csökkentés, isZero)
	private int[] numGlue;
	
	/**
	* Két robot esetén két elemű tömb, ami tárolja a még felhasználatlan olajfoltokat.
	* Indexelés: Robot.id%2
	* Kezdő érték = 1
	* Maximális érték = 3
	*/
	//TODO kell három függvény hozzá (növelés, csökkentés, isZero)
	private int[] numOil;
	
	/**
	* Két robot esetén két elemű tömb, ami tárolja, hogy hány kört tettek meg a robotok eddig.
	* Indexelés: Robot.id%2
	* Kezdő érték = 0
	*/
	private int[] lap;
	
	/**
	* {@link Shape} interfészű, de {@link Polygon} objektumokat tároló ArrayList.
	* Ezek a Polygonok a checkpointokat megvalósító objektumok.
	* MapBuilder hívja meg a setCheckpoints(List<Shape> checkobj) és ebben fog 
	* inicializálódni.
	* {@link #checkpointsearch()} függvényben használjuk, hogy a {@link Robot#hitbox}
	*/
	private List<Shape> checkpoints;
	
	/**
	* {@see Robot} objektumokat tároló ArrayList. Célja, hogy a {@see #checkpointsearch()} függvényben minden robotra elvégezzük a keresést.
	*/
	private List<Robot> robots;
	
	/**
	* Konstruktor, inicializálja a köröket számláló változót, az érintett checkpointokat, a 
	* ragacs és olajkészleteket.
	* @param robs A játékban résztvevő robotok listája.
	*/
	public HUD(List<Robot> robs){
		lap = 0;
		robots = robs;
		numGlue = new int[robots.size()];
		numOil = new int[robots.size()];
		for(Robot i: robots){
			numGlue[i.getId()%2] = 3;
			numOil[i.getId()%2] = 3;
		}
	}
	/**
	 * checkpoint Setter függvény
	 *
	 * Felelősség:
	 * Checkpointokat reprezentáló adatszerkezet betöltése.
	 * int[] checkpointReached inicializálása a checkpointok számától függően.
	 * Funkció:
	 * Phoebe hívja meg, miután lekérdezte a MapBuildertől a checkpointok tömbjét.
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
	
	/**
	 * Ellenőrzi hogy a robotok teljesítették-e a következő checkpointot.
	 * 
	 * Felelősség:
	 * Minden híváskor ellenőrzi, hogy a robot és a checkpoint metszete üres-e.
	 * Ha nem üres, akkor ezt bevezeti a checkpointReached változóba.
	 * 
	 * Funkció: 
	 * A játékmotor hívja meg minden ciklusban, lépés után. 
	 */
	public void checkpointSearch(){
		for(Robot i : robots){
			int robotID = i.getId();
			Area robotarea = new Area(i.getHitbox());
			//következő checkpoint értékének megkeresése
			
			//	Hibás kód -1-gyel indexelés
			//  Area checkpointarea = new Area(checkpoints.get(checkpointReached[robotID%2]-1));			
			
			//A robotarea = robotarea és a checkpointarea metszete
			//robotarea.intersect(checkpointarea);
			
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
