package junit;

import static org.junit.Assert.*;

import java.io.IOException;

import major.Glue;
import major.Oil;
import major.Phoebe;
import major.Robot;
import major.Phoebe.Settings;

import org.junit.Before;
import org.junit.Test;

public class ObstacleTest {
	
	Robot r;
	Oil o;
	Glue g;

	@Before
	public void initObjects(){
		try {
			r = new Robot(0, 0, new Phoebe(new Settings(0)));
			o = new Oil(0, 0);
			g = new Glue(0, 0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*
	 * Itt az irányt át kellene állítani, hogy ne ugorjon bele a megadott következő
	 * olajba, ezáltal ha beleugrik, az irányváltoztatás nem megy, az olaj effect jó.
	 */
	@Test
	public void testEffectOil(){
		assertNotNull(r);
		assertNotNull(o);

		o.effect(r);

		//r.keyPressed(30);
		
		try {
			r.move();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(r.collisionWithObstacles(new Oil(100, 0)), true);
	}
	
	/*
	 * Itt az beragacsozzuk a robotot, majd megnézzük, hogy ütközik-e egy
	 * olyan ragaccsal, amit alap ugrással elérne, de így nem éri el.
	 */
	@Test
	public void testEffectGlue(){
		assertNotNull(r);
		assertNotNull(g);

		g.effect(r);
		
		try {
			r.move();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotEquals(r.collisionWithObstacles(new Glue(0, 100)), true);
	}
	
	@Test
	public void testOilLife(){
		//TODO
	}
	
	@Test
	public void testGlueLife(){
		//TODO
	}

}
