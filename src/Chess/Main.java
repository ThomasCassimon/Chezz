package Chess;

import Chess.Athena.AIPlayer;
import Chess.Game.GameManager;
import Chess.Game.Move;
import Chess.Game.PieceData;
import Chess.Utils.Telemetry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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