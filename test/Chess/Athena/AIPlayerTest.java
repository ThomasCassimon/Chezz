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

		assertEquals(0, aiWhite.scoreGame(gm));
		assertEquals(0, aiBlack.scoreGame(gm));
	}

	@Test
	public void scoreInitialGame () throws Exception
	{
		GameManager gm = new GameManager();
		gm.init();

		AIPlayer aiWhite = new AIPlayer (PieceData.WHITE_BYTE);
		AIPlayer aiBlack = new AIPlayer (PieceData.BLACK_BYTE);

		// Expected values calculated using excel sheet
		assertEquals(36827, aiWhite.scoreGame(gm));
		assertEquals(36827, aiBlack.scoreGame(gm));
	}
	
}