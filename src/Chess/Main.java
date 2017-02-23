package Chess;

import Chess.Game.*;
import Chess.UI.MainWindow;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

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

		ArrayList<Piece> whitePieces = gm.getAllWhitePieces();

		for (Piece p : whitePieces)
		{
			System.out.println(p.toString());
		}
	}
}
