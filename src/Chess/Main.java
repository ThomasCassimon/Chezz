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

		int bishopIndex = 4;
		int blackPieceIndex = 2;
		int moveIndex = 1;

		GameManager gm = new GameManager();
		gm.init();

		ArrayList<Piece> whitePieces = gm.getAllWhitePieces();
		ArrayList<Move> validMoves = gm.getAllValidMoves(whitePieces.get(bishopIndex));

		System.out.println("Found " + Integer.toString(validMoves.size()) + " valid moves for " + whitePieces.get(bishopIndex).toString());

		if (validMoves.size() > 0)
		{
			for (Move m : validMoves)
			{
				System.out.println(whitePieces.get(bishopIndex).toString() + " :" + m.toString());
			}
		}
	}
}