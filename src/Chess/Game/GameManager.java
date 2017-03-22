package Chess.Game;

import Chess.Athena.AIPlayer;
import Chess.Athena.TranspositionTable;
import Chess.Exceptions.Unchecked.IllegalSideException;
import Chess.Time.Chronometer;

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
	private ArrayList <Move> moveHistory;
	private ArrayList <Move> cachedMoves;
	private int activeColor;
	private long zobristHash;
	private ChessBoard cb;

	public static Chronometer chronometer = new Chronometer();


	public GameManager ()
	{
		this.cb = new ChessBoard();
		this.activeColor = PieceData.WHITE_BYTE;
		this.zobristHash = 0;
		this.players = new Player [2];
		this.moveHistory = new ArrayList <Move> ();
		this.cachedMoves = new ArrayList <Move> ();

		//GameManager.chronometer.start();
	}

	public GameManager (GameManager gm)
	{
		this.cb = new ChessBoard();
		this.activeColor = gm.activeColor;
		this.zobristHash = gm.zobristHash;
		this.players = new Player [2];
		this.moveHistory = new ArrayList <Move> ();
		this.cachedMoves = new ArrayList <Move> ();

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				this.cb.set(i,j,gm.get(i,j).getPieceByte());
			}
		}

		for (int i = 0; i < gm.players.length; i++)
		{
			this.players[i] = gm.players[i];
		}

		for (int i = 0; i < gm.moveHistory.size(); i++)
		{
			this.moveHistory.add(gm.moveHistory.get(i));
		}

		for (int i = 0; i < gm.cachedMoves.size(); i++)
		{
			this.cachedMoves.add(gm.cachedMoves.get(i));
		}
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
			this.players[0] = new AIPlayer (PieceData.WHITE_BYTE);
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
			this.players[1] = new AIPlayer (PieceData.BLACK_BYTE);
		}
	}

	/**
	 * Toggles the active player
	 */
	public void toggleActivePlayer ()
	{
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
	public void init ()
	{
		this.cb.init();
		this.activeColor = PieceData.WHITE_BYTE;
		this.players[0] = new HumanPlayer(PieceData.WHITE_BYTE);
		this.players[1] = new HumanPlayer(PieceData.BLACK_BYTE);
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
		ArrayList<Piece> pieces = new ArrayList <Piece> (16);

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if ((this.cb.get(i,j) & colorByte) != 0)
				{
					pieces.add(new Piece (this.cb.get(i, j), i, j));
				}
			}
		}

		return pieces;
	}

	/**
	 * Returns an array of all pieces belonging to the white player
	 * @return	All white pieces
	 * @Deprecated Please use getAllPieces() with the correct color byte instead
	 */
	@Deprecated
	public ArrayList<Piece> getAllWhitePieces ()
	{
		ArrayList<Piece> pieces = new ArrayList <Piece> (16);

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if ((this.cb.get(i, j) & PieceData.WHITE_BYTE) != 0)
				{
					pieces.add(new Piece (this.cb.get(i, j), i, j));
				}
			}
		}

		return pieces;
	}

	/**
	 * Returns an array of all pieces belonging to the Black player
	 * @return	All black pieces
	 * @Deprecated Please use getAllPieces() with the correct color byte instead
	 */
	@Deprecated
	public ArrayList<Piece> getAllBlackPieces ()
	{
		ArrayList<Piece> pieces = new ArrayList <Piece> (16);

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if ((this.cb.get(i,j) & PieceData.BLACK_BYTE) != 0)
				{
					pieces.add(new Piece (this.cb.get(i,j), i, j));
				}
			}
		}

		return pieces;
	}

	/**
	 * Checks if the specified move is a valid bishop-type move. Does not verify if the given piece is actually a bishop
	 * @param m The move to be checked
	 * @return True if and only if the move is a valid bishop-type move, otherwise false
	 */
	public boolean isValidBishopMove (Move m)
	{
		//System.out.println("Move: " + m.toString());
		int deltaRank = (m.getDst() >> 4) - (m.getSrc() >> 4);
		int deltaFile = (m.getDst() & PieceData.PIECE_MASK) - (m.getSrc() & PieceData.PIECE_MASK);

		if (abs(deltaRank) != abs(deltaFile))
		{
			return false;
		}
		else if (deltaRank == 0)
		{
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

				if (!this.get(file, rank).isEmpty())
				{
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
	public boolean isValidRookMove(Move m)
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
	public boolean isValidPawnMove(int color, Move m)
	{
		int initRank = 1;
		int deltaRank = abs((m.getDst() >> 4) - (m.getSrc() >> 4));
		int deltaFile = abs((m.getDst() & PieceData.PIECE_MASK) - (m.getSrc() & PieceData.PIECE_MASK));

		if (color == PieceData.BLACK_BYTE)
		{
			initRank = 6;
		}

		if (m.isCapture())
		{
			if (this.get(m.get2DDst()).getColor() != PieceData.getOpponentColor(color))
			{
				//System.out.println(m.toString() + " does not end on opponent's square");
				return false;
			}
		}

		if ((!this.get(m.get2DDst()).isEmpty()) && (!m.isCapture()))
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

			if ((this.get(m.get2DDst()[0], m.get2DDst()[1] + 1)).getPieceWithoutColorByte() != PieceData.EMPTY_BYTE)
			{
				//System.out.println("Piece in the way of double move");
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
		}

		return true;
	}

	/**
	 * Checks whether or not a move is a valid king-type move. This entails checking if the move will check the king.
	 * @param color	The color of the king who's moving
	 * @param m	The move to be analyzed
	 * @return True of and only if the specified move is a valid king-type move, otherwise false.
	 */
	public boolean isValidKingTypeMove (int color, Move m)
	{
		int opponentColor = PieceData.getOpponentColor(color);
		int from = m.getSrc();
		int to = m.getDst();
		int tmp = 0;
		Piece p;

		if (color == PieceData.WHITE_BYTE)
		{
			for (int i = 0; i < Movesets.PAWN_CAPTURE_WHITE.length; i++)
			{
				if ((p = this.get((tmp = to + Movesets.PAWN_CAPTURE_WHITE[i]))).getColor() == opponentColor)
				{
					if (p.getPieceWithoutColorByte() == PieceData.PAWN_BYTE)
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
				if ((p = this.get((tmp = to + Movesets.PAWN_CAPTURE_BLACK[i]))).getColor() == opponentColor)
				{
					if (p.getPieceWithoutColorByte() == PieceData.PAWN_BYTE)
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
					if (this.get(tmp).getPieceWithoutColorByte() == PieceData.ROOK_BYTE)
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
					if (this.get(tmp).getPieceWithoutColorByte() == PieceData.KNIGHT_BYTE)
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
					if (this.get(tmp).getPieceWithoutColorByte() == PieceData.BISHOP_BYTE)
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
					if (this.get(tmp).getPieceWithoutColorByte() == PieceData.QUEEN_BYTE)
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
					if (this.get(tmp).getPieceWithoutColorByte() == PieceData.KING_BYTE)
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
	public boolean isCapture (int color, Move m)
	{
		if (this.get(m.get2DSrc()).getPieceWithoutColorByte() != PieceData.PAWN_BYTE)
		{
			return this.get(m.get2DDst()).getColor() == PieceData.getOpponentColor(color);
		}

		return false;
	}

	/**
	 * Returns true if the move involves a pawn moving to the back rank
	 * @param m The move to be analyzed
	 * @return true if the pawn reaches the back rank
	 */
	public boolean isPromo (int color, Move m)
	{
		if (color == PieceData.WHITE_BYTE)
		{
			return (m.get2DDst()[1] == 7)  && (this.get(m.get2DSrc()).getPieceWithoutColorByte() == PieceData.PAWN_BYTE);
		}
		else if (color == PieceData.BLACK_BYTE)
		{
			return (m.get2DDst()[1] == 0)  && (this.get(m.get2DSrc()).getPieceWithoutColorByte() == PieceData.PAWN_BYTE);
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
	public boolean isCollision (int color, Move m)
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
		int initRank = 1;
		int deltaRank = abs((m.getDst() >> 4) - (m.getSrc() >> 4));

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

		if ((deltaRank == 2) && ((m.getSrc() >> 4) == initRank) && ((this.get(m.get2DDst()[0], m.get2DDst()[1] + 1)).getPieceWithoutColorByte() == PieceData.EMPTY_BYTE))
		{
			m.setSpecial(m.getSpecial() | Move.DOUBLE_PAWN_PUSH_MASK);
		}

		return m;
	}

	/**
	 * Returns all possible (non-legal) moves
	 * @param p The piece for which we wish to generate all possible moves
	 * @return An ArrayList containing all moves the piece can make (legal or illegal)
	 */
	public ArrayList <Move> getMoves (Piece p)
	{
		if ((p.getPieceWithoutColorByte() != 0) && (p.getColor() == this.activeColor))
		{
			return p.getAllPossibleMoves();
		}
		else
		{
			return new ArrayList<>();
		}
	}

	public boolean isValidMove (Piece p, Move m)
	{
		//System.out.println("Checking " + p.toString() + " pieceByte: " + Integer.toBinaryString(p.getPieceWithoutColorByte()));
		//int src = m.getSrc();
		//int srcFile = m.get2DSrc()[0];
		//int srcRank = m.get2DSrc()[1];
		int dst = m.getDst();
		//int dstFile = m.get2DSrc()[0];
		//int dstRank = m.get2DSrc()[1];
		int color = p.getColor();
		int piece = p.getPieceWithoutColorByte();

		// You can't end on one of your own pieces
		if (this.isCollision(color, m))
		{
			//System.out.println("[isValidMove] " + m.toString() + " collided");
			return false;
		}

		if ((m.isCapture()) && (this.get(dst).getPieceWithoutColorByte() == PieceData.KING_BYTE))
		{
			//System.out.println("[isValidMove] " + m.toString() + " captures king");
			return false;
		}

		switch (piece)
		{
			case PieceData.PAWN_BYTE:
				if (!this.isValidPawnMove(color, m))
				{
					//System.out.println("[isValidMove] Pawn Removing " + m.toString());
					return false;
				}
				break;
			case PieceData.ROOK_BYTE:
				if (!this.isValidRookMove(m))
				{
					//System.out.println("[isValidMove] Rook: Removing " + m.toString());
					return false;
				}
				break;
			case PieceData.KNIGHT_BYTE:
				// There are no special rules for knights besides the ones that are handled outside of this switch
				break;
			case PieceData.BISHOP_BYTE:
				if (!this.isValidBishopMove(m))
				{
					//System.out.println("[isValidMove] Bishop Removing " + m.toString());
					return false;
				}
				break;
			case PieceData.QUEEN_BYTE:
				if ((!this.isValidBishopMove(m)) && (!this.isValidRookMove(m)))
				{
					//System.out.println("[isValidMove] Queen Removing " + m.toString());
					return false;
				}
				break;
			case PieceData.KING_BYTE:
				if (!this.isValidKingTypeMove(color, m))
				{
					//System.out.println("[isValidMove] King Removing " + m.toString());
					return false;
				}
				break;
			case PieceData.EMPTY_BYTE:
				return true;
			default:
				//System.out.println("[isValidMove] default: Removing " + m.toString());
				return false;
		}

		return true;
	}

	/**
	 * Returns all valid moves for a specified piece
	 * @param p The piece whose moves are being requested
	 * @return an ArrayList with all valid & legal moves
	 */
	public ArrayList<Move> getLegalMoves(Piece p)
	{
		if ((p.getPieceWithoutColorByte() != 0) && (p.getColor() == this.activeColor))
		{
			//System.out.println("NEW");
			int color = p.getColor();
			//int piece = p.getPieceWithoutColorByte();

			ArrayList<Move> possibleMoves = p.getAllPossibleMoves();

			//System.out.println("Generated " + Integer.toString(possibleMoves.size()) + " possible moves.");

			for (int i = 0; i < possibleMoves.size(); i++)
			{
				Move m = possibleMoves.get(i);

				m = this.setFlags(color, m);

				if (!this.isValidMove(p, m))
				{
					//System.out.println("[getLegalMoves] removing: " + m.toString());
					possibleMoves.remove(i);
					i--;
				}
			}

			Collections.sort(possibleMoves);

			//System.out.println("Generated:");
			//for(Move m : possibleMoves)
			//{
				//System.out.println(m.toString());
			//}

			return possibleMoves;
		}
		else
		{
			return new ArrayList<Move>();
		}
	}

	/**
	 * Makes the specified move
	 * @param m The move to be made
	 * @return a String in algebraic notation representing the move that was made
	 */
	public String makeMove (Move m)
	{
		//System.out.println("START\nHash: "  + Long.toBinaryString(this.zobristHash));
		this.zobristHash ^= AIPlayer.transpositionTable.getHash(TranspositionTable.getPieceIndex(this.get(m.getSrc())), m.getSrc());	// First, remove the old piece from the table
		//System.out.println("Hashing away piece at old location\nHash: "  + Long.toBinaryString(this.zobristHash));
		this.zobristHash ^= AIPlayer.transpositionTable.getHash(TranspositionTable.getPieceIndex(this.get(m.getSrc())), m.getDst());	// Then, add the new piece to the table
		//System.out.println("Hashing in piece at new location\nHash: "  + Long.toBinaryString(this.zobristHash));

		if (m.isCapture())
		{
			this.zobristHash ^= AIPlayer.transpositionTable.getHash(TranspositionTable.getPieceIndex(this.get(m.getDst())), m.getDst());	// If the move was a capture, remove the captured piece
			//System.out.println("Removing previously captured piece\nHash: "  + Long.toBinaryString(this.zobristHash));
		}

		this.zobristHash ^= AIPlayer.transpositionTable.getBlackTurnHash();


		this.cb.set(m.getDst(), this.cb.get(m.getSrc()));
		this.cb.set(m.getSrc(), PieceData.EMPTY_BYTE);	// Empty the source square

		this.moveHistory.add(m);

		String moveString = "";

		if (this.cb.get(m.getDst()) != PieceData.PAWN_BYTE)
		{
			moveString += PieceData.toShortFromNum(this.cb.get(m.getDst()) & 0x07);
		}

		moveString += m.getPrettyDstCoords();

		this.toggleActivePlayer();
		GameManager.chronometer.toggle();

		return moveString;
	}

	/**
	 * Returns the last move made
	 * If you wish to undo a move, please use the undo() method
	 * @return The last move that was made
	 */
	public Move getLastMove ()
	{
		return this.moveHistory.get(this.moveHistory.size() - 1);
	}

	/**
	 * Returns the move history
	 * @return an ArrayList containing all moves (in chronological order)
	 */
	public ArrayList<Move> getMoveHistory ()
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

		for (Move m : moves)
		{
			this.cachedMoves.add(m);
		}
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
		Move m = this.getLastMove();
		this.makeMove(new Move (m.getDst(), m.getSrc(), 0x0));	// Making a dummy move which is just the inverse of the last move
		this.moveHistory.remove(this.moveHistory.size() - 1);		// Always remove the dummy-move from the move history
	}

	/**
	 * Returns true if the specified colour is check-mate
	 * @param color The colour to be checked
	 * @return true if the player is check-mate, false if he is not
	 */
	public boolean isCheckMate (int color)
	{
		Piece king = this.getKing(color);

		ArrayList <Move> kingMoves = this.getLegalMoves(king);

		return (kingMoves.size() == 0) && this.isCheck(color);
	}

	/**
	 * Returns true if the specified colour is check'ed
	 * @param color	the colour to be checked
	 * @return	true if the player is in check, false if he is not
	 */
	public boolean isCheck (int color )
	{
		Piece king = this.getKing(color);

		for (Move m : this.getAllLegalMoves(PieceData.getOpponentColor(color)))
		{
			if (m.getDst() == king.getPositionByte())
			{
				return true;
			}
		}

		return false;
	}

	private Piece getKing (int color)
	{
		Piece king = new Piece();
		ArrayList <Piece> pieces = this.getAllPieces(color);

		for (Piece p : this.getAllPieces(color))
		{
			if (p.getPieceWithoutColorByte() == PieceData.KING_BYTE)
			{
				king = p;
				break;
			}
		}

		return king;
	}

	/**
	 * Returns all the move a particular side can make
	 * @param color The color who's moves are to be acquired
	 * @return All possible moves for the specified color
	 */
	public ArrayList <Move> getAllLegalMoves(int color)
	{
		ArrayList <Piece> pieces = this.getAllPieces(color);
		ArrayList <Move> pieceMoves;
		ArrayList <Move> moves = new ArrayList<>();

		for (Piece p : pieces)
		{
			pieceMoves = this.getLegalMoves(p);

			for (Move m : pieceMoves)
			{
				moves.add(m);
			}
		}

		return moves;
	}

	public long getZobristHash ()
	{
		return this.zobristHash;
	}

	public void handlePromotion(int[] position, int piece)
	{
		cb.set(position[0],position[1],piece);
	}
}