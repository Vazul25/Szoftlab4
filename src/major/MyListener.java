package major;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class MyListener implements KeyListener, Runnable  {
	boolean isUpPressed, isDownPressed, isRight,isLeft,isW,isD,isS,isA;
	List<Robot> robots;
	public MyListener(List<Robot> r) {
		super();
		this.isUpPressed = false;
		this.isDownPressed = false;
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
		// TODO Auto-generated method stub
		while(true){
			if(isUpPressed) robots.get(0).keyPressed(KeyEvent.VK_UP);
			if (isDownPressed)robots.get(0).keyPressed(KeyEvent.VK_DOWN);
			if(isRight)robots.get(0).keyPressed(KeyEvent.VK_RIGHT);
			if(isLeft)robots.get(0).keyPressed(KeyEvent.VK_LEFT);
			if(isD)robots.get(1).keyPressed(KeyEvent.VK_D);
			if (isA)robots.get(1).keyPressed(KeyEvent.VK_A);
			if(isS)robots.get(1).keyPressed(KeyEvent.VK_S);
			if(isW)robots.get(1).keyPressed(KeyEvent.VK_W);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP: isUpPressed = true; break;
		case KeyEvent.VK_DOWN: isDownPressed = true; break;
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
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP: isUpPressed = false; break;
		case KeyEvent.VK_DOWN: isDownPressed = false; break;
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
		// TODO Auto-generated method stub

	}
	
}
