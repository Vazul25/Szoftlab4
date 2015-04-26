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
	public GUI(){
		initComponents();
	}
	
	/*
	 * initComponents függvény
	 * Felelősség: létrehozza az ablak elemeit, beállítja az ActionListener-eket
	 * 
	 * Funkciók(ki hívja meg és mikor?): A MyGUI konstruktora
	 * 
	 */
	private void initComponents(){

        jPanel1 = new javax.swing.JPanel();
        gameName = new javax.swing.JLabel();
        gameType = new javax.swing.JComboBox();
        jatekmod = new javax.swing.JLabel();
        limit = new javax.swing.JLabel();
        limitField = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        gameName.setText("Phoebe");

        gameType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Időlimit", "Körlimit" }));

        jatekmod.setText("Játékmód");

        limit.setText("Limit");

        startButton.setText("Indítás");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jatekmod)
                            .addComponent(limit))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(limitField)
                            .addComponent(gameType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(startButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(gameName)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(gameName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gameType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jatekmod, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(limit)
                    .addComponent(limitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(startButton)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
	}
	
	/*
	 * startButtonActionPerformed függvény
	 * Felelősség: A beállítások kiolvasása a megfelelő objektumokból, 
	 * majd ezekkel a paraméterekkel a játék elindítása.
	 * 
	 * Funkció(ki hívja meg és mikor?): A CreateGame gomb megnyomása után hívódik meg.
	 * 
	 */	
	 private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
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
		try {
			game = new Phoebe(set);
			Thread t = new Thread(game);
			t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//...
	    }

	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String args[]) {
	        try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }

	        /* Create and display the form */
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new GUI().setVisible(true);
	            }
	        });
	    }

	    private javax.swing.JLabel gameName;
	    private javax.swing.JComboBox gameType;
	    private javax.swing.JPanel jPanel1;
	    private javax.swing.JLabel jatekmod;
	    private javax.swing.JLabel limit;
	    private javax.swing.JTextField limitField;
	    private javax.swing.JButton startButton;
}
