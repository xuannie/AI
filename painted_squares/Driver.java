/**
 * Solve painted-squares puzzle.
 *
 * @author  Alexis Chuah
 * @version 28 Jan 2016
 *
 * <p>
 * Solves and displays solutions to  painted-squares puzzle and calculates time taken.
 * </p>
 *
*/

import java.util.Scanner;

public class Driver
{

	public static void main(String [] args)
	{

		long start, stop;
		double solveTime;
		/*
		Tile t= new Tile(0,1,2,3);
	
			System.out.println(t);
			t.turn();
		}
		*/

		if (args.length==0) {
			System.err.println("Must provide file name");
			System.exit(-1);
		}

		
		Board b= new Board(args[0]);
		//b.display();
		start = System.currentTimeMillis();
		b.solve();
		stop = System.currentTimeMillis();

		solveTime = stop-start;
		System.out.println("Time taken to solve puzzle: " + ((solveTime)/1000.0) + "\n");
	}
}

