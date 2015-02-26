package major;

/*
 * Glue osztály
 * Felelősség:A pályára lerakható olaj megvalósítása.
 * 
 * Ősosztályok: Az Obstacle ami a Unit leszármazottja
 * 
 * Interfészek:még nincs
 *  
 */
public class Oil extends Obstacle {
	

	/*
	 * Oil kontruktor
	 * Felelősség:Egy Oil elem létrehozása
	 * @see major.Unit#Unit()
	 * @param x kezdő koordináta
	 * @param y kezdő koordináta
	 * @param imagelocation a ragacs  képének a helye
	 * Funkció(ki hívja meg és mikor?):A játék motor hívja meg mikor új olajat tesznek  a pályára
	 * 	
	 */
	public Oil(int x, int y, String imagelocation) {
		super(x, y, imagelocation);
		// TODO Auto-generated constructor stub
	}
	

	/*
	 * Effect függvény
	 * @see major.Obstacle#effect(major.Robot)
	 * Felelősség:ez határozza meg hogy milyen hatással van a robotra az olaj
	 * @param r azt határozza meg hogy melyik robotra hajtsa végre a változtatásokat
	 * Funkció(ki hívja meg és mikor?):ütközéskor hívja meg az ütközést vizsgáló függvénye a Robot osztálynak
	 * || a játék motor hivja meg a robotra ami ütközött
	 * 	
	 */
	@Override
	public void effect(Robot r) {
		// TODO Auto-generated method stub

	}

	

}
