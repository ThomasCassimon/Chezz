package Chess.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Astrid on 23-Feb-17.
 */
public class Board extends JPanel
{
	private Tile tiles[] = new Tile[64];

	private static int NUMBER_TILES = 8;



	//Images
	private ImageIcon king = new ImageIcon(MainWindow.class.getResource("KB.png"));


	public Board()
	{
		super();

		this.setPreferredSize(new Dimension(800,800));
		this.setLayout(new GridLayout(NUMBER_TILES +2, NUMBER_TILES +2));

		initBoard();

		tiles[16].setIcon(king);


	}

	private void initBoard()
	{
		//Forming the chess board
		char letter='A';
		int row, col, arrayIndex;


		//TOP of frame

		JLabel label = new JLabel("");
		label.setBackground(UIData.FRAME);
		label.setOpaque(true);
		this.add(label);


		for (col=0;col<8;col++)
		{
			label = new JLabel (Character.toString(letter));
			label.setBackground(UIData.FRAME);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.BOTTOM);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);
			letter++;
			this.add(label );
		}

		label = new JLabel("");
		label.setBackground(UIData.FRAME);
		label.setOpaque(true);
		this.add(label);


		for (row = 0; row < NUMBER_TILES; row++)
		{
			//LEFT of frame
			label = new JLabel(Integer.toString(row+1)+" ");
			label.setBackground(UIData.FRAME);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);

			this.add(label);

			//TILES
			for (col = 0; col < NUMBER_TILES; col++)
			{
				arrayIndex = (row * 8) + col;


				tiles[arrayIndex] = new Tile();

				if ((row + col) % 2 != 0)
				{
					tiles[arrayIndex].setBackground(UIData.BROWN);
				}
				else
				{
				tiles[arrayIndex].setBackground(UIData.LIGHT_BROWN);
				}

				tiles[arrayIndex].setOpaque(true);
				tiles[arrayIndex].setBorderPainted(false);

				this.add(tiles[arrayIndex]);
			}

			//RIGHT of frame
			label = new JLabel(" " + Integer.toString(row+1));
			label.setBackground(UIData.FRAME);
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);

			this.add(label);

		}


		//BOTTOM of frame
		label = new JLabel("");
		label.setBackground(UIData.FRAME);
		label.setOpaque(true);
		this.add(label);

		letter = 'A';
		for (col=0;col<8;col++)
		{
			label = new JLabel (Character.toString(letter));
			label.setBackground(UIData.FRAME);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.TOP);
			label.setFont(label.getFont().deriveFont(UIData.FONT_SIZE));
			label.setOpaque(true);
			letter++;
			this.add(label );
		}

		label = new JLabel("");
		label.setBackground(UIData.FRAME);
		label.setOpaque(true);
		this.add(label);
	}
}
