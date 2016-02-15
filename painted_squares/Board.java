/**
 * Represents a text-based board for holding painted-squares tiles.
 *
 * @author  Alexis Chuah
 * @version 28 Jan 2016 
 *
 * <p>
 * Finds and displays all solutions to the painted-squares tiles. 
 * </p>
 *
*/

import java.util.Scanner;
import java.io.File;
import java.util.*;

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

	/** Flag solution if found */
	protected boolean found;

	/** Track if any solutions were found */
	protected boolean solutionsFound;

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
	 * Displays a grid with the tiles in ASCII format based on current
	 * rotation 
	 */
	public void display()
	{
		int i,j,k;
		String format;

		//header row
		for(j=0; j<numCols; j++)
			System.out.print(" _ _ _ _ ");

		System.out.println();

		//i=tile row, k=line on tile
		for (i=0; i<numRows; i++){
			for (k=0; k<3; k++)
			{
				for(j=0; j<numCols; j++){

					int[]sides = grid[i][j].getSides();

					switch(k){
						case 0: format = "| \\ " + sides[0] + " / |";
										break;
						case 1: format = "|" + sides[3] + "  X  " + sides[1] + "|";
										break;
						case 2: format = "|_/_" + sides[2] + "_\\_|";
										break;
						default:format = ""; 
										break;
					}
					System.out.print(format);
				}
				System.out.println();
			}
		}
		System.out.println();

		//Single line format
		/*	
		for(i=0; i<numRows; i++)
			for(j=0; j<numCols; j++)
				System.out.printf("%-14s",grid[i][j]);	
		*/
	}

	/**
	 * Solve board with queue
	 */
	public void solve(){
		
		int n = numRows * numCols;

		Queue<Tile>available = new LinkedList<Tile>(); 
		for(int i=0; i<n; i++)
			available.add(tiles[i]);

		found = false;
		solutionsFound = false;

		backtrack(available,0);

		if(!solutionsFound){
			String dash = "---------------------------------------";
			System.out.println("\n" + dash + "\n  No solutions found for this puzzle.  \n" + dash + "\n");
		}
	}

	/**
	 * Recursion called to solve board
	 * @param available			Tiles in the queue that have not been placed.
	 * @param pos						position of tile on grid.
	 */
	public void backtrack(Queue<Tile> available, int pos){
		
		Tile curr;
		int i, j, k, n;
		
		i=pos/numCols;
		j=pos%numCols;

		if(available.isEmpty() && found){
			display();
			solutionsFound = true;
			found = false;
		}
		else{
			for (n=0; n<available.size(); n++){
				curr=available.remove();
				//add method to check if sides have possible number in sequence					
				for(k=0; k<4; k++){
					curr.orientation = k;
					
					if(tileFits(curr, pos)){
						grid[i][j]=curr;
						if(pos==n)
							found=true;
						backtrack(available, pos+1);
					}
				}
				available.add(curr);
			}
		}
	}
	
	/**
	 * Check if the tile fits in the current position. 
	 * @param current	current tile to check.
	 * @param pos			position where tile will be placed if it fits.
	 */
	public boolean tileFits(Tile current, int pos){
		int i, j;

		i=pos/numCols;
		j=pos%numCols;

		if(i==0 && j==0)
			return true;
		else if(i==0 && j!=0)
			return current.matchLeft(grid[i][j-1]);
		else if(j==0)
			return current.matchTop(grid[i-1][j]);
		else
			return current.matchLeft(grid[i][j-1]) && current.matchTop(grid[i-1][j]);
	}

}
