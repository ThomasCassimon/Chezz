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

		for (int i = 0; i < 8; i++)
		{
			// White Pawns
			assertEquals(PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, cb.get(i, 1));

			for (int j = 2; j < 6; j++)
			{
				assertEquals(PieceData.EMPTY_BYTE, cb.get(i, j));
			}

			// Black Pawns
			assertEquals(PieceData.PAWN_BYTE | PieceData.BLACK_BYTE, cb.get(i, 6));
		}

		// White Rooks
		assertEquals (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, cb.get( 0, 0));
		assertEquals (PieceData.ROOK_BYTE | PieceData.WHITE_BYTE, cb.get( 7, 0));

		// Black Rooks
		assertEquals (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, cb.get( 0, 7));
		assertEquals (PieceData.ROOK_BYTE | PieceData.BLACK_BYTE, cb.get( 7, 7));

		// White Knights
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, cb.get( 1, 0));
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.WHITE_BYTE, cb.get( 6, 0));

		// Black Knights
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, cb.get( 1, 7));
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.BLACK_BYTE, cb.get( 6, 7));

		// White Bishops
		assertEquals (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, cb.get( 2, 0));
		assertEquals (PieceData.BISHOP_BYTE | PieceData.WHITE_BYTE, cb.get( 5, 0));

		// Black Bishops
		assertEquals (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, cb.get( 2, 7));
		assertEquals (PieceData.BISHOP_BYTE | PieceData.BLACK_BYTE, cb.get( 5, 7));

		// White Queen
		assertEquals(PieceData.QUEEN_BYTE | PieceData.WHITE_BYTE, cb.get(4, 0));

		// Black Queen
		assertEquals(PieceData.QUEEN_BYTE | PieceData.BLACK_BYTE, cb.get(4, 7));

		// White King
		assertEquals(PieceData.KING_BYTE | PieceData.WHITE_BYTE, cb.get( 3,  0));

		// Black King
		assertEquals(PieceData.KING_BYTE | PieceData.BLACK_BYTE, cb.get( 3,  7));
	}

	@Test
	public void getPositive() throws Exception
	{
		ChessBoard cb = new ChessBoard();

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				cb.get(i,j);
			}
		}
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void getIndexTooHigh() throws Exception
	{
		ChessBoard cb = new ChessBoard();

		cb.get( 10,  10);
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void getIndexTooLow() throws Exception
	{
		ChessBoard cb = new ChessBoard();

		cb.get( -1,  -1);
	}

	@Test
	public void setPositive() throws Exception
	{
		ChessBoard cb = new ChessBoard();
		cb.set( 1,  1,  (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE));

		assertEquals( (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE), cb.get( 1,  1));
	}

	@Test (expected=ArrayIndexOutOfBoundsException.class)
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
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				int file = i;
				int rank = j;
				int index0x88 =  ((16 * rank) + file);

				assertEquals(index0x88, ChessBoard.get0x88Index(i,j));
			}
		}
	}

	@Test
	public void get2DCoord() throws Exception
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				int file = i;
				int rank = j;
				int[] coord = {i,j};
				assertArrayEquals(coord, ChessBoard.get2DCoord((16 * rank) + file));
			}
		}
	}
}