/**
 * Represents a graphical tile for a painted square puzzle.
 *
 * @author  Terry Sergeant
 * @version Spring 2016
 *
 * <p>This tile representation inherits from JPanel to provide drawing
 * capabilities. We do not replicate the game-playing methods that
 * exist in a regular Tile object.</p>
*/

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GTile extends JPanel
{
	/** Contains four values representing the values identifying sides of the squares. */
	protected int [] sides;


	/**
	 * Initializes four sides of the tile from individual values.
	 *
	 * @param top    value/color of the top of the tile
	 * @param right  value/color of the top of the tile
	 * @param bottom value/color of the top of the tile
	 * @param left   value/color of the top of the tile
	 */
	public GTile(int top, int right, int bottom, int left)
	{
		sides= new int[4];
		setSides(top,right,bottom,left);
	}


	/**
	 * Initializes four sides of the tile from an array.
	 *
	 * @param defn array of four values corresponding to attribute "sides"
	 */
	public GTile(int [] defn)
	{
		this(defn[0],defn[1],defn[2],defn[3]);
	}


	/**
	 * Initializes four sides of the tile from individual values.
	 *
	 * @param top    value/color of the top of the tile
	 * @param right  value/color of the top of the tile
	 * @param bottom value/color of the top of the tile
	 * @param left   value/color of the top of the tile
	 */
	public void setSides(int top, int right, int bottom, int left)
	{
		sides[0]= top;
		sides[1]= right;
		sides[2]= bottom;
		sides[3]= left;
	}


	/**
	 * Initializes four sides of the tile from an array.
	 *
	 * @param defn array of four values corresponding to attribute "sides"
	 */
	public void setSides(int [] defn)
	{
		setSides(defn[0],defn[1],defn[2],defn[3]);
	}


	/**
	 * Draws 4 triangles with the color defined by sides/getColor().
	 *
	 * @param g reference to the Graphics object on which we draw
	 *
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		int [] xPos= {50,0,100};
		int [] yPos= {50,0,0};

		for (int i=0; i<4; i++) {
			g.setColor(getColor(sides[i]));
			g.fillPolygon(xPos,yPos,3);
			g.setColor(Color.BLACK);
			g.drawPolygon(xPos,yPos,3);
			yPos[1]= xPos[1];
			yPos[2]= xPos[2];
			xPos[1]= i==0 ? 100 : 0;
			xPos[2]= i==2 ? 0 : 100;
		}
	}


	/**
	 * Provides the color mapping from ints to Java Colors.
	 *
	 * @param val integer in the range 0 to 9
	 * @return Java Color value corresponding to val
	 */
	private Color getColor(int val)
	{
		switch (val) {
			case 0: return Color.LIGHT_GRAY;
			case 1: return Color.RED;
			case 2: return Color.PINK;
			case 3: return Color.ORANGE;
			case 4: return Color.YELLOW;
			case 5: return Color.GREEN;
			case 6: return Color.MAGENTA;
			case 7: return Color.CYAN;
			case 8: return Color.BLUE;
			case 9: return Color.DARK_GRAY;
			default: return Color.WHITE;
		}
	}
}
