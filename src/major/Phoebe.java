package major;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
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
	static BufferedImage background;
	private boolean ended;
	private Settings gameInfo;

	//Beállítások
	/*
	 * Setting Enum
	 */
	public final static class Settings{

		public static final int LAPLIMIT = 1;
		public static final int TIMELIMIT = 2;

		private int racemode;
		private int limit;
		private int step;

		/*
		 * Keyconfig 
		 * Mire való?
		 * A játékos irányitását meghatározó mátrix, id vel indexelve a sor
		 */

		public static final int[] keyconfig={
		KeyEvent.VK_LEFT   , KeyEvent.VK_RIGHT  ,KeyEvent.VK_UP     ,KeyEvent.VK_DOWN,
		KeyEvent.VK_A      , KeyEvent.VK_D      ,KeyEvent.VK_W      ,KeyEvent.VK_S
		};

		public Settings(int info){
			this.racemode = info;
		}

		public int getSettings(){
			return racemode;
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

	private Timer gameTimer;
	
	/*
	 * Phoebe konstruktor
	 * Felelősség:A játék felépítése , a robotok,az alap akadályok és map létrehozása
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public Phoebe(Settings set) throws IOException{
		ended=false;
		obstacles=new ArrayList<Obstacle>();
		robots=new ArrayList<Robot>();
		gameInfo = set;
		init();
		JFrame frame = new JFrame("Phoebe");//ez nincs itt csak tesztelés 
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

				robots.get(1).keyPressed(e);
				robots.get(0).keyPressed(e);
			}
		});
		setFocusable(true);

		//ez is csak teszt mint minden frames
		frame.add(this,BorderLayout.CENTER);
		frame.setSize(1000,700);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
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
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);	
		for(int i=0;i<obstacles.size();i++)
		{
			obstacles.get(i).paint(g2d);

		}
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
	private void init() throws IOException{
		//GameTimer létrehozása, inicializálása
		//Ha Időlimites játékmód
		if(gameInfo.getSettings() == Settings.TIMELIMIT)
			gameTimer = new Timer(gameInfo.getLimit());
		//ha körlimites játékmód
		else if(gameInfo.getSettings() == Settings.LAPLIMIT)
			gameTimer = new Timer(0);

		//Pálya létrehozása
		map = new MapBuilder();
		//TODO 


		Robot.setUnitImage();
		Glue.setUnitImage();
		Oil.setUnitImage();
		background=ImageIO.read(new File(System.getProperty("user.dir")+"\\"+"background.jpg"));
		//Játékosok létrehozása
		//TODO
		int startPointX = 0; //MApBuilderből
		int startPointY = 0; //MapBuilderből
		int secondStartPos = 0; //MapBuilderből
		Robot one = new Robot(300/*map.getStartPosPlayer(1)[0]*/,200/* map.getStartPosPlayer(1)[1]*/,  this);
		//szkeleton teszt célszerűbb eggyel hogy a keybindings os dolgot megusszuk
		Robot two = new Robot(500/*map.getStartPosPlayer(2)[0]*/, 500/*map.getStartPosPlayer(2)[2]*/,  this);
		robots.add(one);
		    robots.add(two);

		//HUD létrehozása
		hud = new HUD(robots);

		//Checkpointok eljuttatása a HUD-ba
		hud.setCheckpoints(map.getCheckpoints());
		//kivettem amig tesztelem ne irjon ki annyi mindent
		//Akadályok létrehozása
		/*	for(int i=1;i<=10;i++){
			//TODO Randomgenerált (x,y) pozíciók
			int x=0;
			int y=0;

			Oil item1 = new Oil(x, y, null);
			Glue item2 = new Glue(x, y, null);
			if(!map.obstacleOutsideOfMap(item1)) obstacles.add(item1);
			if(!map.obstacleOutsideOfMap(item2)) obstacles.add(item2);
		}						
*/
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
		/*
		try {
		//	init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		//repaint();
		//TODO rajzolás
		
		Timer startTimer = new Timer(3);
		startTimer.start();
		/*
		while(!startTimer.isZero()){
			//Idő kiírása
			System.out.println(startTimer.getTime());
		}*/
		gameTimer.start();
		//Ha csak egy robot marad a pályán vagy ha lejár a idő/kör

		int elteltidoteszt=0;
		while( !ended)
		{
		 
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Mozgás
			for(int i=0;i<robots.size();i++){
				System.out.println(robots.get(i).toString());
				try {
					robots.get(i).move();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}	

			//TODO lépésanimálása egyenesen végig léptetgetni a robotot és kirajzolni

			//Checkpointok vizsgálata, áthaladtunk-e?
		//	hud.checkpointSearch();

			//Ütközések
			for(Robot i : robots)
			{			

				for(Obstacle j : obstacles){
					//Ütközés akadállyal
					//	System.out.println(j.toString());
					if(i.collisionWithObstacles(j)){
						System.out.println("utkozes "+j.toString());
						j.effect(i);
					}

				}

				for(Robot k : robots){
					//Ütközés robottal
					i.collisionWithRobot(k);

				}
				//Leesés vizsgálata
			/*	if(map.robotOutsideOfMap(i)){
					ended = true;
					i.deathanimation();
				};*/		

			}			
			repaint();	
			if(elteltidoteszt>=60)ended=true;
			{elteltidoteszt+=3;
			System.out.println("eltelt:"+elteltidoteszt+"mp");}
		}
		if(elteltidoteszt>=60)System.out.println("a játéknak vége, lejárt az idő");
		else System.out.println("a játékos leesett a pályáról");

	}
}
