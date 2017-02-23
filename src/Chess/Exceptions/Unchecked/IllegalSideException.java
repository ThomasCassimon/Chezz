package Chess.Exceptions.Unchecked;

/**
 * @author Thomas
 * @since 20/02/2017
 *
 * Project: ChessGame
 * Package: Chess
 */
public class IllegalSideException extends RuntimeException
{
	public IllegalSideException()
	{
		super ();
	}

	public IllegalSideException(String message)
	{
		super (message);
	}

	public IllegalSideException(Throwable cause)
	{
		super(cause);
	}

	public IllegalSideException(String message, Throwable cause)
	{
		super (message, cause);
	}
}
