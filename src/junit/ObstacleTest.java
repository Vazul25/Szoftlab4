package junit;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
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
	 * Itt az irányt átállítjuk, hogy ne ugorjon bele a megadott
	 * olajfoltba, ezáltal ha beleugrik, az irányváltoztatás nem megy, 
	 * az olajfolt effect jó.
	 */
	@Test
	public void testEffectOil(){
		assertNotNull(r);
		assertNotNull(o);

		o.effect(r);
		
		//Irány beállítása, hogy a következő ugrás elérje a (100, 0) koordinátájú olajfoltot
		for (int i = 0; i < 18; i++) {
			r.keyPressed(KeyEvent.VK_A);
		}
		
		try {
			r.move();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(r.collisionWithObstacles(new Oil(0, 100)), true);
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
