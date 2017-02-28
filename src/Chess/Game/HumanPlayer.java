package Chess.Game;

/**
 * @author Thomas
 * @date 21/02/2017
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

	@Override
	public Move playTurn()
	{
		return new Move();
	}
}
