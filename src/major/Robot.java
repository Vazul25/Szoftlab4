﻿package major;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Robot osztály
 * Felelősség:
 * A játékban résztvevő ugráló robotok viselkedését és kezelését leíró osztály, tárolja és kezeli a felhasználható akadályok számát.
 * Olyan objektum, mely a pályán található robotokat valósítja meg. Leírja a viselkedésüket és a kezelésüket. 
 * A „Robot” osztály a Unit-ból származik le, ezáltal van pozíciója és az ütközés is le van kezelve. 
 * Felelős a mozgásért, megállapítja egy adott akadállyal vagy robottal ütközött-e és kezeli a felhasználó által leütött gombokat.
 * 
 * Ősosztály:Unit
 * 
 * Interfészek:
 * IVisible Unitból származva
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

	//TESZT
	//private  static final  int ANIMATIONSPEED=5;
	protected int WIDTH=40;//teszt placeholder
	protected int HEIGHT=40;//teszt placeholder

	/**
	 * Tárolja a még felhasználatlan ragacsokat.
	 * Kezdő érték = 1
	 * Maximális érték = 3
	 */
	private int numGlue;

	/**
	 * Tárolja a még felhasználatlan olajfoltokat.
	 * Kezdő érték = 1
	 * Maximális érték = 3
	 */
	private int numOil;
	/**
	 * Megmondja hogy raktunk-e már le ebbe a körbe olajat vagy ragacsot
	 * kezdő érték false, minden lépés után vissza áll false ra és minden obstacle lerakásnál true ra 
	 */
	private boolean leftobstacle;

	/*
	 * Kép fájl
	 * Felelősség:
	 * 
	 * Melyik függvény használja?
	 * 
	 */
	protected static BufferedImage img[];
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
	private double slowed;//default 1.0
	private boolean oiled;

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
	public int arrowendx=0;//ahova mutat
	public int arrowendy=0;
	private double alpha= 0;//kerület pontjának számításához kell radián , alapérték 90 fok

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
	 * Létrehozza a robotot és inicializálja a változóit.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A Phoebe.init() a pálya létrehozásakor
	 * 
	 * @param x a robot létrehozásának x koordinátája
	 * @param y a robot létrehizásának y koordinátája
	 */
	public Robot(int x, int y, Phoebe p) {
		super(x, y);
		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);

		id=staticid;
		staticid+=1;

		slowed=1.0;
		oiled=false;

		numGlue = 3;
		numOil = 3;

		this.p=p;

		arrowendx=(int)(x+r*Math.cos(alpha));
		arrowendy=(int)(y+r*Math.sin(alpha));
	}

	// Setter-Getterek

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
		oiled=true;
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
		slowed=0.5;
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
		return numOil;
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
		if(numGlue < 3){
			numGlue++;
		}
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
		if(numOil < 3){
			numOil++;
		}
	}

	/**
	 * DeathAnimation függvény
	 * Felelősség:Ha meghal egy béka akkor ez felel az animációért 
	 * 
	 * Funkció(ki hívja meg és mikor?):még nem tudom gondolom a isOnMap ha lesz ilyen
	 * 
	 */
	public void deathanimation(){
		//TODO

	};

	/**
	 * Paint függvény
	 * 
	 * Felelősség:kirajzolja a robotot a saját koordinátáin + ha nem lép akkor a nyilat is
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * a játékmotor fő ciklusa
	 * 
	 * @param g grafikus felület
	 * 
	 */
	public void paint(Graphics g) {

		((Graphics2D) g).setStroke(new BasicStroke(5));
		if(!oiled)
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, arrowendx+WIDTH/2, arrowendy+HEIGHT/2);
		g.drawImage(img[id%2], x, y, WIDTH, HEIGHT, null);


	}
	public  static void setUnitImage() throws IOException{
		img=new BufferedImage[2];
		img[0]=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"frog0.jpg"));
		img[1]=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"frog1.jpg"));
	}


	/**
	 * Move függvény
	 * 
	 * Felelősség:
	 * A robot léptetését kezeli le.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A játékmotor minden lépésnél.
	 * @throws IOException 
	 */
	@Override
	public void move()   {
		//User általi változtatás letiltása (lásd Robot.keyPressed())
		oiled=true;
		//if(alpha>6.283)alpha-=6.283;

		//Nyíl koordinátáinak kiszámolása

		arrowendx=(int)Math.round(x+slowed*r*Math.cos(alpha));
		arrowendy=(int)Math.round(y+slowed*r*Math.sin(alpha));
		//x=arrowendx;	
		//y=arrowendy;

		//TESZT
		/*	double speedx=Math.round((arrowendx-x)/ANIMATIONSPEED);
		double speedy=Math.round((arrowendy-y)/ANIMATIONSPEED);
		 */

		//Olajjal ütközés hatásának eltüntetése
		slowed=1;

		//TESZT, GRAFIKA
		/*	try {
			img[0]=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"frog1.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HEIGHT=60;*/
		//if(Math.abs((int)(arrowendx-x))<20 &&Math.abs((int)(arrowendy-y))<20) reached=true;

		/*	for(int i=0;i<ANIMATIONSPEED;i++){
			if(i<ANIMATIONSPEED/2){WIDTH+=2;HEIGHT+=2;}
			else {WIDTH-=2;HEIGHT-=2;}
			x+=speedx;
			y+=speedy;
			p.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

		//WIDTH=40;

		//HEIGHT=40;
		x=arrowendx;
		y=arrowendy;
		leftobstacle=false;
		oiled=false;
		/*try {
			img[0]=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"frog0.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);

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
	public boolean collisionWithObstacles(Obstacle o){

		if(this.intersect(o)) {
			//double temp=r.alpha;
			System.out.println("there was a collision between this: "+this.toString()+"\nand this: "+o.toString());
			//r.alpha=alpha;
			//alpha=temp;
			//	bounce(r);			
			//	move();
			//r.move();
			return true;
		}
		return false;
	}
	
	public boolean collisionWithCleaner(Cleaner cl){
		if(this.intersect(cl))
			return true;

		return false;
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
	public void bounce(Robot r){
		double temp=r.alpha;
		r.alpha=alpha;
		alpha=temp;
		//double calculatedAnglesRobot0 = VectorUtil.getVectorsSummRobot0(new Point(x,y), new Point(arrowendx, arrowendy), new Point(r.x,r.y), new Point(r.arrowendx, r.arrowendy));
		//double calculatedAnglesRobot1 = VectorUtil.getVectorsSummRobot0(new Point(r.x,r.y), new Point(r.arrowendx, r.arrowendy), new Point(x,y), new Point(arrowendx, arrowendy));
		//alpha = calculatedAnglesRobot0;
		//r.alpha = calculatedAnglesRobot1;
	}

	@Override
	public String toString() {
		return "Robot [id=" + id + ", slowed=" + slowed + ", oiled=" + oiled
				+ ", x=" +x + ", y=" +y
				+ ", nextx=" + arrowendx + ", nexty=" + arrowendy
				+ ", alpha=" + alpha + ", width=" + WIDTH +", height=" + HEIGHT +", NumGlue:"+numGlue+", NumOil:"+numOil+"]";
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
	public boolean collisionWithRobot(Robot r) throws InterruptedException, IOException{
		if (this == r)
			return false;
		if(this.intersect(r)) {
			//double temp=r.alpha;
			System.out.println("there was a collision between this:\n"+this.toString()+"\n and this:"+r.toString());
			//r.alpha=alpha;
			//alpha=temp;
			//	bounce(r);			
			//	move();
			//r.move();
			return true;
		}
		return false;
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

		//Nyíl irányányának változtatása és akadlyok lerakása, ha a robot nem lépett olajba
		if(!oiled){
			//Nyíl irányának megváltoztatása
			if (e== Phoebe.Settings.keyconfig[id%2*4+1])
				alpha+=0.0872764626;//5fokkal növeli
			if (e== Phoebe.Settings.keyconfig[id%2*4])
				alpha-=0.0872664626; 
			arrowendx=(int)(x+r*Math.cos(alpha));
			arrowendy=(int)(y+r*Math.sin(alpha));
			//System.out.println("nextx ,nexty modified to:"+arrowendx+","+arrowendy);

		}
		if(!leftobstacle){
			//Olaj lerakás
			if(e == Phoebe.Settings.keyconfig[id%2*4+2])  {
				if(numOil > 0){
					Oil item0 = new Oil(x, y);
					p.addObstacle(item0);
					leftobstacle=true;
					System.out.println("new oil created at:"+x+","+y);
					numOil--;
				}

				else System.out.println("Not enough oil");
			}
			//Ragacs lerakás
			if(e== Phoebe.Settings.keyconfig[id%2*4+3]){
				if(numGlue > 0){
					Glue item1 = new Glue(x, y);
					p.addObstacle(item1);
					leftobstacle=true;
					System.out.println("new glue created at:"+x+","+y);
					numGlue--;
				}
				else System.out.println("Not enough glue");
			}
		}
		//Nyíl végpontjainak kiszámolása
		//p.repaint();
		arrowendx=(int)Math.round((x+slowed*r*Math.cos(alpha)));
		arrowendy=(int)Math.round(y+slowed*r*Math.sin(alpha));
	}

}
