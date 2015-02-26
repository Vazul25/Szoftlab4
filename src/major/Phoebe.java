package major;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/*
 * Phoebe osztály
 * Felelősség:A játékmotorját képviselő osztály
 * 
 * Ősosztályok:nincs
 * 
 * Interfészek:nincs
 * 
 */
public class Phoebe extends JPanel implements Runnable{
	//Attribútumok
	/*
	 * 
	 * Mire való:
	 ** ended:Állapot változó, ha vége a játéknak true érték íródik be
	 *
	 */
	boolean ended;
	
	/*
	 * Adatszerkezetek
	 * Mire való:
	 ** robots:A játékban szereplő robotok listája
	 ** obstacles:A játékban szereplő akadályok listája
	 */
	List<Robot> robots;
	List<Obstacle> obstacles;
	
	/*
	 * Phoebe konstuktor
	 * Felelősség:A játék felépítése , a robotok,az alap akadályok és map létrehozása
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public Phoebe(){
		ended=false;
		obstacles=new ArrayList<Obstacle>();
		robots=new ArrayList<Robot>();
	}
	
/*	public boolean isend(){return ended;}
	public int robotsize(){return robots.size();}*/
	/*
	 * paint függvény
	 *@see javax.swing.JComponent#paint(java.awt.Graphics)
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 	 
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);			
		for(int i=0;i<robots.size();i++)
		{
			 robots.get(i).paint(g2d);
			
		}
	}
	
	/*
	 * Main függvény
	 * Felelősség:
	 * 
	 * Mit indít el:
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Run függvény
	 * @see java.lang.Runnable#run()
	 * Felelősség:
	 * 
	 * Mire való(kit hív meg, ki hívja meg):
	 * 
	 * 
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(robots.size()!=0 ||!ended)
		{
			for(int i=0;i<robots.size();i++)
			{
				robots.get(i).move();
				for(int j=0;j<robots.size();j++)
				{
					robots.get(i).collisionWithRobot(robots.get(j));	
				if(	robots.get(i).collisionWithObstacles(obstacles.get(j)))obstacles.get(j).effect(robots.get(j));
				}
				
			}
			
			repaint();
			
		}
		
	}

}
