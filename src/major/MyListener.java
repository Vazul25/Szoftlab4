package major;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
/*
 * MyListener osztály
 *
 * A MyListener osztály hivatott megoldani, hogy egyszerre több gomb lenyomásást helyesen kezelje a játékmotor. Külön szálon fut, a gombokhoz rendelt KeyListener interfészt valósítja meg.
 *
 * Interfészek:
 * KeyListener, Runnable
 *
 * Felelősség:
 * Nyilvántartja a gombok lenyomását és felengedését. Meghívja a gombhoz tartozó robotnak a gombnyomást lekezelő metódusát.
 *
 * Funkció:
 * Külön szálon fut a háttérben.
 */
public class MyListener implements KeyListener, Runnable  {
	
	//
	private boolean isUp, isDown, isRight,isLeft,isW,isD,isS,isA;
	
	List<Robot> robots;
	
	public MyListener(List<Robot> r) {
		super();
		this.isUp = false;
		this.isDown = false;
		this.isRight = false;
		this.isLeft = false;
		this.isW = false;
		this.isD = false;
		this.isS = false;
		this.isA = false;
		robots=r;
	}

	@Override
	public void run() {
		while(true){
			if(!robots.get(0).getOiled()){
			if(isUp) robots.get(0).keyPressed(KeyEvent.VK_UP);
			if (isDown)robots.get(0).keyPressed(KeyEvent.VK_DOWN);
			if(isRight)robots.get(0).keyPressed(KeyEvent.VK_RIGHT);
			if(isLeft)robots.get(0).keyPressed(KeyEvent.VK_LEFT);}
			
			if(!robots.get(1).getOiled()){
				
			if(isD)robots.get(1).keyPressed(KeyEvent.VK_D);
			if (isA)robots.get(1).keyPressed(KeyEvent.VK_A);
			if(isS)robots.get(1).keyPressed(KeyEvent.VK_S);
			if(isW)robots.get(1).keyPressed(KeyEvent.VK_W);
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP: isUp = true; break;
		case KeyEvent.VK_DOWN: isDown = true; break;
		case KeyEvent.VK_RIGHT: isRight = true; break;
		case KeyEvent.VK_LEFT: isLeft= true; break;
		case KeyEvent.VK_W: isW = true; break;
		case KeyEvent.VK_S: isS = true; break;
		case KeyEvent.VK_A: isA = true; break;
		case KeyEvent.VK_D: isD= true; break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP: isUp = false; break;
		case KeyEvent.VK_DOWN: isDown = false; break;
		case KeyEvent.VK_RIGHT: isRight = false; break;
		case KeyEvent.VK_LEFT: isLeft= false; break;
		case KeyEvent.VK_W: isW = false; break;
		case KeyEvent.VK_S: isS= false; break;
		case KeyEvent.VK_A: isA = false; break;
		case KeyEvent.VK_D: isD= false; break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
}
