package Chess.Game;

/**
 * <p>
 * Project: ChessGame
 * Package: Chess.Game
 * </p>
 */
public class HumanPlayer extends Player
{
	public HumanPlayer(int colorByte)
	{
		super(colorByte);
	}

	public Move playTurn(GameManager gm)
	{
		return new Move();
	}
}
