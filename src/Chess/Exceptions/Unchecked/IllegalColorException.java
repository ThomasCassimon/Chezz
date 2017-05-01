package Chess.Exceptions.Unchecked;

/**
 * @author Thomas
 * @date 1/05/2017
 * <p>
 * Project: Chezz
 * Package: Chess.Game.Exceptions.Unchecked
 */
public class IllegalColorException extends RuntimeException
{
	public IllegalColorException ()
	{
		super ();
	}

	public IllegalColorException (String message)
	{
		super (message);
	}

	public IllegalColorException (Throwable cause)
	{
		super(cause);
	}

	public IllegalColorException (String message, Throwable cause)
	{
		super (message, cause);
	}
}
