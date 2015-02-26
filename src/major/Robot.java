package major;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

/*
 * Robot osztály
 * Felelősség:
 * 
 * Ősosztály:
 * 
 * Interfészek:
 *  
 */
public class Robot extends Unit{

	/*
	 * Statikus attribútumok
	 * Mire valók:
	 ** staticid:
	 *
	 ** WIDTH:
	 *
	 ** HEIGHT:
	 *
	 */
	static int staticid=0;
	static int WIDTH=40;//teszt placeholder
	static int HEIGHT=40;//teszt placeholder
	
	/*
	 * Azonosító, állapot
	 * Mire valók:
	 ** id:
	 *
	 ** slowed: 
	 *
	 ** oiled:
	 *
	 */
	int id;
	double slowed;//default 1.0
	boolean oiled;
	
	/*
	 * Vektor paraméterek
	 * Mire valók:
	 ** arrowendx:
	 *
	 ** arrowendy:
	 *
	 ** alpha: 
	 *
	 */
	int arrowendx=0;//ahova mutat
	int arrowendy=0;
	double alpha=1.57;//kerület pontjának számításához kell
	
	/*
	 * Mire való:
	 ** moved:
	 *
	 */
	boolean moved;

	/*
	 * KeyEvent
	 * Mire való?
	 * 
	 */
	private static int[] keyconfig={
		KeyEvent.VK_LEFT   , KeyEvent.VK_RIGHT  ,KeyEvent.VK_UP     ,KeyEvent.VK_DOWN,
		KeyEvent.VK_A      , KeyEvent.VK_D      ,KeyEvent.VK_W      ,KeyEvent.VK_S
	};
	
	//Tartalmazó objektum
	Phoebe p;
	
	/*
	 * Konstruktor
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public Robot(int x, int y, String imagelocation,Phoebe p) {
		super(x, y, imagelocation);
		id=staticid;
		staticid+=1;
		this.p=p;
		// TODO Auto-generated constructor stub
	}
	
	// Setter-Getterek
	 
	/*
	 * GetId függvény
	 * @return
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public int getId(){
		return id;
	}
	
	/*
	 * setOiled függvény
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 *  	
	 */
	public void setOiled(){
		oiled=true;
	}
	
	/*
	 * setGlue függvény
	 * Felelősség:
	 * 
	 *  Funkció(ki hívja meg és mikor?):	
	 *
	 */
	public void setGlue(){
		slowed-=slowed/2;
	}
	
	/*
	 * DeathAnimation függvény
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public void deathanimation(){
		//TODO
	};
		
	/*
	 * Paint függvény
	 * @param g 
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public void paint(Graphics g){
		//TODO
	}
	
	/*
	 * Paint függvény
	 * @param g
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 *
	 * Miben más mint a fenti függvény? 
	 */
	public void paint(Graphics2D g) {
		g.fillRect(x, y, WIDTH, HEIGHT);//placeholder ide jön majd a kép
		g.drawLine(x+WIDTH/2, y+HEIGHT/2, arrowendx+WIDTH/2, arrowendy+HEIGHT/2);
		//width,height a buffered image adatai lesznek
	}
	
	/*
	 * Move függvény
	 * @see major.Unit#move()
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub

		while(moved=false){}//azért kell hogy mig nem okézuk le az irányt, ne ugorjon el
		arrowendx=(int)(x+40*Math.cos(alpha));
		arrowendy=(int)(y+40*Math.sin(alpha));	
	}
	
	/*
	 * collisionWithObstacles függvény
	 * @param obstacles
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 */
	public void collisionWithObstacles(List<Obstacle> obstacles){
		
	}
	
	/*
	 * bounce függvény
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public void bounce(){
		
	}
	
	/*
	 * collisionWithRobot függvény
	 * @param r
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public void collisionWithRobot(Robot r){
		if (id==r.getId())return;
		if(this.intersect(r)) {
			bounce();
			r.bounce();
		}
	}
	
	/*
	 * keyPressed függvény
	 * @param e
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public void keyPressed(KeyEvent e) {
		if(id%2==1){
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			alpha+=0.1;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			alpha-=0.1;
		}
		else{
			if (e.getKeyCode() == KeyEvent.VK_A)
				alpha+=0.1;
			if (e.getKeyCode() == KeyEvent.VK_D)
				alpha-=0.1;}
	}
	
}
