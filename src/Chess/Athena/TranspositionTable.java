package Chess.Athena;

import Chess.Game.PieceData;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by thomas on 20/03/17.
 */
public class TranspositionTable
{
	private static TranspositionTable singleton;
	private static final int DB_ROWS = 12;
	private static final int DB_COLS = 64;
	private static long database[];
	private HashMap <Long, TableRecord> table;
	private int hits;

	private TranspositionTable ()
	{
		this.hits = 0;
		this.table = new HashMap<>();
		TranspositionTable.database = new long [DB_ROWS * DB_COLS];
		Random r = new Random();

		for (int i = 0; i < DB_ROWS; i++)
		{
			for (int j = 0; j < DB_COLS; j++)
			{
				database[(i * DB_ROWS) + DB_COLS] = r.nextLong();
			}
		}
	}

	public static TranspositionTable getInstance ()
	{
		if (TranspositionTable.singleton == null)
		{
			TranspositionTable.singleton = new TranspositionTable();
		}

		return TranspositionTable.singleton;
	}

	public void put (long key, TableRecord record)
	{
		this.table.put(key,record);
	}

	public TableRecord get (long  key)
	{
		TableRecord t;

		if ((t = this.table.get(key)) != null)
		{
			this.hits++;
		}

		return t;
	}

	public int size ()
	{
		return this.table.size();
	}

	public int getHits ()
	{
		return this.hits;
	}

	public static long getHash (int piece, int position)
	{
		if ((piece & PieceData.COLOR_MASK) == PieceData.WHITE_BYTE)
		{
			piece = piece & PieceData.PIECE_MASK;
		}
		else if ((piece & PieceData.COLOR_MASK) == PieceData.BLACK_BYTE)
		{
			piece = (piece & PieceData.PIECE_MASK) + 0x08;
		}

		return TranspositionTable.database[(piece * DB_ROWS) + DB_COLS];
	}
}
