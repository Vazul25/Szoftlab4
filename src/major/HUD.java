package major;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.List;
import java.util.Scanner;

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
	public void setCheckpoints(List<Shape> checkObj){
		//Checkpointokat teljesítését számontartó adatszerkezet inicialziálása
		//numOfCheckpoints = checkObj.size();
		checkpointReached = new int[robots.size()];
		for(int i=0;i<robots.size();i++){
			checkpointReached[i] = 0;
		}
		//Checkpointok tárolása
		checkpoints = checkObj;
		
		//
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
		System.out.println("\t\t\t->[:HUD].setCheckpointReached(robots.get.("+r.getId()+"):");
		int robotID = r.getId();
		//Ha az utolsó checkpointhoz érkeztünk nullázuk a checkpointokat és növeljük a körök számát eggyel
		//Nincsenek még checkpointjaink - szkeleton fordításhoz legyen 5 db
		if(checkpointReached[robotID%2] == (4)) { //4 = checkpoints.size()-1
			checkpointReached[robotID%2] = 0;
			lap[robotID%2] += 1;
		}
		//Ha belelépünk egy checkpointba akkor növeljük a checkpointReached-et
		else{
			checkpointReached[robotID%2] += 1;
		}
		System.out.println("\t\t\t<-[:HUD].setCheckpointReached(robots.get.("+r.getId()+"):");
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
		System.out.println("\t->[:HUD].chechpointSearch():");
		for(Robot i : robots){			
			//kiszámoljuk a következő checkpoint indexét
			//Nincsenek még checkpointjaink - szkeleton fordításhoz legyen 5 db
			int nextCheckpoint;
			if(checkpointReached[i.getId()%2] < (4) ){ // 4 = checkpoints.size()-1
				nextCheckpoint = checkpointReached[i.getId()%2] + 1;
			}
			else{
				nextCheckpoint = 0;
			}
			
			Area robotarea = new Area(i.getHitbox());
			//Nem tudunk intersectelni, mert nincsenek checkpointok
			/*Area checkpointarea = new Area(checkpoints.get(nextCheckpoint)); 
			robotarea.intersect(checkpointarea);*/
				
			//Ha a checkpoint és a robot metszik egymást, akkor növeljük a 
			//náluk levő olaj, ragacskészletet és feljegyezzük a 
			//checkpoint teljesítését
			Scanner sc = new Scanner(System.in);
			int temp='0';
			while(temp!='i'&& temp!='n' && temp!='I' && temp!='N'){
				System.out.print("\t\t\t Elérte a Robot"+i.getId()%2" a következő checkpointot? I/N:");
				temp=sc.nextLine().charAt(0);
					
			}
			if(temp=='i'||temp=='I'){
				setCheckpointReached(i);
				i.incNumGlue();
				i.incNumOil();
			}

		}		
		System.out.println("\t<-[:HUD].chechpointSearch():");
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
