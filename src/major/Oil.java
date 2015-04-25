package major;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Glue osztály
 * 
 * Felelősség:
 * Ez az objektum az Obstacle osztály leszármazottja. Hasonlóan a Glue objektumhoz, egy adott hatást 
 * valósít meg, ami letiltja a következő körben történő irányítását a robotnak, ami belelépett.
 * Ha belelép egy játékos egy ilyen olajfoltba, az effect függvény letiltja a mozgatást az adott roboton a 
 * következő ugrásig.
 * 
 * Ősosztályok: 
 * Unit <- Obstacle
 *
 * Interfésze:
 * Unit-ból származott IVisible
 *  
 */
public class Oil extends Obstacle {
	private static BufferedImage img;

	/**
	 * Oil kontruktor
	 *
	 * Felelősség:
	 * Egy Oil elem létrehozásáért felelős.
	 *
	 * @param x kezdő koordináta
	 * @param y kezdő koordináta
	 
	 * Funkció(ki hívja meg és mikor?):
	 * A játék motor hívja meg mikor új olajat tesznek a pályára.
	 * 	
	 */
	public Oil(int x, int y) {
		super(x, y);
		lifetime = 10;
	}
	

	/**
	 * Effect függvény
	 * 
	 * Felelősség:
	 * Meghatározza, milyen hatással van a robotra, ha beleugrik egy 
	 * olajfoltba. Ebben az esetben letiltja a játékost, hogy irányt váltson.
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A Phoebe hivja meg a robotra ami ütközött.
	 *
	 * @param r Azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat
	 * 	
	 */
	@Override
	public void effect(Robot r) {
		System.out.println("you jumped into oil");
		r.setOiled();
	}
	
	/*
	 * TODO
	 * @see major.Obstacle#checkAlive()
	 */
	@Override
	public boolean checkAlive(){
		if(--lifetime > 0 ) return true;
		else return false;		
	}


	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(img, x, y, WIDTH, HEIGHT, null);
		
	}
	public static void setUnitImage() throws IOException{
		img=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"oil.jpg"));
	}

	@Override
	public String toString() {
		return "Oil [x=" + x + ", y=" + y + ", Width=" + WIDTH +", Height=" + HEIGHT +", lifetime:"+lifetime +"]";
	}

}
