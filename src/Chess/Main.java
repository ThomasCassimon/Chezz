package Chess;

import Chess.Athena.AIPlayer;
import Chess.Game.GameManager;
import Chess.Game.Move;
import Chess.Game.PieceData;
import Chess.UI.MainFrame;
import Chess.Utils.Telemetry;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess
 */
public class Main
{
	public static void main (String[] args)
	{
		/*
		System.out.println("Press ENTER to begin");
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			in.readLine();
		}
		catch (IOException ioe)
		{

		}

		Main.testAI();

		System.exit(0);
		*/

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