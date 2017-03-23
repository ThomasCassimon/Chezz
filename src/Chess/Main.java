package Chess;

import Chess.Athena.AIPlayer;
import Chess.Athena.TableRecord;
import Chess.Athena.TranspositionTable;
import Chess.Game.ChessBoard;
import Chess.Game.GameManager;
import Chess.Game.Move;
import Chess.Game.PieceData;
import Chess.UI.*;
import Chess.Utils.Parser;

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
		TranspositionTable t = new TranspositionTable();
		System.out.println("old size of the table: " + Integer.toString(t.size()));
		long start = 0;
		long key = TranspositionTable.getHash(PieceData.PAWN_BYTE | PieceData.WHITE_BYTE, ChessBoard.get0x88Index(0,5));
		System.out.println("Key for white pawn on a6: " + Long.toBinaryString(key));
		TableRecord tr = new TableRecord();
		t.put(key, tr);
		System.out.println("new size of the table: " + Integer.toString(t.size()));
		System.out.println("start\t\t\t" + String.format("%64s",Long.toBinaryString(start)) + "\nkey\t\t\t\t" + String.format("%64s",Long.toBinaryString(key)) + "\n--------------------------------------------------------------------------------\nstart ^ key:\t" + String.format("%64s",Long.toBinaryString(start = (start ^ key))));
		key = TranspositionTable.getHash(PieceData.PAWN_BYTE | PieceData.BLACK_BYTE, ChessBoard.get0x88Index(0,5));
		t.put(key, tr);
		System.out.println("new size of the table: " + Integer.toString(t.size()));
		System.out.println("start\t\t\t" + String.format("%64s",Long.toBinaryString(start)) + "\nkey\t\t\t\t" + String.format("%64s",Long.toBinaryString(key)) + "\n--------------------------------------------------------------------------------\nstart ^ key:\t" + String.format("%64s",Long.toBinaryString(start ^ key)));

		GamePanel gamePanel = new GamePanel();
	}
}