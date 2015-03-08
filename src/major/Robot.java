package major;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Robot osztály
 * Felelősség:A játékban részvevő ugráló robotok viselkedését és kezelését leíró osztály
 * 
 * Ősosztály:Unit
 * 
 * Interfészek:még nincs
 *  
 */
public class Robot extends Unit{

	/*
	 * Statikus attribútumok
	 * Mire valók:
	 ** staticid:A robot statikus sorszáma , ebből képezzük a saját azonosító számukat
	 *
	 ** WIDTH,HEIGHT:placeholder majd a robot képének szélessége és magassága lesz bent ,arra kell,
	 *hogy az irányításhoz tartozó nyil jó helyről induljon(de meg lehet úgy is oldani hogy x,y koordinátából 
	 *kivonjuk a buffer image width/2,height/2 adatát)
	 *
	 */
	protected static int staticid=0;
	 private  static final  int r=100; //sugár
	
	static int WIDTH=40;//teszt placeholder
	static int HEIGHT=40;//teszt placeholder
	//kell majd valami adatszerkezet ami számontartja hogy melyik obstacleből mennyi van a robotnál
	protected static BufferedImage img[];
	/*
	 * Azonosító, állapot
	 * Mire valók:
	 ** id:a Robot azonosítására ,ami a keybinding-nál fontos, kell hogy ne vizsgáljon önmagával ütközést
	 *
	 ** slowed: milyen mértékben van lassítva a robot
	 *
	 ** oiled:megcsuszott-e a robot
	 *
	 */
	private int id;
	private double slowed;//default 1.0
	private boolean oiled;
	
	/*
	 * Vektor paraméterek
	 * Mire valók:
	 ** arrowendx:hova mutat a békából kimenő nyil(x koordináta)
	 *
	 ** arrowendy:a békából kimenő nyil y koordinátája
	 *
	 ** alpha: arrowendx,y kiszámításához szükséges vizszintes tengelyel bezárt szög radiánban
	 *
	 */
	private int arrowendx=0;//ahova mutat
	private int arrowendy=0;
	private double alpha=1.57;//kerület pontjának számításához kell radián , alapérték 90 fok
	
	/*
	 * Mire való:
	 ** moved:lépett e már a játékos vagy irányitási fázisban van
	 *
	 */
	protected boolean moved;

	/*
	 * Keyconfig 
	 * Mire való?
	 * A játékos irányitását meghatározó mátrix, id vel indexelve a sor
	 */
	private static int[] keyconfig={
		KeyEvent.VK_LEFT   , KeyEvent.VK_RIGHT  ,KeyEvent.VK_UP     ,KeyEvent.VK_DOWN,
		KeyEvent.VK_A      , KeyEvent.VK_D      ,KeyEvent.VK_W      ,KeyEvent.VK_S
	};
	
	//Tartalmazó objektum
	protected Phoebe p;
	
	/*
	 * Konstruktor
	 * Felelősség:Létrehozza a robotot és inicializálja a változóit 
	 * @param x a robot létrehozásának x koordinátája
	 * @param y a robot létrehizásának y koordinátája
	 * 
	 * Funkció(ki hívja meg és mikor?):A játék motor a pálya létrehozásakor
	 * 
	 */
	public Robot(int x, int y, Phoebe p) {
		super(x, y);
		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
		id=staticid;
		slowed=1.0;
		staticid+=1;
		this.p=p;
		arrowendx=(int)(x+r*Math.cos(alpha));
		arrowendy=(int)(y+r*Math.sin(alpha));
		// TODO Auto-generated constructor stub
		
	}
	
	// Setter-Getterek
	
	/*
	 * GetId függvény
	 * @return a robot egyedi azonosítója
	 * Funkció(ki hívja meg és mikor?): egyenlőre senki
	 * 
	 */
	public int getId(){
		return id;
	}
	
	/*
	 * setOiled függvény
	 * Felelősség:átállítja a robot olaj effekt követéséhez tartozó állapot változót 
	 * 
	 * Funkció(ki hívja meg és mikor?):Az Oil osztály effekt függvénye
	 *  	
	 */
	public void setOiled(){
		oiled=true;
	}
	
	/*
	 * setGlue függvény
	 * Felelősség:átállítja a robot ragacs effekt követéséhez tartozó állapot változót 
	 * 
	 * Funkció(ki hívja meg és mikor?):A Glue osztály effekt függvénye
	 */
	public void setGlue(){
		slowed=0.5;
	}
	
	/*
	 * DeathAnimation függvény
	 * Felelősség:Ha meghal egy béka akkor ez felel az animációért 
	 * 
	 * Funkció(ki hívja meg és mikor?):még nem tudom gondolom a isOnMap ha lesz ilyen
	 * 
	 */
	public void deathanimation(){
		//TODO
	};
	
	/*
	 * Paint függvény
	 * @param g
	 * Felelősség:kirajzolja a robotot a saját koordinátáin + ha nem lép akkor a nyilat is
	 * 
	 * Funkció(ki hívja meg és mikor?):a játékmotor fő ciklusa
	 *
	 * 
	 */
	public void paint(Graphics2D g) {
		g.fillRect(x, y, WIDTH, HEIGHT);//placeholder ide jön majd a kép
		g.setStroke(new BasicStroke(10));
		g.drawLine(x+WIDTH/2, y+HEIGHT/2, arrowendx+WIDTH/2, arrowendy+HEIGHT/2);
		g.drawImage(img[id%2], x, y, WIDTH, HEIGHT, null);
		
		//width,height a buffered image adatai lesznek
	}
	public  static void setUnitImage() throws IOException{
		img=new BufferedImage[2];
		img[0]=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"frog0.jpg"));
		img[1]=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"frog1.jpg"));
	}


	/*
	 * Move függvény
	 * @see major.Unit#move()
	 * Felelősség:a robot léptetését bonyolítja le
	 * 
	 * Funkció(ki hívja meg és mikor?):a játékmotor minden lépésnél
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub
		arrowendx=(int)(x+slowed*r*Math.cos(alpha));
		arrowendy=(int)(y+slowed*r*Math.sin(alpha));
		x=arrowendx;	
		y=arrowendy;
		slowed=1;
		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
		
			
	}
	
	/*
	 * collisionWithObstacles függvény
	 * @param obstacle az akadály amire az ütközést vizsgáljuk
	 * Felelősség:megnézi hogy a robot érintkezett e valami akadállyal
	 * 
	 * Funkció(ki hívja meg és mikor?):játékmotor a főciklusban
	 */
	public boolean collisionWithObstacles(Obstacle obstacle){

		return this.intersect(obstacle);		
	}
	
	/*
	 * bounce függvény
	 * Felelősség:A következő lépés végpontjának beállítása ha volt üzközés lepattannak egymásról
	 * 
	 * Funkció(ki hívja meg és mikor?):collisionWithRobot
	 * 
	 */
	public void bounce(){
		
	}
	
	@Override
	public String toString() {
		return "Robot [id=" + id + ", slowed=" + slowed + ", oiled=" + oiled
				+ ", x=" +x + ",y=" +y
				+ ", nextx=" + arrowendx + ", nexty=" + arrowendy
				+ ", alpha=" + alpha + ", width=" + WIDTH +", height=" + HEIGHT +"]";
	}

	/*
	 * collisionWithRobot függvény
	 * @param r
	 * Felelősség:azt vizsgálja hogy ütközött e 2 robot és ha igen meghívja a bounce függvényt
	 * 
	 * Funkció(ki hívja meg és mikor?):játékmotor minden lépéskor
	 * 
	 */
	public void collisionWithRobot(Robot r){
		if (id==r.getId())
			return;
		if(this.intersect(r)) {
			bounce();
		}
	}

	
	
	/*
	 * keyPressed függvény
	 * @param e
	 * Felelősség:a játékos irányításának eseménykezelése
	 * !! hiányzik a ragcs/olaj ledobásának lekezelése
	 * 
	 * Funkció(ki hívja meg és mikor?):a játékmotor esemény kezelője
	 * 
	 */
	public void keyPressed(KeyEvent e) {
		//Nyíl irányányának változtatása
		
		if (e.getKeyCode() == keyconfig[id%2*4+1])
			alpha+=0.1;
		if (e.getKeyCode() == keyconfig[id%2*4])
			alpha-=0.1;
		

		
		//Obstacle lerakás
		
		if(e.getKeyCode() == keyconfig[id%2*4+2])  {
		//TODO
		//Olaj lerakás
			Oil item0 = new Oil(x, y);
			p.addObstacle(item0);
			System.out.println("new oil created at:"+x+","+y);
		}
		//...
		//Ragacs lerakás
		//TODO
		if(e.getKeyCode() == keyconfig[id%2*4+3])  {
			Glue item1 = new Glue(x, y);
			p.addObstacle(item1);
		
		}
		
		arrowendx=(int)(x+r*Math.cos(alpha));
		arrowendy=(int)(y+r*Math.sin(alpha));
		System.out.println("nextx ,nexty modified to:"+arrowendx+","+arrowendy);
		p.repaint();
	}
	
}
