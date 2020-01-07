package HighLevel;

import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws CloneNotSupportedException 
	{
		float tour = 0; // on compte les tours, j'suis une formul1 !
		boolean kikiJoue = /* le joueur de type humanoïde commence (true/false) ? */ false;
		Scanner in = new Scanner(System.in);
		Grille grille = new Grille(3); // 3 ou 9.
		BestBot iRobot = new BestBot((Grille)grille.clone());
		while(!grille.fin())
		{
			int x, y;
			grille.afficher();
			if(kikiJoue)
			{
				System.out.print("Entrez x et y (ex: 1 1): ");
				x = in.nextInt();
				y = in.nextInt();
				grille.jouer(x, y, 'X');
				iRobot.reflechir(x, y);
			} else
			{
				// on récupère le meilleur coup calculé, pas bête pour un tas de féraille !
				x = iRobot.getX();
				y = iRobot.getY();
				grille.jouer(x, y, 'O');
			}
			kikiJoue = !kikiJoue;
			tour-=-.5; // pour le style, car on aime être original.
		}
	}
}
