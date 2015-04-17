package minor;

import java.awt.event.KeyEvent;
import java.io.IOException;

import major.*;

public class mainteszt {
	//SEGÉD FÜGGVÉNYEK:
	public static void RotateXDeg(Robot r, int deg){

		for(int i=0;i<deg/5;i++){
			if(r.getId()%2==0)r.keyPressed(KeyEvent.VK_RIGHT);
			else r.keyPressed(KeyEvent.VK_A);

		}
		System.out.println("nextx, nexty modified to: "+r.arrowendx+", "+r.arrowendy);
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
		Robot r = new Robot(400, 500, null);
		
		System.out.println(r.toString());
		
		RotateXDeg(r, 270);
		
		r.move();
		
		System.out.println(r.toString());
		
		//MAP-al való ütközés implementálása
		//r.collisionWithObstacles(map);
	}
	
	public static void testRobotNotOutsideOfMap(){
		MapBuilder map = new MapBuilder();
		Robot r = new Robot(400, 500, null);
		
		System.out.println(r.toString());
		
		RotateXDeg(r, 90);
		
		r.move();
		
		System.out.println(r.toString());
		
		//MAP-al való ütközés implementálása
		//r.collisionWithObstacles(map);
	}
	
	/*
	 * CHEICKPOINTONBA.UGRÁS_TESZT
	 * CHEICKPOINTONBA.NEM.UGRÁS_TESZT
	 * Azt ellenörizzük hogy sikeressen össze tudta-e vetni a
	 * hitboxokat a függvény az ugrás végeztével .
	 */
	public static void testCheckpointCollide(){
		MapBuilder map1 = new MapBuilder();
		MapBuilder map2 = new MapBuilder();
		Robot r = new Robot(100, 100, null);
		
		//setCheckpoints 100, 180
		
		System.out.println(r.toString());
		
		//listCheckpoints
		
		RotateXDeg(r, 90);
		
		r.move();
		
		System.out.println(r.toString());
		
		//Checkpointal való ütközés implementálása
		//r.collisionWithObstacles(?checkpoint?);
	}
	
	public static void testCheckpointNotCollide(){
		MapBuilder map1 = new MapBuilder();
		MapBuilder map2 = new MapBuilder();
		Robot r = new Robot(100, 300, null);
		
		//setCheckpoints 100, 180
		
		System.out.println(r.toString());
		
		//listCheckpoints
		
		RotateXDeg(r, 90);
		
		r.move();
		
		System.out.println(r.toString());
		
		//Checkpointal való ütközés implementálása
		//r.collisionWithObstacles(?checkpoint?);
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

		System.out.flush();
		//LogBase
		/*	
		1.5.1.    CollisionWithRobot_VOLTÜTKÖZÉS_TESZT  
		1.5.2.    CollisionWithRobot_NEMVOLTÜTKÖZÉS_TESZT.   
		1.5.3.    CollisionWithRobot_IRÁNYVÁLTOZTATÁS_TESZT
		1.5.4.    CollisionWithObstacles_OLAJBA.UGRÁS_TESZT
		1.5.5.    CollisionWithObstacles_RAGACSBA.UGRÁS_TESZT
		1.5.6.    CollisionWithObstacles_OLAJ.HATÁSA_TESZT
		1.5.7.    CollisionWithObstacles_RAGACS.HATÁSA_TESZT
		1.5.8.    robotOutsideOfMap_A.PÁLYÁRÓL.LEESETT_TESZT
		1.5.9.    robotOutsideOfMap_NEM.ESETT.LE.PÁLYÁRÓL_TESZT
		1.5.10.  checkpointSearch_CHEICKPOINTONBA.UGRÁS_TESZT
		1.5.11.  checkpointSearch_CHEICKPOINTONBA.NEM.UGRÁS_TESZT
		1.5.12.  RobotCollisionWithCleaner_TESZT
		1.5.13.  Initialisation_Test
		1.5.14.  GameEndWithTimeElapsing_Test
		1.5.15.  AddObstacle_AKADÁLY_LERAKÁS_TESZT
		1.5.16.  ObstacleLife_AKADÁLY_ÉLETTARTAM_TESZT
		1.5.17.  Cleaner_TAKARÍTÓ_KISROBOT_MOZGÁS_TAKARÍTÁS_TESZT
					}*/



		//	if(args.length>1){
		switch(/*Integer.parseInt(args[1])*/5){
		
		case 1://VOLT ÜTKÖZÉS TESZT
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



			break;
		case 2://NEM VOLT ÜTKÖZÉS TESZT
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
			break;

		case 3://KEYPRESSED TESZT(IRÁNYVÁLTÁS)
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

		case 8://TODO A.PÁLYÁRÓL.LEESETT_TESZT		
			testRobotOutsideOfMap();
			break;

		case 9://TODO NEM.ESETT.LE.PÁLYÁRÓL_TESZT
			testRobotNotOutsideOfMap();
			break;
		
		case 10://TODO CHECKPOINTONBA.UGRÁS_TESZT
			testCheckpointCollide();
			break;
			
		case 11://TODO CHECKPOINTONBA.NEM.UGRÁS_TESZT
			testCheckpointNotCollide();
			break;
			
		case 12://TODO RobotCollisionWithCleaner_TESZT
			testRobotCollisionWithCleaner();
			break;
		
		case 13://TODO Initialisation Test
			break;
			
		case 14://TODO GameEndWithTimeElapsing_Test
			break;
			//	}
		}


	}
}

