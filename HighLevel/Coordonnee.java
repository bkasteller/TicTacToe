package HighLevel;

import java.util.Objects;

public class Coordonnee {
	private int x, y;
	
	public Coordonnee(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "; " + y + ")";
	}
	
	@Override
    public boolean equals(Object o) {
		if(o == this)
			return true;
		if (!(o instanceof Coordonnee))
            return false;
        Coordonnee coordonnee = (Coordonnee) o;
        return coordonnee.toString().equals(toString());
    }
	
	@Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}
