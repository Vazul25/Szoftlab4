package minor;

import java.io.IOException;

import major.Phoebe;

/**
 * A grafikus felületet megvalósító objektum. Ez az objektum 
 * maga a menü ami a játék indítása után ugrik fel, itt találhatóak a beállítások 
 * (mint például a gondolkodás idő és a maximális játék idő vagy a körök száma) és a 
 * játékmódok. Gombnyomásra fogja elindítani a játék működési szálát. Ez az objektum 
 * kezeli az ablak eseményeit és a játék bezárását.
 *
 * Felelősség:
 * A grafikus felületért felelős osztály, mely a menüt és a játékot megjeleníti.
 * Ősosztály:
 * 
 * Interfészek:
 *  
 */
public class GUI extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 3610253813412048777L;
	

	/*
	* A {@link Phoebe} játékmotor objektum referenciája.
	*/
	private Phoebe game;
	
	//Tagváltozók
	//private
	//JComboBox, JPanel, JLabel, JButton, JTextField, JMenu, JMenuItem
	//...
	
	/*
	 * Konstruktor. Beállítja az ablak nevét, létrehozza az ablak elemeit, elrendezi őket 
	 * és beállítja a figyelőket(ActionListener).
	 * 
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public GUI(Phoebe p){
		game=p;
	}
	
	/*
	 * initComponents függvény
	 * Felelősség: létrehozza az ablak elemeit, beállítja az ActionListener-eket
	 * 
	 * Funkciók(ki hívja meg és mikor?): A MyGUI konstruktora
	 * 
	 */
	private void initComponents(){
		
	}
	
	/*
	 * createGameButtonActionPerformed függvény
	 * Felelősség: A beállítások kiolvasása a megfelelő objektumokból, 
	 * majd ezekkel a paraméterekkel a játék elindítása.
	 * 
	 * Funkció(ki hívja meg és mikor?): A CreateGame gomb megnyomása után hívódik meg.
	 * 
	 *//*
	private void createGameButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException{
		//...
		//Beállítások lekérdezése
		//...
		int mode = 1; //LapLimit=1, TimeLimit=2
		Phoebe.Settings set = new Phoebe.Settings(mode);
		//...
		int limit = 120; //Másodperc
		set.setLimit(limit);
		//...
		int step = 3; //Másodperc
		set.setStep(step);
		//...
		//Szál létrehozása, indítása
		game = new Phoebe(set);
		Thread t = new Thread(game);
		t.start();
		//...
	}*/


}
