package Chess.Exceptions.Unchecked;

/**
 * Created by Thomas on 20/02/2017.
 */
public class IllegalPieceException extends RuntimeException
{
	public IllegalPieceException ()
	{
		super ();
	}
	
	public IllegalPieceException (String message)
	{
		super (message);
	}
	
	public IllegalPieceException (Throwable cause)
	{
		super(cause);
	}
	
	public IllegalPieceException (String message, Throwable cause)
	{
		super (message, cause);
	}
}
