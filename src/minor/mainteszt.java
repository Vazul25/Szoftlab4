package minor;


import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import major.Cleaner;
import major.Glue;
import major.HUD;
import major.Obstacle;
import major.Oil;
import major.Phoebe;
import major.Phoebe.Settings;
import major.Robot;

public class mainteszt {
	//SEGÉD FÜGGVÉNYEK:
	public static void RotateXDeg(Robot r, int deg){

		for(int i=0;i<deg/5;i++){
			if(r.getId()%2==0)r.keyPressed(KeyEvent.VK_RIGHT);
			else r.keyPressed(KeyEvent.VK_A);

		}
		System.out.println("nextx, nexty modified to: "+r.arrowendx+", "+r.arrowendy);
	}
	
	public static void listObstacles(List<Obstacle> list){
		for(Obstacle i : list)System.out.println(i.toString());	
	}
	
	public static void listRobots(List<Robot> list){
		for(Robot i : list) System.out.println(i.toString());
	}
	
	public static void listCleaner(List<Cleaner> list){
		for(Cleaner i : list) System.out.println(i.toString());
	}
	
	/*
	 * CollisionWithRobot_VOLTÜTKÖZÉS_TESZT
	 * Létrehoz 2 robotot, majd beállítja az őket úgy, hogy egymásra ugorjanak
	 * Azt ellenörizzük hogy sikeressen össze tudta-e vetni a hitboxokat a függvény az ugrás végeztével .
	 */
	public static void testCollisionWithRobotCollision() throws IOException, InterruptedException{
		Robot r1=new Robot(500,500,null);
		Robot r2=new Robot(600,400,null);
		System.out.println(r1.toString());
		System.out.println(r2.toString());
		RotateXDeg(r2,270);
		System.out.println(r1.toString());
		System.out.println(r2.toString());
		r1.move();
		r2.move();
		r1.collisionWithRobot(r2);
		System.out.println(r1.toString());
		System.out.println(r2.toString());
	}
	
	/*
	 * CollisionWithRobot_NEMVOLTÜTKÖZÉS_TESZT
	 * Létrehoz 2 robotot, majd beállítja az őket úgy, hogy egymásra ugorjanak
	 * Azt ellenörizzük hogy sikeressen össze tudta-e vetni a hitboxokat a függvény az ugrás végeztével .
	 */
	public static void testCollisionWithRobotNoCollision() throws IOException, InterruptedException{
		Robot r3=new Robot(500,500,null);
		Robot r4=new Robot(600,400,null);
		System.out.println(r3.toString());
		System.out.println(r4.toString());
		System.out.println(r3.toString());
		System.out.println(r4.toString());
		r3.move();
		r4.move();
		r3.collisionWithRobot(r4);
		System.out.println(r3.toString());
		System.out.println(r4.toString());	
	}
	
	/*
	 * CollisionWithRobot_IRÁNYVÁLTOZTATÁS_TESZT
	 * Létrehoz 2 robotot és meghivja a KeyPressed(int e) függvényt.
	 * Azt ellenörizzük hogy sikeressen ki tudta e számolni a függvény az új koordinátákat.
	 * A várt eredmény, hogy 180 fokban elfordultak és úgy léptek.
	 */
	public static void testCollisionWithRobotKeyPressed(){
		Robot r5=new Robot(500,500,null);
		Robot r6=new Robot(600,400,null);
		System.out.println(r5.toString());
		System.out.println(r6.toString());
		RotateXDeg(r6, 180);
		RotateXDeg(r5, 180);
	
		r5.move();
		r6.move();
		System.out.println(r5.toString());
		System.out.println(r6.toString());	
	}
	
	/*
	 * RAGACS.HATÁSA_TESZT
	 * OLAJ.HATÁSA_TESZT
	 * Effekt Tesztelése
	 * A robotra beállítjuk az adott akadály hatását,
	 * majd ellenőrizük, hogy ez megfelelően kihat-e a robot mozgására.
	 */
	public static void testEffectOil() throws IOException{
		Robot r = new Robot(0, 0, null);
		Oil o1 = new Oil(0, 0);
		Oil o2 = new Oil(100, 0);

		System.out.println(r.toString());
		System.out.println(o1.toString());
		System.out.println(o2.toString());

		o1.effect(r);

		System.out.println(r.toString());

		RotateXDeg(r, 270);

		r.move();

		r.collisionWithObstacles(o2);
	}

	public static void testEffectGlue() throws IOException{
		Robot r = new Robot(0, 0, null);
		Glue g1 = new Glue(0, 0);
		Glue g2 = new Glue(0, 100);

		System.out.println(r.toString());
		System.out.println(g1.toString());
		System.out.println(g2.toString());

		g1.effect(r);

		System.out.println(r.toString());

		RotateXDeg(r, 90);

		r.move();

		r.collisionWithObstacles(g2);
	}

	/*
	 * OLAJBA.UGRÁS_TESZT
	 * RAGACSBA.UGRÁS_TESZT
	 * Robot ütközésének tesztelése a különböző akadályokkal.
	 * Azt ellenörizzük, hogy a robot a megfelelően lerakott akadályba ugrik-e, és ezt érzékeli is.
	 */	
	public static void testCollisionWithOil() {
		Robot r = new Robot(0, 0, null);
		Oil o = new Oil(0, 100);

		System.out.println(r.toString());
		System.out.println(o.toString());

		RotateXDeg(r, 90);

		r.move();
;
		r.collisionWithObstacles(o);
	}

	public static void testCollisionWithGlue() {
		Robot r = new Robot(0, 0, null);
		Glue g = new Glue(0, 100);

		System.out.println(r.toString());
		System.out.println(g.toString());

		RotateXDeg(r, 90);

		r.move();

		r.collisionWithObstacles(g);
	}

	/*
	 * A.PÁLYÁRÓL.LEESETT_TESZT
	 * NEM.ESETT.LE.PÁLYÁRÓL_TESZT
	 * Azt ellenörizzük hogy sikeressen össze 
	 * tudta-e vetni a hitboxokat a függvény az ugrás végeztével.
	 */
	public static void testRobotOutsideOfMap() {
		MapBuilder map = new MapBuilder();
		Robot r = new Robot(500, 350, null);
		
		System.out.println(r.toString());
		
		RotateXDeg(r, 90);
		
		r.move();
		
		System.out.println(r.toString());
		
		//MAP-al való ütközés implementálása
		//r.collisionWithObstacles(map);
		if(map.robotOutsideOfMap(r))
			System.out.print("There was a collision between:\n"+r.toString()+"\n and this: map");
	}
	
	public static void testRobotNotOutsideOfMap(){
		MapBuilder map = new MapBuilder();
		Robot r = new Robot(500, 350, null);
		
		System.out.println(r.toString());
		
		RotateXDeg(r, 270);
		
		r.move();
		
		System.out.println(r.toString());
		
		//MAP-al való ütközés implementálása
		//r.collisionWithObstacles(map);
		if(map.robotOutsideOfMap(r))
			System.out.print("There was a collision between:\n"+r.toString()+"\n and this: map");
	}
	
	/*
	 * CHEICKPOINTONBA.UGRÁS_TESZT
	 * CHEICKPOINTONBA.NEM.UGRÁS_TESZT
	 * Azt ellenörizzük hogy sikeressen össze tudta-e vetni a
	 * hitboxokat a függvény az ugrás végeztével .
	 */
	public static void testCheckpointCollide(){
		MapBuilder map = new MapBuilder();
		Robot r = new Robot(350, 470, null);
		ArrayList<Robot> robs = new ArrayList<Robot>();
		robs.add(r);
		HUD hud= new HUD(robs);
		
		
		//setCheckpoints 100, 180
		map.building(400, 700);
		hud.setCheckpoints(map.getCheckpoints());
		
		System.out.println(r.toString());
		
		//listCheckpoints
		map.listCheckpoints();
		
		RotateXDeg(r, 90);
		
		r.move();
		
		System.out.println(r.toString());
		
		//Checkpointal való ütközés implementálása
		//r.collisionWithObstacles(?checkpoint?);
		hud.checkpointSearch();
	}
	
	public static void testCheckpointNotCollide(){
		MapBuilder map = new MapBuilder();
		Robot r = new Robot(150, 170, null);
		ArrayList<Robot> robs = new ArrayList<Robot>();
		robs.add(r);
		HUD hud= new HUD(robs);
		
		
		//setCheckpoints 100, 180
		map.building(400, 700);
		hud.setCheckpoints(map.checkpoints);
		
		System.out.println(r.toString());
		
		//listCheckpoints
		map.listCheckpoints();
		
		RotateXDeg(r, 90);
		
		r.move();
		
		System.out.println(r.toString());
		
		//Checkpointal való ütközés implementálása
		//r.collisionWithObstacles(?checkpoint?);
		hud.checkpointSearch();
	}

	/*
	 * RobotCollisionWithCleaner_TESZT
	 * Várhatóan a takarító megsemmisül, olajfoltot hátrahagyva maga után.
	 */
	public static void testRobotCollisionWithCleaner() {
		Glue g = new Glue(100, 300);
		Robot r = new Robot(100, 300, null);
		Cleaner c = new Cleaner(100, 300, null);
		
		System.out.println(g.toString());
		System.out.println(r.toString());
		System.out.println(c.toString());
		
		r.move();
		
		/*
		 * listCLeaners
		 * listObstacles
		 * nem működik .toString()-el ugyebár, és nem tudom hogy oldhatnám meg
		 */
	}
	
	/*
	 * AKADÁLY_LERAKÁS_TESZT
	 * Létrehoz egy robotot, majd négyszer meghívja a KeyPressed(int e) függvényt VK_UP paraméterrel.
	 * Azt ellenőrizzük, hogy az akadály létrejön-e, 
	 * illetve hogy egy akadály lerakása után csökken-e a robotnál található akadályok száma.
	 */
	public static void testAddObstacle(){
		Robot r = new Robot(100, 200, null);
		r.keyPressed(KeyEvent.VK_UP);
		r.move(); //mozgatni kell a keypressed-ek után, mert egy lépésben csak egy olajat tehet le
		r.keyPressed(KeyEvent.VK_UP);
		r.move();
		r.keyPressed(KeyEvent.VK_UP);
		r.move();
		r.keyPressed(KeyEvent.VK_UP);
		r.move();
	}
	
	/*
	 * AKADÁLY_ÉLETTARTAM_TESZT
	 * Látrehoz két akadályt, majd bizonyos számú lépés eltelését szimulálja
	 * Azt ellenőrizzük, hogy az akadályok eltűnnek-e adott lépés lefutása után
	 */
	public static void testObstacleLife(){
		
		ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
		obstacles.add(new Glue(0, 0));
		obstacles.add(new Oil(0, 100));
		Robot r = new Robot(100, 100, null);
		for(Obstacle i : obstacles){ //listObstacles
			System.out.println(i.toString());
		}
		for(Obstacle o : obstacles){ //cycles_elapsed(4) - 4x belelépünk a glue-ba (meg az olajba is, de az nem számít)
			for(int i = 1; i <= 4; i++){
				o.effect(r);
			}
		}
		for(Obstacle i : obstacles){ //listObstacles
			System.out.println(i.toString());
		}
		for(Obstacle o : obstacles){ //cycles_elapsed(11) 
			for(int i = 1; i <= 11; i++){
				o.checkAlive();
			}
		}
		for(Obstacle i : obstacles){ //listObstacles
			System.out.println(i.toString());
		}
	}
	
	/*
	 * TAKARÍTÓ_KISROBOT_MOZGÁS_TAKARÍTÁS_TESZT
	 * Létrehozunk egy takarító robotot, majd két akadályt különböző koordinátákon.
	 * Azt ellenőrizzük, hogy a takarító robot odatalál-e a hozzá legközelebbi akadályhoz
	 * és hogy a takarítással törli-e az akadályok listájából.
	 */
	public static void testCleaner(){	
		Cleaner c = new Cleaner(100, 200, null); 
		ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
		obstacles.add(new Oil(100, 300));
		obstacles.add(new Oil(100, 400));
		System.out.println(c.toString()); //listCleaners
		for(Obstacle i : obstacles){ //listObstacles
			System.out.println(i.toString());
		}
		c.move();
		System.out.println(c.toString()); 
		c.move();
		c.move();
		c.move();
		for(Obstacle i : obstacles){ //listObstacles
			System.out.println(i.toString());
		}
	}
	
	/*
	 * Initialisation_Test
	 * A pálya betöltésétól kezdve az egyes elemek létrehozását(Robotok, akadályok,...) hivatott tesztelni.
	 * Az elemek létrehozása, esetleges kivételek kezelése. 
	 * Lehetséges probléma, hogy nem tudja betölteni a pályát vagy a checkpointokat vagy esetleges nem várt kivételekkel terminálódik a futás.
	 */
	public static void testInitialisation(){
		MapBuilder map = new MapBuilder();
		map.building(1000, 700);
		Settings set = new Settings(Settings.LAPLIMIT);
		set.setLimit(2);
		try {
			Phoebe game = new Phoebe(set);
			listRobots(game.robots);
			listObstacles(game.obstacles);
			listCleaner(game.cleaners);
			game.run();
			listRobots(game.robots);
			listObstacles(game.obstacles);
			listCleaner(game.cleaners);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * GameEndWithTimeElapsing_Test
	 * A játék végét szimuláló teszt. Azt az esetet szimulálja, hogy letelik az idő játék futása közben. 
	 * Egy játék inicializálás után átugrunk időben a játék végére és szimuláljuk, hogy egy utolsó ugrást tesznek a robotok. 
	 * Várható hibalehetőségek: nem várt kivétel, a győztes kihirdetése nem valósul meg.
	 */
	private static void testGameEndWithTimeElapsing() {
		MapBuilder map = new MapBuilder();
		map.building(1000, 700);
		Settings set = new Settings(Settings.TIMELIMIT);
		set.setLimit(2);
		try {
			Phoebe game = new Phoebe(set);
			listRobots(game.robots);
			listObstacles(game.obstacles);
			listCleaner(game.cleaners);
			game.run();
			listRobots(game.robots);
			listObstacles(game.obstacles);
			listCleaner(game.cleaners);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		/*
		 * //	GUI g=new GUI();
		 */
		int mode = 1; //LapLimit=1, TimeLimit=2
		Phoebe.Settings set = new Phoebe.Settings(mode);
		//...
		int limit = 120; //M�sodperc
		set.setLimit(limit);
		//...
		int step = 3; //M�sodperc
		set.setStep(step);
		//...
		//Sz�l l�trehoz�sa, ind�t�sa
		//	g.game = new Phoebe(set);

		//	Thread t = new Thread( new Phoebe(set));
		//t.start();
		//...


		/*
		 * Testesetek függvényeinek meghívása: 
		 */
		//LogBase
		/*	
		1.5.1.    CollisionWithRobot_VOLTÜTKÖZÉS_TESZT  		Vazul
		1.5.2.    CollisionWithRobot_NEMVOLTÜTKÖZÉS_TESZT.  	 Vazul
		1.5.3.    CollisionWithRobot_IRÁNYVÁLTOZTATÁS_TESZT		Vazul
		1.5.4.    CollisionWithObstacles_OLAJBA.UGRÁS_TESZT 	Vince
		1.5.5.    CollisionWithObstacles_RAGACSBA.UGRÁS_TESZT 	Vince
		1.5.6.    CollisionWithObstacles_OLAJ.HATÁSA_TESZT 		Vince
		1.5.7.    CollisionWithObstacles_RAGACS.HATÁSA_TESZT 	Vince
		1.5.8.    robotOutsideOfMap_A.PÁLYÁRÓL.LEESETT_TESZT	Attó
		1.5.9.    robotOutsideOfMap_NEM.ESETT.LE.PÁLYÁRÓL_TESZT Attó
		1.5.10.  checkpointSearch_CHEICKPOINTONBA.UGRÁS_TESZT 	Attó
		1.5.11.  checkpointSearch_CHEICKPOINTONBA.NEM.UGRÁS_TESZT Attó
		1.5.12.  RobotCollisionWithCleaner_TESZT				Vince
		1.5.13.  Initialisation_Test							Don
		1.5.14.  GameEndWithTimeElapsing_Test					Don
		1.5.15.  AddObstacle_AKADÁLY_LERAKÁS_TESZT				Milán			
		1.5.16.  ObstacleLife_AKADÁLY_ÉLETTARTAM_TESZT			Milán
		1.5.17.  Cleaner_TAKARÍTÓ_KISROBOT_MOZGÁS_TAKARÍTÁS_TESZT Milán
					}*/



		//	if(args.length>1){
		switch(Integer.parseInt(args[0])){
		
		case 1://VOLT ÜTKÖZÉS TESZT
			testCollisionWithRobotCollision();
			break;
			
		case 2://NEM VOLT ÜTKÖZÉS TESZT
			testCollisionWithRobotNoCollision();	
			break;

		case 3://KEYPRESSED TESZT(IRÁNYVÁLTÁS)
			testCollisionWithRobotKeyPressed();				
			break;

		case 4://OLAJBA.UGRÁS_TESZT
			testCollisionWithOil();
			break;
			
		case 5://RAGACSBA.UGRÁS_TESZT
			testCollisionWithGlue();
			break;
			
		case 6://OLAJ.HATÁSA_TESZT
			testEffectOil();
			break;
			
		case 7://RAGACS.HATÁSA_TESZT
			testEffectGlue();
			break;

		case 8://A.PÁLYÁRÓL.LEESETT_TESZT		
			testRobotOutsideOfMap();
			break;

		case 9://NEM.ESETT.LE.PÁLYÁRÓL_TESZT
			testRobotNotOutsideOfMap();
			break;
		
		case 10://CHECKPOINTONBA.UGRÁS_TESZT
			testCheckpointCollide();
			break;
			
		case 11://CHECKPOINTONBA.NEM.UGRÁS_TESZT
			testCheckpointNotCollide();
			break;
			
		case 12://RobotCollisionWithCleaner_TESZT
			testRobotCollisionWithCleaner();
			break;
		
		case 13://Initialisation Test
			testInitialisation();
			break;
			
		case 14://TODO GameEndWithTimeElapsing_Test
			testGameEndWithTimeElapsing();
			break;
			
		case 15://AKADÁLY_LERAKÁS_TESZT
			testAddObstacle();
			break;	
			
		case 16://AKADÁLY_ÉLETTARTAM_TESZT
			testObstacleLife();
			break;
		
		case 17://TAKARÍTÓ_KISROBOT_MOZGÁS_TAKARÍTÁS_TESZT
			testCleaner();
			break;
			//	}
		}


	}

}

