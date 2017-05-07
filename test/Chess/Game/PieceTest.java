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
public class PieceTest
{
	@Test
	public void get2DCoord() throws Exception
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				int index = ChessBoard.get0x88Index(i,j);
				Piece p = new Piece (PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, index);
				assertArrayEquals(new int [] {i,j}, p.get2DCoord());
			}
		}
	}

	@Test
	public void getPositionByte() throws Exception
	{
		byte pieceByte = PieceData.PAWN_BYTE;
		Piece p;

		for (int i = 1; i <= 8; i++)
		{
			for (int j = 1; j <= 8; j++)
			{
				int index0x88 = ChessBoard.get0x88Index(i,j);

				p = new Piece (pieceByte, i, j);
				assertEquals(index0x88, p.getPositionByte());

				p = new Piece (pieceByte, index0x88);
				assertEquals(index0x88, p.getPositionByte());
			}
		}
	}
	
}