/**
 * Represents a graphical board for a painted square puzzle.
 *
 * @author  Terry Sergeant
 * @version Spring 2016
 *
*/
import javax.swing.*;
import java.awt.*;

public class GBoard extends Board
{
	/** The frame that provides window for the board. */
	private JFrame f;

	/** The panel on which we draw the tiles. */
	private JPanel panel;

	/** The graphical tiles we put on the board. */
	private GTile [][] gtiles;


	/**
	 * Reads filename and sets up the board accordingly.
	 *
	 * @param filename name of data file that defines the tiles
	 */
	public GBoard(String filename)
	{
		super(filename);

		gtiles= new GTile[numRows][numCols];
		f= new JFrame("Painted Squares");
		f.setSize(numCols*100,numRows*110);

		panel= new JPanel();
		panel.setLayout(new GridLayout(numRows,numCols));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		int i,j;
		for (i=0; i<numRows; i++) {
			for (j=0; j<numCols; j++) {
				gtiles[i][j]= new GTile(grid[i][j].getSides());
				panel.add(gtiles[i][j]);
			}
		}

		f.add(panel);
		f.setVisible(true);
	}


	/**
	 * Redraws the tiles based on current configuration of grid.
	 */
	public void display()
	{
		int i,j;

		//super.display();
		for (i=0; i<numRows; i++)
			for (j=0; j<numCols; j++)
				gtiles[i][j].setSides(grid[i][j].getSides());

		panel.repaint();
	}
}
