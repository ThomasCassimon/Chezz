package Chess.Game;

import Chess.Exceptions.Unchecked.IllegalSquareException;
import Chess.Exceptions.Unchecked.IllegalPieceException;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */

/**
 * A Class to represent a chessboard. A 0x88 system is used to store board contents.</br>
 * For more info: <a href="https://chessprogramming.wikispaces.com/0x88?responseToken=0e7f2c1d5523dda265cbd99001abf6ef1">ChessProgramming Wiki</a>
 */
public class ChessBoard
{
	public static final short BOARD_ARRAY_SIZE = 128;
	public static final int ERROR = -1;
	/*
	 *	Actual board...
	 */
	private int[] board;

	/**
	 * Default Constructor, takes no arguments, initializes a board to an empty situation
	 */
	public ChessBoard()
	{
		this.board = new int [BOARD_ARRAY_SIZE];
	}

	/**
	 * Usually called right after the constructor, initializes the board to chess' default starting position
	 * @return ChessBoard, returns itself for easy method chaining
	 */
	public ChessBoard init()
	{
		// Putting down pawns and empty spaces
		for (int i = 1; i <= 8; i++)
		{
			this.set(i,2, PieceData.PAWN_BYTE | PieceData.WHITE_BYTE);
			this.set(i,3, PieceData.EMPTY_BYTE);
			this.set(i,4, PieceData.EMPTY_BYTE);
			this.set(i,5, PieceData.EMPTY_BYTE);
			this.set(i,6, PieceData.EMPTY_BYTE);
			this.set(i,7, PieceData.PAWN_BYTE | PieceData.BLACK_BYTE);
		}

		// Putting down Rooks
		this.set(1, 1, PieceData.ROOK_BYTE | PieceData.WHITE_BYTE);
		this.set(8, 1, PieceData.ROOK_BYTE | PieceData.WHITE_BYTE);

		this.set(1, 8, PieceData.ROOK_BYTE | PieceData.BLACK_BYTE);
		this.set(8, 8, PieceData.ROOK_BYTE | PieceData.BLACK_BYTE);

		// Knights
		this.set(2, 1, PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE);
		this.set(7, 1, PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE);

		this.set(2, 8, PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE);
		this.set(7, 8, PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE);

		// Bishops
		this.set(3, 1, PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE);
		this.set(6, 1, PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE);

		this.set(3, 8, PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE);
		this.set(6, 8, PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE);

		// Queens
		this.set(5, 1, PieceData.QUEEN_BYTE | PieceData.WHITE_BYTE);
		this.set(5, 8, PieceData.QUEEN_BYTE | PieceData.BLACK_BYTE);

		// Kings
		this.set(4, 1, PieceData.KING_BYTE | PieceData.WHITE_BYTE);
		this.set(4, 8, PieceData.KING_BYTE | PieceData.BLACK_BYTE);

		return this;
	}

	/**
	 * This method returns a byte-code that represents a certain piece.
	 * A rank is a number from 1 to 7 (both inclusive)
	 * A file is letter from A to H (both inclusive, case insensitive)
	 * @param rank The rank of the piece (1-7)
	 * @param file The file of the piece (A-H)
	 * @return A byte representing the piece
	 */
	public int get (int file, int rank) throws IllegalSquareException
	{
		return this.board[get0x88Index(file, rank)];
	}

	/**
	 * This method returns a byte-code that represents a certain piece.
	 * The index is a valid 0x88 index
	 * @param index
	 * @return A byte representing the piece
	 */
	public int get (int index)
	{
		return this.board[index];
	}

	/**
	 * Sets the specified square to contain the specified piece.
	 * Throws an exception if either the square or the piece are invalid
	 * @param file The piece's file
	 * @param rank The piece's rank
	 * @param piece The piece's byte representation
	 * @throws IllegalSquareException
	 * @throws IllegalPieceException
	 */
	public void set (int file, int rank, int piece) throws IllegalSquareException, IllegalPieceException
	{
		int bitMask = ~(PieceData.BLACK_BYTE | PieceData.WHITE_BYTE | 0x07); // First two disable color-bits, last one disables piece-bits

		int index = get0x88Index(file, rank);

		if (((piece & bitMask) != 0) || ((piece & 0x07) == 0x07))
		{
			throw new IllegalPieceException(Integer.toString(piece) + " (" + (piece & bitMask) + ") is not a valid piece.");
		}
		else
		{
			this.board[index] = piece;
		}
	}

	/**
	 * Sets the specified square to contain the specified piece.
	 * Throws an exception if either the square or the piece are invalid
	 * @param index
	 * @param piece The piece's byte representation
	 * @throws IllegalSquareException
	 * @throws IllegalPieceException
	 */
	public void set (int index, int piece) throws IllegalSquareException, IllegalPieceException
	{
		byte bitMask =  ~(PieceData.BLACK_BYTE | PieceData.WHITE_BYTE | 0x07); // First two disable color-bits, last one disables piece-bits

		if (((piece & bitMask) != 0) || ((piece & 0x07) == 0x07))
		{
			throw new IllegalPieceException(Integer.toString(piece) + " (" + (piece & bitMask) + ") is not a valid piece.");
		}
		else
		{
			this.board[index] = piece;
		}
	}

	/**
	 * Translates file-first indexing to 0x88 indexing, throws exception for invalid coordinates
	 * A rank is a number from 1 to 7 (both inclusive)
	 * A file is letter from A to H (both inclusive, case insensitive)
	 * @param rank The rank of the piece (1-7)
	 * @param file The file of the piece (A-H)
	 * @return A 0x88 index
	 * @throws IllegalSquareException
	 */
	public static int get0x88Index (int file, int rank) throws IllegalSquareException
	{
		return  ((16 * (rank - 1)) + (file - 1));   // Calculating 0x88 index
		// The -1's correct for arrays starting at 0
		// But board-indexing starting at 1
	}

	/**
	 * Translates 0x88 indexing to file-first indexing, throws exception for invalid coordinates
	 * A rank is a number from 1 to 7 (both inclusive)
	 * A file is letter from A to H (both inclusive, case insensitive)
	 * @param index The 0x88 index of the square
	 * @return A 2-byte array, file-first indexing
	 * @throws IllegalSquareException
	 */
	public static int[] get2DCoord (int index)
	{
		int[] res = new int [2];

		res[0] =  ((index & 7) + 1);
		res[1] =  ((index >> 4) + 1);

		return res;
	}

	/**
	 * Returns a string representation of the board
	 * @param localeByte A locale byte to indicate the desired locale
	 * @return A string (containing newlines) that is a textual representation of the board
	 */
	public String toString (int localeByte)
	{
		String res = "";

		for (byte i = 1; i <= 8; i++)
		{
			for (byte j = 1; j <= 8; j++)
			{
				res += PieceData.toShortFromNum( (this.get(j, i) & 0x0F), localeByte);
			}

			res += "\n";
		}

		return res;
	}

	/**
	 * Wrapper method for {@link ChessBoard#toString()}
	 * @param localeByte A byte representing the desired locale
	 */
	public void print (byte localeByte)
	{
		System.out.println(this.toString(localeByte));
	}
}
