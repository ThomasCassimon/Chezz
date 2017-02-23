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
	private static float FONT_SIZE = 25.0f;


	//Colors
	private Color colorBlack = new Color(140,70,20); 	//Saddlebrown (RGB 139,69,19)
	private Color colorWhite = new Color(210,180,140); 	//Tan (RGB 210,180,140)

	//Images
	private ImageIcon white_Pawn = new ImageIcon("PW.png");


	public Board()
	{
		super();

		int row, col, arrayIndex;
		char letter='A';
		this.setPreferredSize(new Dimension(800,800));
		this.setLayout(new GridLayout(NUMBER_TILES +1, NUMBER_TILES +1));

		//Forming the chess board

		for (row = 0; row < NUMBER_TILES; row++)
		{

			for (col = 0; col < NUMBER_TILES; col++)
			{
				arrayIndex = (row * 8) + col;


				tiles[arrayIndex] = new Tile();

				if ((row + col) % 2 != 0)
				{
					tiles[arrayIndex].setBackground(colorBlack);
				}
				else
				{
					tiles[arrayIndex].setBackground(colorWhite);
				}

				tiles[arrayIndex].setOpaque(true);
				tiles[arrayIndex].setBorderPainted(false);

				this.add(tiles[arrayIndex]);
			}

			JLabel label = new JLabel(" " + Integer.toString(row+1));
			label.setBackground(Color.LIGHT_GRAY);
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setFont(label.getFont().deriveFont(FONT_SIZE));
			label.setOpaque(true);

			this.add(label);

		}

		for (col=0;col<8;col++)
		{
			JLabel label = new JLabel (Character.toString(letter));
			label.setBackground(Color.LIGHT_GRAY);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.TOP);
			label.setFont(label.getFont().deriveFont(FONT_SIZE));
			label.setOpaque(true);
			letter++;
			this.add(label );
		}

		JLabel label = new JLabel("");
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(true);
		this.add(label);


	}
}
