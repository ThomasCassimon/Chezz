package Chess.UI;

import javax.swing.*;
import java.awt.*;

//Created by Astrid on 22/02/2017.

public class MainWindow extends JFrame
{
	private static int WINDOW_HEIGHT = 1200;
	private static int WINDOW_WIDTH = 850;

	private JPanel panel;
	private Board board;
	private SidePanel sidePanel;



	public MainWindow()
	{
		 super("Chezz!");
		 panel = new JPanel();
		 board = new Board();
		 sidePanel = new SidePanel();

		 panel.setBackground(Color.white);

		 setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		 setResizable(false);
		 setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		panel.add(board);
		panel.add(sidePanel);

		panel.setBackground(Color.BLACK);
		this.add(panel);



		 setVisible(true);
	}
}
