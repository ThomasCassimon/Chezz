package Chess;

import Chess.Game.*;
import Chess.UI.*;

import java.util.ArrayList;

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
		MainWindow mw = new MainWindow();

		GameManager gm = new GameManager();
		gm.init();
		System.out.println("Move: " + gm.makeMove(new Move(5,1,1,4,0)) + "");
	}
}