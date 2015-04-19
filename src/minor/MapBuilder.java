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
	
	//public List<Rectangle> paintableCheckpoints;
	
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
		//Fájlból olvasás		
		//...
		//Kezdő koordináták beolvasása robotonként
		int[] temp = {50, 70};
 		startPosPlayerOne = temp;
 		int[] temp2 = {50, 20};
 		startPosPlayerTwo = temp2;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//checkpointok létrehozása
		//checkpoints = new ArrayList<Area>();
		try{ 
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+System.getProperty("file.separator")+"maps"+System.getProperty("file.separator")+"Checkpoints.ser"); 
			ObjectInputStream reader = new ObjectInputStream(fis); 
			if(fis != null && reader != null){
				List<Rectangle> x = new ArrayList<Rectangle>();
				x = (ArrayList<Rectangle>) reader.readObject(); 
				reader.close();
				fis.close();
				//paintableCheckpoints = x; 
				checkpoints = x;
				/*for(Rectangle tmp : x){
					checkpoints.add(new Area(tmp));
				}*/
			}
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//..
		int numberOfCheckpoints = 5;
		for(int i=1;i<=numberOfCheckpoints;i++){
			//Checkpointot határoló pontok beolvasása
			//...
		//	checkpoints.add(new Polygon()); null pointerekkel nem megy
		}
		//...
		
	}
	
	/**
	 * 
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
		//Area area = new Area(map);
		Rectangle leeso_resz = new Rectangle(400,400,600,700);
		//Area otherArea = new Area(r.getHitbox());
		//area.intersect(otherArea);
		//TODO revision
		//System.out.println();
		return leeso_resz.contains(r.getHitbox());
	}
	
	/*
	 * obstacleOutsideOfMap függvény
	 * 
	 *  @return igaz értékkel tér vissza, ha a robot leesett a pályáról
	 */
	public boolean obstacleOutsideOfMap(Obstacle r){
		Area area = new Area(map);
		Area otherArea = new Area(r.getHitbox());
		area.intersect(otherArea);
		//TODO revision
		return area.getBounds().getSize().equals( r.getHitbox().getBounds().getSize() );

	
	}

	public int[] getStartPosPlayer(int id) {
		if(id%2 == 0) return startPosPlayerTwo;
		else return startPosPlayerOne;
	}
	
	public void building(int windowWidth, int windowHeight){
		
		Rectangle outerEdge = new Rectangle(0, 0, windowWidth, windowHeight);
		Rectangle innerEdge = new Rectangle(new Double(windowWidth*0.15).intValue(), new Double(windowHeight*0.2).intValue(),
											new Double(windowWidth*0.7).intValue(), new Double(windowHeight*0.6).intValue());
		//ábra megtalálható: TODO
		
		int checkpointWidth = new Double(windowWidth*0.15).intValue();
		int checkpointHeight = new Double(windowHeight*0.2).intValue();
		
		Rectangle checkpoint0 = new Rectangle(0, 0, checkpointWidth, checkpointHeight);
		
		Rectangle checkpoint1 = new Rectangle(new Double(windowWidth*0.85).intValue(), 0, checkpointWidth, checkpointHeight);
		
		Rectangle checkpoint2 = new Rectangle(new Double(windowWidth*0.85).intValue(), new Double(windowHeight*0.8).intValue(), checkpointWidth, checkpointHeight);
		
		Rectangle checkpoint3 = new Rectangle(0, new Double(windowHeight*0.8).intValue(), checkpointWidth, checkpointHeight);
		
		//értékadás, TODO lecserélni konstruktorban szerializált elemek betöltésére fájlból
		
		//Logikai reprezentáció (ezzel kell ellenőrizni), de nem ezt rajzoljuk ki
		/*map = new Area(outerEdge);
		map.subtract(new Area(innerEdge));	*/
		
		/*checkpoints = new ArrayList<Area>();
		checkpoints.add(new Area(checkpoint0));
		checkpoints.add(new Area(checkpoint1));
		checkpoints.add(new Area(checkpoint2));
		checkpoints.add(new Area(checkpoint3));*/
		
		//Area nem szerializálható
		ArrayList<Rectangle> serializeCheckpoints = new ArrayList<Rectangle>();
		serializeCheckpoints.add(checkpoint0);
		serializeCheckpoints.add(checkpoint1);
		serializeCheckpoints.add(checkpoint2);
		serializeCheckpoints.add(checkpoint3);
		
		
		//Ezeket rajzoljuk ki
		/*paintableCheckpoints = new ArrayList<Rectangle>();
		paintableCheckpoints.add(checkpoint0);
		paintableCheckpoints.add(checkpoint1);
		paintableCheckpoints.add(checkpoint2);
		paintableCheckpoints.add(checkpoint3);*/
		
		//Szerializáció
		try{
			// Serialize data object to a file
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

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
	
	/*public  static void setUnitImage() throws IOException{
		String sep=System.getProperty("file.separator");
		img=new BufferedImage[2];
		//img[0]=ImageIO.read(new File(System.getProperty("user.dir")+sep+"icons"+sep+"restricted.jpg"));
		//img[1]=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"frog1.jpg"));
	}*/

	public void listCheckpoints(){
		for(Rectangle i: checkpoints)
			System.out.println("Checkpoint: "+i.x+" "+i.y);
	}
	
}

