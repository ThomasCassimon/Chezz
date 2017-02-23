package Chess;

import Chess.Game.ChessBoard;
import Chess.Game.Move;
import Chess.Game.Piece;
import Chess.Game.PieceData;
import Chess.UI.MainWindow;

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

		ChessBoard cb = new ChessBoard();
		byte piece = PieceData.KING_BYTE;
		cb.set((byte) 1, (byte) 1, piece);
		Piece p = new Piece ((byte) (piece | PieceData.WHITE_MASK), ChessBoard.get0x88Index((byte) 1, (byte) 1));

		Move[] moves = p.getAllPossibleMoves();

		System.out.println("Got " + moves.length + " valid moves");

		for (int i = 0; i < moves.length; i++)
		{
			if (moves[i] == null)
			{
				System.out.println("null");
			}
			else
			{
				System.out.println(PieceData.toStringFromByte(piece, PieceData.EN_UK.LOCALE_BYTE) + " move: " + moves[i].toString());
			}
		}
	}
}
