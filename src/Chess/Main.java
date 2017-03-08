package Chess;

import Chess.Athena.AIPlayer;
import Chess.Game.*;
import Chess.Time.Chronometer;
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
	public static Chronometer chronometerWhite;
	public static Chronometer chronometerBlack;


	public static void main (String[] args)
	{
		chronometerWhite = new Chronometer(100000);
		chronometerBlack = new Chronometer(100000);


		MainWindow mw = new MainWindow();
	}
}