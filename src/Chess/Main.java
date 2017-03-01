package Chess;

import Chess.Game.*;
import Chess.UI.MainWindow;

import java.io.File;
import java.io.IOException;
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

		int whitePieceIndex = 1;
		int blackPieceIndex = 2;
		int moveIndex = 1;

		GameManager gm = new GameManager();
		gm.init();

		ArrayList<Piece> whitePieces = gm.getAllWhitePieces();
		ArrayList<Move> validMoves = gm.getAllValidMoves(whitePieces.get(whitePieceIndex));

		System.out.println("Found " + Integer.toString(validMoves.size()) + " valid moves for " + whitePieces.get(whitePieceIndex));

		System.out.println("Moving: " + validMoves.get(moveIndex).toString());
		gm.makeMove(validMoves.get(moveIndex));

		ArrayList<Piece> blackPieces = gm.getAllBlackPieces();
		validMoves = gm.getAllValidMoves(blackPieces.get(blackPieceIndex));

		System.out.println("Moving: " + validMoves.get(moveIndex).toString());
		gm.makeMove(validMoves.get(moveIndex));

		whitePieces = gm.getAllWhitePieces();
		validMoves = gm.getAllValidMoves(whitePieces.get(whitePieceIndex));

		System.out.println("Found " + Integer.toString(validMoves.size()) + " valid moves for " + whitePieces.get(whitePieceIndex));

		for (Move m : validMoves)
		{
			System.out.println("Found move for " + whitePieces.get(whitePieceIndex).toString() + " :" + m.toString());
		}
	}
}