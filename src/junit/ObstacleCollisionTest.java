/**
 * 
 */
package junit;

import static org.junit.Assert.*;

import java.io.IOException;

import major.Glue;
import major.Oil;
import major.Phoebe;
import major.Phoebe.Settings;
import major.Robot;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Winnie
 *
 */
public class ObstacleCollisionTest {
	
	Robot r;
	Oil o;
	Glue g;

	@Before
	public void initObjects(){
		try {
			r = new Robot(0,0, new Phoebe(new Settings(0)));
			o = new Oil(0,100);
			g = new Glue(0,100);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*
	 * Itt ellenőrizzük, hogy ugye még nem ütköztek,
	 * majd mozgás után a végpontba rakunk egy olajat
	 * és megint megnézzük, hogy mostmár ütköztek.
	 */
	@Test
	public void testCollisionWithOil() {
		assertNotNull(r);
		assertNotNull(o);
		
		assertNotEquals(r.collisionWithObstacles(o), true);
		
		r.move();
		
		assertEquals(r.collisionWithObstacles(o), true);
	}
	
	/*
	 * Itt ellenőrizzük, hogy ugye még nem ütköztek,
	 * majd mozgás után a végpontba rakunk egy ragacsot
	 * és megint megnézzük, hogy mostmár ütköztek.
	 */
	@Test
	public void testCollisionWithGlue() {
		assertNotNull(r);
		assertNotNull(g);
		
		assertNotEquals(r.collisionWithObstacles(g), true);	
		
		r.move();
		
		assertEquals(r.collisionWithObstacles(g), true);
	}

}
