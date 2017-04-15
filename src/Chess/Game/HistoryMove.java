package Chess.Game;

/**
 * Created by thomas on 15.04.17.
 * This class will be used to store a game's history.
 * When a move is a capture the capturedPiece is valid, this allows for undoing and replacing captured pieces
 */
public class HistoryMove extends Move
{
	private Piece capturedPiece;

	public HistoryMove (Move m)
	{
		this.capturedPiece = null;
	}

	public HistoryMove (Move m, Piece p)
	{
		this.capturedPiece = p;
	}

	public Piece getCapturedPiece()
	{
		return this.capturedPiece;
	}
}
