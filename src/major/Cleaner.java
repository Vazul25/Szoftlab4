package major;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Cleaner extends Unit {
	
	/*
	 * * WIDTH: 
	 * * HEIGHT: 
	 */
	private static final int ANIMATIONSPEED=10;
	private static final int WIDTH=40;
	private static final int HEIGHT=40;
	
	//obstacles: A játékba szereplő akadályok listája.
	private List<Obstacle> obstacles;

	/*
	 * Concluder enum
	 * Célja: A kis robot állapotának leírása.
	 */
	private enum Concluder{
		MOVING,
		WORKING
	}
	
	/*
	 * * - boolean dying: A Cleaner robot meghalását jelző logiaki változó.
	 * * - Concluder state: A robot állapotát tároló változó.
	 * * - int cleaning: A takarító robot takarítási idejének változója.
	 * * - boolean bouncing: A kis robot ütközését másik kisrobottal jelző változó.
	 * * - int arrowendx: A kis robot következő pozícióját segítő nyílnak az x koordinátája.
	 * * - int arrownedy: A kis robot következő pozícióját segítő nyílnak az y koordinátája.
	 * * - double alpha: A kis robot irányának számítására használt szög érték.(radiánban)
	 * * - int r: Ugrás nagyságának értéke.
	 * * - Phoebe p: A játék fő osztályára mutató referencia.
	 */
	private boolean dying=false;
	private Concluder state = Concluder.MOVING;
	private int cleaning;
	private boolean bouncing;
	private int arrowendx;
	private int arrowendy;
	private double alpha=1.57;
	private static final int r=100; //sugár
	protected static BufferedImage[] img=new BufferedImage[2];
	protected Phoebe p;

	/*
	 * Örökölt tagváltozók
	 */
	//protected int x,y;
	//protected Rectangle hitbox;


	/*
	 * Örökölt metódusok
	 */
	//public boolean intersect
	//public Rectangle getHitbox
	//public void paint(Graphics2D g)

	/**
	 * Cleaner Konstruktor
	 * Felelősség: Egy Cleaner objektum létrehozásáért, inicializálásáért felelős konstruktor.
	 * @param x
	 * @param y
	 * @param p
	 */
	public Cleaner(int x, int y, Phoebe p) {
		super(x, y);
		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
		if(p!=null ){
			this.p=p;
			obstacles=p.getObstacles();
		}else{
			p = null;
			obstacles = null;
		}
		bouncing=false;
	}

	/**
	 * nextObstacle metódus
	 * Felelősség: Megkeresi a legközelebbi akadályt a listából és visszaadja a rá mutató referenciát.
	 * @return NULL-al tér vissza, ha nincs elérhető akadály
	 */
	private Obstacle nextObstacle(){
		if(obstacles!=null)
		if(!obstacles.isEmpty()){
			double distance, minDist = 30000;
			Obstacle min = null;
			for(Obstacle i : obstacles){
				distance = Math.sqrt( Math.pow( i.x-x, 2) + Math.pow( i.y-y, 2));
				if (distance < minDist){
					minDist = distance;
					min = i;
				}
			}
			return min;
		}
		return null;
	}

	/**
	 * collisionWithRobot metódus
	 * Felelősség: Abban az esetben, amikor Cleaner ütközik Robottal, le kell kezelnie a lepattanást.
	 * @see major.Robot#collisionWithRobot(major.Robot)
	 */
	
	public boolean collisionWithRobot(Robot r){
		if(r.intersect(this))
		{
			//lepattanást számolni
			try {
				System.out.println("\n\n\n\n\npattanunk\n\n\n\n\n\n");
				bouncing=true;
				alpha+=35*0.0872664626; 
				arrowendx=(int)(x+Cleaner.r * Math.cos(alpha));
				arrowendy=(int)(y+Cleaner.r * Math.sin(alpha));
				move();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		}
		return false;

	}

	/**
	 * collisionWithCleaner metódus
	 * Felelősség: Abban az esetben, amikor Cleaner ütközik Cleanerrel, le kell kezelnie mindkettő lepattanást.
	 */
	public boolean collisionWithCleaner(Cleaner cl) {
		if(this==cl) return false;
		
		if(this.intersect(cl)){
			double alpha=cl.alpha;
			cl.alpha=this.alpha;
			this.alpha=alpha;
			arrowendx=(int)(x+r*Math.cos(alpha));
			arrowendy=(int)(y+r*Math.sin(alpha));
			cl.arrowendx=(int)(x+r*Math.cos(alpha));
			cl.arrowendy=(int)(y+r*Math.sin(alpha));
			cl.move();
			this.move();
			return true;
		}
		return false;
	}

	/**
	 * deathAnimation metódus
	 * @return true-val tér vissa, ha sikerült törölni, minden más esetben false-al 
	 */
	public void deathAnimation(){
		for(int i=0;i<15;i++){
			try {
				img[1]=ImageIO.read(new File(System.getProperty("user.dir")+"\\icons\\"+i+".gif"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.update();
			try {
				Thread.sleep(42);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * move metódus
	 * Felelősség:
	 * A kis robot mozgásának lekezeléséért és akadályok takarításért felelős felelős.
	 */
	@Override
	public void move() {
		System.out.println(this.toString());
		Obstacle destination = null;
		switch(state){
		case MOVING:
			destination = nextObstacle();//megkeressük a következő akadályt

			if(destination == null)//Ha nincs akadály akkor maradunk a helyünkön 
				break;

			//TODO le kell foglalni
			//nem hiszem hogy jó itt kéne meghatározni a 2 pont által bezárt szöget
			double distancex=destination.x-x;
			double distancey=destination.y-y;

			alpha=Math.atan2(distancey,distancex);
			if(!bouncing){//ha épp le pattan akkor ezek már ki vannak számolva
				arrowendx=(int)(x+r*Math.cos(alpha));
				arrowendy=(int)(y+r*Math.sin(alpha));
			}
			//ANIMÁLÁS
			double speedx=Math.round((arrowendx-x)/ANIMATIONSPEED);
			double speedy=Math.round((arrowendy-y)/ANIMATIONSPEED);

			for(int i=0;i<ANIMATIONSPEED;i++){
				x+=speedx;
				y+=speedy;
				hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
				p.update();
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Ha odaértünk egy akadályhoz
				if(destination.intersect(this)){
					x=destination.x;
					y=destination.y;
					hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
					if(obstacles!= null)obstacles.remove(destination);
					state = Concluder.WORKING;
					cleaning = 1; 
					break;
					}// 3 körig fog takarítani
			}
			if(!destination.intersect(this)){
				x=arrowendx;
				y=arrowendy;
			}
			break;
		case WORKING:
			if(--cleaning <= 0){ //Ha lejárt a 3 kör
				//	if(!clean(destination)) throw new Exception(); //Törlés
				state = Concluder.MOVING;
			}
			break;
		default:
			break;
		}
		bouncing=false;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		if(dying)g.drawImage(img[1],x, y, WIDTH, HEIGHT, null);
		else g.drawImage(img[0],x, y, WIDTH, HEIGHT, null);
	}

	@Override
	public String toString() {
		return "Cleaner [x=" + x + ",y=" +y
				+ ", nextx=" + arrowendx + ", nexty=" + arrowendy
				+ ", alpha=" + alpha + ", width=" + WIDTH +", height=" + HEIGHT +"]  " +cleaning+" ";
	}
	public  static void setUnitImage() throws IOException{
		img[0]=ImageIO.read(new File(System.getProperty("user.dir")+"\\icons\\"+"cleaner.png"));
	}

	public void die() {
		dying=true;
		if(obstacles!=null )obstacles.add(new Oil(x,y));
		deathAnimation();
	}
}
