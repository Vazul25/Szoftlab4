import java.util.Scanner;

import major.Phoebe;

public class LogBase {
	private static Phoebe game;
	
	public static void consoleErase(){
		for(int i=1;i<=50;i++) System.out.println();
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("");
		System.out.println("==================================================================");
		System.out.println("|               Phoebe Szkeleton by Scrum_That!                  |");
		System.out.println("==================================================================");
		System.out.println("| 0. Játék beállítása, Játékmód kiválasztása (Új Játék Indítása) |");
		System.out.println("|  0.1 Idő Játékmód                                              |");
		System.out.println("|  0.2 Kör Játékmód                                              |");
		System.out.println("==================================================================");
		
		int settings = 0;
		while(settings != 1 && settings != 2){
			System.out.print("              Adja meg a játékmódot (1/2): ");
			settings = Integer.parseInt(sc.nextLine());
			System.out.println("");
		}
		
		Phoebe.Settings set;
		switch(settings){
			case 1: 
				set = new Phoebe.Settings(Phoebe.Settings.TIMELIMIT); 
				System.out.println("              A választott játékmód: Idő Játékmód");
				break;
			default: 
				set = new Phoebe.Settings(Phoebe.Settings.LAPLIMIT); 
				System.out.println("              A választott játékmód: Kör Játékmód");
				break;
		}
		
		System.out.flush();
		
		System.out.println("===================================================================");
		System.out.println("|                 Phoebe Szkeleton by Scrum_That!                 |");
		System.out.println("===================================================================");
		System.out.println("|	                          Init Game                           |");
		game = new Phoebe(set);
		game.run();
		
		
	}

}
