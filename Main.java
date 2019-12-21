import java.util.*;
//import java.io.*;
//import java.math.*;

class Player
{
	public static void main(String args[]) 
	{
		Scanner in = new Scanner(System.in);
		//Grille grille = new Grille();
		MegaGrille megaGrille = new MegaGrille();
		Grille grille;
		IA robot = new IA();
		while ( true/*!grille.FinPartie()*/ ) {
			robot.Jouer(megaGrille, "(4;4)");
			//grille.Afficher();
			megaGrille.Afficher();
			System.out.println("Entrez x : ");
			int x = in.nextInt();
			System.out.println("Entrez y : ");
			int y = in.nextInt();
			grille = megaGrille.GetGrille(x-1, y-1);
			grille.Jouer(x-1, y-1, grille.Joueur());
			grille = megaGrille.GetGrille("("+(x/3)+";"+(y/3)+")");
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
	
	public int GetX()
	{
		return x;
	}
	
	public int GetY()
	{
		return y;
	}
	
	public int GetVal()
	{
		return val;
	}
	
	public void SetVal(int val)
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
		Remplir();
	}
	
	private void Remplir()
	{
		for (int y = 0; y < taille; y++)
			for (int x = 0; x < taille; x++)
				grille.put("("+x+";"+y+")", new Case(x, y));
	}
	
	public Set<String> Key()
	{
		return grille.keySet();
	}
	
	public int Joueur()
	{
		return joueur;
	}
	
	public int Robot()
	{
		return robot;
	}
	
	public int GetTaille()
	{
		return taille;
	}
	
	public void Afficher()
	{
		for (int y = 0; y < taille; y++)
		{
			for (int x = 0; x < taille; x++)
			{
				int val = grille.get("("+x+";"+y+")").GetVal();
				System.out.print(val == 0 ? "." : val == Joueur() ? "x" : "o");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void Afficher(int x, int y)
	{
		int val = grille.get("("+x+";"+y+")").GetVal();
		System.out.print(val == 0 ? "." : val == Joueur() ? "x" : "o");
	}
	
	public void Annuler(int x, int y)
	{
		grille.get("("+x+";"+y+")").SetVal(0);
	}
	
	public void Jouer(int x, int y, int player)
	{
		grille.get("("+x+";"+y+")").SetVal(player);
	}
	
	public Case GetCase(String key)
	{
		return grille.get(key);
	}
	
	public boolean FinPartie()
	{
		for (int i = 0; i < GetTaille(); i++)
			if ( VerifLigne(i, 3) != 0 || VerifCol(i, 3) != 0 )
				return true;
		if ( VerifDiago1(3) != 0 || VerifDiago2(3) != 0 )
			return true;
		for ( String i : Key() )
			if ( GetCase(i).GetVal() == 0 )
				return false;
		return true;
	}
	
	public int VerifLigne(int y, int n)
	{
		int robot = 0, joueur = 0;
		for (int x = 0; x < GetTaille(); x++)
		{
			int val = GetCase("("+x+";"+y+")").GetVal();
			if ( val == Joueur() )
				joueur++;
			else if ( val == Robot() )
				robot++;
		}
		if ( robot == n )
			return Robot();
		if ( joueur == n )
			return Joueur();
		return 0;
	}
	
	public int VerifCol(int x, int n)
	{
		int robot = 0, joueur = 0;
		for (int y = 0; y < GetTaille(); y++)
		{
			int val = GetCase("("+x+";"+y+")").GetVal();
			if ( val == Joueur() )
				joueur++;
			else if ( val == Robot() )
				robot++;
		}
		if ( robot == n )
			return Robot();
		if ( joueur == n )
			return Joueur();
		return 0;
	}
	
	public int VerifDiago1(int n)
	{
		int robot = 0, joueur = 0;
		for (int i = 0; i < GetTaille(); i++)
		{
			int val = GetCase("("+i+";"+i+")").GetVal();
			if ( val == Joueur() )
				joueur++;
			else if ( val == Robot() )
				robot++;
		}
		if ( robot == n )
			return Robot();
		if ( joueur == n )
			return Joueur();
		return 0;
	}
	
	public int VerifDiago2(int n)
	{
		int robot = 0, joueur = 0;
		for (int i = 0; i < GetTaille(); i++)
		{
			int val = GetCase("("+i+";"+(GetTaille()-i-1)+")").GetVal();
			if ( val == Joueur() )
				joueur++;
			else if ( val == Robot() )
				robot++;
		}
		if ( robot == n )
			return Robot();
		if ( joueur == n )
			return Joueur();
		return 0;
	}
}

class MegaGrille 
{
	private int taille = 3;
	private String key;
	private HashMap<String, Grille> megaGrille = new HashMap<String, Grille>();
	
	public MegaGrille()
	{
		Remplir();
	}
	
	private void Remplir()
	{
		for (int y = 0; y < taille; y++)
			for (int x = 0; x < taille; x++)
				megaGrille.put("("+x+";"+y+")", new Grille());
	}
	
	public void Afficher()
	{
		for (int y = 0; y < taille*taille; y++)
		{
			for (int x = 0; x < taille*taille; x++)
			{
				GetGrille(x, y).Afficher(x%3, y%3);
			}
			System.out.println();
		}
	}
	
	public Set<String> Key()
	{
		return megaGrille.keySet();
	}
	
	public Grille GetGrille(int x, int y)
	{
		this.key = "("+x/taille+";"+y/taille+")";
		return megaGrille.get("("+x/taille+";"+y/taille+")");
	}
	
	public Grille GetGrille(String key)
	{
		return megaGrille.get(key);
	}
	
	public int GetX()
	{
		return 1;
	}
	
	public int GetY()
	{
		return 1;
	}
}

class IA
{
	Grille grille;
	private int max_val = -1000, x, y, profondeur = 3/*9*/;
	
	public void Jouer(Grille grille)
	{
		this.grille = grille;
		Reflechir();
		grille.Jouer(x, y, grille.Robot());
		System.out.println(x+" "+y);
	}
	
	public void Jouer(MegaGrille megaGrille, String key)
	{
		this.grille = megaGrille.GetGrille(key);
		Reflechir();
		grille.Jouer(x, y, grille.Robot());
		System.out.println((megaGrille.GetX()*3+x)+" "+(megaGrille.GetY()*3+y));
	}
	
	private void Reflechir()
	{
		for ( String i : grille.Key() )
		{
			if ( grille.GetCase(i).GetVal() == 0 )
			{
				int x = grille.GetCase(i).GetX(), y = grille.GetCase(i).GetY();
				grille.Jouer(x, y, grille.Robot());
				int val = Min(profondeur);
				if ( val >= max_val)
				{
					max_val = val;
					this.x = x;
					this.y = y;
				}
				grille.Annuler(x, y);
			}
		}
	}
	
	private int Min(int profondeur)
	{
		if ( profondeur == 0 || grille.FinPartie() )
			return Eval();
		int min_val = 1000;
		for ( String i : grille.Key() )
		{
			if ( grille.GetCase(i).GetVal() == 0 )
			{
				int x = grille.GetCase(i).GetX(), y = grille.GetCase(i).GetY();
				grille.Jouer(x, y, grille.Joueur());
				int val = Max(profondeur-1);
				if ( val < min_val)
					min_val = val;
				grille.Annuler(x, y);
			}
		}
		return min_val;
	}
	
	private int Max(int profondeur)
	{
		if ( profondeur == 0 || grille.FinPartie() )
			return Eval();
		int max_val = -1000;
		for ( String i : grille.Key() )
		{
			if ( grille.GetCase(i).GetVal() == 0 )
			{
				int x = grille.GetCase(i).GetX(), y = grille.GetCase(i).GetY();
				grille.Jouer(x, y, grille.Robot());
				int val = Min(profondeur-1);	
				if ( val > max_val)
					max_val = val;	
				grille.Annuler(x, y);
			}
		}
		return max_val;
	}
	
	private int Eval()
	{
		int score = 0;
		
		for (int i = 0; i < grille.GetTaille(); i++)
		{
			if ( grille.VerifLigne(i, 2) == grille.Robot() )
				score += 2;
			else if ( grille.VerifLigne(i, 2) == grille.Joueur() )
				score -= 2;
			else
				score -= 1;
			if ( grille.VerifLigne(i, 3) == grille.Robot() )
				score += 10000;
			else if ( grille.VerifLigne(i, 3) == grille.Joueur() )
				score -= 1000;
			if ( grille.VerifCol(i, 2) == grille.Robot() )
				score += 2;
			else if ( grille.VerifCol(i, 2) == grille.Joueur() )
				score -= 2;
			else
				score -= 1;
			if ( grille.VerifCol(i, 3) == grille.Robot() )
				score += 10000;
			else if ( grille.VerifCol(i, 3) == grille.Joueur() )
				score -= 1000;
		}
		if ( grille.VerifDiago1(2) == grille.Robot() )
			score += 2;
		else if ( grille.VerifDiago1(2) == grille.Joueur() )
			score -= 2;
		else
			score -= 1;
		if ( grille.VerifDiago1(3) == grille.Robot() )
			score += 10000;
		else if ( grille.VerifDiago1(3) == grille.Joueur() )
			score -= 1000;
		if ( grille.VerifDiago2(2) == grille.Robot() )
			score += 2;
		else if ( grille.VerifDiago2(2) == grille.Joueur() )
			score -= 2;
		else
			score -= 1;
		if ( grille.VerifDiago2(3) == grille.Robot() )
			score += 10000;
		else if ( grille.VerifDiago2(3) == grille.Joueur() )
			score -= 1000;
		return score;
	}
}
