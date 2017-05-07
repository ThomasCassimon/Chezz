package Chess.Game;

import Chess.Exceptions.Unchecked.IllegalSquareException;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */



// TODO: add to algebraic string method
public class Move implements Comparable<Move>
{
	public static final int CAPTURE_MASK = 0x04;
	public static final int PROMO_MASK = 0x08;
	public static final int DOUBLE_PAWN_PUSH_MASK = 0x01;
	public static final int KING_SIDE_CASTLE_MASK = 2;
	public static final int QUEEN_SIDE_CASTLE_MASK = 3;
	/**
	 * Both src and dst are stored as 0x88 squares
	 */
	private int src;
	private int dst;
	/**
	 * The special byte indicates certain things about a move:
	 * 1: Special0
	 * 2: Special1
	 * 4: Capture
	 * 8: Promotion
	 *
	 * For non-promo/capture:
	 * XXXX 0000 = Quiet move
	 * XXXX 0001 = Double pawn push
	 * XXXX 0010 = King-side castle
	 * XXXX 0011 = Queen-side castle
	 *
	 * For capture:
	 * XXXX 0100 = regular capture
	 * XXXX 0101 = En-Passant capture
	 *
	 * For promo:
	 * XXXX 1000 = Knight promotion
	 * XXXX 1001 = Bishop promotion
	 * XXXX 1010 = Rook promotion
	 * XXXX 1011 = Queen promotion
	 *
	 * For promo + capture:
	 * XXXX 1100 = Knight-promo + capture
	 * XXXX 1101 = Bishop-promo + capture
	 * XXXX 1110 = Rook-promo + capture
	 * XXXX 1111 = Queen-promo + capture
	 *
	 * For more: <a href="https://chessprogramming.wikispaces.com/Encoding+Moves">ChessProgrammingWiki</a>
	 * todo: Change promo/capture codes to match the bit-encoding for the different pieces
	 */
	private int special;

	public Move ()
	{
		this.src = 0xFFFF;
		this.dst = 0xFFFF;
		this.special = 0xFFFF;
	}

	public Move (int src, int dst, int special)
	{
		this.src = src;
		this.dst = dst;
		this.special = special;
	}

	public Move (int srcFile, int srcRank, int dstFile, int dstRank, int special)
	{
		this.src = ChessBoard.get0x88Index(srcFile, srcRank);
		this.dst = ChessBoard.get0x88Index(dstFile, dstRank);
		this.special = special;
	}

	public Move(Move m)
	{
		this.src = m.src;
		this.dst = m.dst;
		this.special = m.special;
	}

	/**
	 *
	 * @return The source square (in 0x88 notation)
	 */
	public int getSrc ()
	{
		return this.src;
	}

	/**
	 *
	 * @return The destination square (in 0x88 notation)
	 */
	public int getDst ()
	{
		return this.dst;
	}

	/**
	 *
	 * @return The special byte
	 */
	public int getSpecial ()
	{
		return this.special;
	}

	/**
	 * Sets the objects special byte
	 * @param special the desired special byte
	 * @return returns the move after the changes have been made for easy method chaining
	 */
	public Move setSpecial (int special)
	{
		this.special = special;
		return this;
	}

	/**
	 * Checks wheter or not a move contains an off-board space
	 */
	public void checkValid ()
	{
		if (((this.src & 0x88) != 0) || ((this.dst & 0x88) != 0))
		{
			throw new IllegalSquareException("Move from " + Integer.toString(this.src) + " to " + Integer.toString(this.dst) + " is not a valid move");
		}
	}

	/**
	 *
	 * @return true if no captures are made by this move
	 */
	public boolean isQuiet ()
	{
		return this.special == 0;
	}

	/**
	 *
	 * @return true if a pawn moved 2 spaces with this move
	 */
	public boolean isDoublePawnPush ()
	{
		return this.special == 1;
	}

	/**
	 *
	 * @return true if the move is a king-side castle
	 */
	public boolean isKingCastle ()
	{
		return this.special == 2;
	}

	/**
	 *
	 * @return true if the move is a queen-side castle
	 */
	public boolean isQueenCastle ()
	{
		return this.special == 3;
	}

	/**
	 *
	 * @return true if the move is a capture
	 */
	public boolean isCapture ()
	{
		return (this.special & 0x04) > 0;
	}

	/**
	 * Sets the capture flag to 1
	 */
	public void setCapture ()
	{
		this.special = this.special | CAPTURE_MASK;
	}

	/**
	 * Sets the promo flag to 1
	 */
	public void setPromo()
	{
		this.special = this.special | PROMO_MASK;
	}

	public void setKingSideCastle ()
	{
		this.special |= KING_SIDE_CASTLE_MASK;
	}

	public void setQueenSideCastle ()
	{
		this.special |= QUEEN_SIDE_CASTLE_MASK;
	}

	/**
	 *
	 * @return true if the move is a promotion
	 */
	public boolean isPromotion ()
	{
		return (this.special & 0x08) > 0;
	}

	/**
	 *
	 * @return The source square (in file first format)
	 */
	public int[] get2DSrc ()
	{
		return ChessBoard.get2DCoord(this.src);
	}

	public int[] get2DDst ()
	{
		return ChessBoard.get2DCoord(this.dst);
	}

	/**
	 * Converts the move to a string of the following format:
	 * SRC - DST
	 * Both SRC and DST are given in the usual file-first indexing, but with letter-number format
	 * @return a string
	 */
	@Override
	public String toString ()
	{
		return this.getPrettySrcCoords() + "-" + this.getPrettyDstCoords() + " Special: " + Integer.toBinaryString(this.special);
	}

	public String getPrettySrcCoords ()
	{
		int[] coords = ChessBoard.get2DCoord(this.src);
		return Character.toString((char) (coords[0] + 'a')) + Integer.toString(coords[1] + 1);
	}

	public String getPrettyDstCoords ()
	{
		int[] coords = ChessBoard.get2DCoord(this.dst);
		return Character.toString((char) (coords[0] + 'a')) + Integer.toString(coords[1] + 1);
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o)
		{
			return true;
		}
		else if (!(o instanceof Move))
		{
			return false;
		}
		else if ((this.src == ((Move) o).src) && (this.dst == ((Move) o).dst) && (this.special == ((Move) o).special))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// Promo > Capture
	// Capture > Normal
	// So: Normal < Capture < Promo
	@Override
	public int compareTo(Move move)
	{
		if ((this.src == move.src) && (this.dst == move.dst) && (this.special == move.special))
		{
			return 0;
		}
		else
		{
			if (this.isPromotion() && !move.isPromotion())
			{
				return 1;
			}
			else if (!this.isPromotion() && move.isPromotion())
			{
				return -1;
			}
			else
			{
				if (this.isCapture() && !move.isCapture())
				{
					return 1;
				}
				else if (!this.isCapture() && move.isCapture())
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
		}
	}

	public void setDoublePawnPush()
	{
		this.special |= DOUBLE_PAWN_PUSH_MASK;
	}
}
