/**
 * Represents a text-based tile for a painted square puzzle.
 *
 * @author  Alexis Chuah
 * @version 28 Jan 2016
 *
 * <p>
 * Methods: 
 * 		1. Holds tile rotation and values of sides
 * 		2. Rotates tiles 
 * 		3. Returns true/false if tile sides match either top or left tile. 
 * </p>
 *
*/

import java.util.Scanner;

public class Tile
{
	/** Contains four values representing the values identifying sides of the squares. */
	protected int [] sides;

	/** Hold values 0 through 3 representing consecutive left turns of the tile.  */
	protected int orientation;


	/**
	 * Initializes four sides of the tile from individual values.
	 *
	 * @param top    value/color of the top of the tile
	 * @param right  value/color of the top of the tile
	 * @param bottom value/color of the top of the tile
	 * @param left   value/color of the top of the tile
	 */
	public Tile(int top, int right, int bottom, int left)
	{
		sides= new int[4];
		sides[0]= top;
		sides[1]= right;
		sides[2]= bottom;
		sides[3]= left;

		orientation= 0;
	}


	/**
	 * Initializes four sides of the tile from an array.
	 *
	 * @param defn array of four values corresponding to attribute "sides"
	 */
	public Tile(int [] defn)
	{
		this(defn[0],defn[1],defn[2],defn[3]);
	}


	/**
	 * Returns sides as top/right/bottom/left (orientation)"according to current orientation.
	 */
	public String toString()
	{
		String str= ""+sides[orientation];
		int i= (orientation+1)%4;
		str+= "/"+sides[i];
		i=(i+1)%4;
		str+= "/"+sides[i];
		i=(i+1)%4;
		str+= "/"+sides[i];

		return str + " ("+orientation+")";
	}


	/**
	 * Rotate tile 90 degrees to the left.
	 */
	public void turn()
	{
		orientation= (orientation+1)%4;
	}

	/**
	 * Return sides in order of current orientation.
	 *
	 * @return reference to re-oriented sides array
	 */
	public int [] getSides()
	{
		int i=orientation;
		int [] delta = new int[4];
		delta[0]=sides[i];
		i= (i+1)%4;
		delta[1]= sides[i];
		i= (i+1)%4;
		delta[2]= sides[i];
		i= (i+1)%4;
		delta[3]= sides[i];
		return delta;
	}


	/**
	 * Check if current tile's left face (in current orientation) matches the
	 * tileToLeft's right facing tile.
	 *
	 * @param tileToLeft tile to left of this one we are comparing with
	 * @return true if compared faces match; false otherwise
	 */
	public boolean matchLeft(Tile tileToLeft)
	{
		int [] currTile = this.getSides();
		int [] compare = tileToLeft.getSides();
	
		if(currTile[3] == compare[1])
			return true;
		return false;
	}

	/**
	 * Check if current tile's top face (in current orientation) matches the
	 * tileToTop's bottom facing tile.
	 *
	 * @param tileToTop tile to top of this one we are comparing with
	 * @return true if compared faces match; false otherwise
	 */
	public boolean matchTop(Tile tileToTop)
	{
		int [] currTile = this.getSides();
		int [] compare = tileToTop.getSides();

		if(currTile[0] == compare[2])
			return true;
		return false;
	}
}
