package Chess.UI;

import Chess.Game.GameManager;
import Chess.Game.Piece;

import javax.swing.*;
import java.awt.*;
import java.rmi.server.UID;
import java.util.ArrayList;

//Created by Astrid on 22/02/2017.

public class MainWindow extends JFrame
{
	private static int WINDOW_HEIGHT = 1200;
	private static int WINDOW_WIDTH = 850;
	private GameManager gameManager;

	private JPanel panel;
	private Board board;
	private SidePanel sidePanel;



	public MainWindow()
	{
		 super("Chezz!");
		 this.gameManager = new GameManager();
		 this.gameManager.init();
		 panel = new JPanel();
		 board = new Board();
		 sidePanel = new SidePanel();

		 panel.setBackground(UIData.BACKGROUND);

		 this.setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		 this.setResizable(false);
		 this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		panel.add(board);
		panel.add(sidePanel);

		panel.setBackground(UIData.BACKGROUND);
		this.add(panel);
		this.initBoard();


		 this.setVisible(true);

	}

	/**
	 * Sets all the pieces in the right place at the beginning of the game
	 */
	private void initBoard()
	{
		ArrayList<Piece> pieces;
		pieces = gameManager.getAllWhitePieces();

		for (Piece piece: pieces)
		{
			board.setPiece(piece);
		}

		pieces = gameManager.getAllBlackPieces();
		for (Piece piece: pieces)
		{
			board.setPiece(piece);
		}
	}
}
