package Chess.UI;

import Chess.Athena.AIPlayer;
import Chess.Game.GameManager;
import Chess.Game.Move;
import Chess.Game.Piece;
import Chess.Game.PieceData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

//Created by Astrid on 22/02/2017.

public class GamePanel extends JFrame implements ActionListener
{
	private GameManager gameManager;

	private JPanel panel;
	private Board board;
	private SidePanel sidePanel;

	public GamePanel()
	{
		 super("Chezz!");
		 this.gameManager = new GameManager();
		 this.gameManager.init();
		 panel = new JPanel();
		 board = new Board(this);
		 sidePanel = new SidePanel(this);

		 panel.setBackground(UIData.BACKGROUND_COLOR);

		 this.setSize(UIData.WINDOW_WIDTH, UIData.WINDOW_HEIGHT);
		 this.setResizable(false);
		 this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		panel.add(board);
		panel.add(sidePanel);

		this.add(panel);
		this.initBoard();

		this.setResizable(true);
		this.setVisible(true);
	}

	/**
	 * Sets all the pieces in the right place at the beginning of the game
	 */
	private void initBoard()
	{
		ArrayList<Piece> pieces;
		pieces = this.gameManager.getAllPieces(PieceData.WHITE_BYTE);

		for (Piece piece: pieces)
		{
			board.setPiece(piece);
		}

		pieces = this.gameManager.getAllPieces(PieceData.BLACK_BYTE);
		for (Piece piece: pieces)
		{
			board.setPiece(piece);
		}

		//this.testAI();
	}


	/**
	 * Called when a button is pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		int i;
		int[] indexArr;
		ArrayList<Move> moves;
		Piece piece;

		if (e.getSource() == sidePanel.getPause())
		{
			//GameManager.chronometer.pause();
			System.out.println("Pause");
		}

		else if (e.getSource() == sidePanel.getUndo())
		{
			Move move = gameManager.getLastMove();
			move = new Move(move.getDst(), move.getSrc(), 0);
			board.makeMove(move, PieceData.getOpponentColor(gameManager.getActiveColorByte()));

			gameManager.undo();

		}
		else if (gameManager.getCachedMoves().isEmpty())
		{
			for (i = 0; i < UIData.NUMBER_TILES * UIData.NUMBER_TILES; i++)
			{
				if (e.getSource() == board.getTile(i))
				{
					indexArr = board.get2DCoord(i);
					piece = gameManager.get(indexArr[0], indexArr[1]);
					moves = gameManager.getLegalMoves(piece);

					if (!moves.isEmpty())
					{
						board.setActive(i);
					}


					gameManager.setCachedMoves(moves);
					for (Move move : moves)
					{
						board.highlightMove(move);
					}

				}
			}
		}
		else
		{
			moves = gameManager.getCachedMoves();

			board.setNormalTileColor(moves.get(0).get2DSrc());

			for (i = 0; i < UIData.NUMBER_TILES * UIData.NUMBER_TILES; i++)
			{
				if (e.getSource() == board.getTile(i))
				{
					for (Move move : moves)
					{
						board.setNormalTileColor(move.get2DDst());
						if (i == board.getIndex(move.get2DDst()))
						{
							gameManager.makeMove(move);
							board.makeMove(move, gameManager.getActiveColorByte());
						}
					}
				}
			}
			gameManager.resetCachedMoves();
		}
	}

	public void testAI ()
	{
		AIPlayer aip = new AIPlayer(PieceData.WHITE_BYTE);
		System.out.println("Transposition table hits: " + Long.toString(AIPlayer.transpositionTable.getHits()) + "\nGenerated: " + aip.playTurn(this.gameManager, 7).toString());
	}
}
