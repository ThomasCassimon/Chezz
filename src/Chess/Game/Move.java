package Chess.Game;

import Chess.Exceptions.Unchecked.IllegalSquareException;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */
public class Move
{
	/**
	 * Both src and dst are stored as 0x88 squares
	 */
	private byte src;
	private byte dst;
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
	private byte special;

	public Move ()
	{
		this.src = (byte) 0xFF;
		this.dst = (byte) 0xFF;
		this.special = (byte) 0xFF;
	}

	public Move (byte src, byte dst, byte special)
	{
		this.src = src;
		this.dst = dst;
		this.special = special;
	}

	public Move (byte srcFile, byte srcRank, byte dstFile, byte dstRank, byte special)
	{
		this.src = ChessBoard.get0x88Index(srcRank, srcFile);
		this.dst = ChessBoard.get0x88Index(dstRank, dstFile);
		this.special = special;
	}

	/**
	 *
	 * @return The source square (in 0x88 notation)
	 */
	public byte getSrc ()
	{
		return this.src;
	}

	/**
	 *
	 * @return The destination square (in 0x88 notation)
	 */
	public byte getDst ()
	{
		return this.dst;
	}

	/**
	 *
	 * @return The special byte
	 */
	public byte getSpecial ()
	{
		return this.special;
	}

	/**
	 * Sets the objects special byte
	 * @param special the desired special byte
	 */
	public void setSpecial (byte special)
	{
		this.special = special;
	}

	/**
	 * Checks wheter or not a move contains an off-board space
	 */
	public void checkValid ()
	{
		if (((this.src & 0x88) != 0) || ((this.dst & 0x88) != 0))
		{
			throw new IllegalSquareException("Move from " + Byte.toString(this.src) + " to " + Byte.toString(this.dst) + " is not a valid move");
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
	public byte[] get2DSrc ()
	{
		return ChessBoard.get2DCoord(this.src);
	}

	public byte[] get2DDst ()
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
		byte[] srcCoords = ChessBoard.get2DCoord(this.src);
		byte[] dstCoords = ChessBoard.get2DCoord(this.dst);

		return ((char) (srcCoords[0] + ('A'-1))) + Byte.toString(srcCoords[1]) + "-" + ((char) (dstCoords[0] + ('A'-1))) + Byte.toString(dstCoords[1]);
	}
}
