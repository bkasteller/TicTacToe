package HighLevel;

public class Case {
	private Coordonnee coordonnee;
	private Object contenu;
	
	public Case(Grille grille)
	{
		contenu = grille;
	}
	
	public Case(Coordonnee coordonnee, char pion)
	{
		this.coordonnee = coordonnee;
		contenu = pion;
	}
	
	public Coordonnee getCoordonnee()
	{
		return coordonnee;
	}
	
	public Object getContenu()
	{
		return contenu;
	}
}
