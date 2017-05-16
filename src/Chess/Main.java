package Chess;

import Chess.Athena.AIPlayer;
import Chess.Game.GameManager;
import Chess.Game.Move;
import Chess.Game.PieceData;
import Chess.UI.MainFrame;
import Chess.Utils.Telemetry;

/**
 *
 * Project: ChessGame
 * Package: Chess
 */
public class Main
{
	public static void main (String[] args)
	{
		MainFrame mf = new MainFrame();
	}

	public static void testAI ()
	{
		//this.gameManager.isCheckMate(this.gameManager.getActiveColorByte());
		AIPlayer aip = new AIPlayer(PieceData.WHITE_BYTE, 4);
		long start = System.nanoTime();
		GameManager g = new GameManager();
		g.init();
		Move m = aip.playTurn(g);
		long end = System.nanoTime();

		Telemetry.printResults();
	}
}