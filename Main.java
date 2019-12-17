import java.util.*;
import java.io.*;
import java.math.*;

class Player
{
	public static void main(String args[]) 
	{
		Map grille = new Map(3);
		grille.GetAllKey();
	}
}

class Map 
{
	private HashMap<Point, Boolean> grille = new HashMap<Point, Boolean>();
	
	public Map(int taille) 
	{
		for (int j = 1; j <= taille; j++)
		{
			for (int i = 1; i <= taille; i++)
			{
				grille.put(new Point(i,j), null);
			}
		}
	}
	
	public void GetAllKey()
	{
		Set<Point> keys = grille.keySet();
		for (Point i : keys)
		{
			System.out.println(i.toString() + " : " + grille.get(i));
		}
	}
}

class Point 
{
	private int x, y;
	
	public Point(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public int GetX() 
	{
		return x;
	}
	
	public int GetY()
	{
		return y;
	}
	
	@Override
    public String toString() { 
        return "(" + GetX() + ";" + GetY() + ")"; 
    } 
}