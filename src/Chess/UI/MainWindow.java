package Chess.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Created by Astrid on 22/02/2017.

public class MainWindow extends JFrame
{
	private static int BOARD_SIZE = 1200;

	private JPanel panel;
	private Board board;
	private BottomPanel bottomPanel;



	public MainWindow()
	{
		 super("Chezz!");
		 panel = new JPanel();
		 board = new Board();
		 bottomPanel = new BottomPanel();

		 //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		 panel.setBackground(Color.white);

		 setSize(BOARD_SIZE, 1000);
		 setResizable(false);
		 setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		panel.add(board);
		panel.add(bottomPanel);

		this.add(panel);

		 setVisible(true);
	}
}
