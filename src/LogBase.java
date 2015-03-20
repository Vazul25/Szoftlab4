import java.util.Scanner;

import major.Phoebe;

public class LogBase {
	private static Phoebe game;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("");
		System.out.println("==================================================================");
		System.out.println("|               Phoebe Szkeleton by Scrum_That!                  |");
		System.out.println("==================================================================");
		System.out.println("| 0. J�t�k be�ll�t�sa, J�t�km�d kiv�laszt�sa (�j J�t�k Ind�t�sa) |");
		System.out.println("|  0.1 Id� J�t�km�d                                              |");
		System.out.println("|  0.2 K�r J�t�km�d                                              |");
		System.out.println("==================================================================");
		
		int settings = 0;
		while(settings != 1 && settings != 2){
			System.out.print("              Adja meg a j�t�km�dot (1/2): ");
			settings = Integer.parseInt(sc.nextLine());
			System.out.println("");
		}
		
		Phoebe.Settings set;
		switch(settings){
			case 1: 
				set = new Phoebe.Settings(Phoebe.Settings.TIMELIMIT); 
				System.out.println("              A v�lasztott j�t�km�d: Id� J�t�km�d");
				break;
			default: 
				set = new Phoebe.Settings(Phoebe.Settings.LAPLIMIT); 
				System.out.println("              A v�lasztott j�t�km�d: K�r J�t�km�d");
				break;
		}
		
		System.out.flush();
		
		System.out.println("===================================================================");
		System.out.println("|                 Phoebe Szkeleton by Scrum_That!                 |");
		System.out.println("===================================================================");
		System.out.println("|	                          Init Game                           |");
		game = new Phoebe(set);
		game.run(sc);
		
		
	}

}
