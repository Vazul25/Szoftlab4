package major;
import java.awt.Graphics;
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
 * IVisisble Unit-ból származva
 * @see major.Obstacle
 */

public class Glue extends Obstacle {

	//TODO comment
	private static BufferedImage img;
	
	
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
	 */
	public Glue(int x, int y) {
		super(x, y);
		//4 robot után elkopik
		lifetime = 4;
		// TODO Auto-generated constructor stub
	}
	

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
		System.out.println("you jumped into glue");
		
		//Csökkentjük lifetime értékét és ha a csökkentés előtt nagyobb volt 0-nál akkor teljesül a hatás
		if(lifetime-- > 0){
			r.setGlue();
		}
		
	}
	/*
	 * (non-Javadoc)
	 * @see major.Obstacle#checkAlive()
	 */
	@Override
	public boolean checkAlive(){
		if(lifetime > 0) return true;
		else return false;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(img, x, y, WIDTH, HEIGHT, null);
	}
	public  static void setUnitImage() throws IOException{
		img=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"glue.jpg"));
	}
	@Override
	public String toString() {
		return "Glue [x=" + x + ", y=" + y + ", Width=" + WIDTH +", Height=" + HEIGHT +", Lifetime=" +lifetime +"]";
	}

}
