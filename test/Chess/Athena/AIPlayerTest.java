package Chess.Athena;

import Chess.Game.GameManager;
import Chess.Game.PieceData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Thomas
 * @since 21/02/2017
 * <p>
 * Project: ChessGame
 * Package: Chess.Athena
 */
public class AIPlayerTest
{
	@Test
	public void scoreZeroGame() throws Exception
	{
		GameManager gm = new GameManager();

		AIPlayer aiWhite = new AIPlayer(PieceData.WHITE_BYTE, 0);
		AIPlayer aiBlack = new AIPlayer(PieceData.BLACK_BYTE, 0);

		assertEquals(0, aiWhite.scoreGame(gm, PieceData.WHITE_BYTE));
		assertEquals(0, aiBlack.scoreGame(gm, PieceData.BLACK_BYTE));
	}

	@Test
	public void scoreInitialGame () throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();

		AIPlayer aiWhite = new AIPlayer (PieceData.WHITE_BYTE, 0);
		AIPlayer aiBlack = new AIPlayer (PieceData.BLACK_BYTE, 0);

		// Expected values calculated using excel sheet
		assertEquals(36827, aiWhite.scoreGame(gm, PieceData.WHITE_BYTE));
		assertEquals(36827, aiBlack.scoreGame(gm, PieceData.BLACK_BYTE));
	}

	@Test
	public void testHashing () throws Exception
	{
		GameManager gm1 = new GameManager();
		gm1.init();

		GameManager gm2 = new GameManager();
		gm2.init();

		AIPlayer aip = new AIPlayer(PieceData.WHITE_BYTE, 1);
		aip.playTurn(gm1);
		//assertTrue("Table size is not > 0", AIPlayer.transpositionTable.size() > 0);
		aip.playTurn(gm2);
		//assertTrue("No hits were made", AIPlayer.transpositionTable.getHits() > 0);
	}
}