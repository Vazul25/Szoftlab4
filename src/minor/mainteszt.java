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
	public void testEffectOil() throws IOException{
		Robot r = new Robot(0, 0, new Phoebe(new Settings(0)));
		Oil o1 = new Oil(0, 0);
		Oil o2 = new Oil(0, 100);

		System.out.println(r.toString());
		System.out.println(o1.toString());
		System.out.println(o2.toString());

		o1.effect(r);

		this.RotateXDeg(r, 90);
		
		r.move();
	}
	
	public void testEffectGlue() throws IOException{
		Robot r = new Robot(0, 0, new Phoebe(new Settings(0)));
		Glue g1 = new Glue(0, 0);
		Glue g2 = new Glue(0, 100);

		System.out.println(r.toString());
		System.out.println(g1.toString());
		System.out.println(g2.toString());

		g1.effect(r);

		this.RotateXDeg(r, 90);
		
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
			}*/


		/////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		/*A FENTI MENÜNEK EHEZ MÁR SEMMI KÖZE CSAK A SZERKEZET LETT ÁTEMELVE MINDENKI AZ ELÖZŐ DOKSI ALAPJÁN CSINÁLJA by:VAZUL*/
		/////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!			
	
	//	if(args.length>1){
			switch(/*Integer.parseInt(args[1])*/3){
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
				System.out.println("4. Ragacsba lépést választotta.");
				System.out.println("> ->[:Phoebe].run():");
				//robots.get(0).collisionWithObstacles(glue1);
				System.out.println("< <-[:Phoebe].run()");
				break;

			case 5:
				System.out.println("5. Olajba lépést választotta.");
				System.out.println("> ->[:Phoebe].run():");
				//robots.get(0).collisionWithObstacles(oil1);
				System.out.println("< <-[:Phoebe].run()");
				break;

			case 6:
				System.out.println("6. A checkpointba lépést választotta.");
				System.out.println("> ->[:Phoebe].run():");
				//hud.checkpointSearch();
				System.out.println("< <-[:Phoebe].run()");
				break;
			case 7:
				System.out.println("7. A Robot ütközését választotta.");
				System.out.println("> ->[:Phoebe].run():");
				//robots.get(0).collisionWithRobot(robots.get(1));
				System.out.println("< <-[:Phoebe].run()");
				break;

			case 8:
				System.out.println("8. Pályáról leesést választotta.");
				System.out.println("> ->[:Phoebe].run():");
				/*	if(map.robotOutsideOfMap(robots.get(0))){
					robots.get(0).deathanimation();
				}*/
				System.out.println("< <-[:Phoebe].run()");
				break;

			case 9:
				/*if(gameInfo.getSettings() == Settings.TIMELIMIT)
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
				System.out.println("< <-[:Phoebe].run()");*/
				break;
			case 10:

				break;
		//	}
		}
			
			
	}
}

