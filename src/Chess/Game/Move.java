package Chess.Game;

import Chess.Exceptions.Unchecked.IllegalSideException;
import Chess.Exceptions.Unchecked.IllegalSquareException;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */



// TODO: add to algebraic string method
public class Move
{
	public static final int CAPTURE_MASK = 0x04;
	public static final int PROMO_MASK = 0x08;
	public static final int DOUBLE_PAWN_PUSH_MASK = 0x01;
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
	 * XXXX 0011 = Queen castle
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
	 * For promo+capture:
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
		this.src = 0xFF;
		this.dst = 0xFF;
		this.special = 0xFF;
	}

	public Move (int src, int dst, int special)
	{
		this.src = src;
		this.dst = dst;
		this.special = special;
	}

	public Move (int srcFile, int srcRank, int dstFile, int dstRank, int special)
	{
		this.src = ChessBoard.get0x88Index(srcRank, srcFile);
		this.dst = ChessBoard.get0x88Index(dstRank, dstFile);
		this.special = special;
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
		int[] srcCoords = ChessBoard.get2DCoord(this.src);
		int[] dstCoords = ChessBoard.get2DCoord(this.dst);

		String res = ((char) (srcCoords[0] + ('A'-1))) + Integer.toString(srcCoords[1]) + "-" + ((char) (dstCoords[0] + ('A'-1))) + Integer.toString(dstCoords[1]);

		res += " Capture: " + Boolean.toString((this.special & CAPTURE_MASK) == CAPTURE_MASK);
		res += " Promo: " + Boolean.toString((this.special & PROMO_MASK) == PROMO_MASK);

		return res;
	}
}
