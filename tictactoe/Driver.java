/**
 * Driver for the game.
 */
import java.util.Scanner;

public class Driver
{
  public static void main(String [] args)
  {
		Scanner kb= new Scanner(System.in);
		int move;

		TicTacToe game= new TicTacToe();
		game.play();
  }
}
