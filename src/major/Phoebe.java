package major;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import minor.MapBuilder;
import minor.Timer;

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
	private boolean ended;
	private Settings gameInfo;
	
	//Beállítások
	/*
	 * Setting Enum
	 */
	public final static class Settings{
		//...
		public static final int LAPLIMIT = 1;
		public static final int TIMELIMIT = 2;
		
		private int information;
		private int limit;
		private int step;
		
		public Settings(int info){
			this.information = info;
		}
		
		public int getSettings(){
			return information;
		}

		public int getLimit() {
			return limit;
		}

		public void setLimit(int limit) {
			this.limit = limit;
		}

		public int getStep() {
			return step;
		}

		public void setStep(int step) {
			this.step = step;
		}
	}
	
	/*
	 * Adatszerkezetek
	 * Mire való:
	 ** robots:A játékban szereplő robotok listája
	 ** obstacles:A játékban szereplő akadályok listája
	 */
	private List<Robot> robots;
	private List<Obstacle> obstacles;
	
	/*
	 * Phoebe konstruktor
	 * Felelősség:A játék felépítése , a robotok,az alap akadályok és map létrehozása
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public Phoebe(Settings set){
		ended=false;
		obstacles=new ArrayList<Obstacle>();
		robots=new ArrayList<Robot>();
		gameInfo = set;
	}
	
	/*
	 * addObstacle függvény
	 * Felelősség:
	 * 
	 * Funkció:
	 * 
	 */
	public void addObstacle(Obstacle item){
		obstacles.add(item);
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
		
		//GameTimer létrehozása, inicializálása
		Timer gameTimer;
		//Ha Időlimites játékmód
		if(gameInfo.getSettings() == Settings.TIMELIMIT)
			gameTimer = new Timer(gameInfo.getLimit());
		//ha körlimites játékmód
		else if(gameInfo.getSettings() == Settings.LAPLIMIT)
			gameTimer = new Timer(0);

		//HUD létrehozása
		HUD hud = new HUD();
		
		//Pálya létrehozása
		MapBuilder map = new MapBuilder();
		//TODO
		//Checkpointok eljuttatása a HUD-ba
		
		
		//Akadályok létrehozása
		for(int i=1;i<=10;i++){
			//TODO Randomgenerált (x,y) pozíciók
			int x=0;
			int y=0;
			obstacles.add(new Oil(x, y, null));
			obstacles.add(new Glue(x, y, null));
		}
		
		//Játékosok létrehozása
		//TODO
		int startPointX = 0; //MApBuilderből
		int startPointY = 0; //MapBuilderből
		int secondStartPos = 0; //MapBuilderből
		robots.add(new Robot(startPointX, startPointY, null, this));
		robots.add(new Robot(startPointX, startPointY+secondStartPos, null, this));
		
		
		while(robots.size()>1 ||!ended)
		{
			//DirectorTimer inicializálás
			//TODO
			Timer directorTimer = new Timer(gameInfo.getStep());
			//...
			
			//Mozgás
			for(int i=0;i<robots.size();i++)
			{
				robots.get(i).move();
				//TODO
				
				for(int j=0;j<robots.size();j++){
					//Ütközés robottal
					robots.get(i).collisionWithRobot(robots.get(j));
					//Ütközés akadállyal
					if(robots.get(i).collisionWithObstacles(obstacles.get(j)))
						obstacles.get(j).effect(robots.get(j));
				}
				//Leesés vizsgálata
				//...
				//deathanimation
				
				
			}			
			repaint();			
		}		
	}
}
