package major;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Robot extends Unit implements Runnable{
	

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
	Phoebe p;
	
	public Robot(int x, int y, String imagelocation,Phoebe p) {
		super(x, y, imagelocation);
		id=staticid;
		staticid+=1;
		this.p=p;
		// TODO Auto-generated constructor stub
	}
	
	
	public void deathanimation(){};
	public void setGlue(){slowed-=slowed/2;}
	public void setOiled(){oiled=true;}
	@Override
	public void move() {
		
		// TODO Auto-generated method stub
		arrowendx=(int)(x+40*Math.cos(alpha));
		arrowendy=(int)(y+40*Math.sin(alpha));	
	}

	@Override
	public boolean intersect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
		move();
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
