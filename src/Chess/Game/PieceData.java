package Chess.Game;

import Chess.Exceptions.Unchecked.IllegalPieceException;
import Chess.Exceptions.Unchecked.IllegalSideException;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 *
 * This class will hold all constants for different pieces etc.
 */
public final class PieceData
{
	public static final int WHITE_BYTE =	0x10;
	public static final int BLACK_BYTE =	0x20;

	public static final int EMPTY_BYTE =    0x00;
	public static final int PAWN_BYTE =	 	0x01;
	public static final int ROOK_BYTE =	 	0x02;
	public static final int KNIGHT_BYTE =	0x03;
	public static final int BISHOP_BYTE =	0x04;
	public static final int QUEEN_BYTE =	0x05;
	public static final int KING_BYTE =	 	0x06;

	public static final int PAWN_SCORE =    100;
	public static final int ROOK_SCORE =    500;
	public static final int KNIGHT_SCORE =  320;
	public static final int BISHOP_SCORE =  325;
	public static final int QUEEN_SCORE =   975;
	public static final int KING_SCORE =    Short.MAX_VALUE;

	public static final int BOTH_BISHOPS_BONUS = 75;
	public static final int BOTH_ROOKS_PENALTY = -50;
	public static final int BOTH_KNIGHTS_PENALTY = -30;

	/**
	 * Every Locale gets their own inner class, these inner classes
	 * will be used to store locale-dependent things (Strings etc.)
	 * English Locale
	 */
	public static final class EN_UK
	{
		public static final int LOCALE_BYTE = 		0x0;

		public static final String PAWN_NAME =		"Pawn";
		public static final String ROOK_NAME =		"Rook";
		public static final String KNIGHT_NAME =	"Knight";
		public static final String BISHOP_NAME =	"Bishop";
		public static final String QUEEN_NAME =		"Queen";
		public static final String KING_NAME =		"King";

		public static final String PAWN_SHORT =     "P";
		public static final String ROOK_SHORT =     "R";
		public static final String KNIGHT_SHORT =   "N";
		public static final String BISHOP_SHORT =   "B";
		public static final String QUEEN_SHORT =    "Q";
		public static final String KING_SHORT =     "K";
	}

	/**
	 * Every Locale gets their own inner class, these inner classes
	 * will be used to store locale-dependent things (Strings etc.)
	 * Dutch Locale
	 */
	public static final class NL_BE
	{
		public static final int LOCALE_BYTE = 		0x1;

		public static final String PAWN_NAME =		"Pion";
		public static final String ROOK_NAME =		"Toren";
		public static final String KNIGHT_NAME =	"Paard";
		public static final String BISHOP_NAME =	"Loper";
		public static final String QUEEN_NAME =		"Koningin";
		public static final String KING_NAME =		"Koning";

		public static final String PAWN_SHORT =     "P";
		public static final String ROOK_SHORT =     "T";
		public static final String KNIGHT_SHORT =   "A";
		public static final String BISHOP_SHORT =   "L";
		public static final String QUEEN_SHORT =    "O";
		public static final String KING_SHORT =     "K";
	}

	/**
	 * This method translates a int to a human-readable string
	 * @param pieceNum	The int to be translated
	 * @param localeNum A int to indicate the desired locale
	 * @return The name of the piece, in the correct locale
	 * @throws IllegalArgumentException
	 * @throws IllegalPieceException
	 */
	public static String toStringFromNum(int pieceNum, int localeNum) throws IllegalArgumentException, IllegalPieceException
	{
		switch (localeNum)
		{
			case EN_UK.LOCALE_BYTE:
				switch (pieceNum)
				{
					case PAWN_BYTE:
						return EN_UK.PAWN_NAME;
					case ROOK_BYTE:
						return EN_UK.ROOK_NAME;
					case KNIGHT_BYTE:
						return EN_UK.KNIGHT_NAME;
					case BISHOP_BYTE:
						return EN_UK.BISHOP_NAME;
					case QUEEN_BYTE:
						return EN_UK.QUEEN_NAME;
					case KING_BYTE:
						return EN_UK.KING_NAME;
					case EMPTY_BYTE:
						return "";
					default:
						throw new IllegalPieceException(Integer.toString(pieceNum) + " is not a valid piece.");
				}
			case NL_BE.LOCALE_BYTE:
				switch (pieceNum)
				{
					case PAWN_BYTE:
						return NL_BE.PAWN_NAME;
					case ROOK_BYTE:
						return NL_BE.ROOK_NAME;
					case KNIGHT_BYTE:
						return NL_BE.KNIGHT_NAME;
					case BISHOP_BYTE:
						return NL_BE.BISHOP_NAME;
					case QUEEN_BYTE:
						return NL_BE.QUEEN_NAME;
					case KING_BYTE:
						return NL_BE.KING_NAME;
					case EMPTY_BYTE:
						return "";
					default:
						throw new IllegalPieceException(Integer.toString(pieceNum) + " is not a valid piece.");
				}
			default:
				throw new IllegalArgumentException ("Invalid locale.");
		}
	}

	/**
	 * This method translates a human-readable string to a int-code.
	 * @param pieceName The string representation of the piece
	 * @param localeNum A int to indicate the used locale
	 * @return A int to represent the piece
	 * @throws IllegalPieceException
	 * @throws IllegalArgumentException
	 */
	public static int toNumFromString (String pieceName, int localeNum) throws IllegalPieceException, IllegalArgumentException
	{
		switch (localeNum)
		{
			case EN_UK.LOCALE_BYTE:
				switch (pieceName)
				{
					case EN_UK.PAWN_NAME:
						return PAWN_BYTE;
					case EN_UK.ROOK_NAME:
						return ROOK_BYTE;
					case EN_UK.KNIGHT_NAME:
						return KNIGHT_BYTE;
					case EN_UK.BISHOP_NAME:
						return BISHOP_BYTE;
					case EN_UK.QUEEN_NAME:
						return QUEEN_BYTE;
					case EN_UK.KING_NAME:
						return KING_BYTE;
					default:
						throw new IllegalPieceException(pieceName + " is not a valid piece name.");
				}

			case NL_BE.LOCALE_BYTE:
				switch (pieceName)
				{
					case NL_BE.PAWN_NAME:
						return PAWN_BYTE;
					case NL_BE.ROOK_NAME:
						return ROOK_BYTE;
					case NL_BE.KNIGHT_NAME:
						return KNIGHT_BYTE;
					case NL_BE.BISHOP_NAME:
						return BISHOP_BYTE;
					case NL_BE.QUEEN_NAME:
						return QUEEN_BYTE;
					case NL_BE.KING_NAME:
						return KING_BYTE;
					default:
						throw new IllegalPieceException(pieceName + " is not a valid piece name.");
				}
			default:
				throw new IllegalArgumentException ("Invalid locale.");
		}
	}

	/**
	 * Translates a piece's int to a 1-character string to represent that piece on a textual board
	 * @param localeNum The locale to be used for determining the piece short
	 * @param pieceNum A int representing a piece
	 * @return a 1-character string to represent that piece
	 * @throws IllegalPieceException
	 * @throws IllegalArgumentException
	 */
	public static String toShortFromNum(int pieceNum, int localeNum) throws IllegalPieceException, IllegalArgumentException
	{
		switch (localeNum)
		{
			case EN_UK.LOCALE_BYTE:
				switch (pieceNum)
				{
					case PAWN_BYTE:
						return EN_UK.PAWN_SHORT;
					case ROOK_BYTE:
						return EN_UK.ROOK_SHORT;
					case KNIGHT_BYTE:
						return EN_UK.KNIGHT_SHORT;
					case BISHOP_BYTE:
						return EN_UK.BISHOP_SHORT;
					case QUEEN_BYTE:
						return EN_UK.QUEEN_SHORT;
					case KING_BYTE:
						return EN_UK.KING_SHORT;
					case EMPTY_BYTE:
						return " ";
					default:
						throw new IllegalPieceException(Integer.toString(pieceNum) + " is not a valid piece.");
				}
			case NL_BE.LOCALE_BYTE:
				switch (pieceNum)
				{
					case PAWN_BYTE:
						return NL_BE.PAWN_SHORT;
					case ROOK_BYTE:
						return NL_BE.ROOK_SHORT;
					case KNIGHT_BYTE:
						return NL_BE.KNIGHT_SHORT;
					case BISHOP_BYTE:
						return NL_BE.BISHOP_SHORT;
					case QUEEN_BYTE:
						return NL_BE.QUEEN_SHORT;
					case KING_BYTE:
						return NL_BE.KING_SHORT;
					case EMPTY_BYTE:
						return " ";
					default:
						throw new IllegalPieceException(Integer.toString(pieceNum) + " is not a valid piece.");
				}
			default:
				throw new IllegalArgumentException ("Invalid locale.");
		}
	}

	/**
	 * Translates a piece's int to a 1-character string to represent that piece on a textual board. This uses the EN_UK locale.
	 * @param pieceNum A int representing a piece
	 * @return a 1-character string to represent that piece
	 * @throws IllegalPieceException
	 * @throws IllegalArgumentException
	 */
	public static String toShortFromNum(int pieceNum) throws IllegalPieceException, IllegalArgumentException
	{
		return toShortFromNum(pieceNum, EN_UK.LOCALE_BYTE);
	}

	/**
	 * This method translates a 1-character string to a int-code
	 * @param pieceShort The 1-character string representation of the piece
	 * @param localeNum A int to indicate the used locale
	 * @return A int to represent the piece
	 * @throws IllegalPieceException
	 * @throws IllegalArgumentException
	 */
	public static int toNumFromShort (String pieceShort, int localeNum) throws IllegalPieceException, IllegalArgumentException
	{
		switch (localeNum)
		{
			case EN_UK.LOCALE_BYTE:
				switch (pieceShort)
				{
					case EN_UK.PAWN_SHORT:
						return PAWN_BYTE;
					case EN_UK.ROOK_SHORT:
						return ROOK_BYTE;
					case EN_UK.KNIGHT_SHORT:
						return KNIGHT_BYTE;
					case EN_UK.BISHOP_SHORT:
						return BISHOP_BYTE;
					case EN_UK.QUEEN_SHORT:
						return QUEEN_BYTE;
					case EN_UK.KING_SHORT:
						return KING_BYTE;
					default:
						throw new IllegalPieceException(pieceShort + " is not a valid piece short.");
				}

			case NL_BE.LOCALE_BYTE:
				switch (pieceShort)
				{
					case NL_BE.PAWN_SHORT:
						return PAWN_BYTE;
					case NL_BE.ROOK_SHORT:
						return ROOK_BYTE;
					case NL_BE.KNIGHT_SHORT:
						return KNIGHT_BYTE;
					case NL_BE.BISHOP_SHORT:
						return BISHOP_BYTE;
					case NL_BE.QUEEN_SHORT:
						return QUEEN_BYTE;
					case NL_BE.KING_SHORT:
						return KING_BYTE;
					default:
						throw new IllegalPieceException(pieceShort + " is not a valid piece short.");
				}
			default:
				throw new IllegalArgumentException ("Invalid locale.");
		}
	}

	public static int getPieceScore (int pieceNum)
	{
		switch (pieceNum)
		{
			case PAWN_BYTE:
				return PAWN_SCORE;
			case ROOK_BYTE:
				return ROOK_SCORE;
			case KNIGHT_BYTE:
				return KNIGHT_SCORE;
			case BISHOP_BYTE:
				return BISHOP_SCORE;
			case QUEEN_BYTE:
				return QUEEN_SCORE;
			case KING_BYTE:
				return KING_SCORE;
			case EMPTY_BYTE:
				return 0;
			default:
				throw new IllegalPieceException(Integer.toString(pieceNum) + " is not a valid piece.");
		}
	}

	/**
	 * Thie function finds the color int of the opponent
	 * @param color The players color
	 * @return The opponent's color
	 * @throws IllegalSideException
	 */
	public static int getOpponentColorNum (int color) throws IllegalSideException
	{
		if (color == PieceData.WHITE_BYTE)
		{
			return PieceData.BLACK_BYTE;
		}
		else if (color == PieceData.BLACK_BYTE)
		{
			return PieceData.WHITE_BYTE;
		}
		else
		{
			throw new IllegalSideException(Integer.toString(color) + " is not a valid color.");
		}
	}
}
