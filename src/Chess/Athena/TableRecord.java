package Chess.Athena;

import Chess.Game.Move;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by thomas on 23/03/17.
 */
public class TableRecord
{
	private int depth;
	private int score;
	private Move[] movelist;

	public TableRecord ()
	{
		this.score = 0;
		this.depth = 0;
		this.movelist = new Move [16];
	}

	public TableRecord (int score, int depth, ArrayList<Move> moves)
	{
		this.score = score;
		this.depth = depth;
		this.movelist = moves.toArray(new Move [0]);
	}

	public int getScore ()
	{
		return this.score;
	}

	public ArrayList <Move> getMoves ()
	{
		return new ArrayList <Move> (Arrays.asList(this.movelist));
	}
}
