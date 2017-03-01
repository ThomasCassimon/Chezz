package Chess.Game;

import Chess.Exceptions.Unchecked.IllegalPieceException;
import Chess.Exceptions.Unchecked.IllegalSquareException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Thomas
 * @since 20/02/2017
 */
public class ChessBoardTest
{
	@Test
	public void init() throws Exception
	{
		ChessBoard cb = new ChessBoard();
		cb.init();

		for (int i = 1; i <= 8; i++)
		{
			// White Pawns
			assertEquals(PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, cb.get(i, 2));

			for (int j = 3; j < 7; j++)
			{
				assertEquals(PieceData.EMPTY_BYTE, cb.get(i, j));
			}

			// Black Pawns
			assertEquals(PieceData.PAWN_BYTE | PieceData.BLACK_BYTE, cb.get(i, 7));
		}

		// White Rooks
		assertEquals (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, cb.get( 1,  1));
		assertEquals (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, cb.get( 8,  1));

		// Black Rooks
		assertEquals (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, cb.get( 1,  8));
		assertEquals (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, cb.get( 8,  8));

		// White Knights
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, cb.get( 2,  1));
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, cb.get( 7,  1));

		// Black Knights
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, cb.get( 2,  8));
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, cb.get( 7,  8));

		// White Bishops
		assertEquals (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, cb.get( 3,  1));
		assertEquals (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, cb.get( 6,  1));

		// Black Bishops
		assertEquals (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, cb.get( 3,  8));
		assertEquals (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, cb.get( 6,  8));

		// White Queen
		assertEquals(PieceData.QUEEN_BYTE | PieceData.WHITE_BYTE, cb.get( 5,  1));

		// Black Queen
		assertEquals(PieceData.QUEEN_BYTE | PieceData.BLACK_BYTE, cb.get( 5,  8));

		// White King
		assertEquals(PieceData.KING_BYTE | PieceData.WHITE_BYTE, cb.get( 4,  1));

		// Black King
		assertEquals(PieceData.KING_BYTE | PieceData.BLACK_BYTE, cb.get( 4,  8));
	}

	@Test
	public void getPositive() throws Exception
	{
		ChessBoard cb = new ChessBoard();

		for (int i = 1; i <= 8; i++)
		{
			for (int j = 1; j <= 8; j++)
			{
				cb.get(i,j);
			}
		}
	}

	@Test(expected=IllegalSquareException.class)
	public void getIndexTooHigh() throws Exception
	{
		ChessBoard cb = new ChessBoard();

		cb.get( 10,  10);
	}

	@Test(expected=IllegalSquareException.class)
	public void getIndexTooLow() throws Exception
	{
		ChessBoard cb = new ChessBoard();

		cb.get( 0,  0);
	}

	@Test
	public void setPositive() throws Exception
	{
		ChessBoard cb = new ChessBoard();
		cb.set( 1,  1,  (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE));

		assertEquals( (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE), cb.get( 1,  1));
	}

	@Test (expected=IllegalSquareException.class)
	public void setIllegalSquare() throws Exception
	{
		ChessBoard cb = new ChessBoard();
		cb.set( -1,  -1,  (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE));
	}

	@Test (expected=IllegalPieceException.class)
	public void setIllegalPiece() throws Exception
	{
		ChessBoard cb = new ChessBoard();
		cb.set( 1,  1,  (0xF0));
	}

	@Test
	public void get0x88Index() throws Exception
	{
		for (int i = 1; i <= 8; i++)
		{
			for (int j = 1; j <= 8; j++)
			{
				int file =  (i - 1);
				int rank =  (j - 1);
				int index0x88 =  ((16 * rank) + file);

				assertEquals(index0x88, ChessBoard.get0x88Index(i,j));
			}
		}
	}

	@Test
	public void get2DCoord() throws Exception
	{
		for (int i = 1; i <= 8; i++)
		{
			for (int j = 1; j <= 8; j++)
			{
				int file = i - 1;
				int rank = j - 1;
				int[] coord = {i,j};
				assertArrayEquals(coord, ChessBoard.get2DCoord((16 * rank) + file));
			}
		}
	}
}