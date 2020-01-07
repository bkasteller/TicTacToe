package HighLevel;

public class BestBot {
	private Coordonnee coordonnee;
	private Grille grille;
	long temps;
	
	public BestBot(Grille copieGrille)
	{
		grille = copieGrille;
	}
	
	public Coordonnee getCoordonnee()
	{
		return coordonnee;
	}
	
	public void reflechir(Coordonnee coordonnee)
	{
		final long debut = System.nanoTime();
		
		temps = (System.nanoTime() - debut) / 1000000;
	}
	
	public long getTemps()
	{
		return temps;
	}
}
