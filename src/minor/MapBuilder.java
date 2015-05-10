package minor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import major.Obstacle;
import major.Robot;
import major.iVisible;

/*
 * MapBuilder osztály
 * Felelősség:
 * Fájlból beolvassa és létrehozza a memóriában a pályát, a 
 * kezdő pozíciókat és a checkpointokat reprezentáló 
 * objektumokat.  Mivel a  MapBuilder objektum tárolja a pályát 
 * így feladat, hogy vizsgálja a robotok azon belül tartózkodását. 
 *  
 */
public class MapBuilder implements iVisible {

	//Adatszerkezetek
	/**
	 * Shape interfészű pályát tároló objektum
	 */
	private Area map;

	/**
	* Tárolja a checkpointokat reprezentáló objektumokat List 
	* adatszerkezetben. 
	*/
	public List<Rectangle> checkpoints;
	
	public Rectangle paintableInnerMap;
	public Rectangle paintableOuterMap;

	protected static BufferedImage img[];

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
		//Kezdő koordináták beolvasása robotonként
		int[] temp = {50, 70};
 		startPosPlayerOne = temp;
 		int[] temp2 = {50, 20};
 		startPosPlayerTwo = temp2;
 		
 		//Fájlból olvasás	
		//Pálya beolvasása
 		try{ 

			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+System.getProperty("file.separator")+"maps"+System.getProperty("file.separator")+"Map.ser"); 
			ObjectInputStream reader = new ObjectInputStream(fis); 
			if(fis != null && reader != null){
				paintableInnerMap = (Rectangle) reader.readObject();
				paintableOuterMap = (Rectangle) reader.readObject();
				reader.close();
				fis.close();
				map = new Area(paintableOuterMap);
				map.subtract(new Area(paintableInnerMap));
			}
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//checkpointok létrehozása
		try{ 
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+System.getProperty("file.separator")+"maps"+System.getProperty("file.separator")+"Checkpoints.ser"); 
			ObjectInputStream reader = new ObjectInputStream(fis); 
			if(fis != null && reader != null){
				List<Rectangle> x = new ArrayList<Rectangle>();
				x = (ArrayList<Rectangle>) reader.readObject(); 
				reader.close();
				fis.close();
				checkpoints = x;
			}
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getCheckpoints függvény
	 * @return visszaadja a Checkpointokat tartalmazó listát
	 */
	public List<Rectangle> getCheckpoints(){
		return checkpoints;
	}


	/*
	 * robotOutsideOfMap függvény
	 * 
	 *  @return igaz értékkel tér vissza, ha a robot leesett a pályáról
	 */
	public boolean robotOutsideOfMap(Robot r){
		Area area = new Area(map);
		
		int partingwidth=r.getHitbox().width/2;
		int partingheight=r.getHitbox().height/2;
		int tempx=r.getHitbox().x+partingwidth/2;
		int tempy=r.getHitbox().y+partingheight/2;
		
		Rectangle hitboxpart=new Rectangle(tempx,tempy,partingwidth,partingheight);
		
		return !area.contains(hitboxpart);

	}

	/*
	 * obstacleOutsideOfMap függvény
	 * 
	 *  @return igaz értékkel tér vissza, ha a robot leesett a pályáról
	 */
	public boolean obstacleOutsideOfMap(Obstacle r){
		Area area = new Area(map);
		return !area.contains(r.getHitbox());
	}

	/**
	 * getStartPosPlayer függvény
	 * @param id Lekérdezendő robot azonosítója
	 * @return Robot 2dimneziós koordinátája
	 */
	public int[] getStartPosPlayer(int id) {
		if(id%2 == 0) return startPosPlayerTwo;
		else return startPosPlayerOne;
	}

	/**
	 * building függvény
	 * Létrehozza a pályát és a checkpointokat rajta.
	 * @param windowWidth Képernyő szélesség
	 * @param windowHeight Képernyő magasság
	 */
	public void building(int windowWidth, int windowHeight){

		//külső téglalap
		Rectangle outerEdge = new Rectangle(0, 0, windowWidth, windowHeight);
		//belső téglalap (szakadék)
		Rectangle innerEdge = new Rectangle(new Double(windowWidth*0.15).intValue(), new Double(windowHeight*0.25).intValue(),
				new Double(windowWidth*0.7).intValue(), new Double(windowHeight*0.5).intValue());

		//Checkpointok szélesség, magassága a pálya méretétől függően
		int checkpointWidth = new Double(windowWidth*0.15).intValue(); // 150/1000
		int checkpointHeight = new Double(windowHeight*0.25).intValue(); // 150/600

		//Checkpointok
		Rectangle checkpoint0 = new Rectangle(0, 0, checkpointWidth, checkpointHeight);
		Rectangle checkpoint1 = new Rectangle(new Double(windowWidth*0.85).intValue(), 0, checkpointWidth, checkpointHeight);
		Rectangle checkpoint2 = new Rectangle(new Double(windowWidth*0.85).intValue(), new Double(windowHeight*0.75).intValue(), checkpointWidth, checkpointHeight);
		Rectangle checkpoint3 = new Rectangle(0, new Double(windowHeight*0.75).intValue(), checkpointWidth, checkpointHeight);

		ArrayList<Rectangle> serializeCheckpoints = new ArrayList<Rectangle>();
		serializeCheckpoints.add(checkpoint0);
		serializeCheckpoints.add(checkpoint1);
		serializeCheckpoints.add(checkpoint2);
		serializeCheckpoints.add(checkpoint3);

		//Szerializáció
		try{
			ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+System.getProperty("file.separator")+"maps"+System.getProperty("file.separator")+"Checkpoints.ser"));
			if(out1 != null){
				out1.writeObject(serializeCheckpoints);
				out1.close();
			}
			ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+System.getProperty("file.separator")+"maps"+System.getProperty("file.separator")+"Map.ser"));
			if(out2 != null){				
				out2.writeObject(innerEdge);
				out2.writeObject(outerEdge);
				out2.close();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * paint függvény
	 * Kirajzolja a mapot.
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.black);
		for(Rectangle tmp : checkpoints){
			g2.drawRect(tmp.x, tmp.y, tmp.width, tmp.height);
		}
		g2.setPaint(Color.red);
		g2.drawRect(paintableInnerMap.x, paintableInnerMap.y, paintableInnerMap.width, paintableInnerMap.height);
		//g2.drawImage(img[0], paintableInnerMap.x, paintableInnerMap.y, paintableInnerMap.width, paintableInnerMap.height, null);
		g2.setPaint(Color.green);
		g2.drawRect(paintableOuterMap.x, paintableOuterMap.y, paintableOuterMap.width, paintableOuterMap.height);		
	}

	/**
	 * listCheckpoints függvény
	 * Visszatér a checkpointok listájával
	 */
	public void listCheckpoints(){
		for(Rectangle i: checkpoints)
			System.out.println("Checkpoint: "+i.x+" "+i.y);
	}
	
}

