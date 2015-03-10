package major;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Glue osztály
 * Felelősség:A pályára lerakható olaj megvalósítása.
 * 
 * Ősosztályok: Az Obstacle ami a Unit leszármazottja
 * 
 * Interfészek:még nincs
 *  
 */
public class Oil extends Obstacle {
	protected static BufferedImage img;

	/*
	 * Oil kontruktor
	 * Felelősség:Egy Oil elem létrehozása
	 * @see major.Unit#Unit()
	 * @param x kezdő koordináta
	 * @param y kezdő koordináta
	 * @param imagelocation a ragacs  képének a helye
	 * Funkció(ki hívja meg és mikor?):A játék motor hívja meg mikor új olajat tesznek  a pályára
	 * 	
	 */
	public Oil(int x, int y) {
		super(x, y);
	
		// TODO Auto-generated constructor stub
	}
	

	/*
	 * Effect függvény
	 * @see major.Obstacle#effect(major.Robot)
	 * Felelősség:ez határozza meg hogy milyen hatással van a robotra az olaj
	 * @param r azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat
	 * Funkció(ki hívja meg és mikor?):ütközéskor hívja meg az ütközést vizsgáló függvénye a Robot osztálynak
	 * || a játék motor hivja meg a robotra ami ütközött
	 * 	
	 */
	@Override
	public void effect(Robot r) {
		// TODO Auto-generated method stub
		System.out.println("olajos lett");
		r.setOiled();
	}


	@Override
	public void paint(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(img, x, y, WIDTH, HEIGHT, null);
		
	}
	public  static void setUnitImage() throws IOException{
		img=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"oil.jpg"));
	}

	@Override
	public String toString() {
		return "Oil [x=" + x + ", y=" + y + ", Width=" + WIDTH +", Height=" + HEIGHT + "]";
	}

}
