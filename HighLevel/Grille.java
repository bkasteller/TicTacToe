package HighLevel;

import java.util.HashMap;

public class Grille implements Cloneable {
	private HashMap<Coordonnee, Case> grille = new HashMap<Coordonnee, Case>();
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
				Coordonnee coordonnee = new Coordonnee(x, y);
				if(getTaille() == 3)
				{
					grille.put(coordonnee, new Case(coordonnee, '.'));
				}else if(getTaille() == 9)
				{
					
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
		for (int y = 0; y < taille; y++)
		{
			for (int x = 0; x < taille; x++)
			{
				System.out.print(grille.get(new Coordonnee(x, y)).getContenu());
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public boolean fin()
	{
		return false;
	}
	
	public Grille jouer(int x, int y, char pion)
	{
		return this;
	}
}
