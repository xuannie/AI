/**
 * Modified version for solving sliding tile puzzle with A* search.
 *
 * @author	Alexis Chuah
 * @version 15 Feb 2016
 *
 * <p>Implement hash table to store board states that have been processed.</p>
*/
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.HashSet;
import java.io.InputStream;

public class Solver
{
	private int rows;        // number of rows for boards we are solving
	private int cols;        // number of cols for boards we are solving
	private Board board;     // current starting board
	private Board solved;    // solved board
	private Scanner source;  // source from which we read next board
	private Timer timer;     // track wall-clock time of solution
	private int states;       // total number of states encountered
	private int duplicateStates; //number of duplicate states encountered
	private boolean found;

	/**
	 * Initialize the solver by specifying puzzle sizes and input source.
	 *
	 * @param rows number of rows for each puzzle
	 * @param cols columns for each puzzle
	 * @param dataFile input source for board
	 */
	public Solver(int rows, int cols, InputStream dataFile)
	{
		try {
			this.rows= rows;
			this.cols= cols;
			timer= new Timer();
			source= new Scanner(dataFile);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}


	/**
	 * Creates a new board from the input source.
	 *
	 * @return true if the nextBoard was generated successfully; false otherwise
	 */
	public boolean nextBoard()
	{
		int n= rows*cols;
		char [] tiles= new char[n];

		if (source.hasNextInt()) {
			for (int i= 0; i<n; i++)
				tiles[i]= (char)source.nextInt();
			board= new Board(tiles,rows,cols);
			return true;
		}

		return false;
	}


	/**
	 * Public-facing method to solve current board.
	 */
	public void solve()
	{
		if (board==null)
			System.out.println("No board loaded ... try calling nextBoard() ...");
		else {
			timer.start();
			astar(board);
			timer.stop();
		}
	}


	/**
	 * Gives concise representation if solving multiple boards.
	 * @return board with stats along with solution (if available)
	 */
	@Override
	public String toString()
	{
		if (board==null) return "No current board";
		if (solved==null){
			return board.toString();
		}
		display();
		System.out.println();
		return board.toString()+" -->\n"+solved.toString()+" ("+timer+"; duplicate states: " + duplicateStates + "; states: "+states+")";
	}


	/**
	 * Display summary information about solution.
	 */
	public void display()
	{

		System.out.print("Directions to solution: ");
		showSolution(solved);
	
	}


	/**
	 * Display solution by showing moves from start to finish.
	 */
	public void showSolution(Board end)
	{
		if(end.getPrev()!=null)
			showSolution(end.getPrev());
		System.out.print(end.getDir() + " ");	
	}


	/**
	 * Performs A* search.
	 *
	 * @param start Starting board position
	 */
	private void astar(Board start)
	{
		PriorityQueue<Board> openSet = new PriorityQueue<Board>();
		HashSet<Board> closeSet = new HashSet<Board>();	
		Board curr;
		Board next;
		openSet.add(start);
		int steps;
		found = false;
		String moves = "UDRL";

		while(!openSet.isEmpty() && !found){
			curr = openSet.remove();
			steps = curr.getSteps();
			closeSet.add(curr);

			if(curr.isGoal()){
				found=true;
				solved=curr;

				return;
			}

			for (int i=0; i<moves.length(); i++){
				char dir = moves.charAt(i);

				if(curr.canMove(dir)){
					next = curr.movePiece(dir, steps);
					states++;

					if(closeSet.contains(next)){
						duplicateStates++;
					}
					else{
						openSet.add(next);		
					}
				}
			}
		}
	}
}
