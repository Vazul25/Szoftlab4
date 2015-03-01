package major;

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
	private static final long serialVersionUID = 8435890710077230081L;
	//Attribútumok
	/*
	 * 
	 * Mire való:
	 ** ended: Állapot változó, ha vége a játéknak true érték íródik be
	 ** gameInfo:
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
	private HUD hud;
	private MapBuilder map;
	
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
		//TODO Akadályok, Map kirajzolása 
	}
	/*
	 * init függvény
	 * Felelősség:
	 * 
	 * Funkcionalitás:
	 * 
	 */
	private void init(){
		//GameTimer létrehozása, inicializálása
		Timer gameTimer;
		//Ha Időlimites játékmód
		if(gameInfo.getSettings() == Settings.TIMELIMIT)
			gameTimer = new Timer(gameInfo.getLimit());
		//ha körlimites játékmód
		else if(gameInfo.getSettings() == Settings.LAPLIMIT)
			gameTimer = new Timer(0);
		
		//Pálya létrehozása
		map = new MapBuilder();
		//TODO 
		
		//Játékosok létrehozása
		//TODO
		int startPointX = 0; //MApBuilderből
		int startPointY = 0; //MapBuilderből
		int secondStartPos = 0; //MapBuilderből
		Robot one = new Robot(map.getStartPosPlayer(1)[0], map.getStartPosPlayer(1)[1], null, this);
		Robot two = new Robot(map.getStartPosPlayer(2)[0], map.getStartPosPlayer(2)[2], null, this);
		robots.add(one);
		robots.add(two);
		
		//HUD létrehozása
		hud = new HUD(robots);
		
		//Checkpointok eljuttatása a HUD-ba
		hud.setCheckpoints(map.getCheckpoints());
		
		//Akadályok létrehozása
		for(int i=1;i<=10;i++){
			//TODO Randomgenerált (x,y) pozíciók
			int x=0;
			int y=0;
			Oil item1 = new Oil(x, y, null);
			Glue item2 = new Glue(x, y, null);
			if(!map.fallingDown(item1.getHitbox()))	obstacles.add(item1);
			if(!map.fallingDown(item2.getHitbox())) obstacles.add(item2);
		}						
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
		
		init();
		
		//repaint();
		//TODO rajzolás
		Timer directorTimer = new Timer(gameInfo.getStep());
		
		while(robots.size()>1 ||!ended)
		{
			//...
			while(!directorTimer.isZero()){
				//várunk amíg le nem jár a három másodperc
			}
			
			//Mozgás
			for(int i=0;i<robots.size();i++){
				robots.get(i).move();
				
			}
			//TODO lépésanimálása egyenesen végig léptetgetni a robotot és kirajzolni
			
			//Checkpointok vizsgálata, áthaladtunk-e?
			hud.checkpointSearch();
			
			//Ütközések
			for(int i=0;i<robots.size();i++)
			{				
				
				for(int j=0;j<obstacles.size();j++){
				//Ütközés akadállyal
				if(robots.get(i).collisionWithObstacles(obstacles.get(j)))
					obstacles.get(j).effect(robots.get(j));
				}
				
				for(int j=0;j<robots.size();j++){
					//Ütközés robottal
					robots.get(i).collisionWithRobot(robots.get(j));
				}
				//Leesés vizsgálata
				if(map.fallingDown(robots.get(i).getHitbox())){
					ended = true;
					robots.get(i).deathanimation();
				};			
			}			
			//repaint();			
		}		
	}
}
