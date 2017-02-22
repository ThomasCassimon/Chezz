package Chess.Game;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess.Game
 */
public abstract class Player
{
	protected byte colorByte;

	public Player (byte colorByte)
	{
		this.colorByte = colorByte;
	}

	public abstract Move playTurn ();
}
