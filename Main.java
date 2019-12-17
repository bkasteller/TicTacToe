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
			System.out.println("Entrez x : ");
			int x = in.nextInt();
			System.out.println("Entrez y : ");
			int y = in.nextInt();
			grille.setPoint(x, y, 1);
			grille.showGrille();
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
	
	public HashMap<String, Point> getGrille() {
		return grille;
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
	
	public void setPoint(int x, int y, int b)
	{
		grille.get("(" + x + ";" + y + ")").setValue(b);
	}
	
	public int getPoint(int x, int y)
	{
		return grille.get("(" + x + ";" + y + ")").getValue();
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
	public void play(Map grille)
	{
		grille.setPoint(1, 1, 2);
	}
}

class OneCase
{	
	private Map grille;
	private int score, x, y;
	
	public OneCase(Map grille, int x, int y) 
	{
		this.grille = grille;
		this.x = x;
		this.y = y;
		this.score = calculScore();
	}
	
	public int getScore()
	{
		return score;
	}
	
	private int calculScore()
	{
		int score = 0;
		boolean full_line = true;
		for (int x = 1; x <= 3 && full_line; x++)
			if (x != this.x)
				if ( grille.getPoint(x, this.y) == 1 )
					full_line = false;
		if(full_line){score++;}else{full_line = true;};
		for (int y = 1; y <= 3 && full_line; y++)
			if (y != this.y)
				if ( grille.getPoint(this.x, y) == 1 )
					full_line = false;
		if ( this.x == this.y || (grille.getSize() - this.x + 1) == this.y ) // nous somme sur une diagonale
		{
			if(full_line){score++;}else{full_line = true;};
			
			if(full_line){score++;}else{full_line = true;};
			
			if(full_line){score++;};
		}
		return score;
	}
}