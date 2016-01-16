/**
 * Represents a text-based board for holding painted-squares tiles.
 *
 * @author  Terry Sergeant
 * @version Spring 2016
 *
*/

import java.util.Scanner;
import java.io.File;

public class Board
{
	/** The tiles available for placement. */
	protected Tile [] tiles;

	/** The number of rows on the board.  */
	protected int numRows;

	/** The number of columns on the board.  */
	protected int numCols;

	/** The grid (numRows x numCols) on which tiles are placed. */
	protected Tile [][] grid;


	/**
	 * Read board and tile definitions from "filename".
	 *
	 * @param filename name/path of a data file containing board/tile layout
	 *
	 * <p>Line 1 has 2 ints separated by a space: numRows NumCols</p>
	 *
	 * <p>Remaining lines have one tile per line; 4 ints separated by space where
	 * the numbers are color/pattern id values: top right bottom left</p>
	 *
	 * <p>NOTE: number of tiles will be numRows*numCols</p>
	 */
	public Board(String filename)
	{
		int n,i,j;

		try
		{
			Scanner f= new Scanner(new File(filename));
			numRows= f.nextInt();
			numCols= f.nextInt();
			n= numRows*numCols;
			tiles= new Tile[n];

			for (i=0; i<n; i++)
				tiles[i]= new Tile(f.nextInt(),f.nextInt(),f.nextInt(),f.nextInt());

			f.close();

			grid= new Tile[numRows][numCols];
			for (i=0; i<numRows; i++)
				for (j=0; j<numCols; j++)
					grid[i][j]= tiles[i*numCols+j];
		}
		catch (Exception e)
		{
			System.err.println(e);
			System.exit(-1);
		}
	}


	/**
	 * Displays a grid with the squares in them.
	 */
	public void display()
	{
		int i,j;

		for (i=0; i<numRows; i++)
		{
			for (j=0; j<numCols; j++)
				System.out.printf("%-14s",grid[i][j]);
			System.out.println();
		}
	}

}
