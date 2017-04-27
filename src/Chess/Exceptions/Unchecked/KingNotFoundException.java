package Chess.Exceptions.Unchecked;

/**
 * Created by thomas on 27/04/17.
 */
public class KingNotFoundException extends RuntimeException
{
	public KingNotFoundException()
	{
		super ();
	}

	public KingNotFoundException(String message)
	{
		super (message);
	}

	public KingNotFoundException(Throwable cause)
	{
		super(cause);
	}

	public KingNotFoundException(String message, Throwable cause)
	{
		super (message, cause);
	}
}
