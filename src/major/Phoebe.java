package major;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import minor.MapBuilder;
import minor.MyTimer;


/*
 * Phoebe osztály
 * 
 * Felelősség:
 * A játék logikát megvalósító objektum. Listában tárolja a pályán tartózkodó robotokat, akadályokat és 
 * figyeli, hogy mikor ér véget a játék. A „Phoebe” objektum rajzolja ki az objektumokat a pályán és 
 * szálként indítható osztályt, melyben maga a játék fut. Játékindításkor berakja a pályára a robotokat és 
 * az akadályokat a kezdő pozíciókba. Ebben az objektumban történnek az ellenőrzések (akadályba vagy 
 * robotba ütközések, pályáról leesés)
 * 
 * Ősosztályok:
 * JPanel
 * 
 * Interfészek:
 * Runnable
 * 
 */
public class Phoebe  extends JFrame implements Runnable{

	private static final long serialVersionUID = 8435890710077230081L;

	//Attribútumok
	/**
	 * Mire való:
	 ** ended: Állapot változó, ha vége a játéknak true érték íródik be. Ha beteljesül egy játék végét jelentő esemény, akkor ezen a változón keresztül leáll a játék és megállapítódik a nyertes.
	 ** gameInfo: A játék kezdeti beállításait tárolja (kör/idő mód, max kör/max idő).
	 ** background: A játék háttérképét tároló objektum
	 ** d: Az MVC tervezési minta View elemét megvalósító objektum.
	 */
	
	private boolean ended;
	public static BufferedImage background;
	private Settings gameInfo;
	private Display d;
	
	//Beállítások
	/*
	 * Setting Enum
	 */
	public final static class Settings{
		//Játékmódok
		public static final int LAPLIMIT = 1;
		public static final int TIMELIMIT = 2;

		//játékmód tárolására szánt tagváltozó
		private int racemode;

		//Maximális kör/idő limit játékmódtól függően
		private int limit;

		//Ugrások mértéke (def = 3 sec)
		private int step;
		
		/*
		 * Keyconfig 
		 * Mire való:
		 * A játékos irányitását meghatározó mátrix.
		 */

		public static final int[] keyconfig={
			KeyEvent.VK_LEFT   , KeyEvent.VK_RIGHT  ,KeyEvent.VK_UP     ,KeyEvent.VK_DOWN,
			KeyEvent.VK_A      , KeyEvent.VK_D      ,KeyEvent.VK_W      ,KeyEvent.VK_S
		};
		
		//Képernyő méretek		
		public static int WINDOW_WIDTH = 1000;//min= 1200
		public static int WINDOW_HEIGHT = 600;
		public static int HUD_HEIGHT = 100; //max = 155, preffered= 130, min = 100

		public Settings(int info){
			this.racemode = info;
		}

		public int getSettings(){
			return racemode;
		}

		public int getLimit() {
			return limit;
		}

		public void setLimit(int limit) {
			this.limit = limit;
		}

		public int getStep() {
			return step;
		}

		public void setStep(int step) {
			this.step = step;
		}
	}

	/**
	 * Adatszerkezetek
	 * Mire való:
	 ** robots: A játékban szereplő robotok listája
	 ** obstacles: A játékban szereplő akadályok listája
	 ** hud: A játékosok előrehaladását, ragacs és olajkészleteit tartja számon
	 ** map: A pályát reprezentáló objektum. Tárolja még a checkpointokat és a robotok kezdő koordinátáit.
	 */
	private List<Robot> robots;
	private List<Obstacle> obstacles;
	private List<Cleaner> cleaners;
	private HUD hud;
	private MapBuilder map;

	/**
	 * Phoebe konstruktor
	 * Felelősség:
	 * A játék felépítése, adatszerkezetek inicializálása. Gomb lenyomását lekezlő szál létrehozása.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * GUI hívja meg, ha megnyomják a NewGame gombot.
	 * 
	 */
	public Phoebe(Settings set) throws IOException{
		super("Phoebe");
		gameInfo = set;

		//Méretek beállítása
		this.setSize(Settings.WINDOW_WIDTH,Settings.WINDOW_HEIGHT+Settings.HUD_HEIGHT+25);
		map = new MapBuilder();
		//Pálya felépítése
		map.building(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		
		//JFrame beállítások
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		//Játék incializálása
		init();

		//View architektúrális elem létrehozása
		d=new Display(this);
		this.add(d,BorderLayout.CENTER);		
		
		//KeyListener (gombok lenyomásának lekezelése)		
		MyListener listener=new MyListener(robots);
		addKeyListener(listener);
		setFocusable(true);
		Thread listenert=new Thread(listener);
		listenert.start();		
	}

	/**
	 * addObstacle függvény
	 * Felelősség:
	 * Az obstacles tárolóba helyez egy akadályt.
	 * 
	 * Funkció:
	 * Ha létrehoznak a robotok akadályt ennek a függvény meghívásával hozzáteszik a többihez (obstacles tároló)
	 *
	 * @param item Akadályt reprezentáló objektum.
	 */
	public void addObstacle(Obstacle item){
		obstacles.add(item);
	}
	/**
	 * getObstacles függvény
	 * Felelősség:
	 * Az obstacles lista referenciájának lekérdezése.
	 * 
	 * Funkció:A Cleaner ezen keresztül veszi át az obstacle listát.
	 * 
	 */
	List<Obstacle> getObstacles(){
		return obstacles;
	}
	
	/**
	 *  GetVisibleData függvény
	 *  Visszaadja az összes kirajzolandó objektumot
	 */
	public List<iVisible> getVisibleData(){
		List<iVisible> visible=new ArrayList<iVisible>();
		visible.add(map);
		visible.addAll(obstacles);
		visible.addAll(robots);
		visible.addAll(cleaners);
		visible.add(hud);
		
		return visible;
	}
	
	/**
	 * getBackgrounding függvény
	 * Visszatér a Háttérkép BufferedImage-el.
	 * @return
	 */
	public BufferedImage getBackgroundimg(){
		return Phoebe.background;
	}
	
	/**
	 * update függvény
	 * 
	 * Felelősség:
	 * Rajzolás
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * Külön szál, repaint()
	 * 	 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */    
	public void update() { 
		d.repaint();	
	}
	
	/**
	 * init függvény
	 * Felelősség:
	 * Timer létrehozása. Pálya létrehozása. Játékosok létrehozása. HUD létrehozása. 
	 * Checkpointok átadása a HUD-nak. Kezdeti akadályok létrehozása.
	 * 
	 * Funkcionalitás:
	 * run() hívja meg.
	 */
	private void init() throws IOException{
		//Változók inicializálása, adatszerkezetek létrehozása
		ended=false;
		obstacles=new ArrayList<Obstacle>();
		robots=new ArrayList<Robot>();
		cleaners = new ArrayList<Cleaner>();

		//Pálya létrehozása
		map = new MapBuilder();

		//Képek betöltése
		Robot.setUnitImage();
		Glue.setUnitImage();
		Oil.setUnitImage();
		Cleaner.setUnitImage();
		background=ImageIO.read(new File(System.getProperty("user.dir")+"\\icons\\"+"background.jpg"));

		//Játékosok létrehozása
		Robot one = new Robot(map.getStartPosPlayer(1)[0], map.getStartPosPlayer(1)[1],  this);
		Robot two = new Robot(map.getStartPosPlayer(0)[0], map.getStartPosPlayer(0)[1],  this);
		robots.add(one);
		robots.add(two);
		
		//Teszt
		cleaners.add(new Cleaner(200,300,this));
		cleaners.add(new Cleaner(300,400,this));
		cleaners.add(new Cleaner(100,100,this));
		cleaners.add(new Cleaner(400,400,this));

		//HUD létrehozása
		hud = new HUD(robots, this);

		//Checkpointok eljuttatása a HUD-ba
		hud.setCheckpoints(map.getCheckpoints());

		//GameTimer létrehozása, inicializálása
			//Ha Időlimites játékmód
		if(gameInfo.getSettings() == Settings.TIMELIMIT)
			hud.gameTimer = new MyTimer(gameInfo.getLimit());
			//ha körlimites játékmód
		else if(gameInfo.getSettings() == Settings.LAPLIMIT)
			hud.gameTimer = new MyTimer(0);
		
		int count=0;
		//Akadályok létrehozása
		while(count<4){
			//Random értékek generálása
			Random rn=new Random();
			int x=rn.nextInt()%this.getWidth();
			int y=rn.nextInt()%this.getHeight();
			
			//Olaj letétele
			Oil item1 = new Oil(x, y);
			
			//Random értékek generálása
			 x=rn.nextInt()%this.getWidth();
			 y=rn.nextInt()%this.getHeight();
			 
			//Ragacs lerakása
			Glue item2 = new Glue(x, y);
			
			//Vizsgálat, hogy lerakhatjuk-e az akadályt
			if(!PlaceTaken(item1)){
				if(!map.obstacleOutsideOfMap(item1)) {
					obstacles.add(item1);
					count++;
				}
			}
			//Vizsgálat, hogy lerakhatjuk-e az akadályt
			if(!PlaceTaken(item2)){				
				if(!map.obstacleOutsideOfMap(item2)) {
					obstacles.add(item2);
					count++;
				}
			}
		}						
		 
	}
	
	/**
	 * PlaceTaken függvény
	 * Megvizsgálja, hogy nem ütközik-e bármilyen más objektummal a pályán a paraméterben megkapott Unit
	 * @param u
	 * @return
	 */
	private boolean PlaceTaken(Unit u){
		ArrayList<Unit> existing =new ArrayList<Unit>();
		existing.addAll(obstacles);
		existing.addAll(cleaners);
		existing.addAll(robots);
		for(Unit i : existing){
			if(u.intersect(i))return true;
		}
		return false;
	}
	
	/*
	 * Run függvény
	 * @see java.lang.Runnable#run()
	 * 
	 * Felelősség:
	 * Ez a metódus futtatja a főciklust, amelyben maga a játék működik.
	 * 
	 * Mire való(kit hív meg, ki hívja meg):
	 * Thread.start()
	 */
	@Override
	public void run() {
		
		//Játék eleji visszaszámlálás
		hud.startTimer = new MyTimer(3);
		hud.startTimer.start();
		
		//HUD elindítása
		Thread hud_thread = new Thread(hud);
		hud_thread.start();		
		
		//Meg kell várni, hogy lejárjon a három másodperc
		while(!hud.startTimer.isZero()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//Játék visszaszámláló elindítása
		hud.gameTimer.start();

		//Cleanerek bejövetele minden 45. másodpercben, ha köridős, akkor a játék során 3-szor fog bejönni 2 robot
		MyTimer cleanerTimer = new MyTimer(45);
		if(gameInfo.getSettings() == Settings.TIMELIMIT) cleanerTimer = new MyTimer(gameInfo.getLimit()/4);
		cleanerTimer.start();

		//Játék logika
		while(!ended)
		{
			//A User ideje, hogy változtathasson az ugrás irányán
			try {
				Thread.sleep(gameInfo.getStep()*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//Mozgás
			for(int i=0;i<robots.size();i++){
				System.out.println(robots.get(i).toString());
				robots.get(i).move();
				update();

			}	

			//Checkpointok vizsgálata, áthaladtunk-e?
			hud.checkpointSearch();

			//Ütközések vizsgálata robottal, akadállyal
			for(Robot i : robots)
			{							
				//Robot-><-Cleaner
				Iterator<Cleaner> cl=cleaners.iterator();
				while(cl.hasNext())
				{
					Cleaner temp=cl.next();
					
					if(i.collisionWithCleaner(temp)){
						temp.die();
						cl.remove();
					}
				}	
				
				//Robot-><-Obstacle
				for(Obstacle j : obstacles){
					//Ütközés akadállyal
					if(i.collisionWithObstacles(j)){
						System.out.println("utkozes "+j.toString());
						j.effect(i);
						update();
					}

				}
				
				//Robot-><-Robot
				for(Robot k : robots){
					try {
						i.collisionWithRobot(k);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				//Leesés vizsgálata
				if(map.robotOutsideOfMap(i)){
					ended = true;
					i.deathanimation();
					hud.endOfTheGame();
					update();
				}
			}	
			
			//léptetjük a kisrobotokat és megnézzük hogy ütköznek e a robotokkal ha igen akkor le pattanak 
			for(Cleaner i : cleaners)
			{
				i.move();
				update();
				//Cleaner-><-robot
				for(Robot r:robots){
					i.collisionWithRobot(r);
				}
				//Cleaner-><-Cleaner
				for(Cleaner cl : cleaners){
					i.collisionWithCleaner(cl);
				}
			}
			
			//iterátoros megoldás a obstacle-ök vizsgálatára, ez azért kell mert törölhetünk iterálás közben
			Iterator<Obstacle> it=obstacles.iterator();
			while(it.hasNext())
			{
				Obstacle temp=it.next();
				
				if(!temp.checkAlive()){
					
					it.remove();

				}
			}			
			//Ha megfelelő idő letelt, akkor berakunk a pályára 2 robotot
			if(cleanerTimer.isZero()){
				cleaners.add(new Cleaner(200,300,this));
				cleaners.add(new Cleaner(300,400,this));
				cleanerTimer.start();
			}

			//Képernyő frissítése
			update();	
			
			//Játék végének kiértékelése
			if(gameInfo.getSettings()==Settings.TIMELIMIT){
					if(hud.gameTimer.isZero()){ 
						ended = true;
						hud.endOfTheGame();
					}
			}
			else{
				if(hud.maxLapReached(gameInfo)){
					ended = true;
				}
			}
			
		}
		
		//Ha már kiedrült, hogy vége a játéknak
		if(hud.gameTimer.isZero()) System.out.println("A játéknak vége, lejárt az idő.");
		//Várunk 3 másodpercet, hogy mindenki megnézhesse ki nyert
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Felszabadítjuk az erőforrásokat
		dispose();
	}
}
