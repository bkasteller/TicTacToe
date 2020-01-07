package HighLevel;

import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws CloneNotSupportedException 
	{
		final boolean detaille = /* on affiche les detailles (true/false) ? */ true;
		boolean kikiJoue = /* le joueur de type humanoïde commence (true/false) ? */ false;
		float tour = 0;
		Scanner sc = new Scanner(System.in);
		Grille grille = new Grille(9); // 3 ou 9.
		BestBot iRobot = new BestBot((Grille)grille.clone());
		while(!grille.fin())
		{
			if(tour % 1 == 0 && detaille)
			{
				System.out.println();
				System.out.println("Tour : " + (int)tour);
			}
			Coordonnee coordonnee;
			if(kikiJoue)
			{
				System.out.println();
				System.out.print("A vous, entrez x y : ");
				coordonnee = new Coordonnee(sc.nextInt() - 1, sc.nextInt() - 1);
				grille.jouer(coordonnee, 'X');
				iRobot.reflechir(coordonnee);
			}
			else
			{
				coordonnee = iRobot.getCoordonnee();
				//grille.jouer(coordonnee, 'O');
			}
			grille.afficher();
			System.out.println(kikiJoue ? "Player1 a joué " + coordonnee : "iRobot a joué " + coordonnee + (detaille ? " | ExecutionTime : " + iRobot.getTemps() + "ms" : ""));
			kikiJoue = !kikiJoue;
			tour-=-.5; // pour le style, car on aime être original.
		}
		
	}
}
