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
	/**
	 * Called when a button is pressed.
	 */
	public void actionPerformed(ActionEvent e)
	{
		int i;
		int[] indexArr;
		ArrayList<Move> moves;
		Piece piece;

			if (gameManager.getCachedMoves().isEmpty())
			{
				for (i = 0; i < UIData.NUMBER_TILES * UIData.NUMBER_TILES; i++)
				{
					if (e.getSource() == board.getTile(i))
					{

						indexArr = board.get2DCoord(i);
						piece = gameManager.get(indexArr[0], indexArr[1]);
						moves = gameManager.getAllValidMoves(piece);

						if (!moves.isEmpty())
						{
							board.setActive(i);
						}


						gameManager.setCachedMoves(moves);
						for (Move move : moves)
						{
							board.highlightPiece(move.get2DDst());
						}

					}
				}
			}
			else
			{
				moves = gameManager.getCachedMoves();

				board.setNormalTileColor(moves.get(0).get2DSrc());

				for (i=0;i<UIData.NUMBER_TILES*UIData.NUMBER_TILES;i++)
				{
					if(e.getSource() == board.getTile(i))
					{
						for(Move move: moves)
						{
							board.setNormalTileColor(move.get2DDst());
							if (i == board.getIndex(move.get2DDst()))
							{
								gameManager.makeMove(move);
								board.makeMove(move);
							}
						}
					}
				}


				gameManager.resetCachedMoves();
			}


		}


}
