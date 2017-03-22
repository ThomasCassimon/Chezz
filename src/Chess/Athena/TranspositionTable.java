package Chess.Athena;

import Chess.Game.ChessBoard;
import Chess.Game.Piece;
import Chess.Game.PieceData;
import Chess.UI.UIData;

import java.util.Hashtable;
import java.util.Random;

/**
 * Created by thomas on 20/03/17.
 */
public class TranspositionTable
{
	public static final int PAWN_INDEX = 0;
	public static final int ROOK_INDEX = 1;
	public static final int KNIGHT_INDEX = 2;
	public static final int BISHOP_INDEX = 3;
	public static final int QUEEN_INDEX = 4;
	public static final int KING_INDEX = 5;
	public static final int WHITE_SHIFT = 0;
	public static final int BLACK_SHIFT = KING_INDEX + 1;

	private static TranspositionTable table;
	private Hashtable <Long, Integer> hashTable;
	private long [] pieceSquareRandomTable;
	private long blackTurnHash;

	private TranspositionTable ()
	{
		this.hashTable = new Hashtable<>(2048);
		this.pieceSquareRandomTable = new long [PieceData.NUM_PIECES * PieceData.NUM_SQUARES];
		Random r = new Random();

		for (int i = 0; i < PieceData.NUM_PIECES; i++)
		{
			for (int j = 0; j < PieceData.NUM_SQUARES; j++)
			{
				this.pieceSquareRandomTable[(i * PieceData.NUM_PIECES) + j] = r.nextLong();
			}
		}

		this.blackTurnHash = r.nextLong();
	}

	public static TranspositionTable getInstance ()
	{
		if (TranspositionTable.table == null)
		{
			TranspositionTable.table = new TranspositionTable();
		}

		return TranspositionTable.table;
	}

	public int put (long hash, int score)
	{
			this.hashTable.put(hash, score);
			return this.hashTable.size();
	}

	public Integer get (long hash)
	{
		return this.hashTable.get(hash);
	}

	public long getHash (int pieceIndex, int index0x88)
	{
		//System.out.println("pieceIndex " + Integer.toString(pieceIndex));
		return this.pieceSquareRandomTable[(pieceIndex * PieceData.NUM_PIECES) + (ChessBoard.get2DCoord(index0x88)[0] * UIData.NUMBER_TILES) + ChessBoard.get2DCoord(index0x88)[1]];
	}

	public long getHash (int pieceIndex, int file, int rank)
	{
		return this.pieceSquareRandomTable[(pieceIndex * PieceData.NUM_PIECES) + (file * UIData.NUMBER_TILES) + rank];
	}

	public long getBlackTurnHash ()
	{
		return this.blackTurnHash;
	}

	public static int getShift (int color)
	{
		if (color == PieceData.WHITE_BYTE)
		{
			return TranspositionTable.WHITE_SHIFT;
		}
		else if (color == PieceData.BLACK_BYTE)
		{
			return TranspositionTable.BLACK_SHIFT;
		}
		else
		{
			return Integer.MAX_VALUE;
		}
	}

	public static Integer getPieceIndex (Piece p)
	{
		int shift = TranspositionTable.getShift(p.getColor());

		switch (p.getPieceWithoutColorByte())
		{
			case PieceData.PAWN_BYTE:
				return TranspositionTable.PAWN_INDEX + shift;
			case PieceData.ROOK_BYTE:
				return TranspositionTable.ROOK_INDEX + shift;
			case PieceData.KNIGHT_BYTE:
				return TranspositionTable.KNIGHT_INDEX + shift;
			case PieceData.BISHOP_BYTE:
				return TranspositionTable.BISHOP_INDEX + shift;
			case PieceData.QUEEN_BYTE:
				return TranspositionTable.QUEEN_INDEX + shift;
			case PieceData.KING_BYTE:
				return TranspositionTable.KING_INDEX + shift;
			case 0:
				return null;
			default:
				return Integer.MAX_VALUE;
		}
	}
}
