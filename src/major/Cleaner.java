package major;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Cleaner extends Unit {
	
	private static final int WIDTH=30;
	private static final int HEIGHT=30;
	List<Obstacle> obstacles;
	
	private enum Concluder{
		MOVING,
		WORKING
	}
	private Concluder state = Concluder.MOVING;
	private int cleaning;
	
	private int arrowendx;
	private int arrowendy;
	private double alpha=1.57;
	private static final int r=100; //sugár
	protected static int staticid=0;
	protected static BufferedImage img[];
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
	
	
	public Cleaner(int x, int y, Phoebe p) {
		super(x, y);
		hitbox = new Rectangle(x, y, WIDTH, HEIGHT);
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
	
	/*
	 * TODO
	 * @see major.Robot#collisionWithRobot(major.Robot)
	 */
	public boolean collisionWithRobot(Robot r){
		return true;
		
	}
	
	/*
	 * TODO
	 */
	private void colllisionWithCleaner(Cleaner cl){
			
	}
	
	/*
	 * TODO
	 */
	private boolean collisionWithObstacles(Obstacle obst){
		return false;
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
	public void move() throws Exception{
		Obstacle destination = null;
		switch(state){
		case MOVING:
			destination = nextObstacle();//megkeressük a következő akadályt
			
			if(destination == null)//Ha nincs akadály akkor maradunk a helyünkön 
				break;
			
			//TODO le kell foglalni
			
			if(!collisionWithObstacles(destination)){ //Ha nem értünk oda megyünk tovább
			}else{ //Ha odaértünk egy akadályhoz
				state = Concluder.WORKING;
				cleaning = 3; // 3 körig fog takarítani
			}
			break;
		case WORKING:
			if(cleaning-- < 0){ //Ha lejárt a 3 kör
				if(!clean(destination)) throw new Exception(); //Törlés
				state = Concluder.MOVING;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
}
