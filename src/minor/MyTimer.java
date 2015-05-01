package minor;

import java.math.BigDecimal;

/*
 * Timer osztály
 * Felelősség:
 * 
 * A játék elején a kezdésig visszaszámol három másodpercet, utána indulhat a játék. Játéktípustól függően felfelé(kör játékmód) 
 * vagy visszafelé(idő játékmód) számol. Ez az osztály felelős, azért ha lejár az idő vége legyen a játéknak, 
 * 
 *  
 */
public class MyTimer {
	
	/*
	 * DIR Enum
	 * Az óra számolási irányának enumerizácciója. 
	 */
	private enum DIR{
		Up,
		Down
	}
	
	/**
	 * Attribútumok
	 * 
	 ** - long T_start: Az óra indításának időpontja millisec pontosággal.
	 ** - int duration: Ha az óra visszafelé számol, akkor tárolja, hogy mennyi volt a kezdő érték, ha felfelé számol, akkor értéke 0. Mértékegysége millisekundum.
	 ** - DIR direction: Az óra számolási irányának eltárolásáért felelős enum.
	 */
	private long T_start;
	private long duration;
	private DIR direction;
	
	/**
	 * MyTimer(int i)
	 * 
	 * Felelősség: 
	 * Ha 0-val vagy negatívval inicializálják felfele számol,
	 * ha pozitív számmal inicializálják akkor lefelé számol.
	 * 
	 * @param i A visszaszámolás időtartamát(másodpercben) tartalmazó paraméter.
	 */
	public MyTimer(int i){
		if(i > 0){
			direction = DIR.Down; //lefele számolunk
			duration = i*1000; //Milliszekundomba átváltás
			T_start = System.currentTimeMillis()-2*i*1000; //beállítjuk kétszer duration milliszekundummal korábbi időpontra, hogy a start() egyből elindítsa a Timert. 
		}else{
			direction = DIR.Up;
			duration = 0; 
		}
	}
	
	/**
	 * + void start()
	 * 
	 * Felelősség: 
	 * Az óra indításakor vagy újraindításakor meghívott függvény. Csak akkor indul újra (visszaszámláló üzemmódban), ha elérte a 0-t.
	 * 
	 * Funkciók:
	 * A Phoebe run metódusa hívja meg, mikor vissza kell számolni a játék kezdete előtt három másodpercet. Illetve, a játék kezdetekor.
	 * 
	 */
	public void start(){
		switch (direction){
		case Down:
			if( T_start + duration < System.currentTimeMillis() ) T_start = System.currentTimeMillis();
			break;
		default:
			T_start = System.currentTimeMillis();
			break;
		}
		
	}
	
	/**
	 * + boolean isZero()
	 * 
	 * Felelősség:
	 * Az idő lejárását ellenörző függvény, megadja, hogy az indítás plusz a megadott időtartam kisebb-e a pillanatnyi időnél.
	 * 
	 * @return Igazzal tér vissza, ha lejárt az idő, illetve hamissal minden más esetben.
	 */
	public boolean isZero(){
		switch(direction){
		case Down:
			return ( T_start + duration <= System.currentTimeMillis());	
		default:
			return false;
		}
			
	}
	
	/**
	 * + int getTime()
	 * 
	 * Felelősség: 
	 * Ha pozitív számal inicializálódott az objektum, akkor megadja mennyi idő van még hátra a visszaszámlálásból 
	 * vagy, ha nullával, akkor a start() hívás óta eltelt idővel tér vissza.
	 * 
	 * @return Visszatér egy szekundumban megadott értékkel.
	 */
	public int getTime(){
		//System.out.println("Current: "+ new Integer((int) ((System.currentTimeMillis()%3600000)/60000)).toString()+":"+new Integer((int) (System.currentTimeMillis()%60000)));
		//System.out.println("T_start: "+ new Integer((int) ((T_start%3600000)/60000)).toString()+":"+new Integer((int) (T_start%60000)));
		//System.out.println("Duration: "+ duration);
		//int time = new BigDecimal(((T_start+duration-System.currentTimeMillis())/1000)).intValueExact();
		//long time = T_start+duration;
		//System.out.println("Time: "+ new Integer((int) ((time%3600000)/60000)).toString()+":"+new Integer((int) (time%60000)));
		return new BigDecimal(((T_start+duration-System.currentTimeMillis())/1000)).intValueExact();
	};
}
