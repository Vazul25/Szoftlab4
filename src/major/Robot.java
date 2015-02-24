package major;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

public class Robot extends Unit{
	

	private static int[] keyconfig={
		KeyEvent.VK_LEFT   , KeyEvent.VK_RIGHT  ,KeyEvent.VK_UP     ,KeyEvent.VK_DOWN,
		KeyEvent.VK_A      , KeyEvent.VK_D      ,KeyEvent.VK_W      ,KeyEvent.VK_S
	};
	static int WIDTH=40;//teszt placeholder
	static int HEIGHT=40;//teszt placeholder
	static int staticid=0;
	int id;
	double slowed;//default 1.0
	boolean oiled;
	int arrowendx=0;//ahova mutat
	int arrowendy=0;
	double alpha=1.57;//kerület pontjának számításához kell
	boolean moved;
	Phoebe p;
	
	public Robot(int x, int y, String imagelocation,Phoebe p) {
		super(x, y, imagelocation);
		id=staticid;
		staticid+=1;
		this.p=p;
		// TODO Auto-generated constructor stub
	}
	
	public int getid(){return id;}
	public void deathanimation(){};
	public void paint(Graphics g){}
	public void setGlue(){slowed-=slowed/2;}
	public void setOiled(){oiled=true;}
	@Override
	public void move() {
		// TODO Auto-generated method stub

		while(moved=false){}//azért kell hogy mig nem okézuk le az irányt, ne ugorjon el
		arrowendx=(int)(x+40*Math.cos(alpha));
		arrowendy=(int)(y+40*Math.sin(alpha));	
	}
	
	
	public void collisionWithObstacles(List<Obstacle> obstacles){}
	public void bounce(){}
	public void collisionWithRobot(Robot r){
		if (id==r.getid())return;
		if(this.intersect(r)) {
			bounce();
			r.bounce();
		}
	}


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
	public void paint(Graphics2D g) {
		g.fillRect(x, y, WIDTH, HEIGHT);//placeholder ide jön majd a kép
		g.drawLine(x+WIDTH/2, y+HEIGHT/2, arrowendx+WIDTH/2, arrowendy+HEIGHT/2);
		//width,height a buffered image adatai lesznek
	}


}
