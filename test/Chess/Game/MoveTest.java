package Chess.Game;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Thomas
 * @since 21/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */
public class MoveTest
{
	@Test
	public void getSrc() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x0;
		Move m;

		m = new Move ();
		assertEquals((byte) 0xFF, m.getSrc());

		m = new Move (src, dst, special);
		assertEquals(src, m.getSrc());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertEquals(src, m.getSrc());
	}

	@Test
	public void getDst() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x0;
		Move m;

		m = new Move ();
		assertEquals((byte) 0xFF, m.getDst());

		m = new Move (src, dst, special);
		assertEquals(dst, m.getDst());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertEquals(dst, m.getDst());
	}

	@Test
	public void getSpecial() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x0;
		Move m;

		m = new Move ();
		assertEquals((byte) 0xFF, m.getSpecial());

		m = new Move (src, dst, special);
		assertEquals(special, m.getSpecial());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertEquals(special, m.getSpecial());
	}

	@Test
	public void setSpecial() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special1 = (byte) 0x0;
		byte special2 = (byte) 0x8;
		Move m;

		m = new Move ();
		m.setSpecial(special2);
		assertEquals(special2, m.getSpecial());

		m = new Move (src, dst, special1);
		m.setSpecial(special2);
		assertEquals(special2, m.getSpecial());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special1);
		m.setSpecial(special2);
		assertEquals(special2, m.getSpecial());
	}

	@Test
	public void isQuiet() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x0;
		Move m;

		m = new Move (src, dst, special);
		assertEquals(true, m.isQuiet());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertEquals(true, m.isQuiet());
	}

	@Test
	public void isDoublePawnPush() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x1;
		Move m;

		m = new Move (src, dst, special);
		assertEquals(true, m.isDoublePawnPush());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertEquals(true, m.isDoublePawnPush());
	}

	@Test
	public void isKingCastle() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x2;
		Move m;

		m = new Move (src, dst, special);
		assertEquals(true, m.isKingCastle());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertEquals(true, m.isKingCastle());
	}

	@Test
	public void isQueenCastle() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x3;
		Move m;

		m = new Move (src, dst, special);
		assertEquals(true, m.isQueenCastle());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertEquals(true, m.isQueenCastle());
	}

	@Test
	public void isCapture() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x4;
		Move m;

		m = new Move (src, dst, special);
		assertEquals(true, m.isCapture());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertEquals(true, m.isCapture());

		special = (byte) 0x5;

		m = new Move (src, dst, special);
		assertEquals(true, m.isCapture());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertEquals(true, m.isCapture());

		for (byte i = 12; i <= 15; i++)
		{
			m = new Move (src, dst, i);
			assertEquals(true, m.isCapture());

			m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], i);
			assertEquals(true, m.isCapture());
		}
	}

	@Test
	public void isPromotion() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		Move m;

		for (byte i = 8; i <= 15; i++)
		{
			m = new Move (src, dst, i);
			assertEquals(true, m.isPromotion());

			m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], i);
			assertEquals(true, m.isPromotion());
		}
	}

	@Test
	public void get2DSrc() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x0;
		Move m;

		/*
		Throws invalid square exception (as it should)
		m = new Move ();
		assertEquals((byte) 0xFF, m.get2DSrc());
		*/

		m = new Move (src, dst, special);
		assertArrayEquals(srcFileRank, m.get2DSrc());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertArrayEquals(srcFileRank, m.get2DSrc());
	}

	@Test
	public void get2DDst() throws Exception
	{
		byte src = (byte) 0x44;
		byte dst = (byte) 0x33;
		byte[] srcFileRank = ChessBoard.get2DCoord(src);
		byte[] dstFileRank = ChessBoard.get2DCoord(dst);

		byte special = (byte) 0x0;
		Move m;

		/*
		Throws invalid square exception (as it should)
		m = new Move ();
		assertEquals((byte) 0xFF, m.get2DSrc());
		*/

		m = new Move (src, dst, special);
		assertArrayEquals(dstFileRank, m.get2DDst());

		m = new Move (srcFileRank[0], srcFileRank[1], dstFileRank[0], dstFileRank[1], special);
		assertArrayEquals(dstFileRank, m.get2DDst());
	}
	
}