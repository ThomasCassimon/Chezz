package Chess.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Astrid on 22/02/2017.
 */
public class MainWindow extends JFrame
{
	private JPanel panel = new JPanel();
	private Tile tiles[] = new Tile[64];

	public MainWindow()
	{
		 super("Chezz!");

		 int i;

		 setSize(400,400);
		 setResizable(false);
		 setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 panel.setLayout(new GridLayout(8,8));


		 for (i=0;i<64;i++)
		 {
		 	tiles[i] = new Tile();
		 	panel.add(tiles[i]);
		 }

		 setVisible(true);
	}


}
