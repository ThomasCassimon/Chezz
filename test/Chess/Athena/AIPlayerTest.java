package Chess.Athena;

import Chess.Game.GameManager;
import Chess.Game.PieceData;
import org.junit.Test;

import static org.junit.Assert.*;

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

		AIPlayer aiWhite = new AIPlayer(PieceData.WHITE_BYTE);
		AIPlayer aiBlack = new AIPlayer(PieceData.BLACK_BYTE);

		assertEquals(0, aiWhite.scoreGame(gm, PieceData.WHITE_BYTE));
		assertEquals(0, aiBlack.scoreGame(gm, PieceData.BLACK_BYTE));
	}

	@Test
	public void scoreInitialGame () throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();

		AIPlayer aiWhite = new AIPlayer (PieceData.WHITE_BYTE);
		AIPlayer aiBlack = new AIPlayer (PieceData.BLACK_BYTE);

		// Expected values calculated using excel sheet
		assertEquals(36827, aiWhite.scoreGame(gm, PieceData.WHITE_BYTE));
		assertEquals(36827, aiBlack.scoreGame(gm, PieceData.BLACK_BYTE));
	}
	
}