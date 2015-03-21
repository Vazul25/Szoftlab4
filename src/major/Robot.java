package major;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
 * Robot osztĂˇly
 * FelelĹ‘ssĂ©g:
 * A jĂˇtĂ©kban rĂ©szvevĹ‘ ugrĂˇlĂł robotok viselkedĂ©sĂ©t Ă©s kezelĂ©sĂ©t leĂ­rĂł osztĂˇly.
 * Olyan objektum, mely a pĂˇlyĂˇn talĂˇlhatĂł robotokat valĂłsĂ­tja meg. LeĂ­rja a viselkedĂ©sĂĽket Ă©s a kezelĂ©sĂĽket. 
 * A â€žRobotâ€ť osztĂˇly a Unit-bĂłl szĂˇrmazik le, ezĂˇltal van pozĂ­ciĂłja Ă©s az ĂĽtkĂ¶zĂ©s is le van kezelve. 
 * FelelĹ‘s a mozgĂˇsĂ©rt, megĂˇllapĂ­tja egy adott akadĂˇllyal vagy robottal ĂĽtkĂ¶zĂ¶tt-e Ă©s kezeli a felhasznĂˇlĂł Ăˇltal leĂĽtĂ¶tt gombokat.
 * 
 * Ĺ�sosztĂˇly:Unit
 * 
 * InterfĂ©szek:mĂ©g nincs
 *  
 */
public class Robot extends Unit{

	/*
	 * Statikus attribĂştumok
	 * Mire valĂłk:
	 ** staticid:A robot statikus sorszĂˇma , ebbĹ‘l kĂ©pezzĂĽk a sajĂˇt azonosĂ­tĂł szĂˇmukat
	 *
	 ** WIDTH,HEIGHT:placeholder majd a robot kĂ©pĂ©nek szĂ©lessĂ©ge Ă©s magassĂˇga lesz bent ,arra kell,
	 * hogy az irĂˇnyĂ­tĂˇshoz tartozĂł nyil jĂł helyrĹ‘l induljon(de meg lehet Ăşgy is oldani hogy x,y koordinĂˇtĂˇbĂłl 
	 * kivonjuk a buffer image width/2,height/2 adatĂˇt)
	 *
	 */
	protected static int staticid=0;
	private  static final  int r=100; //sugĂˇr

	/*static */int WIDTH=40;//teszt placeholder
	/*static*/ int HEIGHT=40;//teszt placeholder

	/**
	 * TĂˇrolja a mĂ©g felhasznĂˇlatlan ragacsokat.
	 * KezdĹ‘ Ă©rtĂ©k = 1
	 * MaximĂˇlis Ă©rtĂ©k = 3
	 */
	private int numGlue;

	/**
	 * TĂˇrolja a mĂ©g felhasznĂˇlatlan olajfoltokat.
	 * KezdĹ‘ Ă©rtĂ©k = 1
	 * MaximĂˇlis Ă©rtĂ©k = 3
	 */
	private int numOil;

	/*
	 * KĂ©p fĂˇjl
	 * FelelĹ‘ssĂ©g:
	 * 
	 * Melyik fĂĽggvĂ©ny hasznĂˇlja?
	 * 
	 */
	//protected static BufferedImage img[];
	/*
	 * AzonosĂ­tĂł, Ăˇllapot
	 * Mire valĂłk:
	 ** id:a Robot azonosĂ­tĂˇsĂˇra ,ami a keybinding-nĂˇl fontos, kell hogy ne vizsgĂˇljon Ă¶nmagĂˇval ĂĽtkĂ¶zĂ©st
	 *
	 ** slowed: milyen mĂ©rtĂ©kben van lassĂ­tva a robot
	 *
	 ** oiled:megcsuszott-e a robot
	 *
	 */
	private int id;
	private double slowed;//default 1.0
	private boolean oiled;

	/*
	 * Vektor paramĂ©terek
	 * Mire valĂłk:
	 ** arrowendx:hova mutat a bĂ©kĂˇbĂłl kimenĹ‘ nyil(x koordinĂˇta)
	 *
	 ** arrowendy:a bĂ©kĂˇbĂłl kimenĹ‘ nyil y koordinĂˇtĂˇja
	 *
	 ** alpha: arrowendx,y kiszĂˇmĂ­tĂˇsĂˇhoz szĂĽksĂ©ges vizszintes tengelyel bezĂˇrt szĂ¶g radiĂˇnban
	 *
	 */
	private int arrowendx=0;//ahova mutat
	private int arrowendy=0;
	private double alpha=1.57;//kerĂĽlet pontjĂˇnak szĂˇmĂ­tĂˇsĂˇhoz kell radiĂˇn , alapĂ©rtĂ©k 90 fok

	/*
	 * Mire valĂł:
	 ** moved:lĂ©pett e mĂˇr a jĂˇtĂ©kos vagy irĂˇnyitĂˇsi fĂˇzisban van
	 *
	 */
	protected boolean moved;

	//TartalmazĂł objektum
	protected Phoebe p;

	/**
	 * Konstruktor
	 * FelelĹ‘ssĂ©g:
	 * LĂ©trehozza a robotot Ă©s inicializĂˇlja a vĂˇltozĂłit 
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):
	 * A Phoebe.init() a pĂˇlya lĂ©trehozĂˇsakor
	 * 
	 * @param x a robot lĂ©trehozĂˇsĂˇnak x koordinĂˇtĂˇja
	 * @param y a robot lĂ©trehizĂˇsĂˇnak y koordinĂˇtĂˇja
	 */
	public Robot(int x, int y, Phoebe p) {
		super(x, y);
		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);

		id=staticid;
		staticid+=1;

		slowed=1.0;
		oiled=false;

		numGlue = 1;
		numOil = 1;

		this.p=p;

		arrowendx=(int)(x+r*Math.cos(alpha));
		arrowendy=(int)(y+r*Math.sin(alpha));
	}

	// Setter-Getterek

	/**
	 * GetId fĂĽggvĂ©ny
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?): egyenlĹ‘re senki
	 * 
	 * @return a robot egyedi azonosĂ­tĂłja
	 */
	public int getId(){
		return id;
	}

	/**
	 * setOiled fĂĽggvĂ©ny
	 * FelelĹ‘ssĂ©g:
	 * Ă�tĂˇllĂ­tja a robot olaj effekt kĂ¶vetĂ©sĂ©hez tartozĂł Ăˇllapot vĂˇltozĂłt 
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):
	 * Az Oil osztĂˇly effekt fĂĽggvĂ©nye
	 *  	
	 */
	public void setOiled(){
		System.out.println("\t\t\t\t->[:Robot].setOiled():");
		//System.out.println("\t\t\t\t\t Tudja a jĂˇtĂ©kos a robot irĂˇnyĂˇt vĂˇltoztatni kĂ¶vetkezĹ‘ ugrĂˇsnĂˇl? I/N: N");
		oiled=true;
		System.out.println("\t\t\t\t<-[:Robot].setOiled():");
	}

	/**
	 * setGlue fĂĽggvĂ©ny
	 * FelelĹ‘ssĂ©g:
	 * Ă�tĂˇllĂ­tja a robot ragacs effekt kĂ¶vetĂ©sĂ©hez tartozĂł Ăˇllapot vĂˇltozĂłt.
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):
	 * A Glue osztĂˇly effekt fĂĽggvĂ©nye.
	 */
	public void setGlue(){
		System.out.println("\t\t\t\t->[:Robot].setGlue():");
		//System.out.println("\t\t\t\t\t Lelassult a robot? I/N: I");
		slowed=0.5;
		System.out.println("\t\t\t\t<-[:Robot].setGlue():");
	}

	/**
	 * getNumGlue fĂĽggvĂ©nye
	 * FelelĹ‘ssĂ©g:
	 * VisszatĂ©r a felhasznĂˇlhatĂł ragcsok szĂˇmĂˇval.
	 *
	 * FunkciĂł: 
	 * A HUD kĂ©rdezi le, hogy megjelenĂ­thesse a kĂ©pernyĹ‘n.
	 */
	public int getNumGlue(){
		return numGlue;
	}

	/**
	 * getNumOil fĂĽggvĂ©ny
	 * FelelĹ‘ssĂ©g: 
	 * VisszatĂ©r a felhasznĂˇlhatĂł olajok szĂˇmĂˇval
	 *
	 * FunkciĂłk: 
	 * A HUD kĂ©rdezi le, hogy megjelenĂ­tse a kĂ©pernyĹ‘n.
	 */
	public int getNumOil(){
		return numOil;
	}


	public Rectangle getHitbox(){
		System.out.println("\t\t->["+id+":Robot].getHitbox():");
		System.out.println("\t\t<-["+id+":Robot].getHitbox():");
		return hitbox;
	}

	/**
	 * incNumGlue fĂĽggvĂ©ny
	 * FelelĹ‘ssĂ©g:
	 * NĂ¶veli a robotnĂˇl tĂˇrolt ragacsok szĂˇmĂˇt.
	 *
	 * FunkciĂł:
	 * Amikor a robot checkpointot Ă©r, akkor hĂ­vĂłdik meg.
	 */
	public void incNumGlue(){
		System.out.println("\t\t\t->["+id+":Robot].incNumGlue():");
		if(numGlue < 3){
			numGlue++;
		}
		System.out.println("\t\t\t<-["+id+":Robot].incNumGlue():");
	}

	/**
	 * incNumOil fĂĽggvĂ©ny
	 * FelelĹ‘ssĂ©g:
	 * NĂ¶veli a robotnĂˇl tĂˇrolt olajok szĂˇmĂˇt.
	 *
	 * FunkciĂł:
	 * Amikor a robot checkpointot Ă©r, akkor hĂ­vĂłdik meg.
	 */
	public void incNumOil(){
		System.out.println("\t\t\t->["+id+":Robot].incNumOil():");
		if(numOil < 3){
			numOil++;
		}
		System.out.println("\t\t\t<-["+id+":Robot].incNumOil():");
	}

	/**
	 * DeathAnimation fĂĽggvĂ©ny
	 * FelelĹ‘ssĂ©g:Ha meghal egy bĂ©ka akkor ez felel az animĂˇciĂłĂ©rt 
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):mĂ©g nem tudom gondolom a isOnMap ha lesz ilyen
	 * 
	 */
	public void deathanimation(){
		System.out.println("\t->[:Robot].deathanimation()");
		System.out.println("\t<-[:Robot].deathanimation()");
	};


	/**
	 * Move fĂĽggvĂ©ny
	 * 
	 * FelelĹ‘ssĂ©g:
	 * A robot lĂ©ptetĂ©sĂ©t kezeli le.
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):
	 * A jĂˇtĂ©kmotor minden lĂ©pĂ©snĂ©l.
	 */
	@Override
	public void move() throws InterruptedException, IOException {

		System.out.println("\t->[:Robot].move():");
		//User Ăˇltali vĂˇltoztatĂˇs letiltĂˇsa (lĂˇsd Robot.keyPressed())

		Scanner sc = new Scanner(System.in);

		int temp = -1;

		while(temp < 0 || temp > 180){
			System.out.print("\t\t Milyen szögben ugrik a robot? (0-180):");
			temp = sc.nextLine().charAt(0);
		}

		oiled=true;
		//NyĂ­l koordinĂˇtĂˇinak kiszĂˇmolĂˇsa
		arrowendx=(int)(x+slowed*r*Math.cos(temp));
		arrowendy=(int)(y+slowed*r*Math.sin(temp));
		x=arrowendx;	
		y=arrowendy;

		//Olajjal ĂĽtkĂ¶zĂ©s hatĂˇsĂˇnak eltĂĽntetĂ©se
		slowed=1;
		//User Ăˇltali vĂˇltoztatĂˇs engedĂ©lyezĂ©se
		oiled=false;
		
		System.out.print("\t\t\t A Robot új koordinátái: " + x + ", " + y + "\n");

		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
		System.out.println("\t<-[:Robot].move():\n");
	}

	/**
	 * collisionWithObstacles fĂĽggvĂ©ny
	 * 
	 * FelelĹ‘ssĂ©g:
	 * MegnĂ©zi hogy a robot Ă©rintkezett-e valami akadĂˇllyal.
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):
	 * Phoebe.run()
	 * 
	 * @param obstacle az akadĂˇly amire az ĂĽtkĂ¶zĂ©st vizsgĂˇljuk
	 */
	public boolean collisionWithObstacles(Obstacle obstacle){
		Scanner sc = new Scanner(System.in);
		String temp = "";

		while (!temp.equals("i")  && !temp.equals("n") && !temp.equals("I")  && !temp.equals("N")) {

			System.out.println("\t->[:Robot].collisionWithObstacles(obstacle):");
			System.out.print("\t\t Rossz helyre lĂ©pett a robot? i/n: ");
			temp = sc.nextLine();

			if (temp.equals("i") || temp.equals("I"))
			{
				obstacle.effect(this);	
			}
			if (temp.equals("n")  || temp.equals("N")) ;
		}
		System.out.println("\t->[:Robot].collisionWithObstacles(obstacle):");
		return this.intersect(obstacle);		
	}

	/**
	 * bounce fĂĽggvĂ©ny
	 * FelelĹ‘ssĂ©g:
	 * A kĂ¶vetkezĹ‘ lĂ©pĂ©s vĂ©gpontjĂˇnak beĂˇllĂ­tĂˇsa ha volt ĂĽtkĂ¶zĂ©s lepattannak egymĂˇsrĂłl.
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):
	 * Robot.collisionWithRobot()
	 * 
	 */
	public void bounce(){
		System.out.println("\t\t->["+id+":Robot].bounce()");
		System.out.println("\t\t<-["+id+":Robot].bounce()");
	}

	/*
	 * collisionWithRobot fĂĽggvĂ©ny
	 * 
	 * FelelĹ‘ssĂ©g:
	 * Azt vizsgĂˇlja hogy ĂĽtkĂ¶zĂ¶tt e 2 robot Ă©s ha igen meghĂ­vja a bounce fĂĽggvĂ©nyt
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):
	 * Phoebe.run() minden lĂ©pĂ©skor
	 * 
	 * @param r A robot, amivel az ĂĽtkĂ¶zĂ©st vizsgĂˇlni kell.
	 * 
	 */
	public void collisionWithRobot(Robot r){

		Scanner sc = new Scanner(System.in);
		System.out.println("\t->[:Robot].collisionWithRobot(r):");

		int temp='0';

		while(temp!='i'&& temp!='n'&&temp!='I'&& temp!='N'){
			System.out.print("\t\t Van-e ĂĽtkĂ¶zĂ©s? I/N:");
			temp=sc.nextLine().charAt(0);
		}

		if(temp=='i'||temp=='I'){
			bounce();
			r.bounce();
		}
		System.out.println("\t<-[:Robot].collisionWithRobot(r):");
	}



	/*
	 * keyPressed fĂĽggvĂ©ny
	 *
	 * FelelĹ‘ssĂ©g:
	 * A jĂˇtĂ©kos irĂˇnyĂ­tĂˇsĂˇnak esemĂ©nykezelĂ©se.
	 * 
	 * FunkciĂł(ki hĂ­vja meg Ă©s mikor?):
	 * MyListener.run()
	 * 
	 * @param e (lĂˇsd Phoebe.Settings.keyconfig)
	 */
	public void keyPressed(int e) {
		Scanner sc = new Scanner(System.in);
		//NyĂ­l irĂˇnyĂˇnyĂˇnak vĂˇltoztatĂˇsa Ă©s akadĂˇlyok lerakĂˇsa, ha a robot nem lĂ©pett olajba

		//NyĂ­l irĂˇnyĂˇnak megvĂˇltoztatĂˇsa
		if (e == Phoebe.Settings.keyconfig[id%2*4+1])
			alpha+=0.1;
		if (e == Phoebe.Settings.keyconfig[id%2*4])
			alpha-=0.1;

		//Olaj lerakĂˇs
		if(e == Phoebe.Settings.keyconfig[id%2*4+2])  {
			int temp='0';

			System.out.println("\t->[:Robot].KeyPressed(KeyEvent.VK_UP)");
			System.out.print("\t\tVan-e rendelkezĂ©sre ĂˇllĂł olaj? I/N:");

			while(temp!='i'&& temp!='n'&& temp!='I'&& temp!='N') 
				temp=sc.nextLine().charAt(0);

			if(temp =='i' || temp == 'I'){
				Oil item0 = new Oil(x, y);
				p.addObstacle(item0);
			}
			System.out.println("\t<-[:Robot].KeyPressed(KeyEvent.VK_UP):");
		}

		//Ragacs lerakĂˇs
		if(e == Phoebe.Settings.keyconfig[id%2*4+3]){
			int temp='0';
			System.out.println("\t->[:Robot].KeyPressed(KeyEvent.VK_DOWN):");

			while(temp!='i'&& temp!='n'&&temp!='I'&& temp!='N'){
				System.out.print("\t\tVan-e rendelkezĂ©sre Ăˇllo ragacs? I/N:");
				temp=sc.nextLine().charAt(0);
				System.out.println();
			}
			if(temp=='i'||temp=='I'){	
				Glue item1 = new Glue(x, y);
				p.addObstacle(item1);
			}

			System.out.println("\t<-[:Robot].KeyPressed(KeyEvent.VK_DOWN)");
		}
	}
}
