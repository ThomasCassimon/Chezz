package Chess.Game;

/**
 * Created by thomas on 25/03/17.
 * Checks wheter or not the given square is being attacked
 * It uses the given offset-array to determine this
 * Used for parallelising GameManager.isAttacked()
 */
public class AttackChecker extends Thread
{
	boolean isAttacked;

	int[] offsetArray;
	int index0x88;
	int pieceByte;
	int color;
	int opponentColor;

	GameManager gm;

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
		int tmp = 0;

		for (int i = 0; i < this.offsetArray.length; i++)
		{
			if (((tmp = index0x88 + Movesets.ROOK_MOVE[i]) & 0x88) == 0)
			{
				if (this.gm.get(tmp).getColor() == opponentColor)
				{
					if (this.gm.get(tmp).getPieceWithoutColorByte() == this.pieceByte)
					{
						if(this.gm.isValidMove(this.pieceByte, this.color, new Move (tmp, index0x88, 0x0)))
						{
							this.isAttacked = true;
							break;
						}
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
