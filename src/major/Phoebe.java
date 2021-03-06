﻿package major;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import minor.MapBuilder;
import minor.*;

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
public class Phoebe extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 8435890710077230081L;
	
	//Attribútumok
	/**
	 * Mire való:
	 ** ended: Állapot változó, ha vége a játéknak true érték íródik be. Ha beteljesül egy játék végét jelentő esemény, akkor ezen a változón keresztül leáll a játék és megállapítódik a nyertes.
	 ** gameInfo: A játék kezdeti beállításait tárolja (kör/idő mód, max kör/max idő).
	 */
	public static BufferedImage background;
	private boolean ended;
	private Settings gameInfo;

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
	private HUD hud;
	private MapBuilder map;
	private MyTimer gameTimer;
	
	/**
	 * Phoebe konstruktor
	 * Felelősség:
	 * A játék felépítése, a robotok lista, az akadályok lista létrehozása.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * GUI hívja meg, ha megnyomják a NewGame gombot.
	 * 
	 */
	public Phoebe(Settings set) throws IOException{
		//Változók inicializálása, adatszerkezetek létrehozása
		ended=false;
		obstacles=new ArrayList<Obstacle>();
		robots=new ArrayList<Robot>();
		gameInfo = set;
		
		//Játék incializálása
		init();
		
		//Teszt
		JFrame frame = new JFrame("Phoebe");//ez nincs itt csak tesztelés 
		
		//KeyListener (gombok lenyomásának lekezelése)
		MyListener listener=new MyListener(robots);
		addKeyListener(listener);
		setFocusable(true);
		Thread listenert=new Thread(listener);
		listenert.start();
		
		//Teszt
		frame.add(this,BorderLayout.CENTER);
		frame.setSize(1000,700);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
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
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);	
		for(int i=0;i<obstacles.size();i++)
		{
			obstacles.get(i).paint(g2d);

		}
		for(int i=0;i<robots.size();i++)
		{
			robots.get(i).paint(g2d);

		}
		//TODO Akadályok, Map kirajzolása 
	}
	/*
	 * init függvény
	 * Felelősség:
	 * Timer létrehozása. Pálya létrehozása. Játékosok létrehozása. HUD létrehozása. 
	 * Checkpointok átadása a HUD-nak. Kezdeti akadályok létrehozása.
	 * 
	 * Funkcionalitás:
	 * run() hívja meg.
	 */
	private void init() throws IOException{
	//GameTimer létrehozása, inicializálása
		//Ha Időlimites játékmód
		if(gameInfo.getSettings() == Settings.TIMELIMIT)
			gameTimer = new MyTimer(gameInfo.getLimit());
		//ha körlimites játékmód
		else if(gameInfo.getSettings() == Settings.LAPLIMIT)
			gameTimer = new MyTimer(0);

		//Pálya létrehozása
		map = new MapBuilder();

	//Teszt
		Robot.setUnitImage();
		Glue.setUnitImage();
		Oil.setUnitImage();
		background=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"background.jpg"));
		
	//Játékosok létrehozása
		//TODO
		Robot one = new Robot(300/*map.getStartPosPlayer(1)[0]*/,200/* map.getStartPosPlayer(1)[1]*/,  this);
		
		Robot two = new Robot(500/*map.getStartPosPlayer(2)[0]*/, 500/*map.getStartPosPlayer(2)[2]*/,  this);
		
		robots.add(one);
		robots.add(two);

	//HUD létrehozása
		hud = new HUD(robots);

	//Checkpointok eljuttatása a HUD-ba
		hud.setCheckpoints(map.getCheckpoints());
		

	//Akadályok létrehozása
			/*for(int i=1;i<=10;i++){
			//TODO Randomgenerált (x,y) pozíciók
			int x=0;
			int y=0;

			Oil item1 = new Oil(x, y, null);
			Glue item2 = new Glue(x, y, null);
			if(!map.obstacleOutsideOfMap(item1)) obstacles.add(item1);
			if(!map.obstacleOutsideOfMap(item2)) obstacles.add(item2);
		}						
*/
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
		
		/*try {
		//	init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		//TESZT
		//repaint();
		
	//Játék eleji visszaszámlálás
		MyTimer startTimer = new MyTimer(3);
		startTimer.start();
		/*
		while(!startTimer.isZero()){
			//Idő kiírása
			System.out.println(startTimer.getTime());
		}*/
		
	//Játék visszaszámláló elindítása
		gameTimer.start();
		
	//Teszt
		int elteltidoteszt=0;
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
				try {
					robots.get(i).move();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}	

	//TODO lépésanimálása egyenesen végig léptetgetni a robotot és kirajzolni

	//Checkpointok vizsgálata, áthaladtunk-e?
			//hud.checkpointSearch();

	//Ütközések vizsgálata robottal, akadállyal
			for(Robot i : robots)
			{			
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
					i.collisionWithRobot(k);

				}
				//Leesés vizsgálata
				/*if(map.robotOutsideOfMap(i)){
					ended = true;
					i.deathanimation();
				};*/		

			}		
	//Teszt
			repaint();	
			if(elteltidoteszt>=60) ended=true;
			elteltidoteszt+=3;
			System.out.println("eltelt:"+elteltidoteszt+"mp");
		}
		if(elteltidoteszt>=60)System.out.println("a játéknak vége, lejárt az idő");
		else System.out.println("a játékos leesett a pályáról");

	}
}
