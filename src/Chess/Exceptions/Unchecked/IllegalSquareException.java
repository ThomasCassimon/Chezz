package Chess.Exceptions.Unchecked;

/**
 * Created by Thomas on 20/02/2017.
 */
public class IllegalSquareException extends RuntimeException
{
	public IllegalSquareException()
	{
		super ();
	}

	public IllegalSquareException(String message)
	{
		super (message);
	}

	public IllegalSquareException(Throwable cause)
	{
		super(cause);
	}

	public IllegalSquareException(String message, Throwable cause)
	{
		super (message, cause);
	}
}
