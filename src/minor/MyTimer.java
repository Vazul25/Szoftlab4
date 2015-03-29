package minor;

import java.math.BigDecimal;

/*
 * Timer osztály
 * Felelősség:
 * 
 * A játék elején a kezdésig visszaszámol. Játéktípustól függően felfelé 
 * vagy visszafelé számol. Ez az osztály felelős, hogy ha lejárt az idő, 
 * akkor legyen vége a játéknak.
 * 
 * Ősosztály:
 * 
 * Interfészek:
 *  
 */
public class MyTimer {
	
	private long T_start;
	private int duration;
	
	private enum DIR{
		Up,
		Down
	}
	
	private DIR direction;
	
	/*
	 * Timer konstruktor
	 * ha 0-val vagy negatívval inicializálják felfele számol
	 * ha pozitív számmal inicializálják akkor lefelé számol.
	 */
	public MyTimer(int i){
		if(i > 0){
			direction = DIR.Down;
			duration = i*1000;
			T_start = System.currentTimeMillis()-2*i*1000;
		}else{
			direction = DIR.Up;
			duration = 0;
		}
	}
	
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
	
	public boolean isZero(){
		switch(direction){
		case Down:
			return ( T_start + duration <= System.currentTimeMillis());	
		default:
			return false;
		}
			
	}
	
	public int getTime(){
		return new BigDecimal(((System.currentTimeMillis()-T_start)/1000)).intValueExact();
	};
}
