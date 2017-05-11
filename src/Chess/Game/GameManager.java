package Chess.Game;

import Chess.Athena.AIPlayer;
import Chess.Exceptions.Unchecked.IllegalPieceException;
import Chess.Exceptions.Unchecked.IllegalSideException;
import Chess.Exceptions.Unchecked.KingNotFoundException;
import Chess.Time.Chronometer;
import Chess.UI.GamePanel;
import Chess.Utils.SettingsObject;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;

/**
 * @author Thomas
 * @since 21/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */
public class GameManager
{
	/**
	 * Stores the players, index 0 = White, index 1 = Black
	 */
	private Player[] players;
	private ArrayList <HistoryMove> moveHistory;
	private ArrayList <Move> cachedMoves;
	private int activeColor;
	private ChessBoard cb;
	private SettingsObject so;

	private Chronometer chronometer;


	public GameManager ()
	{
		this.cb = new ChessBoard();
		this.activeColor = PieceData.WHITE_BYTE;
		this.players = new Player [2];
		this.moveHistory = new ArrayList <HistoryMove> ();
		this.cachedMoves = new ArrayList <Move> ();
		this.so = new SettingsObject();
		this.chronometer = new Chronometer();
		//this.chronometer.disable();
	}

	public GameManager (SettingsObject so)
	{
		this.cb = new ChessBoard();
		this.activeColor = PieceData.WHITE_BYTE;
		this.players = new Player [2];
		this.moveHistory = new ArrayList <HistoryMove> ();
		this.cachedMoves = new ArrayList <Move> ();
		this.so = new SettingsObject(so);
		this.chronometer = new Chronometer();
		//this.chronometer.disable();
	}

	public GameManager (GameManager gm)
	{
		this.cb = new ChessBoard();
		this.activeColor = gm.activeColor;
		this.players = new Player [2];
		this.moveHistory = new ArrayList <HistoryMove> ();
		this.cachedMoves = new ArrayList <Move> ();
		this.chronometer = new Chronometer(gm.chronometer);
		//this.chronometer.disable();

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				this.cb.set(i,j,gm.get(i,j).getDataByte());
			}
		}

		System.arraycopy(gm.players, 0, this.players, 0, gm.players.length);
		this.moveHistory.addAll(gm.moveHistory);
		this.cachedMoves.addAll(gm.cachedMoves);
		this.so = new SettingsObject(gm.so);
	}

	/**
	 * Sets the white player's type
	 * WARNING: This resets the player, an AI player will lose it's stored data like hashtables and search trees
	 * @param human true sets the player to be human, false sets the player to be AI-controlled
	 */
	public void setWhitePlayerType (boolean human)
	{
		if (human)
		{
			this.players[0] = new HumanPlayer (PieceData.WHITE_BYTE);
		}
		else
		{
			this.players[0] = new AIPlayer (PieceData.WHITE_BYTE, 20);
		}
	}

	/**
	 * Sets the black player's type
	 * WARNING: This resets the player, an AI player will lose it's stored data like hashtables and search trees
	 * @param human true sets the player to be human, false sets the player to be AI-controlled
	 */
	public void setBlackPlayerType (boolean human)
	{
		if (human)
		{
			this.players[1] = new HumanPlayer (PieceData.BLACK_BYTE);
		}
		else
		{
			this.players[1] = new AIPlayer (PieceData.BLACK_BYTE, 20);
		}
	}

	/**
	 * Toggles the active player
	 */
	private void toggleActivePlayer ()
	{
		//System.out.println("Toggling active playah");
		if (this.activeColor == PieceData.WHITE_BYTE)
		{
			this.activeColor = this.activeColor << 1;
		}
		else
		{
			this.activeColor = this.activeColor >> 1;
		}
	}

	/**
	 * Returns the color byte of the currently active player
	 * @return the color byte of the player who's turn it is
	 */
	public int getActiveColorByte ()
	{
		return this.activeColor;
	}

	/**
	 * Initializes the game to it's starting position/situation
	 */
	public GameManager init ()
	{
		this.cb.init();
		this.activeColor = PieceData.WHITE_BYTE;
		this.players[0] = new HumanPlayer(PieceData.WHITE_BYTE);
		this.players[1] = new HumanPlayer(PieceData.BLACK_BYTE);


		return this;
	}

	/**
	 * Returns the piece at the specified 0x88 position
	 * @param index0x88 The desired index in 0x88 notation.
	 * @return The piece at the specified 0x88 index
	 */
	public Piece get (int index0x88)
	{
		return new Piece (this.cb.get(index0x88), index0x88);
	}

	/**
	 * Returns the piece at the specified file and rank
	 * @param file	The file of the piece
	 * @param rank	The rank of the piece
	 * @return	The piece at coordinate file-rank
	 */
	public Piece get (int file, int rank)
	{
		return new Piece(this.cb.get(file, rank), ChessBoard.get0x88Index(file, rank));
	}

	/**
	 * Returns the piece at the specified file and rank
	 * @param coord a 2-element array, indexed file-first
	 * @return	The piece at coordinate file-rank
	 */
	public Piece get (int[] coord)
	{
		return new Piece(this.cb.get(coord[0], coord[1]), ChessBoard.get0x88Index(coord[0], coord[1]));
	}

	/**
	 * Returns all pieces of a specific color. Intended to replace the getAllWhitePieces() and getAllBlackPieces() methods
	 * @param colorByte the color to be retrieved
	 * @return	An ArrayList of pieces of the specified color
	 */
	public ArrayList<Piece> getAllPieces (int colorByte)
	{
		//System.out.println("[getAllPieces] called for " + PieceData.getColorString(colorByte));
		ArrayList<Piece> pieces = new ArrayList <Piece> (16);

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (this.get(i,j).getColor() == colorByte)
				{
					pieces.add(new Piece (this.cb.get(i, j), i, j));
				}
			}
		}

		//System.out.println("[getAllPieces] Found " + Integer.toString(pieces.size()) + " pieces");

		return pieces;
	}

	/**
	 * Requires flags to be set on the given move
	 * @param pieceByte
	 * @param color
	 * @param m
	 * @return
	 */
	public boolean isValidMove (int pieceByte, int color, Move m)
	{
		switch(pieceByte & PieceData.PIECE_MASK)
		{
			case PieceData.PAWN_BYTE:
				return this.isValidPawnMove(color, m);

			case PieceData.ROOK_BYTE:
				return this.isValidRookMove(m);

			case PieceData.KNIGHT_BYTE:
				return true;

			case PieceData.BISHOP_BYTE:
				//System.out.println("[isValidMove()] Bishop");
				return this.isValidBishopMove(m);

			case PieceData.QUEEN_BYTE:
				//System.out.println("[isValidMove()] Queen");
				//System.out.println("[isValidMove] isValidRookMove: " + Boolean.toString(this.isValidRookMove(m)) + " isValidBishopMove: " + this.isValidBishopMove(m) +  " for move: " + m.toString());
				return this.isValidRookMove(m) || this.isValidBishopMove(m);

			case PieceData.KING_BYTE:
				return this.isValidKingMove(color, m);

			default:
				throw new IllegalPieceException(Integer.toString(pieceByte) + " is not a valid pieceByte");
		}
	}

	/**
	 * Checks if the specified move is a valid bishop-type move. Does not verify if the given piece is actually a bishop
	 * @param m The move to be checked
	 * @return True if and only if the move is a valid bishop-type move, otherwise false
	 */
	private boolean isValidBishopMove (Move m)
	{
		//System.out.println("[isValidBishopMove()] chessboard: \n" + this.cb.toString());
		//System.out.println("[isValidBishopMove]\tMove: " + m.toString());
		int deltaRank = (m.getDst() >> 4) - (m.getSrc() >> 4);
		int deltaFile = (m.getDst() & PieceData.PIECE_MASK) - (m.getSrc() & PieceData.PIECE_MASK);

		if (abs(deltaRank) != abs(deltaFile))
		{
			//System.out.println("deltaRank != deltaFile");
			return false;
		}
		else if (deltaRank == 0)
		{
			//System.out.println("deltaRank == 0");
			return false;
		}
		else
		{
			int rankDir = deltaRank / abs(deltaRank);
			int fileDir = deltaFile / abs(deltaFile);

			for (int i = 1; i < abs(deltaRank); i++)
			{
				int file = m.get2DSrc()[0] + (fileDir * i);
				int rank = m.get2DSrc()[1] + (rankDir * i);

				//System.out.println("[isValidBishopMove()] Checking file: " + file + " rank: " + rank + " result: " + this.get(file, rank).isEmpty());

				//System.out.println("Chessboard: \n" + this.cb.toString());

				if (!this.get(file, rank).isEmpty())
				{
					//System.out.println("Piece in the way on " + (char)(file + 'a') + (rank + 1));
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Checks if the move is a valid Rook-type move
	 * @param m The move to be checked
	 * @return	True if and only if the move is a valid Rook-type move, otherwise false
	 */
	private boolean isValidRookMove(Move m)
	{
		int deltaRank = (m.getDst() >> 4) - (m.getSrc() >> 4);
		int deltaFile = (m.getDst() & PieceData.PIECE_MASK) - (m.getSrc() & PieceData.PIECE_MASK);

		if ((abs(deltaFile) != 0) && (abs(deltaRank) != 0))
		{
			return false;
		}
		else
		{
			if (abs(deltaFile) != 0)
			{
				int fileDir = deltaFile / abs(deltaFile);

				for (int i = 1; i < abs(deltaFile); i++)
				{
					int file = m.get2DSrc()[0] + (i * fileDir);
					int rank = m.get2DSrc()[1];

					if (!this.get(file,rank).isEmpty())
					{
						return false;
					}
				}
			}
			else if (abs(deltaRank) != 0)
			{
				int rankDir = deltaRank / abs(deltaRank);

				for (int i = 1; i < abs(deltaRank); i++)
				{
					int file = m.get2DSrc()[0];
					int rank = m.get2DSrc()[1] + (i * rankDir);

					if (!this.get(file,rank).isEmpty())
					{
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Checks wheter the specified move is a valid pawn-type move
	 * @param color	The color of the pawn that's moving
	 * @param m The move a pawn wishes to make
	 * @return True if and oly if the move is a valid pawn-type move, otherwise false
	 */
	private boolean isValidPawnMove (int color, Move m)
	{
		//System.out.println("Checking: " + m.toString());
		//System.out.println("Src: " + this.get(m.getSrc()).getDataByte());
		//System.out.println("Dst: " + this.get(m.getDst()).getDataByte());

		int initRank = 1;
		int colorMult = 1;
		int deltaRank = abs((m.getDst() >> 4) - (m.getSrc() >> 4));
		int deltaFile = abs((m.getDst() & PieceData.PIECE_MASK) - (m.getSrc() & PieceData.PIECE_MASK));

		if (color == PieceData.BLACK_BYTE)
		{
			initRank = 6;
			colorMult = -1;
		}

		//System.out.println("");

		if (this.isCapture(color, m))
		{
			if (this.get(m.get2DDst()).getColor() != PieceData.getOpponentColor(color))
			{
				//System.out.println("Dst Color: " + Integer.toBinaryString(this.get(m.get2DDst()).getColor()));
				//System.out.println(m.toString() + " does not end on opponent's square");
				return false;
			}
		}

		if ((!this.get(m.get2DDst()).isEmpty()) && (!this.isCapture(color, m)))
		{
			//System.out.println("Destination isn't empty");
			return false;
		}

		if (deltaRank == 2)
		{
			// It's a double move and the pawn is still on it's initial rank
			if ((m.getSrc() >> 4) != initRank)
			{
				//System.out.println(m.toString() + " is double moving outside first rank.");
				return false;
			}

			if ((this.get(m.get2DSrc()[0], m.get2DSrc()[1] + (colorMult))).getPieceByte() != PieceData.EMPTY_BYTE)
			{
				//System.out.println("Piece in the way of double move: " + this.get(m.get2DSrc()[0], m.get2DSrc()[1] + (1 * colorMult)));
				return false;
			}
		}

		// moving across files => capture
		if (deltaFile > 0)
		{
			if (this.get(m.get2DDst()).getColor() == color)
			{
				//System.out.println(m.toString() + " is trying to capture own color");
				return false;
			}
			else if (this.get(m.get2DDst()).getPieceByte() == PieceData.EMPTY_BYTE)
			{
				//System.out.println("Trying to capture empty sqaure");
				return false;
			}
		}

		//System.out.println("valid");
		return true;
	}

	/**
	 * Checks whether or not a move is a valid king-type move. This entails checking if the move will check the king.
	 * @param color	The color of the king who's moving
	 * @param m	The move to be analyzed
	 * @return True of and only if the specified move is a valid king-type move, otherwise false.
	 */
	private boolean isValidKingMove(int color, Move m)
	{
		int opponentColor = PieceData.getOpponentColor(color);
		int from = m.getSrc();
		int to = m.getDst();
		int tmp = 0;	// todo: look into usage of tmp
		Piece p;


		if (color == PieceData.WHITE_BYTE)
		{
			for (int i = 0; i < Movesets.PAWN_CAPTURE_WHITE.length; i++)
			{
				if ((p = this.get((to + Movesets.PAWN_CAPTURE_WHITE[i]))).getColor() == opponentColor)
				{
					if (p.getPieceByte() == PieceData.PAWN_BYTE)
					{
						return false;
					}
				}
			}
		}
		else if (color == PieceData.BLACK_BYTE)
		{
			for (int i = 0; i < Movesets.PAWN_CAPTURE_BLACK.length; i++)
			{
				if ((p = this.get((to + Movesets.PAWN_CAPTURE_BLACK[i]))).getColor() == opponentColor)
				{
					if (p.getPieceByte() == PieceData.PAWN_BYTE)
					{
						return false;
					}
				}
			}
		}

		for (int i = 0; i < Movesets.ROOK_MOVE.length; i++)
		{
			if (((tmp = to + Movesets.ROOK_MOVE[i]) & 0x88) == 0)
			{
				if (this.get(tmp).getColor() == opponentColor)
				{
					if (this.get(tmp).getPieceByte() == PieceData.ROOK_BYTE)
					{
						if(this.isValidRookMove(new Move (tmp, to, 0x0)))
						{
							return false;
						}
					}
				}
			}
		}

		for (int i = 0; i < Movesets.KNIGHT_MOVE.length; i++)
		{
			if (((tmp = to + Movesets.KNIGHT_MOVE[i]) & 0x88) == 0)
			{
				if (this.get(tmp).getColor() == opponentColor)
				{
					if (this.get(tmp).getPieceByte() == PieceData.KNIGHT_BYTE)
					{
						return false;
					}
				}
			}
		}

		for (int i = 0; i < Movesets.BISHOP_MOVE.length; i++)
		{
			if (((tmp = to + Movesets.BISHOP_MOVE[i]) & 0x88) == 0)
			{
				if (this.get(tmp).getColor() == opponentColor)
				{
					if (this.get(tmp).getPieceByte() == PieceData.BISHOP_BYTE)
					{
						if(this.isValidBishopMove(new Move (tmp, to, 0x0)))
						{
							return false;
						}
					}
				}
			}
		}

		for (int i = 0; i < Movesets.QUEEN_MOVE.length; i++)
		{
			if (((tmp = to + Movesets.QUEEN_MOVE[i]) & 0x88) == 0)
			{
				if (this.get(tmp).getColor() == opponentColor)
				{
					if (this.get(tmp).getPieceByte() == PieceData.QUEEN_BYTE)
					{
						if ((this.isValidRookMove(new Move (tmp, to, 0x0)) || this.isValidBishopMove(new Move (tmp, to, 0x0))))
						{
							return false;
						}
					}
				}
			}
		}

		for (int i = 0; i < Movesets.KING_MOVE.length; i++)
		{
			if (((tmp = to + Movesets.KING_MOVE[i]) & 0x88) == 0)
			{
				if (this.get(tmp).getColor() == opponentColor)
				{
					if (this.get(tmp).getPieceByte() == PieceData.KING_BYTE)
					{
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Returns true if the move captures a piece
	 * @param m the move we're trying to make
	 * @return True if the destination square is inhabited by an enemy piece
	 */
	private boolean isCapture (int color, Move m)
	{
		//System.out.println("[isCapture] Color: " + Integer.toBinaryString(color) + " Move: " + m.toString());
		if (this.get(m.getSrc()).getPieceByte() != PieceData.PAWN_BYTE)
		{
			return this.get(m.getDst()).getColor() == PieceData.getOpponentColor(color);
		}
		else if (this.get(m.getSrc()).getPieceByte() == PieceData.PAWN_BYTE)
		{
			if (m.get2DSrc()[0] != m.get2DDst()[0])
			{
				return this.get(m.getDst()).getColor() == PieceData.getOpponentColor(color);
			}
		}

		return false;
	}

	/**
	 * Returns true if the move involves a pawn moving to the back rank
	 * @param m The move to be analyzed
	 * @return true if the pawn reaches the back rank
	 */
	private boolean isPromo (int color, Move m)
	{
		if (color == PieceData.WHITE_BYTE)
		{
			return (m.get2DDst()[1] == 7)  && (this.get(m.get2DSrc()).getPieceByte() == PieceData.PAWN_BYTE);
		}
		else if (color == PieceData.BLACK_BYTE)
		{
			return (m.get2DDst()[1] == 0)  && (this.get(m.get2DSrc()).getPieceByte() == PieceData.PAWN_BYTE);
		}
		else
		{
			throw new IllegalSideException(Integer.toString(color) + " is not a valid color");
		}
	}

	/**
	 * Returns true if the move steps on a piece of it's own side
	 * @param color the color of the moving piece
	 * @param m The move to be analyzed
	 * @return true if the destination square is owned by color
	 */
	private boolean isCollision (int color, Move m)
	{
		return this.get(m.get2DDst()).getColor() == color;
	}

	/**
	 * Sets the capture and promo flags for a given move.
	 * @param color The color that whishes to make the move
	 * @param m the move to be checked
	 * @return the move given, but with appropriate flags set
	 */
	public Move setFlags (int color, Move m)
	{
		//System.out.println("[setFlags] Color: " + Integer.toBinaryString(color) + " Move: " + m.toString());
		int initRank = 1;
		int deltaRank = abs((m.getDst() >> 4) - (m.getSrc() >> 4));
		Piece p = this.get(m.getSrc());

		if (color == PieceData.BLACK_BYTE)
		{
			initRank = 6;
		}

		if (this.isCapture(color, m))
		{
			//System.out.println(m.toString() + " set capture flag");
			m.setCapture();
		}

		if (this.isPromo(color, m))
		{
			//System.out.println(m.toString() + " set promo flag");
			m.setPromo();
		}

		if ((deltaRank == 2) && ((m.getSrc() >> 4) == initRank) && ((this.get(m.get2DDst()[0], m.get2DDst()[1] + 1)).getPieceByte() == PieceData.EMPTY_BYTE))
		{
			m.setDoublePawnPush();
		}

		if (p.getPieceByte() == PieceData.KING_BYTE)
		{
			// Piece is a king
			if (m.getSrc() - 0x00000002 == m.getDst())
			{
				m.setQueenSideCastle();
			}
			else if (m.getSrc() + 0x00000002 == m.getDst())
			{
				m.setKingSideCastle();
			}
		}

		return m;
	}

	public boolean isLegalMove (Piece p, Move m)
	{
		//System.out.println("[isLegalMove] called for " + p.toString() + " making move " + m.toString());

		if (this.isAlmostLegalMove(p,m))
		{
			/*
			if (p.getPieceByte() == PieceData.KING_BYTE)
			{
				System.out.println("[isLegalMove()]\tAnalyzing " + m.toString() + " by " + p.toString());
			}
			*/
			int capturedPiece = PieceData.EMPTY_BYTE;

			if (this.isCapture(p.getColor(), m))
			{
				capturedPiece = this.get(m.getDst()).getDataByte();
			}

			boolean check = false;

			if (this.get(m.getDst()).getPieceByte() != PieceData.KING_BYTE)
			{
				/*
				if (p.getPieceByte() == PieceData.KING_BYTE)
				{
					System.out.println("[isLegalMove()]\tMove didn't end on a king ");
				}
				*/

				int piece = p.getDataByte();

				this.cb.set(m.getDst(), piece);
				this.cb.set(m.getSrc(), PieceData.EMPTY_BYTE);

				check = this.isCheck(p.getColor());

				this.cb.set(m.getDst(), capturedPiece);
				this.cb.set(m.getSrc(), piece);

				//System.out.println("[isLegalMove] Move: " + m.toString() + " checkMate: " + check);
			}

			/*
			if (p.getPieceByte() == PieceData.KING_BYTE)
			{
				System.out.println("[isLegalMove()]\t" + m.toString() +  " leaves the king in check: " + Boolean.toString(check));
			}
			*/

			//System.out.println("Move: " + m.toString() + " check: " + Boolean.toString(check));
			return !check;
		}
		else
		{
			return false;
		}
	}

	private boolean isAlmostLegalMove (Piece p, Move m)
	{
		//System.out.println("[isAlmostLegalMove] checking " + m.toString() + " for " + p.toString());
		//System.out.println("Checking " + p.toString() + " pieceByte: " + Integer.toBinaryString(p.getPieceByte()));
		//int src = m.getSrc();
		//int srcFile = m.get2DSrc()[0];
		//int srcRank = m.get2DSrc()[1];
		int dst = m.getDst();
		int src = m.getSrc();
		//int dstFile = m.get2DSrc()[0];
		//int dstRank = m.get2DSrc()[1];
		int color = p.getColor();
		int piece = p.getPieceByte();

		m = this.setFlags(p.getColor(), m);

		// You can't end on one of your own pieces
		if (this.isCollision(color, m))
		{
			//System.out.println("[isAlmostLegalMove] " + m.toString() + " collided");
			return false;
		}

		if ((this.isCapture(p.getColor(), m)) && (this.get(dst).getPieceByte() == PieceData.KING_BYTE))
		{
			//System.out.println("[isAlmostLegalMove] " + m.toString() + " captures king");
			return false;
		}

		if (m.isKingCastle())
		{
			System.out.println("Analyzing King-side castle");
			ArrayList <Piece> pieces = this.getAllPieces(p.getColor());
			ArrayList <Piece> rooks = new ArrayList<>(2);

			for (Piece r : pieces)
			{
				if (r.getPieceByte() == PieceData.ROOK_BYTE)
				{
					rooks.add(r);
				}
			}

			if (rooks.size() == 0)
			{
				return false;
			}

			for (Piece rook : rooks)
			{
				if (rook.hasMoved())
				{
					// Rook moved, castling no longer possible
					//System.out.println("[K] Rook moved, no castling possible");
					return false;
				}
				else
				{
					if (p.hasMoved())
					{
						// King move, castling no longer possible
						System.out.println("[isAlmostLegalMove()][K] King moved, no castling possible");
						return false;
					}
					else
					{
						if ((this.get(src + 0x0001).getPieceByte() != PieceData.EMPTY_BYTE) || (this.get(src + 0x0002).getPieceByte() != PieceData.EMPTY_BYTE))
						{
							// One of squares between king and rook is occupied
							//System.out.println("[K] One of the spaces is occupied, no castling possible");
							return false;
						}
						else
						{
							if ((this.isAttacked(color, src + 0x0001) > 0) || (this.isAttacked(color, src + 0x0002) > 0))
							{
								// One of the squares was attacked
								//System.out.println("[K] One of the spaces is attacked, no castling possible");
								return false;
							}
						}
					}
				}
			}
		}
		else if (m.isQueenCastle())
		{
			System.out.println("Analyzing Queen-side castle");
			ArrayList <Piece> pieces = this.getAllPieces(p.getColor());
			ArrayList <Piece> rooks = new ArrayList<>(2);

			for (Piece r : pieces)
			{
				if (r.getPieceByte() == PieceData.ROOK_BYTE)
				{
					rooks.add(r);
				}
			}

			if (rooks.size() == 0)
			{
				// No more rooks left to castle with
				return false;
			}

			for (Piece rook : rooks)
			{
				if (rook.hasMoved())
				{
					// Rook moved, castling no longer possible
					//System.out.println("[Q] Rook moved, no castling possible");
					return false;
				}
				else
				{
					if (p.hasMoved())
					{
						// King move, castling no longer possible
						System.out.println("[isAlmostLegalMove()][Q] King moved, no castling possible");
						return false;
					}
					else
					{
						if ((this.get(src - 0x0001).getPieceByte() != PieceData.EMPTY_BYTE) || (this.get(src - 0x0002).getPieceByte() != PieceData.EMPTY_BYTE) || (this.get(src - 0x0003).getPieceByte() != PieceData.EMPTY_BYTE))
						{
							// One of squares between king and rook
							//System.out.println("[Q] Squares between king and rook occupied, no castling possible");
							return false;
						}
						else
						{
							if ((this.isAttacked(color, src - 0x0001) > 0) || (this.isAttacked(color, src - 0x0002) > 0) || ((this.isAttacked(color, src - 0x0003) > 0)))
							{
								// One of the squares was attacked
								//System.out.println("[Q] Squares between king and rook attacked, no castling possible");
								return false;
							}
						}
					}
				}
			}
		}

		return this.isValidMove(piece, color, m);
	}

	private ArrayList <Move> getAlmostLegalMoves (Piece p)
	{
		//System.out.println("[getAlmostLegalMoves] called for " + p.toString());
		ArrayList <Move> moves = p.getAllPossibleMoves();
		ArrayList <Move> removeQueue = new ArrayList<Move>();

		for (int i = 0; i < moves.size(); i++)
		{
			Move m = moves.get(i);

			if (!this.isAlmostLegalMove(p, m))
			{
				removeQueue.add(m);
			}
			else
			{
				moves.set(i, this.setFlags(p.getColor(), m));
			}
		}

		moves.removeAll(removeQueue);

		return moves;
	}

	private ArrayList <Move> getAllAlmostLegalMoves (int color)
	{
		//System.out.println("[getAllAlmostLegalMoves] called for " + PieceData.getColorString(color));
		ArrayList <Move> moves = new ArrayList<Move>();

		for (Piece p : this.getAllPieces(color))
		{
			moves.addAll(this.getAlmostLegalMoves(p));
		}

		return moves;
	}

	/**
	 * Returns all valid moves for a specified piece
	 * @param p The piece whose moves are being requested
	 * @return an ArrayList with all valid & legal moves
	 */
	public ArrayList<Move> getLegalMoves (Piece p)
	{
		//System.out.println("[getLegalMoves()] requested legal moves for " + p.toString());
		int color = p.getColor();
		//TableRecord tr = null;

		/*
		if (TranspositionTable.getInstance().get(this.hash) != null)
		{

		}
		else*/
		if ((p.getPieceByte() != PieceData.EMPTY_BYTE) && (color == this.activeColor))
		{
			//System.out.println("NEW");
			//int piece = p.getPieceByte();

			//System.out.println("[getLegalMoves] chessboard: \n" + this.cb.toString());

			ArrayList<Move> possibleMoves = p.getAllPossibleMoves();

			//System.out.println("[getLegalMoves()] Generated " + Integer.toString(possibleMoves.size()) + " possible moves.");

			for (int i = 0; i < possibleMoves.size(); i++)
			{
				possibleMoves.set(i, this.setFlags(color, possibleMoves.get(i)));

				Move m = possibleMoves.get(i);

				//System.out.println("Analyzing " + m.toString());

				if (!this.isLegalMove(p, m))
				{
					//System.out.println("[getLegalMoves()]\tremoving: " + m.toString());
					possibleMoves.remove(i);
					i--;
				}
			}

			/*
			if (p.getPieceByte() == PieceData.KING_BYTE)
			{
				//System.out.println("[getLegalMoves()]\tApproved moves: ");

				for (Move m : possibleMoves)
				{
					System.out.println("[getLegalMoves()]\t" + m.toString());
				}
			}
			*/

			Collections.sort(possibleMoves);

			/*System.out.println("Generated:");
			for(Move m : possibleMoves)
			{
				System.out.println(m.toString());
			}*/

			return possibleMoves;
		}
		else
		{
			return new ArrayList<Move>();
		}
	}

	/**
	 * Returns all possible (non-legal) moves
	 * @param p The piece for which we wish to generate all possible moves
	 * @return An ArrayList containing all moves the piece can make (legal or illegal)
	 */
	public ArrayList <Move> getPseudoLegalMoves (Piece p)
	{
		ArrayList <Move> moves = p.getAllPossibleMoves();

		for (int i = 0; i < moves.size(); i++)
		{
			Move m = moves.get(i);

			if (this.isCollision(p.getColor(), m))
			{
				moves.remove(i);
				i--;
			}
			else
			{
				moves.set(i, this.setFlags(p.getColor(), m));
			}
		}

		Collections.sort(moves);

		return moves;
	}

	/**
	 * Makes the specified move
	 * @param m The move to be made
	 * @return itself after making the move
	 */
	public GameManager makeMove (Move m)
	{
		//System.out.println("[makeMove] attempting to make move");
		//System.out.println("[makeMove] chronometer running = " + chronometer.isRunning());
		if (this.chronometer.isRunning())
		{
			int color = this.activeColor;

			if (m.isKingCastle())
			{
				ArrayList<Piece> pieces = this.getAllPieces(this.activeColor);
				ArrayList<Piece> rooks = new ArrayList<>(2);

				for (Piece p : pieces)
				{
					if (p.getPieceByte() == PieceData.ROOK_BYTE)
					{
						rooks.add(p);
					}
				}

				for (Piece r : rooks)
				{
					if (r.get2DCoord()[0] == 7)
					{
						this.cb.set(r.getPositionByte() - 0x00000002, r.getDataByte());
						this.cb.set(r.getPositionByte(), PieceData.EMPTY_BYTE);
						this.cb.set(r.getPositionByte() - 0x00000002, r.incMoves().getDataByte());
						//this.get(r.getPositionByte() + 0x0003).incMoves();
						break;

					}
				}
			}
			else if (m.isQueenCastle())
			{
				ArrayList<Piece> pieces = this.getAllPieces(this.activeColor);
				ArrayList<Piece> rooks = new ArrayList<>(2);

				for (Piece p : pieces)
				{
					if (p.getPieceByte() == PieceData.ROOK_BYTE)
					{
						rooks.add(p);
					}
				}

				for (Piece r : rooks)
				{
					if (r.get2DCoord()[0] == 0)
					{
						this.cb.set(r.getPositionByte() + 0x00000003, r.getDataByte());
						this.cb.set(r.getPositionByte(), PieceData.EMPTY_BYTE);
						this.cb.set(r.getPositionByte() + 0x00000003, r.incMoves().getDataByte());
						//this.get(r.getPositionByte() + 0x00000003).incMoves();
						break;

					}
				}
			}

			//this.get(m.getSrc()).incMoves();
			this.cb.set(m.getSrc(), this.get(m.getSrc()).incMoves().getDataByte());

			if (this.isCapture(color, m))
			{
				this.moveHistory.add(new HistoryMove(m, this.get(m.getDst())));
			}
			else
			{
				this.moveHistory.add(new HistoryMove(m));
			}

			//System.out.println("[makeMove()]\tMoving piece: " + this.get(m.getSrc()).toString());

			this.cb.set(m.getDst(), this.cb.get(m.getSrc()));
			this.cb.set(m.getSrc(), PieceData.EMPTY_BYTE);    // Empty the source square

			this.toggleActivePlayer();
			this.chronometer.toggle();

			//System.out.println("[makeMove] MADE MOVE: " + m.toString() + " BY " + this.get(m.getDst()).toString());

			return this;
		}

		//System.out.println("[makeMove] chrono not running, no moves made");

		throw new NullPointerException("You tried to make a move, but the GameManager failed to do so");
	}

	/**
	 * Returns true if there is a move in the history, aka when getLastMove will return a valid move object
	 * @return returns true if and only if there's still a move in the moveHistory
	 */
	public boolean hasLastMove ()
	{
		return this.moveHistory.size() > 0;
	}

	/**
	 * Returns the last move made
	 * If you wish to undo a move, please use the undo() method
	 * @return The last move that was made
	 */
	public HistoryMove getLastMove ()
	{
		if (this.hasLastMove())
		{
			return this.moveHistory.get(this.moveHistory.size() - 1);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Returns the move history
	 * @return an ArrayList containing all moves (in chronological order)
	 */
	public ArrayList<HistoryMove> getMoveHistory ()
	{
		return this.moveHistory;
	}

	/**
	 * copies all moves from the given array into the move cache
	 * @param moves	The moves to be placed in cache
	 */
	public void setCachedMoves (ArrayList <Move> moves)
	{
		this.cachedMoves = new ArrayList<Move>(moves.size());
		this.cachedMoves.addAll(moves);
	}

	/**
	 * empties cachedMoves
	 */
	public void resetCachedMoves()
	{
		this.cachedMoves.clear();
	}

	/**
	 * Returns the moves that were previously cached by setCachedMoves
	 * @return	All moves in the move cache
	 */
	public ArrayList <Move> getCachedMoves ()
	{
		return this.cachedMoves;
	}

	/**
	 * Undoes the last move made
	 */
	public void undo ()
	{
		if ((this.moveHistory.size() > 0) && (this.so.undoEnabled()))
		{
			HistoryMove m = this.getLastMove();

			if (m.isKingCastle())
			{
				//System.out.println("Kingside castling detected");
				ArrayList<Piece> pieces = this.getAllPieces(PieceData.getOpponentColor(this.activeColor));
				ArrayList<Piece> rooks = new ArrayList<>(2);

				for (Piece p : pieces)
				{
					if (p.getPieceByte() == PieceData.ROOK_BYTE)
					{
						rooks.add(p);
					}
				}

				for (Piece r : rooks)
				{
					//System.out.println("Analyzing rook: " + r.toString());
					if (r.get2DCoord()[0] == 5)
					{
						//System.out.println("Putting back rook");
						this.cb.set(r.getPositionByte() + 0x00000002, r.getDataByte());
						this.cb.set(r.getPositionByte(), PieceData.EMPTY_BYTE);
						//this.get(r.getPositionByte() + 0x0002).decMoves();
						this.cb.set(r.getPositionByte() + 0x00000002, r.decMoves().getDataByte());
						break;
					}
				}
			}
			else if (m.isQueenCastle())
			{
				//System.out.println("QueenSideCastle");
				ArrayList<Piece> pieces = this.getAllPieces(PieceData.getOpponentColor(this.activeColor));
				ArrayList<Piece> rooks = new ArrayList<>(2);

				for (Piece p : pieces)
				{
					if (p.getPieceByte() == PieceData.ROOK_BYTE)
					{
						rooks.add(p);
					}
				}

				//System.out.println("Found " + rooks.size() + " rooks");

				for (Piece r : rooks)
				{
					//System.out.println("Rook: " + r.toString());
					if (r.get2DCoord()[0] == 3)
					{
						//System.out.println("Putting back rook");
						this.cb.set(r.getPositionByte() - 0x00000003, r.getDataByte());
						this.cb.set(r.getPositionByte(), PieceData.EMPTY_BYTE);
						this.cb.set(r.getPositionByte() - 0x00000003, r.decMoves().getDataByte());
						break;
					}
				}
			}

			Piece piece = this.get(m.getDst()).decMoves();

			if (m.isPromotion())
			{
				piece = new Piece((piece.getDataByte() & (~PieceData.PIECE_MASK)) | PieceData.PAWN_BYTE, piece.getPositionByte());
			}

			this.cb.set(m.getSrc(), piece.getDataByte());    // Put the piece back on the source square

			if (m.isCapture())
			{
				this.cb.set(m.getDst(), m.getCapturedPiece().getDataByte());
			}
			else
			{
				this.cb.set(m.getDst(), PieceData.EMPTY_BYTE);
			}

			//this.makeMove(new Move (m.getDst(), m.getSrc(), 0x0));		// Making a dummy move which is just the inverse of the last move
			this.moveHistory.remove(this.moveHistory.size() - 1);        // Always remove the last from the move history
			this.toggleActivePlayer();
		}
		else if (!this.so.undoEnabled())
		{
			System.out.println("Undo is not enabled");
		}
	}

	/**
	 * Returns true if the specified colour is check-mate
	 * @param color The colour to be checked
	 * @return true if the player is check-mate, false if he is not
	 */
	public boolean isCheckMate (int color)
	{
		//System.out.println("[isCheckMate] called for " + PieceData.getColorString(color));
		//System.out.println("[isCheckMate] PRE chessboard:\n"  + this.cb.toString());

		if (this.isCheck(color))
		{
			//System.out.println("[isCheckMate] king is in check");
			Piece king = null;
			ArrayList<Piece> pieces = this.getAllPieces(color);

			for (Piece piece : pieces)
			{
				//System.out.println("[isCheckMate] found piece: " + piece.toString());
				if (piece.getPieceByte() == PieceData.KING_BYTE)
				{
					king = piece;
					break;
				}
			}

			if (king != null)
			{
				//pieces.remove(king);

				ArrayList<Move> almostLegalMoves = new ArrayList<Move>();

				for (Piece p : pieces)
				{
					almostLegalMoves.addAll(this.getAlmostLegalMoves(p));
				}

				boolean checkPreventionPossible = false;

				for (Move m : almostLegalMoves)
				{
					//System.out.println("Attempting to resolve check by " + m.toString() + " made by " + this.get(m.getSrc()).toString());
					int capturedPiece = PieceData.EMPTY_BYTE;

					if (m.isCapture())
					{
						capturedPiece = this.get(m.getDst()).getDataByte();
					}

					//System.out.println("Ending on: " + this.get(m.getDst()).toString());

					if (this.get(m.getDst()).getPieceByte() != PieceData.KING_BYTE)
					{
						//System.out.println("Analyzing move: " + m.toString() + " by " + this.get(m.getSrc()).toString());
						int piece = this.get(m.getSrc()).getDataByte();
						this.cb.set(m.getDst(), piece);
						this.cb.set(m.getSrc(), PieceData.EMPTY_BYTE);

						if (!this.isCheck(color))
						{
							//System.out.println("");
							checkPreventionPossible = true;
						}

						this.cb.set(m.getDst(), capturedPiece);
						this.cb.set(m.getSrc(), piece);
					}
				}


				//System.out.println("[isCheckMate] POST chessboard:\n"  + this.cb.toString());
				return !checkPreventionPossible;
			}
			else
			{
				throw new KingNotFoundException("isCheckMate() couldn't find king for " + PieceData.getColorString(color));
			}
		}
		else
		{
			return false;
		}
	}

	/**
	 * Returns true if the specified colour is in checkmate
	 * @param color	the colour to be checked
	 * @return	true if the player is stalemate'd, false if he is not
	 */
	public boolean isStaleMate (int color)
	{
		return (this.getAllLegalMoves(color).size() == 0) && (!this.isCheck(color));
	}

	/**
	 * Returns true if the specified colour is check'ed
	 * @param color	the colour to be checked
	 * @return	true if the player is in check, false if he is not
	 */
	public boolean isCheck (int color)
	{
		//System.out.println("[isCheck] called for " + PieceData.getColorString(color));
		//System.out.println("[isCheck] PRE: chessboard: \n" + this.cb.toString());
		Piece king = null;

		ArrayList <Piece> pieces = this.getAllPieces(color);

		//System.out.println("Pieces: " + pieces.size());

		for (Piece piece : pieces)
		{
			//System.out.println("[isCheck] found piece: " + piece.toString());
			if (piece.getPieceByte() == PieceData.KING_BYTE)
			{
				//System.out.println("King detected");
				king = piece;
				break;
			}
		}

		//System.out.println("[isCheck] POST: chessboard: \n" + this.cb.toString());

		if (king != null)
		{
			//System.out.println("Chessboard:\n" + this.cb.toString());
			//System.out.println("king attacked from " + this.isAttacked(color, king.getPositionByte()) + " sides color: " + PieceData.getColorString(color));
			return (this.isAttacked(color, king.getPositionByte()) >= 1);
		}
		else
		{
			throw new KingNotFoundException("isCheck() couldn't find king for " + PieceData.getColorString(color));
		}
	}

	/**
	 * Returns all the move a particular side can make
	 * @param color The color who's moves are to be acquired
	 * @return All possible moves for the specified color
	 */
	public ArrayList <Move> getAllLegalMoves(int color)
	{
		//System.out.println("[getAllLegalMoves] called for " + PieceData.getColorString(color));
		ArrayList <Piece> pieces = this.getAllPieces(color);
		ArrayList <Move> pieceMoves;
		ArrayList <Move> moves = new ArrayList<>();

		for (Piece p : pieces)
		{
			pieceMoves = this.getLegalMoves(p);

			moves.addAll(pieceMoves);
		}

		return moves;
	}

	public ArrayList <Move> getAllPseudoLegalMoves (int color)
	{
		ArrayList <Piece> pieces = this.getAllPieces(color);
		ArrayList <Move> moves = new ArrayList<Move>(8);

		for (Piece p : pieces)
		{
			moves.addAll(this.getPseudoLegalMoves(p));
		}

		return moves;
	}

	private int isAttacked (int color, int index0x88)
	{
		//System.out.println("[isAttacked] color: " + Integer.toHexString(color));
		int attacked = 0;
		Piece p;

		AttackChecker pawnCapture = null;
		AttackChecker rook = new AttackChecker(Movesets.ROOK_MOVE, PieceData.ROOK_BYTE, color, index0x88, this);
		rook.setName("RookChecker");
		AttackChecker knight = new AttackChecker(Movesets.KNIGHT_MOVE, PieceData.KNIGHT_BYTE, color, index0x88, this);
		knight.setName("KnightChecker");
		AttackChecker bishop = new AttackChecker(Movesets.BISHOP_MOVE, PieceData.BISHOP_BYTE, color, index0x88, this);
		bishop.setName("BishopChecker");
		AttackChecker queen = new AttackChecker(Movesets.QUEEN_MOVE, PieceData.QUEEN_BYTE, color, index0x88, this);
		queen.setName("QueenChecker");
		AttackChecker king = new AttackChecker(Movesets.KING_MOVE, PieceData.KING_BYTE, color, index0x88, this);
		king.setName("KingChecker");

		if (color == PieceData.WHITE_BYTE)
		{
			pawnCapture = new AttackChecker(Movesets.PAWN_CAPTURE_WHITE, PieceData.PAWN_BYTE, color, index0x88, this);
		}
		else if (color == PieceData.BLACK_BYTE)
		{
			pawnCapture = new AttackChecker(Movesets.PAWN_CAPTURE_BLACK, PieceData.PAWN_BYTE, color, index0x88, this);
		}
		pawnCapture.setName("pawnCaptureChecker");

		AttackChecker[] threadArray = new AttackChecker[6];

		threadArray[0] = pawnCapture;
		threadArray[1] = rook;
		threadArray[2] = knight;
		threadArray[3] = bishop;
		threadArray[4] = queen;
		threadArray[5] = king;

		for (AttackChecker attackThread : threadArray)
		{
			attackThread.start();
		}

		try
		{
			for (AttackChecker attackThread : threadArray)
			{
				attackThread.join();

				//System.out.println(attackThread.getName() + " attacking: " + attackThread.getResult());

				if (attackThread.getResult())
				{
					attacked++;
				}
			}
		}
		catch (InterruptedException ie)
		{
			ie.printStackTrace();
		}

		return attacked;
	}

	public void handlePromotion(int[] position, int piece)
	{
		//System.out.println("Promo: (" + Integer.toString(position[0]) + Integer.toString(position[1]) + " piecebyte: " + Integer.toBinaryString(piece));

		this.cb.set(position[0],position[1],piece);
	}

	public Chronometer getChronometer()
	{
		return this.chronometer;
	}

	public void setSettings(SettingsObject settings, GamePanel gamePanel)
	{
		this.so = settings;

		//System.out.println("Setting chronometer to " + settings.getTimeMsW());
		gamePanel.refreshTimePanel(settings.getTimeMsW(),settings.getTimeMsB());
	}

	public void startChronometer()
	{
		this.chronometer.start();
	}

	public void setChronometer(Chronometer chronometer)
	{
		this.chronometer = chronometer;
	}

	public void disableChronometer()
	{
		this.chronometer.disable();
		//System.out.println("Chronometer disabled via gamemanager");
	}

	public SettingsObject getSettings()
	{
		return this.so;
	}
}