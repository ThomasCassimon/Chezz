package Chess.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Created by Astrid on 22/02/2017.

public class MainWindow extends JFrame
{
	private JPanel panel = new JPanel();
	private Tile tiles[] = new Tile[64];

	//Images
	private ImageIcon white_Pawn = new ImageIcon("PW.png");


	//Colors
	private Color colorBlack = Color.BLACK;
	private Color colorWhite = Color.WHITE;


	public MainWindow()
	{
		 super("Chezz!");

		 int row,col, arrayIndex;

		 setSize(800,800);
		 setResizable(false);
		 setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 panel.setLayout(new GridLayout(8,8));



		//Forming the chess board

		 for (row=0; row <8; row++)
		 {

		 	for (col=0;col<8;col++)
			{
				arrayIndex = (row * 8) + col;


				tiles[arrayIndex] = new Tile();

				if ( (row + col)%2 == 0)
				{
					tiles[arrayIndex].setBackground(colorBlack);
				}
				else
				{
					tiles[arrayIndex].setBackground(colorWhite);
				}

				tiles[arrayIndex].setOpaque(true);
				tiles[arrayIndex].setBorderPainted(false);

				panel.add(tiles[arrayIndex]);
			}

		 }

		 add(panel);
		 setVisible(true);
	}
}
