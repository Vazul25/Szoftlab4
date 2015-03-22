package major;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
	 ** ended:  Állapot változó, ha vége a játéknak true érték íródik be. 
	 * 			Ha beteljesül egy játék végét jelentő esemény, akkor ezen a változón keresztül leáll a játék és megállapítódik a nyertes.
	 ** gameInfo: A játék kezdeti beállításait tárolja (kör/idő mód, max kör/max idő).
	 */
	private boolean ended = false;
	public static Settings gameInfo;

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
			step = 3;
			limit = 180;
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
	Oil oil1; //szekelton 5. usecase-hez
	Glue glue1; //szkeleton 4. usecase-hez
	
	/**
	 * Phoebe konstruktor
	 * Felelősség:
	 * A játék felépítése, a robotok lista, az akadályok lista létrehozása.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * GUI hívja meg, ha megnyomják a NewGame gombot.
	 */
	public Phoebe(Settings set){
		System.out.println("> ->[:Phoebe].Phoebe(Settings):");
		gameInfo = set;
		init();		
		System.out.println("< <-[:Phoebe].Phoebe(Settings)");
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
		System.out.println(">\t\t->[:Phoebe].AddObstacle(Obstacle)");
		System.out.println("<\t\t<-[:Phoebe].AddObstacle(Obstacle)");
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
	//Pálya létrehozása
		map = new MapBuilder();
		
	
	//Játékosok létrehozása
		robots = new ArrayList<Robot>();
		Robot one = new Robot(200,300, this);		
		Robot two = new Robot(200,300, this);
		
		robots.add(one);
		robots.add(two);

	//HUD létrehozása
		hud = new HUD(robots);		

	//Akadályok létrehozása
		for(int i=1;i<=2;i++){
			//TODO Randomgenerált (x,y) pozíciók
			int x=0;
			int y=0;

			oil1 = new Oil(x, y);
			glue1 = new Glue(x, y);						
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
	
		
		System.out.println("|	                      Init Game END                           |");
		System.out.print("|Írjon be egy karaktert és nyomjon ENTER-t a menü megjelenítéséhez:");
		sc.nextLine();
		
		boolean quit = false;
		
		while(!quit){		
			System.out.flush();
			//LogBase
			
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
				System.out.println("1. A Robot irányváltoztatását, ugrását választotta.");
				System.out.println("> ->[:Phoebe].run():");
				robots.get(0).move();				
				System.out.println("< <-[:Phoebe].run()");
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
				System.out.println("4. Ragacsba lépést választotta.");
				System.out.println("> ->[:Phoebe].run():");
				robots.get(0).collisionWithObstacles(glue1);
				System.out.println("< <-[:Phoebe].run()");
				break;
				
			case 5:
				System.out.println("5. Olajba lépést választotta.");
				System.out.println("> ->[:Phoebe].run():");
				robots.get(0).collisionWithObstacles(oil1);
				System.out.println("< <-[:Phoebe].run()");
				break;
				
			case 6:
				System.out.println("6. A checkpointba lépést választotta.");
				System.out.println("> ->[:Phoebe].run():");
				hud.checkpointSearch();
				System.out.println("< <-[:Phoebe].run()");
				break;
			case 7:
				System.out.println("7. A Robot ütközését választotta.");
				System.out.println("> ->[:Phoebe].run():");
				robots.get(0).collisionWithRobot(robots.get(1));
				System.out.println("< <-[:Phoebe].run()");
				break;
				
			case 8:
				System.out.println("8. Pályáról leesést választotta.");
				System.out.println("> ->[:Phoebe].run():");
				if(map.robotOutsideOfMap(robots.get(0))){
					robots.get(0).deathanimation();
				}
				System.out.println("< <-[:Phoebe].run()");
				break;
				
			case 9:
				if(gameInfo.getSettings() == Settings.TIMELIMIT)
					System.out.println("9. Az idő lejárását választotta.");
				else 
					System.out.println("9. A minden kör teljesítését választotta.");
				System.out.println("< <-[:Phoebe].run()");
				while(temp!='i'&& temp!='n' && temp!='I' && temp!='N'){
					if(Phoebe.gameInfo.getSettings()==Settings.TIMELIMIT) 
						System.out.print("?\t 9.1 Lejárt az idő? I/N:");
					else
						System.out.print("?\t 9.1 Minden kör teljesítve van? I/N:");
					temp=sc.nextLine().charAt(0);
				}
				if(temp == 'i' || temp == 'I')	hud.endOfTheGame();
				System.out.println("< <-[:Phoebe].run()");
				break;
			case 10:
				quit = true;
				break;
			}
		}
	}
}
