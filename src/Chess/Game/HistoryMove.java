package Chess.Game;

/**
 * This class will be used to store a game's history.
 * When a move is a capture the capturedPiece is valid, this allows for undoing and replacing captured pieces
 */
public class HistoryMove extends Move
{
	private Piece capturedPiece;

	public HistoryMove (Move m)
	{
		super(m);
		this.capturedPiece = null;
	}

	public HistoryMove (Move m, Piece p)
	{
		super(m);
		this.capturedPiece = p;
	}

	public Piece getCapturedPiece()
	{
		return this.capturedPiece;
	}
}
