package Chess.Game;

import Chess.Athena.AIPlayer;
import Chess.Exceptions.Unchecked.IllegalSquareException;

import java.util.ArrayList;

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
	private ChessBoard cb;

	public GameManager ()
	{
		this.cb = new ChessBoard();
		this.activeColor = PieceData.WHITE_BYTE;
		this.players = new Player [2];
		this.moveHistory = new ArrayList <Move> ();
		this.cachedMoves = new ArrayList <Move> ();
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
	 * Returns all pieces of a specific color. Intended to replace the getAllWhitePieces() and getAllBlackPieces() methods
	 * @param colorByte the color to be retrieved
	 * @return	An ArrayList of pieces of the specified color
	 */
	public ArrayList<Piece> getAllPieces (int colorByte)
	{
		ArrayList<Piece> pieces = new ArrayList <Piece> (16);

		for (int i = 1; i <= 8; i++)
		{
			for (int j = 1; j <= 8; j++)
			{
				if ((this.cb.get(i,j) & colorByte) != 0)
				{
					pieces.add(new Piece (this.cb.get(i,j), i, j));
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

		for (int i = 1; i <= 8; i++)
		{
			for (int j = 1; j <= 8; j++)
			{
				if ((this.cb.get(i,j) & PieceData.WHITE_BYTE) != 0)
				{
					pieces.add(new Piece (this.cb.get(i,j), i, j));
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

		for (int i = 1; i <= 8; i++)
		{
			for (int j = 1; j <= 8; j++)
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
	 * Returns all valid moves for a specified piece
	 * @param p The piece whose moves are being requested
	 * @return an ArrayList with all valid & legal moves
	 */
	public ArrayList<Move> getAllValidMoves (Piece p)
	{
		int piece = p.getPieceWithoutColorByte();
		int color = p.getColorByte();
		ArrayList <Move> possibleMoves = p.getAllPossibleMoves();

		for (int i = 0; i < possibleMoves.size(); i++)
		{
			Move m = possibleMoves.get(i);
			int src = m.getSrc();
			int dst = m.getDst();
			int deltaRank = (dst >> 4) - (src >> 4);
			int deltaFile = (dst & 7) - (src & 7);

			// You can't end on one of your own pieces
			if ((this.cb.get(dst) & (PieceData.BLACK_BYTE | PieceData.WHITE_BYTE)) == (color))
			{
				possibleMoves.remove(i);
				i--;
				continue;
			}
			// If you end on an opponent's piece it's a capture
			else if (((this.cb.get(dst) & (PieceData.BLACK_BYTE | PieceData.WHITE_BYTE)) == (PieceData.getOpponentColorNum(color))) && (piece != PieceData.PAWN_BYTE))
			{
				// Pawns don't capture on their moves so they aren't included in this check
				possibleMoves.set(i, m.setSpecial((m.getSpecial() | Move.CAPTURE_MASK)));
			}
			// If you're a pawn and you're moving forward and you're ending on an opponent's piece you can't move there
			else if (((this.cb.get(dst) & (PieceData.BLACK_BYTE | PieceData.WHITE_BYTE)) == (PieceData.getOpponentColorNum(color))) && (piece == PieceData.PAWN_BYTE) && (deltaRank == 0))
			{
				possibleMoves.remove(i);
				i--;
				continue;
			}

			switch (piece)
			{
				case PieceData.PAWN_BYTE:
					// If you're a pawn and you're moving 2 steps at a time
					if (abs(deltaRank) == 2)
					{
						// If there's a piece in the middle of your 2 steps
						if (this.cb.get(dst + 0x10) != PieceData.EMPTY_BYTE)
						{
							possibleMoves.remove(i);
							i--;
							continue;
						}
						else
						{
							// Setting the double pawn move mask
							possibleMoves.get(i).setSpecial(possibleMoves.get(i).getSpecial() | Move.DOUBLE_PAWN_PUSH_MASK);
						}
					}


					if (color == PieceData.WHITE_BYTE)
					{
						// If a pawn reaches the back rank it's move should be marked as a promotion
						if ((dst & 0x70) == 0x70)
						{
							possibleMoves.get(i).setSpecial(possibleMoves.get(i).getSpecial() | Move.PROMO_MASK);
						}

						int indexL = src + 0x0F;
						int indexR = dst + 0x11;

						if ((indexL & 0x88) == 0)
						{
							// Moves up and left (from white's POV)
							Move capL = new Move(src, indexL, Move.CAPTURE_MASK);

							if (((this.cb.get(capL.getDst()) & (PieceData.WHITE_BYTE | PieceData.BLACK_BYTE)) == PieceData.getOpponentColorNum(color)) && (!possibleMoves.contains(capL)))
							{
								// Up/Left capture is possible
								//todo: check indices for captures etc.
								possibleMoves.add(capL);
								i += 2;
							}
						}

						if ((indexR & 0x88) == 0)
						{
							// Moves up and right (from white's POV)
							Move capR = new Move(src, indexR, Move.CAPTURE_MASK);
							if (((this.cb.get(capR.getDst()) & (PieceData.WHITE_BYTE | PieceData.BLACK_BYTE)) == PieceData.getOpponentColorNum(color)) && (!possibleMoves.contains(capR)))
							{
								// Up/Right capture is possible
								//todo: check indices for captures etc.
								possibleMoves.add(capR);
								i += 2;
							}
						}
					}
					else if (color == PieceData.BLACK_BYTE)
					{
						// If a pawn reaches the back rank it's move should be marked as a promotion
						if ((dst & 0x70) == 0x00)
						{
							possibleMoves.get(i).setSpecial(possibleMoves.get(i).getSpecial() | Move.PROMO_MASK);
						}

						int indexL = src - 0x0F;
						int indexR = src - 0x11;

						if ((indexL & 0x88) == 0)
						{
							// Moves down and left (from white's POV)
							Move capL = new Move(src, src - 0x0F, Move.CAPTURE_MASK);
							if (((this.cb.get(capL.getDst()) & (PieceData.WHITE_BYTE | PieceData.BLACK_BYTE)) == PieceData.getOpponentColorNum(color)) && (!possibleMoves.contains(capL)))
							{
								// Down/Left capture is possible
								//todo: check indices for captures etc.
								possibleMoves.add(capL);
								i += 2;
							}
						}

						if ((indexR & 0x88) == 0)
						{
							// Moves down and right (from white's POV)
							Move capR = new Move(src, src - 0x11, Move.CAPTURE_MASK);


							if (((this.cb.get(capR.getDst()) & (PieceData.WHITE_BYTE | PieceData.BLACK_BYTE)) == PieceData.getOpponentColorNum(color)) && (!possibleMoves.contains(capR)))
							{
								// Down/Right capture is possible
								//todo: check indices for captures etc.
								possibleMoves.add(capR);
								i += 2;
							}
						}
					}

					break;
				case PieceData.ROOK_BYTE:
					// Moving Top-Bottom
					if ((abs(deltaRank) > 0) && (deltaFile == 0))
					{
						// Checking all squares along move path
						for (int j = 1; j <= abs(deltaRank); j++)
						{
							// Intermediate square index
							int index = src + (j * 0x10);
							if (((index & 0x88) == 0) && (this.cb.get(index) != 0))
							{
								possibleMoves.remove(i);
								i--;
								break;
							}

							index = src - (j * 0x10);

							if (((index & 0x88) == 0) && (this.cb.get(index) != 0))
							{
								possibleMoves.remove(i);
								i--;
								break;
							}
						}
					}
					else if ((deltaRank == 0) && (abs(deltaFile) > 0))
					{
						for (int j = 1; j <= abs(deltaFile); j++)
						{
							// Intermediate square index
							int index = src + j;
							if (((index & 0x88) == 0) && (this.cb.get(index) != 0))
							{
								possibleMoves.remove(i);
								i--;
								break;
							}

							index = src - j;

							if (((index & 0x88) == 0) && (this.cb.get(index) != 0))
							{
								possibleMoves.remove(i);
								i--;
								break;
							}
						}
					}
					else
					{
						possibleMoves.remove(i);
						i--;
					}
					break;

				case PieceData.KNIGHT_BYTE:
					// There are no special rules for knights besides the ones that are handled outside of this switch
					break;
				case PieceData.BISHOP_BYTE:
					// Checking if the move is a valid Bishop-type move
					//todo: Stop checking unnecessary squares
					if (abs(deltaRank) == abs(deltaFile))
					{
						for (int j = 1; j <= abs(deltaRank); j++)
						{
							int index = 0;

							if ((deltaRank > 0) && (deltaFile > 0))
							{
								index = src + (j * 0x11);

								if ((index & 0x88) == 0)
								{
									if (this.cb.get(index) != PieceData.EMPTY_BYTE)
									{
										possibleMoves.remove(i);
										i--;
										break;
									}
								}
							}
							else if ((deltaRank > 0) && (deltaFile < 0))
							{
								index = src + (j * 0x0F);

								if ((index & 0x88) == 0)
								{
									if (this.cb.get(index) != PieceData.EMPTY_BYTE)
									{
										possibleMoves.remove(i);
										i--;
										break;
									}
								}
							}
							else if ((deltaRank < 0) && (deltaFile > 0))
							{
								index = src - (j * 0x11);

								if ((index & 0x88) == 0)
								{
									if (this.cb.get(index) != PieceData.EMPTY_BYTE)
									{
										possibleMoves.remove(i);
										i--;
										break;
									}
								}
							}
							else if ((deltaRank < 0) && (deltaFile < 0))
							{
								index = src - (j * 0x0F);

								if ((index & 0x88) == 0)
								{									System.out.println("Move is on the board");

									if (this.cb.get(index) != PieceData.EMPTY_BYTE)
									{
										possibleMoves.remove(i);
										i--;
										break;
									}
								}
							}
						}
					}
					else
					{
						possibleMoves.remove(i);
						i--;
						continue;
					}
					break;

				case PieceData.QUEEN_BYTE:
					// Queen will just check Rook and Bishop moves sequentially
					// Checking Rook-type moves
					// Moving Top-Bottom
					if ((abs(deltaRank) > 0) && (deltaFile == 0))
					{
						// Checking all squares along move path
						for (int j = 1; j <= abs(deltaRank); j++)
						{
							// Intermediate square index
							int index = src + (j * 0x10);
							if (((index & 0x88) == 0) && (this.cb.get(index) != 0))
							{
								possibleMoves.remove(i);
								i--;
								break;
							}

							index = src - (j * 0x10);

							if (((index & 0x88) == 0) && (this.cb.get(index) != 0))
							{
								possibleMoves.remove(i);
								i--;
								break;
							}
						}
					}
					else if ((deltaRank == 0) && (abs(deltaFile) > 0))
					{
						for (int j = 1; j <= abs(deltaFile); j++)
						{
							// Intermediate square index
							int index = src + j;
							if (((index & 0x88) == 0) && (this.cb.get(index) != 0))
							{
								possibleMoves.remove(i);
								i--;
								break;
							}

							index = src - j;

							if (((index & 0x88) == 0) && (this.cb.get(index) != 0))
							{
								possibleMoves.remove(i);
								i--;
								break;
							}
						}
					}
					// Checking Bishop-type moves
					if (abs(deltaRank) == abs(deltaFile))
					{
						for (int j = 1; j <= abs(deltaRank); j++)
						{
							int index = 0;

							if ((deltaRank > 0) && (deltaFile > 0))
							{
								index = src + (j * 0x11);

								if ((index & 0x88) == 0)
								{
									if (this.cb.get(index) != PieceData.EMPTY_BYTE)
									{
										possibleMoves.remove(i);
										i--;
										break;
									}
								}
							}
							else if ((deltaRank > 0) && (deltaFile < 0))
							{
								index = src + (j * 0x0F);

								if ((index & 0x88) == 0)
								{
									if (this.cb.get(index) != PieceData.EMPTY_BYTE)
									{
										possibleMoves.remove(i);
										i--;
										break;
									}
								}
							}
							else if ((deltaRank < 0) && (deltaFile > 0))
							{
								index = src - (j * 0x11);

								if ((index & 0x88) == 0)
								{
									if (this.cb.get(index) != PieceData.EMPTY_BYTE)
									{
										possibleMoves.remove(i);
										i--;
										break;
									}
								}
							}
							else if ((deltaRank < 0) && (deltaFile < 0))
							{
								index = src - (j * 0x0F);

								if ((index & 0x88) == 0)
								{									System.out.println("Move is on the board");

									if (this.cb.get(index) != PieceData.EMPTY_BYTE)
									{
										possibleMoves.remove(i);
										i--;
										break;
									}
								}
							}
						}
					}
					else
					{
						possibleMoves.remove(i);
						i--;
						continue;
					}
					// Done checking bishop-type moves

					break;

				case PieceData.KING_BYTE:
					// For the king we will do check-checking
					int opponentColor = PieceData.getOpponentColorNum(color);
					ArrayList <Piece> opponentPieces = this.getAllPieces(opponentColor);

					for (int j = 0; j < opponentPieces.size(); j++)
					{
						ArrayList <Move> opponentMoves = this.getAllValidMoves(opponentPieces.get(j));

						for (int k = 0; k < opponentMoves.size(); k++)
						{
							if (possibleMoves.get(i).getDst() == opponentMoves.get(i).getDst())
							{
								possibleMoves.remove(i);
								k = opponentMoves.size();
								j = opponentMoves.size();
								i--;
							}
						}
					}

					break;

				default:
					break;
			}
		}
		/*
		for (Move m : possibleMoves)
		{
			System.out.println("Generated: " + m.toString());
		}
		*/
		return possibleMoves;
	}

	/**
	 * Makes the specified move
	 * @param m The move to be made
	 * @return a String in algebraic notation representing the move that was made
	 */
	public String makeMove (Move m)
	{
		this.cb.set(m.getDst(), this.cb.get(m.getSrc()));
		this.cb.set(m.getSrc(), PieceData.EMPTY_BYTE);	// Empty the source square
		this.moveHistory.add(m);

		String moveString = "";

		if (this.cb.get(m.getDst()) != PieceData.PAWN_BYTE)
		{
			moveString += PieceData.toShortFromNum(this.cb.get(m.getDst()) & 0x07);
		}

		moveString += m.getPrettyDstCoords();

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
		this.moveHistory.remove(this.moveHistory.size() - 1);	// Always remove the dummy-move from the move history
	}
}