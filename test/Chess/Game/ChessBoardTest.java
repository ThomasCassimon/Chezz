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

		for (byte i = 1; i <= 8; i++)
		{
			// White Pawns
			assertEquals(PieceData.PAWN_BYTE | PieceData.WHITE_MASK, cb.get(i,(byte) 2));

			for (byte j = 3; j < 7; j++)
			{
				assertEquals(PieceData.EMPTY_BYTE, cb.get(i, j));
			}

			// Black Pawns
			assertEquals(PieceData.PAWN_BYTE | PieceData.BLACK_MASK, cb.get(i,(byte) 7));
		}

		// White Rooks
		assertEquals (PieceData.ROOK_BYTE | PieceData.WHITE_MASK, cb.get((byte) 1, (byte) 1));
		assertEquals (PieceData.ROOK_BYTE | PieceData.WHITE_MASK, cb.get((byte) 8, (byte) 1));

		// Black Rooks
		assertEquals (PieceData.ROOK_BYTE | PieceData.BLACK_MASK, cb.get((byte) 1, (byte) 8));
		assertEquals (PieceData.ROOK_BYTE | PieceData.BLACK_MASK, cb.get((byte) 8, (byte) 8));

		// White Knights
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.WHITE_MASK, cb.get((byte) 2, (byte) 1));
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.WHITE_MASK, cb.get((byte) 7, (byte) 1));

		// Black Knights
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.BLACK_MASK, cb.get((byte) 2, (byte) 8));
		assertEquals (PieceData.KNIGHT_BYTE | PieceData.BLACK_MASK, cb.get((byte) 7, (byte) 8));

		// White Bishops
		assertEquals (PieceData.BISHOP_BYTE | PieceData.WHITE_MASK, cb.get((byte) 3, (byte) 1));
		assertEquals (PieceData.BISHOP_BYTE | PieceData.WHITE_MASK, cb.get((byte) 6, (byte) 1));

		// Black Bishops
		assertEquals (PieceData.BISHOP_BYTE | PieceData.BLACK_MASK, cb.get((byte) 3, (byte) 8));
		assertEquals (PieceData.BISHOP_BYTE | PieceData.BLACK_MASK, cb.get((byte) 6, (byte) 8));

		// White Queen
		assertEquals(PieceData.QUEEN_BYTE | PieceData.WHITE_MASK, cb.get((byte) 4, (byte) 1));

		// Black Queen
		assertEquals(PieceData.QUEEN_BYTE | PieceData.BLACK_MASK, cb.get((byte) 4, (byte) 8));

		// White King
		assertEquals(PieceData.KING_BYTE | PieceData.WHITE_MASK, cb.get((byte) 5, (byte) 1));

		// Black King
		assertEquals(PieceData.KING_BYTE | PieceData.BLACK_MASK, cb.get((byte) 5, (byte) 8));
	}

	@Test
	public void getPositive() throws Exception
	{
		ChessBoard cb = new ChessBoard();

		for (byte i = 1; i <= 8; i++)
		{
			for (byte j = 1; j <= 8; j++)
			{
				cb.get(i,j);
			}
		}
	}

	@Test(expected=IllegalSquareException.class)
	public void getIndexTooHigh() throws Exception
	{
		ChessBoard cb = new ChessBoard();

		cb.get((byte) 10, (byte) 10);
	}

	@Test(expected=IllegalSquareException.class)
	public void getIndexTooLow() throws Exception
	{
		ChessBoard cb = new ChessBoard();

		cb.get((byte) 0, (byte) 0);
	}

	@Test
	public void setPositive() throws Exception
	{
		ChessBoard cb = new ChessBoard();
		cb.set((byte) 1, (byte) 1, (byte) (PieceData.PAWN_BYTE | PieceData.WHITE_MASK));

		assertEquals((byte) (PieceData.PAWN_BYTE | PieceData.WHITE_MASK), cb.get((byte) 1, (byte) 1));
	}

	@Test (expected=IllegalSquareException.class)
	public void setIllegalSquare() throws Exception
	{
		ChessBoard cb = new ChessBoard();
		cb.set((byte) -1, (byte) -1, (byte) (PieceData.PAWN_BYTE | PieceData.WHITE_MASK));
	}

	@Test (expected=IllegalPieceException.class)
	public void setIllegalPiece() throws Exception
	{
		ChessBoard cb = new ChessBoard();
		cb.set((byte) 1, (byte) 1, (byte) (0xF0));
	}

	@Test
	public void get0x88Index() throws Exception
	{
		for (byte i = 1; i <= 8; i++)
		{
			for (byte j = 1; j <= 8; j++)
			{
				byte file = (byte) (i - 1);
				byte rank = (byte) (j - 1);
				byte index0x88 = (byte) ((16 * rank) + file);

				assertEquals(index0x88, ChessBoard.get0x88Index(i,j));
			}
		}
	}

	@Test
	public void get2DCoord() throws Exception
	{
		byte[] lowerLeft = {1,1};
		assertArrayEquals(lowerLeft, ChessBoard.get2DCoord((byte) 0x00));

		byte[] middleLeft = {1, 2};
		assertArrayEquals(middleLeft, ChessBoard.get2DCoord((byte) 0x10));

		byte[] middleRight = {8, 2};
		assertArrayEquals(middleRight, ChessBoard.get2DCoord((byte) 0x17));

		byte[] upperRight = {8,8};
		assertArrayEquals(upperRight, ChessBoard.get2DCoord((byte) 0x77));
	}
}