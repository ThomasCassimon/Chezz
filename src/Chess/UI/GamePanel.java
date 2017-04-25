package Chess.UI;

import Chess.Exceptions.Checked.GameOverException;
import Chess.Game.*;
import Chess.Utils.Parser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class GamePanel extends JFrame implements ActionListener, MouseListener
{
	private GameManager gameManager;

	private JPanel panel;
	private Board board;
	private SidePanel sidePanel;
	private MainFrame mainFrame;

	public GamePanel(GameManager gameManager, MainFrame mainFrame)
	{
		super("Chezz!");
		this.gameManager = gameManager;
		this.mainFrame = mainFrame;

		//
		panel = new JPanel();
		board = new Board(this);
		sidePanel = new SidePanel(this);


		panel.setBackground(UIData.BACKGROUND_COLOR);
		this.setSize(UIData.GAMEPANEL_DIMENSION);


		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		panel.add(board);
		panel.add(sidePanel);

		this.setContentPane(panel);
		this.initBoard();

		this.setResizable(false);

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

		if(gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
		{
			board.setBoardWhite();
		}
		else
		{
			board.setBoardBlack();
		}
	}


	/**
	 * Called when a button is pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{


		if (e.getSource() == sidePanel.getPause())                                                    //PAUSE
		{
			this.gameManager.getChronometer().pause();
			System.out.println("Pause");
		}

		else if (e.getSource() == sidePanel.getUndo())                                                //UNDO
		{
			if(gameManager.hasLastMove())
			{

				HistoryMove historyMove = gameManager.getLastMove();
				gameManager.undo();

				board.setPiece(gameManager.get(historyMove.getSrc()));
				board.setPiece(gameManager.get(historyMove.getDst()));


				board.update(gameManager.getActiveColorByte());

				this.setHistory();

			}

		}
		else if (e.getSource() == sidePanel.getSave())                                                //SAVE
		{
			System.out.println("Save");
			Parser.saveToFile(gameManager.getMoveHistory(), this);
		}
		else if (e.getSource() == sidePanel.getMoveInput())											//TEXT MOVE
		{
			String input = sidePanel.getMoveInput().getText();
			Move move = Parser.stringToMove(input, gameManager);
			//System.out.println("Move: " + move.toString());
			//System.out.println("Move: " + move.getPrettySrcCoords() + " - " + move.getPrettyDstCoords());
			//System.out.println("Pieces: Src: " + this.gameManager.get(move.getSrc()).toString() + " Dst: " + this.gameManager.get(move.getDst()).toString());


			if (move != null)
			{
				if(move.isKingCastle()|move.isKingCastle())
				{
					handleCastling(move);
				}
				//try
				{
					this.makeMove(move);

				}
				//catch (GameOverException goe)
				{
					// Game is over
					System.out.println("Game over!");
				}

				System.out.println("Move input: " + input);
				sidePanel.setMoveInput("");
			}
			else
			{
				sidePanel.setMoveInput("Invalid Move");
			}


		}
		else if(e.getSource() == sidePanel.getExit())
		{
			System.out.println("Exit detected in gamepanel");
			mainFrame.actionPerformed(e);
		}
		else if (gameManager.getCachedMoves().isEmpty())                                            //SELECT SOURCE
		{
			int[] indexArr;
			ArrayList<Move> moves;
			Piece piece;
			for (int i = 0; i < UIData.NUMBER_TILES * UIData.NUMBER_TILES; i++)
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
		else                                                                                        //SELECT DESTINATION
		{
			ArrayList<Move> moves;

			moves = gameManager.getCachedMoves();

			board.setNormalTileColor(moves.get(0).get2DSrc());

			for (int i = 0; i < UIData.TOTAL_TILES; i++)
			{
				if (e.getSource() == board.getTile(i))
				{

					for (Move move : moves)
					{
						board.setNormalTileColor(move.get2DDst());
						if (i == board.getIndex(move.get2DDst()))
						{
							this.makeMove(move);
							if (gameManager.isCheckMate(PieceData.getOpponentColor(gameManager.getActiveColorByte())))
							{
								this.handleCheckMate();
							}
							if (move.isPromotion())
							{
								board.getTile(i).setIcon(this.handlePromotion(board.get2DCoord(i)));
							}
							if(move.isKingCastle()|move.isQueenCastle())
							{
								this.handleCastling(move);
							}
						}

					}
				}
			}
			gameManager.resetCachedMoves();
		}
	}


	public Icon handlePromotion(int[] position)
	{
		final int QUEEN = 0;
		final int ROOK = 1;
		final int BISHOP = 2;
		final int KNIGHT = 3;
		Object[] options = {"Queen","Rook","Bishop","Knight"};

		int choice = JOptionPane.showOptionDialog(this, "Which piece do you want your pawn to become?", "Promotion", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,UIData.BK,options,null);

		if (PieceData.getOpponentColor(gameManager.getActiveColorByte()) == PieceData.WHITE_BYTE)
		{
			switch (choice)
			{
				case QUEEN :
					gameManager.handlePromotion(position, PieceData.QUEEN_BYTE|PieceData.WHITE_BYTE);
					return UIData.WQ;
				case ROOK :
					gameManager.handlePromotion(position, PieceData.ROOK_BYTE|PieceData.WHITE_BYTE);
					return UIData.WR;
				case BISHOP :
					gameManager.handlePromotion(position, PieceData.BISHOP_BYTE|PieceData.WHITE_BYTE);
					return UIData.WB;
				case KNIGHT :
					gameManager.handlePromotion(position, PieceData.KNIGHT_BYTE|PieceData.WHITE_BYTE);
					return UIData.WN;
				default:
					return UIData.WP;
			}


		}
		else
		{
			switch (choice)
			{
				case QUEEN :
					gameManager.handlePromotion(position, PieceData.QUEEN_BYTE|PieceData.BLACK_BYTE);
					return UIData.BQ;
				case ROOK :
					gameManager.handlePromotion(position, PieceData.ROOK_BYTE|PieceData.BLACK_BYTE);
					return UIData.BR;
				case BISHOP :
					gameManager.handlePromotion(position, PieceData.BISHOP_BYTE|PieceData.BLACK_BYTE);
					return UIData.BB;
				case KNIGHT :
					gameManager.handlePromotion(position, PieceData.KNIGHT_BYTE|PieceData.BLACK_BYTE);
					return UIData.BN;
				default:
					return UIData.BP;
			}
		}
	}

	public void handleCheckMate()
	{
		Object[] options = { "Exit","Save & Exit","Save & Start New Game","Start New Game"};
		String winner;
		if(gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
		{
			winner = "Black";
		}
		else
		{
			winner = "White";
		}

		int choice = JOptionPane.showOptionDialog(this,"Checkmate! " + winner + " wins!", "Game Over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,  UIData.BK, options, null);
	}

	public void handleCastling(Move move)
	{
		if(move.isKingCastle())
		{
			if(gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
			{
				this.makeMove(UIData.ROOK_KINGSIDE_CASTLING_W);
			}
			else
			{
				this.makeMove(UIData.ROOK_KINGSIDE_CASTLING_B);
			}
		}
		else
		{
			if(gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
			{
				this.makeMove(UIData.ROOK_QUEENSIDE_CASTLING_W);
			}
			else
			{
				this.makeMove(UIData.ROOK_QUEENSIDE_CASTLING_B);
			}
		}
	}

	public void makeMove(Move move)
	{
		try
		{
			gameManager.makeMove(move);
		}
		catch (GameOverException goe)
		{
			// Game was over
			System.out.println("Game over!");
		}

		board.makeMove(move, gameManager.getActiveColorByte());
		this.setHistory();
	}

	public void setHistory()
	{
		String history = "";

		for(Move move_history : gameManager.getMoveHistory())
		{
			history += Parser.moveToString(move_history) + "\n";
		}

		sidePanel.setHistory(history);
	}

	public JButton getExit()
	{
		return sidePanel.getExit();
	}

	public GameManager getGameManager()
	{
		return gameManager;
	}


	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getSource()==sidePanel.getMoveInput())
		{
			sidePanel.setMoveInput("");
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}


}
