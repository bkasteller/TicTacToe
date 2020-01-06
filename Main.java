import java.util.*;
//import java.io.*;
//import java.math.*;

class Player
{
	public static void main(String args[]) 
	{
		Scanner in = new Scanner(System.in);
		Grille grille = new Grille();
		IA robot = new IA();
		while ( !grille.finPartie() ) {
			robot.jouer(grille);
			grille.afficher();
			System.out.print("Entrez x et y : ");
			int x = in.nextInt();
			int y = in.nextInt();
			grille.jouer(x-1, y-1, grille.joueur());
		}
	}
}

class Case
{
	private int x, y, val = 0;
	
	public Case(int x, int y)
	{
		this.x = x;
		this.y = y ;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getVal()
	{
		return val;
	}
	
	public void setVal(int val)
	{
		this.val = val;
	}
}

class Grille
{
	private int joueur = 1, robot = 2, taille = 3;
	private HashMap<String, Case> grille = new HashMap<String, Case>();
	
	public Grille()
	{
		remplir();
	}
	
	private void remplir()
	{
		for (int y = 0; y < taille; y++)
			for (int x = 0; x < taille; x++)
				grille.put("("+x+";"+y+")", new Case(x, y));
	}
	
	public Set<String> key()
	{
		return grille.keySet();
	}
	
	public int joueur()
	{
		return joueur;
	}
	
	public int robot()
	{
		return robot;
	}
	
	public int getTaille()
	{
		return taille;
	}
	
	public void afficher()
	{
		for (int y = 0; y < taille; y++)
		{
			for (int x = 0; x < taille; x++)
			{
				int val = grille.get("("+x+";"+y+")").getVal();
				System.out.print(val == 0 ? "." : val == joueur() ? "x" : "o");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void annuler(int x, int y)
	{
		grille.get("("+x+";"+y+")").setVal(0);
	}
	
	public void jouer(int x, int y, int player)
	{
		grille.get("("+x+";"+y+")").setVal(player);
	}
	
	public Case getCase(String key)
	{
		return grille.get(key);
	}
	
	public boolean finPartie()
	{
		for (int i = 0; i < getTaille(); i++)
			if ( verifLigne(i, 3) != 0 || verifCol(i, 3) != 0 )
				return true;
		if ( verifDiago1(3) != 0 || verifDiago2(3) != 0 )
			return true;
		for ( String i : key() )
			if ( getCase(i).getVal() == 0 )
				return false;
		return true;
	}
	
	public int verifLigne(int y, int n)
	{
		int robot = 0, joueur = 0;
		for (int x = 0; x < getTaille(); x++)
		{
			int val = getCase("("+x+";"+y+")").getVal();
			if ( val == joueur() )
				joueur++;
			else if ( val == robot() )
				robot++;
		}
		if ( robot == n )
			return robot();
		if ( joueur == n )
			return joueur();
		return 0;
	}
	
	public int verifCol(int x, int n)
	{
		int robot = 0, joueur = 0;
		for (int y = 0; y < getTaille(); y++)
		{
			int val = getCase("("+x+";"+y+")").getVal();
			if ( val == joueur() )
				joueur++;
			else if ( val == robot() )
				robot++;
		}
		if ( robot == n )
			return robot();
		if ( joueur == n )
			return joueur();
		return 0;
	}
	
	public int verifDiago1(int n)
	{
		int robot = 0, joueur = 0;
		for (int i = 0; i < getTaille(); i++)
		{
			int val = getCase("("+i+";"+i+")").getVal();
			if ( val == joueur() )
				joueur++;
			else if ( val == robot() )
				robot++;
		}
		if ( robot == n )
			return robot();
		if ( joueur == n )
			return joueur();
		return 0;
	}
	
	public int verifDiago2(int n)
	{
		int robot = 0, joueur = 0;
		for (int i = 0; i < getTaille(); i++)
		{
			int val = getCase("("+i+";"+(getTaille()-i-1)+")").getVal();
			if ( val == joueur() )
				joueur++;
			else if ( val == robot() )
				robot++;
		}
		if ( robot == n )
			return robot();
		if ( joueur == n )
			return joueur();
		return 0;
	}
}

class IA
{
	Grille grille;
	private int max_val = -1000, x, y, profondeur = 9;
	
	public void jouer(Grille grille)
	{
		this.grille = grille;
		reflechir();
		grille.jouer(x, y, grille.robot());
		//System.out.println(x+" "+y);
	}
	
	private void reflechir()
	{
		for ( String i : grille.key() )
		{
			if ( grille.getCase(i).getVal() == 0 )
			{
				int x = grille.getCase(i).getX(), y = grille.getCase(i).getY();
				grille.jouer(x, y, grille.robot());
				int val = min(profondeur);
				if ( val >= max_val)
				{
					max_val = val;
					this.x = x;
					this.y = y;
				}
				grille.annuler(x, y);
			}
		}
	}
	
	private int min(int profondeur)
	{
		if ( profondeur == 0 || grille.finPartie() || eval() == -1000 || eval() == 1000 )
			return eval();
		int min_val = 1000;
		for ( String i : grille.key() )
		{
			if ( grille.getCase(i).getVal() == 0 )
			{
				int x = grille.getCase(i).getX(), y = grille.getCase(i).getY();
				grille.jouer(x, y, grille.joueur());
				int val = max(profondeur-1);
				if ( val < min_val)
					min_val = val;
				grille.annuler(x, y);
			}
		}
		return min_val;
	}
	
	private int max(int profondeur)
	{
		if ( profondeur == 0 || grille.finPartie() || eval() == -1000 || eval() == 1000 )
			return eval();
		int max_val = -1000;
		for ( String i : grille.key() )
		{
			if ( grille.getCase(i).getVal() == 0 )
			{
				int x = grille.getCase(i).getX(), y = grille.getCase(i).getY();
				grille.jouer(x, y, grille.robot());
				int val = min(profondeur-1);	
				if ( val > max_val)
					max_val = val;	
				grille.annuler(x, y);
			}
		}
		return max_val;
	}
	
	private int eval()
	{
		int score = 0;
		
		for (int i = 0; i < grille.getTaille(); i++)
		{
			if ( grille.verifLigne(i, 2) == grille.robot() || grille.verifCol(i, 2) == grille.robot() )
				score += 2;
			else if ( grille.verifLigne(i, 2) == grille.joueur() || grille.verifCol(i, 2) == grille.joueur() )
				score -= 2;
			else
				score -= 1;
			
			if ( grille.verifLigne(i, 3) == grille.robot() || grille.verifCol(i, 3) == grille.robot() )
				return 1000;
			else if ( grille.verifLigne(i, 3) == grille.joueur() || grille.verifCol(i, 3) == grille.joueur() )
				return -1000;
		}
		
		if ( grille.verifDiago1(2) == grille.robot() || grille.verifDiago2(2) == grille.robot() )
			score += 2;
		else if ( grille.verifDiago1(2) == grille.joueur() || grille.verifDiago2(2) == grille.joueur() )
			score -= 2;
		else
			score -= 1;
		
		if ( grille.verifDiago1(3) == grille.robot() || grille.verifDiago2(3) == grille.robot() )
			return 1000;
		else if ( grille.verifDiago1(3) == grille.joueur() || grille.verifDiago2(3) == grille.joueur() )
			return -1000;

		return score;
	}
}
