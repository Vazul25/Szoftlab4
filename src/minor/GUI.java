package minor;

import major.Phoebe;

/*
 * GUI osztály
 * Felelősség:
 * 
 * Ősosztály:
 * 
 * Interfészek:
 *  
 */
public class GUI extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 3610253813412048777L;
	
	
	Phoebe game;
	
	//Tagváltozók
	//private
	//JComboBox, JPanel, JLabel, JButton, JTextField, JMenu, JMenuItem
	//...
	
	/*
	 * GUI konstruktor
	 * Felelősség:
	 * 
	 * Funkció(ki hívja meg és mikor?):
	 * 
	 */
	public GUI(){
		
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
	 */
	private void createGameButtonActionPerformed(java.awt.event.ActionEvent evt){
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
	}
	

}
