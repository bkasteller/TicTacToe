package HighLevel;

public class BestBot {
	private int x, y;
	private Grille grille;
	
	public BestBot(Grille copieGrille)
	{
		grille = copieGrille;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void reflechir(int x, int y)
	{
		long debut = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis()-debut);
	}
}
