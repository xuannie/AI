/**
 * Solve painted-squares puzzle.
 *
 * @author  Terry Sergeant
 * @version Spring 2016
 *
*/

import java.util.Scanner;

public class Driver
{
	public static void main(String [] args)
	{
		/*
		Tile t= new Tile(0,1,2,3);

		for (int i=0; i<5; i++) {
			System.out.println(t);
			t.turn();
		}
		*/

		if (args.length==0) {
			System.err.println("Must provide file name");
			System.exit(-1);
		}

		Board b= new Board(args[0]);
		b.display();
	}

}

