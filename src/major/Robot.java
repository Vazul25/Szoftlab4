package major;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

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
	static int WIDTH=40;//teszt placeholder
	static int HEIGHT=40;//teszt placeholder
	//kell majd valami adatszerkezet ami számontartja hogy melyik obstacleből mennyi van a robotnál
	
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
	public Robot(int x, int y, String imagelocation,Phoebe p) {
		super(x, y, imagelocation);
		id=staticid;
		staticid+=1;
		this.p=p;
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
		slowed-=slowed/2;
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
	
	public 
		

	
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
		g.drawLine(x+WIDTH/2, y+HEIGHT/2, arrowendx+WIDTH/2, arrowendy+HEIGHT/2);
		//width,height a buffered image adatai lesznek
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

		//while(moved=false){}//(nem kell ha időosztásos lesz)azért kell hogy mig nem okézuk le az irányt, ne ugorjon el
		arrowendx=(int)(x+40*Math.cos(alpha));
		arrowendy=(int)(y+40*Math.sin(alpha));	
	}
	
	/*
	 * collisionWithObstacles függvény
	 * @param obstacle az akadály amire az ütközést vizsgáljuk
	 * Felelősség:megnézi hogy a robot érintkezett e valami akadállyal
	 * 
	 * Funkció(ki hívja meg és mikor?):játékmotor a főciklusban
	 */
	public boolean collisionWithObstacles(Obstacle obstacle){
		return false;		
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
		if(id%2==1){
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			alpha+=0.1;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			alpha-=0.1;
		}
		else{
			if (e.getKeyCode() == KeyEvent.VK_A)
				alpha+=0.1;
			if (e.getKeyCode() == KeyEvent.VK_D)
				alpha-=0.1;}
		
		//Obstacle lerakás
		if(id%2 == 1){
			if(e.getKeyCode() == KeyEvent.VK_UP) ;
			//TODO
			//Olaj lerakás
			Oil item0 = new Oil(x, y, null);
			p.addObstacle(item0);
			//...
			//Ragacs lerakás
			//TODO
			Glue item1 = new Glue(x, y, null);
			p.addObstacle(item1);
			//...
		}
		else{
			//Olaj lerakás
			//TODO
			Oil item0 = new Oil(x, y, null);
			p.addObstacle(item0);
			//...
			//TODO
			//Ragacs lerakás
			Glue item1 = new Glue(x, y, null);
			p.addObstacle(item1);
			//...
		}
		
		p.repaint();
	}
	
}
