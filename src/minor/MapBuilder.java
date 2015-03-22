package minor;

import java.awt.Shape;
import java.util.List;
import java.util.Scanner;

import major.Obstacle;
import major.Robot;

/*
 * MapBuilder osztály
 * Felelősség:
 * Fájlból beolvassa és létrehozza a memóriában a pályát, a 
 * kezdő pozíciókat és a checkpointokat reprezentáló 
 * objektumokat.  Mivel a  MapBuilder objektum tárolja a pályát 
 * így feladat, hogy vizsgálja a robotok azon belül tartózkodását. 
 *  
 */
public class MapBuilder{
	
	//Adatszerkezetek
	/**
	* Shape interfészű pályát tároló objektum
	*/
	private Shape map;
	
	/**
	* Tárolja a checkpointokat reprezentáló objektumokat List 
	* adatszerkezetben. 
	*/
	private List<Shape> checkpoints = null;
	
	/*
	* Meghatároz egy (x,y) koordinátát, ahol az első játékos kezd.
	*/
	private int[] startPosPlayerOne;
	/*
	* Meghatároz egy (x,y) koordinátát, ahol az második játékos kezd.
	*/
	private int[] startPosPlayerTwo;
	
	/*
	 * MapBuilder konstruktor
	 * Felelősség:
	 * Konstruktor, a pálya beolvasása fájlból, majd létrehozása.
	 * A robotok kezdőkoordinátáját, pályán található checkpointokat.
	 * 
	 * Funkció:
	 * A Játékmotor indulása közben jön létre. A robotoknak 
	 * szolgáltatja a kezdőkoordinátájukat, a HUD-nak 
	 * szolgáltatja a checkpointokat.
	 */
	public MapBuilder(){
		System.out.println("> \t ->[:MapBuilder].MapBuilder():");

		System.out.println("< \t <-[:MapBuilder].MapBuilder()");
	}
	
	/**
	 * getCheckpoints függvény
	 * @return visszaadja a Checkpointokat tartalmazó listát
	 */
	public List<Shape> getCheckpoints(){
		System.out.println("> \t ->[:MapBuilder].getCheckpoints():");
		System.out.println("< \t <-[:MapBuilder].getCheckpoints()");
		return checkpoints;		
	}
	
	/*
	 * robotOutsideOfMap függvény
	 * 
	 *  @return igaz értékkel tér vissza, ha a robot leesett a pályáról
	 */
	public boolean robotOutsideOfMap(Robot r){
		Scanner sc = new Scanner(System.in);
		char temp = '0';
		System.out.println("> \t ->[:MapBuilder].robotOutsideOfMap(Robot):");
		
		while(temp!='i'&& temp!='n'&& temp!='I'&& temp!='N') {
			System.out.print("?\t\t8.1 A robot leesett a pályáról? I/N: ");
			temp=sc.nextLine().charAt(0);
		}
		System.out.println("< \t <-[:MapBuilder].robotOutsideOfMap(Robot)");
		if(temp == 'i' || temp == 'I') return true;
		else return false;
	}
}
