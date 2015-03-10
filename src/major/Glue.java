package major;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * A „Glue” objektum megvalósít egy adott tulajdonságú 
 * akadályt. Amely robot belemegy, annak a sebességét megfelezi. 
 *
 * Felelősség: 
 * A pályára lerakható ragacs megvalósítása.
 * A játékban szereplő Ragacs foltok viselkedését leíró osztály.
 * 
 * Ősosztályok: 
 * Az Obstacle ami a Unit leszármazottja
 * 
 * Interfészek:
 * nincs 
 * @see major.Obstacle
 */

public class Glue extends Obstacle {

	/**
	 * Felelősség:
	 * Egy Glue elem létrehozása
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * A játék motor hívja meg mikor új ragacsot tesznek a pályára.
	 *
	 * @see major.Unit#Unit()
	 * @param x kezdő koordináta
	 * @param y kezdő koordináta
	 * @see major.Unit#Unit()
	 * @see major.Obstacle#Obstacle()
	 */
	public Glue(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	//TODO comment
	 static BufferedImage img;

	/**
	 * Felelősség:
	 * Ez határozza meg hogy milyen hatással van a robotra a ragacs
	 * Funkció(ki hívja meg és mikor?):Ütközéskor hívja meg az ütközést vizsgáló függvénye a Robot osztálynak.
	 * @param r azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat	 
	 * 
	 * @see major.Obstacle#effect(major.Robot)	 
	 */
	@Override
	public void effect(Robot r) {
	
		// TODO Auto-generated method stub
		System.out.println("ragacsos lett");
		r.setGlue();
	}

	@Override
	public void paint(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(img, x, y, WIDTH, HEIGHT, null);
	}
	public  static void setUnitImage() throws IOException{
		img=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"glue.jpg"));
	}
	@Override
	public String toString() {
		return "Glue [x=" + x + ", y=" + y + ", Width=" + WIDTH +", Height=" + HEIGHT + "]";
	}

}
