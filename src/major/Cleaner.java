package major;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Iterator;

public class Cleaner extends Robot {
	
	private static final int WIDTH=100;
	private static final int HEIGHT=100;
	List<Obstacle> obstacles;
	
	private enum Concluder{
		MOVING,
		WORKING
	}
	private Concluder state;
	private int cleaning;
	
	/*
	 * Örökölt tagváltozók
	 */
	//protected int x,y;
	//protected Rectangle hitbox;
	//protected static int staticid=0;
	//protected int WIDTH=40;
	//protected int HEIGHT=40;
	//protected static BufferedImage img[];
	//protected boolean moved;
	//protected Phoebe p;
	
	/*
	 * Örökölt metódusok
	 */
	//public boolean intersect
	//public Rectangle getHitbox
	//public int getId
	//public void setOiled
	//public void setGlue
	//public int getNumGlue
	//public int getNumOil
	//public void incNumGlue
	//public void incNumOil
	//public void deathanimation
	//public void paint(Graphics2D g)
	//public static void setUnitImage()
	//public void move()
	//public boolean collisionWithObstacles(Obstacle obstacle)
	//public void bounce()
	//public String toString()
	//public void collisionWithRobot(Robot r)
	//public void keyPressed(int e)
	
	
	public Cleaner(int x, int y, Phoebe p) {
		super(x, y, p);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * TODO
	 *  
	 */
	public void setObstacles(List<Obstacle> obsts){
		if(obsts != null) obstacles = obsts;
	}
	/**
	 * TODO
	 * @return NULL-al tér vissza, ha nincs elérhető akadály
	 */
	private Obstacle nextObstacle(){
		if(obstacles.isEmpty()) 
			return null;
		else{
			double distance, minDist = 30000;
			Obstacle min = null;
			Iterator<Obstacle> it = obstacles.iterator();
			while(it.hasNext()){
				Obstacle obs = it.next();
				distance = Math.sqrt( Math.pow( obs.x-x, 2) + Math.pow( obs.y-y, 2));
				if (distance < minDist){
					minDist = distance;
					min = obs;
				}
			}
			return min;
		}
		
	}
	
	/*
	 * TODO
	 * @see major.Robot#collisionWithRobot(major.Robot)
	 */
	@Override
	public void collisionWithRobot(Robot r){
		
	}
	
	/*
	 * TODO
	 */
	private void colllisionWithCleaner(Cleaner cl){
			
	}
	
	/**
	 * TODO
	 * @return true-val tér vissa, ha sikerült törölni, minden más esetben false-al 
	 */
	private boolean clean(Obstacle obst){
		if(obst != null) return obstacles.remove(obst);
		else return false;
	}
	
	/*
	 * TODO
	 */
	@Override
	public void move(){
		Obstacle destination = null;
		switch(state){
		case MOVING:
			destination = nextObstacle();//megkeressük a következő akadályt
			
			if(destination == null)//Ha nincs akadály akkor maradunk a helyünkön 
				break;
			
			//TODO le kell foglalni
			
			if(!collisionWithObstacles(destination)){ //Ha nem értünk oda megyünk tovább
				arrowendx = 200; 
				arrowendy = 300;
			}else{ //Ha odaértünk egy akadályhoz
				state = Concluder.WORKING;
				cleaning = 3; // 3 körig fog takarítani
			}
			break;
		case WORKING:
			if(cleaning-- < 0){ //Ha lejárt a 3 kör
				if(destination != null) clean(destination); //Törlés
				state = Concluder.MOVING;
			}
			break;
		default:
			break;
		}
	}
	
	/*
	 * Letiltandó függvények
	 * //public void setOiled
	 * //public void setGlue
	 * //public int getNumGlue
	 * //public int getNumOil
	 * //public void incNumGlue
	 * //public void incNumOil
	 */
	public void setOiled(){}
	public void setGlue(){}
	public int getNumGlue(){return 0;}
	public int getNumOil(){return 0;}
	public void incNumGlue(){}
	public void incNumOil(){}
	
}
