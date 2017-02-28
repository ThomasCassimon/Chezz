package Chess;

import Chess.Game.*;
import Chess.UI.MainWindow;

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
		//MainWindow mw = new MainWindow();

		GameManager gm = new GameManager();
		gm.init();

		ArrayList<Piece> whitePieces = gm.getAllWhitePieces();
		ArrayList<Move> validMoves = new ArrayList<>();

		for (Piece p : whitePieces)
		{
			if (p.getPieceWithoutColorByte() == PieceData.ROOK_BYTE)
			{
				validMoves = gm.getAllValidMoves(p);

				System.out.println("Found " + validMoves.size() + " valid moves for " + p.toString());

				for (byte i = 0; i < validMoves.size(); i++)
				{
					System.out.println(p.toString() + " moves: " + validMoves.get(i).toString());
				}
			}
		}
	}
}
