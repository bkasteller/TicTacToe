import java.util.*;
import java.io.*;
import java.math.*;

class Player
{
	public static void main(String args[]) 
	{
		Scanner in = new Scanner(System.in);
		Map grille = new Map(3);
		IA ia = new IA();
		grille.showGrille();
		while (true) {
			ia.play(grille);
			grille.showGrille();
			System.out.println("Entrez x : ");
			int x = in.nextInt();
			System.out.println("Entrez y : ");
			int y = in.nextInt();
			grille.setPoint(x, y, 2);
			grille.showGrille();
		}
	}
}

class Map 
{
	int taille;
	private HashMap<String, Point> grille = new HashMap<String, Point>();
	
	public Map(int taille) 
	{
		this.taille = taille;
		fileGrille();
	}
	
	public Set<String> AllKey()
	{
		return grille.keySet();
	}
	
	private void fileGrille()
	{
		for (int j = 1; j <= taille; j++)
		{
			for (int i = 1; i <= taille; i++)
			{
				Point p = new Point(i, j);
				grille.put(p.myToString(), p);
			}
		}
	}
	
	public int getSize()
	{
		return taille;
	}
	
	public void showGrille()
	{
		for (int j = 1; j <= taille; j++)
		{
			for (int i = 1; i <= taille; i++)
			{
				System.out.print(grille.get("(" + i + ";" + j + ")").getValue() == 0 ? "." : grille.get("(" + i + ";" + j + ")").getValue() == 1 ? "x" : "o");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void resetPoint(int x, int y)
	{
		grille.get("(" + x + ";" + y + ")").setValue(0);
	}
	
	public void setPoint(int x, int y, int b)
	{
		grille.get("(" + x + ";" + y + ")").setValue(b);
	}
	
	public Point getPoint(String key)
	{
		return grille.get(key);
	}
}

class Point 
{
	private int x, y, value = 0;
	
	public Point(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX() 
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
    public String myToString() { 
        return "(" + getX() + ";" + getY() + ")"; 
    } 
}

class IA
{
	private int max_val = -1000, x, y, profondeur = 9;
	private Map grille;
	
	public void play(Map grille)
	{
		this.grille = grille;
		
		// Pour tout les coups possibles
		for ( String i : grille.AllKey() )
		{
			if ( grille.getPoint(i).getValue() == 0 )
			{
				int x = grille.getPoint(i).getX(), y = grille.getPoint(i).getY();
				grille.setPoint(x, y, 1);
				
				int val = Min(etat_du_jeu, profondeur);
				
				if ( val > max_val)
				{
					max_val = val;
					this.x = x;
					this.y = y;
				}
				
				grille.resetPoint(x, y);
			}
		}
		
		
		grille.setPoint(x, y, 1);
	}
	
	private int Min(int etat_du_jeu, int profondeur)
	{
		if ( profondeur == 0 || FinJeu() )
			return Eval(etat_du_jeu);
		
		int min_val = 1000;
		
		// Pour tout les coups possibles
		for ( String i : grille.AllKey() )
		{
			if ( grille.getPoint(i).getValue() == 0 )
			{
				int x = grille.getPoint(i).getX(), y = grille.getPoint(i).getY();
				grille.setPoint(x, y, 1);
				
				int val = Max(etat_du_jeu, profondeur-1);
				
				if ( val < min_val)
				{
					min_val = val;
				}
				
				grille.resetPoint(x, y);
			}
		}
		
		return min_val;
	}
	
	private int Max(int etat_du_jeu, int profondeur)
	{
		if ( profondeur == 0 || FinJeu() )
			return Eval(etat_du_jeu);
		
		int max_val = -1000;
		
		// Pour tout les coups possibles
		for ( String i : grille.AllKey() )
		{
			if ( grille.getPoint(i).getValue() == 0 )
			{
				int x = grille.getPoint(i).getX(), y = grille.getPoint(i).getY();
				grille.setPoint(x, y, 1);
				
				int val = Min(etat_du_jeu, profondeur-1);
				
				if ( val > max_val)
				{
					max_val = val;
				}
				
				grille.resetPoint(x, y);
			}
		}
		
		return max_val;
	}
	
	private boolean FinJeu()
	{
		
		return false;
	}
	
	private int Eval(int etat_du_jeu)
	{
		int score = 0;
		// si on a été appelé car la partie est finie 
		//if ( FinJeu() ) 
		
		//else
		//{
			for (int i = 1; i <= grille.getSize(); i++)
			{
				// lignes
				for (int x = 1; x <= grille.getSize(); x++)
				{
					if ( grille.getPoint("(" + x + ";" + i + ")").getValue() == 1 )
				}
				// colonnes
				for (int y = 1; y <= grille.getSize(); y++)
				{
					if ( grille.getPoint("(" + i + ";" + y + ")").getValue() == 1 )
				}
			}
			// 1ere diago
			for (int xy = 1; xy <= grille.getSize(); xy++)
			{
				if ( grille.getPoint(xy, xy) == 1 )
			}
			// 2nd diago
			for (int xy = grille.getSize(); xy > 0; xy--)
			{
				if ( grille.getPoint(x, (taille - x + 1)) == 1 )
			}
		//}
		return score;
	}
}