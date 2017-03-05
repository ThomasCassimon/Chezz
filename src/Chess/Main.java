package Chess;

import Chess.Athena.AIPlayer;
import Chess.Game.*;
import Chess.UI.*;

import java.util.ArrayList;

import static java.lang.System.nanoTime;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess
 */
public class Main
{
	public static final int searchDepth = 2;
	public static void main (String[] args)
	{
		MainWindow mw = new MainWindow();

		GameManager gm = new GameManager();
		gm.init();

		AIPlayer athena = new AIPlayer(PieceData.WHITE_BYTE);

		long start = 0;
		long end = 0;
		Move move;

		start = System.nanoTime();

		move = athena.playTurn(gm, searchDepth);

		end = System.nanoTime();

		System.out.println("Took " + Long.toString(end - start) + "ns (= " + Long.toString((end - start) / 1000) + "µs = " + Long.toString((end - start) / 1000000) + "ms) to search " + Integer.toString(searchDepth) + " levels deep");
		System.out.println("Chosen move: " + move.toString());
	}
}