package major;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Glue osztály
 * Felelősség:A pályára lerakható ragacs megvalósítása.
 * 
 * Ősosztályok: Az Obstacle ami a Unit leszármazottja
 * 
 * Interfészek:még nincs
 *  
 */
public class Glue extends Obstacle {
 static BufferedImage img;
	/*
	 * Obstacle kontruktor
	 * Felelősség:Egy Glue elem létrehozása
	 * @see major.Unit#Unit()
	 * @param x kezdő koordináta
	 * @param y kezdő koordináta
	 * @param imagelocation a ragacs  képének a helye
	 * Funkció(ki hívja meg és mikor?):A játék motor hívja meg mikor új ragacsot tesznek  a pályára
	 * 	
	 */
	public Glue(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Effect függvény
	 * @see major.Obstacle#effect(major.Robot)
	 * Felelősség:ez határozza meg hogy milyen hatással van a robotra a ragacs
	 * @param r azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat
	 * Funkció(ki hívja meg és mikor?):ütközéskor hívja meg az ütközést vizsgáló függvénye a Robot osztálynak
	 * 	
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
