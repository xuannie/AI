/**
 * Implements a tic-tac-toe board capable of 1-move AI evaluation.
 *
 * @author  T.Sergeant
 * @version For AI In-Class Work
 *
 * <p>Board is represented as a 1d array of chars.</p>
*/
import java.util.Scanner;

public class Board
{
	char [] board;  // the board
	int moveCount;  // number of pieces placed
	int boardValue; // the AI evaluation of the board at the current time

	/**
	 * Start with an empty board and counts at zero ...
	 */
	public Board()
	{
		int i;
		moveCount= 0;
		boardValue= 0;
		board= new char[9];
		for (i=0; i<9; i++)
			board[i]= ' ';
	}


	/**
	 * Display the board along with a key for aiding user input.
	 */
	public void show()
	{
		System.out.printf("=============   =============\n");
		System.out.printf("||   Key   ||   ||  Game   ||\n");
		System.out.printf("=============   =============\n");
		System.out.printf("  0 | 1 | 2       %c | %c | %c \n",board[0],board[1],board[2]);
		System.out.printf(" ---+---+---     ---+---+---\n");
		System.out.printf("  3 | 4 | 5       %c | %c | %c \n",board[3],board[4],board[5]);
		System.out.printf(" ---+---+---     ---+---+---\n");
		System.out.printf("  6 | 7 | 8       %c | %c | %c \n",board[6],board[7],board[8]);
		System.out.printf("=============   =============\n");
	}


	/**
	 * Attempt to make a move in the given position.
	 *
	 * @param pos the board position at which the move is requested.
	 * @param token the token/piece to be placed on the board if a good request.
	 * @return false if pos is out of range or a piece has already been placed; true otherwise.
	 *
	 * We update move count and boardValue if piece can be placed.
	 */
	public boolean makeMove(int pos, char token)
	{
		if (pos<0 || pos>8 || board[pos]!=' ') return false;
	  board[pos]= token;
		moveCount++;
		boardValue= evaluate(token);
		return true;
	}


	/**
	 * Determine if game is over or not.
	 *
	 * @return true if number of moves is 9 or if boardValue indicated a winner
	 */
	public boolean gameOver()
	{
		return moveCount >= 9 || boardValue > 50;
	}


	/**
	 * Determine if it is a tie game or not ...
	 *
	 * @return true if game ended in a tie
	 */
	public boolean tieGame()
	{
		return moveCount >= 9 && Math.abs(boardValue) < 50;
	}


	/**
	 * Given 3 board positions, see if 'token' is favored or disfavored.
	 *
	 * @return number representing the strength of the position from token's
	 * perspective; 100 means a win.
	 */
	private int evalLine(int x, int y, int z, char token)
	{
		int good,bad;
		good= bad= 0;

		// count good marks and bad marks ...
		if (board[x]==token)
			good++;
		else if (board[x]!=' ')
			bad++;

		if (board[y]==token)
			good++;
		else if (board[y]!=' ')
			bad++;

		if (board[z]==token)
			good++;
		else if (board[z]!=' ')
			bad++;

		// evaluate the results ...
		if (good==3) return 100;
		if (bad==3) return -200;
		if (good > 0 && bad==0) return 1<<good;
		if (bad > 0 && good==0) return -2<<bad;
		return 0;
	}

	/**
	 * Determine total goodness of the board from token's perspective.
	 *
	 * @return number representing the strength of the position from token's
	 * perspective; anything over 50 is a win.
	 */

	public int evaluate(char token)
	{
		return evalLine(0,1,2,token) + evalLine(3,4,5,token) + evalLine(6,7,8,token) +
		       evalLine(0,3,6,token) + evalLine(1,4,7,token) + evalLine(2,5,8,token) +
		       evalLine(0,4,8,token) + evalLine(2,4,6,token);
  }

	/**
	 * Determine if given token has won.
	 *
	 * @return true if token has won, false otherwise.
	 */
	public boolean isWinner(char token)
	{
		return evaluate(token) > 50;
	}


	/**
	 * Suggest a best move for "token" (assuming it really is "token's" turn).
	 *
	 * @return the position of the suggested move
	 */
	public int suggestMove(char token)
	{
		int bestMove= -1;
		int bestEval= -100;
		int move;
		int origValue= boardValue;

		for (move=0; move<9; move++)
			if (makeMove(move,token))
			{
				if (boardValue > bestEval) {
					bestEval= boardValue;
					bestMove= move;
				}
				board[move]= ' '; // umake move
				moveCount--;
			}

		boardValue= origValue; // restore boardValue to previous state

		return bestMove;
	}

}
