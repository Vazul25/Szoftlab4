package major;

import java.awt.Graphics;
/*
 * IVisible interfész
 * 
 * Felelősség: A grafikus motorhoz szükséges interfész. Olyan 
 * osztályok, melyek kirajzolható elemeket tartalmaznak 
 * megvalósítják ezt az interfészt.
 */
public interface iVisible {
	
	/** 
	 * paint metódus
	 * 
	 * Felelősség: Rajzolást elvégző metódus.
	 *
	 * @param g Grafikus rajzoló osztály.
	 */
	public abstract void paint(Graphics g) ;
}
