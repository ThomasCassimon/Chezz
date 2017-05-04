package Chess.Game;

/**
 * Created by thomas on 25/03/17.
 * Checks wheter or not the given square is being attacked
 * It uses the given offset-array to determine this
 * Used for parallelising GameManager.isAttacked()
 */
public class AttackChecker extends Thread
{
	private boolean isAttacked;

	private int[] offsetArray;
	private int pieceByte;
	private int index0x88;
	private int color;
	private int opponentColor;

	private GameManager gm;

	public AttackChecker (int[] offsetArray, int pieceByte, int color, int index0x88, GameManager gm)
	{
		this.isAttacked = false;
		this.offsetArray = offsetArray;
		this.pieceByte = pieceByte & PieceData.PIECE_MASK;
		this.index0x88 = index0x88;
		this.color = color;
		this.opponentColor = PieceData.getOpponentColor(color);
		this.gm = gm;
	}

	@Override
	public void run()
	{
		// Loop over all moves in move-offset-array
		for (int moveOffset : this.offsetArray)
		{
			int currentSquare = index0x88 + moveOffset;

			// Check if move is on the board
			if ((currentSquare & 0x88) == 0)
			{
				// We found a piece that matches the given moveset and belongs to the opponent
				Piece piece = this.gm.get(currentSquare);

				if ((piece.getPieceWithoutColorByte() == this.pieceByte) && (piece.getColor() == this.opponentColor))
				{
					//System.out.println("[attackChecker()]\tFound a " + PieceData.toStringFromNum(piece.getPieceWithoutColorByte()) + " on its moves (" + piece.toString() + ")");
					//System.out.println("Checking for " + gm.get(tmp).toString());
					//System.out.println("Checking attack from: " + (char) (ChessBoard.get2DCoord(tmp)[0] + 'a') + (ChessBoard.get2DCoord(tmp)[1] + 1));

					Move m = new Move(currentSquare, index0x88, 0x0);
					this.gm.setFlags(this.opponentColor, m);

					//System.out.println("[attackChecker()]\tChecking move: " + m.toString());

					if (this.gm.isValidMove(piece.getPieceWithoutColorByte(), piece.getColor(), m))
					{
						//System.out.println("Color: " + Integer.toHexString(this.opponentColor));
						//System.out.println(piece.toString() + " is attacking piece " + this.gm.get(index0x88));
						this.isAttacked = true;
						break;
					}
				}
			}
		}
	}

	public boolean getResult()
	{
		return this.isAttacked;
	}
}
