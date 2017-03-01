package Chess.UI;

import Chess.Game.GameManager;
import Chess.Game.Move;
import Chess.Game.Piece;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//Created by Astrid on 22/02/2017.

public class MainWindow extends JFrame implements ActionListener
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
		 board = new Board(this);
		 sidePanel = new SidePanel();

		 panel.setBackground(UIData.BACKGROUND_COLOR);

		 this.setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		 this.setResizable(false);
		 this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		panel.add(board);
		panel.add(sidePanel);

		panel.setBackground(UIData.BACKGROUND_COLOR);
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		int i;
		int[] indexArr;
		ArrayList<Move> moves = new ArrayList<Move>();
		Piece piece;

		System.out.println("action detected");

		for (i=0;i<UIData.NUMBER_TILES*UIData.NUMBER_TILES; i++)
		{
			if(e.getSource() == board.getTile(i))
			{
				board.setActive(i);
				indexArr = board.get2DCoord(i);
				piece = gameManager.get(indexArr[1],indexArr[0]);
				System.out.println("Piece byte " + piece.getPieceByte());
				moves = gameManager.getAllValidMoves(piece);

				for(Move move: moves)
				{
					System.out.println("MOVE");
					board.highlightPiece(move.get2DDst());
				}

			}
		}

	}
}
