package Chess.Game;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */

public class Piece
{
	private byte pieceByte;
	private byte positionByte;

	/**
	 * Constructor, takes a pieceByte and a 0x88 positionByte
	 * @param pieceByte a piece byte
	 * @param positionByte a (0x88) position byte
	 */
	public Piece (byte pieceByte, byte positionByte)
	{
		this.pieceByte = pieceByte;
		this.positionByte = positionByte;
	}

	/**
	 * Constructor, takes a pieceByte and a file and rank byte
	 * @param pieceByte a pieceByte
	 * @param file a file byte
	 * @param rank a rank byte
	 */
	public Piece (byte pieceByte, byte file, byte rank)
	{
		this.pieceByte = pieceByte;
		this.positionByte = ChessBoard.get0x88Index(file, rank);
	}

	/**
	 * Constructor, takes a pieceString, localeByte and (0x88) positionByte
	 * The sting can be either in long, human-readable format or in short
	 * 1-character format
	 * @param pieceString can be either in long, human-readable format or in short 1-character format
	 * @param positionByte a 0x88 position byte
	 * @param localeByte a locale that goes with pieceString
	 */
	public Piece (String pieceString, byte positionByte, byte localeByte)
	{
		switch (pieceString.length())
		{
			case 1:
				this.pieceByte = PieceData.toByteFromShort(pieceString, localeByte);
				break;
			default:
				this.pieceByte = PieceData.toByteFromString(pieceString, localeByte);
				break;
		}

		this.positionByte = positionByte;
	}

	/**
	 * Constructor, takes a pieceString, localeByte and a file and rank byte.
	 * The sting can be either in long, human-readable format or in short
	 * 1-character format
	 * @param pieceString can be either in long, human-readable format or in short 1-character format
	 * @param file  piece file
	 * @param rank  piece rank
	 * @param localeByte locale that goes with pieceString
	 */
	public Piece (String pieceString, byte file, byte rank, byte localeByte)
	{
		switch (pieceString.length())
		{
			case 1:
				this.pieceByte = PieceData.toByteFromShort(pieceString, localeByte);
				break;
			default:
				this.pieceByte = PieceData.toByteFromString(pieceString, localeByte);
				break;
		}

		this.positionByte = ChessBoard.get0x88Index(file,rank);
	}

	public byte getPieceByte ()
	{
		return this.pieceByte;
	}

	public byte getPositionByte ()
	{
		return this.positionByte;
	}
}
