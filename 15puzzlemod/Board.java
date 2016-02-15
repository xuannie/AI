/**
 * Implements a text-based 15-puzzle board.
 *
 * @author  Alexis Chuah 
 * @version 15 Feb 2016
 *
 */

public class Board implements Comparable<Board>
{
	private int rows,cols; // board size in rows/cols
	private char [] tiles; // the actual tiles and their locations
	private char dir;      // direction prev used to get here
	private int  bpos;     // position of blank element
	private Board prev;    // pointer to previous state
	private int	h;         // heuristic value (manhattan distance)
	private int	g;         // cost so far


	/**
	 * Constructor based on starting tile positions and board dimensions.
	 *
	 * @param tiles an array of tile positions
	 * @param rows number of rows on the board
	 * @param cols number of columns on the board
	 *
	 * <p>We do not make sure the tile list is valid. It should contain
	 * exactly the int/char values 0 - rows*cols-1. The tile numbered
	 * rows*cols-1 is interpreted as the blank tile.</p>
	 */
	public Board(char [] tiles, int rows, int cols)
	{
		this.rows= rows;
		this.cols= cols;
		this.tiles= tiles.clone();
		this.bpos= new String(tiles).indexOf((char) rows*cols-1);
		this.dir= 'X';       // initially direction is not valid
		this.prev= null;     // and no previous board
		this.h= manhattan(); // use manhattan distance to estimate position
		this.g= 0;           // no steps taken with a new board
	}


	/**
	 * Constructor based on starting tile positions and board dimensions.
	 *
	 * @param lastBoard pointer to previous board
	 * @param tiles array of tile positions
	 * @param direction direction taken from previous board
	 * @param steps total steps taken since start of board
	 *
	 */
	public Board(Board lastBoard, char []tiles, char direction, int steps)
	{
		this.rows= lastBoard.rows;
		this.cols= lastBoard.cols;
		this.tiles= tiles.clone();
		this.bpos= new String(tiles).indexOf((char) rows*cols-1);
		this.dir= direction;      
		this.prev= lastBoard;     
		this.h= manhattan(); // use manhattan distance to estimate position
		this.g= steps+1;           
	}

	/**
	 * Overrides compareTo to provide proper order in priority queue.
	 *
	 * @param other board we are comparing this one to
	 * @return difference of "cost" between this board and other board
	 */
	@Override
	public int compareTo(Board other)
	{
		int thisCost, otherCost;
		
		thisCost = this.h + this.g;
		otherCost = other.h + other.g;
	
		return (thisCost - otherCost);
	}


	/**
	 * Calculate classic manhattan distance for a board.
	 *
	 * @return manhattan distance
	 *
	 * <p>We calculate distance for all tiles and then subtract back out the
	 * distance added by the blank tile.</p>
	 */
	private int manhattan()
	{
		int i,n,h= 0;
		n= rows*cols;
		for (i=0; i<n; i++)
				h+= Math.abs(i/cols-tiles[i]/cols) + Math.abs(i%cols-tiles[i]%cols);
		h-= (n-1)/cols-bpos/cols + (n-1)%cols-bpos%cols;

		return h;
	}


	/**
	 * Determine whether a move in the specified direction is possible.
	 *
	 * @param trydir the direction we may try to move: U, D, R, or L
	 * @return true if the move is possible, false otherwise
	 *
	 * <p>Moves are from the perspective of the blank tile.</p>
	 * <p>A move is considered not possible if it "undoes" the move
	 * the got us in this state. Also, if it moves the blank tile
	 * off the board it is not possible.</p>
	 */
	public boolean canMove(char trydir)
	{
		switch (trydir) {
			case 'U': return dir!='D' && bpos-cols >= 0;
			case 'D': return dir!='U' && bpos+cols < rows*cols;
			case 'R': return dir!='L' && (bpos+1)%cols!=0; //(bpos+1)/rows==(bpos/rows);
			case 'L': return dir!='R' && bpos%cols!=0; //bpos>0 && (bpos-1)/rows==(bpos/rows);
			default: return false;
		}
	}

	/**
	 * Move piece in direction and swaps, assumes move is valid. 
	 *
	 * @param direction Directions possible: U, D. R, or L. 
	 * @return Board new that links to previous. 
	 *
	 */
	
	public Board movePiece(char direction, int steps){
		Board newBoard;
		char[]newtiles = this.tiles.clone();
		char basetile = newtiles[bpos];
		switch (direction){
			case 'U': 
					//swap "blank" with tile above.
					newtiles[bpos]=newtiles[bpos-cols];
					newtiles[bpos-cols]=basetile;
				break;

			case 'D': 
					//swap "blank" with tile below.
					newtiles[bpos]=newtiles[bpos+cols];
					newtiles[bpos+cols]=basetile;
				break;
		
			case 'L': 
					//swap "blank" with tile to left.
					newtiles[bpos]=newtiles[bpos-1];
					newtiles[bpos-1]=basetile;
				break;

			case 'R': 
					//swap "blank" with tile to right.
					newtiles[bpos]=newtiles[bpos+1];
					newtiles[bpos+1]=this.tiles[bpos];
				break;
		}

		newBoard = new Board(this, newtiles, direction, steps);

		return newBoard;
	}


	/**
	 * Getter for reference to previous board.
	 */
	public Board getPrev()
	{
		return prev;
	}


	/**
	 * Getter for steps traveled so far.
	 */
	public int getSteps()
	{
		return g;
	}

	/**
	 * Getter for direction used.
	 */
	public char getDir()
	{
		return dir;
	}

	/**
	 * Determine whether we are at goal state or not.
	 *
	 * @return true if this board is at goal state; false otherwise
	 */
	public boolean isGoal()
	{
		return h==0;
	}


	/**
	 * Concise representation of the board.
	 *
	 * @return list of tiles enclosed in ()'s
	 */
	@Override
	public String toString()
	{
		String str="("+(int)tiles[0];
		for (int i=1; i<tiles.length; i++)
			str+= " "+(int)tiles[i];
		return String.format(str+" | f:%2d, g:%2d, h:%2d)",g+h,g,h);
	}


	/**
	 * Displays ASCII board along with f(), g(), and h() values.
	 */
	public void display()
	{
		int i,j;
		System.out.println("+----+----+----+----+");
		System.out.printf("| f=%3d g=%3d h=%3d |\n",h+g,g,h);
		for (i=0; i<rows; i++) {
			System.out.println("+----+----+----+----+");
			System.out.printf("| ");
			for (j=0; j<cols; j++)
				if (tiles[i*cols+j]==rows*cols-1)  // if blank
					System.out.printf("%2s | ",' ');
				else
					System.out.printf("%2d | ",(int)tiles[i*cols+j]);
			System.out.println();
		}
		System.out.println("+----+----+----+----+\n");
	}
}
