package Chess.UI;

import Chess.Game.*;
import Chess.Utils.Parser;
//import com.apple.eawt.Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class GamePanel extends JFrame implements ActionListener, MouseListener
{
	private GameManager gameManager;

	private JPanel contentPanel;
	private Board board;
	private SidePanel sidePanel;
	private MainFrame mainFrame;

	public GamePanel(GameManager gameManager, MainFrame mainFrame)
	{
		super("Chezz!");
		this.gameManager = gameManager;
		this.mainFrame = mainFrame;


		//
		contentPanel = new JPanel();
		board = new Board(this);
		sidePanel = new SidePanel(this);


		contentPanel.setBackground(UIData.BACKGROUND_COLOR);
		this.setSize(UIData.GAMEPANEL_DIMENSION);


		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		contentPanel.add(board);
		contentPanel.add(sidePanel);

		this.setContentPane(contentPanel);
		this.initBoard();

		this.setResizable(false);

	}

	/**
	 * Sets all the pieces in the right place at the beginning of the game
	 */
	public void initBoard()
	{
		ArrayList<Piece> pieces;
		pieces = this.gameManager.getAllPieces(PieceData.WHITE_BYTE);

		for(int i= 0; i< UIData.TOTAL_TILES; i++)
		{
			board.setTileIcon(i,null);
		}

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

				if(historyMove.isQueenCastle()| historyMove.isKingCastle())
				{
					this.handleUndoCastling(historyMove);
				}


				board.update(gameManager.getActiveColorByte());

				this.setHistory();

			}
			this.resetHighlight();

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

				this.makeMove(move);

				System.out.println("Move input: " + input);
				sidePanel.setMoveInput("");
				if(move.isKingCastle()|move.isKingCastle())
				{
					handleCastling(move);
				}
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
		else if(gameManager.getChronometer().getTimeWhite() == 0)
		{
			JOptionPane.showConfirmDialog(this,"Game over! Time for player white has run out. Player black wins!", "Game over!", JOptionPane.CLOSED_OPTION,JOptionPane.QUESTION_MESSAGE, UIData.BK);
		}

		else if(gameManager.getChronometer().getTimeBlack() == 0)
		{
			JOptionPane.showConfirmDialog(this,"Game over! Time for player black has run out. Player white wins!", "Game over!", JOptionPane.CLOSED_OPTION,JOptionPane.QUESTION_MESSAGE, UIData.WK);
		}

		else if (gameManager.getCachedMoves().isEmpty())                                            //SELECT SOURCE
		{
			//System.out.println("Active color is: " + PieceData.getColorString(this.gameManager.getActiveColorByte()));
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

					for (int j = 0; j<moves.size();j++)
					{
						//board.setNormalTileColor(move.get2DDst());
						if (i == board.getIndex(moves.get(j).get2DDst()))
						{
							this.makeMove(moves.get(j));
							//this.handleMove(move);


						}

					}
				}
			}
			this.resetHighlight();
			//gameManager.resetCachedMoves();

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
		final int EXIT = 0;
		final int SAVE_EXIT = 1;

		this.resetHighlight();

		Object[] options = {"Exit", "Save & Exit"};
		String winner;
		ImageIcon winnerIcon = null;

		if (gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
		{
			winner = "Black";
			winnerIcon = UIData.BK;
		}
		else
		{
			winner = "White";
			winnerIcon = UIData.WK;
		}

		int choice = JOptionPane.showOptionDialog(this, "Checkmate! " + winner + " wins!", "Game Over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, winnerIcon, options, null);

		switch (choice)
		{
			case EXIT:
				mainFrame.actionPerformed(new ActionEvent(getExit(), 1, "Exit"));
				break;
			case SAVE_EXIT:
				Parser.saveToFile(gameManager.getMoveHistory(), this);
				mainFrame.actionPerformed(new ActionEvent(getExit(), 1, "Exit"));
				break;
		}
	}

	public void handleCastling(Move move)
	{
		if(move.isKingCastle())
		{
			if(gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
			{
				board.makeMove(UIData.ROOK_KINGSIDE_CASTLING_W,gameManager);
			}
			else
			{
				board.makeMove(UIData.ROOK_KINGSIDE_CASTLING_B, gameManager);
			}
		}
		else
		{
			if(gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
			{
				board.makeMove(UIData.ROOK_QUEENSIDE_CASTLING_W,gameManager);
			}
			else
			{
				board.makeMove(UIData.ROOK_QUEENSIDE_CASTLING_B,gameManager);
			}
		}
	}

	public void handleUndoCastling(HistoryMove historyMove)
	{
		int src, dst;
		if(historyMove.isKingCastle())
		{
			if(gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
			{
				src = UIData.ROOK_KINGSIDE_CASTLING_B.getSrc();
				dst = UIData.ROOK_KINGSIDE_CASTLING_B.getDst();
			}
			else
			{
				src = UIData.ROOK_KINGSIDE_CASTLING_W.getSrc();
				dst = UIData.ROOK_KINGSIDE_CASTLING_W.getDst();
			}
		}
		else
		{
			if(gameManager.getActiveColorByte() == PieceData.WHITE_BYTE)
			{
				src = UIData.ROOK_QUEENSIDE_CASTLING_B.getSrc();
				dst = UIData.ROOK_QUEENSIDE_CASTLING_B.getDst();
			}
			else
			{
				src = UIData.ROOK_QUEENSIDE_CASTLING_W.getSrc();
				dst = UIData.ROOK_QUEENSIDE_CASTLING_W.getDst();
			}
		}

		board.setPiece(gameManager.get(src));
		board.setPiece(gameManager.get(dst));
	}

	public void makeMove(Move move)
	{
		gameManager.makeMove(move);

		board.makeMove(move, gameManager);

		this.setHistory();

		this.handleMove(move);

	}

	public void handleMove(Move move)
	{
		if (gameManager.isCheckMate(gameManager.getActiveColorByte()))
		{
			this.handleCheckMate();
		}
		if(gameManager.isStaleMate(gameManager.getActiveColorByte()))
		{
			this.resetHighlight();
			JOptionPane.showConfirmDialog(this,"Stalemate! It's a draw.","Game over!",JOptionPane.CLOSED_OPTION,JOptionPane.QUESTION_MESSAGE,UIData.BK );
		}
		if (move.isPromotion())
		{
			board.getTile(board.getIndex(move.get2DSrc())).setIcon(this.handlePromotion(move.get2DSrc()));
		}
		if(move.isKingCastle()|move.isQueenCastle())
		{
			this.handleCastling(move);
		}
	}

	public void setHistory()
	{
		String history = "";
		ArrayList<HistoryMove> move_history = gameManager.getMoveHistory();
		int startIndex;

		if(move_history.size()>UIData.MAX_HISTORY_ELEMENTS)
		{
			startIndex = move_history.size()-UIData.MAX_HISTORY_ELEMENTS;
		}
		else
		{
			startIndex = 0;
		}

		for(int i= startIndex; i<move_history.size();i++)
		{
			history += (i+1) + ": " + Parser.moveToString(move_history.get(i)) + "\n";
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

	public void refreshTimePanel(long timelimit_W, long timelimit_B)
	{
		sidePanel.refreshTimePanel(timelimit_W, timelimit_B);
	}

	public void resetHighlight()
	{
		ArrayList<Move> moves;

		moves = gameManager.getCachedMoves();

		if(!moves.isEmpty())
		{
			board.setNormalTileColor(moves.get(0).get2DSrc());

			for (Move move : moves)
			{
				board.setNormalTileColor(move.get2DDst());
			}

			gameManager.resetCachedMoves();
		}
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
