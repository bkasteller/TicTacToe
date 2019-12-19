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
		while ( !grille.FinJeu() ) {
			System.out.println("Entrez x : ");
			int x = in.nextInt();
			System.out.println("Entrez y : ");
			int y = in.nextInt();
			if ( x != -1 && y != -1 )
			{
				grille.setPoint(x, y, 2);
				grille.showGrille();
			}
			ia.play(grille);
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
	
	public boolean FinJeu()
	{
		// Vérifie qu'il n'y a pas de suite de 3
		for (int i = 1; i <= getSize(); i++)
		{
			if ( CalcRow(i) || CalcCol(i) )
				return true;
		}
		if ( CalcDiago1() || CalcDiago2() )
			return true;
		
		// Vérifie si il reste des cases vides
		for ( String i : AllKey() )
			if ( getPoint(i).getValue() == 0 )
				return false;
		return true;
	}
	
	private boolean CalcRow(int numLigne)
	{
		int ai = 0, joueur = 0;
		for (int x = 1; x <= getSize(); x++)
		{
			int value = getPoint("(" + x + ";" + numLigne + ")").getValue();
			if ( value == 1 )
				ai++;
			else if ( value == 2 )
				joueur++;
		}
		return Result(ai, joueur);
	}
	
	private boolean CalcCol(int numCol)
	{
		int ai = 0, joueur = 0;
		for (int y = 1; y <= getSize(); y++)
		{
			int value = getPoint("(" + numCol + ";" + y + ")").getValue();
			if ( value == 1 )
				ai++;
			else if ( value == 2 )
				joueur++;
		}
		return Result(ai, joueur);
	}
	
	private boolean CalcDiago1()
	{
		int ai = 0, joueur = 0;
		for (int xy = 1; xy <= getSize(); xy++)
		{
			int value = getPoint("(" + xy + ";" + xy + ")").getValue();
			if ( value == 1 )
				ai++;
			else if ( value == 2 )
				joueur++;
		}
		return Result(ai, joueur);
	}
	
	private boolean CalcDiago2()
	{
		int ai = 0, joueur = 0;
		for (int xy = getSize(); xy > 0; xy--)
		{
			int value = getPoint("(" + xy + ";" + (getSize() - xy + 1) + ")").getValue();
			if ( value == 1 )
				ai++;
			else if ( value == 2 )
				joueur++;
		}
		return Result(ai, joueur);
	}
	
	private boolean Result(int ai, int joueur)
	{
		if ( ai == 3 )
			return true;
		if ( joueur == 3 )
			return true;				
		return false;
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
				
				int val = Min(profondeur);
				
				if ( val >= max_val)
				{
					max_val = val;
					this.x = x;
					this.y = y;
				}
				
				grille.resetPoint(x, y);
			}
		}
		
		
		grille.setPoint(this.x, this.y, 1);
	}
	
	private int Min(int profondeur)
	{
		if ( profondeur == 0 || grille.FinJeu() )
			return Eval();
		
		int min_val = 1000;
		
		// Pour tout les coups possibles
		for ( String i : grille.AllKey() )
		{
			if ( grille.getPoint(i).getValue() == 0 )
			{
				int x = grille.getPoint(i).getX(), y = grille.getPoint(i).getY();
				grille.setPoint(x, y, 2);
				
				int val = Max(profondeur-1);
				
				if ( val < min_val)
				{
					min_val = val;
				}
				
				grille.resetPoint(x, y);
			}
		}
		
		return min_val;
	}
	
	private int Max(int profondeur)
	{
		if ( profondeur == 0 || grille.FinJeu() )
			return Eval();
		
		int max_val = -1000;
		
		// Pour tout les coups possibles
		for ( String i : grille.AllKey() )
		{
			if ( grille.getPoint(i).getValue() == 0 )
			{
				int x = grille.getPoint(i).getX(), y = grille.getPoint(i).getY();
				grille.setPoint(x, y, 1);
				
				int val = Min(profondeur-1);
				
				if ( val > max_val)
				{
					max_val = val;
				}
				
				grille.resetPoint(x, y);
			}
		}
		
		return max_val;
	}
	
	private int Eval()
	{
		int score = 0;
		
		for (int i = 1; i <= grille.getSize(); i++)
		{
			score += CalcRow(i);
			score += CalcCol(i);
		}
		score += CalcDiago1();
		score += CalcDiago2();
		
		if ( grille.FinJeu() && score > -1000 && score < 1000 )
			return 0;

		return score;
	}
	
	private int CalcRow(int numLigne)
	{
		int ai = 0, joueur = 0;
		for (int x = 1; x <= grille.getSize(); x++)
		{
			int value = grille.getPoint("(" + x + ";" + numLigne + ")").getValue();
			if ( value == 1 )
				ai++;
			else if ( value == 2 )
				joueur++;
		}
		return Worth(ai, joueur);
	}
	
	private int CalcCol(int numCol)
	{
		int ai = 0, joueur = 0;
		for (int y = 1; y <= grille.getSize(); y++)
		{
			int value = grille.getPoint("(" + numCol + ";" + y + ")").getValue();
			if ( value == 1 )
				ai++;
			else if ( value == 2 )
				joueur++;
		}
		return Worth(ai, joueur);
	}
	
	private int CalcDiago1()
	{
		int ai = 0, joueur = 0;
		for (int xy = 1; xy <= grille.getSize(); xy++)
		{
			int value = grille.getPoint("(" + xy + ";" + xy + ")").getValue();
			if ( value == 1 )
				ai++;
			else if ( value == 2 )
				joueur++;
		}
		return Worth(ai, joueur);
	}
	
	private int CalcDiago2()
	{
		int ai = 0, joueur = 0;
		for (int xy = grille.getSize(); xy > 0; xy--)
		{
			int value = grille.getPoint("(" + xy + ";" + (grille.getSize() - xy + 1) + ")").getValue();
			if ( value == 1 )
				ai++;
			else if ( value == 2 )
				joueur++;
		}
		return Worth(ai, joueur);
	}
	
	private int Worth(int ai, int joueur)
	{
		if ( ai == 3 )
			return 1000;
		if ( joueur == 3 )
			return -1000;
		if ( ai == 2 && joueur == 0 )
			return 1;
		if ( joueur == 2 && ai == 0 )
			return -1;
		return 0;
	}
}