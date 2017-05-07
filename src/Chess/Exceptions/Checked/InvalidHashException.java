package Chess.Exceptions.Checked;

/**
 * Created by thomas on 20/03/17.
 */
public class InvalidHashException extends Exception
{
	public InvalidHashException ()
	{
		super ();
	}

	public InvalidHashException (String message)
	{
		super (message);
	}

	public InvalidHashException (Throwable cause)
	{
		super(cause);
	}

	public InvalidHashException (String message, Throwable cause)
	{
		super (message, cause);
	}
}
