package minor;

import java.io.IOException;

import major.Phoebe;

public class mainteszt {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		GUI g=new GUI();
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
		g.game = new Phoebe(set);
		Thread t = new Thread(g.game);
		t.start();
		//...
		
	}

}
