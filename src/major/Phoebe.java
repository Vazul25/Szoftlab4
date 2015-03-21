package major;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import minor.MapBuilder;
//import LogBase;

/*
 * Phoebe osztĂˇly
 * 
 * FelelĹ‘ssĂ©g:
 * A jĂˇtĂ©k logikĂˇt megvalĂłsĂ­tĂł objektum. ListĂˇban tĂˇrolja a pĂˇlyĂˇn tartĂłzkodĂł robotokat, akadĂˇlyokat Ă©s 
 * figyeli, hogy mikor Ă©r vĂ©get a jĂˇtĂ©k. A â€žPhoebeâ€ť objektum rajzolja ki az objektumokat a pĂˇlyĂˇn Ă©s 
 * szĂˇlkĂ©nt indĂ­thatĂł osztĂˇlyt, melyben maga a jĂˇtĂ©k fut. JĂˇtĂ©kindĂ­tĂˇskor berakja a pĂˇlyĂˇra a robotokat Ă©s 
 * az akadĂˇlyokat a kezdĹ‘ pozĂ­ciĂłkba. Ebben az objektumban tĂ¶rtĂ©nnek az ellenĹ‘rzĂ©sek (akadĂˇlyba vagy 
 * robotba ĂĽtkĂ¶zĂ©sek, pĂˇlyĂˇrĂłl leesĂ©s)
 * 
 * Ĺ�sosztĂˇlyok:
 * 
 * 
 * InterfĂ©szek:
 * Runnable
 * 
 */
public class Phoebe 
//implements Runnable //Szkeletonhoz nem kell
{
	
	//AttribĂştumok
	/**
	 * Mire valĂł:
	 ** ended: Ă�llapot vĂˇltozĂł, ha vĂ©ge a jĂˇtĂ©knak true Ă©rtĂ©k Ă­rĂłdik be. Ha beteljesĂĽl egy jĂˇtĂ©k vĂ©gĂ©t jelentĹ‘ esemĂ©ny, akkor ezen a vĂˇltozĂłn keresztĂĽl leĂˇll a jĂˇtĂ©k Ă©s megĂˇllapĂ­tĂłdik a nyertes.
	 ** gameInfo: A jĂˇtĂ©k kezdeti beĂˇllĂ­tĂˇsait tĂˇrolja (kĂ¶r/idĹ‘ mĂłd, max kĂ¶r/max idĹ‘).
	 */
	public static BufferedImage background;
	private boolean ended;
	private Settings gameInfo;

	//BeĂˇllĂ­tĂˇsok
	/*
	 * Setting Enum
	 */
	public final static class Settings{
		//JĂˇtĂ©kmĂłdok
		public static final int LAPLIMIT = 1;
		public static final int TIMELIMIT = 2;

		//jĂˇtĂ©kmĂłd tĂˇrolĂˇsĂˇra szĂˇnt tagvĂˇltozĂł
		private int racemode;
		
		//MaximĂˇlis kĂ¶r/idĹ‘ limit jĂˇtĂ©kmĂłdtĂłl fĂĽggĹ‘en
		private int limit;
		
		//UgrĂˇsok mĂ©rtĂ©ke (def = 3 sec)
		private int step;

		/*
		 * Keyconfig 
		 * Mire valĂł:
		 * A jĂˇtĂ©kos irĂˇnyitĂˇsĂˇt meghatĂˇrozĂł mĂˇtrix.
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
	 * Mire valĂł:
	 ** robots: A jĂˇtĂ©kban szereplĹ‘ robotok listĂˇja
	 ** obstacles: A jĂˇtĂ©kban szereplĹ‘ akadĂˇlyok listĂˇja
	 ** hud: A jĂˇtĂ©kosok elĹ‘rehaladĂˇsĂˇt, ragacs Ă©s olajkĂ©szleteit tartja szĂˇmon
	 ** map: A pĂˇlyĂˇt reprezentĂˇlĂł objektum. TĂˇrolja mĂ©g a checkpointokat Ă©s a robotok kezdĹ‘ koordinĂˇtĂˇit.
	 ** gameTimer: A jĂˇtĂ©k sorĂˇn idĹ‘t mĂ©rĹ‘ objektum.
	 */
	private List<Robot> robots;
	private List<Obstacle> obstacles;
	private HUD hud;
	private MapBuilder map;
	//private MyTimer gameTimer;
	
	/**
	 * Phoebe konstruktor
	 * FelelĹ‘ssĂ©g:
	 * A jĂˇtĂ©k felĂ©pĂ­tĂ©se, a robotok lista, az akadĂˇlyok lista lĂ©trehozĂˇsa.
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):
	 * GUI hĂ­vja meg, ha megnyomjĂˇk a NewGame gombot.
	 * 
	 */
	public Phoebe(Settings set){
		//VĂˇltozĂłk inicializĂˇlĂˇsa, adatszerkezetek lĂ©trehozĂˇsa
		ended=false;
		obstacles=new ArrayList<Obstacle>();
		robots=new ArrayList<Robot>();
		gameInfo = set;
		
		//JĂˇtĂ©k incializĂˇlĂˇsa
		init();		
	}

	/**
	 * addObstacle fĂĽggvĂ©ny
	 * FelelĹ‘ssĂ©g:
	 * Az obstacles tĂˇrolĂłba helyez egy akadĂˇlyt.
	 * 
	 * FunkciĂł:
	 * Ha lĂ©trehoznak a robotok akadĂˇlyt ennek a fĂĽggvĂ©ny meghĂ­vĂˇsĂˇval hozzĂˇteszik a tĂ¶bbihez (obstacles tĂˇrolĂł)
	 *
	 * @param item AkadĂˇlyt reprezentĂˇlĂł objektum.
	 */
	public void addObstacle(Obstacle item){
		System.out.println("\t\t->[:Phoebe].AddObstacle(item)");
		System.out.println("\t\t<-[:Phoebe].AddObstacle(item)");
	
		obstacles.add(item);
	}

	/*
	 * init fĂĽggvĂ©ny
	 * FelelĹ‘ssĂ©g:
	 * Timer lĂ©trehozĂˇsa. PĂˇlya lĂ©trehozĂˇsa. JĂˇtĂ©kosok lĂ©trehozĂˇsa. HUD lĂ©trehozĂˇsa. 
	 * Checkpointok ĂˇtadĂˇsa a HUD-nak. Kezdeti akadĂˇlyok lĂ©trehozĂˇsa.
	 * 
	 * FunkcionalitĂˇs:
	 * run() hĂ­vja meg.
	 */
	private void init(){
	//GameTimer lĂ©trehozĂˇsa, inicializĂˇlĂˇsa
		//Ha IdĹ‘limites jĂˇtĂ©kmĂłd
	/*	if(gameInfo.getSettings() == Settings.TIMELIMIT)
			gameTimer = new MyTimer(gameInfo.getLimit());
		//ha kĂ¶rlimites jĂˇtĂ©kmĂłd
		else if(gameInfo.getSettings() == Settings.LAPLIMIT)
			gameTimer = new MyTimer(0);*/

		//PĂˇlya lĂ©trehozĂˇsa
		map = new MapBuilder();

	
	//JĂˇtĂ©kosok lĂ©trehozĂˇsa
		Robot one = new Robot(map.getStartPosPlayer(1)[0], map.getStartPosPlayer(1)[1], this);		
		Robot two = new Robot(map.getStartPosPlayer(2)[0], map.getStartPosPlayer(2)[1], this);
		
		robots.add(one);
		robots.add(two);

	//HUD lĂ©trehozĂˇsa
		hud = new HUD(robots);

	//Checkpointok eljuttatĂˇsa a HUD-ba
		hud.setCheckpoints(map.getCheckpoints());
		

	//AkadĂˇlyok lĂ©trehozĂˇsa
		for(int i=1;i<=10;i++){
			//TODO RandomgenerĂˇlt (x,y) pozĂ­ciĂłk
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
	 * Run fĂĽggvĂ©ny
	 * @see java.lang.Runnable#run()
	 * 
	 * FelelĹ‘ssĂ©g:
	 * Ez a metĂłdus futtatja a fĹ‘ciklust, amelyben maga a jĂˇtĂ©k mĹ±kĂ¶dik.
	 * 
	 * Mire valĂł(kit hĂ­v meg, ki hĂ­vja meg):
	 * Thread.start()
	 * 
	 * @param sc Console-rĂłl olvasĂˇshoz, ez csak a szkeleton felĂĽlet miatt kell.
	 */
	public void run(Scanner sc) {				
	//JĂˇtĂ©k eleji visszaszĂˇmlĂˇlĂˇs
		//MyTimer startTimer = new MyTimer(3);
	/*	startTimer.start();
		
		while(!startTimer.isZero()){
			//IdĹ‘ kiĂ­rĂˇsa
			System.out.println(startTimer.getTime());
		}*/
		
	//JĂˇtĂ©k visszaszĂˇmlĂˇlĂł elindĂ­tĂˇsa
	//	gameTimer.start();
		
		System.out.println("|	                      Init Game END                           |");
		System.out.print("|ĂŤrjon be egy karaktert Ă©s nyomjon ENTER-t a menĂĽ megjelenĂ­tĂ©sĂ©hez:");
		sc.nextLine();
		
		boolean quit = false;
		
		while(!quit){		
			System.out.flush();
			//LogBase
			
			System.out.println("========================================================");
			System.out.println("|           Phoebe Szkeleton by Scrum_That!            |");
			System.out.println("========================================================");
			System.out.println("| 1. Robot irĂˇnyvĂˇltoztatĂˇs, ugrĂˇsa                    |");
			System.out.println("| 2. Ragacs lerakĂˇsa                                   |");
			System.out.println("| 3. Olajfolt lerakĂˇsa                                 |");
			System.out.println("| 4. Ragacsba lĂ©pĂ©s                                    |");
			System.out.println("| 5. Olajfoltba lĂ©pĂ©s                                  |");
			System.out.println("| 6. Checkpointba lĂ©pĂ©s                                |");
			System.out.println("| 7. Robotok ĂĽtkĂ¶zĂ©se                                  |");
			System.out.println("| 8. PĂˇlyĂˇrĂłl valĂł leesĂ©s                              |");
			if(gameInfo.getSettings()==Settings.TIMELIMIT) 
				System.out.println("| 9. IdĹ‘ lejĂˇrĂˇsa                                      |");
			else
				System.out.println("| 9. Minden kĂ¶r teljesĂ­tĂ©se                            |");
			System.out.println("| 10. KilĂ©pĂ©s                                          |");
			System.out.println("========================================================");
			int usecase = 0;
			while(usecase <= 0 || usecase >= 11){
				System.out.print("            Adja meg a jĂˇtĂ©kmĂłdot (1-10): ");
				usecase = Integer.parseInt(sc.nextLine());
				System.out.println("");
			}
			int temp=0;
			switch(usecase){
			case 1:
				System.out.println("1. A Robot irányváltoztatását választotta.");
				try {
					robots.get(0).move();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				System.out.println("2. A ragacs lerakĂˇsĂˇt vĂˇlasztotta.");
				robots.get(0).keyPressed(KeyEvent.VK_DOWN);				
				break;
				
			case 3:
				System.out.println("3. Az olaj lerakĂˇsĂˇt vĂˇlasztotta.");
				robots.get(0).keyPressed(KeyEvent.VK_UP);					
				break;
				
			case 4:		
				System.out.println("Ragacsba lĂ©pĂ©st vĂˇlasztotta.");
				robots.get(0).collisionWithObstacles(obstacles.get(1));
				//TODO
				break;
				
			case 5:
				System.out.println("Olajba lĂ©pĂ©st vĂˇlasztotta.");
				robots.get(0).collisionWithObstacles(obstacles.get(0));
				//TODO
				break;
				
			case 6:
				System.out.println("A checkpointba lĂ©pĂ©st vĂˇlasztotta.");
				hud.checkpointSearch();
				break;
			case 7:
				System.out.println("7. A Robot ĂĽtkĂ¶zĂ©sĂ©t vĂˇlasztotta.");
				robots.get(0).collisionWithRobot(robots.get(1));
				break;
				
			case 8:
				System.out.println("8. PĂˇlyĂˇrĂłl leesĂ©st vĂˇlasztotta.");
				if(map.robotOutsideOfMap(robots.get(0))){
					robots.get(0).deathanimation();
				}
				break;
				
			case 9:
				int tmp='0';
				while(tmp!='i'&& tmp!='n' && tmp!='I' && tmp!='N'){
					if(gameInfo.getSettings()==Settings.TIMELIMIT) 
						System.out.print("LejĂˇrt az idĹ‘? I/N:");
					else
						System.out.print("Minden kĂ¶r teljesĂ­tve van? I/N:");
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
		//TODO Innen lehet ihletet merĂ­teni
		/*
		while(!ended)
		{
	//A User ideje, hogy vĂˇltoztathasson az ugrĂˇs irĂˇnyĂˇn
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

	//MozgĂˇs
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

	//Checkpointok vizsgĂˇlata, Ăˇthaladtunk-e?
			hud.checkpointSearch();

	//ĂśtkĂ¶zĂ©sek vizsgĂˇlata robottal, akadĂˇllyal
			for(Robot i : robots)
			{			
				for(Obstacle j : obstacles){
				//ĂśtkĂ¶zĂ©s akadĂˇllyal
					if(i.collisionWithObstacles(j)){
						System.out.println("utkozes "+j.toString());
						j.effect(i);
					}

				}

				for(Robot k : robots){
				//ĂśtkĂ¶zĂ©s robottal
					i.collisionWithRobot(k);

				}
				//LeesĂ©s vizsgĂˇlata
				if(map.robotOutsideOfMap(i)){
					ended = true;
					i.deathanimation();
				};		

			}		

		}*/
	}
}
