import java.util.*;
import java.io.*;
import java.math.*;

class Player
{
	public static void main(String args[]) 
	{
		Scanner in = new Scanner(System.in);
		Grille grille = new Grille();
		IA robot = new IA();
		while ( !grille.FinPartie() ) {
			grille.AfficherGrille();
			robot.Jouer(grille);
			System.out.println("Entrez x : ");
			int x = in.nextInt();
			System.out.println("Entrez y : ");
			int y = in.nextInt();
			grille.Jouer(x, y, grille.Joueur());
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
	
	public void AfficherGrille()
	{
		for (int y = 0; y < taille; y++)
		{
			for (int x = 0; x < taille; x++)
			{
				int val = grille.get("("+x+";"+y+")").GetVal();
				System.out.print(val == 0 ? "." : val == Joueur() ? "j" : "r");
			}
			System.out.println();
		}
		System.out.println();
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
			if ( VerifLigne(i) || VerifCol(i) )
				return true;
		if ( VerifDiago1() || VerifDiago2() )
			return true;
		
		for ( String i : Key() )
			if ( GetCase(i).GetVal() == 0 )
				return false;
		
		return true;
	}
	
	public boolean VerifLigne(int y)
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
		return robot == 3 || joueur == 3;
	}
	
	public boolean VerifCol(int x)
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
		return robot == 3 || joueur == 3;
	}
	
	
}

class IA
{
	Grille grille;
	public void Jouer(Grille grille)
	{
		this.grille = grille;
		
	}
}