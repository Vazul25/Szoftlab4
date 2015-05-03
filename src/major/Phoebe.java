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
		
		public static int WINDOW_WIDTH = 1280;
		public static int WINDOW_HEIGHT = 700;
		public static int HUD_HEIGHT = 130; //max = 155, preffered= 130, min = 100

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
	 ** gameTimer: A játék során időt mérő objektum.
	 */
	private List<Robot> robots;
	private List<Obstacle> obstacles;
	private List<Cleaner> cleaners;
	private HUD hud;
	private MapBuilder map;
	//public MyTimer gameTimer;

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
		gameInfo = set;

		this.setSize(Settings.WINDOW_WIDTH,Settings.WINDOW_HEIGHT+Settings.HUD_HEIGHT);
		map = new MapBuilder();
		map.building(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		//Játék incializálása
		init();

		//Teszt
		//JFrame frame = new JFrame("Phoebe");//ez nincs itt csak tesztelés 
		
		//--------------------------------ÚJ KÓD----------------------
		d=new Display(this);
		this.add(d,BorderLayout.CENTER);		
		
		//------------------------------------
		//KeyListener (gombok lenyomásának lekezelése)
		
		MyListener listener=new MyListener(robots);
		addKeyListener(listener);
		setFocusable(true);
		Thread listenert=new Thread(listener);
		listenert.start();
		
		
		//Teszt
	
		
		//frame.add(hud,BorderLayout.SOUTH);
		

	
		this.setSize(Settings.WINDOW_WIDTH,Settings.WINDOW_HEIGHT+Settings.HUD_HEIGHT+25);

		this.setVisible(true);
		
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
	 * 
	 * @return
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
	
	public BufferedImage getBackgroundimg(){
		return Phoebe.background;
	}
	/**
	 * paint függvény
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

		//Teszt
		Robot.setUnitImage();
		Glue.setUnitImage();
		Oil.setUnitImage();
		Cleaner.setUnitImage();
		background=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"background.jpg"));

		//Játékosok létrehozása
		//TODO
		Robot one = new Robot(map.getStartPosPlayer(1)[0], map.getStartPosPlayer(1)[1],  this);

		Robot two = new Robot(map.getStartPosPlayer(0)[0], map.getStartPosPlayer(0)[1],  this);

		robots.add(one);
		robots.add(two);

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
		
		//TESZTELÉSHEZ by Vazul
	/*	obstacles.add(new Glue(100,100));
		obstacles.add(new Glue(500,100));
		obstacles.add(new Oil(100,600));
		obstacles.add(new Oil(500,600));
		cleaners.add(new Cleaner(500,200,this));
		cleaners.add(new Cleaner(620,200,this));*/
		//System.out.println(cleaners.get(0).toString());
		////////////////////////////////////////
		int count=0;
		//Akadályok létrehozása
		while(count<4){
			//TODO Randomgenerált (x,y) pozíciók
			Random rn=new Random();
			int x=rn.nextInt()%this.getWidth();
			int y=rn.nextInt()%this.getHeight();

		
			Oil item1 = new Oil(x, y);
			 x=rn.nextInt()%this.getWidth();
			 y=rn.nextInt()%this.getHeight();

			Glue item2 = new Glue(x, y);
			//System.out.println(item1.toString());
			
			if(!PlaceTaken(item1)){
			if(!map.obstacleOutsideOfMap(item1)) {obstacles.add(item1);count++;}
			}
			if(!PlaceTaken(item2)){
				
			if(!map.obstacleOutsideOfMap(item2)) {obstacles.add(item2);count++;}
			}
		}						
		 
	}
	
	/**
	 * TODO
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
		
		Thread hud_thread = new Thread(hud);
		hud_thread.start();		
		
		while(!hud.startTimer.isZero()){
			//Szünet
		}

		//Játék visszaszámláló elindítása
		hud.gameTimer.start();

		//Cleanerek bejövetele minden 3. percben
		MyTimer cleanerTimer = new MyTimer(180);
		cleanerTimer.start();

		//Teszt
		//int elteltidoteszt=0;

		while( !ended)
		{
			//A User ideje, hogy változtathasson az ugrás irányán
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//Mozgás
			for(int i=0;i<robots.size();i++){
				System.out.println(robots.get(i).toString());
				robots.get(i).move();

			}	

			//TODO lépésanimálása egyenesen végig léptetgetni a robotot és kirajzolni

			//Checkpointok vizsgálata, áthaladtunk-e?
			hud.checkpointSearch();

			//Ütközések vizsgálata robottal, akadállyal
			for(Robot i : robots)
			{			
				for(Cleaner cl : cleaners){					
					if(i.collisionWithCleaner(cl)){
						cl.die();
						cleaners.remove(cl);
					}
				}
				for(Obstacle j : obstacles){
					//Ütközés akadállyal
					//	System.out.println(j.toString());
					if(i.collisionWithObstacles(j)){
						System.out.println("utkozes "+j.toString());
						j.effect(i);
					}

				}



				for(Robot k : robots){
					//Ütközés robottal
					try {
						i.collisionWithRobot(k);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}



				}
				//Leesés vizsgálata
				if(map.robotOutsideOfMap(i)){
					ended = true;
					i.deathanimation();
				};		

			}		
			//léptetjük a kisrobotokat és megnézzük hogy ütköznek e a robotokkal ha igen akkor le pattanak 
			for(Cleaner i : cleaners)
			{
					i.move();
				
				for(Robot r:robots){
					if(i.collisionWithRobot(r));
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

			
			
			if(cleanerTimer.isZero()){
				cleaners.add(new Cleaner(200,300,this));
				cleaners.add(new Cleaner(300,400,this));
				cleanerTimer.start();
			}
			//Teszt
			update();	
			//System.out.println("Fennmaradt idő: "+gameTimer.getTime()+" mp");
			if(hud.gameTimer.isZero()) ended = true;
			//if(elteltidoteszt>=1000) ended=true;
			//elteltidoteszt+=3;
			//System.out.println("eltelt:"+elteltidoteszt+"mp");
		}
		if(hud.gameTimer.isZero())System.out.println("A játéknak vége, lejárt az idő.");
		//else System.out.println("A játékos leesett a pályáról.");

	}
}
