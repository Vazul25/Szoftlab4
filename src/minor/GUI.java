package minor;

import major.Phoebe;

/*
 * GUI oszt�ly
 * Felel�ss�g:
 * 
 * �soszt�ly:
 * 
 * Interf�szek:
 *  
 */
public class GUI extends javax.swing.JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3610253813412048777L;
	
	
	Phoebe game;
	
	//Tagv�ltoz�k
	//private
	//JComboBox, JPanel, JLabel, JButton, JTextField, JMenu, JMenuItem
	//...
	
	/*
	 * GUI konstruktor
	 * Felel�ss�g:
	 * 
	 * Funkci�(ki h�vja meg �s mikor?):
	 * 
	 */
	public GUI(){
		
	}
	
	/*
	 * initComponents f�ggv�ny
	 * Felel�ss�g: l�trehozza az ablak elemeit, be�ll�tja az ActionListener-eket
	 * 
	 * Funkci�k(ki h�vja meg �s mikor?): A MyGUI konstruktora
	 * 
	 */
	private void initComponents(){
		
	}
	
	/*
	 * createGameButtonActionPerformed f�ggv�ny
	 * Felel�ss�g: A be�ll�t�sok kiolvas�sa a megfelel� objektumokb�l, 
	 * majd ezekkel a param�terekkel a j�t�k elind�t�sa.
	 * 
	 * Funkci�(ki h�vja meg �s mikor?): A CreateGame gomb megnyom�sa ut�n h�v�dik meg.
	 * 
	 */
	private void createGameButtonActionPerformed(java.awt.event.ActionEvent evt){
		//...
		//Be�ll�t�sok lek�rdez�se
		//...
		int mode = 1; //LapLimit=1, TimeLimit=2
		Phoebe.Settings set = new Phoebe.Settings(mode);
		//...
		int limit = 120; //M�sodperc
		set.setLimit(limit);
		//...
		int step = 3; //M�sodperc
		set.setStep(step);
		//...
		//Sz�l l�trehoz�sa, ind�t�sa
		game = new Phoebe(set);
		Thread t = new Thread(game);
		t.start();
		//...
	}
	

}
