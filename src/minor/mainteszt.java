package minor;

import java.awt.event.KeyEvent;
import java.io.IOException;

import major.*;
import major.Phoebe.Settings;

public class mainteszt {
	//SEGÉD FÜGGVÉNYEK:
	public static void RotateXDeg(Robot r,int deg){

		for(int i=0;i<deg/5;i++){
			if(r.getId()%2==0)r.keyPressed(KeyEvent.VK_RIGHT);
			else r.keyPressed(KeyEvent.VK_A);
			
		}
		System.out.println("nextx ,nexty modified to:"+r.arrowendx+","+r.arrowendy);
	}
	
	/*
	 * Effekt Tesztelése
	 * A robotra beállítjuk az adott akadály hatását,
	 * majd ellenőrizük, hogy ez megfelelően kihat-e a robot mozgására.
	 */
	public static void testEffectOil() throws IOException{
		Robot r = new Robot(0, 0, null);
		Oil o1 = new Oil(0, 0);
		Oil o2 = new Oil(0, 100);

		System.out.println(r.toString());
		System.out.println(o1.toString());
		System.out.println(o2.toString());

		o1.effect(r);

		RotateXDeg(r, 90);
		
		r.move();
	}
	
	public static void testEffectGlue() throws IOException{
		Robot r = new Robot(0, 0, null);
		Glue g1 = new Glue(0, 0);
		Glue g2 = new Glue(0, 100);

		System.out.println(r.toString());
		System.out.println(g1.toString());
		System.out.println(g2.toString());

		g1.effect(r);

		RotateXDeg(r, 90);
		
		r.move();
		
		System.out.println(r.toString());
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
			switch(/*Integer.parseInt(args[1])*/7){
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

			case 4:	
				
				break;

			case 5:
				
				break;

			case 6:
				testEffectOil();
				break;
			case 7:
				testEffectGlue();
				break;

			case 8:
				
				break;

			case 9:
				
				break;
			case 10:

				break;
			case 13://Initialisation Test
				
				break;
			case 14://GameEndWithTimeElapsing_Test
				
				break;
		//	}
		}
			
			
	}
}

