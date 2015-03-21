package major;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import minor.MapBuilder;

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
 * 
 * 
 * Interfészek:
 * Runnable
 * 
 */
public class Phoebe 
//implements Runnable //Szkeletonhoz nem kell
{
	
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
	//private MyTimer gameTimer;
	
	/**
	 * Phoebe konstruktor
	 * Felelősség:
	 * A játék felépítése, a robotok lista, az akadályok lista létrehozása.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * GUI hívja meg, ha megnyomják a NewGame gombot.
	 * 
	 */
	public Phoebe(Settings set){
		//Változók inicializálása, adatszerkezetek létrehozása
		ended=false;
		obstacles=new ArrayList<Obstacle>();
		robots=new ArrayList<Robot>();
		gameInfo = set;
		
		//Játék incializálása
		init();		
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
		System.out.println("\t\t->[:Phoebe].AddObstacle(item)");
		System.out.println("\t\t<-[:Phoebe].AddObstacle(item)");
	
		obstacles.add(item);
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
	private void init(){
	//GameTimer létrehozása, inicializálása
		//Ha Időlimites játékmód
	/*	if(gameInfo.getSettings() == Settings.TIMELIMIT)
			gameTimer = new MyTimer(gameInfo.getLimit());
		//ha körlimites játékmód
		else if(gameInfo.getSettings() == Settings.LAPLIMIT)
			gameTimer = new MyTimer(0);*/

		//Pálya létrehozása
		map = new MapBuilder();

	
	//Játékosok létrehozása
		Robot one = new Robot(map.getStartPosPlayer(1)[0], map.getStartPosPlayer(1)[1], this);		
		Robot two = new Robot(map.getStartPosPlayer(2)[0], map.getStartPosPlayer(2)[1], this);
		
		robots.add(one);
		robots.add(two);

	//HUD létrehozása
		hud = new HUD(robots);

	//Checkpointok eljuttatása a HUD-ba
		hud.setCheckpoints(map.getCheckpoints());
		

	//Akadályok létrehozása
		for(int i=1;i<=10;i++){
			//TODO Randomgenerált (x,y) pozíciók
			int x=0;
			int y=0;

			Oil item1 = new Oil(x, y);
			Glue item2 = new Glue(x, y);
			//if(!map.obstacleOutsideOfMap(item1)) 
				obstacles.add(item1);
			//if(!map.obstacleOutsideOfMap(item2)) 
				obstacles.add(item2);
		}						

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
	 * 
	 * @param sc Console-ról olvasáshoz, ez csak a szkeleton felület miatt kell.
	 */
	public void run(Scanner sc) {				
	//Játék eleji visszaszámlálás
		//MyTimer startTimer = new MyTimer(3);
	/*	startTimer.start();
		
		while(!startTimer.isZero()){
			//Idő kiírása
			System.out.println(startTimer.getTime());
		}*/
		
	//Játék visszaszámláló elindítása
	//	gameTimer.start();
		
		System.out.println("|	                      Init Game END                           |");
		System.out.print("|Írjon be egy karaktert és nyomjon ENTER-t a menü megjelenítéséhez:");
		sc.nextLine();
		
		boolean quit = false;
		
		while(!quit){		
			System.out.flush();
			
			System.out.println("========================================================");
			System.out.println("|           Phoebe Szkeleton by Scrum_That!            |");
			System.out.println("========================================================");
			System.out.println("| 1. Robot irányváltoztatás, ugrása                    |");
			System.out.println("| 2. Ragacs lerakása                                   |");
			System.out.println("| 3. Olajfolt lerakása                                 |");
			System.out.println("| 4. Ragacsba lépés                                    |");
			System.out.println("| 5. Olajfoltba lépés                                  |");
			System.out.println("| 6. Checkpointba lépés                                |");
			System.out.println("| 7. Robotok ütközése                                  |");
			System.out.println("| 8. Pályáról való leesés                              |");
			if(gameInfo.getSettings()==Settings.TIMELIMIT) 
				System.out.println("| 9. Idő lejárása                                      |");
			else
				System.out.println("| 9. Minden kör teljesítése                            |");
			System.out.println("| 10. Kilépés                                          |");
			System.out.println("========================================================");
			int usecase = 0;
			while(usecase <= 0 || usecase >= 11){
				System.out.print("            Adja meg a játékmódot (1-10): ");
				usecase = Integer.parseInt(sc.nextLine());
				System.out.println("");
			}
			int temp=0;
			switch(usecase){
			case 1:
				//TODO
				break;
			case 2:
				System.out.println("2. A ragacs lerakását választotta.");
				robots.get(0).keyPressed(KeyEvent.VK_DOWN);				
				break;
				
			case 3:
				System.out.println("3. Az olaj lerakását választotta.");
				robots.get(0).keyPressed(KeyEvent.VK_UP);					
				break;
				
			case 4:		
				System.out.println("Ragacsba lépést választotta.");
				robots.get(0).collisionWithObstacles(obstacles.get(1));
				obstacles.get(1).effect(robots.get(0));
				//TODO
				break;
				
			case 5:
				System.out.println("Olajba lépést választotta.");
				robots.get(0).collisionWithObstacles(obstacles.get(0));
				obstacles.get(0).effect(robots.get(0));
				//TODO
				break;
				
			case 6:
				System.out.println("A checkpointba lépést választotta.");
				hud.checkpointSearch();
				break;
			case 7:
				System.out.println("7. A Robot ütközését választotta.");
				robots.get(0).collisionWithRobot(robots.get(1));
				break;
				
			case 8:
				System.out.println("8. Pályáról leesést választotta.");
				if(map.robotOutsideOfMap(robots.get(0))){
					robots.get(0).deathanimation();
				}
				break;
				
			case 9:
				int tmp='0';
				while(tmp!='i'&& tmp!='n' && tmp!='I' && tmp!='N'){
					if(gameInfo.getSettings()==Settings.TIMELIMIT) 
						System.out.print("Lejárt az idő? I/N:");
					else
						System.out.print("Minden kör teljesítve van? I/N:");
					tmp=sc.nextLine().charAt(0);
				}
				if(tmp=='i'||tmp=='I'){
					quit = true;
				}
				
				break;
			case 10:
				quit = true;
				break;
			}
			
		}
		//TODO Innen lehet ihletet meríteni
		/*
		while(!ended)
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

	//Checkpointok vizsgálata, áthaladtunk-e?
			hud.checkpointSearch();

	//Ütközések vizsgálata robottal, akadállyal
			for(Robot i : robots)
			{			
				for(Obstacle j : obstacles){
				//Ütközés akadállyal
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
				if(map.robotOutsideOfMap(i)){
					ended = true;
					i.deathanimation();
				};		

			}		

		}*/
	}
	
	public static void consoleErase(){
		for(int i=1;i<=50;i++) System.out.println();
	}
}
