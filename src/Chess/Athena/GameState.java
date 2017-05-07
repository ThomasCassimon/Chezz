package Chess.Athena;

import Chess.Game.Move;

import java.util.ArrayList;

/**
 * @author Thomas
 * @date 29/04/2017
 * <p>
 * Project: Chezz
 * Package: Chess.Athena
 */
public class GameState
{
	private ArrayList<Move> moves;

	public GameState (ArrayList<Move> moves)
	{
		this.moves = new ArrayList<Move>(moves);
	}
}
