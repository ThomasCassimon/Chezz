package Chess.Exceptions.Checked;

/**
 * Created by thomas on 20/04/17.
 */
public class GameOverException extends Exception
{
	public GameOverException ()
	{
		super ();
	}

	public GameOverException (String message)
	{
		super (message);
	}

	public GameOverException (Throwable cause)
	{
		super(cause);
	}

	public GameOverException (String message, Throwable cause)
	{
		super (message, cause);
	}
}
