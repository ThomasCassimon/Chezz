package Chess.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Created by Astrid on 22/02/2017.

public class MainWindow extends JFrame
{
	//private JPanel panel = new JPanel();
	private Board board;


	public MainWindow()
	{
		 super("Chezz!");
		 board = new Board();
		 setSize(900,900);
		 setResizable(false);
		 setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		 //panel.add(board);

		 //add(panel);
		this.add(board);

		 setVisible(true);
	}
}
