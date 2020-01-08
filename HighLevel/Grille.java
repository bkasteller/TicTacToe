package HighLevel;

import java.util.HashMap;

public class Grille implements Cloneable {
	private HashMap<Coordonnee, Object> grille = new HashMap<Coordonnee, Object>();
	private int taille;
	
	public Grille(int taille)
	{
		this.taille = taille;
		remplir();
	}
	
	private void remplir()
	{
		for(int y = 0; y < getTaille(); y++)
		{
			for(int x = 0; x < getTaille(); x++)
			{
				if(getTaille() == 3)
				{
					Coordonnee coordonnee = new Coordonnee(x, y);
					grille.put(coordonnee, new Case(coordonnee, '.'));
				}
				else if(getTaille() == 9)
				{
					grille.put(new Coordonnee(x / 3, y / 3), new Grille(3));
				}
			}
		}
	}
	
	public int getTaille()
	{
		return taille;
	}
	
	public Object clone() throws CloneNotSupportedException 
	{ 
		return super.clone();
	} 
	
	public void afficher()
	{
		System.out.print("  ");
		for (int i = 0; i < getTaille(); i++)
		{
			System.out.print(i + " ");
			if(i < getTaille() - 1 && i % 3 == 2)
			{
				System.out.print("  ");
			}
		}
		System.out.println();
		for (int y = 0; y < getTaille(); y++)
		{
			for (int x = 0; x < getTaille(); x++)
			{
				if(x == 0)
				{
					System.out.print(y + " ");
				}
				if(getTaille() == 3)
				{
					Case c = (Case)grille.get(new Coordonnee(x, y));
					System.out.print(c.getContenu() + " ");
				}
				else if(getTaille() == 9)
				{
					Grille g = (Grille)grille.get(new Coordonnee(x / 3, y / 3));
					Case c = (Case)g.getGrille().get(new Coordonnee(x % 3, y % 3));
					System.out.print(c.getContenu() + " ");
					if(x < getTaille() - 1 && x % 3 == 2)
					{
						System.out.print("| ");
					}
				}
			}
			System.out.println();
			if(y < getTaille() - 1 && y % 3 == 2)
			{
				System.out.print(" -");
				for(int i = 0; i < getTaille()-1; i++)
				{
					System.out.print("--");
					if(i % 3 == 2)
					{
						System.out.print("+-");
					}
				}
				System.out.print("--");
				System.out.println();
			}
		}
		System.out.println();
	}
	
	public boolean fin()
	{
		return false;
	}
	
	public Grille jouer(Coordonnee coordonnee, char pion)
	{
		int x = coordonnee.getX(), 
			y = coordonnee.getY();
		if(getTaille() == 3)
		{
			Case c = (Case)grille.get(coordonnee);
			c.setContenu(pion);
		}
		else if(getTaille() == 9)
		{
			Grille g = (Grille)grille.get(new Coordonnee(x / 3, y / 3));
			Case c = (Case)g.getGrille().get(new Coordonnee(x % 3, y % 3));
			c.setContenu(pion);
		}
		return this;
	}
	
	public HashMap<Coordonnee, Object> getGrille()
	{
		return grille;
	}
}
