package major;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
 * Robot osztály
 * Felelősség:
 * A játékban részvevő ugráló robotok viselkedését és kezelését leíró osztály.
 * Olyan objektum, mely a pályán található robotokat valósítja meg. Leírja a viselkedésüket és a kezelésüket. 
 * A „Robot” osztály a Unit-ból származik le, ezáltal van pozíciója és az ütközés is le van kezelve. 
 * Felelős a mozgásért, megállapítja egy adott akadállyal vagy robottal ütközött-e és kezeli a felhasználó által leütött gombokat.
 * 
 * Ősosztály:Unit
 * 
 * Interfészek:még nincs
 *  
 */
public class Robot extends Unit{

	/*
	 * Statikus attribútumok
	 * Mire valók:
	 ** staticid:A robot statikus sorszáma , ebből képezzük a saját azonosító számukat
	 *
	 ** WIDTH,HEIGHT:placeholder majd a robot képének szélessége és magassága lesz bent ,arra kell,
	 * hogy az irányításhoz tartozó nyil jó helyről induljon(de meg lehet úgy is oldani hogy x,y koordinátából 
	 * kivonjuk a buffer image width/2,height/2 adatát)
	 *
	 */
	protected static int staticid=0;
	private  static final  int r=100; //sugár

	/*static */int WIDTH=40;//teszt placeholder
	/*static*/ int HEIGHT=40;//teszt placeholder

	/**
	 * Tárolja a még felhasználatlan ragacsokat.
	 * Kezdő érték = 1
	 * Maximális érték = 3
	 */
	private int numGlue = 1;

	/**
	 * Tárolja a még felhasználatlan olajfoltokat.
	 * Kezdő érték = 1
	 * Maximális érték = 3
	 */
	private int numOil = 1;

	/*
	 * Kép fájl
	 * Felelősség:
	 * 
	 * Melyik függvény használja?
	 * 
	 */
	//protected static BufferedImage img[];
	/*
	 * Azonosító, állapot
	 * Mire valók:
	 ** id:a Robot azonosítására ,ami a keybinding-nál fontos, kell hogy ne vizsgáljon önmagával ütközést
	 *
	 ** slowed: milyen mértékben van lassítva a robot
	 *
	 ** oiled:megcsuszott-e a robot
	 *
	 */
	private int id;
	private double slowed = 1.0;//default 1.0
	private boolean oiled = false;

	/*
	 * Vektor paraméterek
	 * Mire valók:
	 ** arrowendx:hova mutat a békából kimenő nyil(x koordináta)
	 *
	 ** arrowendy:a békából kimenő nyil y koordinátája
	 *
	 ** alpha: arrowendx,y kiszámításához szükséges vizszintes tengelyel bezárt szög radiánban
	 *
	 */
	private int arrowendx=0;//ahova mutat
	private int arrowendy=0;
	private double alpha=1.57;//kerület pontjának számításához kell radián , alapérték 90 fok

	/*
	 * Mire való:
	 ** moved:lépett e már a játékos vagy irányitási fázisban van
	 *
	 */
	protected boolean moved;

	//Tartalmazó objektum
	protected Phoebe p;

	/**
	 * Konstruktor
	 * Felelősség:
	 * Létrehozza a robotot és inicializálja a változóit 
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A Phoebe.init() a pálya létrehozásakor
	 * 
	 * @param x a robot létrehozásának x koordinátája
	 * @param y a robot létrehizásának y koordinátája
	 */
	public Robot(int x, int y, Phoebe p) {
		super(x, y);
		System.out.println("> \t ->[:Robot].Robot(int,int,Phoebe):");		
		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);

		id=staticid;
		staticid+=1;

		this.p=p;

		arrowendx=(int)(x+r*Math.cos(alpha));
		arrowendy=(int)(y+r*Math.sin(alpha));
		System.out.println("< \t <-[:Robot].Robot(int,int,Phoebe):");
	}

	/**
	 * GetId függvény
	 * 
	 * Funkció(ki hívja meg és mikor?): egyenlőre senki
	 * 
	 * @return a robot egyedi azonosítója
	 */
	public int getId(){
		return id;
	}

	/**
	 * setOiled függvény
	 * Felelősség:
	 * Átállítja a robot olaj effekt követéséhez tartozó állapot változót 
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * Az Oil osztály effekt függvénye
	 *  	
	 */
	public void setOiled(){
		System.out.println(">\t\t\t\t->[:Robot].setOiled():");
		System.out.println("<\t\t\t\t<-[:Robot].setOiled()");
	}

	/**
	 * setGlue függvény
	 * Felelősség:
	 * Átállítja a robot ragacs effekt követéséhez tartozó állapot változót.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A Glue osztály effekt függvénye.
	 */
	public void setGlue(){
		System.out.println(">\t\t\t\t->[:Robot].setGlue():");
		System.out.println("<\t\t\t\t<-[:Robot].setGlue()");
	}

	/**
	 * getNumGlue függvénye
	 * Felelősség:
	 * Visszatér a felhasználható ragcsok számával.
	 *
	 * Funkció: 
	 * A HUD kérdezi le, hogy megjeleníthesse a képernyőn.
	 */
	public int getNumGlue(){
		System.out.println("> \t ->[:Robot].getNumGlue():");
		System.out.println("< \t <-[:Robot].getNumGlue()");
		return numGlue;
	}

	/**
	 * getNumOil függvény
	 * Felelősség: 
	 * Visszatér a felhasználható olajok számával
	 *
	 * Funkciók: 
	 * A HUD kérdezi le, hogy megjelenítse a képernyőn.
	 */
	public int getNumOil(){
		System.out.println("> \t ->[:Robot].getNumOil():");
		System.out.println("< \t <-[:Robot].getNumOil()");
		return numOil;
	}
	
	
	public Rectangle getHitbox(){
		System.out.println(">\t\t->["+id+":Robot].getHitbox():");
		System.out.println("<\t\t<-["+id+":Robot].getHitbox():");
		return hitbox;
	}
	
	/**
	 * incNumGlue függvény
	 * Felelősség:
	 * Növeli a robotnál tárolt ragacsok számát.
	 *
	 * Funkció:
	 * Amikor a robot checkpointot ér, akkor hívódik meg.
	 */
	public void incNumGlue(){
		System.out.println(">\t\t\t->["+id+":Robot].incNumGlue():");
		System.out.println("<\t\t\t<-["+id+":Robot].incNumGlue():");
	}

	/**
	 * incNumOil függvény
	 * Felelősség:
	 * Növeli a robotnál tárolt olajok számát.
	 *
	 * Funkció:
	 * Amikor a robot checkpointot ér, akkor hívódik meg.
	 */
	public void incNumOil(){
		System.out.println(">\t\t\t->["+id+":Robot].incNumOil():");
		System.out.println("<\t\t\t<-["+id+":Robot].incNumOil()");
	}

	/**
	 * DeathAnimation függvény
	 * Felelősség:Ha meghal egy béka akkor ez felel az animációért 
	 * 
	 * Funkció(ki hívja meg és mikor?):még nem tudom gondolom a isOnMap ha lesz ilyen
	 * 
	 */
	public void deathanimation(){
		System.out.println(">\t->[:Robot].deathanimation()");
		System.out.println("<\t<-[:Robot].deathanimation()");
	};


	/**
	 * Move függvény
	 * 
	 * Felelősség:
	 * A robot léptetését kezeli le.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A játékmotor minden lépésnél.
	 */
	@Override
	public void move() {
		//User általi változtatás letiltása (lásd Robot.keyPressed())
		//oiled=true;
		
		Scanner sc = new Scanner(System.in);
		System.out.println(">\t->[:Robot].move():");
		
		int temp = -1;
		
		while(temp < 0 || temp > 180){
			System.out.print("? \t\t 1.1 Milyen szögben ugrik a robot? (0-180):");
			temp=Integer.parseInt(sc.nextLine());
		}
		
		//Nyíl koordinátáinak kiszámolása
		//arrowendx=(int)(x+slowed*r*Math.cos(temp));
		//arrowendy=(int)(y+slowed*r*Math.sin(temp));
		//x=arrowendx;	
		//y=arrowendy;

		//Olajjal ütközés hatásának eltüntetése
		//slowed=1;
		//User általi változtatás engedélyezése
		//oiled=false;

		//hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
		//System.out.print("\t\t\t A robot új koordinátái: " + x + " " + y + "\n");
		System.out.println("<\t<-[:Robot].move()");
	}

	/**
	 * collisionWithObstacles függvény
	 * 
	 * Felelősség:
	 * Megnézi hogy a robot érintkezett-e valami akadállyal.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * Phoebe.run()
	 * 
	 * @param obstacle az akadály amire az ütközést vizsgáljuk
	 */
	public boolean collisionWithObstacles(Obstacle obstacle){
		Scanner sc = new Scanner(System.in);
		String temp = "";
		
		while (!temp.equals("i")  && !temp.equals("n") && !temp.equals("I")  && !temp.equals("N")) {
			
			System.out.println(">\t->[:Robot].collisionWithObstacles(obstacle):");
			System.out.print("?\t\t(4/5).1 Akadályba lépett a robot? i/n: ");
			temp = sc.nextLine();
		
			if (temp.equals("i") || temp.equals("I"))
			{
				obstacle.effect(this);	
			}
			if (temp.equals("n")  || temp.equals("N")) ;
		}
		System.out.println("\t->[:Robot].collisionWithObstacles(obstacle):");
		return true;		
	}

	/**
	 * bounce függvény
	 * Felelősség:
	 * A következő lépés végpontjának beállítása ha volt ütközés lepattannak egymásról.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * Robot.collisionWithRobot()
	 * 
	 */
	public void bounce(){
		System.out.println(">\t\t->["+id+":Robot].bounce():");
		System.out.println("<\t\t<-["+id+":Robot].bounce()");
	}

	/*
	 * collisionWithRobot függvény
	 * 
	 * Felelősség:
	 * Azt vizsgálja hogy ütközött e 2 robot és ha igen meghívja a bounce függvényt
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * Phoebe.run() minden lépéskor
	 * 
	 * @param r A robot, amivel az ütközést vizsgálni kell.
	 * 
	 */
	public void collisionWithRobot(Robot r){
		
		Scanner sc = new Scanner(System.in);
		System.out.println(">\t->[:Robot].collisionWithRobot(r):");
		
		int temp='0';
		
		while(temp!='i'&& temp!='n'&&temp!='I'&& temp!='N'){
			System.out.print("?\t\t7.1 Van-e ütközés? I/N:");
			temp=sc.nextLine().charAt(0);
		}
			
		if(temp=='i'||temp=='I'){
			bounce();
			r.bounce();
		}
		System.out.println("<\t<-[:Robot].collisionWithRobot(r)");
	}



	/*
	 * keyPressed függvény
	 *
	 * Felelősség:
	 * A játékos irányításának eseménykezelése.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * MyListener.run()
	 * 
	 * @param e (lásd Phoebe.Settings.keyconfig)
	 */
	public void keyPressed(int e) {
		Scanner sc = new Scanner(System.in);
		//Nyíl irányányának változtatása és akadályok lerakása, ha a robot nem lépett olajba
			 
			//Nyíl irányának megváltoztatása
			if (e == Phoebe.Settings.keyconfig[id%2*4+1])
				alpha+=0.1;
			if (e == Phoebe.Settings.keyconfig[id%2*4])
				alpha-=0.1;
			
			//Olaj lerakás
			if(e == Phoebe.Settings.keyconfig[id%2*4+2])  {
				int temp='0';
				
				System.out.println("> ->[:Robot].KeyPressed(KeyEvent.VK_UP):");
				System.out.print("?\t3.1 Van-e rendelkezésre álló olaj? I/N:");
				
				while(temp!='i'&& temp!='n'&& temp!='I'&& temp!='N') 
					temp=sc.nextLine().charAt(0);
				
				if(temp =='i' || temp == 'I'){
					Oil item0 = new Oil(x, y);
					p.addObstacle(item0);
				}
				System.out.println("< <-[:Robot].KeyPressed(KeyEvent.VK_UP)");
			}
			
			//Ragacs lerakás
			if(e == Phoebe.Settings.keyconfig[id%2*4+3]){
				int temp='0';
				System.out.println("> ->[:Robot].KeyPressed(KeyEvent.VK_DOWN):");

				while(temp!='i'&& temp!='n'&&temp!='I'&& temp!='N'){
					System.out.print("?\t2.1 Van-e rendelkezésre állo ragacs? I/N:");
					temp=sc.nextLine().charAt(0);
				}
				if(temp=='i'||temp=='I'){	
					Glue item1 = new Glue(x, y);
					p.addObstacle(item1);
				}

				System.out.println("< <-[:Robot].KeyPressed(KeyEvent.VK_DOWN)");
			}
	}
}
